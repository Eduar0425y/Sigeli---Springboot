package com.example.fesc.sigeliFesc.services;

import com.example.fesc.sigeliFesc.data.entidades.MultaEntity;
import com.example.fesc.sigeliFesc.data.entidades.PrestamoEntity;
import com.example.fesc.sigeliFesc.data.entidades.UsuarioEntity;
import com.example.fesc.sigeliFesc.data.repositorios.IMultaRepository;
import com.example.fesc.sigeliFesc.data.repositorios.IPrestamoRepository;
import com.example.fesc.sigeliFesc.data.repositorios.IUsuarioRepository;
import com.example.fesc.sigeliFesc.models.clasesEnum.carrera;
import com.example.fesc.sigeliFesc.models.clasesEnum.estados;
import com.example.fesc.sigeliFesc.shared.MultaDto;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Service
public class MultaService implements IMultaService{

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    IMultaRepository iMultaRepository;

    @Autowired
    IUsuarioRepository iUsuarioRepository;

    @Autowired
    IPrestamoRepository iPrestamoRepository;


    @Override
    public List<MultaDto> verMultas() {

        List<MultaDto> multaDtoList = new ArrayList<>();
        MultaDto multaDto;

        generarMultas();

        for(MultaEntity multaEntity : iMultaRepository.findAll()){

            if(multaEntity.getIdEstado() == 6){

                multaDto = modelMapper.map(multaEntity, MultaDto.class);
                multaDtoList.add(multaDto);

            }

        }

        return multaDtoList;

    }

    @Override
    public String pagarMulta(long id) {

        generarMultas();

        MultaEntity multaEntity = iMultaRepository.findById(id);

        long diasDesde = (long) Math.floor(multaEntity.getPrestamoEntity().getFechaEntrega().getTime() / (1000*60*60*24)); // convertimos a dias, para que no afecten cambios de hora
        long diasHasta = (long) Math.floor(Date.valueOf(LocalDate.now()).getTime() / (1000*60*60*24)); // convertimos a dias, para que no afecten cambios de hora
        long dias = diasHasta - diasDesde;

        int pago = (int) ((3000) * dias);

        multaEntity.setPago(pago);
        multaEntity.setFechaPago(Date.valueOf(LocalDate.now()));
        multaEntity.setIdEstado(5);

        iMultaRepository.save(multaEntity);

        return "Se ha pagado la multa";


    }

    @Override
    public List<MultaDto> buscarDocumento(String documento) {

        List<MultaDto> multaDtoList = new ArrayList<>();
        MultaDto multaDto;

        generarMultas();

        UsuarioEntity usuarioEntity = iUsuarioRepository.findByDocumento(documento);

        List<PrestamoEntity> prestamoEntityList = iPrestamoRepository.findByUsuarioEntity(usuarioEntity);

        for(PrestamoEntity prestamoEntity : prestamoEntityList){

            for(MultaEntity multaEntity : iMultaRepository.findAll()){

                if(multaEntity.getIdEstado() == 6 && prestamoEntity == multaEntity.getPrestamoEntity()){

                    multaDto = modelMapper.map(multaEntity, MultaDto.class);
                    multaDtoList.add(multaDto);

                }

            }

        }



        return multaDtoList;
    }

    @Override
    public MultaDto buscarId(long id) {

        generarMultas();


        MultaEntity multaEntity = iMultaRepository.findById(id);

        MultaDto multaDto = modelMapper.map(multaEntity, MultaDto.class);

        return multaDto;
    }

    @Override
    public List<MultaDto> verPagos() {

        List<MultaDto> multaDtoList = new ArrayList<>();
        MultaDto multaDto;

        for(MultaEntity multaEntity : iMultaRepository.findByIdEstado(5)){

            multaDto = modelMapper.map(multaEntity, MultaDto.class);
            multaDto.setEstado(String.valueOf(estados.pago));
            multaDtoList.add(multaDto);

        }

        return multaDtoList;

    }

    @Override
    public MultaDto verPago(long id) {

        MultaEntity multaEntity = iMultaRepository.findById(id);

        if(multaEntity.getIdEstado() == 5) {

            MultaDto multaDto = modelMapper.map(multaEntity, MultaDto.class);

            return multaDto;
        }
        else{
            throw new RuntimeException("No se ha encontrado el pago");
        }

    }

    @Override
    public List<MultaDto> verPagoPersona(String documento) {

        List<MultaDto> multaDtoList = new ArrayList<>();


        for(MultaEntity multaEntity : iMultaRepository.findAll()){

            if(multaEntity.getPrestamoEntity().getUsuarioEntity().getDocumento().equals(documento) && multaEntity.getIdEstado() == 5){

                MultaDto multaDto = modelMapper.map(multaEntity, MultaDto.class);
                multaDto.setEstado(String.valueOf(estados.pago));
                multaDtoList.add(multaDto);

            }

        }

        return multaDtoList;

    }

    public void enviarCorreo(MultaEntity multaEntity){

        Properties propiedad = new Properties();
        propiedad.setProperty("mail.smtp.host", "smtp.gmail.com");
        propiedad.setProperty("mail.smtp.starttls.enable", "true");
        propiedad.setProperty("mail.smtp.port", "587");
        propiedad.setProperty("mail.smtp.auth", "true");

        Session sesion = Session.getDefaultInstance(propiedad);

        //Correo
        String correoEnvia = "est_ex_avendano@fesc.edu.co";
        //Clave
        String contrasena = "Xavierserrano1!";


        MimeMessage mail = new MimeMessage(sesion);

        try {

            String mensaje = "Cordial saludo " + multaEntity.getPrestamoEntity().getUsuarioEntity().getNombre() + ".\n\n" +
                    "Le informamos que el prestamo número : " + multaEntity.getPrestamoEntity().getId() +
                    ", realizado en la fecha: " + multaEntity.getPrestamoEntity().getFechaPrestamo() + " y debía ser entregado en la fecha: " + multaEntity.getPrestamoEntity().getFechaEntrega() +
                    " ha generado una multa ya que el libro \"" + multaEntity.getPrestamoEntity().getLibroEntity().getNombre() + "\" no fue entregado entre las fechas establecidas \n" +
                    "Si presenta alguna duda sobre la multa o el prestamo realizado, por favor presentarse en la biblioteca de la FESC\n\n" +
                    "Muchas gracias.";

            mail.setFrom(new InternetAddress(correoEnvia));
            mail.addRecipient(Message.RecipientType.TO, new InternetAddress(multaEntity.getPrestamoEntity().getUsuarioEntity().getCorreo()));
            mail.setSubject("Multa del libro " + multaEntity.getPrestamoEntity().getLibroEntity().getNombre());
            mail.setText(mensaje);


            Transport transporte = sesion.getTransport("smtp");
            transporte.connect(correoEnvia,contrasena);
            transporte.sendMessage(mail, mail.getRecipients(Message.RecipientType.TO));


        } catch (AddressException ex) {
            System.out.println(ex.getMessage());
        } catch (MessagingException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public void generarMultas(){

        for(PrestamoEntity prestamoEntity : iPrestamoRepository.findAll()){

            if(prestamoEntity.getFechaEntrega().before(Date.valueOf(LocalDate.now())) && iMultaRepository.findByPrestamoEntity(prestamoEntity) == null && prestamoEntity.getIdEstado() != 2){

                MultaEntity multaEntity = new MultaEntity();

                multaEntity.setPrestamoEntity(prestamoEntity);
                long diasDesde = (long) Math.floor(multaEntity.getPrestamoEntity().getFechaEntrega().getTime() / (1000*60*60*24)); // convertimos a dias, para que no afecten cambios de hora
                long diasHasta = (long) Math.floor(Date.valueOf(LocalDate.now()).getTime() / (1000*60*60*24)); // convertimos a dias, para que no afecten cambios de hora
                long dias = diasHasta - diasDesde;

                int pago = (int) ((3000) * dias);
                multaEntity.setPago(pago);
                multaEntity.setIdEstado(6);

                enviarCorreo(multaEntity);

                iMultaRepository.save(multaEntity);

            }

        }

        for(MultaEntity multaEntity : iMultaRepository.findByIdEstado(6)){
            long diasDesde = (long) Math.floor(multaEntity.getPrestamoEntity().getFechaEntrega().getTime() / (1000*60*60*24)); // convertimos a dias, para que no afecten cambios de hora
            long diasHasta = (long) Math.floor(Date.valueOf(LocalDate.now()).getTime() / (1000*60*60*24)); // convertimos a dias, para que no afecten cambios de hora
            long dias = diasHasta - diasDesde;

            int pago = (int) ((3000) * dias);
            multaEntity.setPago(pago);
            iMultaRepository.save(multaEntity);
        }

    }

    @Override
    public Document generarReporte(int opcion, Date fechaInicio, Date fechaFinal, String observaciones) {

        int carrera0 = 0, carrera1 = 0, carrera2 = 0, carrera3 = 0, carrera4 = 0, carrera5 = 0, carrera6 = 0, carrera7 = 0, cantDinero = 0, cantPagos =0, cantCondenados = 0;

        Font fuenteTitulo = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
        Font fuenteSubTitulo = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);


        if(opcion == 1){
            try {
                Document documentoPdf = new Document();
                ByteArrayOutputStream ficheroDocumento = new ByteArrayOutputStream();



                PdfWriter.getInstance(documentoPdf, ficheroDocumento).setInitialLeading(10);

                documentoPdf.setMargins(30,30, 0, 30);
                documentoPdf.open();


                Image imagen = Image.getInstance("C:\\sigeli\\imagenes\\encabezadoPDF.png");

                documentoPdf.add(imagen);

                documentoPdf.setMargins(30,30, 30, 30);

                documentoPdf.add(new Paragraph("\n"));

                documentoPdf.add(new Paragraph("REPORTE DE PRESTAMOS  desde: " + fechaInicio + " hasta: "+ fechaFinal , fuenteTitulo));
                documentoPdf.add(new Paragraph("\n"));
                if(!observaciones.equals("")){
                    documentoPdf.add(new Paragraph("Observaciones: ", fuenteSubTitulo));
                    documentoPdf.add(new Paragraph(observaciones));
                }
                documentoPdf.add(new Paragraph("\n"));
                documentoPdf.add(new Paragraph("Prestamos" , fuenteSubTitulo));
                documentoPdf.add(new Paragraph("\n"));

                for(PrestamoEntity prestamo : iPrestamoRepository.findAll()){
                    if(prestamo.getFechaPrestamo().after(fechaInicio)  && prestamo.getFechaPrestamo().before(fechaFinal)){
                        for(UsuarioEntity persona : iUsuarioRepository.findAll()){
                            if(prestamo.getUsuarioEntity().getDocumento() == persona.getDocumento()){
                                documentoPdf.add(new Paragraph("Prestamo número: " + prestamo.getId()));
                                documentoPdf.add(new Paragraph("Persona que pidió el prestamo: " + persona.getNombre() + "\nDocumento: " + prestamo.getUsuarioEntity().getDocumento()));
                                if(persona.getIdCarrera() != 0){
                                    documentoPdf.add(new Paragraph("Programa al que pertenece: " + carrera.ordinal(persona.getIdCarrera())));
                                }
                                else{
                                    documentoPdf.add(new Paragraph("Administrador"));
                                }
                                documentoPdf.add(new Paragraph("ISBN del libro prestado: " + prestamo.getLibroEntity().getIsbn()));
                                documentoPdf.add(new Paragraph("Fecha del prestamo: " + prestamo.getFechaPrestamo() + "Fecha de entrega: " + prestamo.getFechaEntrega()));
                                documentoPdf.add(new Paragraph("Estado del prestamo: " + ((prestamo.getIdEstado() == 3)? "En deuda":"Entregado")));
                                documentoPdf.add(new Paragraph("\n"));
                                documentoPdf.add(new Paragraph("\n"));

                                if(persona.getIdCarrera() == 0){
                                    carrera0 += 1;
                                }
                                else if(persona.getIdCarrera() == 1){
                                    carrera1 += 1;
                                }
                                else if(persona.getIdCarrera() == 2){
                                    carrera2 += 1;
                                }
                                else if(persona.getIdCarrera() == 3){
                                    carrera3 += 1;
                                }
                                else if(persona.getIdCarrera() == 4){
                                    carrera4 += 1;
                                }
                                else if(persona.getIdCarrera() == 5){
                                    carrera5 += 1;
                                }
                                else if(persona.getIdCarrera() == 6){
                                    carrera6 += 1;
                                }
                                else if(persona.getIdCarrera() == 7){
                                    carrera7 += 1;
                                }

                            }
                        }
                    }
                }


                PdfPTable tabla = new PdfPTable(2);


                documentoPdf.add(new Paragraph("\n"));
                documentoPdf.add(new Paragraph("\n"));
                documentoPdf.add(new Paragraph("Estadisticas por carrera - Prestamos" , fuenteSubTitulo));
                documentoPdf.add(new Paragraph("\n"));

                documentoPdf.setMargins(30,30, 0, 30);

                //Cabecera
                tabla.addCell("Cargo/carrera");
                tabla.addCell("Cantidad de prestamos");
                //Carrera 0
                tabla.addCell("Administradores");
                tabla.addCell("" + carrera0);
                //Carrera 1
                tabla.addCell("Ingeniería de software");
                tabla.addCell("" + carrera1);
                //Carrera 2
                tabla.addCell("Diseño gráfico");
                tabla.addCell("" + carrera2);
                //Carrera 3
                tabla.addCell("Diseño de modas");
                tabla.addCell("" + carrera3);
                //Carrera 4
                tabla.addCell("Economía y finanzas");
                tabla.addCell("" + carrera4);
                //Carrera 5
                tabla.addCell("Comercio internacional");
                tabla.addCell("" + carrera5);
                //Carrera 6
                tabla.addCell("Hotelería y turísmo");
                tabla.addCell("" + carrera6);
                //Carrera 7
                tabla.addCell("Logística empresarial");
                tabla.addCell("" + carrera7);

                //Agregamos la tabla al pdf
                documentoPdf.add(tabla);

                documentoPdf.add(new Paragraph("\n"));
                documentoPdf.add(new Paragraph("Gráfica de los prestamos" , fuenteSubTitulo));
                documentoPdf.add(new Paragraph("\n"));

                //Creacion de las graficas

                //Graficas de pastel

                DefaultPieDataset graficaPastel = new DefaultPieDataset();

                int promPrestamos = 0;

                try {
                    promPrestamos = 100/(carrera0 +carrera1 + carrera2 + carrera3 + carrera4 + carrera5 + carrera6 + carrera7);
                } catch (Exception e) {
                }

                if(promPrestamos != 0){
                    graficaPastel.setValue("Administradores", Double.valueOf((promPrestamos*carrera0)));
                    graficaPastel.setValue("Ingeniería de software", Double.valueOf(promPrestamos*carrera1));
                    graficaPastel.setValue("Diseño gráfico", Double.valueOf(promPrestamos*carrera2));
                    graficaPastel.setValue("Diseño de modas", Double.valueOf(promPrestamos*carrera3));
                    graficaPastel.setValue("Economía y finanzas", Double.valueOf(promPrestamos*carrera4));
                    graficaPastel.setValue("Comercio internacional", Double.valueOf(promPrestamos*carrera5));
                    graficaPastel.setValue("Hotelería y turísmo", Double.valueOf(promPrestamos*carrera6));
                    graficaPastel.setValue("Logística empresarial", Double.valueOf(promPrestamos*carrera7));

                    JFreeChart graficoCahrt = ChartFactory.createPieChart3D("Prestamos", graficaPastel, true, true, false);

                    ChartUtilities.saveChartAsJPEG(new File("C:\\sigeli\\graficos\\graficoPastel.png"), graficoCahrt, 500, 500);

                    Image image = Image.getInstance("C:\\sigeli\\graficos\\graficoPastel.png");
                    documentoPdf.add(image);

                    //Graficas de barras

                    DefaultCategoryDataset diagramaBarras = new DefaultCategoryDataset();

                    diagramaBarras.setValue(carrera0, "Administrador", "Administrador");
                    diagramaBarras.setValue(carrera1, "Ingeniería de software", "Ingeniería de software");
                    diagramaBarras.setValue(carrera2, "Diseño gráfico", "Diseño gráfico");
                    diagramaBarras.setValue(carrera3, "Diseño de modas", "Diseño de modas");
                    diagramaBarras.setValue(carrera4, "Economía y finanzas", "Economía y finanzas");
                    diagramaBarras.setValue(carrera5, "Comercio internacional", "Comercio internacional");
                    diagramaBarras.setValue(carrera6, "Hotelería y turísmo", "Hotelería y turísmo");
                    diagramaBarras.setValue(carrera7, "Logística empresarial", "Logística empresarial");

                    JFreeChart graficaBarraChart = ChartFactory.createBarChart3D("Prestamos","Carrera", "Cantidad", diagramaBarras, PlotOrientation.VERTICAL, true,true, false);

                    ChartUtilities.saveChartAsJPEG(new File("C:\\sigeli\\graficos\\graficoBarra.png"), graficaBarraChart, 500, 500);

                    Image imagenGraf = Image.getInstance("C:\\sigeli\\graficos\\graficoBarra.png");
                    documentoPdf.add(imagenGraf);
                }
                else{
                    documentoPdf.add(new Paragraph("No existe ningún registro"));
                }

                documentoPdf.close();

                return documentoPdf;

            } catch (Exception e) {

                throw new RuntimeException("No se ha podido crear el documento " + e.getMessage());
            }
        }
        else if(opcion == 2){
            Document documentoPdf = new Document();
            try {
                ByteArrayOutputStream ficheroDocumento = new ByteArrayOutputStream();
                PdfWriter.getInstance(documentoPdf, ficheroDocumento).setInitialLeading(10);

                documentoPdf.setMargins(30,30, 0, 30);
                documentoPdf.open();

                Image imagen = Image.getInstance("C:\\sigeli\\imagenes\\encabezadoPDF.png");

                documentoPdf.add(imagen);

                documentoPdf.setMargins(30,30, 30, 30);

                documentoPdf.add(new Paragraph("\n"));
                documentoPdf.add(new Paragraph("REPORTE DE MULTAS  desde: " + fechaInicio + " hasta: "+ fechaFinal , fuenteTitulo));
                documentoPdf.add(new Paragraph("\n"));
                if(!observaciones.equals("")){
                    documentoPdf.add(new Paragraph("Observaciones: ", fuenteSubTitulo));
                    documentoPdf.add(new Paragraph(observaciones));
                }
                documentoPdf.add(new Paragraph("\n"));
                documentoPdf.add(new Paragraph("Multas" , fuenteSubTitulo));
                documentoPdf.add(new Paragraph("\n"));


                for(MultaEntity multa : iMultaRepository.findAll()){
                    for(PrestamoEntity prestamo : iPrestamoRepository.findAll()){
                        if(multa.getIdEstado() == 6 && prestamo.getFechaEntrega().after(fechaInicio) && prestamo.getFechaEntrega().before(fechaFinal)){
                            if(prestamo == multa.getPrestamoEntity()){
                                for(UsuarioEntity persona : iUsuarioRepository.findAll()){
                                    if(prestamo.getUsuarioEntity().getDocumento() == persona.getDocumento()){
                                        documentoPdf.add(new Paragraph("Multa numero: " + multa.getId() + "\nID del prestamo multado : " + multa.getPrestamoEntity().getId()));
                                        documentoPdf.add(new Paragraph("Persona que pidió el prestamo: " + persona.getNombre() + "\nDocumento: " + persona.getDocumento()));
                                        if(persona.getIdCarrera() != 0){
                                            documentoPdf.add(new Paragraph("Programa al que pertenece: " + carrera.ordinal(persona.getIdCarrera())));
                                        }
                                        else{
                                            documentoPdf.add(new Paragraph("Administrador"));
                                        }
                                        documentoPdf.add(new Paragraph("Fecha del prestamo: " + prestamo.getFechaPrestamo() + "\nFecha de entrega: " + prestamo.getFechaEntrega()));
                                        documentoPdf.add(new Paragraph("Fecha en la que se creó la multa: " + prestamo.getFechaEntrega()));
                                        documentoPdf.add(new Paragraph("ISBN del libro multado: " + prestamo.getLibroEntity().getIsbn()));
                                        documentoPdf.add(new Paragraph("Valor de la multa: " + multa.getPago()));
                                        documentoPdf.add(new Paragraph("\n"));
                                        documentoPdf.add(new Paragraph("\n"));

                                        if(persona.getIdCarrera() == 0){
                                            carrera0 += 1;
                                        }
                                        else if(persona.getIdCarrera() == 1){
                                            carrera1 += 1;
                                        }
                                        else if(persona.getIdCarrera() == 2){
                                            carrera2 += 1;
                                        }
                                        else if(persona.getIdCarrera() == 3){
                                            carrera3 += 1;
                                        }
                                        else if(persona.getIdCarrera() == 4){
                                            carrera4 += 1;
                                        }
                                        else if(persona.getIdCarrera() == 5){
                                            carrera5 += 1;
                                        }
                                        else if(persona.getIdCarrera() == 6){
                                            carrera6 += 1;
                                        }
                                        else if(persona.getIdCarrera() == 7){
                                            carrera7 += 1;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                PdfPTable tabla = new PdfPTable(2);

                documentoPdf.add(new Paragraph("\n"));
                documentoPdf.add(new Paragraph("Estadisticas por carrera - Multas" , fuenteSubTitulo));
                documentoPdf.add(new Paragraph("\n"));

                //Cabecera
                tabla.addCell("Cargo/carrera");
                tabla.addCell("Cantidad de multas");
                //Carrera 0
                tabla.addCell("Administradores");
                tabla.addCell("" + carrera0);
                //Carrera 1
                tabla.addCell("Ingeniería de software");
                tabla.addCell("" + carrera1);
                //Carrera 2
                tabla.addCell("Diseño gráfico");
                tabla.addCell("" + carrera2);
                //Carrera 3
                tabla.addCell("Diseño de modas");
                tabla.addCell("" + carrera3);
                //Carrera 4
                tabla.addCell("Economía y finanzas");
                tabla.addCell("" + carrera4);
                //Carrera 5
                tabla.addCell("Comercio internacional");
                tabla.addCell("" + carrera5);
                //Carrera 6
                tabla.addCell("Hotelería y turísmo");
                tabla.addCell("" + carrera6);
                //Carrera 7
                tabla.addCell("Logística empresarial");
                tabla.addCell("" + carrera7);

                documentoPdf.add(tabla);

                documentoPdf.add(new Paragraph("\n"));
                documentoPdf.add(new Paragraph("Gráfica de las multas" , fuenteSubTitulo));
                documentoPdf.add(new Paragraph("\n"));

                documentoPdf.setMargins(30,30, 0, 30);
                //Creacion de las graficas

                //Graficas de pastel

                DefaultPieDataset graficaPastel = new DefaultPieDataset();

                int promMultas = 0;

                try {
                    promMultas = 100/(carrera0 +carrera1 + carrera2 + carrera3 + carrera4 + carrera5 + carrera6 + carrera7);
                } catch (Exception e) {
                }

                if(promMultas != 0){
                    graficaPastel.setValue("Administradores", Double.valueOf((promMultas*carrera0)));
                    graficaPastel.setValue("Ingeniería de software", Double.valueOf(promMultas*carrera1));
                    graficaPastel.setValue("Diseño gráfico", Double.valueOf(promMultas*carrera2));
                    graficaPastel.setValue("Diseño de modas", Double.valueOf(promMultas*carrera3));
                    graficaPastel.setValue("Economía y finanzas", Double.valueOf(promMultas*carrera4));
                    graficaPastel.setValue("Comercio internacional", Double.valueOf(promMultas*carrera5));
                    graficaPastel.setValue("Hotelería y turísmo", Double.valueOf(promMultas*carrera6));
                    graficaPastel.setValue("Logística empresarial", Double.valueOf(promMultas*carrera7));

                    JFreeChart graficoCahrt = ChartFactory.createPieChart3D("Multas", graficaPastel, true, true, false);

                    ChartUtilities.saveChartAsJPEG(new File("C:\\sigeli\\graficos\\graficoPastelMultas.png"), graficoCahrt, 500, 500);

                    Image image = Image.getInstance("C:\\sigeli\\graficos\\graficoPastelMultas.png");
                    documentoPdf.add(image);

                    //Graficas de barras

                    DefaultCategoryDataset diagramaBarras = new DefaultCategoryDataset();

                    diagramaBarras.setValue(carrera0, "Administrador", "Administrador");
                    diagramaBarras.setValue(carrera1, "Ingeniería de software", "Ingeniería de software");
                    diagramaBarras.setValue(carrera2, "Diseño gráfico", "Diseño gráfico");
                    diagramaBarras.setValue(carrera3, "Diseño de modas", "Diseño de modas");
                    diagramaBarras.setValue(carrera4, "Economía y finanzas", "Economía y finanzas");
                    diagramaBarras.setValue(carrera5, "Comercio internacional", "Comercio internacional");
                    diagramaBarras.setValue(carrera6, "Hotelería y turísmo", "Hotelería y turísmo");
                    diagramaBarras.setValue(carrera7, "Logística empresarial", "Logística empresarial");

                    JFreeChart graficaBarraChart = ChartFactory.createBarChart3D("Multas","Carrera", "Cantidad", diagramaBarras, PlotOrientation.VERTICAL, true,true, false);

                    ChartUtilities.saveChartAsJPEG(new File("C:\\sigeli\\graficos\\graficoBarraMultas.png"), graficaBarraChart, 500, 500);

                    Image imagenGraf = Image.getInstance("C:\\sigeli\\graficos\\graficoBarraMultas.png");
                    documentoPdf.add(imagenGraf);
                }
                else{
                    documentoPdf.add(new Paragraph("No existe ningún registro"));
                }


                documentoPdf.close();

                return documentoPdf;

            } catch (Exception e) {

                throw new RuntimeException("No se ha podido crear el documento " + e.getMessage());
            }


        }
        else if(opcion == 3){
            Document documentoPdf = new Document();
            try {
                ByteArrayOutputStream ficheroDocumento = new ByteArrayOutputStream();
                PdfWriter.getInstance(documentoPdf, ficheroDocumento).setInitialLeading(10);

                documentoPdf.setMargins(30,30, 0, 30);
                documentoPdf.open();

                Image imagen = Image.getInstance("C:\\sigeli\\imagenes\\encabezadoPDF.png");

                documentoPdf.add(imagen);

                documentoPdf.setMargins(30,30, 30, 30);

                documentoPdf.add(new Paragraph("\n"));
                documentoPdf.add(new Paragraph("REPORTE DE PAGOS  desde: " + fechaInicio + " hasta: "+ fechaFinal , fuenteTitulo));
                documentoPdf.add(new Paragraph("\n"));
                if(!observaciones.equals("")){
                    documentoPdf.add(new Paragraph("Observaciones: ", fuenteSubTitulo));
                    documentoPdf.add(new Paragraph(observaciones));
                }
                documentoPdf.add(new Paragraph("\n"));
                documentoPdf.add(new Paragraph("Pagos" , fuenteSubTitulo));
                documentoPdf.add(new Paragraph("\n"));

                for(MultaEntity multa : iMultaRepository.findAll()){
                    for(PrestamoEntity prestamo : iPrestamoRepository.findAll()){
                        if(multa.getIdEstado() == 5 || multa.getIdEstado() == 7 && prestamo.getFechaEntrega().after(fechaInicio) && prestamo.getFechaEntrega().before(fechaFinal)){
                            if(prestamo == multa.getPrestamoEntity()){
                                for(UsuarioEntity persona : iUsuarioRepository.findAll()){
                                    if(prestamo.getUsuarioEntity().getDocumento() == persona.getDocumento()){
                                        documentoPdf.add(new Paragraph("Pago de la multa: " + multa.getId() + "\nID del prestamo multado : " + multa.getPrestamoEntity().getId()));
                                        documentoPdf.add(new Paragraph("Persona que pagó la multa: " + persona.getNombre() + "\nDocumento: " + persona.getDocumento()));
                                        if(persona.getIdCarrera() != 0){
                                            documentoPdf.add(new Paragraph("Programa al que pertenece: " + carrera.ordinal(persona.getIdCarrera())));
                                        }
                                        else{
                                            documentoPdf.add(new Paragraph("Administrador"));
                                        }
                                        documentoPdf.add(new Paragraph("Fecha del prestamo: " + prestamo.getFechaPrestamo() + "\nFecha de entrega: " + prestamo.getFechaEntrega()));
                                        documentoPdf.add(new Paragraph("Fecha en la que se Pagó la multa: " + multa.getFechaPago()));
                                        documentoPdf.add(new Paragraph("ISBN del libro multado: " + prestamo.getLibroEntity().getIsbn()));
                                        documentoPdf.add(new Paragraph("Valor pagado: " + multa.getPago()));
                                        documentoPdf.add(new Paragraph("\n"));
                                        documentoPdf.add(new Paragraph("\n"));

                                        if(multa.getIdEstado() == 5){
                                            cantPagos += 1;
                                            cantDinero += multa.getPago();
                                        }
                                        else if(multa.getIdEstado() == 7){
                                            cantCondenados += 1;
                                        }


                                        if(persona.getIdCarrera() == 0){
                                            carrera0 += 1;
                                        }
                                        else if(persona.getIdCarrera() == 1){
                                            carrera1 += 1;
                                        }
                                        else if(persona.getIdCarrera() == 2){
                                            carrera2 += 1;
                                        }
                                        else if(persona.getIdCarrera() == 3){
                                            carrera3 += 1;
                                        }
                                        else if(persona.getIdCarrera() == 4){
                                            carrera4 += 1;
                                        }
                                        else if(persona.getIdCarrera() == 5){
                                            carrera5 += 1;
                                        }
                                        else if(persona.getIdCarrera() == 6){
                                            carrera6 += 1;
                                        }
                                        else if(persona.getIdCarrera() == 7){
                                            carrera7 += 1;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                PdfPTable tabla = new PdfPTable(2);

                documentoPdf.add(new Paragraph("\n"));
                documentoPdf.add(new Paragraph("Estadisticas por carrera - Pagos" , fuenteSubTitulo));
                documentoPdf.add(new Paragraph("\n"));

                //Cabecera
                tabla.addCell("Cargo/carrera");
                tabla.addCell("Cantidad de Pagos");
                //Carrera 0
                tabla.addCell("Administradores");
                tabla.addCell("" + carrera0);
                //Carrera 1
                tabla.addCell("Ingeniería de software");
                tabla.addCell("" + carrera1);
                //Carrera 2
                tabla.addCell("Diseño gráfico");
                tabla.addCell("" + carrera2);
                //Carrera 3
                tabla.addCell("Diseño de modas");
                tabla.addCell("" + carrera3);
                //Carrera 4
                tabla.addCell("Economía y finanzas");
                tabla.addCell("" + carrera4);
                //Carrera 5
                tabla.addCell("Comercio internacional");
                tabla.addCell("" + carrera5);
                //Carrera 6
                tabla.addCell("Hotelería y turísmo");
                tabla.addCell("" + carrera6);
                //Carrera 7
                tabla.addCell("Logística empresarial");
                tabla.addCell("" + carrera7);

                documentoPdf.add(tabla);

                PdfPTable reciboPago = new PdfPTable(2);

                documentoPdf.add(new Paragraph("\n"));
                documentoPdf.add(new Paragraph("Pagos" , fuenteSubTitulo));
                documentoPdf.add(new Paragraph("\n"));

                reciboPago.addCell("Cantidad de pagos");
                reciboPago.addCell("Dinero en caja");

                reciboPago.addCell("Condenados");
                reciboPago.addCell("" + cantCondenados);

                reciboPago.addCell("pagos");
                reciboPago.addCell("" + cantPagos);

                reciboPago.addCell("Total");
                reciboPago.addCell("" + cantDinero);

                documentoPdf.add(reciboPago);

                //Creacion de las graficas

                //Graficas de pastel

                documentoPdf.setMargins(30,30, 0, 30);

                DefaultPieDataset graficaPastel = new DefaultPieDataset();

                int promPagos = 0;

                try {
                    promPagos = 100/(carrera0 +carrera1 + carrera2 + carrera3 + carrera4 + carrera5 + carrera6 + carrera7);
                } catch (Exception e) {
                }


                if(promPagos != 0){
                    graficaPastel.setValue("Administradores", Double.valueOf((promPagos*carrera0)));
                    graficaPastel.setValue("Ingeniería de software", Double.valueOf(promPagos*carrera1));
                    graficaPastel.setValue("Diseño gráfico", Double.valueOf(promPagos*carrera2));
                    graficaPastel.setValue("Diseño de modas", Double.valueOf(promPagos*carrera3));
                    graficaPastel.setValue("Economía y finanzas", Double.valueOf(promPagos*carrera4));
                    graficaPastel.setValue("Comercio internacional", Double.valueOf(promPagos*carrera5));
                    graficaPastel.setValue("Hotelería y turísmo", Double.valueOf(promPagos*carrera6));
                    graficaPastel.setValue("Logística empresarial", Double.valueOf(promPagos*carrera7));

                    JFreeChart graficoCahrt = ChartFactory.createPieChart3D("Multas", graficaPastel, true, true, false);

                    ChartUtilities.saveChartAsJPEG(new File("C:\\sigeli\\graficos\\graficoPastelPagos.png"), graficoCahrt, 500, 500);

                    Image image = Image.getInstance("C:\\sigeli\\graficos\\graficoPastelPagos.png");
                    documentoPdf.add(image);

                    //Graficas de barras

                    DefaultCategoryDataset diagramaBarras = new DefaultCategoryDataset();

                    diagramaBarras.setValue(carrera0, "Administrador", "Administrador");
                    diagramaBarras.setValue(carrera1, "Ingeniería de software", "Ingeniería de software");
                    diagramaBarras.setValue(carrera2, "Diseño gráfico", "Diseño gráfico");
                    diagramaBarras.setValue(carrera3, "Diseño de modas", "Diseño de modas");
                    diagramaBarras.setValue(carrera4, "Economía y finanzas", "Economía y finanzas");
                    diagramaBarras.setValue(carrera5, "Comercio internacional", "Comercio internacional");
                    diagramaBarras.setValue(carrera6, "Hotelería y turísmo", "Hotelería y turísmo");
                    diagramaBarras.setValue(carrera7, "Logística empresarial", "Logística empresarial");

                    JFreeChart graficaBarraChart = ChartFactory.createBarChart3D("Multas","Carrera", "Cantidad", diagramaBarras, PlotOrientation.VERTICAL, true,true, false);

                    ChartUtilities.saveChartAsJPEG(new File("C:\\sigeli\\graficos\\graficoBarraPagos.png"), graficaBarraChart, 500, 500);

                    Image imagenGraf = Image.getInstance("C:\\sigeli\\graficos\\graficoBarraPagos.png");
                    documentoPdf.add(imagenGraf);
                }
                else{
                    documentoPdf.add(new Paragraph("No existe ningún registro"));
                }

                documentoPdf.close();

                return documentoPdf;


            } catch (Exception e) {

                throw new RuntimeException("No se ha podido crear el documento " + e.getMessage());


            }
        }
        else if(opcion == 4){
            Document documentoPdf = new Document();
            Boolean prestamosDoc = false, multasDoc = false, pagosDoc = false;
            try {
                ByteArrayOutputStream ficheroDocumento = new ByteArrayOutputStream();
                PdfWriter.getInstance(documentoPdf, ficheroDocumento).setInitialLeading(10);

                documentoPdf.setMargins(30,30, 0, 30);
                documentoPdf.open();

                Image imagen = Image.getInstance("C:\\sigeli\\imagenes\\encabezadoPDF.png");

                documentoPdf.add(imagen);

                documentoPdf.setMargins(30,30, 30, 30);

                documentoPdf.add(new Paragraph("\n"));
                documentoPdf.add(new Paragraph("REPORTE TOTAL  desde: " + fechaInicio + " hasta: "+ fechaFinal , fuenteTitulo));
                documentoPdf.add(new Paragraph("\n"));
                if(!observaciones.equals("")){
                    documentoPdf.add(new Paragraph("Observaciones: ", fuenteSubTitulo));
                    documentoPdf.add(new Paragraph(observaciones));
                }
                documentoPdf.add(new Paragraph("\n"));

                documentoPdf.add(new Paragraph("Prestamos" , fuenteSubTitulo));
                documentoPdf.add(new Paragraph("\n"));


                for(PrestamoEntity prestamo : iPrestamoRepository.findAll()){
                    if(prestamo.getFechaPrestamo().after(fechaInicio)  && prestamo.getFechaPrestamo().before(fechaFinal)){
                        for(UsuarioEntity persona : iUsuarioRepository.findAll()){
                            if(prestamo.getUsuarioEntity().getDocumento() == persona.getDocumento()){
                                documentoPdf.add(new Paragraph("Prestamo número: " + prestamo.getId()));
                                documentoPdf.add(new Paragraph("Persona que pidió el prestamo: " + persona.getNombre() + "\nDocumento: " + prestamo.getUsuarioEntity().getDocumento()));
                                if(persona.getIdCarrera() != 0){
                                    documentoPdf.add(new Paragraph("Programa al que pertenece: " + carrera.ordinal(persona.getIdCarrera())));
                                }
                                else{
                                    documentoPdf.add(new Paragraph("Administrador"));
                                }
                                documentoPdf.add(new Paragraph("ISBN del libro prestado: " + prestamo.getLibroEntity().getIsbn()));
                                documentoPdf.add(new Paragraph("Fecha del prestamo: " + prestamo.getFechaPrestamo() + "Fecha de entrega: " + prestamo.getFechaEntrega()));
                                documentoPdf.add(new Paragraph("Estado del prestamo: " + ((prestamo.getIdEstado() == 3)? "En deuda":"Entregado")));
                                documentoPdf.add(new Paragraph("\n"));
                                documentoPdf.add(new Paragraph("\n"));
                                if(persona.getIdCarrera() == 0){
                                    carrera0 += 1;
                                }
                                else if(persona.getIdCarrera() == 1){
                                    carrera1 += 1;
                                }
                                else if(persona.getIdCarrera() == 2){
                                    carrera2 += 1;
                                }
                                else if(persona.getIdCarrera() == 3){
                                    carrera3 += 1;
                                }
                                else if(persona.getIdCarrera() == 4){
                                    carrera4 += 1;
                                }
                                else if(persona.getIdCarrera() == 5){
                                    carrera5 += 1;
                                }
                                else if(persona.getIdCarrera() == 6){
                                    carrera6 += 1;
                                }
                                else if(persona.getIdCarrera() == 7){
                                    carrera7 += 1;
                                }

                            }
                        }
                    }
                }



                //Creacion de las graficas

                //Graficas de pastel

                DefaultPieDataset graficaPastel = new DefaultPieDataset();

                int promPrestamos = 0;

                try {
                    promPrestamos = 100/(carrera0 +carrera1 + carrera2 + carrera3 + carrera4 + carrera5 + carrera6 + carrera7);
                } catch (Exception e) {
                }

                if(promPrestamos != 0){

                    prestamosDoc = true;
                    PdfPTable tabla = new PdfPTable(2);

                    documentoPdf.add(new Paragraph("\n"));
                    documentoPdf.add(new Paragraph("Estadisticas por carrera - Prestamos" , fuenteSubTitulo));
                    documentoPdf.add(new Paragraph("\n"));

                    //Cabecera
                    tabla.addCell("Cargo/carrera");
                    tabla.addCell("Cantidad de prestamos");
                    //Carrera 0
                    tabla.addCell("Administradores");
                    tabla.addCell("" + carrera0);
                    //Carrera 1
                    tabla.addCell("Ingeniería de software");
                    tabla.addCell("" + carrera1);
                    //Carrera 2
                    tabla.addCell("Diseño gráfico");
                    tabla.addCell("" + carrera2);
                    //Carrera 3
                    tabla.addCell("Diseño de modas");
                    tabla.addCell("" + carrera3);
                    //Carrera 4
                    tabla.addCell("Economía y finanzas");
                    tabla.addCell("" + carrera4);
                    //Carrera 5
                    tabla.addCell("Comercio internacional");
                    tabla.addCell("" + carrera5);
                    //Carrera 6
                    tabla.addCell("Hotelería y turísmo");
                    tabla.addCell("" + carrera6);
                    //Carrera 7
                    tabla.addCell("Logística empresarial");
                    tabla.addCell("" + carrera7);

                    documentoPdf.add(tabla);


                    graficaPastel.setValue("Administradores", Double.valueOf((promPrestamos*carrera0)));
                    graficaPastel.setValue("Ingeniería de software", Double.valueOf(promPrestamos*carrera1));
                    graficaPastel.setValue("Diseño gráfico", Double.valueOf(promPrestamos*carrera2));
                    graficaPastel.setValue("Diseño de modas", Double.valueOf(promPrestamos*carrera3));
                    graficaPastel.setValue("Economía y finanzas", Double.valueOf(promPrestamos*carrera4));
                    graficaPastel.setValue("Comercio internacional", Double.valueOf(promPrestamos*carrera5));
                    graficaPastel.setValue("Hotelería y turísmo", Double.valueOf(promPrestamos*carrera6));
                    graficaPastel.setValue("Logística empresarial", Double.valueOf(promPrestamos*carrera7));

                    JFreeChart graficoCahrt = ChartFactory.createPieChart3D("Prestamos", graficaPastel, true, true, false);

                    ChartUtilities.saveChartAsJPEG(new File("C:\\sigeli\\graficos\\graficoPastel.png"), graficoCahrt, 400, 400);



                    //Graficas de barras

                    DefaultCategoryDataset diagramaBarras = new DefaultCategoryDataset();

                    diagramaBarras.setValue(carrera0, "Administrador", "Administrador");
                    diagramaBarras.setValue(carrera1, "Ingeniería de software", "Ingeniería de software");
                    diagramaBarras.setValue(carrera2, "Diseño gráfico", "Diseño gráfico");
                    diagramaBarras.setValue(carrera3, "Diseño de modas", "Diseño de modas");
                    diagramaBarras.setValue(carrera4, "Economía y finanzas", "Economía y finanzas");
                    diagramaBarras.setValue(carrera5, "Comercio internacional", "Comercio internacional");
                    diagramaBarras.setValue(carrera6, "Hotelería y turísmo", "Hotelería y turísmo");
                    diagramaBarras.setValue(carrera7, "Logística empresarial", "Logística empresarial");

                    JFreeChart graficaBarraChart = ChartFactory.createBarChart3D("Prestamos","Carrera", "Cantidad", diagramaBarras, PlotOrientation.VERTICAL, true,true, false);

                    ChartUtilities.saveChartAsJPEG(new File("C:\\sigeli\\graficos\\graficoBarra.png"), graficaBarraChart, 400, 400);


                }
                else{
                    documentoPdf.add(new Paragraph("No existe ningún registro"));
                }

                documentoPdf.add(new Paragraph("\n"));
                //Multas

                carrera0 = 0;
                carrera1 = 0;
                carrera2 = 0;
                carrera3 = 0;
                carrera4 = 0;
                carrera5 = 0;
                carrera6 = 0;
                carrera7 = 0;
                documentoPdf.add(new Paragraph("\n"));
                documentoPdf.add(new Paragraph("Multas" , fuenteSubTitulo));
                documentoPdf.add(new Paragraph("\n"));


                for(MultaEntity multa : iMultaRepository.findAll()){
                    for(PrestamoEntity prestamo : iPrestamoRepository.findAll()){
                        if(multa.getIdEstado() == 6 && prestamo.getFechaEntrega().after(fechaInicio) && prestamo.getFechaEntrega().before(fechaFinal)){
                            if(prestamo == multa.getPrestamoEntity()){
                                for(UsuarioEntity persona : iUsuarioRepository.findAll()){
                                    if(prestamo.getUsuarioEntity().getDocumento() == persona.getDocumento()){
                                        documentoPdf.add(new Paragraph("Multa numero: " + multa.getId() + "\nID del prestamo multado : " + multa.getPrestamoEntity().getId()));
                                        documentoPdf.add(new Paragraph("Persona que pidió el prestamo: " + persona.getNombre() + "\nDocumento: " + persona.getDocumento()));
                                        if(persona.getIdCarrera() != 0){
                                            documentoPdf.add(new Paragraph("Programa al que pertenece: " + carrera.ordinal(persona.getIdCarrera())));
                                        }
                                        else{
                                            documentoPdf.add(new Paragraph("Administrador"));
                                        }
                                        documentoPdf.add(new Paragraph("Fecha del prestamo: " + prestamo.getFechaPrestamo() + "\nFecha de entrega: " + prestamo.getFechaEntrega()));
                                        documentoPdf.add(new Paragraph("Fecha en la que se creó la multa: " + prestamo.getFechaEntrega()));
                                        documentoPdf.add(new Paragraph("ISBN del libro multado: " + prestamo.getLibroEntity().getIsbn()));
                                        documentoPdf.add(new Paragraph("Valor de la multa: " + multa.getPago()));
                                        documentoPdf.add(new Paragraph("\n"));
                                        documentoPdf.add(new Paragraph("\n"));
                                        if(persona.getIdCarrera() == 0){
                                            carrera0 += 1;
                                        }
                                        else if(persona.getIdCarrera() == 1){
                                            carrera1 += 1;
                                        }
                                        else if(persona.getIdCarrera() == 2){
                                            carrera2 += 1;
                                        }
                                        else if(persona.getIdCarrera() == 3){
                                            carrera3 += 1;
                                        }
                                        else if(persona.getIdCarrera() == 4){
                                            carrera4 += 1;
                                        }
                                        else if(persona.getIdCarrera() == 5){
                                            carrera5 += 1;
                                        }
                                        else if(persona.getIdCarrera() == 6){
                                            carrera6 += 1;
                                        }
                                        else if(persona.getIdCarrera() == 7){
                                            carrera7 += 1;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                DefaultPieDataset graficaPastelMultas = new DefaultPieDataset();

                int promMultas = 0;

                try {
                    promMultas = 100/(carrera0 +carrera1 + carrera2 + carrera3 + carrera4 + carrera5 + carrera6 + carrera7);
                } catch (Exception e) {
                }

                if(promMultas != 0){

                    multasDoc = true;
                    PdfPTable tabla2 = new PdfPTable(2);

                    documentoPdf.add(new Paragraph("\n"));
                    documentoPdf.add(new Paragraph("Estadisticas por carrera - Multas" , fuenteSubTitulo));
                    documentoPdf.add(new Paragraph("\n"));

                    //Cabecera
                    tabla2.addCell("Cargo/carrera");
                    tabla2.addCell("Cantidad de multas");
                    //Carrera 0
                    tabla2.addCell("Administradores");
                    tabla2.addCell("" + carrera0);
                    //Carrera 1
                    tabla2.addCell("Ingeniería de software");
                    tabla2.addCell("" + carrera1);
                    //Carrera 2
                    tabla2.addCell("Diseño gráfico");
                    tabla2.addCell("" + carrera2);
                    //Carrera 3
                    tabla2.addCell("Diseño de modas");
                    tabla2.addCell("" + carrera3);
                    //Carrera 4
                    tabla2.addCell("Economía y finanzas");
                    tabla2.addCell("" + carrera4);
                    //Carrera 5
                    tabla2.addCell("Comercio internacional");
                    tabla2.addCell("" + carrera5);
                    //Carrera 6
                    tabla2.addCell("Hotelería y turísmo");
                    tabla2.addCell("" + carrera6);
                    //Carrera 7
                    tabla2.addCell("Logística empresarial");
                    tabla2.addCell("" + carrera7);

                    documentoPdf.add(tabla2);


                    graficaPastelMultas.setValue("Administradores", Double.valueOf((promMultas*carrera0)));
                    graficaPastelMultas.setValue("Ingeniería de software", Double.valueOf(promMultas*carrera1));
                    graficaPastelMultas.setValue("Diseño gráfico", Double.valueOf(promMultas*carrera2));
                    graficaPastelMultas.setValue("Diseño de modas", Double.valueOf(promMultas*carrera3));
                    graficaPastelMultas.setValue("Economía y finanzas", Double.valueOf(promMultas*carrera4));
                    graficaPastelMultas.setValue("Comercio internacional", Double.valueOf(promMultas*carrera5));
                    graficaPastelMultas.setValue("Hotelería y turísmo", Double.valueOf(promMultas*carrera6));
                    graficaPastelMultas.setValue("Logística empresarial", Double.valueOf(promMultas*carrera7));

                    JFreeChart graficoCahrtMultas = ChartFactory.createPieChart3D("Multas", graficaPastelMultas, true, true, false);

                    ChartUtilities.saveChartAsJPEG(new File("C:\\sigeli\\graficos\\graficoPastelMultas.png"), graficoCahrtMultas, 400, 400);



                    //Graficas de barras

                    DefaultCategoryDataset diagramaBarrasMultas = new DefaultCategoryDataset();

                    diagramaBarrasMultas.setValue(carrera0, "Administrador", "Administrador");
                    diagramaBarrasMultas.setValue(carrera1, "Ingeniería de software", "Ingeniería de software");
                    diagramaBarrasMultas.setValue(carrera2, "Diseño gráfico", "Diseño gráfico");
                    diagramaBarrasMultas.setValue(carrera3, "Diseño de modas", "Diseño de modas");
                    diagramaBarrasMultas.setValue(carrera4, "Economía y finanzas", "Economía y finanzas");
                    diagramaBarrasMultas.setValue(carrera5, "Comercio internacional", "Comercio internacional");
                    diagramaBarrasMultas.setValue(carrera6, "Hotelería y turísmo", "Hotelería y turísmo");
                    diagramaBarrasMultas.setValue(carrera7, "Logística empresarial", "Logística empresarial");

                    JFreeChart graficaBarraChartMultas = ChartFactory.createBarChart3D("Multas","Carrera", "Cantidad", diagramaBarrasMultas, PlotOrientation.VERTICAL, true,true, false);

                    ChartUtilities.saveChartAsJPEG(new File("C:\\sigeli\\graficos\\graficoBarraMultas.png"), graficaBarraChartMultas, 400, 400);


                }
                else{
                    documentoPdf.add(new Paragraph("No existe ningún registro"));
                }

                documentoPdf.add(new Paragraph("\n"));
                //Pagos

                carrera0 = 0;
                carrera1 = 0;
                carrera2 = 0;
                carrera3 = 0;
                carrera4 = 0;
                carrera5 = 0;
                carrera6 = 0;
                carrera7 = 0;

                documentoPdf.add(new Paragraph("Pagos" , fuenteSubTitulo));
                documentoPdf.add(new Paragraph("\n"));

                for(MultaEntity multa : iMultaRepository.findAll()){
                    for(PrestamoEntity prestamo : iPrestamoRepository.findAll()){
                        if(multa.getIdEstado() == 5 || multa.getIdEstado() == 7 && prestamo.getFechaEntrega().after(fechaInicio) && prestamo.getFechaEntrega().before(fechaFinal)){
                            if(prestamo == multa.getPrestamoEntity()){
                                for(UsuarioEntity persona : iUsuarioRepository.findAll()){
                                    if(prestamo.getUsuarioEntity().getDocumento() == persona.getDocumento()){
                                        documentoPdf.add(new Paragraph("Pago de la multa: " + multa.getId() + "\nID del prestamo multado : " + multa.getPrestamoEntity().getId()));
                                        documentoPdf.add(new Paragraph("Persona que pagó la multa: " + persona.getNombre() + "\nDocumento: " + persona.getDocumento()));
                                        if(persona.getIdCarrera() != 0){
                                            documentoPdf.add(new Paragraph("Programa al que pertenece: " + carrera.ordinal(persona.getIdCarrera())));
                                        }
                                        else{
                                            documentoPdf.add(new Paragraph("Administrador"));
                                        }
                                        documentoPdf.add(new Paragraph("Fecha del prestamo: " + prestamo.getFechaPrestamo() + "\nFecha de entrega: " + prestamo.getFechaEntrega()));
                                        documentoPdf.add(new Paragraph("Fecha en la que se Pagó la multa: " + multa.getFechaPago()));
                                        documentoPdf.add(new Paragraph("ISBN del libro multado: " + prestamo.getLibroEntity().getIsbn()));
                                        documentoPdf.add(new Paragraph("Valor pagado: " + multa.getPago()));
                                        documentoPdf.add(new Paragraph("\n"));
                                        documentoPdf.add(new Paragraph("\n"));

                                        if(multa.getIdEstado() == 5){
                                            cantPagos += 1;
                                            cantDinero += multa.getPago();
                                        }
                                        else if(multa.getIdEstado() == 7){
                                            cantCondenados += 1;
                                        }

                                        if(persona.getIdCarrera() == 0){
                                            carrera0 += 1;
                                        }
                                        else if(persona.getIdCarrera() == 1){
                                            carrera1 += 1;
                                        }
                                        else if(persona.getIdCarrera() == 2){
                                            carrera2 += 1;
                                        }
                                        else if(persona.getIdCarrera() == 3){
                                            carrera3 += 1;
                                        }
                                        else if(persona.getIdCarrera() == 4){
                                            carrera4 += 1;
                                        }
                                        else if(persona.getIdCarrera() == 5){
                                            carrera5 += 1;
                                        }
                                        else if(persona.getIdCarrera() == 6){
                                            carrera6 += 1;
                                        }
                                        else if(persona.getIdCarrera() == 7){
                                            carrera7 += 1;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }



                //Creacion de las graficas

                //Graficas de pastel

                DefaultPieDataset graficaPastelPago = new DefaultPieDataset();

                int promPagos = 0;

                try {
                    promPagos = 100/(carrera0 +carrera1 + carrera2 + carrera3 + carrera4 + carrera5 + carrera6 + carrera7);
                } catch (Exception e) {
                }


                if(promPagos != 0){
                    pagosDoc = true;
                    PdfPTable tabla3 = new PdfPTable(2);

                    documentoPdf.add(new Paragraph("\n"));
                    documentoPdf.add(new Paragraph("Estadisticas por carrera - Pagos" , fuenteSubTitulo));
                    documentoPdf.add(new Paragraph("\n"));

                    //Cabecera
                    tabla3.addCell("Cargo/carrera");
                    tabla3.addCell("Cantidad de Pagos");
                    //Carrera 0
                    tabla3.addCell("Administradores");
                    tabla3.addCell("" + carrera0);
                    //Carrera 1
                    tabla3.addCell("Ingeniería de software");
                    tabla3.addCell("" + carrera1);
                    //Carrera 2
                    tabla3.addCell("Diseño gráfico");
                    tabla3.addCell("" + carrera2);
                    //Carrera 3
                    tabla3.addCell("Diseño de modas");
                    tabla3.addCell("" + carrera3);
                    //Carrera 4
                    tabla3.addCell("Economía y finanzas");
                    tabla3.addCell("" + carrera4);
                    //Carrera 5
                    tabla3.addCell("Comercio internacional");
                    tabla3.addCell("" + carrera5);
                    //Carrera 6
                    tabla3.addCell("Hotelería y turísmo");
                    tabla3.addCell("" + carrera6);
                    //Carrera 7
                    tabla3.addCell("Logística empresarial");
                    tabla3.addCell("" + carrera7);

                    documentoPdf.add(tabla3);

                    PdfPTable reciboPago = new PdfPTable(2);

                    documentoPdf.add(new Paragraph("\n"));
                    documentoPdf.add(new Paragraph("Pagos" , fuenteSubTitulo));
                    documentoPdf.add(new Paragraph("\n"));

                    reciboPago.addCell("Cantidad de pagos");
                    reciboPago.addCell("Dinero en caja");

                    reciboPago.addCell("Condenados");
                    reciboPago.addCell("" + cantCondenados);

                    reciboPago.addCell("pagos");
                    reciboPago.addCell("" + cantPagos);

                    reciboPago.addCell("Total");
                    reciboPago.addCell("" + cantDinero);

                    documentoPdf.add(reciboPago);

                    graficaPastelPago.setValue("Administradores", Double.valueOf((promPagos*carrera0)));
                    graficaPastelPago.setValue("Ingeniería de software", Double.valueOf(promPagos*carrera1));
                    graficaPastelPago.setValue("Diseño gráfico", Double.valueOf(promPagos*carrera2));
                    graficaPastelPago.setValue("Diseño de modas", Double.valueOf(promPagos*carrera3));
                    graficaPastelPago.setValue("Economía y finanzas", Double.valueOf(promPagos*carrera4));
                    graficaPastelPago.setValue("Comercio internacional", Double.valueOf(promPagos*carrera5));
                    graficaPastelPago.setValue("Hotelería y turísmo", Double.valueOf(promPagos*carrera6));
                    graficaPastelPago.setValue("Logística empresarial", Double.valueOf(promPagos*carrera7));

                    JFreeChart graficoCahrtPago = ChartFactory.createPieChart3D("Pagos", graficaPastel, true, true, false);

                    ChartUtilities.saveChartAsJPEG(new File("C:\\sigeli\\graficos\\graficoPastelPago.png"), graficoCahrtPago, 400, 400);



                    //Graficas de barras

                    DefaultCategoryDataset diagramaBarrasPago = new DefaultCategoryDataset();

                    diagramaBarrasPago.setValue(carrera0, "Administrador", "Administrador");
                    diagramaBarrasPago.setValue(carrera1, "Ingeniería de software", "Ingeniería de software");
                    diagramaBarrasPago.setValue(carrera2, "Diseño gráfico", "Diseño gráfico");
                    diagramaBarrasPago.setValue(carrera3, "Diseño de modas", "Diseño de modas");
                    diagramaBarrasPago.setValue(carrera4, "Economía y finanzas", "Economía y finanzas");
                    diagramaBarrasPago.setValue(carrera5, "Comercio internacional", "Comercio internacional");
                    diagramaBarrasPago.setValue(carrera6, "Hotelería y turísmo", "Hotelería y turísmo");
                    diagramaBarrasPago.setValue(carrera7, "Logística empresarial", "Logística empresarial");

                    JFreeChart graficaBarraChartPago = ChartFactory.createBarChart3D("Pagos","Carrera", "Cantidad", diagramaBarrasPago, PlotOrientation.VERTICAL, true,true, false);

                    ChartUtilities.saveChartAsJPEG(new File("C:\\sigeli\\graficos\\graficoBarraPago.png"), graficaBarraChartPago, 400, 400);

                }
                else{
                    documentoPdf.add(new Paragraph("No existe ningún registro"));
                }

                documentoPdf.setMargins(30,30, 0, 30);

                if(prestamosDoc){
                    Image image = Image.getInstance("C:\\sigeli\\graficos\\graficoPastel.png");
                    documentoPdf.add(image);

                    Image imagenGraf = Image.getInstance("C:\\sigeli\\graficos\\graficoBarra.png");
                    documentoPdf.add(imagenGraf);

                }
                if(multasDoc){
                    Image imageMultas = Image.getInstance("C:\\sigeli\\graficos\\graficoPastelMultas.png");
                    documentoPdf.add(imageMultas);

                    Image imagenGrafMultas = Image.getInstance("C:\\sigeli\\graficos\\graficoBarraMultas.png");
                    documentoPdf.add(imagenGrafMultas);
                }

                if(pagosDoc){
                    Image imagePago = Image.getInstance("C:\\sigeli\\graficos\\graficoPastelPago.png");
                    documentoPdf.add(imagePago);

                    Image imagenGrafPago = Image.getInstance("C:\\sigeli\\graficos\\graficoBarraPago.png");
                    documentoPdf.add(imagenGrafPago);
                }


                documentoPdf.close();

                return documentoPdf;


            } catch (Exception e) {
                documentoPdf.close();

                throw new RuntimeException("No se ha podido crear el documento " + e.getMessage());

            }
        }

        else {
            Document documentoPdf = new Document();

            return documentoPdf;
        }

    }


}

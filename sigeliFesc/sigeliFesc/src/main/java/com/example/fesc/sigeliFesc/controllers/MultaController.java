package com.example.fesc.sigeliFesc.controllers;

import com.example.fesc.sigeliFesc.models.respuestas.MultaDataRestModel;
import com.example.fesc.sigeliFesc.services.IMultaService;
import com.example.fesc.sigeliFesc.shared.MultaDto;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/multa")
public class MultaController {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    IMultaService iMultaService;

    @GetMapping
    public List<MultaDataRestModel> verMultas(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        List<MultaDataRestModel> multaDataRestModelList = new ArrayList<>();

        MultaDataRestModel multaDataRestModel;

        for(MultaDto multaDto : iMultaService.verMultas()){

            multaDataRestModel = modelMapper.map(multaDto, MultaDataRestModel.class);
            multaDataRestModelList.add(multaDataRestModel);

        }

        return multaDataRestModelList;

    }

    @GetMapping("/buscar/documento/{documento}")
    public List<MultaDataRestModel> buscarDocumento(@PathVariable("documento")String documento){

        List<MultaDataRestModel> multaDataRestModelList = new ArrayList<>();

        MultaDataRestModel multaDataRestModel;

        for(MultaDto multaDto : iMultaService.buscarDocumento(documento)){

            multaDataRestModel = modelMapper.map(multaDto, MultaDataRestModel.class);
            multaDataRestModelList.add(multaDataRestModel);

        }

        return multaDataRestModelList;

    }

    @GetMapping("/buscar/id/{id}")
    public MultaDataRestModel buscarId(@PathVariable("id")long id){

        MultaDto multaDto = iMultaService.buscarId(id);

        MultaDataRestModel multaDataRestModel = modelMapper.map(multaDto, MultaDataRestModel.class);

        return multaDataRestModel;

    }

    @GetMapping("/pagos")
    public  List<MultaDataRestModel> verPagos(){

        List<MultaDataRestModel> multaDataRestModelList = new ArrayList<>();

        MultaDataRestModel multaDataRestModel;

        for(MultaDto multaDto : iMultaService.verPagos()){

            multaDataRestModel = modelMapper.map(multaDto, MultaDataRestModel.class);
            multaDataRestModelList.add(multaDataRestModel);

        }

        return multaDataRestModelList;

    }

    @GetMapping("/pagos/documento/{documento}")
    public List<MultaDataRestModel> buscarPagoDocumento(@PathVariable("documento")String documento){

        List<MultaDataRestModel> multaDataRestModelList = new ArrayList<>();

        MultaDataRestModel multaDataRestModel;

        for(MultaDto multaDto : iMultaService.verPagoPersona(documento)){

            multaDataRestModel = modelMapper.map(multaDto, MultaDataRestModel.class);
            multaDataRestModelList.add(multaDataRestModel);

        }

        return multaDataRestModelList;

    }

    @GetMapping("/pagos/id/{id}")
    public MultaDataRestModel buscarPagoId(@PathVariable("id")long id){

        MultaDto multaDto = iMultaService.verPago(id);

        MultaDataRestModel multaDataRestModel = modelMapper.map(multaDto, MultaDataRestModel.class);

        return multaDataRestModel;

    }

    @GetMapping("/reporte/{modo}/{fIni}/{fFin}")
    public ResponseEntity<byte[]> generarReporte(@PathVariable("modo") int modo, @RequestBody String observaciones, @PathVariable("fIni") String fechaInicio, @PathVariable("fFin") String fechaFin){

        try{


            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-dd-MM");

            java.util.Date fechaInicioUtil = sdf.parse(fechaInicio);
            java.sql.Date fechaInicioSql = new java.sql.Date(fechaInicioUtil.getTime());

            java.util.Date fechaFinalUtil = sdf.parse(fechaFin);
            java.sql.Date fechaFinaSql = new java.sql.Date(fechaFinalUtil.getTime());


            Document document = iMultaService.generarReporte(modo, fechaInicioSql , fechaFinaSql, observaciones);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, baos);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "archivo.pdf");

            byte[] pdfContent = baos.toByteArray();

            // Retornar el archivo PDF como respuesta
            return new ResponseEntity<>(pdfContent, headers, 200);

        } catch (Exception e) {
            // Manejar errores si ocurriera alguna excepción
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/pagar/{id}")
    public String generarPago(@PathVariable("id")long id){

        iMultaService.pagarMulta(id);

        return "Pago realizado con éxito";

    }

}

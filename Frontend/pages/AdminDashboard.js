import { PrestamosCardsAdmin } from "../components/prestamo/PrestamosCardsAdmin.js";
import { getEstadisticas } from "../connections/helpers/getEstadisticas.js"
import { getPrestamosHoy } from "../connections/helpers/getPrestamosHoy.js";
import { NavAdmin } from "../layouts/NavAdmin.js";

const AdminDashboard = async() =>{

  let estadisticas = await getEstadisticas();
  let prestamosHoy = await getPrestamosHoy();

  console.log(prestamosHoy.length)
  const header = document.querySelector("header")

  header.innerHTML = await NavAdmin()
    

    const vista = `
    <div class="fondoRegistro row" style="width: 100.7%; overflow-y: hidden;">
      <div class="col-md-3">

        <div class="row d-flex justify-content-center">

          <div class="card m-4 col-md-12">
            <ul class="list-group list-group-flush">
              <li class="list-group-item"><i class="bi bi-book"></i><b> Prestamos</b></li>
            </ul>
            <div class="card-body">
              <p class="card-text">Prestamos sin entregar: ${estadisticas.cantPrestamos}</p>
              <p class="card-text">Prestamos entregados: ${estadisticas.cantPrestamosEntregados}</p>
              <p class="card-text">Promedio de prestamos: ${estadisticas.promPrestamosEntregados}</p>
            </div>
    
          </div>
    
          <div class="card m-4 col-md-12">
          <ul class="list-group list-group-flush">
            <li class="list-group-item"><i class="bi bi-flag"></i><b> Multas</b></li>
            
            </ul>
            <div class="card-body">
              <p class="card-text">Multas sin pagar: ${estadisticas.cantMultas}</p>
              <p class="card-text">Multas pagadas: ${estadisticas.cantPagos}</p>
              <p class="card-text">Promedio: ${(estadisticas.cantPagos / estadisticas.cantMultas).toFixed(3)}</p>
            </div>
          </div>
    
          <div class="card m-4 col-md-12">
            <ul class="list-group list-group-flush">
            <li class="list-group-item"><i class="bi bi-credit-card"></i><b> Pagos</b></li>
            </ul>
            <div class="card-body">
              <p class="card-text">Dinero pagado: ${estadisticas.dineroPagado}</p>
              <p class="card-text">Dinero en deuda: ${estadisticas.dineroEnDeuda}</p>
              <p class="card-text">Dinero total: ${estadisticas.dineroTotal}</p>
            </div>
            
          </div>
    
    
    
      
    
        </div>

      </div>
    

    <div class="col-sm-9">

      <div class="row mt-4">



      <div class="col-md-5 ms-5 card" >

        <ul class="list-group list-group-flush">
        <li class="list-group-item"><b><i class="bi bi-clipboard-data"></i> Prestamos por carrera</b></li>
        </ul>
        <div class="card-body">
          <p class="card-text">Software: ${estadisticas.porcentajesCarrerasPrestamos.Software} %</p>
          <div class="progress" role="progressbar" aria-label="Basic example" aria-valuenow="25" aria-valuemin="0" aria-valuemax="100">
            <div class="progress-bar" style="width: ${estadisticas.porcentajesCarrerasPrestamos.Software}%"></div>
          </div>
          <hr>
          <p class="card-text">Diseño Grafico: ${estadisticas.porcentajesCarrerasPrestamos.DisenoGrafico} %</p>
          <div class="progress" role="progressbar" aria-label="Basic example" aria-valuenow="25" aria-valuemin="0" aria-valuemax="100">
            <div class="progress-bar" style="width: ${estadisticas.porcentajesCarrerasPrestamos.DisenoGrafico}%"></div>
          </div>
          <hr>
          <p class="card-text">Modas: ${estadisticas.porcentajesCarrerasPrestamos.Modas} %</p>
          <div class="progress" role="progressbar" aria-label="Basic example" aria-valuenow="25" aria-valuemin="0" aria-valuemax="100">
            <div class="progress-bar" style="width: ${estadisticas.porcentajesCarrerasPrestamos.Modas}%"></div>
          </div>
          <hr>
          <p class="card-text">Turismo: ${estadisticas.porcentajesCarrerasPrestamos.turismo} %</p>
          <div class="progress" role="progressbar" aria-label="Basic example" aria-valuenow="25" aria-valuemin="0" aria-valuemax="100">
            <div class="progress-bar" style="width: ${estadisticas.porcentajesCarrerasPrestamos.turismo}%"></div>
          </div>
          <hr>
          <p class="card-text">Financiera: ${estadisticas.porcentajesCarrerasPrestamos.financiera} %</p>
          <div class="progress" role="progressbar" aria-label="Basic example" aria-valuenow="25" aria-valuemin="0" aria-valuemax="100">
            <div class="progress-bar" style="width: ${estadisticas.porcentajesCarrerasPrestamos.financiera}%"></div>
          </div>
          <hr>
          <p class="card-text">Software: ${estadisticas.porcentajesCarrerasPrestamos.NoEspecificada} %</p>
          <div class="progress" role="progressbar" aria-label="Basic example" aria-valuenow="25" aria-valuemin="0" aria-valuemax="100">
            <div class="progress-bar" style="width: ${estadisticas.porcentajesCarrerasPrestamos.NoEspecificada}%"></div>
          </div>

          <br>
          <br>
          
        </div>


      </div>

      <container class="col-md-5 ms-5 card" >

        <ul class="list-group list-group-flush">
        <li class="list-group-item"><b>Prestamos para entregar hoy</b></li>
        </ul>
        <div class="card-body">

          ${(prestamosHoy.length != 0) ? 

              PrestamosCardsAdmin(prestamosHoy)

              :
          
          `
            <div class="ms-5" style="width: 400px;">

              <p>No hay prestamos por entregar hoy</p>

              <img src="/assets/img/notFoundIcon.png" alt=""  width="80%">

            </div>
`}

          
        </div>

      </container>


      </div>
      
    </div>

      <br>
      <br>
      <br>

    </div>
    </div>

      
    
    `

    return vista

}

export {AdminDashboard}
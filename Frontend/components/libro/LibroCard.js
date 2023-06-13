import getLibros from "../../connections/helpers/getLibros.js"
import { EliminarLibroBoton } from "./EliminarLibroBoton.js"

const LibroCard = (libro) =>{

    const vista = (libro.estado == 1) ? `

    <div class="card m-4 col-md-3" style="width: 18rem;">
        <div class="card-body">
          <h5 class="card-title">${libro.nombre}</h5>
          <div class="card-text">
            <p>Isbn: ${libro.isbn}</p>
            <p>Autor: ${libro.autor}</p>
            <p>Edición: ${libro.edicion}</p>
            <p>Estante: ${libro.estante}</p>
            <p>Fila: ${libro.fila}</p>
          </div>
          <div class="row">
          <a href="#/verLibro/${libro.isbn}" class="btn btn-dark col-md-5">ver</a>
          <div class="col-md-2"></div>
          ${EliminarLibroBoton()}
          </div>
        </div>
      </div>
    
    ` : ``

    return vista

}

export {LibroCard}
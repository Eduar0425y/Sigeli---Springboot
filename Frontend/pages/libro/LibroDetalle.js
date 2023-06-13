import getHash, { hashId } from "../../connections/helpers/getHash.js"
import { NavAdmin } from "../../layouts/NavAdmin.js"
import { NavEstudiante } from "../../layouts/NavEstudiante.js"
import getLibros from "../../connections/helpers/getLibros.js"


const LibroDetalle = async () =>{

    const id = hashId()

    let libroR = await getLibros(id)
    let libro = libroR[0]

    if(localStorage.getItem("Rol") == 1){
        const header = document.querySelector("header")
        header.innerHTML = NavAdmin()
    }
    else if(localStorage.getItem("Rol") == 2){
        const header = document.querySelector("header")
        header.innerHTML = NavEstudiante()
    }


    const vista = `
    
    <form class="row g-3 needs-validation w-50 mt-4 mb-4" style="margin-left: 25%;">

        <h3 style="text-align: center">Datos del libro</h3>
        <div class="col-md-6">
            <label for="validationCustom01" class="form-label">Nombre</label>
            <input type="text" class="form-control" id="validationCustom01" value="${libro.nombre}" required>
        </div>

        <div class="col-md-6">
            <label for="validationCustom01" class="form-label">Autor</label>
            <input type="text" class="form-control" id="validationCustom01" disabled value="${libro.autor}" required>
        </div>

        <div class="col-md-12">
            <label for="validationCustom01" class="form-label">Isbn</label>
            <input type="text" class="form-control" id="validationCustom01" disabled value="${libro.isbn}" required>
        </div>

        <div class="col-md-6">
            <label for="validationCustom01" class="form-label">Fecha de creación</label>
            <input type="text" class="form-control" id="validationCustom01" disabled value="${libro.creacion}" required>
        </div>

        <div class="col-md-6">
            <label for="validationCustom01" class="form-label">Edición</label>
            <input type="text" class="form-control" id="validationCustom01" disabled value="${libro.edicion}" required>
        </div>

        <div class="col-md-6">
            <label for="validationCustom01" class="form-label">Fila</label>
            <input type="text" class="form-control" id="validationCustom01" value="${libro.fila}" required>
        </div>

        <div class="col-md-6">
            <label for="validationCustom01" class="form-label">Estante</label>
            <input type="text" class="form-control" id="validationCustom01" value="${libro.estante}" required>
        </div>
        <div class="col-4">
            <button class=" w-100 btn btn-dark">Actualizar</button>
        </div>
        <div class="col-4">
            <button class="btn w-100 btn-dark">Administrar categorías</button>
        </div>
        <div class="col-4">
            <button class="btn w-100 btn-dark">Prestar</button>
        </div>
    </form>
    
        

    `

    return vista

}

export {LibroDetalle}
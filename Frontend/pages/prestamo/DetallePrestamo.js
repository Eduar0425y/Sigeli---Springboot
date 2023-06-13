import getHash, { hashId } from "../../connections/helpers/getHash.js"
import { NavAdmin } from "../../layouts/NavAdmin.js"
import { NavEstudiante } from "../../layouts/NavEstudiante.js"
import getLibros from "../../connections/helpers/getLibros.js"
import getPrestamo from "../../connections/helpers/getPrestamo.js"


const PrestamoDetalle = async () =>{

    const id = hashId()

    let prestamoR = await getPrestamo()

    if(localStorage.getItem("Rol") == 1){
        const header = document.querySelector("header")
        header.innerHTML = NavAdmin()
    }
    else if(localStorage.getItem("Rol") == 2){
        const header = document.querySelector("header")
        header.innerHTML = NavEstudiante()
    }

    let prestamo

    prestamoR.forEach(presta => {

        if(presta.id == id){
            prestamo = presta
        }
        
    });

    console.log(prestamo)


    const vista = `
    
    <form class="row g-3 needs-validation w-50 mt-4 mb-4" style="margin-left: 25%;">

        <h3 style="text-align: center">Datos del prestamo</h3>
        <div class="col-md-6">
            <label for="validationCustom01" class="form-label">Id del prestamo</label>
            <input type="text" class="form-control" id="validationCustom01" disabled value="${prestamo.id}" required>
        </div>

        <div class="col-md-6">
            <label for="validationCustom01" class="form-label">Persona</label>
            <input type="text" class="form-control" id="validationCustom01" disabled value="${prestamo.usuarioEntity.nombre} - ${prestamo.usuarioEntity.documento}" required>
        </div>

        <div class="col-md-12">
            <label for="validationCustom01" class="form-label">Isbn</label>
            <input type="text" class="form-control" id="validationCustom01" disabled value="${prestamo.libroEntity.isbn}" required>
        </div>

        <div class="col-md-12">
            <label for="validationCustom01" class="form-label">Libro</label>
            <input type="text" class="form-control" id="validationCustom01" disabled value="${prestamo.libroEntity.nombre} - ${prestamo.libroEntity.autor}" required>
        </div>

        <div class="col-md-6">
            <label for="validationCustom01" class="form-label">Fecha de prestamo</label>
            <input type="text" class="form-control" id="validationCustom01" disabled value="${prestamo.fechaPrestamo}" required>
        </div>

        <div class="col-md-6">
            <label for="validationCustom01" class="form-label">Fecha de entrega</label>
            <input type="text" class="form-control" id="validationCustom01" disabled value="${prestamo.fechaEntrega}" required>
        </div>

        <div class="col-4 mt-5">
        </div>
        <div class="col-4 mt-5">
            <button class=" w-100 btn btn-dark">Entregar</button>
        </div>
        <div class="col-4 mt-5">
        </div>
    </form>
    
        

    `

    return vista

}

export {PrestamoDetalle}
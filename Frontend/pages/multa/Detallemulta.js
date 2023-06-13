import getHash, { hashId } from "../../connections/helpers/getHash.js"
import { NavAdmin } from "../../layouts/NavAdmin.js"
import { NavEstudiante } from "../../layouts/NavEstudiante.js"
import getMultas from "../../connections/helpers/getMultas.js"


const MultaDetalle = async () =>{

    const id = hashId()

    let multa = await getMultas(id)

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

        <h3 style="text-align: center">Datos de la multa</h3>
        <div class="col-md-2 mt-4">
            <label for="validationCustom01" class="form-label">Id de la multa</label>
            <input type="text" class="form-control" disabled id="validationCustom01" value="${multa.id}" required>
        </div>

        <div class="col-md-5 mt-4">
            <label for="validationCustom01" class="form-label">Usuario multado</label>
            <input type="text" class="form-control" disabled id="validationCustom01" value="${multa.prestamoEntity.usuarioEntity.nombre}" required>
        </div>

        <div class="col-md-5 mt-4">
            <label for="validationCustom01" class="form-label">Documento</label>
            <input type="text" class="form-control" disabled id="validationCustom01" value="${multa.prestamoEntity.usuarioEntity.documento}" required>
        </div>

        <div class="col-md-6 mt-4">
            <label for="validationCustom01" class="form-label">ISBN del libro multado</label>
            <input type="text" class="form-control" disabled id="validationCustom01" value="${multa.prestamoEntity.libroEntity.isbn}" required>
        </div>

        <div class="col-md-6 mt-4">
            <label for="validationCustom01" class="form-label">Nombre del libro multado</label>
            <input type="text" class="form-control" disabled id="validationCustom01" value="${multa.prestamoEntity.libroEntity.nombre}" required>
        </div>

        <div class="col-md-6 mt-4">
            <label for="validationCustom01" class="form-label">Fecha del prestamo</label>
            <input type="text" class="form-control" disabled id="validationCustom01" value="${multa.prestamoEntity.fechaPrestamo}" required>
        </div>

        <div class="col-md-6 mt-4">
            <label for="validationCustom01" class="form-label">Fecha estimada de entrega</label>
            <input type="text" class="form-control" disabled id="validationCustom01" value="${multa.prestamoEntity.fechaEntrega}" required>
        </div>

        ${!multa.fechaPago ? `<div class="col-4 mt-5">
            <button class=" w-100 btn btn-dark">Pagar</button>
        </div>
        <div class="col-4">
        </div>
        <div class="col-4 mt-5">
            <button class="btn w-100 btn-dark">Condenar</button>
        </div>`:
        `<div class="col-md-6 mt-4">
            <label for="validationCustom01" class="form-label">Fecha del pago</label>
            <input type="text" class="form-control" disabled id="validationCustom01" value="${multa.fechaPago}" required>
        </div>
        <div class="col-md-6 mt-4">
            <label for="validationCustom01" class="form-label">Cantidad de dinero</label>
            <input type="text" class="form-control" disabled id="validationCustom01" value="${multa.pago}" required>
        </div>
        `
        }

        
    </form>
    
        

    `

    return vista

}

export {MultaDetalle}
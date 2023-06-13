import { PrestamosCardsAdmin } from "../../components/prestamo/PrestamosCardsAdmin.js"
import getPrestamo from "../../connections/helpers/getPrestamo.js"
import { NavAdmin } from "../../layouts/NavAdmin.js"
import { NavEstudiante } from "../../layouts/NavEstudiante.js"

const VerPrestamos = async () =>{

    const prestamos = await getPrestamo()

    if(localStorage.getItem("Rol") == 1){
        const header = document.querySelector("header")
        header.innerHTML = NavAdmin()
    }
    else if(localStorage.getItem("Rol") == 2){
        const header = document.querySelector("header")
        header.innerHTML = NavEstudiante()
    }

    const vista = `

    <h3 style="text-align: center" class="mt-3">Prestamos</h3>

    <div class="row">${prestamos.map(prestamo => 
        
            PrestamosCardsAdmin(prestamo, prestamo.usuarioEntity.documento)
        
        )}</div>


    
    `

    return vista

}

export {VerPrestamos}
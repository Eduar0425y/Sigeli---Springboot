import { LibroCard } from "../../components/libro/LibroCard.js"
import { PrestamosCardsAdmin } from "../../components/prestamo/PrestamosCardsAdmin.js"
import { hashId } from "../../connections/helpers/getHash.js"
import getPrestamo from "../../connections/helpers/getPrestamo.js"
import { NavAdmin } from "../../layouts/NavAdmin.js"
import { NavEstudiante } from "../../layouts/NavEstudiante.js"

const VerPrestamosPersona = async () =>{

    const prestamos = await getPrestamo()
    const documento = hashId();

    if(localStorage.getItem("Rol") == 1){
        const header = document.querySelector("header")
        header.innerHTML = NavAdmin()
    }
    else if(localStorage.getItem("Rol") == 2){
        const header = document.querySelector("header")
        header.innerHTML = NavEstudiante()
    }

    const vista = `

    <h3 style="text-align: center" class="mt-3">Prestamos de ${documento}</h3>

    <div class="row">${prestamos.map(prestamo => 
        
            PrestamosCardsAdmin(prestamo, documento)
        
        )}</div>


    
    `

    return vista

}

export {VerPrestamosPersona}
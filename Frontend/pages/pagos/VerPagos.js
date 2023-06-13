import { PagoCard } from "../../components/pagos/PagoCard.js"
import getPagos from "../../connections/helpers/getPagos.js"
import { NavAdmin } from "../../layouts/NavAdmin.js"
import { NavEstudiante } from "../../layouts/NavEstudiante.js"

const VerPagos = async () =>{

    const pagos = await getPagos()

    if(localStorage.getItem("Rol") == 1){
        const header = document.querySelector("header")
        header.innerHTML = NavAdmin()
    }
    else if(localStorage.getItem("Rol") == 2){
        const header = document.querySelector("header")
        header.innerHTML = NavEstudiante()
    }

    const vista = `

    <h3 style="text-align: center" class="mb-3 mt-5">Pagos</h3>

    <div class="row">${pagos.map(pago => 
        
            PagoCard(pago)
        
        )}</div>


    
    `

    return vista

}

export {VerPagos}

import { MultaCard } from "../../components/multa/MultaCard.js"
import getMultas from "../../connections/helpers/getMultas.js"
import { NavAdmin } from "../../layouts/NavAdmin.js"
import { NavEstudiante } from "../../layouts/NavEstudiante.js"

const VerMultas = async () =>{

    const multas = await getMultas()

    console.log(multas)

    if(localStorage.getItem("Rol") == 1){
        const header = document.querySelector("header")
        header.innerHTML = NavAdmin()
    }
    else if(localStorage.getItem("Rol") == 2){
        const header = document.querySelector("header")
        header.innerHTML = NavEstudiante()
    }

    const vista = `

    <h3 style="text-align: center" class="mb-3 mt-5">Multas</h3>


    <div class="row">${multas.map(multa => 
        
            MultaCard(multa)
        
        )}</div>


    
    `

    return vista

}

export {VerMultas}
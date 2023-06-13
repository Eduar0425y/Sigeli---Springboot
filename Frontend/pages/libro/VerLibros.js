import { LibroCard } from "../../components/libro/LibroCard.js"
import getLibros from "../../connections/helpers/getLibros.js"
import { NavAdmin } from "../../layouts/NavAdmin.js"
import { NavEstudiante } from "../../layouts/NavEstudiante.js"

const VerLibros = async () =>{

    const libros = await getLibros()

    if(localStorage.getItem("Rol") == 1){
        const header = document.querySelector("header")
        header.innerHTML = NavAdmin()
    }
    else if(localStorage.getItem("Rol") == 2){
        const header = document.querySelector("header")
        header.innerHTML = NavEstudiante()
    }

    const vista = `

    <div class="row">${libros.map(libro => 
        
            LibroCard(libro)
        
        )}</div>


    
    `

    return vista

}

export {VerLibros}
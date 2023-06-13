import { CategoriaCard } from "../../components/categorias/CategoriaCard.js"
import getCategorias from "../../connections/helpers/getCategorias.js"
import { NavAdmin } from "../../layouts/NavAdmin.js"
import { NavEstudiante } from "../../layouts/NavEstudiante.js"

const VerCategorias = async () =>{

    const categorias = await getCategorias()

    if(localStorage.getItem("Rol") == 1){
        const header = document.querySelector("header")
        header.innerHTML = NavAdmin()
    }
    else if(localStorage.getItem("Rol") == 2){
        const header = document.querySelector("header")
        header.innerHTML = NavEstudiante()
    }

    const vista = `

    <div class="row">${categorias.map(categoria=> 
        
            CategoriaCard(categoria)
        
        )}</div>


    
    `

    return vista

}

export {VerCategorias}
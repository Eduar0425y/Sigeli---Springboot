import { NavEstudiante } from "../layouts/NavEstudiante.js"

const EstudianteDashboard = async() =>{

    const header = document.querySelector("header")

    header.innerHTML = await NavEstudiante()

    let user = localStorage.getItem("Usuario")

    const vista = `

        <p> Documento: ${user}</p>
    

    `

    return vista

}

export {EstudianteDashboard}
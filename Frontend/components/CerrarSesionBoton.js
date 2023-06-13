const CerrarSesionBoton = () =>{

    const vista = `
        <li><a class="dropdown-item" onClick="

        
            
            localStorage.removeItem('token')
            localStorage.removeItem('Usuario')
            localStorage.removeItem('correoUser')
            localStorage.removeItem('Rol')
            window.open('/', '_self')

            "><i class="bi bi-box-arrow-right"></i> Cerrar sesion</a></li>
            
    `

    return vista

}

export {CerrarSesionBoton}
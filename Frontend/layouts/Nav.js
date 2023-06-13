
const Nav = () =>{

    const vista = `<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container-fluid">
        <a class="navbar-brand" href="#">Sigeli</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav ms-auto mb-2 mb-lg-0">
            <li class="nav-item">
                <a class="nav-link" aria-current="page" href="#/">Inicio</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" aria-current="page" href="#/login/">Iniciar sesion</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" aria-current="page" href="#/registro/">Registrarse</a>
            </li>
            <!--<li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                Libro
                </a>
                <ul class="dropdown-menu">
                <li><a class="dropdown-item" href="#">Ver libros</a></li>
                <li><a class="dropdown-item" href="#">Añadir libro</a></li>
                </ul>
            </li>
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                Usuario
                </a>
                <ul class="dropdown-menu dropdown-menu-end">
                <li><a class="dropdown-item" href="#">Cerrar sesion</a></li>
                <li><a class="dropdown-item" href="#">Actualizar contraseña</a></li>
                </ul>
            </li>-->
            </ul>
        </div>
        </div>
    </nav>`

    return vista

}

export {Nav}
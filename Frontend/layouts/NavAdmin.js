import { CerrarSesionBoton } from "../components/CerrarSesionBoton.js"

const NavAdmin = () =>{

    const vista = `<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container-fluid">
    <a class="navbar-brand" href="#"><i class="bi bi-cup-hot"></i> Sigeli - admin</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav ms-auto mb-2 mb-lg-0">
        <li class="nav-item">
            <a class="nav-link" aria-current="page" href="#/admin"><i class="bi bi-house"></i> Inicio</a>
        </li>
        <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
            <i class="bi bi-journal-bookmark"></i> Libro
            </a>
            <ul class="dropdown-menu">
            <li><a class="dropdown-item" href="/#/libros">Ver libros</a></li>
            <li><a class="dropdown-item" href="#">Crear libro</a></li>
            </ul>
        </li>
        <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
            <i class="bi bi-bookmark-check"></i> Categoría
            </a>
            <ul class="dropdown-menu">
            <li><a class="dropdown-item" href="#/categorias">Ver categorías</a></li>
            <li><a class="dropdown-item" href="#">Añadir categoría</a></li>
            </ul>
        </li>
        <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
            <i class="bi bi-minecart-loaded"></i> Prestamos
            </a>
            <ul class="dropdown-menu">
            <li><a class="dropdown-item" href="#/verPrestamos/">Ver prestamos</a></li>
            <li><a class="dropdown-item" data-bs-toggle="modal" data-bs-target="#prestamo">Prestamos de usuario</a></li>
            <li><a class="dropdown-item" href="#">Crear Prestamos</a></li>
            </ul>
        </li>
        <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
            <i class="bi bi-shield-slash"></i> Multas
            </a>
            <ul class="dropdown-menu">
            <li><a class="dropdown-item" href="#/multas">Ver multas</a></li>
            </ul>
        </li>
        <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
            <i class="bi bi-credit-card"></i> Pagos
            </a>
            <ul class="dropdown-menu">
            <li><a class="dropdown-item" href="#/verPagos" >Ver pagos</a></li>
            </ul>
        </li>
        <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
            ${localStorage.getItem("correoUser")} <i class="bi bi-person-circle"></i> 
            </a>
            <ul class="dropdown-menu dropdown-menu-end">
            ${CerrarSesionBoton()}
            <li><a class="dropdown-item" href="#"><i class="bi bi-gear"></i> Cuenta</a></li>
            </ul>
        </li>
        </ul>
    </div>
    </div>
</nav>

<div class="modal fade" id="prestamo" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h1 class="modal-title fs-5" id="exampleModalLabel">Ver pretamos de una persona</h1>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        <form class="row g-3 needs-validation" novalidate>
            <div class="col-md-12">
                <label for="validationCustom01" class="form-label">Documento</label>
                <input type="number" class="form-control" id="documentoBuscarPrestamo" required>
            </div>
            <div class="col-12">
            <button class="btn btn-dark" data-bs-dismiss="modal" aria-label="Close" onclick='
                let documento = document.getElementById("documentoBuscarPrestamo").value;
                window.open("#/verPrestamo/" + documento, "_self");
                ''>Buscar</button>
            </div>
        </form>
      </div>
    </div>
  </div>
</div>`

    return vista

}

export {NavAdmin}
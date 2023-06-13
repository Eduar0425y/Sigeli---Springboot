const PrestamosCardsAdmin = (prestamo, documento) =>{

    let vista = prestamo.usuarioEntity.documento == documento ? `
        
        <div class="card m-4 col-md-3" style="width: 18rem;">
        <div class="card-body">
        <h5 class="card-title">${prestamo.id}</h5>
        <img src="/assets/img/multaIcono.png" class="w-50 mb-3" style="margin-left: 25%;" />
        <div class="card-text">
            <p>Libro prestado: ${prestamo.libroEntity.isbn}</p>
            <p>Fecha de entrega: ${prestamo.fechaEntrega}</p>
            <p>Fecha de prestamo: ${prestamo.fechaPrestamo}</p>
            <p>Usuario: ${prestamo.usuarioEntity.nombre} - ${prestamo.usuarioEntity.documento}</p>
        </div>
        <div class="row">
        <a href="#/verPrestamo/${prestamo.id}" class="btn btn-dark w-100 col-md-5">ver</a>
        </div>
        </div>
    </div>
    
    `
    :``

        
    

    return vista

}

export {PrestamosCardsAdmin}
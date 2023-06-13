
const MultaCard = (multa) =>{

    const vista = `

    <div class="card m-4 col-md-3" style="width: 18rem;">
        <div class="card-body">
          <h5 class="card-title">${multa.id}</h5>
          <img src="/assets/img/multaIcono.png" class="w-50 mb-3" style="margin-left: 25%;" />
          <div class="card-text">
            <p>Prestamo multado: ${multa.prestamoEntity.id}</p>
            <p>Libro multado: ${multa.prestamoEntity.libroEntity.isbn}</p>
          </div>
          <div class="row">
          <a href="#/verMulta/${multa.id}" class="btn btn-dark w-100 col-md-5">ver</a>
          </div>
        </div>
      </div>
    
    `

    return vista

}

export {MultaCard}
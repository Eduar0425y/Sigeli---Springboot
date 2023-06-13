
const PagoCard = (pago) =>{

    const vista = `

    <div class="card m-4 col-md-3" style="width: 18rem;">
        <div class="card-body">
          <h5 class="card-title">${pago.id}</h5>
          <img src="/assets/img/pagoIcono.png" class="w-50 mb-3" style="margin-left: 25%;" />
          <div class="card-text">
            <p>Prestamo multado: ${pago.prestamoEntity.id}</p>
            <p>Libro multado: ${pago.prestamoEntity.libroEntity.isbn}</p>
          </div>
          <div class="row">
          <a href="#/verMulta/${pago.id}" class="btn btn-dark w-100 col-md-5">ver</a>
          </div>
        </div>
      </div>
    
    `

    return vista

}

export {PagoCard}
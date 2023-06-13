

const CategoriaCard = (categoria) =>{

    const vista =`

    <div class="card m-4 col-md-3" style="width: 18rem;">
        <div class="card-body">
          <h5 class="card-title">${categoria.id}</h5>
          <div class="card-text">
            <p>Nombre: ${categoria.nombre}</p>
          </div>
          <div class="row">
          <div class="col-md-2"></div>
            <button class="btn btn-dark">Actualizar</button>
          </div>
        </div>
      </div>
    
    ` 

    return vista

}

export {CategoriaCard}
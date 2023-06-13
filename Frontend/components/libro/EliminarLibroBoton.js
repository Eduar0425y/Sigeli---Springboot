const EliminarLibroBoton = () =>{

    const vista = `

    <a href="/Libros" class="btn btn-dark col-md-5" data-bs-toggle="modal" data-bs-target="#modalEliminar">Eliminar</a>

    <!-- Modal -->
<div class="modal fade" id="modalEliminar" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h1 class="modal-title fs-5" id="exampleModalLabel">Eliminar libro</h1>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        ¿Está seguro de eliminar el libro?
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
        <button type="button" class="btn btn-primary" onclick="window.alert('ok')" >Eliminar</button>
      </div>
    </div>
  </div>
</div>

    `

    return vista
}

export {EliminarLibroBoton}
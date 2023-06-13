const RegistrarseFormulario = () =>{

    const vista = `

    <form>
      
        <div class="d-flex align-items-center mb-3 pb-1">
        <span class=" fw-bold mb-0"><h3>Registrate Aquí</h3>
            </span>
        </div>

        <div class="form-outline">
        <input type="number" id="documento" class="form-control form-control-lg" />
        <label class="form-label" for="documento">Documento</label>
        </div>

        <div class="form-outline">
        <input type="text" id="nombre" class="form-control form-control-lg" />
        <label class="form-label" for="nombre">Nombre</label>
        </div>

        <div class="form-outline">
        <input type="text" id="apellido" class="form-control form-control-lg" />
        <label class="form-label" for="apellido">Apellido</label>
        </div>

        <div class="form-outline">
        <input type="email" id="correo" class="form-control form-control-lg" />
        <label class="form-label" for="correo">Correo</label>
        </div>

        <div class="form-outline">
        <input type="number" id="telefono" class="form-control form-control-lg" />
        <label class="form-label" for="telefono">Telefono</label>
        </div>

        <div class="form-outline">
        <select class="form-control form-control-lg" name="" id="carrera">
            <option selected>Seleccione</option>
            <option value="1">Software</option>
            <option value="2">Diseño gráfico</option>
            <option value="3">Modas</option>
            <option value="4">Financiera</option>
            <option value="5">Negocios internacionales</option>
        </select>
        <label class="form-label" for="carrera">Carrera</label>
        </div>

        <div class="form-outline">
        <input type="password" id="clave" class="form-control form-control-lg" />
        <label class="form-label" for="clave">Contraseña</label>
        </div>

        <div class="pt-1 mb-2">
        <button class="btn btn-dark btn-lg btn-block" onclick="
        
        var documento = document.getElementById('documento').value;
        var nombre = document.getElementById('nombre').value;
        var apellido = document.getElementById('apellido').value;
        var correo = document.getElementById('correo').value;
        var telefono = document.getElementById('telefono').value;
        var idCarrera = document.getElementById('carrera').value;
        var password = document.getElementById('clave').value;
      
        // Crea un objeto de datos con los valores
        var data = {
            
            'documento': documento,
            'nombre': nombre,
            'apellido': apellido,
            'correo': correo,
            'telefono' : telefono,
            'idCarrera': parseInt(carrera),
            'idCargo' : '1',
            'password' : password

        };
      
        // Realiza la petición POST utilizando fetch o XMLHttpRequest
        fetch('http://localhost:8080/usuario', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify(data)
        }).then(response => {

            if(response.headers.get('status') != 200 && response.headers.get('status') != null){

                window.alert('Error')

            }
            else{
                window.alert('Usuario registrado con éxito')
            }

            return response.json();
        })
          .then(respuesta => {
            // Hacer algo con la respuesta recibida

            console.log(respuesta);
            // Mostrar el objeto JSON en el DOM
          })
          .catch(error => {
            console.error('Error:', error);
          });
">Registrarse</button>
        </div>

    

    </form>


    `

    return vista

}

export {RegistrarseFormulario}
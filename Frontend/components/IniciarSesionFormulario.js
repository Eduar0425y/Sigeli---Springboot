const IniciarSesionFormulario = () =>{

    const vista = `

    <form style="height: 580px;" id="loginForm">
      
        <div class="d-flex align-items-center mb-3 pb-1">
        <i class="fas fa-cubes fa-2x me-3" style="color: #ff6219;"></i>
        <span class="h1 fw-bold mb-0"><img src="https://upload.wikimedia.org/wikipedia/commons/thumb/2/21/Logo_of_FESC_University.svg/2560px-Logo_of_FESC_University.svg.png"
            width="35%"></span>
        </div>

        <h5 class="fw-normal mb-3 pb-3" style="letter-spacing: 1px;">Inicia sesion con tu cuenta</h5>

        <div class="form-outline mb-4">
        <input type="number" id="documento" class="form-control form-control-lg" />
        <label class="form-label" for="documento">Documento de identidad</label>
        </div>

        <div class="form-outline mb-4">
        <input type="password" id="clave" class="form-control form-control-lg" />
        <label class="form-label" for="clave">Contraseña</label>
        </div>

        <div class="pt-1 mb-4">
        <button class="btn btn-dark btn-lg btn-block"  onclick="// Obtiene los valores de los campos del formulario
        var username = document.getElementById('documento').value;
        var password = document.getElementById('clave').value;
      
        // Crea un objeto de datos con los valores
        var data = {
            
          'username':username,
          'password':password

        };
      
        // Realiza la petición POST utilizando fetch o XMLHttpRequest
        fetch('http://localhost:8080/usuario/login', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify(data)
        }).then(response => response.json())
          .then(respuesta => {
            // Hacer algo con la respuesta recibida
            localStorage.setItem('token', respuesta.token)

            if(localStorage.getItem('token') != null){
              window.alert('gg bro')

              fetch('http://localhost:8080/usuario/buscar/'+ username, {
              method: 'GET',
              headers: {
                'Authorization': 'Bearer ' + localStorage.getItem('token'),
                'Content-Type': 'application/json'
              },
            }).then(response => response.json())
              .then(respuesta => {
                // Hacer algo con la respuesta recibida
                const user = respuesta

                localStorage.setItem('Usuario', user.documento)
                localStorage.setItem('correoUser', user.correo)
                localStorage.setItem('Rol', user.idCargo)

                if(respuesta.idCargo == 2){
                  
                  window.open('/#/estudiante', '_self')
                  
                }
                else if(respuesta.idCargo == 1){
                 
                  window.open('/#/admin', '_self')
                  
                }
                console.log(respuesta);
                // Mostrar el objeto JSON en el DOM
              })
              .catch(error => {
                console.error('Error:', error);
              });

            }
            // Mostrar el objeto JSON en el DOM
          })
          .catch(error => {
            console.error('Error:', error);
          });

        
        
        ">Ingresar</button>
        </div>

        <a class="small text-muted" href="#!">¿Olvidaste tu contraseña?</a>
        

    </form>


    `




    

    return vista

}

export {IniciarSesionFormulario}
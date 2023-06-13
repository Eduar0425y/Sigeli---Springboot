//modulo, por eso es en mayuscula

import { IniciarSesionFormulario } from "../components/IniciarSesionFormulario.js"

const IniciarSesion = () =>{

    const vista = (localStorage.getItem("token") ? 
    
    (localStorage.getItem("Rol") == 1 ? 
    
      (window.open("#/admin", '_self'))
      :
      (window.open("#/estudiante", '_self')))

    :
    
    
    `<section class="vh-120 fondoRegistro">
    <div class="container py-5 h-100" >
      <div class="row d-flex justify-content-center align-items-center h-100" >
        <div class="col  col-xl-10" >
          <div class="card" style="border-radius: 1rem;">
            <div class="row g-0" style="margin-bottom: -70px;">
              <div class="col-md-6 col-lg-5 d-none d-md-block">
                <img src="/assets/img/bannerLogin.jpg"
                  alt="login form" class="img-fluid" style="border-radius: 1rem 0 0 1rem;"/>
              </div>
              <div class="col-md-6 col-lg-7 d-flex align-items-center">
                <div class="card-body p-4 p-lg-5 text-black">
  
                  ${IniciarSesionFormulario()}

                  <p class="mb-5 pb-lg-2" style="color: #393f81;">¿No estás registrado? <a href="/#/registro"
                        style="color: #393f81;">Regsitrate ahora</a></p>
  
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
`)
    
    
    

    return vista

}

export {IniciarSesion}
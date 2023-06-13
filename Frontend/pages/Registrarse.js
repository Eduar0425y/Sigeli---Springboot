import { RegistrarseFormulario } from "../components/RegistrarseFormulario.js"

const Registrarse = () =>{

    const vista = `
    
    <section class="vh-120 fondoRegistro" >
        <div class="container py-5 h-100" >
          <div class="row d-flex justify-content-center align-items-center h-100" >
            <div class="col col-xl-10" >
              <div class="card" style="border-radius: 1rem;">
                <div class="row g-0" style="margin-bottom: -70px;">
                  <div class="col-md-12 col-lg-12 d-none d-md-block">
                    <img src="/assets/img/bannerRegistro.png"
                      alt="login form" class="img-fluid" style="border-radius: 1rem 1rem 1rem 1rem;"/>
                  </div>
                  <div class="col-md-12 col-lg-12 d-flex align-items-center">
                    <div class="card-body p-4 p-lg-5 text-black">
      
                      ${RegistrarseFormulario()}

                      <p class="mb-5 pb-lg-2" style="color: #393f81;">¿Ya tienes una cuenta? <a href="/#/login"
                            style="color: #393f81;">Inicia sesion</a></p>
      
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>

    `

    return vista

}

export {Registrarse}
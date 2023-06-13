import { Nav } from "../layouts/Nav.js"
import { Footer } from "../layouts/Footer.js"
import { IniciarSesion } from "../pages/IniciarSesion.js"
import { Registrarse } from "../pages/Registrarse.js"
import { Error404 } from "../pages/Error404.js"
import { Inicio } from "../pages/Inicio.js"
import getHash from "../connections/helpers/getHash.js"
import resolverRuta from "../connections/helpers/resolverRuta.js"
import { VerLibros } from "../pages/libro/VerLibros.js"
import { AdminDashboard } from "../pages/AdminDashboard.js"
import { EstudianteDashboard } from "../pages/EstudianteDashboard.js"
import { LibroDetalle } from "../pages/libro/LibroDetalle.js"
import { VerCategorias } from "../pages/categorias/VerCategorias.js"
import { VerMultas } from "../pages/multa/VerMultas.js"
import { MultaDetalle } from "../pages/multa/Detallemulta.js"
import { VerPagos } from "../pages/pagos/VerPagos.js"
import { VerPrestamosPersona} from "../pages/prestamo/VerPrestamosPersona.js"
import { VerPrestamos } from "../pages/prestamo/VerPrestamo.js"
import { PrestamoDetalle } from "../pages/prestamo/DetallePrestamo.js"

const Rutas = {

    "/" : Inicio,
    "/login" : IniciarSesion,
    "/registro" : Registrarse,
    "/libros" : VerLibros,
    "/verlibro/:id" : LibroDetalle,
    "/admin" : AdminDashboard,
    "/estudiante" : EstudianteDashboard,
    "/categorias" : VerCategorias,
    "/multas" : VerMultas,
    "/verMulta/:id" : MultaDetalle,
    "/verPagos" : VerPagos,
    "/verPrestamos/:documento" : VerPrestamosPersona,
    "/verPrestamos" : VerPrestamos,
    "/verPrestamo/:id": PrestamoDetalle

}

const App = async () =>{

    const header = document.querySelector("header")
    const main = document.querySelector("main")
    const footer = document.querySelector("footer")

    header.innerHTML = await Nav()
    footer.innerHTML = await Footer()

    let ruta = await resolverRuta(getHash())
    console.log(ruta)
    let pagina = (Rutas[ruta]) ? Rutas[ruta] : Error404

    main.innerHTML = await pagina()

}

export {App}
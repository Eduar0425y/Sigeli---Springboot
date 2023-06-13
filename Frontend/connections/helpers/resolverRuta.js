
export default (ruta) => {

    if(ruta.length < 3){

        return(ruta === "/") ? ruta : "/:id"
    }


    if(ruta == "verLibro"){

        return "/verlibro/:id"

    }

    if(ruta == "verMulta"){

        return "/verMulta/:id"

    }

    if(ruta == "verPrestamo"){

        return "/verPrestamos/:documento"

    }

    return `/${ruta}`

}
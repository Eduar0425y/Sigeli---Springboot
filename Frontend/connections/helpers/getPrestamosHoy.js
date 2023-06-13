import { addToken } from "../autenticaciones.js"
import { urlPrestamoHoy } from "./endpoints.js"

const getPrestamosHoy = async() => {


    if(localStorage.getItem("Rol") == 1){

        try {

            const response = await addToken(urlPrestamoHoy, 'GET')
            const data = await response.json()


            return data

        }
        catch (error) {

            console.log("Error fetch ", error)
            
        }
    }


}

export {getPrestamosHoy}
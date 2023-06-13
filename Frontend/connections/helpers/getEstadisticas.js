import { addToken } from "../autenticaciones.js"
import  {urlEstadisticas}  from "../helpers/endpoints.js"

const getEstadisticas = async() => {


    if(localStorage.getItem("Rol") == 1){

        try {


            const response = await addToken(urlEstadisticas, 'GET')
            const data = await response.json()


            return data

        }
        catch (error) {

            console.log("Error fetch ", error)
            
        }
    }


}

export {getEstadisticas}
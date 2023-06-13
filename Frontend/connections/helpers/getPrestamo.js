import { addToken } from "../autenticaciones.js"
import { urlPrestamo } from "./endpoints.js"


const API = urlPrestamo

export default async (id) =>{

    const apiUrl = (id) ? `${API}/buscarId/${id}` : API

    try {

        const response = await addToken(apiUrl, "GET")
        const data = await response.json()


        return data
        
    } catch (error) {

        console.log("Error fetch ", error)
        
    }

}
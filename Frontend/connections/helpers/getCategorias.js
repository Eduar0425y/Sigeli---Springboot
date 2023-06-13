import { addToken } from "../autenticaciones.js"
import { urlCategoria } from "./endpoints.js"


const API = urlCategoria

export default async (nombre) =>{

    const apiUrl = (nombre) ? `${API}/buscar/${nombre}` : API

    try {

        const response = await addToken(apiUrl, "GET")
        const data = await response.json()

        return data
        
    } catch (error) {

        console.log("Error fetch ", error)
        
    }

}
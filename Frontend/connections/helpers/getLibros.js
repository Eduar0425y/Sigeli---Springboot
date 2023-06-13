import { addToken } from "../autenticaciones.js"
import { urlLibro } from "./endpoints.js"


const API = urlLibro

export default async (id) =>{

    const apiUrl = (id) ? `${API}/buscarModo/${id}/1` : API

    try {

        const response = await addToken(apiUrl, "GET")
        const data = await response.json()


        return data
        
    } catch (error) {

        console.log("Error fetch ", error)
        
    }

}
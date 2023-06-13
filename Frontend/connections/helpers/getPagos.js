import { addToken } from "../autenticaciones.js"
import { urlPagos } from "./endpoints.js"


const API = urlPagos

export default async (id) =>{

    const apiUrl = (id) ? `${API}/id/${id}` : API

    try {

        const response = await addToken(apiUrl, "GET")
        const data = await response.json()


        return data
        
    } catch (error) {

        console.log("Error fetch ", error)
        
    }

}
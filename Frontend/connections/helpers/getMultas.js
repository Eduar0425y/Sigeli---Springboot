import { addToken } from "../autenticaciones.js"
import { urlMulta } from "./endpoints.js"


const API = urlMulta

export default async (id) =>{

    const apiUrl = (id) ? `${API}/buscar/id/${id}` : API

    try {

        const response = await addToken(apiUrl, "GET")
        const data = await response.json()


        return data
        
    } catch (error) {

        console.log("Error fetch ", error)
        
    }

}
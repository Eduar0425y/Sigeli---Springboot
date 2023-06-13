
const addToken = async(url, metodo) =>{

    const peticion = await fetch(url, {
        method: metodo,
        headers: {
          'Content-Type': 'application/json',
          'Authorization': 'Bearer ' + localStorage.getItem("token")
        },
      })

      console.log(peticion)

      return peticion;

}

export {addToken}
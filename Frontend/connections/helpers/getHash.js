export default () => location.hash.slice(1).split("/")[1] || "/"

const hashId = () =>{
    return location.hash.slice(1).split("/")[2] || "/"
}

export {hashId}

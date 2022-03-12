
function EnviarFormulario() {
    //Si estamos en plataforma 2d
    var query;
    if(document.getElementById("tipo-juego-plat").checked){
        query =  checkPlatfrom();
    }
    else{
        query = checkShooter();
    }
}

function checkPlatfrom(){
    var bodyFormData = {      
        id: 1,
        content: "Hello, World!",
        prueba: {
            holo: "high",
            saludation: "holichiwinchis"
        }
    }
    //var bodyFormData = document.getElementById("myFormElement");
    const location = new FormData();
    location.set("id",1)
    location.set("content", "bu")
    
    axios({
        method: "post",
        url: "/prueba",
        data: bodyFormData,
      })
    .then(function (response) {
          //handle success
          console.log(response);
    })
    .catch(function (response) {
          //handle error
          console.log(response);
    });
    
}

function checkShooter(){

}

window.onload = function (){
    console.log("Iniciando axios");
    const instance = axios.create({
        baseURL: 'localhost:8080',
        timeout: 1000,
        headers: {'X-Custom-Header': 'foobar'}
      });
}
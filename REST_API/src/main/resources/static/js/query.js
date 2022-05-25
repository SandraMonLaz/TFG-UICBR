
function EnviarFormulario() {
    //Si estamos en plataforma 2d
    var query;
    if(document.getElementById("tipo-juego-plat").checked){
        query =  checkPlatfrom();
    }
}

function checkPlatfrom(){
    var myFormElement = document.getElementById("myFormElement");
    var formData = new FormData(myFormElement);
    
    //-------Health-------------
    var vida = null;
    if(document.getElementById("opcion-vida").checked){
        vida = {
            importance: formData.get("imp-vida"),
            rangeType:  formData.get("rango-vida")
        }
    }
    //----------Time-------------
    var tiempo = null;
    if(document.getElementById("opcion-tiempo").checked){
        tiempo = {
            importance: formData.get("imp-tiempo"),
            use:  formData.get("tipo-tiempo")
        }
    }
    //----Porcentaje de Nivel----
    var porcentajeNivel = null;
    if(document.getElementById("opcion-porcentaje-lvl").checked){
        porcentajeNivel = {
            importance: formData.get("imp-porcentaje-lvl"),
            rangeType:  formData.get("rango-porcentaje-lvl"),
            progressType: formData.get("tipo-porcentaje-lvl")
        }   
    }
    //-------Coleccionable-------
    var coleccionable = null;
    if(document.getElementById("opcion-coleccionable").checked){
        coleccionable = {
            importance: formData.get("imp-coleccionable")
        }   
    }
    //-------Puntuación----------
    var puntuacion = null;
    if(document.getElementById("opcion-puntuacion").checked){
        puntuacion = {
            importance: formData.get("imp-puntuacion")
        }   
    }
    //-------InfoPersonaje-------
    var personajeInfo = null;
    if(document.getElementById("opcion-personajeInfo").checked){
        personajeInfo = {
            importance: formData.get("imp-personajeInfo")
        }   
    }
    //-----Progreso Personaje-----
    var progresoPersonaje = null;
    if(document.getElementById("opcion-progresoPersonaje").checked){
        progresoPersonaje = {
            importance: formData.get("imp-progresoPersonaje"),
            rangeType:  formData.get("rango-progresoPersonaje"),
        }   
    }
    //-----------Armas------------
    var armas = null;
    if(document.getElementById("opcion-armas").checked){
        armas = {
            importance: formData.get("imp-armas"),
            nWeapons: formData.get("numero-armas"),
            use:  formData.get("uso-arma")
        }   
    }
    //--------Habilidades-----------
    var habilidades = null;
    if(document.getElementById("opcion-habilidades").checked){
        habilidades = {
            importance: formData.get("imp-habilidades"),
            nWeapons: formData.get("numero-habilidades"),
            use:  formData.get("uso-habilidades")
        }   
    }
    //-------Health-------------
    var escudo = null;
    if(document.getElementById("opcion-escudo").checked){
        escudo = {
            importance: formData.get("imp-escudo"),
            rangeType:  formData.get("rango-escudo")
        }
    }
    var bodyFormData = {      
        characterinfo: personajeInfo,
        characterProgress: progresoPersonaje,
        collectable: coleccionable,
        abilities: habilidades,
        health : vida,
        levelProgress: porcentajeNivel,
        score: puntuacion,
        shields: escudo,
        time: tiempo,
        weapons: armas
    }
    
    console.log(bodyFormData)

    axios({
        method: "post",
        url: "/platform",
        data: bodyFormData,
      })
    .then(function (response) {
          //handle success
          download(JSON.stringify(response.data,undefined,2), "yourfile.json", "text/plain");
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

function download(content, fileName, contentType) {
    const a = document.createElement("a");
    const file = new Blob([content], { type: contentType });
    a.href = URL.createObjectURL(file);
    a.download = fileName;
    a.click();
  }
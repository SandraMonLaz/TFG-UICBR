
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
    if(document.getElementById("opcion-vida").checked){

    }
}

function checkShooter(){

}
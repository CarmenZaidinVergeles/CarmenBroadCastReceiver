(function(){
    var boton, texto, divtexto;
    boton= document.getElementById("boton");
    texto = document.getElementById("texto");
    divtexto = document.getElementById("divtexto");

/*
    boton.addEventListener("click", function(){
       texto.innerHTML=(InterfazAndroid.recibirValorDeJavascript(valor)).value;
    });
    var datos = InterfazAndroid.enviarValorAJavascript();
*/

     boton.addEventListener("click", function(){
           InterfazAndroid.recibirValorDeJavascript(texto.value);
        });


// Formas de crear funciones en javascript, ninguna de estas funciones pueden ser llamadas a android si se declaran dentro de una funcion.
     function f(x){}
     var g = function(x){}

})();

var recibirValorAndroid = function(){
    var texto = document.getElementById("texto");
    texto.value=InterfazAndroid.enviarValorAJavascript();
}

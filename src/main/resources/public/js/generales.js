function includeHTML(){
    var z, i, elmnt, file, xhttp;
    /* Loop through a collection of all HTML elements: */
    z = document.getElementsByTagName("*");
    for (i = 0; i < z.length; i++) {
        elmnt = z[i];
        /*search for elements with a certain atrribute:*/
        file = elmnt.getAttribute("w3-include-html");
        if (file) {
            /* Make an HTTP request using the attribute value as the file name: */
            xhttp = new XMLHttpRequest();
            xhttp.onreadystatechange = function() {
                if (this.readyState == 4) {
                    if (this.status == 200) {elmnt.innerHTML = this.responseText;}
                    if (this.status == 404) {elmnt.innerHTML = "Page not found.";}
                    /* Remove the attribute, and call this function once more: */
                    elmnt.removeAttribute("w3-include-html");
                    includeHTML();
                }
            }
            xhttp.open("GET", file, true);
            xhttp.send();
            /* Exit the function: */
            return;
        }
    }
}
$(document).ready(function(){
    $("#inputUsuario").on("keyup", function() {
        var value = $(this).val().toLowerCase();
        $("#tabla tr").filter(function() {
            $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
        });
    });
});

function confirmarEliminacion(id){
    if (confirm("¿Estas seguro que deseas eliminar esta mascota?")){
        eliminarMascota(id);
    }
}

function eliminarMascota(id){
    $.ajax({
        type: "DELETE",
        url: "/listado-mascotas/" +id,
        success: function(result){
            location.reload(true);
        }
    });
}

function cerrarModal(){
    document.getElementsByClassName("modal")[0].style.display = 'none';
}

function aprobarPublicacion(id){
    $.ajax({
        type: "PUT",
        url: "/detalle-para-aprobar/"+ id,
        success: function(result){
            location.href="/publicaciones-voluntario"
        }
    });


}

function desaprobarPublicacion(id){
    $.ajax({
        type: "PUT",
        url: "/detalle-para-aprobar/"+ id,
        success: function(result){
            location.href="/publicaciones-voluntario"
        }
    });


}

function agregarCaracteristica() {

    var descripcion = document.getElementById('descripcion').value;

    let table = document.getElementById('tablaDeValores');
    let body = table.tBodies.item(0);
    let cantidadDeFilas = body.rows.length;
    let indiceValor = 2;
    var valores = []

    for (var indice=0; indice < cantidadDeFilas; indice++) {
        valores.push(body.rows.item(indice).cells.item(indiceValor).innerHTML);
    }

    var url = "/caracteristica"

    var data = {}

    var valoresZip = ""

    valores.forEach(
        value => {
            valoresZip = valoresZip + value + ";"
        }
    )

    data.descripcion = descripcion
    data.valores = valoresZip

    // Send the data using post
    $.post({
        url: url,
        data: data,
        success: function(result){
            goBack()
        }
    });
}

function modificarCaracteristica(id) {

    var descripcion = document.getElementById('descripcion').value;

    let table = document.getElementById('tablaDeValores');
    let body = table.tBodies.item(0);
    let cantidadDeFilas = body.rows.length;
    let indiceValor = 2;
    var valores = []

    for (var indice=0; indice < cantidadDeFilas; indice++) {
        valores.push(body.rows.item(indice).cells.item(indiceValor).innerHTML);
    }

    var url = "/editar-caracteristica/"+id.toString()

    var data = {}

    var valoresZip = ""

    valores.forEach(
        value => {
            valoresZip = valoresZip + value + ";"
        }
    )

    data.descripcion = descripcion
    data.valores = valoresZip

    $.post({
        url: url,
        data: data,
        success: function(result){
            goBack()
        }
    });
}

function confirmarEliminacionDeCaracteristica(id){
    if (confirm("¿Estás seguro de que deseas eliminar esta característica?")){
        eliminarCaracteristica(id);
    }
}

function eliminarCaracteristica(id){
    $.ajax({
        type: "DELETE",
        url: "/caracteristica/"+id,
        success: function(result){
            location.reload();
        }
    });
}

function agregarPregunta() {

    var descripcion = document.getElementById('descripcion').value;

    let table = document.getElementById('tablaDeValores');
    let body = table.tBodies.item(0);
    let cantidadDeFilas = body.rows.length;
    let indiceValor = 2;
    var valores = []

    for (var indice=0; indice < cantidadDeFilas; indice++) {
        valores.push(body.rows.item(indice).cells.item(indiceValor).innerHTML);
    }

    var url = "/pregunta"

    var data = {}

    var valoresZip = ""

    valores.forEach(
        value => {
            valoresZip = valoresZip + value + ";"
        }
    )

    data.descripcion = descripcion
    data.valores = valoresZip

    // Send the data using post
    $.post({
        url: url,
        data: data,
        success: function(result){
            goBack()
        }
    });
}

function modificarPregunta(id) {

    var descripcion = document.getElementById('descripcion').value;

    let table = document.getElementById('tablaDeValores');
    let body = table.tBodies.item(0);
    let cantidadDeFilas = body.rows.length;
    let indiceValor = 2;
    var valores = []

    for (var indice=0; indice < cantidadDeFilas; indice++) {
        valores.push(body.rows.item(indice).cells.item(indiceValor).innerHTML);
    }

    var url = "/editar-pregunta/"+id.toString()

    var data = {}

    var valoresZip = ""

    valores.forEach(
        value => {
            valoresZip = valoresZip + value + ";"
        }
    )

    data.descripcion = descripcion
    data.valores = valoresZip

    $.post({
        url: url,
        data: data,
        success: function(result){
            goBack()
        }
    });
}

function confirmarEliminacionDePregunta(id){
    if (confirm("¿Estás seguro de que deseas eliminar esta pregunta de adopción?")){
        eliminarPregunta(id);
    }
}

function eliminarPregunta(id){
    $.ajax({
        type: "DELETE",
        url: "/pregunta/"+id,
        success: function(result){
            location.reload();
        }
    });
}

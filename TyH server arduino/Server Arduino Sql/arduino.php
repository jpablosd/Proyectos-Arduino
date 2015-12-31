<?php
// www.internetdelascosas.cl
// Script para recolectar datos enviados por arduinos conectados a la red
// contacto@internetdelascosas.cl

// Parametros de base de datos



$mysql_servidor = "localhost";
$mysql_base = "variable";
$mysql_usuario = "root";
$mysql_clave = "";

$id  = htmlspecialchars($_GET["id"],ENT_QUOTES);
$nombre = htmlspecialchars($_GET["n"],ENT_QUOTES);
$valor = htmlspecialchars($_GET["v"],ENT_QUOTES);

// Valida que esten presente todos los parametros
if (($id!="") and ($nombre!="") and ($valor!="")) {
	mysql_connect($mysql_servidor,$mysql_usuario,$mysql_clave) or die ("Imposible conectarse al servidor.");
	mysql_select_db($mysql_base) or die("Imposible abrir Base de datos");
	$sql = "insert into variable (fecha, id, nombre, valor) values (NOW(),'$id','$nombre','$valor')";
	mysql_query($sql);
}
?>
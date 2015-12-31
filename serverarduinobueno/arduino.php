<?php
// www.internetdelascosas.cl
// Script para recolectar datos enviados por arduinos conectados a la red
// contacto@internetdelascosas.cl

// Parametros de base de datos



$mysql_servidor = "localhost";
$mysql_base = "variables";
$mysql_usuario = "caw11863";
$mysql_clave = "Dg3EDR2W";

$habitacion  = htmlspecialchars($_GET["habitacion"],ENT_QUOTES);
$sensor = htmlspecialchars($_GET["sensor"],ENT_QUOTES);
$temperatura = htmlspecialchars($_GET["temperatura"],ENT_QUOTES);
$humedad = htmlspecialchars($_GET["humedad"],ENT_QUOTES);


// Valida que esten presente todos los parametros
if ( ($habitacion!="") and ($sensor!="") and ($temperatura!="") and ($humedad!="") ) {
	mysql_connect($mysql_servidor,$mysql_usuario,$mysql_clave) or die ("Imposible conectarse al servidor.");
	mysql_select_db($mysql_base) or die("Imposible abrir Base de datos");
	$sql = "insert into variable (fecha, habitacion, sensor, temperatura, humedad) values (NOW(),'$habitacion','$sensor','$temperatura','$humedad')";
	mysql_query($sql);
}
?>
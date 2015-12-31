//Sample using LiquidCrystal library
#include <LiquidCrystal.h>

#include <dht.h>
 const int dht_dpin = A1;

dht DHT;

// select the pins used on the LCD panel
LiquidCrystal lcd(8, 9, 4, 5, 6, 7);



 //for testing purposes I added a light sensor to gather some data
 const int lightsensor = A2;
 //variable to store the light value
 int lightvalue = 0;
 
 const int buttonPin = 2;
 const int ledPin =  13; 
 
 const int gassensor = A0;
int gasvalue;

int pingPin = 13; //Pin que emite los sonidos, Trig
int entradaPin = 12; //Pin que va a recibir de vuelta la onda (echo)
 

 
void setup()
{
 lcd.begin(16, 2);              // start the library 16char y 2 corridas
  // declare the lightsensor as an INPUT for the Arduino
 pinMode(lightsensor, INPUT);
 pinMode(buttonPin, INPUT); 
 pinMode(dht_dpin, INPUT);
 
 pinMode(gassensor, INPUT);
 
 //Inicializamos los pines como entradas y salidas
  pinMode(pingPin, OUTPUT);
  pinMode(entradaPin, INPUT);

}
 
void loop()
{
 lcd.clear();
 
 lcd.setCursor(0,0); // move cursor to second line "1" and 9 spaces over
 lightvalue = analogRead(lightsensor);
 //Write information to Arduinos serial monitor
 lcd.print("luminosidad: ");
 lcd.print(lightvalue);
 delay(2000);
  gasvalue = analogRead(gassensor);
 lcd.setCursor(0,1);
 lcd.print("gas: ");
 lcd.print(gasvalue);
 delay(2000);
 
  lcd.clear();
 DHT.read11(dht_dpin);
 lcd.setCursor(0,0);
 lcd.print("hum: ");
 lcd.print(DHT.humidity);
 lcd.print("%");
 lcd.setCursor(0,1);
 lcd.print("temp: ");
 lcd.print(DHT.temperature);
 lcd.print("ºC");
 delay(2000);
 
 lcd.clear();
 //Creamos 2 variables, una para la duracion y otra para la distancia
  long duracion, distanciaEnCm;
  
  /*
  Hacemos un pulso bajo-alto-bajo para encender el sensor
  Al encender y apagar esperamos en microsegundos, de esta
  manera enviaremos nuestra primer onda
  */
  digitalWrite(pingPin, LOW); // Envía un pulso bajo
  delayMicroseconds(2);       // Espera dos microsegundos
  digitalWrite(pingPin, HIGH);// Envía un pulso alto
  delayMicroseconds(5);       // Espera 5 microsegundos
  digitalWrite(pingPin, LOW); // Se queda en espera
  
  /*Obtenemos la duracion de tiempo mientras
  el sensor este recibiendo la informacion
  */
  duracion = pulseIn(entradaPin, HIGH);
  
  /*
  Convertimos la duracion del tiempo a distancia
  La velocidad del sonido es de 340metros/segundo que 
  es igual a 29 microsegundos por centimetro es por es
  que vamos a dividir la duracion entre 29. 
  Despues se divide entre 2 porque es el tiempo que viaja
  el sonido de ida y de vuelta, solo queremos un valor pero
  ambos son iguales, es por eso que solo dividimos
  entre 2
  */
  distanciaEnCm = (duracion/29)/2;
  
  
  if(distanciaEnCm < 30){
  //Imprimimos la distancia en consola
  lcd.setCursor(0,0);
  lcd.print("distancia: ");
  lcd.print(distanciaEnCm);
  lcd.print("cm");
  lcd.setCursor(0,1);
  lcd.print("mov detectado");
  }
  else{
  lcd.print("no hay movimiento");
  }
  
  delay(2000);
  
 
 
 
}



    






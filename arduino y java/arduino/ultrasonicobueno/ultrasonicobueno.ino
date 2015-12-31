
#include <ultrasonic.h>
#define ECHO 8        // Pin para recibir el pulso de eco
#define TRIGGER 9 // Pin para enviar el pulso de disparo
#define LED 13
 
unsigned int tiempo, distancia;
 
void setup() {
  Serial.begin(9600); 
  pinMode(ECHO, INPUT);
  pinMode(TRIGGER, OUTPUT);
  pinMode(LED, OUTPUT);
}
 
void loop() {
  digitalWrite(TRIGGER, LOW);
  delayMicroseconds(5);
  digitalWrite(TRIGGER, HIGH);
  delayMicroseconds(10);
  digitalWrite(TRIGGER, LOW);

  // Calcula la distancia midiendo el tiempo del estado alto del pin ECHO
  tiempo = pulseIn(ECHO, HIGH);
  
  // La velocidad del sonido es de 340m/s o 29 microsegundos por centimetro
  distancia= tiempo/58;

  //manda la distancia al monitor serie
  Serial.print(distancia);
  Serial.println(" cm distancia ");

      if(distancia < 10){
        Serial.println("prendo led");
        digitalWrite(LED, HIGH);
      }
      else{
        Serial.print("apago led");
        digitalWrite(LED, LOW);
      }
}


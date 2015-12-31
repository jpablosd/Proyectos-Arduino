#include <dht.h>

#define dht_dpin A0
dht DHT;
int temperatura = 0;
int humedad = 0;

void setup() {
    Serial.begin(9600);

}

void loop() {
  DHT.read11(dht_dpin);
  temperatura = DHT.temperature;
  humedad = DHT.humidity;
    Serial.print("temperatura: ");
  Serial.println(temperatura);
  Serial.print("humedad: ");
  Serial.println(humedad);
   delay(500);
}

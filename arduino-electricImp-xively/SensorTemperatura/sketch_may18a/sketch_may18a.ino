#include <dht.h>
//Adding the library for the software serial
#include <SoftwareSerial.h>
//defining the Pins for TX and RX serial communication
SoftwareSerial electricimpSerial(9,10);

#define dht_dpin A2 //no ; here. Set equal to channel sensor is on

dht DHT;

void setup(){
  Serial.begin(9600);
   //open software serial port so values can be passed on to the electric imp
 electricimpSerial.begin(9600);
  delay(300);//Let system settle
  //accessing sensor
}//end "setup()"

void loop(){
  //This is the "heart" of the program.
  DHT.read11(dht_dpin);

    Serial.print("Current Humidity = ");
    Serial.print(DHT.humidity);
    Serial.print(" %  ");
    Serial.print("Temperature = ");
    Serial.print(DHT.temperature); 
    Serial.println(" C ");
     electricimpSerial.print(DHT.temperature);
  delay(10000);//Don't try to access too frequently... in theory
  //should be once per two seconds, fastest,
  //but seems to work after 0.8 second.
}// end loop()

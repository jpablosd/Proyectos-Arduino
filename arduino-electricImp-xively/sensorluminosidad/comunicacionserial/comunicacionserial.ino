//Adding the library for the software serial
 #include <SoftwareSerial.h>
 //defining the Pins for TX and RX serial communication
 SoftwareSerial electricimpSerial(9,10);
 
 
 //for testing purposes I added a light sensor to gather some data
 const int lightsensor = A2;
 //variable to store the light value
 int lightvalue = 0;
 
 void setup() {
 // put your setup code here, to run once:
 //open Arduino serial port so values are shown in the Arduino serial monitor
 Serial.begin(9600);
 //open software serial port so values can be passed on to the electric imp
 electricimpSerial.begin(9600);
 // declare the lightsensor as an INPUT for the Arduino
 pinMode(lightsensor, INPUT);
 }
 
 void loop() {
 // for testing purposes read the value ofarduin the lightsensor
 lightvalue = analogRead(lightsensor);
 //Write information to Arduinos serial monitor
 Serial.print("Light level: ");
 Serial.println(lightvalue);
 
 //Write information to electric imp
 //electricimpSerial.print("Ligh level: ");
 electricimpSerial.print(lightvalue);
 // Wait a bit
 delay(2000);
 }

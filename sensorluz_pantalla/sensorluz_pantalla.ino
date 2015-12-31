//Sample using LiquidCrystal library
#include <LiquidCrystal.h>

// select the pins used on the LCD panel
LiquidCrystal lcd(8, 9, 4, 5, 6, 7);

 //for testing purposes I added a light sensor to gather some data
 const int lightsensor = A2;
 //variable to store the light value
 int lightvalue = 0;
 
void setup()
{
 lcd.begin(16, 2);              // start the library 16char y 2 corridas
  // declare the lightsensor as an INPUT for the Arduino
 pinMode(lightsensor, INPUT);

}
 
void loop()
{
 lcd.setCursor(0,0); // move cursor to second line "1" and 9 spaces over
 lightvalue = analogRead(lightsensor);
 //Write information to Arduinos serial monitor
 lcd.print(lightvalue);
 delay(2000);
 

}


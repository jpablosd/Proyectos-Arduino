//Sample using LiquidCrystal library
#include <LiquidCrystal.h>

// select the pins used on the LCD panel
LiquidCrystal lcd(8, 9, 4, 5, 6, 7);

void setup()
{
 lcd.begin(16, 2);              // start the library 16char y 2 corridas
 lcd.setCursor(0,0);
 lcd.print(""); // print a simple message
}
 
void loop()
{
 lcd.setCursor(0,1); // move cursor to second line "1" and 9 spaces over
 
 
 //lcd.print(millis()/1000);      // display seconds elapsed since power-up

 lcd.print("hola arduino");
 

}


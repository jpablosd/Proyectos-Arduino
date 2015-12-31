#include <Wire.h>
  
#include <LiquidCrystal_I2C.h>
 
 /*
 En arduino mega se conecta como dice en la pantalla, vcc, gnd, sda y scl
 En arduino uno se conecta vcc y gnd normal, luego para la coneccion 
 SDA se conecta en el pin A4
 SCL se conecta en el pin A5
 */
 
LiquidCrystal_I2C lcd(0x27,16,2); // 0x27 es la direccion del LCD 16x2
 
void setup(){
    lcd.init(); 
    lcd.backlight(); //enciende la iluminacion
    lcd.setCursor(0, 0);
    lcd.print("Probando i2c");
}
 
void loop(){
  
    delay(1000);
}


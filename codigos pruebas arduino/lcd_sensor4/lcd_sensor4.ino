#include <LiquidCrystal.h>
#include <DHT22.h>
#define DHT22_PIN 2
int tem=0;
int hum=0;
DHT22 myDHT22(DHT22_PIN);

LiquidCrystal lcd(3, 5, 6, 7, 8, 9);

void setup() {
  Serial.begin(9600);
  lcd.begin(16, 2);
 }

void loop() {
  DHT22_ERROR_t errorCode;
  delay(2000);
  errorCode = myDHT22.readData();
  if(errorCode==DHT_ERROR_NONE)
  {   
    tem=myDHT22.getTemperatureC();
    hum=myDHT22.getHumidity();
      lcd.setCursor(0, 0);
      lcd.print("Temp: ");
      lcd.print(tem);
      lcd.print(" C");
      lcd.setCursor(0, 1);
      lcd.print("Hume: ");
      lcd.print(hum);
      lcd.println(" %"); 
      Serial.print("Temperaura: ");
      Serial.print(tem);
      Serial.print("C  / Humedad: ");
      Serial.print(hum);
      Serial.println("%");       
  }else{
     Serial.print("Error");
  }
  
}

#include <LiquidCrystal.h>
#include <DHT22.h>
#define DHT22_PIN 2
int tem=0;
int hum=0;
int val=0;
int ledPin = 13;
DHT22 myDHT22(DHT22_PIN);
LiquidCrystal lcd(3, 5, 6, 7, 8, 9);

void setup() {
  Serial.begin(19200);
  lcd.begin(16, 2);
}

void loop() {
  val = Serial.read(); 
  DHT22_ERROR_t errorCode; 
  errorCode = myDHT22.readData();
  if(errorCode==DHT_ERROR_NONE){  
    tem=myDHT22.getTemperatureC();
    hum=myDHT22.getHumidity();
    pantalla();  
    delay(500);
  }else{
     //Serial.println("Error");
  } 
  if (val !=-1){
    if(val=='1'){
       Serial.println(tem);
     }else if(val=='2'){
       Serial.println(hum);
    }else if(val=='3'){
      //Serial.println(t2);
    }
  }
}

void pantalla(){
  lcd.setCursor(0, 0);
  lcd.print("Temp: ");
  lcd.print(tem);
  lcd.print(" C");
  lcd.setCursor(0, 1);
  lcd.print("Hume: ");
  lcd.print(hum);
  lcd.println(" %");     
}

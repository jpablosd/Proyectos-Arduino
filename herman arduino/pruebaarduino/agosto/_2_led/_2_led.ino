const int LED_Rojo=12;
const int LED_Azul=13;
int Byte_Entrada=0;

void setup(){
  Serial.begin(9600);
  pinMode(LED_Rojo, OUTPUT);
    pinMode(LED_Azul, OUTPUT);
    digitalWrite(LED_Rojo,LOW);
    digitalWrite(LED_Azul,LOW);
}

void loop(){
  if(Serial.available()>0){
    Byte_Entrada=Serial.read();
      if(Byte_Entrada='0'){
         digitalWrite(LED_Rojo,LOW);
      }else if(Byte_Entrada='1'){
        digitalWrite(LED_Rojo,HIGH);
      }else if(Byte_Entrada='2'){
        digitalWrite(LED_Azul,LOW);
      }else if(Byte_Entrada='3'){
        digitalWrite(LED_Azul,HIGH);
      }
  }
}

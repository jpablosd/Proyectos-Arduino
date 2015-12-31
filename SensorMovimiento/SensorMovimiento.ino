/*
	Implementación básica de un sensor de movimiento (PIR)
*/
 
const int buttonPin = 2;
const int ledPin =  13; 
 
int buttonState = 0;
 
void setup() {
  pinMode(ledPin, OUTPUT);      
  pinMode(buttonPin, INPUT);     
  Serial.begin(9600);
}
 
void loop(){
  buttonState = digitalRead(buttonPin);
  if (buttonState == HIGH) {     
    digitalWrite(ledPin, HIGH);  
  } 
  else {
    digitalWrite(ledPin, LOW); 
  }
  
  Serial.println(buttonState);
  
  delay(500);
}

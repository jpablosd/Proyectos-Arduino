int redLed = 5;
int greenLed = 6;
int yellowLed = 7;
int number_in = 0;

void setup() {
  pinMode(redLed, OUTPUT);
  pinMode(greenLed, OUTPUT);
  pinMode(yellowLed, OUTPUT);
  Serial.begin(9600);
  
  digitalWrite(redLed, LOW);
  digitalWrite(greenLed, LOW);
  digitalWrite(yellowLed, LOW);
}

void loop() {
  if (Serial.available() > 0) {
    number_in = Serial.read();
  }
 
  if (number_in == 55) { //7 == 55
    digitalWrite(redLed, HIGH);
    digitalWrite(greenLed, LOW);
    digitalWrite(yellowLed, LOW);
  }
  if (number_in == 56) {
    digitalWrite(redLed, LOW);
    digitalWrite(greenLed, HIGH);
    digitalWrite(yellowLed, LOW);
  }
  if (number_in == 57) {
    digitalWrite(redLed, LOW);
    digitalWrite(greenLed, LOW);
    digitalWrite(yellowLed, HIGH);
  }
  
  number_in = 0;
}


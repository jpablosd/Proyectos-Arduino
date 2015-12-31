//controlador de motor

int motorPin = 6;
void setup()
{
pinMode(motorPin, OUTPUT);
}
void loop()
{
int onTime = 2500;
int offTime = 1000;

digitalWrite(motorPin, HIGH);
delay (onTime);
digitalWrite(motorPin, LOW);
delay (offTime);
}


//controlador de motor

int adelante = 6;
int atras = 9;

void setup()
{
pinMode(adelante, OUTPUT);
pinMode(atras, OUTPUT);

}

void loop()
{

  int onTime = 2500;
  int offTime = 1000;

  digitalWrite(adelante, HIGH);
  delay (onTime);
  digitalWrite(adelante, LOW);
  delay (offTime);

  digitalWrite(atras, HIGH);
  delay (onTime);
  digitalWrite(atras, LOW);
  delay (offTime);

}


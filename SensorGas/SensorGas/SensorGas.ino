

int gas = 0;


void setup() {
    Serial.begin(9600);

}

void loop() {
   gas = analogRead(1); //imprime el valor de gas de 20ppm a 3000ppm (partes por millon de gas
   Serial.print("gas: ");
   Serial.println(gas);
   delay(500);
}

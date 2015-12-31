int reset = 7;
int bulb1 = 2;
int bulb2 = 3;
int bulb3 = 4;
int bulb4 = 5;
int bulb5 = 8;
int fan   = 9;

boolean isCommand = false;
String command="";

void setup(){
  pinMode(reset,OUTPUT);
  pinMode(bulb1,OUTPUT);
  pinMode(bulb2,OUTPUT);
  pinMode(bulb3,OUTPUT);
  pinMode(bulb4,OUTPUT);
  pinMode(bulb5,OUTPUT);
  pinMode(fan,OUTPUT);
  //resest the WT-11 module
  digitalWrite(reset,HIGH);
  digitalWrite(reset,LOW);
   
  Serial.begin(115200);
  Serial.println("SET BT PAGEMODE 3 2000 1");
  Serial.println("SET BT NAME ARDUINOBT2");
  Serial.println("SET BT ROLE 0 f 7d00");
  Serial.println("SET CONTROL ECHO 0");
  Serial.println("SET BT AUTH * 12345");
  Serial.println("SET CONTROL ESCAPE - 00 1");
  Serial.println("SET CONTROL BAUD 115200,8n1");
}

void loop(){
  //Check if ther's anything available on the serial port
  if (Serial.available()==1){
    //we do read the character
    char character = Serial.read();
    //if we're not recieving a command we check if the character read is the beginning of a command (@)
    if (! isCommand) {
      if (character=='@'){
        command="@";
        isCommand=true;
      }
    }else{
      command=command + character;
      if (character=='\r'){
        delay(700);
        isCommand=false;//Stop recieving the command
        //Here begins the code section to describe the behaviour of the command
        //NOTE: We cannot use a 'switch' this far because it only evaluates 'int' and 'char'
             if (command=="@ST\r") {String b1stat;String b2stat;String b3stat;String b4stat;String b5stat;String fstat;
               if (digitalRead(bulb1)>0) b1stat="1"; else b1stat="0";
               if (digitalRead(bulb2)>0) b2stat="1"; else b2stat="0";
               if (digitalRead(bulb3)>0) b3stat="1"; else b3stat="0";
               if (digitalRead(bulb4)>0) b4stat="1"; else b4stat="0";
               if (digitalRead(bulb5)>0) b5stat="1"; else b5stat="0";
               if (digitalRead(fan)>0) fstat="1"; else fstat="0";
               Serial.println("#7#" + b1stat + b2stat + b3stat + b4stat + b5stat + fstat + '\r');
                                                     }
        else if (command=="@10\r") {digitalWrite(bulb1,LOW); Serial.println("#7#OK1OFF\r");} //Switch-off bulb1
        else if (command=="@11\r") {digitalWrite(bulb1,HIGH);Serial.println("#6#OK1ON\r");}//Switch-on bulb1
        else if (command=="@20\r") {digitalWrite(bulb2,LOW); Serial.println("#7#OK2OFF\r");}//Switch-off bulb2
        else if (command=="@21\r") {digitalWrite(bulb2,HIGH);Serial.println("#6#OK2ON\r");}//Switch-on bulb2
        else if (command=="@30\r") {digitalWrite(bulb3,LOW); Serial.println("#7#OK3OFF\r");}//Switch-off bulb3
        else if (command=="@31\r") {digitalWrite(bulb3,HIGH);Serial.println("#6#OK3ON\r");}//Switch-on bulb3
        else if (command=="@40\r") {digitalWrite(bulb4,LOW); Serial.println("#7#OK4OFF\r");}//Switch-off bulb4
        else if (command=="@41\r") {digitalWrite(bulb4,HIGH);Serial.println("#6#OK4ON\r");}//Switch-on bulb4
        else if (command=="@50\r") {digitalWrite(bulb5,LOW); Serial.println("#7#OK5OFF\r");}//Switch-off bulb5
        else if (command=="@51\r") {digitalWrite(bulb5,HIGH);Serial.println("#6#OK5ON\r");}//Switch-on bulb5
        else if (command=="@F0\r") {digitalWrite(fan,LOW);Serial.println("#7#OKFOFF\r");}//Switch-off fan
        else if (command=="@F1\r") {digitalWrite(fan,HIGH);Serial.println("#6#OKFON\r");}//Switch-on fan
        else if (command=="@P0\r") {digitalWrite(bulb1,LOW);delay(100);
                                    digitalWrite(bulb2,LOW);delay(100);
                                    digitalWrite(bulb3,LOW);delay(100);
                                    digitalWrite(bulb4,LOW);delay(100);
                                    digitalWrite(bulb5,LOW);delay(100);
                                    digitalWrite(fan,LOW);delay(100);
                                    Serial.println("#7#OKPOFF\r");}//Switch-on EVERYTHING!
        else if (command=="@P1\r") {digitalWrite(bulb1,HIGH);delay(100);
                                    digitalWrite(bulb2,HIGH);delay(100);
                                    digitalWrite(bulb3,HIGH);delay(100);
                                    digitalWrite(bulb4,HIGH);delay(100);
                                    digitalWrite(bulb5,HIGH);delay(100);
                                    digitalWrite(fan,HIGH);delay(100);
                                    Serial.println("#6#OKPON\r");}//Switch-off EVERYTHING!                                    
        else Serial.println("I Don't understand:" + command);//Protocol doesn't recognize the commmand
        //End executing command
        //Reset the command-string
        command="";
      }
    }
  }
}


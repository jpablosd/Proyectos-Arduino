// Transmit data between UART and Input/OutputPorts on the impee
// by: Jim Lindblom
//     SparkFun Electronics
// date: September 26, 2012
// license: BeerWare
//          Please use, reuse, and modify this code as you need.
//          We hope it saves you some time, or helps you learn something!
//          If you find it handy, and we meet some day, you can buy me a beer or iced tea in return.
// impeeIn will override the InputPort class. 
// Whenever data is received to the impee, we'll jump into the set(c) function defined within
class impeeIn extends InputPort
{
    name = "UART Out";
    type = "string";

    // This function takes whatever character was sent to the impee
    // and sends it out over the UART5/7. We'll also toggle the txLed
    function set(c)
    {
        hardware.uart57.write(c);
    }
}

//local impeeInput = impeeIn();  // assign impeeIn class to the impeeInput
//local impeeOutput = OutputPort("UART In", "string");  // set impeeOutput as a string

/*function initUart()
{
    hardware.configure(UART_57);    // Using UART on pins 5 and 7
    hardware.uart57.configure(9600, 8, PARITY_NONE, 1, NO_CTSRTS); // 19200 baud worked well, no parity, 1 stop bit, 8 data bits
}*/

// This is our UART polling function. We'll call it once at the beginning of the program,
// then it calls itself every 10us. If there is data in the UART57 buffer, this will read
// as much of it as it can, and send it out of the impee's outputPort.
function pollUart()
{
    imp.wakeup(0.1, pollUart.bindenv(this));    // schedule the next poll in 10us
    local bcnt = 0;
    local s = "";
    local byte = hardware.uart57.read();    // read the UART buffer
    // This will return -1 if there is no data to be read.
    while (byte != -1)  // otherwise, we keep reading until there is no data to be read.
    {
        s+=byte.tochar();
//      s+=byte;
        bcnt++;
        //  server.log(format("%c", byte)); // send the character out to the server log. Optional, great for debugging
        // impeeOutput.set(byte);  // send the valid character out the impee's outputPort
        byte = hardware.uart57.read();  // read from the UART buffer again (not sure if it's a valid character yet)
        // server.show(byte);
    }
    
    if (bcnt > 0){
//        server.show("String received");
        server.log(s);
        agent.send("SendToXively", {id = "pulsador", value = s});
        
        //impeeOutput.set(s);
    } 
}
// This is where our program actually starts! Previous stuff was all function and variable declaration.
// This'll configure our impee. It's name is "UartCrossAir", and it has both an input and output to be connected:
//imp.configure("UartCommunication", [impeeInput], [impeeOutput]);

//impeeIn();
OutputPort("UART In", "string");  // set impeeOutput as a string
hardware.configure(UART_57);    // Using UART on pins 5 and 7
hardware.uart57.configure(9600, 8, PARITY_NONE, 1, NO_CTSRTS); // 19200 baud worked well, no parity, 1 stop bit, 8 data bits


//initUart(); // Initialize the UART, called just once
pollUart(); // start the UART polling, this function continues to call itself
// From here, the main functions are at play:
//      1. We'll be calling pollUart every 10us. If data is sent from the UART, we'll send out out of the impee.
//      2. If data is sent into the impee, we'll jump into the set function in the InputPort.
//
// The end
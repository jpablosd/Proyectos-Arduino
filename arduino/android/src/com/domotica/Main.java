package com.domotica;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Main extends Activity implements OnClickListener{
	private static final int REQUEST_ENABLE_BT = 0;
	
	private Button button_scaner;
	private String statusBotonTodo="ENCENDIDO";
	private ImageView botonTodo;
	private ImageView bulb1Image;
	private ImageView bulb2Image;
	private ImageView bulb3Image;
	private ImageView bulb4Image;
	private ImageView bulb5Image;
	private ImageView fanImage;

	private ImageView bluetoothIndicator;
	private TextView bluetoothStatus;
	private View arduinoLayout;
	
	private BluetoothAdapter mBtAdapter;
	private BluetoothConnection btC;
	
	private String macArduinoBT2="";
	private int[] boardStatus = {0,0,0,0,0,0};
	
	private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context arg0, Intent arg1) {
          String action = arg1.getAction();
          if (BluetoothDevice.ACTION_FOUND.equals(action)) {    
        	  //Detecta evento de descubrimiento de dispositivo.
              BluetoothDevice device = arg1.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
        	  if (device.getName().contains("ARDUINOBT2")==true) macArduinoBT2=device.getAddress();
        	  Toast.makeText(getApplicationContext(), device.getName() + " " + device.getAddress(), Toast.LENGTH_LONG).show();
          }else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {    
        	  //Detecta evento de fin de búsqueda de dispositivos.
                  Toast toast = Toast.makeText(getApplicationContext(), "Fin de búsqueda", Toast.LENGTH_SHORT);
                  toast.show();
                  button_scaner.setText(getResources().getString(R.string.scan));
                  if (macArduinoBT2!="")
                	  if (tryConnectArduino()==true){
                		  arduinoLayout.setVisibility(0);//Hacemos visible el layout del comando.
                		  String status = sendCommand("@ST");//preguntamos por el estado de los elementos de la placa
                		  for (int i = 0;i<status.substring(1).length();i++){
                	    		boardStatus[i] = Character.digit(status.substring(1).charAt(i), 10);
                		  }
                		  updateImages();
                	  }
          }
        }
	};
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.main);
        
        this.botonTodo = (ImageView)findViewById(R.id.botonTodo);
        this.botonTodo.setOnClickListener(this);
        this.bulb1Image = (ImageView)findViewById(R.id.bulb1);
        this.bulb1Image.setOnClickListener(this);
        this.bulb2Image = (ImageView)findViewById(R.id.bulb2);
        this.bulb2Image.setOnClickListener(this);
        this.bulb3Image = (ImageView)findViewById(R.id.bulb3);
        this.bulb3Image.setOnClickListener(this);
        this.bulb4Image = (ImageView)findViewById(R.id.bulb4);
        this.bulb4Image.setOnClickListener(this);
        this.bulb5Image = (ImageView)findViewById(R.id.bulb5);
        this.bulb5Image.setOnClickListener(this);
        this.fanImage = (ImageView)findViewById(R.id.fan);
        this.fanImage.setOnClickListener(this);
        
        
        this.button_scaner = (Button)findViewById(R.id.button1);
        this.button_scaner.setOnClickListener(this);
        
        this.bluetoothIndicator = (ImageView)findViewById(R.id.btIndicator);
        this.bluetoothStatus = (TextView)findViewById(R.id.connectedStatus);
        
        this.arduinoLayout= (View)findViewById(R.id.linearLayout2);
        
        //Comprueba la conectividad del bluetooth para actualizar el icono.
    	mBtAdapter = BluetoothAdapter.getDefaultAdapter();  
        if ((mBtAdapter == null) || (!mBtAdapter.isEnabled())){
        	bluetoothIndicator.setImageDrawable(getResources().getDrawable(R.drawable.bluetooth_disc));
        	bluetoothStatus.setText(getResources().getString(R.string.bt_disconnected));
        }
        
    }
    
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		String response="";
		switch(v.getId()){
			case (R.id.button1):
				startScanner();
				break;
			case(R.id.botonTodo):
				if (statusBotonTodo=="ENCENDIDO"){
					response=sendCommand("@P1");
					if (response.compareTo("#OKPON")==0) 
						for (int i = 0;i<boardStatus.length;i++)
							boardStatus[i]=1;
					this.botonTodo.setImageDrawable(getResources().getDrawable(R.drawable.apagado));
					this.statusBotonTodo="APAGADO";
				}else{
					response=sendCommand("@P0");
					if (response.compareTo("#OKPOFF")==0) 
						for (int i = 0;i<boardStatus.length;i++)
							boardStatus[i]=0;
					this.botonTodo.setImageDrawable(getResources().getDrawable(R.drawable.encendido));
					this.statusBotonTodo="ENCENDIDO";
				}
				break;
			case(R.id.bulb1):
				if (boardStatus[0]==0){
					response=sendCommand("@11");
					{if (response.compareTo("#OK1ON")==0) 
						boardStatus[0]=1;}}
				else{
					response=sendCommand("@10");
					{if (response.compareTo("#OK1OFF")==0) 
						boardStatus[0]=0;}}
				break;
			case(R.id.bulb2):
				if (boardStatus[1]==0){
					response=sendCommand("@21");
					{if (response.compareTo("#OK2ON")==0)  
						boardStatus[1]=1;}}
				else{
					response=sendCommand("@20");
					{if (response.compareTo("#OK2OFF")==0)  
						boardStatus[1]=0;}}
				break;
			case(R.id.bulb3):
				if (boardStatus[2]==0){
					response=sendCommand("@31");
					{if (response.compareTo("#OK3ON")==0)  
						boardStatus[2]=1;}}
				else{
					response=sendCommand("@30");
					{if (response.compareTo("#OK3OFF")==0)  
						boardStatus[2]=0;}}
				break;
			case(R.id.bulb4):
				if (boardStatus[3]==0){
					response=sendCommand("@41");
					{if (response.compareTo("#OK4ON")==0)  
						boardStatus[3]=1;}}
				else{
					response=sendCommand("@40");
					{if (response.compareTo("#OK4OFF")==0)  
						boardStatus[3]=0;}}
				break;
			case(R.id.bulb5):
				if (boardStatus[4]==0){
					response=sendCommand("@51");
					{if (response.compareTo("#OK5ON")==0)  
						boardStatus[4]=1;}}
				else{
					response=sendCommand("@50");
					{if (response.compareTo("#OK5OFF")==0)  
						boardStatus[4]=0;}}
				break;
			case(R.id.fan):
				if (boardStatus[5]==0){
					response=sendCommand("@F1");
					{if (response.compareTo("#OKFON")==0)  
						boardStatus[5]=1;}}
				else{
					response=sendCommand("@F0");
					{if (response.compareTo("#OKFOFF")==0)  
						boardStatus[5]=0;}}
				break;
		}
		updateImages();
	}
	
	public void startScanner(){
		mBtAdapter = BluetoothAdapter.getDefaultAdapter();    //Inicializa manejador Bluetooth. Android no nos da un manejador
        if (mBtAdapter == null) {    //Entra si el dispositivo Bluetooth no está disponible o si no hay dispositivo Bluetooth en nuestro terminal
            Toast.makeText(this, "Bluetooth no está disponible", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        if (!mBtAdapter.isEnabled()) {    //Entra si no es capaz de habilitar el manejador. El blueetooth está apagado
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);    //Lanza un Intent de solicitud de activación del dispositivo Bluetooth.
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);    //Recoge el resultado en REQUEST_ENABLE_BT previamente declarada por nosotros.
        }
        
        while(!mBtAdapter.isEnabled()){}
        
        bluetoothIndicator.setImageDrawable(getResources().getDrawable(R.drawable.bluetooth));
        bluetoothStatus.setText(getResources().getString(R.string.bt_connected));
        
        button_scaner.setText(getResources().getString(R.string.scanning));
        mBtAdapter.startDiscovery();	//Empieza el escaner de dispositivos bluetooth.
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);  //Evento descubrimiento dispositivo
        this.registerReceiver(mReceiver, filter);    //Registra evento en BroadcastReceiver.
        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);    //Evento fin de búsqueda de dispositivo.
        this.registerReceiver(mReceiver, filter);    //Registra evento en BroadcastReceiver.
	}
	
	public boolean tryConnectArduino() {
		btC=new BluetoothConnection(macArduinoBT2);
    	if (btC.doConnect()==BluetoothConnection.BLUETOOTH_CREATION_SUCCESS) 
			return true;
    	else 
    		return false;
	}
	
    private String sendCommand(String command){
    	//show(command);
    	//show("Result: "+ BluetoothConnection.showResult(btC.sendInfo(command)));
    	btC.sendInfo(command);
    	return (btC.receiveInfo());
    }
    
    private void updateImages(){
    	if (boardStatus[0]==0) 
    		this.bulb1Image.setImageDrawable(getResources().getDrawable(R.drawable.b1off));
    	else
    		this.bulb1Image.setImageDrawable(getResources().getDrawable(R.drawable.b1on));
    	if (boardStatus[1]==0) 
    		this.bulb2Image.setImageDrawable(getResources().getDrawable(R.drawable.b2off));
    	else
    		this.bulb2Image.setImageDrawable(getResources().getDrawable(R.drawable.b2on));
    	if (boardStatus[2]==0) 
    		this.bulb3Image.setImageDrawable(getResources().getDrawable(R.drawable.b3off));
    	else
    		this.bulb3Image.setImageDrawable(getResources().getDrawable(R.drawable.b3on));
    	if (boardStatus[3]==0) 
    		this.bulb4Image.setImageDrawable(getResources().getDrawable(R.drawable.b4off));
    	else
    		this.bulb4Image.setImageDrawable(getResources().getDrawable(R.drawable.b4on));
    	if (boardStatus[4]==0) 
    		this.bulb5Image.setImageDrawable(getResources().getDrawable(R.drawable.b5off));
    	else
    		this.bulb5Image.setImageDrawable(getResources().getDrawable(R.drawable.b5on));
    	if (boardStatus[5]==0) 
    		this.fanImage.setImageDrawable(getResources().getDrawable(R.drawable.fan_off));
    	else
    		this.fanImage.setImageDrawable(getResources().getDrawable(R.drawable.fan_on));
    }
    
    /*
    private void show(String message){
    	Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
    */
}
package com.domotica;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;


public class BluetoothConnection {

	public static final int BLUETOOTH_NOT_AVAILABLE = 1;
	public static final int BLUETOOTH_NOT_CONNECTED = 2;
	public static final int BLUETOOTH_SOCKET_CREATION_ERROR = 3;
	public static final int BLUETOOTH_CONNECTION_ERROR = 4;
	public static final int BLUETOOTH_CREATION_SUCCESS = 5;
	public static final int BLUETOOTH_OUTPUTSTREAM_FAIL = 6;
	public static final int BLUETOOTH_SEND_FAIL = 7;
	public static final int BLUETOOTH_SEND_SUCCESS = 8;
	public static final String BLUETOOTH_INPUTSTREAM_FAIL="Error_DataInputStream";
	public static final String BLUETOOTH_RECIEVE_FAIL="Error_Recieve";
	
	
	private BluetoothAdapter mBtAdapter=BluetoothAdapter.getDefaultAdapter();
	private String mac;
	private BluetoothDevice device;
	private BluetoothSocket socket_device;
	private DataOutputStream tmpOut;
	private DataInputStream tmpIn;
	
	public BluetoothConnection(String mac){
		this.mac=mac;		
	}
	
	public Integer doConnect() {
		//INICIALIZACION
		mBtAdapter = BluetoothAdapter.getDefaultAdapter();
		if (mBtAdapter == null) {
			return BLUETOOTH_NOT_AVAILABLE; //Devuelve FALSO en caso de no estar disponible el Bluetooth
		}
		if (!mBtAdapter.isEnabled()) {
			return BLUETOOTH_NOT_CONNECTED;
		}
		//OBTENIENDO DISPOSITIVO
		this.device=mBtAdapter.getRemoteDevice(this.mac);
		
		//CREANDO SOCKET
		try {
			this.socket_device =
				this.device.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
		} catch (IOException e) {
			return BLUETOOTH_SOCKET_CREATION_ERROR;
		}
		
		//CREANDO CONEXION
		try {
			this.socket_device.connect();
		} catch (IOException e) {
			try {
				this.socket_device.close();
			} catch (IOException e2) {
				return BLUETOOTH_CONNECTION_ERROR;
			}
		}
		return BLUETOOTH_CREATION_SUCCESS;
	}
	
	public Integer sendInfo(String message) {
		byte[] buffer;
		try {
			tmpOut = new DataOutputStream(this.socket_device.getOutputStream());
		} catch (IOException e) {
			return BLUETOOTH_OUTPUTSTREAM_FAIL;
		}
		try {
			buffer = (message + '\r').getBytes();
			tmpOut.write(buffer);
			tmpOut.flush();
		} catch (IOException e) {
			return BLUETOOTH_SEND_FAIL;
		}
		return BLUETOOTH_SEND_SUCCESS;
	}
	
	public String receiveInfo(){
		try {
			tmpIn = new DataInputStream(this.socket_device.getInputStream());
		} catch (IOException e) {
			return "BLUETOOTH_INPUTSTREAM_FAIL";
		}
		try {
			char readChar=(char)tmpIn.readByte();
			while(readChar!='#'){readChar=(char)tmpIn.readByte();}
			Integer cBytes=Integer.parseInt("" + (char)tmpIn.readByte());
			byte buffer[] = new byte[cBytes];
			tmpIn.read(buffer,0,cBytes);
			return new String(buffer);
		} catch (IOException e) {
			return "BLUETOOTH_RECIEVE_FAIL";
		}
	}
	
	public static String showResult(Integer result) {
		switch (result){
			case BLUETOOTH_NOT_AVAILABLE:{return ("BLUETOOTH_NOT_AVAILABLE");}
			case BLUETOOTH_NOT_CONNECTED:{return ("BLUETOOTH_NOT_CONNECTED");}
			case BLUETOOTH_SOCKET_CREATION_ERROR:{return ("BLUETOOTH_SOCKET_CREATION_ERROR");}
			case BLUETOOTH_CONNECTION_ERROR:{return ("BLUETOOTH_CONNECTION_ERROR");}
			case BLUETOOTH_CREATION_SUCCESS:{return ("BLUETOOTH_CREATION_SUCCESS");}
			case BLUETOOTH_OUTPUTSTREAM_FAIL:{return ("BLUETOOTH_OUTPUTSTREAM_FAIL");}
			case BLUETOOTH_SEND_FAIL:{return ("BLUETOOTH_SEND_FAIL");}
			case BLUETOOTH_SEND_SUCCESS:{return ("BLUETOOTH_SEND_SUCCESS");}
			default: {return ("Not understood!");}
		}
	}

}

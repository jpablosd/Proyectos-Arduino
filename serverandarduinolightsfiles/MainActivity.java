package com.example.controlarduinolights;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import android.R.string;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {
	private TextView textView1;
	private String localIP = "192.168.1.66";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		textView1 = (TextView)findViewById(R.id.textView1);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void redButtonAction(View v)
	{
		textView1.setText("connecting");
		
        try{
            Socket s = new Socket(localIP, 9090);
            textView1.setText("connection established");
            
            PrintWriter serverOut = new PrintWriter(s.getOutputStream(), true);
            serverOut.println("red");
            
            BufferedReader serverIn = new BufferedReader(new InputStreamReader(s.getInputStream()));
            String answer = serverIn.readLine();
            textView1.setText(answer);
        }
        catch(IOException io){
        	textView1.setText("client error1");
        }
	}
	
	public void greenButtonAction(View v)
	{
		textView1.setText("connecting");
		
        try{
            Socket s = new Socket(localIP, 9090);
            textView1.setText("connection established");
            
            PrintWriter serverOut = new PrintWriter(s.getOutputStream(), true);
            serverOut.println("green");
            
            BufferedReader serverIn = new BufferedReader(new InputStreamReader(s.getInputStream()));
            String answer = serverIn.readLine();
            textView1.setText(answer);
        }
        catch(IOException io){
        	textView1.setText("client error1");
        }
	}
	
	public void yellowButtonAction(View v)
	{
		textView1.setText("connecting");
		
        try{
            Socket s = new Socket(localIP, 9090);
            textView1.setText("connection established");
            
            PrintWriter serverOut = new PrintWriter(s.getOutputStream(), true);
            serverOut.println("yellow");
            
            BufferedReader serverIn = new BufferedReader(new InputStreamReader(s.getInputStream()));
            String answer = serverIn.readLine();
            textView1.setText(answer);
        }
        catch(IOException io){
        	textView1.setText("client error1");
        }
	}

}

package ledcross;

import cc.arduino.*;
import processing.core.PApplet;
import processing.serial.*;

import java.util.Arrays;

import gnu.io.*;

public class CrossSerialOutput {

	// Arduino arduino;
	Serial myPort;
	PApplet parent;
	CrossPrototyper prototyper;

	boolean isConnected = false;

	boolean isSent = false;
	CrossSerialOutput(PApplet _parent) {
		parent = _parent;
		myPort = new Serial(parent, Serial.list()[0], 9600);
	}

	CrossSerialOutput(PApplet _parent, CrossPrototyper _prototyper) {

		parent = _parent;
		prototyper = _prototyper;

		parent.println("serials : " + Serial.list()[0]);
		myPort = new Serial(parent, Serial.list()[0], 9600);

	}

	public void serialEvent(Serial myPort) {

		//consided using this event handler to dictate the frame rate?
		//the computer is probably much faster than the arduino!?
		
		
		int inByte = myPort.read();
		 parent.println("myPort :" + inByte);

		if (!isConnected) {
			if (inByte == 65) {
				myPort.clear(); // clear the serial port buffer
				isConnected = true; // you've had first contact from the
									// microcontroller
				// myPort.write('A'); // ask for more
				parent.println("IS CONNECTED!!!!!");
			}
		}
	}

	public void update() {
		if (isConnected) {
			// get data from prototyper
		}
	}

	public void display() {
		parent.println("display : " + isConnected) ;
		if (isConnected) {
			// send data to display
			if(!isSent){
			int bankNum = 4;
			
			//serial recieve buffer only holds 64 bytes!
			
			for (int i = 0; i < bankNum; i++) {
				LED[] bank = prototyper.get_bank(i);
				//parent.println("bank :  " + bank + " : " + bank.length);
				byte[] data = new byte[bank.length];
				for(int d = 0; d < data.length; d ++){
					data[d] = bank[d].getCurrentByte();
					//parent.println(bank[d].getCurrentAlpha() + " : " + bank[d].getCurrentByte());
				}
				parent.println("length : " + data.length);
				myPort.write(data);
			}
			isSent = true;
			}
			//parent.println("display!");
			//isConnected = false;
		}
	}

}

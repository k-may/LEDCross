package ledcross;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;

import controlP5.ControlEvent;
import controlP5.ControlP5;
import controlP5.Toggle;
import processing.core.PApplet;

import proxml.*;

public class LEDCross extends PApplet {

	CrossConsole console;
	CrossPrototyper prototyper;

	int totalFrames = 20;
	int frameNum;
	int frameRate = 10;

	ControlP5 control;

	XMLInOut xmlInOut;
	XMLElement data;

	boolean isPlaying = false;

	public void setup() {
		size(1000, 1000);


		control = new ControlP5(this);

		xmlInOut = new XMLInOut(this);
		try{
			xmlInOut.loadElement("frameData.xml"); 
		}catch(Exception e){
			//if the xml file could not be loaded it has to be created
			xmlEvent(new XMLElement("data"));
		}

		prototyper = new CrossPrototyper(this);
		console = new CrossConsole(this, control);

	}


	public void controlEvent(ControlEvent theEvent) {
		println(theEvent.controller().name() + " : " + theEvent.controller().value());
		switch(theEvent.controller().name()){
		case "frame":
			if(!isPlaying){
				frameNum = (int)theEvent.controller().value();
			}
			println("frame : " + console.frameField.getText());
			break;
		case "next":
			if(!isPlaying) frameNum ++;
			break;
		case "previous":
			if(!isPlaying) frameNum --;
			break;
		case "play":
			if((int)theEvent.controller().value() == 1){
				isPlaying = true;
			}else{
				isPlaying = false;
			}
			break;
		case "save":
			//save data
			saveData();
			break;
		}

	}


	public void draw() {
		if(isPlaying){
			frameNum = (this.frameCount%frameRate == 0)?frameNum + 1:frameNum;
		}

		if(frameNum == totalFrames) frameNum = 0;


		update();

		display();
	}


	void update(){
		console.update();
		prototyper.update();
	}


	void display(){
		background(0);
		console.display();
		prototyper.display();

	}

	public void mousePressed(){
		prototyper.mousePressed();
	}

	void saveData(){
		
		int xPos = 0;
		int yPos = 0;

		XMLElement frames = new XMLElement("data");
		data.addChild(frames);
		 XMLElement position = new XMLElement("position");
		 position.addAttribute("xPos",xPos);
		 position.addAttribute("yPos",yPos);
		 frames.addChild(position);
		 XMLElement size = new XMLElement("size");
		 size.addAttribute("Xsize",abs(xPos-mouseX));
		 size.addAttribute("Ysize",abs(yPos-mouseY));
		 frames.addChild(size);
		 xmlInOut.saveElement(data,"file:///C:/Users/kev/workspace/LED_Cross/bin/frameData.xml//");
		 
	}

	public void xmlEvent(proxml.XMLElement element){
		//parse data
		data = element;
		println("data :"+ element);
	}


}

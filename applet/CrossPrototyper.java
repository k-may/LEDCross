package ledcross;

import ledcross.LED;
import processing.core.PApplet;

public class CrossPrototyper implements IView {

	
	LEDCross parent;
	
	LED[] tLED = new LED[30];
	LED[] bLED = new LED[30];
	LED[] lLED = new LED[40];
	LED[] rLED = new LED[40];
	
	LED[]leds = new LED[140];
	
	int _res = 15;


	int xPos = 10;
	int yPos = 10;
	int width;
	int height;
	
	CrossPrototyper(LEDCross _parent){
		parent = _parent;
		
		int i;
		int x =0;
		int y =0;
		int ledCount = 0;
		
		for(i = 0; i < 30; i ++){
			x = ((i%5)*2) +11;
			y = (i/5)*2;
			tLED[i] = new LED(x, y, parent);
			leds[ledCount] = tLED[i];
			ledCount ++;
			bLED[i] = new LED(x, y + 22, parent);
			leds[ledCount] = bLED[i];
			ledCount ++;
		}

		for(i =0; i < 40; i ++){
			x = (i%8)*2;
			y = (i/8)*2 +12;
			rLED[i] = new LED(x, y, parent);
			leds[ledCount] = rLED[i];
			ledCount ++;
			lLED[i] = new LED(x+16, y, parent);
			leds[ledCount] = lLED[i];
			ledCount ++;
		}

		width = (x  + 16)*20 + 20;
		height = (y + 22)*20 + 20;
		
		setPos(xPos, yPos, 20);
	}
	
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		int i;
		for(i = 0; i < tLED.length; i ++){
			tLED[i].update(parent.frameNum);
		}
		for(i = 0; i < bLED.length; i ++){
			bLED[i].update(parent.frameNum);
		}
		for(i = 0; i < rLED.length; i ++){
			rLED[i].update(parent.frameNum);
		}
		for(i = 0; i < lLED.length; i ++){
			lLED[i].update(parent.frameNum);
		}
		
//		if(isOver()){
//			parent.cursor(java.awt.Cursor.HAND_CURSOR);
//		}else{
//			parent.cursor(java.awt.Cursor.DEFAULT_CURSOR);
//		}

	}
	
	
	public void display(){
		int i;
		for(i = 0; i < tLED.length;  i++){
			tLED[i].display();
		}
		for(i = 0; i < bLED.length;  i++){
			bLED[i].display();
		}
		for(i = 0; i < rLED.length;  i++){
			rLED[i].display();
		}
		for(i = 0; i < lLED.length;  i++){
			lLED[i].display();
		}
	}

	public void setPos(int _xPos, int _yPos, int resolution){

		for(int i = 0; i < leds.length; i ++){
			leds[i].setPos(_xPos, _yPos, resolution);
		}
	}


	public boolean isOver(){
		if(parent.mouseX >= xPos && parent.mouseX <= (xPos + width) && parent.mouseY >= yPos && parent.mouseY <= (yPos + height)){
			return true;
		}else{
			return false;
		}
	}
	
	public void mousePressed(){
		for(int i = 0; i < leds.length; i ++){
			if(leds[i].pressed()){
				leds[i].set_frame(parent.frameNum, 255);
			}
		}
	}
}

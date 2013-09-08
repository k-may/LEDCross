package ledcross;

import java.awt.Color;

import processing.core.PApplet;
import processing.core.*;

public class LED {

	private boolean _isOver;
	private boolean _isPressed;

	private boolean isLocked;

	private int overColor = 0xffff0000;
	private int outColor = 0xff00ff00;
	
	private int currentColor;
	private int currentAlpha = 255;

	private int xOffset;
	private int yOffset;

	public int xPos;
	public int yPos;

	LEDCross parent;

	public int size = 10;
	public int area = 20;
	public int resolution = 20;

	public int[] frameData;

	public LED(int _xOffset, int _yOffset, LEDCross _parent){
		xOffset = _xOffset;
		yOffset = _yOffset;
		parent = _parent;

		frameData = new int[parent.totalFrames];
	}



	public void update(int $frameNum){
		if(isOver()){
			currentColor = overColor;
			currentAlpha = 255;
		}else{
			currentColor = outColor;
			currentAlpha = frameData[$frameNum];
		}
	}


	public void display(){
		parent.stroke(255);
		parent.fill(currentColor, currentAlpha);
		parent.ellipse(xPos, yPos, size, size);
	}

	public void setPos(int _xPos, int _yPos, int _resolution){
		resolution = _resolution;
		xPos = _xPos + (resolution*xOffset);
		yPos = _yPos + (resolution*yOffset);
	}
	public void printOut(){
		parent.println("xPos: " + xPos + " : " + parent.mouseX + " , " + yPos + " : " + parent.mouseY + " : " + size + " : " + isOver() + " : " + currentColor);
	}

	public boolean pressed(){
		if(isOver()){
			isLocked = true;
			return true;
		}else{
			isLocked = false;
			return false;
		}
	}


	public boolean isOver(){


		if(parent.mouseX >= (xPos - area) && parent.mouseX < xPos+area && parent.mouseY >= (yPos - area) && parent.mouseY < yPos + area){
			return true;
		}else{
			return false;
		}
	}

	
	public void appendFrame(){

		int[] tempFrameData = new int[frameData.length + 1];

		for(int i = 0; i < frameData.length; i ++){
			tempFrameData[i] = frameData[i];
		}

		frameData = tempFrameData;
	}


	public void appendFrames(int _frameNum){
		
		int[] tempFrameData = new int[frameData.length + 1];

		for(int i = 0; i < frameData.length; i ++){
			tempFrameData[i] = frameData[i];
		}

		frameData = tempFrameData;
	}

	public void spliceFrames(int _frameNum){
		int[] tempFrameData = new int[frameData.length - _frameNum];

		for(int i= 0; i < tempFrameData.length; i ++){
			tempFrameData[i] = frameData[i];
		}

		frameData = tempFrameData;
	}

	public void popFrame(){

	}

	public void set_frame(int $frameNum, int value){
		frameData[$frameNum] = value;
	}
}

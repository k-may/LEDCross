package ledcross;

import java.awt.Color;

import processing.core.PApplet;
import processing.core.*;

public class LED {

	private boolean _isOver;
	private boolean _isPressed;

	private boolean isLocked;

	private int overColor = 0xff6161CE;
	private int outColor = 0xff0000ff;

	private int currentColor;
	private int _currentAlpha = 255;

	int xOffset;
	int yOffset;

	public int xPos;
	public int yPos;

	LEDCross parent;

	public int size = 10;
	public int area = 20;
	public int resolution = 20;

	public int[] frameData;

	
	public LED(int _xOffset, int _yOffset, LEDCross _parent) {
		xOffset = _xOffset;
		yOffset = _yOffset;
		parent = _parent;
		frameData = new int[0];
	}

	public void update(int $frameNum) {
		if (isOver()) {
			currentColor = overColor;
			_currentAlpha = 255;
		} else {
			currentColor = outColor;
			_currentAlpha = frameData[$frameNum];
		}
	}

	public void display() {
		parent.stroke(255);
		parent.fill(currentColor, _currentAlpha);
		parent.ellipse(xPos, yPos, size, size);
	}

	public void setPos(int _xPos, int _yPos, int _resolution) {
		resolution = _resolution;
		xPos = _xPos + (resolution * xOffset);
		yPos = _yPos + (resolution * yOffset);
	}

	public boolean isPressed() {
		if (isOver()) {
			isLocked = true;
			return true;
		} else {
			isLocked = false;
			return false;
		}
	}

	public boolean isOver() {
		if (parent.mouseX >= (xPos - area) && parent.mouseX < xPos + area
				&& parent.mouseY >= (yPos - area)
				&& parent.mouseY < yPos + area) {
			return true;
		} else {
			return false;
		}
	}

	// -----------------getters & setters ------------------------------
	public void set_totalFrameNum(int _totalFrameNum) {
		if (frameData != null) {
			if (frameData.length < _totalFrameNum) {
				appendFrames(_totalFrameNum - frameData.length);
			} else {
				// spliceFrames
			}
		} else {
			frameData = new int[_totalFrameNum];
		}
	}

	public void set_frameData(int[] $frameData) {
		frameData = new int[$frameData.length];
		for (int i = 0; i < $frameData.length; i++) {
			frameData[i] = $frameData[i];
		}
	}

	public void set_frame(int $frameNum, int value) {
		frameData[$frameNum] = value;
	}

	public int get_frameData(int $frameNum) {
		return frameData[$frameNum];
	}

	// -------------------- frame utils -------------------------
	private void appendFrames() {
		int[] tempFrameData = new int[frameData.length + 1];
		for (int i = 0; i < frameData.length; i++) {
			tempFrameData[i] = frameData[i];
		}
		frameData = tempFrameData;
	}

	private void appendFrames(int _frameNum) {
		int[] tempFrameData = new int[frameData.length + _frameNum];
		// copy existing frames
		for (int i = 0; i < frameData.length; i++) {
			tempFrameData[i] = frameData[i];
		}
		frameData = tempFrameData;
	}

	private void spliceFrames(int _frameNum) {
		int[] tempFrameData = new int[frameData.length - _frameNum];
		for (int i = 0; i < tempFrameData.length; i++) {
			tempFrameData[i] = frameData[i];
		}
		frameData = tempFrameData;
	}

	private void popFrame() {
	}

	public int getCurrentAlpha(){
		return _currentAlpha;
	}
	
	public byte getCurrentByte(){
		return (byte)(_currentAlpha - 124);
	}
	
	public void printOut() {
		PApplet.println("xPos: " + xPos + " : " + parent.mouseX + " , " + yPos
				+ " : " + parent.mouseY + " : " + size + " : " + isOver()
				+ " : " + currentColor);
	}
}

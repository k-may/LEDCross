package ledcross;

import java.util.Arrays;

import ledcross.LED;
import processing.core.PApplet;

public class CrossPrototyper implements IView {

	LEDCross parent;

	LED[] tLED = new LED[30];
	LED[] bLED = new LED[30];
	LED[] lLED = new LED[40];
	LED[] rLED = new LED[40];

	LED[] leds = new LED[140];

	int _res = 15;

	int bankNum = 4;
	int area = 10;
	int xPos = 10;
	int yPos = 50;
	int width;
	int height;

	int totalFrameNum;

	CrossPrototyper(LEDCross _parent) {
		parent = _parent;
		//totalFrameNum = parent.totalFrameNum;

		int i;
		int x = 0;
		int y = 0;
		int ledCount = 0;
		LED led;

		for (i = 0; i < 30; i++) {
			x = ((i % 5) * 2) + 11;
			y = (i / 5) * 2;
			led = new LED(x, y, parent);
			leds[ledCount] = led;
			tLED[i] = led;
			ledCount++;
		}

		for (i = 0; i < 30; i++) {
			x = ((i % 5) * 2) + 11;
			y = (i / 5) * 2;
			led = new LED(x, y + 22, parent);
			leds[ledCount] = led;
			bLED[i] = led;
			ledCount++;
		}

		for (i = 0; i < 40; i++) {
			x = (i % 8) * 2;
			y = (i / 8) * 2 + 12;
			led = new LED(x, y, parent);
			leds[ledCount] = led;
			lLED[i] = led;
			ledCount++;
		}

		for (i = 0; i < 40; i++) {
			x = (i % 8) * 2;
			y = (i / 8) * 2 + 12;
			led = new LED(x + 16, y, parent);
			leds[ledCount] = led;
			rLED[i] = led;
			ledCount++;
		}

		width = (x + 16) * 20 + 20;
		height = (y + 22) * 20 + 20;

		xPos = (parent.width - width + _res) >> 1;

		setPos(xPos, yPos, 20);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		int i;
		for (i = 0; i < tLED.length; i++) {
			tLED[i].update(parent.currentFrameNum - 1);
		}
		for (i = 0; i < bLED.length; i++) {
			bLED[i].update(parent.currentFrameNum - 1);
		}
		for (i = 0; i < rLED.length; i++) {
			rLED[i].update(parent.currentFrameNum - 1);
		}
		for (i = 0; i < lLED.length; i++) {
			lLED[i].update(parent.currentFrameNum - 1);
		}

		// if(isOver()){
		// parent.cursor(java.awt.Cursor.HAND_CURSOR);
		// }else{
		// parent.cursor(java.awt.Cursor.DEFAULT_CURSOR);
		// }

	}

	public void display() {
		int i;
		for (i = 0; i < tLED.length; i++) {
			tLED[i].display();
		}
		for (i = 0; i < bLED.length; i++) {
			bLED[i].display();
		}
		for (i = 0; i < rLED.length; i++) {
			rLED[i].display();
		}
		for (i = 0; i < lLED.length; i++) {
			lLED[i].display();
		}
	}

	public void setPos(int _xPos, int _yPos, int resolution) {

		for (int i = 0; i < leds.length; i++) {
			leds[i].setPos(_xPos, _yPos, resolution);
		}
	}

	public boolean isOver() {
		if (parent.mouseX >= xPos && parent.mouseX <= (xPos + width)
				&& parent.mouseY >= yPos && parent.mouseY <= (yPos + height)) {
			return true;
		} else {
			return false;
		}
	}

	public void mousePressed() {
		for (int i = 0; i < leds.length; i++) {
			if (leds[i].isPressed()) {
				leds[i].set_frame(parent.currentFrameNum - 1, parent.get_brushValue());
			}
		}
	}

	public void set_totalFrameNum(int _totalFrameNum) {
		if (totalFrameNum != _totalFrameNum) {
			totalFrameNum = _totalFrameNum;
			for (int i = 0; i < leds.length; i++) {
				leds[i].set_totalFrameNum(totalFrameNum);
			}
		}
	}

	public LED[] get_bank(int _bankNum) {
		LED[] l = null;

		switch (_bankNum) {
		case 0:
			l = tLED;
			break;
		case 1:
			l = bLED;
			break;
		case 2:
			l = lLED;
			break;
		case 3:
			l = rLED;
			break;
		}

		return l;

	}

	public void set_bank(int _bankNum, int _frameNum, int[] _frameData) {

		// set framedata
		LED[] bank;
		switch (_bankNum) {
		case 0:
			bank = tLED;
			break;
		case 1:
			bank = bLED;
			break;
		case 2:
			bank = lLED;
			break;
		case 3:
			bank = rLED;
			break;
		default:
			return;

		}

		int count = 0;
		
		// set each value for frame
		for (int i = 0; i < bank.length; i++) {
			bank[i].set_frame(_frameNum, _frameData[i]);
		}
	}

	public void set_Area(int _area) {
		area = _area;
		for (int i = 0; i < leds.length; i++) {
			leds[i].area = area;
		}
	}

}

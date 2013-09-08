package ledcross;

import java.util.List;

import processing.core.PApplet;

import controlP5.Button;
import controlP5.ControlEvent;
import controlP5.ControlP5;
import controlP5.Slider;
import controlP5.Textfield;
import controlP5.Toggle;

public class CrossConsole implements IView {

	LEDCross parent;

	int frameNum;

	ControlP5 control;
	Toggle toggle;
	public Textfield frameField;
	
	Slider radius;
	
	int currentRadius = 10;

	Boolean isPlaying = false;

	CrossConsole(LEDCross _parent, ControlP5 _control) {
		parent = _parent;
		control = _control;

		int xPos = 10;
		int yPos = 590;
		int margin = 35;

		control = new ControlP5(parent);

		toggle = control.addToggle("play", false, xPos, yPos, 20, 20);
		yPos += margin;

		control.addButton("next", 1, xPos, yPos, 100, 20);
		yPos += margin;

		control.addButton("previous", 1, xPos, yPos, 100, 20);
		yPos += margin;

		frameField = control.addTextfield("frame", xPos, yPos, 100, 20);
		frameField.setFocus(true);
		frameField.setAutoClear(false);
		yPos += margin;

		yPos += margin;

		control.addButton("save", 0, xPos, yPos, 100, 20);

		control.addSlider("value", 0, 255, parent.get_brushValue(), parent.width - 200, yPos, 100, 10);
		radius = control.addSlider("radius", 0, 20, 10, parent.width - 200, yPos - margin, 100, 10);

	}
	

	@Override
	public void update() {
		// TODO Auto-generated method stub
		if (!frameField.isFocus()) {
			frameField.setText(Integer.toString(parent.currentFrameNum));
		}

		if (parent.isPlaying != isPlaying) {
			isPlaying = parent.isPlaying;
			if (isPlaying) {
				toggle.setLabel("PAUSE");
			} else {
				toggle.setLabel("PLAY");
			}
		}
	}

	public void display() {

	}
	
	public void set_Radius(int _rad){
		parent.println("rad : " + _rad);
		radius.setValue(_rad);
	}
}

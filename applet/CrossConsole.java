package ledcross;

import java.util.List;

import processing.core.PApplet;

import controlP5.Button;
import controlP5.ControlEvent;
import controlP5.ControlP5;
import controlP5.Textfield;
import controlP5.Toggle;

public class CrossConsole implements IView {

	LEDCross parent;

	int frameNum;

	ControlP5 control;
	Toggle toggle;
	public Textfield frameField;

	CrossConsole(LEDCross _parent, ControlP5 _control){
		parent = _parent;
		control = _control;


		int xPos = 10;
		int yPos = 700;
		int margin = 35;

		control = new ControlP5(parent);

		toggle = control.addToggle("play",false,xPos,yPos,20,20);
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
	}


	@Override
	public void update() {
		// TODO Auto-generated method stub
		if(!frameField.isFocus()){
			frameField.setText(Integer.toString(parent.frameNum));
		}
		
		
		if(parent.isPlaying){
			toggle.setLabel("PAUSE");
		}else{
			toggle.setLabel("PLAY");
		}
	}

	public void display(){

	}
}

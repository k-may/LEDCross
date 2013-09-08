package ledcross;

import processing.core.PApplet;

public class CrossBrush {

	LEDCross parent;

	int xPos;
	int yPos;
	int currentRadius;

	CrossBrush(LEDCross _parent) {
		parent = _parent;
	}

	public void update() {
		// currentValue
	}

	public void display() {
		parent.stroke(255);
		parent.fill(0, 0, 255, parent.get_brushValue());
		parent.ellipse(parent.mouseX, parent.mouseY, currentRadius, currentRadius);
	}

}
package ledcross;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.lang.reflect.Array;
import java.util.Arrays;

import controlP5.ControlEvent;
import controlP5.ControlP5;
import controlP5.Toggle;
import processing.core.PApplet;
import processing.serial.Serial;

import proxml.*;

public class LEDCross extends PApplet {

	CrossConsole console;
	CrossPrototyper prototyper;
	CrossBrush brush;
	CrossSerialOutput serialOutput;

	int totalFrameNum = 0;
	int currentFrameNum = 1;
	int frameRate = 10;
	int currentBrushValue = 128;

	ControlP5 control;

	XMLInOut xmlInOut;
	XMLElement data;
	private static final String docStart = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>";

	boolean isPlaying = false;

	public void setup() {
		size(700, 800);

		control = new ControlP5(this);

		xmlInOut = new XMLInOut(this);

		prototyper = new CrossPrototyper(this);

		//serialOutput = new CrossSerialOutput(this, prototyper);
		// this.serialEvent = serialOutput.serialEvent();

		try {
			xmlInOut.loadElement("frameData.xml");
		} catch (Exception e) {
			// if the xml file could not be loaded it has to be created
			xmlEvent(new XMLElement("data"));
		}
		console = new CrossConsole(this, control);
		brush = new CrossBrush(this);

		set_radius(10);
	}

	public void serialEvent(Serial myPort) {
		serialOutput.serialEvent(myPort);
	}

	public void controlEvent(ControlEvent theEvent) {
		if (theEvent.controller().name() == "frame") {
			if (!isPlaying) {
				currentFrameNum = Integer.parseInt(theEvent.controller()
						.stringValue());
				if (currentFrameNum > totalFrameNum) {
					set_totalFrameNum(currentFrameNum);
				}
			}
		}
		if (theEvent.controller().name() == "next") {
			if (!isPlaying) {
				currentFrameNum = (currentFrameNum + 1) > totalFrameNum ? 1
						: currentFrameNum + 1;
			}
		}
		if (theEvent.controller().name() == "previous") {
			if (!isPlaying) {
				currentFrameNum = (currentFrameNum - 1) == 0 ? totalFrameNum
						: currentFrameNum - 1;
			}
		}
		if (theEvent.controller().name() == "play") {

			if ((int) theEvent.controller().value() == 1) {
				isPlaying = true;
			} else {
				isPlaying = false;
			}
		}
		if (theEvent.controller().name() == "radius") {
			set_radius((int) theEvent.controller().value());
		}
		if (theEvent.controller().name() == "value") {
			currentBrushValue = (int) theEvent.controller().value();
		}

	}

	public void draw() {
		if (isPlaying) {
			currentFrameNum = (this.frameCount % frameRate == 0) ? (currentFrameNum + 1)
					% totalFrameNum + 1
					: currentFrameNum;
		}
		update();
		display();
	}

	void update() {
		console.update();
		prototyper.update();
		brush.update();
	//	serialOutput.update();
	}

	void display() {
		background(0);
		console.display();
		prototyper.display();
		brush.display();
	//	serialOutput.display();

	}

	// -------------- events -------------------
	public void mousePressed() {
		prototyper.mousePressed();
	}

	public void xmlEvent(proxml.XMLElement element) {
		println(element.toString());
		data = element;
		if (data.hasChildren()) {
			parseXML();
		} else {
			// start from scratch
			set_totalFrameNum(2);
		}
	}

	// --------------getter & setters-----------------
	private void set_totalFrameNum(int _num) {
		totalFrameNum = _num;
		prototyper.set_totalFrameNum(_num);
	}

	private void set_radius(int $radius) {
		int $currentRadius = (int) Math.pow($radius, 2) + 10;
		brush.currentRadius = $currentRadius;
		prototyper.set_Area($currentRadius);
	}

	public int get_brushValue() {
		return currentBrushValue;
	}

	void save(int theValue) {
		println("save bitches");
		int xPos = 0;
		int yPos = 0;

		String[][] leddata = new String[prototyper.bankNum][];

		// convert each bank to a string of values
		for (int f = 0; f < totalFrameNum; f++) {
			leddata[f] = new String[4];
			for (int b = 0; b < prototyper.bankNum; b++) {
				LED[] _b = prototyper.get_bank(b);
				String bankData = "";
				for (int l = 0; l < _b.length; l++) {
					bankData += _b[l].get_frameData(f) + " ";
				}
				println(bankData);
				leddata[f][b] = bankData;
			}
		}

		data = createXML(leddata);

		String filePath = this.sketchPath + File.separator + "data";
		File file = new File(filePath, "frameData.xml");

		try {
			PrintWriter output = new PrintWriter(new FileOutputStream(file));
			output.println(docStart);
			printXML(data, output, " ", " ");
			output.flush();
			output.close();
		} catch (Exception e) {
			println("save error :  " + e);
		}

	}

	XMLElement createXML(String[][] _leddata) {

		XMLElement xml = new XMLElement("frameData");
		XMLElement frames = new XMLElement("frames");

		for (int f = 0; f < totalFrameNum; f++) {
			XMLElement frame = new XMLElement("f");
			frame.addAttribute("num", f);

			for (int b = 0; b < prototyper.bankNum; b++) {
				XMLElement bank = new XMLElement("b");
				bank.addAttribute("id", b);
				// int banksize = _leddata[b].length;
				// String bankData = "";
				// println("data : " + Arrays.toString(_leddata[b]));
				// for (int l = 0; l < banksize; l++) {
				// bankData += _leddata[b][l] + " ";
				// }
				bank.addChild(new XMLElement(_leddata[f][b], true));
				frame.addChild(bank);
			}
			frames.addChild(frame);
		}

		xml.addChild(frames);
		return xml;
	}

	public void parseXML() {
		XMLElement frames = data.getChild(0);
		set_totalFrameNum(frames.getChildren().length);

		for (int f = 0; f < totalFrameNum; f++) {
			XMLElement frame = frames.getChild(f);
			for (int b = 0; b < frame.getChildren().length; b++) {
				XMLElement bank = frame.getChild(b);
				String b_data = bank.getChild(0).getText();
				String[] b_array = b_data.split(" ");

				int bankSize = b_array.length;
				int[] bankData = new int[bankSize];

				for (int l = 0; l < bankSize; l++) {
					bankData[l] = Integer.parseInt(b_array[l]);
				}

				prototyper.set_bank(b, f, bankData);
			}
		}
	}

	void printXML(XMLElement element, PrintWriter output, String start,
			String dist) {
		output.println(element.toString());
		for (int i = 0; i < element.getChildren().length; i++) {
			printXML((XMLElement) element.getChild(i), output, start + dist,
					dist);
		}
		if (element.hasChildren()) {
			output.println(start + "</" + element.getName() + ">");
		}
	}

}

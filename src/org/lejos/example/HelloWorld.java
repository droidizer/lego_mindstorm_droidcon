package org.lejos.example;

import lejos.nxt.*;
import lejos.robotics.objectdetection.Feature;
import lejos.robotics.objectdetection.FeatureDetector;
import lejos.robotics.objectdetection.FeatureListener;
import lejos.robotics.objectdetection.RangeFeatureDetector;
import lejos.util.Delay;

/**
 * Example leJOS Project with an ant build file
 *
 */
class HelloWorld implements FeatureListener {
	/**
	 * Maximum distance to report a detected object. Default is 80 cm.
	 */
	public static int MAX_DETECT = 80;
	private static NXTRegulatedMotor motorB;

	public static void main(String[] args) throws Exception {

		// Instructions:
//		System.out.println("Autodetect ON");
//		System.out.println("Max dist: " + MAX_DETECT);
//		System.out.println("ENTER = do scan");
//		System.out.println("RIGHT = on/off");
//		System.out.println("ESCAPE = exit");

		// Initialize the detection objects:
		HelloWorld listener = new HelloWorld();
		UltrasonicSensor us = new UltrasonicSensor(SensorPort.S4);
		us.continuous();
		//RangeFeatureDetector fd = new RangeFeatureDetector(us, MAX_DETECT, 500);
		//fd.addListener(listener);

		Button.setKeyClickVolume(0); // Disable default button sound
		motorB = Motor.B;
		motorB.setSpeed(720);
		motorB.forward();

		// Button inputs:
		while(!Button.ESCAPE.isDown()) {

//			// Perform a single scan:
//			if(Button.ENTER.isDown()) {
//				Feature res = fd.scan();
//				if(res == null) System.out.println("Nothing detected");
//				else {
//					// This is unorthodox--easier to piggy-back on listener's display code:
//					listener.featureDetected(res, fd);
//				}
//				Thread.sleep(500);
//			}
//
//			// Enable/disable detection using buttons:
//			if(Button.RIGHT.isDown()) {
//				if(fd.isEnabled()) {
//					Sound.beepSequence();
//					System.out.println("Autodetect OFF");
//				} else {
//					Sound.beepSequenceUp();
//					System.out.println("Autodetect ON");
//				}
//				fd.enableDetection(!fd.isEnabled());
//				Thread.sleep(500);
//			}
			Delay.msDelay(200);
			if(us.getDistance() < 20)
			{
				motorB.stop();
				Delay.msDelay(2000);
			} else {
				motorB.forward();
			}
			Thread.yield();
//			Delay.msDelay(5000);
//			fd.scan();
		}
	}

	/**
	 * Output data about the detected object to LCD.
	 */
	public void featureDetected(Feature feature, FeatureDetector detector) {
		int range = (int)feature.getRangeReading().getRange();
		//Sound.playTone(1200 - (range * 10), 100);
		System.out.println("Range:" + range);
		if(range < 20) {
			motorB.stop();
		} else {
			motorB.forward();
		}
	}
}

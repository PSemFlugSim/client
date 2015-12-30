package de.gymolching.fsb.client.gui;

/**
 * Created by Julian Hesse on 30.11.2014.
 */
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ManipulateCoordinatesPanel extends JPanel {
	// Necessary because of reasons
	private static final long serialVersionUID = 1L;

	// static variables for JSlider
	protected static int positionInPercent_Min = -50;
	protected static int positionInPercent_Max = 50;
	protected static int positionInPercent_Init = 0;
	protected static int positionInPercent_Tic = 10;
	protected static int rotationInDegree_Min = -180;
	protected static int rotationInDegree_Max = 180;
	protected static int rotationInDegree_Init = 0;
	protected static int rotationInDegree_Tic = 36;

	JSlider positionX;
	JSlider positionY;
	JSlider positionZ;
	JSlider rotationX;
	JSlider rotationY;
	JSlider rotationZ;

	JLabel positionX_Label;
	JLabel positionY_Label;
	JLabel positionZ_Label;
	JLabel rotationX_Label;
	JLabel rotationY_Label;
	JLabel rotationZ_Label;

	public ManipulateCoordinatesPanel() {
		// Sliders manipulate the coordinates relative to the default location
		positionX = new JSlider(JSlider.HORIZONTAL, positionInPercent_Min, positionInPercent_Max,
				positionInPercent_Init);
		positionY = new JSlider(JSlider.HORIZONTAL, positionInPercent_Min, positionInPercent_Max,
				positionInPercent_Init);
		positionZ = new JSlider(JSlider.HORIZONTAL, positionInPercent_Min, positionInPercent_Max,
				positionInPercent_Init);
		// Sliders manipulate the rotation in x, y and z direction
		rotationX = new JSlider(JSlider.HORIZONTAL, rotationInDegree_Min, rotationInDegree_Max, rotationInDegree_Init);
		rotationY = new JSlider(JSlider.HORIZONTAL, rotationInDegree_Min, rotationInDegree_Max, rotationInDegree_Init);
		rotationZ = new JSlider(JSlider.HORIZONTAL, rotationInDegree_Min, rotationInDegree_Max, rotationInDegree_Init);

		// configuration of the sliders
		positionX.setMinorTickSpacing(positionInPercent_Tic);
		positionX.setPaintTicks(true);
		positionY.setMinorTickSpacing(positionInPercent_Tic);
		positionY.setPaintTicks(true);
		positionZ.setMinorTickSpacing(positionInPercent_Tic);
		positionZ.setPaintTicks(true);

		rotationX.setMinorTickSpacing(rotationInDegree_Tic);
		rotationX.setPaintTicks(true);
		rotationY.setMinorTickSpacing(rotationInDegree_Tic);
		rotationY.setPaintTicks(true);
		rotationZ.setMinorTickSpacing(rotationInDegree_Tic);
		rotationZ.setPaintTicks(true);

		// Labels for sliders
		positionX_Label = new JLabel("Position of X in percent: 0°");
		positionY_Label = new JLabel("Position of Y in percent: 0°");
		positionZ_Label = new JLabel("Position of Z in percent: 0°");
		rotationX_Label = new JLabel("Rotation of X in percent: 0°");
		rotationY_Label = new JLabel("Rotation of Y in percent: 0°");
		rotationZ_Label = new JLabel("Rotation of Z in percent: 0°");

		// add Swing component to panel
		add(positionX_Label);
		add(positionX);
		add(positionY_Label);
		add(positionY);
		add(positionZ_Label);
		add(positionZ);
		add(rotationX_Label);
		add(rotationX);
		add(rotationY_Label);
		add(rotationY);
		add(rotationZ_Label);
		add(rotationZ);

		CoordinatesPanelChangeListener ev = new CoordinatesPanelChangeListener();
		positionX.addChangeListener(ev);
		positionY.addChangeListener(ev);
		positionZ.addChangeListener(ev);
		rotationX.addChangeListener(ev);
		rotationY.addChangeListener(ev);
		rotationZ.addChangeListener(ev);
	}

	public class CoordinatesPanelChangeListener implements ChangeListener {
		public void stateChanged(ChangeEvent ev) {
			// get values of sliders
			int value1 = positionX.getValue();
			int value2 = positionY.getValue();
			int value3 = positionZ.getValue();
			int value4 = rotationX.getValue();
			int value5 = rotationY.getValue();
			int value6 = rotationZ.getValue();

			// add values to label
			positionX_Label.setText("Position of X in percent: " + value1 + " °");
			positionY_Label.setText("Position of Y in percent: " + value2 + " °");
			positionZ_Label.setText("Position of Z in percent: " + value3 + " °");
			rotationX_Label.setText("Rotation of X in percent: " + value4 + " °");
			rotationY_Label.setText("Rotation of Y in percent: " + value5 + " °");
			rotationZ_Label.setText("Rotation of Z in percent: " + value6 + " °");
		}
	}
}

package de.gymolching.fsb.client.gui; /**
										* Here the panels and the tabs are created and the swing components are added to the panels.
										*
										* Created by Julian Hesse on 15.11.2014.
										*/

import javax.swing.*;

import de.gymolching.fsb.client.api.FSBClientInterface;
import de.gymolching.fsb.client.api.FSBPosition;
import de.gymolching.fsb.client.implementation.FSBClient;
import de.gymolching.fsb.client.maths.Maths;

import static javax.swing.BoxLayout.PAGE_AXIS;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class MainFrame extends JFrame {
	// Necessary because of reasons
	private static final long serialVersionUID = 1L;

	// Client network interface to server
	private FSBClientInterface client;

	// Panel-array for pillarLength in degree and a panel to contain them
	private JPanel[] panelPillarLength;
	private JPanel firstPanel;

	// Panel-array for indirect manipulation of pillars
	private JPanel[] panelPillarManipulation;
	private JPanel secondPanel;

	// Third and final panal
	private TextFieldForInput[] textFieldPanel;
	private PanelForPillarLength[] sliderForFinalPanel;
	private JPanel thirdPanel;

	public MainFrame(String title) {
		super(title);

		// Setup client connection TODO: replace with actual implementation
		this.client = new FSBClientInterface() {

			@Override
			public void setVerbose(boolean verbose) {
				// Do nothing
			}

			@Override
			public void sendNewPosition(FSBPosition position) throws IOException {
				System.out.println("Sending new position: " + position.toString());
			}

			@Override
			public void disconnect() throws IOException {
				System.out.println("Disconnected");
			}

			@Override
			public void connect(String host, int port) throws IOException {
				System.out.println("Connected");
			}

			@Override
			public boolean isConnected() {
				return true;
			}
		};
		boolean connected = false;
		String portIP = "";

		do {
			// Ask user for serverIP and Port
			portIP = JOptionPane.showInputDialog(this, "Please enter<serverIP>:<port>",
					portIP != null && !portIP.isEmpty() ? portIP : "127.0.0.1:666");
			if (portIP == null || portIP.split(":").length != 2)
				continue;

			// try to connect
			String[] portIPComponents = portIP.split(":");
			try {
				this.client.connect(portIPComponents[0], new Integer(portIPComponents[1]));
			} catch (NumberFormatException e1) {
				e1.printStackTrace();
				continue;
			} catch (IOException e1) {
				e1.printStackTrace();
				continue;
			}

			// Are we connected?
			connected = this.client.isConnected();
		} while (!connected);

		// the first panel allow the user to manipulate the 6 pillars directly
		// (lengthsInPercent)
		panelPillarLength = new PanelForPillarLength[6];

		for (int i = 0; i <= 5; i++) {
			panelPillarLength[i] = new PanelForPillarLength(i, "Pillar " + (i + 1) + " length in percent:");
		}

		firstPanel = new JPanel();
		firstPanel.setLayout(new BoxLayout(firstPanel, PAGE_AXIS));

		for (int i = 0; i <= 5; i++) {
			firstPanel.add(panelPillarLength[i]);
		}

		// the second panel allows the user to define the x, y and z C

		secondPanel = new JPanel();
		// set Layout of second Panel
		secondPanel.setLayout(new BoxLayout(secondPanel, PAGE_AXIS));

		panelPillarManipulation = new PanelForPillarLength[6];

		panelPillarManipulation[0] = new PanelForPillarLength(0, "Position of X in percent: ", 0);
		panelPillarManipulation[1] = new PanelForPillarLength(1, "Position of Y in percent: ", 0);
		panelPillarManipulation[2] = new PanelForPillarLength(2, "Position of Z in percent: ", 0);
		panelPillarManipulation[3] = new PanelForPillarLength(3, "Rotation of X in degree: ", -180, 180, 0, 36, 18,
				'd');
		panelPillarManipulation[4] = new PanelForPillarLength(4, "Rotation of Y in degree: ", -180, 180, 0, 36, 18,
				'd');
		panelPillarManipulation[5] = new PanelForPillarLength(5, "Rotation of Z in degree: ", -180, 180, 0, 36, 18,
				'd');

		for (int i = 0; i <= 5; i++) {
			secondPanel.add(panelPillarManipulation[i]);
		}

		// the third Panel is probably used
		thirdPanel = new JPanel();

		thirdPanel.setLayout(new BoxLayout(thirdPanel, PAGE_AXIS));

		textFieldPanel = new TextFieldForInput[3];

		textFieldPanel[0] = new TextFieldForInput("Position of X: ", "cm");
		textFieldPanel[1] = new TextFieldForInput("Position of Y: ", "cm");
		textFieldPanel[2] = new TextFieldForInput("Position of Z: ", "cm");

		for (int i = 0; i < 3; i++) {
			thirdPanel.add(textFieldPanel[i]);
		}

		sliderForFinalPanel = new PanelForPillarLength[3];

		sliderForFinalPanel[0] = new PanelForPillarLength(1, "Rotation of X in degree: ", -180, 180, 0, 36, 18, 'd');
		sliderForFinalPanel[1] = new PanelForPillarLength(2, "Rotation of Y in degree: ", -180, 180, 0, 36, 18, 'd');
		sliderForFinalPanel[2] = new PanelForPillarLength(3, "Rotation of Z in degree: ", -180, 180, 0, 36, 18, 'd');

		for (int i = 0; i < 3; i++) {
			thirdPanel.add(sliderForFinalPanel[i]);
		}

		JButton start = new JButton("Start");

		start.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int values[] = new int[6];
				values[0] = textFieldPanel[0].getValue();
				values[1] = textFieldPanel[1].getValue();
				values[2] = textFieldPanel[2].getValue();
				values[3] = sliderForFinalPanel[0].GetValueOfSlider();
				values[4] = sliderForFinalPanel[1].GetValueOfSlider();
				values[5] = sliderForFinalPanel[2].GetValueOfSlider();

				try {
					client.sendNewPosition(
							Maths.calculatePosition(values[0], values[1], values[2], values[3], values[4], values[5]));
				} catch (IOException e1) {
					e1.printStackTrace();
					// Sending did not work TODO: try to reconnect as this must
					// mean that we're disconnected
				}
			}
		});

		thirdPanel.add(start);

		// create Tabs
		JPanel tabbedPane = new JPanel();

		// add Panels as Tabs
		tabbedPane.add(thirdPanel);

		// super.add(firstPanel);
		super.setSize(650, 800);
		super.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		super.setVisible(true);
		super.add(tabbedPane);

		// Hacky window listener to disconnect client on exit
		super.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if (client != null && client.isConnected())
					try {
						client.disconnect();
					} catch (IOException e1) {
						e1.printStackTrace();
					}

				System.exit(0);
			}
		});
	}
}

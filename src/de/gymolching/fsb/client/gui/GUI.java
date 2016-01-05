package de.gymolching.fsb.client.gui;

/**
 * Created by Julian Hesse on 15.11.2014.
 */
import javax.swing.*;

public class GUI {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new MainFrame("Flugsimulator"));
	}
}
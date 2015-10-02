/*
 * Copyright (C) 2015  Simon Schaeffner <simon.schaeffner@googlemail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package de.gymolching.fsb.gui;

import de.gymolching.fsb.client.api.FSBPosition;
import de.gymolching.fsb.client.implementation.FSBClient;
import me.sschaeffner.lcSwing.LcsPanel;
import me.sschaeffner.lcSwing.api.ButtonListener;
import me.sschaeffner.lcSwing.components.LcsButton;
import me.sschaeffner.lcSwing.components.LcsFader;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * @author sschaeffner
 */
public class NewGui {

    private static final String DEFAULT_HOST_PORT_VALUE = "localhost:1234";

    public static void main(String[] args) {
        System.out.println("hello world");
        new NewGui();
    }

    private final ServerController serverController;

    private final JFrame frame;
    private final LcsPanel panel;
    private final LcsFader[] faders;
    private final LcsButton quitButton;
    private final LcsButton submitButton;
    private final SubmitButtonListener submitButtonListener;


    public NewGui() {
        serverController = new ServerController();

        boolean connected = false;
        while (!connected) {
            String input = (String) JOptionPane.showInputDialog(null, "Enter host:port", "Connect...", JOptionPane.PLAIN_MESSAGE, null, null, DEFAULT_HOST_PORT_VALUE);
            if (input == null) System.exit(0);
            try {
                String[] inputArr = input.split(":");
                if (inputArr.length == 2) {
                    String host = inputArr[0];
                    int port = Integer.valueOf(inputArr[1]);
                    if (port > 0)  {
                        serverController.connect(host, port);
                        connected = true;
                    }
                }

            } catch (Exception e) {
                //do nothing and try again
            }
        }

        frame = new JFrame("fs client");
        panel = new LcsPanel();

        faders = new LcsFader[6];
        for (int i = 0; i < faders.length; i++) {
            faders[i] = new LcsFader(60 * i, 0, 60, 190, "m" + i, new Color(150, 150, 255));
            panel.addLcsComponent(faders[i]);
        }
        quitButton = new LcsButton(60 * faders.length - 80 - 80 - 5, 195, 80, 40, "quit", new Color(255, 100, 100));
        quitButton.setListener(() -> System.exit(0));
        panel.addLcsComponent(quitButton);
        submitButton = new LcsButton(60 * faders.length - 80, 195, 80, 40, "submit", new Color(100, 255, 100));
        submitButtonListener = new SubmitButtonListener();
        submitButton.setListener(submitButtonListener);
        panel.addLcsComponent(submitButton);
        panel.getJPanel().setPreferredSize(new Dimension(60 * 6 + 20, 190 + 25 + 40));

        frame.setContentPane(panel.getJPanel());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private class SubmitButtonListener implements ButtonListener {

        @Override
        public void onClick() {
            serverController.sendNewPosition(faders[0].getValue(), faders[1].getValue(), faders[2].getValue(), faders[3].getValue(), faders[4].getValue(), faders[5].getValue());
        }
    }

    private class ServerController {
        private final FSBClient fsbClient;

        ServerController() {
            fsbClient = new FSBClient(true);
        }

        void connect(String host, int port) throws IOException {
            fsbClient.connect(host, port);
        }

        void sendNewPosition(int l1, int l2, int l3, int l4, int l5, int l6) {
            try {
                fsbClient.sendNewPosition(new FSBPosition(l1, l2, l3, l4, l5, l6));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

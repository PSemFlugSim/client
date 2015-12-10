package gui_simple.src; /**
 * Here the panels and the tabs are created and the swing components are added to the panels.
 *
 * Created by Julian Hesse on 15.11.2014.
 */

import javax.swing.*;

import gui_simple.src.ManipulateCoordinatesPanel.event;

import static javax.swing.BoxLayout.PAGE_AXIS;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MainFrame extends JFrame {

    //Panel-array for pillarLength in degree and a panel to contain them
    JPanel[] panelPillarLength ;
    JPanel firstPanel;

    //Panel-array for indirect manipulation of pillars
    JPanel[] panelPillarManipulation;
    JPanel secondPanel;

    //Third and final panal
    TextFieldForInput[] textFieldPanel;
    PanelForPillarLength[] sliderForFinalPanel;
    JPanel thirdPanel;
    
    //event for Button
    event e;
    
    public MainFrame(String title) {
        super(title);

        //the first panel allow the user to manipulate the 6 pillars directly (lengthsInPercent)
        panelPillarLength = new PanelForPillarLength[6];

        for (int i = 0; i <= 5; i++){
            panelPillarLength[i] = new PanelForPillarLength(i, "Pillar "+ (i + 1) +" length in percent:");
        }

        firstPanel = new JPanel();
        firstPanel.setLayout(new BoxLayout(firstPanel, PAGE_AXIS));

        for (int i = 0; i <= 5; i++){
            firstPanel.add(panelPillarLength[i]);
        }


        //the second panel allows the user to define the x, y and z C

        secondPanel = new JPanel();
        //set Layout of second Panel
        secondPanel.setLayout(new BoxLayout(secondPanel, PAGE_AXIS));

        panelPillarManipulation = new PanelForPillarLength[6];

        panelPillarManipulation[0] = new PanelForPillarLength(0, "Position of X in percent: ", 0);
        panelPillarManipulation[1] = new PanelForPillarLength(1, "Position of Y in percent: ", 0);
        panelPillarManipulation[2] = new PanelForPillarLength(2, "Position of Z in percent: ", 0);
        panelPillarManipulation[3] = new PanelForPillarLength(3, "Rotation of X in degree: ", -180, 180, 0, 36, 18, 'd');
        panelPillarManipulation[4] = new PanelForPillarLength(4, "Rotation of Y in degree: ", -180, 180, 0, 36, 18, 'd');
        panelPillarManipulation[5] = new PanelForPillarLength(5, "Rotation of Z in degree: ", -180, 180, 0, 36, 18, 'd');

        for (int i = 0; i <= 5; i++){
           secondPanel.add(panelPillarManipulation[i]);
        }

        //the third Panel is probably used
        thirdPanel = new JPanel();

        thirdPanel.setLayout(new BoxLayout(thirdPanel, PAGE_AXIS));


        textFieldPanel = new TextFieldForInput[3];

        textFieldPanel[0] = new TextFieldForInput("Position of X: ", "cm");
        textFieldPanel[1] = new TextFieldForInput("Position of Y: ", "cm");
        textFieldPanel[2] = new TextFieldForInput("Position of Z: ", "cm");

        for (int i = 0; i < 3; i++){
            thirdPanel.add(textFieldPanel[i]);
        }


        sliderForFinalPanel = new PanelForPillarLength[3];

        sliderForFinalPanel[0] = new PanelForPillarLength(1, "Rotation of X in degree: ", -180, 180, 0, 36, 18, 'd');
        sliderForFinalPanel[1] = new PanelForPillarLength(2, "Rotation of Y in degree: ", -180, 180, 0, 36, 18, 'd');
        sliderForFinalPanel[2] = new PanelForPillarLength(3, "Rotation of Z in degree: ", -180, 180, 0, 36, 18, 'd');

        for (int i = 0; i < 3; i++){
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
				
				for (int v : values) System.out.println("v=" + v);
			}
		});

        thirdPanel.add(start);

        //create Tabs
        JPanel  tabbedPane = new JPanel();

        //add Panels as Tabs
        tabbedPane.add(thirdPanel);

        //super.add(firstPanel);
        super.setSize(650, 800);
        super.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        super.setVisible(true);
        super.add(tabbedPane);
    } 
}

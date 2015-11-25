package gui_simple.src;

/**
 * Created by Julian on 11.05.2015.
 */
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class PanelForPillarLength extends JPanel{

    protected static int PERCENT_MIN = 0;
    protected static int PERCENT_MAX = 100;
    protected static int PERCENT_INIT = 0;
    protected static int PERCENT_TIC = 10;
    protected static final int degree_init = 90;

    //Slider fro the Panel
    private JSlider pillar;

    //Label for degree in percent
    private JLabel label;

    //Title for Boarder
    private TitledBorder title;

    //Layout for JPanel
    private BorderLayout layout;

    //Dimension for the size of the panel
    private Dimension dimension2;

    //Event for JSlider
    private event e;

    //Char for event
    private char ev;

    public PanelForPillarLength(int number, String text, int percent_min, int percent_max, int percent_init, int majorTickSpacing, int minorTickSpacing, char event){
        pillar = new JSlider(JSlider.HORIZONTAL, percent_min, percent_max, percent_init);
        pillar.setMinorTickSpacing(PERCENT_TIC);
        pillar.setPaintLabels(true);
        pillar.setPaintTicks(true);
        pillar.setMajorTickSpacing(majorTickSpacing);
        pillar.setMinorTickSpacing(minorTickSpacing);
        pillar.createStandardLabels(1);

        Dimension dimension = new Dimension(60, 10);
        if(event == 'e') {
            label = new JLabel(percent_init + " %");
        }
        else{
            label = new JLabel(percent_init + " °");
        }
        label.setFont(new Font("Arial", Font.BOLD, 20));
        label.setMaximumSize(dimension);
        label.setPreferredSize(dimension);
        label.setHorizontalAlignment(SwingConstants.RIGHT);

        title = BorderFactory.createTitledBorder(text);

        layout = new BorderLayout();
        layout.setHgap(10);

        e = new event();
        
        ev = event;

        pillar.addChangeListener(e);

        dimension2 = new Dimension(600, 80); //Dimension for panel-size
        super.setPreferredSize(dimension2);
        super.setBorder(title);
        super.setLayout(layout);
        super.add(pillar, BorderLayout.CENTER);
        super.add(label, BorderLayout.EAST);
    }

    public PanelForPillarLength( int number, String text, int percent_min, int percent_max, int percent_init, char event ){

        super.add(new PanelForPillarLength( number, text, percent_min, percent_max, percent_init, 10, 5, event));
    }

    public PanelForPillarLength( int number, String text ){
        super.add(new PanelForPillarLength( number, text, PERCENT_MIN, PERCENT_MAX, PERCENT_INIT, 'e' ));
    }

    public PanelForPillarLength( int number, String text, int degree_init ){
        super.add(new PanelForPillarLength( number, text, -50, 50, degree_init, 'e' ));
    }
    public int GetValueOfSlider(){
        return pillar.getValue();
    }

    public void SetValueOfSlider(int value){
        pillar.setValue(value);
    }

    public class event implements ChangeListener {
        public void stateChanged(ChangeEvent e) {
            int value = pillar.getValue();

            if(ev == 'e') {
                label.setText(value + " %");
            }
            else{
                label.setText(value + " °");
            }
    }
}
}

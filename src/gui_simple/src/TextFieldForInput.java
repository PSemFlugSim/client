package gui_simple.src;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class TextFieldForInput extends JPanel{

    //Title for Boarder
    private TitledBorder title;

    private BorderLayout layout;

    //Textfield for the Input
    JTextField textField;

    //Label for unit
    JLabel label;

    //Dimension for the size of the panel
    private Dimension dimension;

    TextFieldForInput(String text, String unit){
        //Initiate Label
        label = new JLabel(unit);
        label.setFont(new Font("Arial", Font.BOLD, 20));
        label.setHorizontalAlignment(SwingConstants.RIGHT);

        Dimension dimension2 = new Dimension(60,10);

        label.setMaximumSize(dimension2);
        label.setPreferredSize(dimension2);

        //Initiate TextField
        textField = new JTextField("0");

        title = BorderFactory.createTitledBorder(text);

        layout = new BorderLayout();
        layout.setHgap(10);

        dimension = new Dimension(600, 80); //Dimension for panel-size
        super.setPreferredSize(dimension);
        super.setBorder(title);
        super.setLayout(new BorderLayout());

        super.add(textField, BorderLayout.CENTER);
        super.add(label, BorderLayout.EAST);
    }

}

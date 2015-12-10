package gui_simple.src; /**
 * Created by Julian Hesse on 15.11.2014.
 */
import javax.swing.*;
import java.awt.*;

public class GUI {
    public static void main(String[] args){
        SwingUtilities.invokeLater( new Runnable() {
            @Override
            public void run () {
                new MainFrame("Flugsimulator");
            }
        });

    }
}
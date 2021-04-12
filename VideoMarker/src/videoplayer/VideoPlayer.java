/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videoplayer;

import java.util.Arrays;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


/**
 *
 * @author ericr
 */
public class VideoPlayer {

    /**
     * @param args the command line arguments
     */
    private final ControlPanel controlPanel;
    private final JPanel mainPane;
    private final String mediaURL;
    private static final VideoPlayer VIDEO_TAGGER = new VideoPlayer();

    //Specify the look and feel to use.  Valid values:
    //null (use the default), "Metal", "System", "Motif", "GTK+"
    private VideoPlayer() {
//         mediaURL = "/home/ericr/tmp/mytest.mp4";
       // mediaURL = "/home/ericr/Videos/Wartburgia/MAH00514.MP4";
        // mediaURL = "/home/ericr/Videos/Goalie One/FILE0111.AVI";
        mediaURL = "/home/ericr/Videos/jvc/output.mp4";


        String homedir = System.getProperty("user.home");

        controlPanel = new ControlPanel(mediaURL);

        mainPane = new JPanel();

        mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.LINE_AXIS));
        mainPane.add(controlPanel);

        //playerPanel.startPlayer();
    }

    /**
     * Create the GUI and show it. For thread safety, this method should be
     * invoked from the event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Set the look and feel.
        // initLookAndFeel();

        //Create and set up the window.
        JFrame frame = new JFrame("VideoTagger");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(100, 100);

        //Create and set up the content pane.
        VIDEO_TAGGER.mainPane.setOpaque(true); // TODO (what does this mean): content panes must be opaque
        frame.setContentPane(VIDEO_TAGGER.mainPane);
        frame.setSize(1600, 800);

        //Display the window.
        //frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        try {
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException | ClassNotFoundException ex) {
            System.err.println(Arrays.toString(ex.getStackTrace()));
        }
        /* Turn off metal's use of bold fonts */
        UIManager.put("swing.boldMetal", Boolean.FALSE);
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(() -> {
            createAndShowGUI();
        });
    }

    
}

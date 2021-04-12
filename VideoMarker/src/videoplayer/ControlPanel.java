/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videoplayer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

/**
 *
 * @author ericr
 */
public class ControlPanel extends JPanel {

    private final String mediaURL;
    private final LineEdit le;
    private final EmbeddedMediaPlayerComponent mediaPlayerComponent;
    private EmbeddedMediaPlayer mediaPlayer;
    private ControlStatus status = ControlStatus.BEFORE;
    private boolean pause = false;
    private boolean start = false;
    private boolean cut = false;
    private boolean isAcceptOrCancel = false;
    private float speed = (float) 1.0;
    private TimeControl timeControl;
    final JButton cutButton;
    final JButton acceptTagButton;
    final JButton cancelTagButton;
    final JButton pauseButton;
    final JButton startButton;

    public ControlPanel(String mediaURL) {
        this.mediaURL = mediaURL;
        le = new LineEdit(mediaURL);
        mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
        mediaPlayer = mediaPlayerComponent.getMediaPlayer();

        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        ClickListener cl = new ClickListener();

        pauseButton = new JButton("Pause");
        pauseButton.addActionListener(cl);

        JButton incSpeedButton = new JButton("Increase speed");
        incSpeedButton.addActionListener((ActionEvent e) -> {
            speed = speed * 2;
            mediaPlayer.setRate(speed);
            timeControl.updateSpeedLabel();
        });
        JButton decSpeedButton = new JButton("Decrease speed");
        decSpeedButton.addActionListener((ActionEvent e) -> {
            speed = speed / 2;
            mediaPlayer.setRate(speed);
            timeControl.updateSpeedLabel();
        });
        startButton = new JButton("Start");
        startButton.addActionListener(cl);

        cutButton = new JButton("Start Cut");
        cutButton.addActionListener(cl);

        acceptTagButton = new JButton("Accept");
        acceptTagButton.addActionListener(cl);
        
        cancelTagButton = new JButton("Cancel");
        cancelTagButton.addActionListener(cl);

        acceptTagButton.setEnabled(false);
        cancelTagButton.setEnabled(false);
        pauseButton.setEnabled(false);
        cutButton.setEnabled(false);

        JPanel controlPanel = new JPanel();
        controlPanel.add(startButton);
        controlPanel.add(pauseButton);
        controlPanel.add(incSpeedButton);
        controlPanel.add(decSpeedButton);
        controlPanel.add(cutButton);
        controlPanel.add(acceptTagButton);
        controlPanel.add(cancelTagButton);

        timeControl = new TimeControl(mediaPlayer);
        this.add(mediaPlayerComponent);
        this.add(timeControl);
        this.add(controlPanel);

    }

    public void startPlayer() {
        timeControl.startTimer();
        mediaPlayer.playMedia(mediaURL);
    }

    public void stopPlayer() {
        mediaPlayer.stop();
        timeControl.stopTimer();
    }

    public long getTime() {
        return mediaPlayer.getTime();
    }

    private class ClickListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton b = (JButton) e.getSource();
            if (b == startButton) {
                start = !start;                
                pauseButton.setEnabled(start);
                cutButton.setEnabled(start);
                if (start) {
                    b.setText("Stop");
                    status = ControlStatus.RUNNING;
                    startPlayer();
                } else {
                    // pressing Stop
                    b.setText("Start");     // change label to Start
                    status = ControlStatus.FINISHED;
                    stopPlayer();           // stop the player
                }
            } else if (b == pauseButton) {
                pause = !pause;
                mediaPlayer.setPause(pause);
                if (pause) {
                    b.setText("Continue");
                    status = ControlStatus.PAUSED;
                } else {
                    b.setText("Pause");
                    status = ControlStatus.RUNNING;
                    acceptTagButton.setEnabled(!isAcceptOrCancel);
                    cancelTagButton.setEnabled(!isAcceptOrCancel);
                }
            } else if (b == cutButton) {
                long l = getTime();
                cut = !cut;
                acceptTagButton.setEnabled(!cut);
                cancelTagButton.setEnabled(!cut);
                isAcceptOrCancel = !cut;
                if (cut) {
                    le.setStartTime(l);
                    b.setText("Stop Cut");
                } else {
                    b.setText("Start Cut");
                    pauseButton.doClick();
                }
            } else if (b == cancelTagButton) {            
                le.reset();
                cut = false;
                acceptTagButton.setEnabled(false);
                cancelTagButton.setEnabled(false);
                cutButton.setText("Start Cut");            
            } else if (b == acceptTagButton) {
                long l = getTime();
                le.setEndTime(l);
                le.flushLine();
                le.reset();
                cut = false;
                acceptTagButton.setEnabled(false);
                cancelTagButton.setEnabled(false);
                cutButton.setText("Start Cut");
            }
        }
    }
}

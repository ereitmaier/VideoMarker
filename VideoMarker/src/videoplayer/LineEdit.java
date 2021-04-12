/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videoplayer;

import java.util.concurrent.TimeUnit;

/**
 *
 * @author ericr
 */
public class LineEdit {

    private long startTime = 0L;
    private long endTime = 0L;
    private long duration = 0L;
    private final String prefix = "edit";
    private final String filename;
    private int sequence = 0;

    public LineEdit(String filename) {
        this.filename = filename.substring(filename.lastIndexOf("/") + 1).substring(filename.lastIndexOf("\\") + 1);
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
        duration = (endTime) - startTime;
    }

    @Override
    public String toString() {
        return "LineEdit{" + "startTime=" + startTime + ", endTime=" + endTime + ", duration=" + duration + ", prefix=" + prefix + ", filename=" + filename + '}';
    }

    private String getTimeString(long milliSecs) {
        return String.format("%02d:%02d:%02d.%03d",
                TimeUnit.MILLISECONDS.toHours(milliSecs) % 24,
                TimeUnit.MILLISECONDS.toMinutes(milliSecs) % 60,
                TimeUnit.MILLISECONDS.toSeconds(milliSecs) % 60,
                milliSecs % 1000);
    }

    private String createString() {
        // 10,a,test.mp4,00:00:0,00:00:10.000,00:00:03.000    
        StringBuilder sb = new StringBuilder();
        sb.append(prefix);
        sb.append(",");
        sb.append(String.format("%02d", sequence));
        sb.append(",");
        sb.append(filename);
        sb.append(",");
        sb.append(getTimeString(startTime));
        sb.append(",");
        sb.append(getTimeString(endTime));
        sb.append(",");
        sb.append(getTimeString(duration));

        return sb.toString();
    }

    public void flushLine() {
        System.out.println(createString());
        sequence++;
        reset();
    }

    public void reset() {
        startTime = 0L;
        endTime = 0L;
        duration = 0L;
    }

}

package org.palax.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

/**
 * The {@code CompleteTestTimeFormat} class which extends {@link SimpleTagSupport} is a custom tag that is used to
 * format the complete test time
 *
 * @author Taras Palashynskyy
 */
public class CompleteTestTimeFormat extends SimpleTagSupport {

    private static final int SECONDS_IN_MINUTE = 60;
    private long time;

    public CompleteTestTimeFormat() {
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public void doTag() throws IOException {
        if(time > 0) {
            writeFormattedTime();
        } else
            writeZeroTime();
    }

    private void writeZeroTime() throws IOException {
        getJspContext().getOut().write(0);
    }
    
    private void writeFormattedTime() throws IOException {
        long minutes = time / SECONDS_IN_MINUTE;
        long seconds = time % SECONDS_IN_MINUTE;
        getJspContext().getOut().write(minutes + ":" + String.format("%02d", seconds));
    }
}

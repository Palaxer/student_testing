package org.palax.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The {@code DateTimeFormat} class which extends {@link SimpleTagSupport} is a custom tag that is used to
 * format the date depending on local attribute
 *
 * @author Taras Palashynskyy
 */
public class DateTimeFormat extends SimpleTagSupport {

    private LocalDateTime date;
    private String local;

    public DateTimeFormat() {
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    /**
     * Formats a date depending on locale attribute if a given locale is not present
     * is formed as follows: {@code "MM/dd/yyyy HH:mm"}
     */
    @Override
    public void doTag() throws IOException {
        if(date != null)
            writeFormattedDate();
    }

    private void writeFormattedDate() throws IOException {
        DateTimeFormatter df = getDateTimeFormatter();
        getJspContext().getOut().write(df.format(date));
    }

    private DateTimeFormatter getDateTimeFormatter() {
        DateTimeFormatter df;

        switch (local) {
            case "ru" :
                df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                break;
            case "en" :
                df = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
                break;
            default:
                df = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
                break;
        }

        return df;
    }



}

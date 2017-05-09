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
    /**The value is used for store {@code date} which will be pass in the attribute
     * and represents by {@link LocalDateTime}. */
    private LocalDateTime date;
    /**The value is used for store {@code local} which will be pass in the attribute
     * and used to select which format will be use to date. */
    private String local;

    public DateTimeFormat() {
    }

    /**
     * Sets {@code date} attribute and represents by {@link Timestamp}
     *
     * @param date {@code date} attribute
     */
    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    /**
     * Sets {@code local} attribute which will be used to select which format will be use to date
     *
     * @param local {@code date} attribute
     */
    public void setLocal(String local) {
        this.local = local;
    }

    /**
     * Formats a date depending on locale attribute if a given locale is not present
     * is formed as follows: {@code "MM/dd/yyyy HH:mm"}
     *
     * @throws JspException {@link JspException}
     * @throws IOException {@link IOException}
     */
    @Override
    public void doTag() throws JspException, IOException {

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

        if(date != null)
            getJspContext().getOut().write(df.format(date));
    }
}

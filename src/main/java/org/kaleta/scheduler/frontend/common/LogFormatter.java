package org.kaleta.scheduler.frontend.common;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Date;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

/**
 * Created by Stanislav Kaleta on 23.06.2015.
 *
 * Basic log formatter.
 */
public class LogFormatter extends SimpleFormatter {

    @Override
    public String format(LogRecord rec){
        StringBuilder sb = new StringBuilder();

        Date date = new Date(rec.getMillis());
        sb.append(date.toString());
        sb.append(" | ");
        Level lvl = rec.getLevel();
        sb.append(lvl);
        sb.append(" | ");
        sb.append(formatMessage(rec));
        sb.append("\n");

        return sb.toString();
    }

    @Override
    public String getHead(Handler h){
        Date date = new Date(System.currentTimeMillis());
        return "######### Application started #########\nstarting time: "+date.toString() + "\n";
    }

    @Override
    public String getTail(Handler h) {
        return "######### Application ended #########\n";
    }
}

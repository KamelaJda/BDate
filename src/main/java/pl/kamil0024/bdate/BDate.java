package pl.kamil0024.bdate;

import lombok.Data;
import org.joda.time.DateTime;
import pl.kamil0024.bdate.util.BLanguage;
import pl.kamil0024.bdate.util.Time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@SuppressWarnings("UnusedReturnValue")
@Data
public class BDate {

    private long timestamp;
    private DateTime dateTime;
    private BLanguage lang = null;

    public BDate() {
        this.timestamp = System.currentTimeMillis();
        this.dateTime = new DateTime(this.timestamp);
    }

    public BDate(long timestamp) {
        this.timestamp = timestamp;
        this.dateTime = new DateTime(timestamp);
    }

    public BDate(BLanguage lang) {
        this.timestamp = System.currentTimeMillis();
        this.dateTime = new DateTime(this.timestamp);
        this.lang = lang;
    }

    public BDate(long timestamp, BLanguage lang) {
        this.timestamp = timestamp;
        this.dateTime = new DateTime(this.timestamp);
        this.lang = lang;
    }

    public BDate(long timestamp, TimeZone timeZone) {
        this.timestamp = timestamp - (timeZone.getOffset(timestamp));
        this.dateTime = new DateTime(this.timestamp);
    }

    public String difference(long ms) {
        return new Timespan(getTimestamp(), lang).difference(ms);
    }

    public BDate add(long count, Time type) {
        return set(getTimestamp() + (type.getMs() * count));
    }

    public BDate remove(long count, Time type) {
        return set(getTimestamp() - (type.getMs() * count));
    }

    public BDate set(long ms) {
        setTimestamp(ms);
        setDateTime(new DateTime(getTimestamp()));
        return this;
    }

    public String difference(BDate bdate) {
        return difference(bdate.getTimestamp());
    }

    public String getDate(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(getTimestamp()));
    }

    public String getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat();
        return sdf.format(new Date(getTimestamp()));
    }

    public static BDate now() {
        return new BDate(System.currentTimeMillis());
    }
    
    public static BDate parse(Date date) {
        return new BDate(date.getTime());
    }
    
    public static BDate parse(String pattern, String date) throws ParseException {
        return new BDate(new SimpleDateFormat(pattern).parse(date).getTime());
    }

}

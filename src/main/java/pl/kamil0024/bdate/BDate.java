package pl.kamil0024.bdate;

import lombok.Data;
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
    private BLanguage lang = null;

    public BDate() {
        this.timestamp = System.currentTimeMillis();
    }

    public BDate(long timestamp) {
        this.timestamp = timestamp;
    }

    public BDate(BLanguage lang) {
        this.timestamp = System.currentTimeMillis();
        this.lang = lang;
    }

    public BDate(long timestamp, BLanguage lang) {
        this.timestamp = timestamp;
        this.lang = lang;
    }

    public BDate(long timestamp, TimeZone timeZone) {
        this.timestamp = timestamp - (timeZone.getOffset(timestamp));
    }

    public String difference(long ms) {
        return new Timespan(getTimestamp(), lang).difference(ms);
    }

    public BDate add(long count, Time type) {
        setTimestamp(getTimestamp() + (type.getMs() * count));
        return this;
    }

    public BDate remove(long count, Time type) {
        setTimestamp(getTimestamp() - (type.getMs() * count));
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

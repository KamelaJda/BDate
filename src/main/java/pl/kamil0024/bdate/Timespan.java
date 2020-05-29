package pl.kamil0024.bdate;

import lombok.Data;
import org.joda.time.*;
import pl.kamil0024.bdate.util.BLanguage;

import java.util.Date;

@SuppressWarnings("SpellCheckingInspection")
@Data
public class Timespan {

    private long kiedys = 0;
    private BLanguage lang = null;

    public Timespan(long kiedys, BLanguage lang) {
        this.kiedys = kiedys;
        this.lang = lang;
    }

    public String difference(long pozniej) {
        if (lang == null) throw new UnsupportedOperationException("BLanguage lang == null");
        Date teraz = new Date(kiedys);
        Date nowa = new Date(pozniej);
        StringBuilder sb = new StringBuilder();

        DateTime date1 = new DateTime(teraz);
        DateTime date2 = new DateTime(nowa);

        String day = String.valueOf(Days.daysBetween(date1, date2).getDays());
        String hour = String.valueOf(Hours.hoursBetween(date1, date2).getHours() % 24);
        String minute = String.valueOf(Minutes.minutesBetween(date1, date2).getMinutes() % 60);
        String secound = String.valueOf(Seconds.secondsBetween(date1, date2).getSeconds() % 60);

        String[] list = new String[] {day, hour, minute, secound};
        int i = 0;

        for (String s : list) {
            i++;
            if (!s.equals("0")) sb.append(format(i, s)).append(" ");
        }
        String res = sb.toString();
        return res.substring(0, res.length() - 1);
    }

    private String format(int i, String s) {
        String czas = null;
        if (i == 1) czas = getLang().getDay();
        if (i == 2) czas = getLang().getHour();
        if (i == 3) czas = getLang().getMinute();
        if (i == 4) czas = getLang().getSecond();
        return s + " " + czas;
    }

}

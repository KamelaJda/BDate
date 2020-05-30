import org.joda.time.DateTime;
import pl.kamil0024.bdate.BDate;
import pl.kamil0024.bdate.util.BLanguage;
import pl.kamil0024.bdate.util.Time;

import java.text.ParseException;
import java.util.Date;

import static org.junit.Assert.*;

@SuppressWarnings("SpellCheckingInspection")
public class Test {

    @org.junit.Test
    public void testDifference() {
        BDate stara = new BDate(getLang()); // bierzemy czas teraźniejszy
        BDate nowa = new BDate(new Date().getTime() + 5340000, getLang()); // dodajemy 1 godz. i 30 min. do czasu teraźniejszego
        String dif = stara.difference(nowa); // bierzemy róznice
        assertEquals("1 godz. 29 min.", dif);
    }

    @org.junit.Test
    public void cooldownEarlierTest() {
        BDate now = new BDate(getLang()); // bierzemy czas terazniejszy
        BDate cooldown = new BDate(now.getTimestamp(), getLang()); // bierzemy czas kiedy sie cooldown konczy
        cooldown.remove(3, Time.DAY); // odejmujemy czas - możemy go też dodać ofc (symulowanie cooldownu)
        assertFalse(cooldown.getTimestamp() - now.getTimestamp() > 0);
        assertEquals("-3 d.", now.difference(cooldown));
    }

    @org.junit.Test
    public void cooldownLaterTest() {
        BDate now = new BDate(getLang()); // bierzemy czas terazniejszy
        BDate cooldown = new BDate(getLang()); // bierzemy czas kiedy sie cooldown konczy
        cooldown.add(3, Time.DAY); // odejmujemy czas - możemy go też dodać ofc (symulowanie cooldownu)
        assertTrue(cooldown.getTimestamp() - now.getTimestamp() > 0);
        assertEquals("3 d.", now.difference(cooldown));
    }

    @org.junit.Test
    public void test2() throws ParseException { // pomysły = null
        assertFalse(betweenHoursInOtherDay("19:00:00", "5:00:00", "17:00:00"));
    }

    @org.junit.Test
    public void test3() throws ParseException { // pomysły = null
        assertTrue(betweenHoursInOtherDay("19:00:00", "5:00:00", "20:00:00"));
    }

    @org.junit.Test
    public void test4() throws ParseException { // pomysły = null
        assertTrue(betweenHoursInOtherDay("19:00:00", "5:00:00", "3:00:00"));
    }

    @org.junit.Test
    public void test5() throws ParseException { // pomysły = null
        assertFalse(betweenHoursInOtherDay("19:00:00", "5:00:00", "8:00:00"));
    }

    @org.junit.Test
    public void timestampShouldMatchDateTimeAdd() {
        BDate d = new BDate(getLang());
        d.add(1, Time.DAY);
        assertEquals(d.getTimestamp(), d.getDateTime().getMillis());
    }

    @org.junit.Test
    public void timestampShouldMatchDateTimeRemove() {
        BDate d = new BDate(getLang());
        d.remove(1, Time.DAY);
        assertEquals(d.getTimestamp(), d.getDateTime().getMillis());
    }

    public static boolean betweenHours(int date1hour, int date1min, int date2hour, int date2min) {
        DateTime dt = new DateTime();
        int min = dt.getMinuteOfHour();
        int hour = dt.getHourOfDay();

        boolean minimum = hour >= date1hour && min >= date1min;
        boolean max = hour <= date2hour;
        if (max && min > date2min) max = false; // to serio musi tak być xD

        return minimum && max;
    }

    public static boolean betweenHoursInOtherDay(String date1, String date2, String teraz) throws ParseException { // sprawdzamy czy jest pomiędzy 19 a 5
        String   pat    = "HH:mm:ss";
        BDate    start  = BDate.parse(pat, date1);
        BDate    end    = BDate.parse(pat, date2).add(1, Time.DAY);
        BDate    now    = BDate.parse(pat, teraz);
        BDate    now1   = BDate.parse(pat, teraz).add(1, Time.DAY);

        return now.getTimestamp() > start.getTimestamp() || now1.getTimestamp() < end.getTimestamp();
    }
    
    private static BLanguage getLang() { // ustawiamy jezyk
        BLanguage blang = new BLanguage();
        blang.setDay("d.");
        blang.setHour("godz.");
        blang.setMinute("min.");
        blang.setSecond("sek.");
        return blang;
    }

}

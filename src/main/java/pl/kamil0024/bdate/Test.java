package pl.kamil0024.bdate;

import org.joda.time.DateTime;
import pl.kamil0024.bdate.util.BLanguage;
import pl.kamil0024.bdate.util.Time;

import java.text.ParseException;
import java.util.Date;

@SuppressWarnings("SpellCheckingInspection")
public class Test {

    public static void main(String[] args) throws ParseException {
        BDate stara = new BDate(getLang()); // bierzemy czas teraźniejszy
        BDate nowa = new BDate(new Date().getTime() + 5340000, getLang()); // dodajemy 1 godz. i 30 min. do czasu teraźniejszego
        String dif = stara.difference(nowa); // bierzemy róznice

        System.out.println("Tak: " + dif);
        System.out.println("---------------------------------");
        // wyskoczy nam "Tak: 1 godz. 29 min."

        simpleCooldown();
        System.out.println("test1: " + betweenHours(7, 5, 20, 50)); // sprwadzamy czy jest pomiędzy 17:05 a 20:50
        System.out.println("test2: " + betweenHoursInOtherDay("19:00:00", "5:00:00", "17:00:00")); // false
        System.out.println("test3: " + betweenHoursInOtherDay("19:00:00", "5:00:00", "20:00:00")); // true
        System.out.println("test4: " + betweenHoursInOtherDay("19:00:00", "5:00:00", "3:00:00")); // true
        System.out.println("test5: " + betweenHoursInOtherDay("19:00:00", "5:00:00", "8:00:00")); // false
    }
    
    public static void simpleCooldown() {
        BDate now = new BDate(getLang()); // bierzemy czas terazniejszy
        BDate cooldown = new BDate(getLang()); // bierzemy czas kiedy sie cooldown konczy
        cooldown.remove(3, Time.DAY); // odejmujemy czas - możemy go też dodać ofc (symulowanie cooldownu)

        if (cooldown.getTimestamp() - now.getTimestamp() > 0) { // jeżeli cooldown się nie skończył
            System.out.println("Musisz poczekać jeszcze: " + now.difference(cooldown));
            // w tym przypadku wyskoczy "Musisz poczekać jeszcze 3 d."
        } else System.out.println("Nagrodę mogłeś odebrać " + cooldown.difference(now) + " temu");
            // w tym przypadku wyskoczy na "Nagrodę mogłeś odebrać 3 d. temu"
        System.out.println("---------------------------------");
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

    public static boolean betweenHoursInOtherDay(String date1, String date2) throws ParseException {
        BDate bt     = new BDate();
        String   st    =  bt.getDateTime().getHourOfDay() + ":" + bt.getDateTime().getMinuteOfHour() + ":00";
        return betweenHoursInOtherDay(date1, date2, st);
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

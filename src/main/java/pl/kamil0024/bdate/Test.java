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
        System.out.println("Kekw1:" + betweenHours());
        System.out.println("Kekw2:" + betweenHoursInOtherDay());
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

    public static boolean betweenHours() { // sprwadzamy czy jest pomiędzy 17:05 a 18:50
        DateTime dt = new DateTime();
        int min = dt.getMinuteOfHour();
        int hour = dt.getHourOfDay();

        boolean minimum = hour >= 17 && min >= 5;
        boolean max = hour <= 18;
        if (max && min > 50) max = false; // to serio musi tak być xD

        return minimum && max;
    }

    public static boolean betweenHoursInOtherDay() throws ParseException { // sprawdzamy czy jest pomiędzy 19 a 5
        String   pat    = "HH:mm:ss";
        BDate    start  = BDate.parse(pat, "19:00:21");
        BDate    end    = BDate.parse(pat, "5:00:37").add(1, Time.DAY);
        DateTime dt     = new DateTime();
        BDate    now    = BDate.parse(pat, dt.getHourOfDay() + ":" + dt.getMinuteOfHour() + ":00");

        return now.getTimestamp() > start.getTimestamp() && now.getTimestamp() < end.getTimestamp();
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

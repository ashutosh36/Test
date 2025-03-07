package src.kafka;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateParser {

    public static void main(String[] args) throws Exception {
//        new DateParser().LocalDateTimeRunner();
//        new DateParser().LocalDateRunner();

        String input= "clv";
        DataCenter dataCenter= DataCenter.valueOf("CALVIN");
        System.out.println(dataCenter.name);
        System.out.println(dataCenter);
    }
    void LocalDateTimeRunner(){
        String str = "2023-11-01 12:30";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime localDateTime = LocalDateTime.parse(str, formatter);
        ZoneOffset zoneOffset= ZoneId.systemDefault().getRules().getOffset(Instant.now());
        localDateTime.toInstant(zoneOffset).toEpochMilli();

        Long epoch= localDateTime.toEpochSecond(zoneOffset);
        System.out.println(epoch);

        long timestampInMillis =epoch*1000l;
        LocalDateTime obtainedLocalDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestampInMillis), ZoneId.systemDefault());
        System.out.println(obtainedLocalDateTime);

        LocalDateTime now= LocalDateTime.now(ZoneId.systemDefault());
        System.out.println(now);


        LocalDate date= localDateTime.toLocalDate();
        System.out.println(date);
    }

    public enum DataCenter {
        CALVIN("clv"),
        HYDERABAD("hyd");

        private String name;

        DataCenter(String name){
            this.name= name;
        }

    }

    void LocalDateRunner() throws Exception{
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT"));

        String dateInString = "1-Nov-2023 12:30";
        Date date = formatter.parse(dateInString);
        System.out.println(date);

        long epoch= date.getTime();
        System.out.println(epoch);

        Date obtainedDate= new Date(epoch);
        System.out.println(obtainedDate);
    }
}

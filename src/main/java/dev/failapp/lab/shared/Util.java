package dev.failapp.lab.shared;

import org.jboss.logging.Logger;

import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class Util {

    private static final Logger log = Logger.getLogger(Util.class);


    public static final DateTimeFormatter formatDateTime = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    public static final DateTimeFormatter formatDateTimeUI = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    public static final DateTimeFormatter formatDateTimeApp = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    public static void sleep(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            //throw new IllegalStateException(e);
            log.errorf("[x] error: %s", e.getMessage());
        }
    }


}

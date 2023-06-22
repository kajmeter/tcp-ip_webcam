import java.text.SimpleDateFormat;
import java.util.Date;

public class logger {
    public static void log(String text){
        SimpleDateFormat formatter= new SimpleDateFormat("[HH:mm:ss]");
        Date date = new Date(System.currentTimeMillis());
        System.out.println(formatter.format(date)+" "+text);
    }
    public static void log(String text,String prename){
        SimpleDateFormat formatter= new SimpleDateFormat("[HH:mm:ss]");
        Date date = new Date(System.currentTimeMillis());
        System.out.println("["+prename+"]"+formatter.format(date)+" "+text);
    }
}

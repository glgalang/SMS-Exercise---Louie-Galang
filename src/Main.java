import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    final private static Logger logger = Logger.getLogger(Main.class.getName());
    public static void main(String[] args) {
        smsCheck();
        validate(smsCheck());

    }
    public static Map<String, String> smsCheck() {
        Map<String,String> checkMap = new HashMap<>();
        checkMap.put("msisdn",Sms.getMsisdn());
        checkMap.put("Message",Sms.getMessage());
        checkMap.put("ShortCode",Sms.getShortcode());
        //System.out.println(checkMap);
        return checkMap;
    }
    public static void validate(Map<String, String> checkMap) {
    Iterator<Map.Entry<String, String>> itr = checkMap.entrySet().iterator();
        while (itr.hasNext()) {
            Map.Entry<String, String> entry = itr.next();
            logger.log(Level.INFO, "Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }
        if (String.valueOf(checkMap.get("msisdn")).length() == 13 &&
                (String.valueOf(checkMap.get("ShortCode")).length() == 7 &&
                        (String.valueOf(checkMap.get("Message")) != ""))) {
                logger.log(Level.INFO, "Promo Accepted");
        }else{
                logger.log(Level.WARNING, "Invalid Format, Check Right Format");
        }

    }


}

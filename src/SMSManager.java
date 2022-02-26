import java.util.ArrayList;

public interface SMSManager {
    void insertSMS();
    ArrayList<Sms> retrieveSMSbyDate(String startDate, String endDate);
    ArrayList<Sms> retrieveSMSbyPromoCode(String promocode);
    ArrayList<Sms> retrieveSMSbyMSISDN(String msisdn);
    ArrayList<Sms> retrieveSMSSentBySystem(String sender);
    ArrayList<Sms> retrieveSMSReceiveBySystem(String recipient);
    ArrayList<Sms> retrieveSMSGivenSeveralMSISDN(String[] msisdn);

}

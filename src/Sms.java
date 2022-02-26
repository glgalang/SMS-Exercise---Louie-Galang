import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Sms {
    private static String msisdn; //mobile number
    private String recipient; //store
    private String sender; //sender credentials
    private static String shortcode; //number for promos
    private String transactionID; //transactions identity
    private static String message;
    private String status;
    private static LocalDateTime timestamp;

    public Sms(String msisdn, String recipient, String sender, String shortCode, String transactionID, LocalDateTime timestamp) {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public static String getShortcode() {
        return shortcode;
    }

    public void setShortcode(String shortcode) {
        this.shortcode = shortcode;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public static LocalDateTime getTimestamp() {
        return timestamp;
    }

    public static void setTimestamp(LocalDateTime timestamp) {
        Sms.timestamp = timestamp;
    }
    public String getFormattedTimestamp(){
        return this.timestamp.format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}

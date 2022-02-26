import java.util.ArrayList;

public interface Reports {
    ArrayList<Sms> listOfFailedTransactions();
    ArrayList<Sms> listOfFailedTransactionsByType(String sender,String recipient);
    ArrayList<Sms> listOfSuccessfulTransactions();
    ArrayList<Sms> listOfSuccessfulTransactionsByType(String sender,String recipient);
    ArrayList<Sms> PersonsJoinedPromo();
    int totalSMSReceived();
    int totalSMSSent();
}

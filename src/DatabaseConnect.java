import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseConnect implements SMSManager,Reports{
    final private static Logger logger = Logger.getLogger(DatabaseConnect.class.getName());
    private static Connection con = null;
    private Statement statement = null;
    private ResultSet resultSet = null;
    private String query;
    private Sms sms = null;

    public static void main(String[] args) {
        DatabaseConnect.connect();

        DatabaseConnect.disconnect();
    }

    public static void connect() {
        try {
            Properties properties = getProperties();
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                    properties.getProperty("db.url"),
                    properties.getProperty("db.user"),
                    properties.getProperty("db.password"));
            logger.info("Connected");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Not Connected", e);
        }
    }

    private static Properties getProperties() {
        Properties properties = new Properties();
        try {
            properties.load(
                    new FileInputStream(
                            "/Users/louieangelo.galang/IdeaProjects/Lab 2/src/config.properties")
            );

        } catch (IOException exception) {
            logger.log(Level.SEVERE, "IOException: ", exception);
        }
        return properties;
    }

    public static void disconnect() {
        try {
            if (con != null) {
                con.close();
                logger.info("Disconnected");
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Not Connected", e);
        }
    }

    public String insertPromo(Promo.Promos promo) {
        query = "INSERT INTO SMS.table_promo " +
                "(details, shortcode, startdate, enddate, promo_code) " +
                "values (" +
                "'" + promo.getDetails() + "', " +
                "'" + promo.getShortcode() + "', " +
                "'" + promo.getFormattedStart_date() + "', " +
                "'" + promo.getFormattedEnd_date() + "', " +
                "'" + promo.getPromoCode() + "' )";

        try {
            statement = con.createStatement();
            statement.execute(query);

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "SQLException: ", e);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }

            } catch (Exception e) {
                logger.log(Level.SEVERE, "ERROR IN CLOSING: ", e);
            }
        }

        logger.log(Level.INFO, "INSERTED DATA: " + promo.toString());
        disconnect();
        return "Created Promo: " + promo.getDetails();
    }


    public void insertSMS(Sms sms) {
        query = "INSERT INTO SMS.table_sms " +
                "(msisdn, recipient, sender, shortCode, " +
                "transaction_id, timestamp) " +
                "values ( " +
                "'" + sms.getMsisdn() + "', " +
                "'" + sms.getRecipient() + "', " +
                "'" + sms.getSender() + "', " +
                "'" + sms.getShortcode() + "', " +
                "'" + sms.getTransactionID() + "', " +
                "'" + sms.getFormattedTimestamp() + "' )";

        try {
            statement = con.createStatement();
            statement.execute(query);

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "SQLException: ", e);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }

            } catch (Exception e) {
                logger.log(Level.SEVERE, "ERROR IN CLOSING: ", e);
            }
        }

        logger.log(Level.INFO, "INSERTED DATA: " + sms.toString());
        disconnect();
    }

    @Override
    public void insertSMS() {

    }

    @Override
    public ArrayList<Sms> retrieveSMSbyDate(String startDate, String endDate) {

        ArrayList<Sms> smsList = new ArrayList<>();

        query = "SELECT * FROM SMS.table_sms " +
                "WHERE timestamp BETWEEN '" + startDate +
                "' AND '" + endDate + "' ";

        return retrieveSMSData(smsList, query);
    }

    @Override
    public ArrayList<Sms> retrieveSMSbyPromoCode(String promocode) {
        ArrayList<Sms> smsList = new ArrayList<>();
        query = "SELECT * FROM SMS.table_sms AS s " +
                "INNER JOIN SMS.table_promo AS p " +
                "ON s.shortCode = p.shortCode " +
                "WHERE p.promoCode = '" + promocode + "' ";

        return retrieveSMSData(smsList, query);
    }

    @Override
    public ArrayList<Sms> retrieveSMSbyMSISDN(String msisdn) {
        ArrayList<Sms> smsList = new ArrayList<>();

        query = "SELECT * FROM SMS.table_sms " +
                "WHERE msisdn = '" + msisdn + "' ";

        return retrieveSMSData(smsList, query);
    }

    @Override
    public ArrayList<Sms> retrieveSMSSentBySystem(String sender) {
        ArrayList<Sms> smsList = new ArrayList<>();

        query = "SELECT * FROM SMS.table_sms " +
                "WHERE sender = '" + sender + "' ";

        return retrieveSMSData(smsList, query);
    }

    @Override
    public ArrayList<Sms> retrieveSMSReceiveBySystem(String recipient) {
        ArrayList<Sms> smsList = new ArrayList<>();

        query = "SELECT * FROM SMS.table_sms " +
                "WHERE recipient = '" + recipient + "' ";

        return retrieveSMSData(smsList, query);
    }

    @Override
    public ArrayList<Sms> retrieveSMSGivenSeveralMSISDN(String[] msisdn) {
        ArrayList<Sms> smsList = new ArrayList<>();
        query = "SELECT * FROM SMS.table_sms " +
                "WHERE msisdn = '" + msisdn + "' OR " + "msisdn = '"+ msisdn + "' ";

        return retrieveSMSData(smsList, query);
    }

    private ArrayList<Sms> retrieveSMSData(ArrayList<Sms> smsList, String query) {
        try {
            statement = con.createStatement();
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                sms = new Sms(
                        resultSet.getString("msisdn"),
                        resultSet.getString("recipient"),
                        resultSet.getString("sender"),
                        resultSet.getString("shortCode"),
                        resultSet.getString("transactionID"),
                        LocalDateTime.parse(
                                resultSet.getString("timestamp"),
                                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                );
                smsList.add(sms);

                logger.log(Level.INFO, sms.toString());
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "SQLException: ", e);

        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (Exception e) {
                logger.log(Level.SEVERE, "ERROR IN CLOSING: ", e);
            }
        }
        logger.log(Level.INFO, "Retrieved : {0}", smsList);

        disconnect();
        return smsList;
    }

    @Override
    public ArrayList<Sms> listOfFailedTransactions() {
        ArrayList<Sms> smsList = new ArrayList<>();
        query = "SELECT * FROM SMS.table_sms " +
                "WHERE status = 'Failed' ";

        return retrieveSMSData(smsList, query);
    }

    @Override
    public ArrayList<Sms> listOfFailedTransactionsByType(String sender,String recipient) {
        ArrayList<Sms> smsList = new ArrayList<>();
        query = "SELECT * FROM SMS.table_sms " +
                "WHERE status = 'Failed' AND " +
                "sender = '"+ sender + "' AND " +
                "recipient = '" + recipient + "'";

        return retrieveSMSData(smsList, query);
    }

    @Override
    public ArrayList<Sms> listOfSuccessfulTransactions() {
        ArrayList<Sms> smsList = new ArrayList<>();
        query = "SELECT * FROM SMS.table_sms " +
                "WHERE status = 'Success' ";

        return retrieveSMSData(smsList, query);
    }

    @Override
    public ArrayList<Sms> listOfSuccessfulTransactionsByType(String sender, String recipient) {
        ArrayList<Sms> smsList = new ArrayList<>();
        query = "SELECT * FROM SMS.table_sms " +
                "WHERE status = 'Success' AND " +
                "sender = '"+ sender + "' AND " +
                "recipient = '" + recipient + "'";

        return retrieveSMSData(smsList, query);
    }

    @Override
    public ArrayList<Sms> PersonsJoinedPromo() {
        ArrayList<Sms> smsList = new ArrayList<>();
        query = "SELECT * FROM SMS.table_sms " +
                "WHERE sender != 'System' ";

        return retrieveSMSData(smsList, query);
    }

    @Override
    public int totalSMSReceived() {

        query = "SELECT COUNT(recipient) FROM SMS.table_sms " +
                "WHERE recipient = 'System' ";
        Statement statement = null;
        ResultSet resultSet = null;
        int count = 0;

        connect();

        try {
            statement = con.createStatement();
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "SQLException", e);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (Exception e) {
                logger.log(Level.SEVERE, "ERROR IN CLOSING", e);
            }
        }
        disconnect();
        return count;
    }
    @Override
    public int totalSMSSent() {

        query = "SELECT COUNT(sender) FROM SMS.table_sms " +
                "WHERE sender != 'System' ";
        Statement statement = null;
        ResultSet resultSet = null;
        int count = 0;

        connect();

        try {
            statement = con.createStatement();
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "SQLException", e);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (Exception e) {
                logger.log(Level.SEVERE, "ERROR IN CLOSING", e);
            }
        }
        disconnect();
        return count;
    }
}



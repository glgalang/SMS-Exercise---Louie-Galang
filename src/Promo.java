import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

public class Promo {

    enum Promos {
        PIZZA("P1ZZA",
                "Piso Pizza",
                "1234555",
                LocalDateTime.of(2021, 02, 01, 10, 0),
                LocalDateTime.of(2021, 06, 30, 23, 59)),

        POWER("P0WER",
                "People Power - 50% off on PowerMac Devices",
                "9693722",
                LocalDateTime.of(2022, 02, 26, 9, 0),
                LocalDateTime.of(2022, 02, 28, 22, 59)),

        CRYPTO("CRYPTO",
                "Earn 100K BNB Trial Fund - Learn and Trade",
                "2778622",
                LocalDateTime.of(2022, 03, 01, 04, 0),
                LocalDateTime.of(2022, 9, 06, 23, 59));

        private String promocode;
        private String details;
        private String shortcode;
        private LocalDateTime start_date;
        private LocalDateTime end_date;

        Promos(String promocode, String details, String shortcode, LocalDateTime start_date, LocalDateTime end_date) {
            this.promocode = promocode;
            this.details = details;
            this.shortcode = shortcode;
            this.start_date = start_date;
            this.end_date = end_date;
        }


        public String getPromoCode() {
            return promocode;
        }

        public void setPromoCode(String promocode) {
            this.promocode = promocode;
        }

        public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
        }

        public String getShortcode() {
            return shortcode;
        }

        public void setShortcode(String shortcode) {
            this.shortcode = shortcode;
        }

        public LocalDateTime getStart_date() {
            return start_date;
        }

        public void setStart_date(LocalDateTime start_date) {
            this.start_date = start_date;
        }

        public LocalDateTime getEnd_date() {
            return end_date;
        }

        public void setEnd_date(LocalDateTime end_date) {
            this.end_date = end_date;
        }

        public String getFormattedStart_date() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    .format(this.start_date);
        }

        public String getFormattedEnd_date() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    .format(this.end_date);
        }
    }
}

package util;

import java.util.Random;

public class IDGenerator {
    private static final Random random = new Random();

    public static String generateTicketCode() {
        int number = 100000 + random.nextInt(900000);
        return "TK-" + number;
    }

    public static String generateBookingCode() {
        int number = 100000 + random.nextInt(900000);
        return "BK-" + number;
    }
}

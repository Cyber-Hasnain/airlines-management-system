package util;

import java.util.regex.Pattern;

public class Validator {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$");
    private static final Pattern CNIC_PATTERN = Pattern.compile("^\\d{5}-\\d{7}-\\d{1}$");
    private static final Pattern SIMPLE_CNIC_PATTERN = Pattern.compile("^\\d{13}$");

    public static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    public static boolean isValidEmail(String email) {
        if (isEmpty(email)) return false;
        return EMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean isValidCNIC(String cnic) {
        if (isEmpty(cnic)) return false;
        return CNIC_PATTERN.matcher(cnic).matches() || SIMPLE_CNIC_PATTERN.matcher(cnic).matches();
    }

    public static boolean isValidPhone(String phone) {
        if (isEmpty(phone)) return false;
        return phone.matches("^\\+?\\d{10,15}$");
    }

    public static boolean isPositiveDouble(String val) {
        try {
            double d = Double.parseDouble(val);
            return d >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isPositiveInt(String val) {
        try {
            int i = Integer.parseInt(val);
            return i >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}

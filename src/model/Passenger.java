package model;

public class Passenger {
    private int passengerId;
    private String fullName;
    private String cnic;
    private String passportNumber;
    private String gender;
    private String phoneNumber;
    private String email;

    public Passenger() {}

    public Passenger(int passengerId, String fullName, String cnic, String passportNumber, String gender, String phoneNumber, String email) {
        this.passengerId = passengerId;
        this.fullName = fullName;
        this.cnic = cnic;
        this.passportNumber = passportNumber;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public Passenger(String fullName, String cnic, String passportNumber, String gender, String phoneNumber, String email) {
        this.fullName = fullName;
        this.cnic = cnic;
        this.passportNumber = passportNumber;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public int getPassengerId() { return passengerId; }
    public void setPassengerId(int passengerId) { this.passengerId = passengerId; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getCnic() { return cnic; }
    public void setCnic(String cnic) { this.cnic = cnic; }

    public String getPassportNumber() { return passportNumber; }
    public void setPassportNumber(String passportNumber) { this.passportNumber = passportNumber; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public String toString() {
        return fullName + " (ID: " + passengerId + " | CNIC: " + cnic + ")";
    }
}

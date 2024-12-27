package model;

public class Customer {
    private int c_id;
    private String c_firstName;
    private String c_lastName;
    private String c_email;
    private String c_address;
    private String c_city;
    private String c_country;
    private String c_phoneNbr;
    private String c_password;

    public int getC_id() {
        return c_id;
    }

    public void setC_id(int c_id) {
        this.c_id = c_id;
    }

    public String getC_firstName() {
        return c_firstName;
    }

    public void setC_firstName(String c_firstName) {
        this.c_firstName = c_firstName;
    }

    public String getC_lastName() {
        return c_lastName;
    }

    public void setC_lastName(String c_lastName) {
        this.c_lastName = c_lastName;
    }

    public String getC_email() {
        return c_email;
    }

    public void setC_email(String c_email) {
        this.c_email = c_email;
    }

    public String getC_address() {
        return c_address;
    }

    public void setC_address(String c_address) {
        this.c_address = c_address;
    }

    public String getC_city() {
        return c_city;
    }

    public void setC_city(String c_city) {
        this.c_city = c_city;
    }

    public String getC_country() {
        return c_country;
    }

    public void setC_country(String c_country) {
        this.c_country = c_country;
    }

    public String getC_phoneNbr() {
        return c_phoneNbr;
    }

    public void setC_phoneNbr(String c_phoneNbr) {
        this.c_phoneNbr = c_phoneNbr;
    }

    public String getC_password() {
        return c_password;
    }

    public void setC_password(String c_password) {
        this.c_password = c_password;
    }

    public String[] toStringArray() {
        return new String[] {
                Integer.toString(c_id), c_firstName, c_lastName, c_email, c_address, c_city, c_country, c_phoneNbr, c_password
        };
    }
}

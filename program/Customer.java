package program;

public class Customer {

    String fName;
    String lName;
    int userID;

    public Customer(String fName, String lName, int userID) {
        this.fName = fName;
        this.lName = lName;
        this.userID = userID;
    }
    
    public String getfName() {
        return fName;
    }
    public String getlName() {
        return lName;
    }
    public int getUserID() {
        return userID;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}

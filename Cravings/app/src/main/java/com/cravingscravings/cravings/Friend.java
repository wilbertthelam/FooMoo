package com.cravingscravings.cravings;

/**
 * Created by Wilbert Lam on 8/27/2015.
 * Getters and Setters for each individual Friend listed on the main news feed.
 */
public class Friend {

    private int userId;
    private String fname;
    private String lname;
    private String currentCraving;
    private String fav1;
    private String fav2;
    private String fav3;
    private String cravingTimestamp;

    public String getCravingTimestamp() {
        return cravingTimestamp;
    }

    public void setCravingTimestamp(String cravingTimestamp) {
        this.cravingTimestamp = cravingTimestamp;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getCurrentCraving() {
        return currentCraving;
    }

    public void setCurrentCraving(String currentCraving) {
        this.currentCraving = currentCraving;
    }

    public String getFav1() {
        return fav1;
    }

    public void setFav1(String fav1) {
        this.fav1 = fav1;
    }

    public String getFav2() {
        return fav2;
    }

    public void setFav2(String fav2) {
        this.fav2 = fav2;
    }

    public String getFav3() {
        return fav3;
    }

    public void setFav3(String fav3) {
        this.fav3 = fav3;
    }

}

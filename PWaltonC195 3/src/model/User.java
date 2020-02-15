package model;

public class User {
    public static int NOID = -1;



    private int userId;
    private String userName;
    private String password;
    private int  active;
    private int createDate;
    private String createdBy;
    private int lastUpdate;
    private String lastUpdateBy;
    private String user;


    public User ( int userId, String userName, String password, int active) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.active = active;


    }
    public User (String userName, String password, int active){
        this.userId = NOID;
        this.userName = userName;
        this.password = password;
        this.active = active;
    }


    public int getCreateDate() {
        return createDate;
    }

    public void setCreateDate(int createDate) {
        this.createDate = createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public int getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(int lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    @Override
    public String toString(){
        return( userName );
    }

}

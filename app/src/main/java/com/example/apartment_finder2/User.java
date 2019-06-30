package com.example.apartment_finder2;

class User {
    String Displayname;
    String Email;
    String connection;
    int avatarId;
    long createdAt;

    private String mRecipientId;
    public User(){}

    public User(String displayname, String email, long createdAt ) {
        this.Displayname = displayname;
        this.Email = email;
        this.createdAt = createdAt;
    }

    /*public  String createUniqueChatRef(long createdAtCurrentUser, String currentUserEmail){
        String uniqueChatRef = "";

        if (createdAtCurrentUser>getCreatedAt()){
            uniqueChatRef = cleanEmailAddress(currentUserEmail)+"-"+cleanEmailAddress(getUserEmail());
        }else{
            uniqueChatRef = cleanEmailAddress(getUserEmail())+"-"+cleanEmailAddress(currentUserEmail);
        }

        return  uniqueChatRef;
    }*/

    private String cleanEmailAddress(String Email) {
        return  Email.replace(".","-");
    }


    public String getDisplayname() {
        return Displayname;
    }

    public String getEmail() {
        return Email;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public String getUserEmail() {
        return Email;
    }

    public String getConnection() {
        return connection;
    }

    public int getAvatarId() {
        return avatarId;
    }

    public String getmRecipientId() {
        return mRecipientId;
    }

    public void setmRecipientId(String mRecipientId) {
        this.mRecipientId = mRecipientId;
    }
}


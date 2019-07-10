package com.example.apartment_finder2;

public class UploadValidationForm {
    private String mFname;
    private String mLname;
    private String mAge;
    private String mSex;
    private String mOcc;
    private String mNid;
    private String mPchoice;
    private String mES;
    private String mValidation;
    private String mKey;

    public UploadValidationForm() {
        //empty constructor needed
    }

    public UploadValidationForm(String Fname,String Lname,String Age,String Sex,String Occ,String Nid,String choice,String ES,String Validation,String key) {
        mFname=Fname;
        mLname=Lname;
        mAge=Age;
        mSex=Sex;
        mOcc=Occ;
        mNid=Nid;
        mPchoice=choice;
        mES=ES;
        mValidation=Validation;
        mKey=key;
    }

    public String getmFname() {
        return mFname;
    }

    public void setmFname(String mFname) {
        this.mFname = mFname;
    }

    public String getmLname() {
        return mLname;
    }

    public void setmLname(String mLname) {
        this.mLname = mLname;
    }

    public String getmAge() {
        return mAge;
    }

    public void setmAge(String mAge) {
        this.mAge = mAge;
    }

    public String getmSex() {
        return mSex;
    }

    public void setmSex(String mSex) {
        this.mSex = mSex;
    }

    public String getmOcc() {
        return mOcc;
    }

    public void setmOcc(String mOcc) {
        this.mOcc = mOcc;
    }

    public String getmValidation() {
        return mValidation;
    }

    public void setmValidation(String mValidation) {
        this.mValidation = mValidation;
    }

    public String getmNid() {
        return mNid;
    }

    public void setmNid(String mNid) {
        this.mNid = mNid;
    }

    public String getmPchoice() {
        return mPchoice;
    }

    public void setmPchoice(String mPchoice) {
        this.mPchoice = mPchoice;
    }

    public String getmES() {
        return mES;
    }

    public void setmES(String mES) {
        this.mES = mES;
    }

    public String getmKey() {
        return mKey;
    }

    public void setmKey(String mKey) {
        this.mKey = mKey;
    }
}

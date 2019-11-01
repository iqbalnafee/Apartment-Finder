package com.example.apartment_finder2;

public class UploadValidationForm {
    private String mFname;
    private String mLname;
    private String mAge;
    private String mSex;
    private String mOcc;
    private String mNid;
    private String mPhone;
    private String mES;
    private String mValidation;
    private String mKey;
    private String mSeeMblNum;
    private String mImageUrl1;
    private String mImageUrl2;

    public UploadValidationForm() {
        //empty constructor needed
    }

    public UploadValidationForm(String Fname,String Lname,String Age,String Sex,String Occ,String Nid,String choice,String SeeMblNum,String imageUrl1,String imageUrl2,String ES,String Validation,String key) {
        mFname=Fname;
        mLname=Lname;
        mAge=Age;
        mSex=Sex;
        mOcc=Occ;
        mNid=Nid;
        mPhone=choice;
        mSeeMblNum=SeeMblNum;
        mImageUrl1=imageUrl1;
        mImageUrl2=imageUrl2;
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

    public String getmPhone() {
        return mPhone;
    }

    public void setmPhone(String mPchoice) {
        this.mPhone = mPchoice;
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

    public String getmSeeMblNum() {
        return mSeeMblNum;
    }

    public void setmSeeMblNum(String mSeeMblNum) {
        this.mSeeMblNum = mSeeMblNum;
    }

    public String getmImageUrl1() {
        return mImageUrl1;
    }

    public void setmImageUrl1(String mImageUrl1) {
        this.mImageUrl1 = mImageUrl1;
    }

    public String getmImageUrl2() {
        return mImageUrl2;
    }

    public void setmImageUrl2(String mImageUrl2) {
        this.mImageUrl2 = mImageUrl2;
    }
}

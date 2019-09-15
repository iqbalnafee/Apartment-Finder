package com.example.apartment_finder2;

public class UploadFeedbackForm {
    private String LEmail;
    private int level;
    private String FeedbackCategory;
    private String FeedbackMessage;
    private String FeedbackTime;

    public UploadFeedbackForm()
    {

    }
    public UploadFeedbackForm(String LE,int Level,String FC,String FM,String Time)
    {
        LEmail=LE;
        level=Level;
        FeedbackCategory=FC;
        FeedbackMessage=FM;
        FeedbackTime=Time;
    }

    public String getFeedbackTime() {
        return FeedbackTime;
    }

    public void setFeedbackTime(String feedbackTime) {
        FeedbackTime = feedbackTime;
    }

    public String getLEmail() {
        return LEmail;
    }

    public void setLEmail(String LEmail) {
        this.LEmail = LEmail;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getFeedbackCategory() {
        return FeedbackCategory;
    }

    public void setFeedbackCategory(String feedbackCategory) {
        FeedbackCategory = feedbackCategory;
    }

    public String getFeedbackMessage() {
        return FeedbackMessage;
    }

    public void setFeedbackMessage(String feedbackMessage) {
        FeedbackMessage = feedbackMessage;
    }
}

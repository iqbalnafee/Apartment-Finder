package com.example.apartment_finder2.Model;

public class ChatMessage2 {

    private boolean isImage,isMine;
    private String content;
    public  ChatMessage2(){}

    public ChatMessage2(boolean isI,boolean isM,String con)
    {
        isImage=isI;
        isMine=isM;
        content=con;
    }

    public boolean isImage() {
        return isImage;
    }

    public void setImage(boolean image) {
        isImage = image;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

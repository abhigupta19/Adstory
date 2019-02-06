package com.sars.user.adstory;

public class blog {
    private String title;
    private String rate;
    private String image;

    public blog()
    {

    }

    public blog(String title, String rate, String image) {
        this.title = title;
        this.rate = rate;
        this.image = image;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


}

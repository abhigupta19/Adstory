package com.sars.user.adstory;

class Artist {
    String id;
    String name;
    String mobile;

    public Artist(String id, String name, String mobile) {
        this.id = id;
        this.name = name;
        this.mobile = mobile;
    }

    public Artist()
    {

    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMobile() {
        return mobile;
    }
}

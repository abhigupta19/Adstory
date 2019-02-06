package com.sars.user.adstory;

class Information {
    public Information() {
    }

    private String title;
    private String rate;
    private String image;
    private  String zomato;
    private String shar;
    private String slink;
    private String inst;
    public Information(String title, String rate, String image,String zomato,String shar,String slink,String inst) {
        this.title = title;
        this.rate = rate;
        this.image = image;
        this.zomato=zomato;
        this.shar=shar;
        this.slink=slink;
        this.inst=inst;
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
   public String getZomato()
   {
       return zomato;
   }
   public void setZomato(String zomato)
   {
       this.zomato=zomato;
   }
    public String getShar()
    {
        return shar;
    }
    public void setShar(String shar)
    {
        this.shar=shar;
    }
    public String getSlink()
    {
        return slink;
    }
    public void setSlink(String slink)
    {
        this.slink=slink;
    }
    public String getInst()
    {
        return inst;
    }
    public void setInst(String inst)
    {
        this.inst=inst;
    }

}

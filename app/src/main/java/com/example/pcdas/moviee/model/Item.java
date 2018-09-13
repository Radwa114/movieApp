package com.example.pcdas.moviee.model;


public class Item {


    private String name ;
    private String description ;
    private String image ;
    private String rating ;
    private String rdate ;
    private int id ;


    public Item() {


    }

    public Item(String name, String description, String image, String rating, String rdate , int id) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.rating = rating;
        this.rdate = rdate;
        this.id = id;
    }

    public String getrdate() {
        return rdate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setrdate(String category) {
        this.rdate = category;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }




}

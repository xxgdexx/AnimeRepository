package com.cibertec.proyectoanime.Models;

public class Anime {
    private int number;
    private String coverImage;
    private String attributes;

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getAttributes() {
        return attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }

    public int getNumber() {
        String[] urlPartes = coverImage.split("/") ;
       return Integer.parseInt(urlPartes[urlPartes.length - 2]);
     }

    public void setNumber(int number) {
        this.number = number;
    }
}

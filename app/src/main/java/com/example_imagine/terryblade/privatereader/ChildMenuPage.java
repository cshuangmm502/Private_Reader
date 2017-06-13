package com.example_imagine.terryblade.privatereader;

/**
 * Created by Terryblade on 2017/5/10.
 */
public class ChildMenuPage {
    private String name;
    private int imageId;
    public ChildMenuPage(String name,int imageId){
        this.name=name;
        this.imageId=imageId;
    }

    public String getName(){
        return name;
    }

    public int getImageId(){
        return imageId;
    }
}

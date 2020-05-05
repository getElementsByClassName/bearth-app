package com.example.medtonulfem;

public class User
{
   private String name;
   private String photourl;
   private Long points;

    public User()
    {
        // Constructor required for Firebase Database
    }

    public User(String name, String photourl, Long points)
    {
        this.name = name;
        this.photourl = photourl;
        this.points = points;
    }

    public String getPhotourl()
    {
        return photourl;
    }

    public void setPhotourl(String photourl)
    {
        this.photourl = photourl;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Long getPoints()
    {
        return points;
    }

    public void setPoints(Long points)
    {
        this.points = points;
    }
}

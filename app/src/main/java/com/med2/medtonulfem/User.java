package com.med2.medtonulfem;

public class User
{
   private String name;
   private String photourl;
   private Long points;
   private String avatarStatus;

    public User()
    {
        // Constructor required for Firebase Database
    }

    public User(String avatarStatus, String name, String photourl, Long points)
    {
        this.avatarStatus = avatarStatus;
        this.name = name;
        this.photourl = photourl;
        this.points = points;
    }

    public String getPhotourl()
    {
        return photourl;
    }

    /*public void setPhotourl(String photourl)
    {
        this.photourl = photourl;
    }*/

    public String getName()
    {
        return name;
    }

   /* public void setName(String name)
    {
        this.name = name;
    }*/

    public Long getPoints()
    {
        return points;
    }

   /* public void setPoints(Long points)
    {
        this.points = points;
    }*/

   public String getAvatarStatus() {return avatarStatus;}

    /*public void setAvatarStatus(String avatarStatus)
    {
        this.avatarStatus = avatarStatus;
    }*/
}

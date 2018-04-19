package com.tsystem.data.pojo;

public class Photo_tags
{
    private String title;

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [title = "+title+"]";
    }
}
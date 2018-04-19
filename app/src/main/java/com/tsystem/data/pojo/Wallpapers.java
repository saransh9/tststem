package com.tsystem.data.pojo;

/**
 * Created by saransh on 23/03/18.
 */

public class Wallpapers
{
    private String id;

    private String height;

    private String file_size;

    private String width;

    private String url_page;

    private String url_image;

    private String url_thumb;

    private String file_type;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getHeight ()
    {
        return height;
    }

    public void setHeight (String height)
    {
        this.height = height;
    }

    public String getFile_size ()
    {
        return file_size;
    }

    public void setFile_size (String file_size)
    {
        this.file_size = file_size;
    }

    public String getWidth ()
    {
        return width;
    }

    public void setWidth (String width)
    {
        this.width = width;
    }

    public String getUrl_page ()
    {
        return url_page;
    }

    public void setUrl_page (String url_page)
    {
        this.url_page = url_page;
    }

    public String getUrl_image ()
    {
        return url_image;
    }

    public void setUrl_image (String url_image)
    {
        this.url_image = url_image;
    }

    public String getUrl_thumb ()
    {
        return url_thumb;
    }

    public void setUrl_thumb (String url_thumb)
    {
        this.url_thumb = url_thumb;
    }

    public String getFile_type ()
    {
        return file_type;
    }

    public void setFile_type (String file_type)
    {
        this.file_type = file_type;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", height = "+height+", file_size = "+file_size+", width = "+width+", url_page = "+url_page+", url_image = "+url_image+", url_thumb = "+url_thumb+", file_type = "+file_type+"]";
    }
}

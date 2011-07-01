package br.unifesp.psf.cassandra.dao;

public class Base
{
    private Long key;
    private String xml;
    private String url;

    public Long getKey()
    {
        return key;
    }

    public void setKey(Long key)
    {
        this.key = key;
    }

    public String getXml()
    {
        return xml;
    }

    public void setXml(String xml)
    {
        this.xml = xml;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }
}
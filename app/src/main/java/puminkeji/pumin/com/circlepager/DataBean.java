package puminkeji.pumin.com.circlepager;

public class DataBean {
    private String url;
    private String describe;

    public DataBean(String url, String describe) {
        this.url = url;
        this.describe = describe;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}

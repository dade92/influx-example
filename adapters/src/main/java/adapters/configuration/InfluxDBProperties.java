package adapters.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("influx")
public class InfluxDBProperties {
    public String url;
    public String token;
    public String org;
    public String bucket;

    public void setUrl(String url) {
        this.url = url;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }
}

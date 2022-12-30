package top.cuteworld.show.cutestore.model.domain.httptrace;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "http_logs")
public class SimpleHttpTrace {

    @Id
    private Long id;
    @Column
    private String method;
    @Column
    private String url;
    @Column
    private Date accessTime;
    @Column
    private long take;
    @Column
    private int httpStatus;

    @Lob
    private String originTrace;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getAccessTime() {
        return accessTime;
    }

    public void setAccessTime(Date accessTime) {
        this.accessTime = accessTime;
    }

    public long getTake() {
        return take;
    }

    public void setTake(long take) {
        this.take = take;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getOriginTrace() {
        return originTrace;
    }

    public void setOriginTrace(String originTrace) {
        this.originTrace = originTrace;
    }
}

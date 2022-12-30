package top.cuteworld.show.cutestore.model.domain.behavior;

import top.cuteworld.show.cutestore.utils.Utils;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "user_behavior")
public class UserProductBehavior {

    public UserProductBehavior() {
    }

    public UserProductBehavior(Long productId, String sessionId, Long clientTime) {
        this.productId = productId;
        this.sessionId = sessionId;
        this.viewTime = Utils.toDate(clientTime);
        this.createdAt = Utils.currentDate();
        this.userAction = UserAction.USER_ACTION_VIEW;
    }

    @Id
    private Long id;

    @Column(nullable = false)
    private Long productId;

    @Column(nullable = false)
    private String sessionId;

    @Column
    private Long userId;

    @Column
    private String client;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date viewTime;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column
    private int userAction; //用户行为

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public Date getViewTime() {
        return viewTime;
    }

    public void setViewTime(Date viewTime) {
        this.viewTime = viewTime;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public int getUserAction() {
        return userAction;
    }

    public void setUserAction(int userAction) {
        this.userAction = userAction;
    }
}

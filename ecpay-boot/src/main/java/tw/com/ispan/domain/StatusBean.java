package tw.com.ispan.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Statuses")
public class StatusBean {

    @Id
    @Column(name = "StatusID")
    private int statusID;

    @Column(name = "DetailedStatus", nullable = false)
    private String detailedStatus;

    // Getters and Setters
    public int getStatusID() {
        return statusID;
    }

    public void setStatusID(int statusID) {
        this.statusID = statusID;
    }

    public String getDetailedStatus() {
        return detailedStatus;
    }

    public void setDetailedStatus(String detailedStatus) {
        this.detailedStatus = detailedStatus;
    }
}
package bookstore.domain;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Ranks")
public class RankBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RankID")
    private Long rankID;

    @Column(name = "RankName")
    private String rankName;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rank")
    private List<AdminBean> admins;

    public String toString() {
        return "RankBean [" + rankID + "," + rankName + "]";
    }

    // Getters and Setters
    public Long getRankID() {
        return rankID;
    }

    public void setRankID(Long rankID) {
        this.rankID = rankID;
    }

    public String getRankName() {
        return rankName;
    }

    public void setRankName(String rankName) {
        this.rankName = rankName;
    }

    public List<AdminBean> getAdmins() {
        return admins;
    }

    public void setAdmins(List<AdminBean> admins) {
        this.admins = admins;
    }
}

package bookstore.dto;

public class RankDTO {
    private Long rankID; // 顯示 RankID
    private String rankName; // Rank 名稱

    // Getters and Setters
    public Long getRankID() {
        return rankID;
    }

    public void setRankId(Long rankID) {
        this.rankID = rankID;
    }

    public String getRankName() {
        return rankName;
    }

    public void setRankName(String rankName) {
        this.rankName = rankName;
    }

    @Override
    public String toString() {
        return "RankDTO [rankID=" + rankID + ", rankName=" + rankName + "]";
    }
}

package bookstore.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

@Entity
@Table(name = "Authors")
public class AuthorBean {

    @Id
    @Column(name = "AuthorID") // 對應資料庫的 AuthorID 欄位
    private Integer authorId;
    
    @Column(name = "AuthorName") // 對應資料庫的 AuthorName 欄位
    private String authorName;
    
    @Column(name = "AuthorDescription") // 對應資料庫的 AuthorDescription 欄位
    private String authorDescription;

    // Getter 和 Setter
    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorDescription() {
        return authorDescription;
    }

    public void setAuthorDescription(String authorDescription) {
        this.authorDescription = authorDescription;
    }
}


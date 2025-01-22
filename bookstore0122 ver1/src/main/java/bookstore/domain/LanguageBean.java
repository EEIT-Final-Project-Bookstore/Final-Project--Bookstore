package bookstore.domain;


import jakarta.persistence.*;
import java.util.List;
@Entity
@Table(name = "Languages")
public class LanguageBean {

    @Id
    @Column(name = "LanguageID")
    private Integer languageId;

    @Column(name = "Language", nullable = false, length = 100)
    private String language;

    @OneToMany(mappedBy = "language")
    private List<BookBean> books;

    // Getters 和 Setters
    public Integer getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Integer languageId) {
        this.languageId = languageId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<BookBean> getBooks() {
        return books;
    }

    public void setBooks(List<BookBean> books) {
        this.books = books;
    }
}

//@Entity
//@Table(name = "Languages")
//public class LanguageBean {
//
//    @Id
//    @Column(name = "LanguageID")
//    private Integer languageId;
//
//    @Column(name = "Language", nullable = false, length = 100)
//    private String language;
//
//    @OneToMany(mappedBy = "language")
//    private List<BookBean> books;
//
//    // Getters 和 Setters
//    public Integer getLanguageId() {
//        return languageId;
//    }
//
//    public void setLanguageId(Integer languageId) {
//        this.languageId = languageId;
//    }
//
//    public String getLanguage() {
//        return language;
//    }
//
//    public void setLanguage(String language) {
//        this.language = language;
//    }
//
//    public List<BookBean> getBooks() {
//        return books;
//    }
//
//    public void setBooks(List<BookBean> books) {
//        this.books = books;
//    }
//}

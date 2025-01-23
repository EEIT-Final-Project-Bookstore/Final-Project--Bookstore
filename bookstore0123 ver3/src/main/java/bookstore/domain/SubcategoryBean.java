package bookstore.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
@Entity
@Table(name = "Subcategories")
public class SubcategoryBean {

    @Id
    @Column(name = "SubcategoryID")
    private Integer subcategoryId; // 不使用自動生成策略

    @Column(name = "SubcategoryName", nullable = false, length = 100)
    private String subcategoryName;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "CategoryID", nullable = false)
    private CategoryBean category;

    @OneToMany(mappedBy = "subcategory")
    private List<BookBean> books;

    // Getters 和 Setters
    public Integer getSubcategoryId() {
        return subcategoryId;
    }

    public void setSubcategoryId(Integer subcategoryId) {
        this.subcategoryId = subcategoryId;
    }

    public String getSubcategoryName() {
        return subcategoryName;
    }

    public void setSubcategoryName(String subcategoryName) {
        this.subcategoryName = subcategoryName;
    }

    public CategoryBean getCategory() {
        return category;
    }

    public void setCategory(CategoryBean category) {
        this.category = category;
    }

    public List<BookBean> getBooks() {
        return books;
    }

    public void setBooks(List<BookBean> books) {
        this.books = books;
    }
}

//@Entity
//@Table(name = "Subcategories")
//public class SubcategoryBean {
//	 @Id
//	    @GeneratedValue(strategy = GenerationType.IDENTITY)
//	    private Integer subcategoryID;
//
//	    @Column(nullable = false, length = 100)
//	    private String subcategoryName;
//
//	    @ManyToOne
//	    @JoinColumn(name = "CategoryID", nullable = false)
//	    private CategoryBean category;
//
//	    // Getters and Setters
//
//	    public Integer getSubcategoryID() {
//	        return subcategoryID;
//	    }
//
//	    public void setSubcategoryID(Integer subcategoryID) {
//	        this.subcategoryID = subcategoryID;
//	    }
//
//	    public String getSubcategoryName() {
//	        return subcategoryName;
//	    }
//
//	    public void setSubcategoryName(String subcategoryName) {
//	        this.subcategoryName = subcategoryName;
//	    }
//
//	    public CategoryBean getCategory() {
//	        return category;
//	    }
//
//	    public void setCategory(CategoryBean category) {
//	        this.category = category;
//	    }
//	}

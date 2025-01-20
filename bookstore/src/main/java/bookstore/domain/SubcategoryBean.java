package bookstore.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Subcategories")
public class SubcategoryBean {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Integer subcategoryID;

	    @Column(nullable = false, length = 100)
	    private String subcategoryName;

	    @ManyToOne
	    @JoinColumn(name = "CategoryID", nullable = false)
	    private CategoriesBean category;

	    // Getters and Setters

	    public Integer getSubcategoryID() {
	        return subcategoryID;
	    }

	    public void setSubcategoryID(Integer subcategoryID) {
	        this.subcategoryID = subcategoryID;
	    }

	    public String getSubcategoryName() {
	        return subcategoryName;
	    }

	    public void setSubcategoryName(String subcategoryName) {
	        this.subcategoryName = subcategoryName;
	    }

	    public CategoriesBean getCategory() {
	        return category;
	    }

	    public void setCategory(CategoriesBean category) {
	        this.category = category;
	    }
	}

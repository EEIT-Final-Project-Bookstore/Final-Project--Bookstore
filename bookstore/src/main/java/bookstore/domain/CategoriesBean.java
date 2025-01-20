package bookstore.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Categories")
public class CategoriesBean {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Integer categoryID;

	    @Column(nullable = false, length = 100)
	    private String categoryName;

	    // Getters and Setters

	    public Integer getCategoryID() {
	        return categoryID;
	    }

	    public void setCategoryID(Integer categoryID) {
	        this.categoryID = categoryID;
	    }

	    public String getCategoryName() {
	        return categoryName;
	    }

	    public void setCategoryName(String categoryName) {
	        this.categoryName = categoryName;
	    }
	}
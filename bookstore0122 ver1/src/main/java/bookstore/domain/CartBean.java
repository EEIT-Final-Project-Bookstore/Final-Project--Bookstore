package bookstore.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Carts")
public class CartBean {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CartID")
    private Integer cartId;

    @Column(name = "CustomerID", nullable = false, unique = true)
    private Integer customerId;

    @Column(name = "CreationTime", nullable = false)
    private LocalDateTime creationTime;

    @Column(name = "LastUpdatedTime", nullable = false)
    private LocalDateTime lastUpdatedTime;

    @Column(name = "Quantity", nullable = false)
    private Integer quantity;

    @Column(name = "StatusID")
    private Integer statusId;
    
    @JsonManagedReference  // 這裡使用 JsonManagedReference，指示 CartBean 是序列化的根節點
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItemBean> items = new ArrayList<>();  // 購物車中的所有商品項目，初始化為空列表，避免為 null
    
    @ManyToMany
    @JoinTable(
      name = "Cart_Books", 
      joinColumns = @JoinColumn(name = "CartID"), 
      inverseJoinColumns = @JoinColumn(name = "BookID")
    )
    private List<BookBean> books; // 多對多關聯

    @ManyToOne
    @JoinColumn(name = "CustomerID", referencedColumnName = "CustomerID", insertable = false, updatable = false)
    private CustomerBean customer;

    public CartBean() {
	}

	public CartBean(Integer cartId, Integer customerId, LocalDateTime creationTime, LocalDateTime lastUpdatedTime,
			Integer quantity, Integer statusId, List<CartItemBean> items, List<BookBean> books, CustomerBean customer) {
		this.cartId = cartId;
		this.customerId = customerId;
		this.creationTime = creationTime;
		this.lastUpdatedTime = lastUpdatedTime;
		this.quantity = quantity;
		this.statusId = statusId;
		this.items = items;
		this.books = books;
		this.customer = customer;
	}

	// 當實體保存到資料庫之前自動設置 creationTime 和 lastUpdatedTime
//    @PrePersist
//    public void prePersist() {
//        if (this.creationTime == null) {
//            this.creationTime = LocalDateTime.now();
//        }
//        if (this.lastUpdatedTime == null) {
//            this.lastUpdatedTime = LocalDateTime.now();
//        }
//    }
    
    // Add item method
    public void addItem(CartItemBean item) {
        if (!this.items.contains(item)) {
            this.items.add(item);
            item.setCart(this);  // Set the bidirectional relationship
        }
    }

    // Remove item method
    public void removeItem(CartItemBean item) {
        this.items.remove(item);
        item.setCart(null);  // Remove the bidirectional relationship
    }
    
	public Integer getCartId() {
		return cartId;
	}

	public void setCartId(Integer cartId) {
		this.cartId = cartId;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public LocalDateTime getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(LocalDateTime creationTime) {
		this.creationTime = creationTime;
	}

	public LocalDateTime getLastUpdatedTime() {
		return lastUpdatedTime;
	}

	public void setLastUpdatedTime(LocalDateTime lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getStatusId() {
		return statusId;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}
    public CustomerBean getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerBean customer) {
        this.customer = customer;
    }

    public List<CartItemBean> getItems() {
        return items;
    }

    public void setItems(List<CartItemBean> items) {
        this.items = items;
    }
}

package bookstore.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "CartItems")
public class CartItemBean {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CartItemID")
    private Integer cartItemID;

    @ManyToOne
    @JoinColumn(name = "CartID", referencedColumnName = "CartID")
    @JsonBackReference // 這裡使用 JsonBackReference，指示 CartItemBean 只會反序列化一次，避免循環
    private CartBean cart;

    @ManyToOne
    @JoinColumn(name = "CustomerID", referencedColumnName = "CustomerID")
    @JsonBackReference
    private CustomerBean customer;

    @ManyToOne
    @JoinColumn(name = "BookID", referencedColumnName = "BookID")
    private BookBean book;

    @Column(name = "Quantity")
    private Integer quantity;

    @Column(name = "Price")
    private Integer price;

    // 計算小計
    @Column(name = "Subtotal")
    private Integer subtotal;

    // Getters and Setters
    public Integer getSubtotal() {
        return price * quantity; // 小計 = 單價 * 數量
    }

    public void setSubtotal(Integer subtotal) {
        this.subtotal = subtotal;
    }

    public CustomerBean getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerBean customer) {
        this.customer = customer;
    }

    public Integer getCartItemID() {
        return cartItemID;
    }

    public void setCartItemID(Integer cartItemID) {
        this.cartItemID = cartItemID;
    }

    public CartBean getCart() {
        return cart;
    }

    public void setCart(CartBean cart) {
        this.cart = cart;
    }

    public BookBean getBook() {
        return book;
    }

    public void setBook(BookBean book) {
        this.book = book;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
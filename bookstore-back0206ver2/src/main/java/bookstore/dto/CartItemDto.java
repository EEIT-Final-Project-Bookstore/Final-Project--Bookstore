package bookstore.dto;

public class CartItemDto {
    private String bookName; // 書籍名稱
    private String imageUrl; // 書籍照片 (圖片 URL)
    private Integer quantity; // 數量
    private Integer price; // 單價
    private Integer subtotal; // 小計（單價 * 數量）

    public CartItemDto() {
    }

    public CartItemDto(String imageUrl, String bookName, Integer quantity, Integer price, Integer subtotal) {
        this.bookName = bookName;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
        this.price = price;
        this.subtotal = subtotal;
    }

    public CartItemDto(String bookName, String imageUrl, Integer quantity, Integer price) {
        this.bookName = bookName;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
        this.price = price;
        this.subtotal = price * quantity;
    }

    // Getters and Setters
    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    public void setSubtotal(Integer subtotal) {
        this.subtotal = subtotal;
    }

    public Integer getSubtotal() {
        return subtotal;
    }
}

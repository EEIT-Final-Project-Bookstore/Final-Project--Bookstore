# Final-Project--Bookstore
注意事項:

需新增表格CartItems

CREATE TABLE CartItems (

CartItemID INT IDENTITY(1,1) PRIMARY KEY, -- 流水號

CartID INT NOT NULL, -- 關聯到Carts表格的ID

BookID INT NOT NULL, -- 關聯到Books表格的ID

CustomerID INT NOT NULL, -- 關聯到Customers表格的ID

Quantity INT NOT NULL,

Price INT NOT NULL,

Subtotal INT NOT NULL,

CONSTRAINT FK_CartItems_Cart FOREIGN KEY (CartID) REFERENCES Carts(CartID) ON DELETE CASCADE, -- 外鍵關聯Carts表格

CONSTRAINT FK_CartItems_Book FOREIGN KEY (BookID) REFERENCES Books(BookID) ON DELETE CASCADE, -- 外鍵關聯Books表格

CONSTRAINT FK_CartItems_Customers FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerID) -- 外鍵關聯Customers表格
);

最新完整版 sql 指令在 SQLTable-new0131.sql 檔案

測試請務必先 Insert 狀態 和 付款方式 的測試資料

INSERT INTO Statuses(StatusID, DetailedStatus)

VALUES

(1, '保留'), --購物車 (2, '已清空'), --購物車 (3, '未使用'), --優惠券 (4, '已使用'), --優惠券 (5, '已過期'), --優惠券 (6, '待付款'), --訂單 (7, '已付款'), --訂單 (8, '已出貨'), --訂單 (9, '已完成'); --訂單

INSERT INTO PaymentMethods(PaymentMethodID, PaymentMethodName)

VALUES

(1, '信用卡');
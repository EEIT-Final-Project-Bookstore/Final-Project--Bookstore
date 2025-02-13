-- Insert表格資料順序:
-- 1 分類Insert
USE storedb
INSERT INTO Categories (
    CategoryID,
    CategoryName
)
VALUES 
(1,'ParentChild');

--------------------------------------------------------------------------------------------
-- 2 子分類Insert
USE storedb
INSERT INTO Subcategories (
    SubcategoryID,  -- 子類別ID，手動指定
    CategoryID,     -- 主類別ID，必須在 Categories 表中存在
	SubCategoryName
)
VALUES 
(1,1,'ChildLiterature'),
(2,1,'ChildLit'),
(3,1,'ChilLit'),
(4,1,'Literature'),
(6,1,'Literature1'),
(8,1,'ChildLiterature2'),
(9,1,'ChildL3'),
(10,1,'ChildLit2'),
(11,1,'ChildLiterature3'),
(12,1,'ChildLiterature4'),
(15,1,'ChildLiterature5');

--------------------------------------------------------------------------------------------
-- 3 出版社Insert
USE storedb
INSERT INTO Publishers (
    PublisherID,    -- 出版社ID，手動指定
    PublisherName
) 
VALUES
(1,'小山丘'),
(2,'小山'),
(3,'山丘'),
(4,'小丘'),
(5,'小山丘2'),
(6,'小山丘3'),
(7,'小山2'),
(8,'小山丘3'),
(9,'山丘2'),
(10,'山丘3');

--------------------------------------------------------------------------------------------
-- 4 作者Insert
USE storedb
INSERT INTO Authors(AuthorID, AuthorName, AuthorDescription)
VALUES
(1, '王曉明', ''),
(2, 'Alex A', '');

--------------------------------------------------------------------------------------------
-- 5 語言Insert
USE storedb
INSERT INTO Languages(LanguageID, Language)
VALUES
(1, '中文'),
(2, '英文');

--------------------------------------------------------------------------------------------
-- 6 書籍Insert
USE storedb
INSERT INTO Books (BookName, PublisherID, AuthorID, LanguageID, Price, Stock, Description, ShelfTime, LastUpdated, PublishDate, SubcategoryID, ImageURL, ImageDescription)
VALUES
('小鳥憂憂好想飛', '1', 1, 1, 350, 70, '', '', '', '2024-02-05', '1', 'https://cdnec.sanmin.com.tw/product_images/626/626370916.jpg', ''),
('黑暗集會03', '2', 1, 1, 99, 150, '', '', '', '2022-05-12', '3', 'https://cdnec.sanmin.com.tw/product_images/626/626322134.jpg', ''),
('負責任的小猴子', '3', 1, 1, 160, 100, '', '', '', '2024-06-30', '2', 'https://cdnec.sanmin.com.tw/product_images/626/626750224.jpg', ''),
('NBA經典進攻戰術解析', '4', 1, 1, 280, 150, '', '', '', '2024-07-01', '4', 'https://cdnec.sanmin.com.tw/product_images/986/986346338.jpg', ''),
('中學＆初級英檢，1500單字 × 文法，一書制霸！（25K+QR碼線上音檔）', '5', 1, 1, 369, 200, '', '', '', '2024-02-06', '6', 'https://cdnec.sanmin.com.tw/product_images/986/986246884.jpg', ''),
('生活英語，每天看點，每天聽點 閒聊就上手！', '5', 1, 1, 325, 50, '', '', '', '2021-10-11', '6', 'https://cdnec.sanmin.com.tw/product_images/986/986246649.jpg', ''),
('風鈴中的刀聲（上）【珍藏限量紀念版】', '6', 1, 1, 340, 150, '', '', '', '2024-02-10', '8', 'https://cdnec.sanmin.com.tw/product_images/626/626751031.jpg', ''),
('Sips & Snacks: Food and Drinks for Fun and Festive Occasions', '7', 2, 2, 600, 50, '', '', '', '2024-04-03', '9', 'https://cdnec.sanmin.com.tw/product_images/163/163938833.jpg', ''),
('Brain Games - To Go - Stress Free: Sudoku Puzzles (Purple): Volume 2', '7', 2, 2, 380, 200, '', '', '', '2024-04-03', '11', 'https://cdnec.sanmin.com.tw/product_images/163/163938807.jpg', ''),
('Reimagining Environmental Identity: Selected Works by Atelier Ping Jiang Eid Arch 2015-2023', '8', 2, 2, 2000, 100, '', '', '', '2024-04-01', '12', 'https://cdnec.sanmin.com.tw/product_images/196/196185638.jpg', ''),
('Intuitive Eating for Diabetes: The No Shame, No Blame, Non-Diet Approach to Managing Your Blood Sugar', '9', 2, 2, 760, 100, '', '', '', '2024-04-01', '15', 'https://cdnec.sanmin.com.tw/product_images/164/164848409.jpg', ''),
('Campfire Stories: Chesapeake Bay: Tales & Travel Companion', '10', 2, 2, 720, 50, '', '', '', '2024-04-01', '10', 'https://cdnec.sanmin.com.tw/product_images/168/168051752.jpg', '');

--------------------------------------------------------------------------------------------
-- 顧客Insert
USE storedb
INSERT INTO Customers (Username, CustomerName, Password, Email, PhoneNumber, MobileNumber)
VALUES
('Aaa', 'Alex', CONVERT(varbinary, 'A'), 'Alex@gmail.com', 987654321, 223456789),
('Bbb', 'Babe', CONVERT(varbinary, 'B'), 'Babe@gmail.com', 912345678, 221314151),
('Ccc', 'Carol', CONVERT(varbinary, 'C'), 'Carol@gmail.com', 924682468, 233147256),
('Ddd', 'David', CONVERT(varbinary, 'D'), 'David@gmail.com', 912341234, 43475342),
('Eee', 'Ethan', CONVERT(varbinary, 'E'), 'Ethan@gmail.com', 913571357, 43758142);

--------------------------------------------------------------------------------------------
-- 狀態Insert
USE storedb
INSERT INTO Statuses(StatusID, DetailedStatus)
VALUES 
(1, '保留'),	--購物車
(2, '已清空'),	--購物車
(3, '未使用'),	--優惠券
(4, '已使用'),	--優惠券
(5, '已過期'),	--優惠券
(6, '待付款'),	--訂單
(7, '已付款'),	--訂單
(8, '已取消'),	--訂單
(11, '已讀'),   -- 通知 -> StatusID不能變(程式裡有指定)
(12, '未讀');   -- 通知 -> StatusID不能變(程式裡有指定)

--------------------------------------------------------------------------------------------
-- 付款方式Insert
USE storedb
INSERT INTO PaymentMethods(PaymentMethodID, PaymentMethodName)
VALUES 
(1, '信用卡');

--------------------------------------------------------------------------------------------
-- 首頁圖片
USE storedb
INSERT INTO HomepageImages(ImageID, BookID)
VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5),
(6, 6),
(7, 7),
(8, 8),
(9, 9),
(10, 10),
(11, 11),
(12, 12);

--------------------------------------------------------------------------------------------
-- 通知類別Insert
INSERT INTO NotificationTypes(NotificationTypeID, NotificationType)
VALUES
(1, '訂單通知'),
(2, '促銷通知'),
(3, '系統公告'),
(4, '活動提醒');

--==========================================================================================
-- 優惠券Insert (會由後台管理頁面直接發送優惠券給所有顧客)
/*USE storedb
INSERT INTO Coupons (CouponCode, Discount, StartDate, EndDate, MinimumAmount)
VALUES
('XMAS2024', 66, '2024-12-15', '2024-12-31', 300),         --已過期
('WINTERSALE', 50, '2024-12-31', '2025-03-01', 250),
('NEWYEAR2025', 88, '2024-12-31', '2025-02-10', 330),      --已過期
('SUMMER2025', 15.0, '2025-06-01', '2025-08-31', 150),
('WINTERSALE', 25.0, '2025-12-01', '2025-12-31', 200),     --已過期
('WELCOME10', 10.0, '2025-01-01', '2025-02-28', 150),
('BLACKFRIDAY50', 50.0, '2025-11-25', '2025-11-29', 300),  --已過期
('NEWYEAR2025', 5.0, '2025-01-01', '2025-01-31', 120),     --已過期
('DISCOUNT30', 30.0, '2025-07-01', '2025-07-31', 150),
('FLASHSALE', 10.0, '2025-01-14', '2025-01-14', 150),      --已過期
('HOLIDAY20', 20.0, '2025-12-15', '2025-12-25', 120),      --已過期
('VIPCODE', 40.0, '2025-05-01', '2025-05-31', 500),
('EASTER15', 15.0, '2025-04-01', '2025-04-10', 180);
*/
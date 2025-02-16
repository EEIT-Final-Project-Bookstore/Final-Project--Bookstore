-- Insert表格資料順序:
-- 1 分類Insert
USE storedb
INSERT INTO Categories (CategoryID, CategoryName)
VALUES 
(1,'親子共讀'),
(2,'休閒生活'),
(3,'語言學習'),
(4,'文學作品');

--------------------------------------------------------------------------------------------
-- 2 子分類Insert
USE storedb
INSERT INTO Subcategories (SubcategoryID, CategoryID, SubCategoryName)
VALUES 
(1,1,'兒童文學'),
(2,1,'童話'),
(3,2,'漫畫'),
(4,2,'運動'),
(5,3,'英文'),
(6,3,'日文'),
(7,4,'文學'),
(8,4,'小說');

--------------------------------------------------------------------------------------------
-- 3 出版社Insert
USE storedb
INSERT INTO Publishers (PublisherID, PublisherName) 
VALUES
(1,'小山丘'),
(2,'大山丘'),
(3,'青文'),
(4,'東立'),
(5,'尖端'),
(6,'角川');

--------------------------------------------------------------------------------------------
-- 4 作者Insert
USE storedb
INSERT INTO Authors(AuthorID, AuthorName, AuthorDescription)
VALUES
(1, '王曉明', '很有名的作者'),
(2, 'Alex A', 'A very famous author'),
(3, '張小銘', '有名的作者'),
(4, 'Bob B','A famous author');

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
('小鳥憂憂好想飛', 1, 1, 1, 350, 100, '這是一本關於小鳥憂憂的書', '2024-01-01', '2025-02-15', '2024-02-05', 1, 'https://cdnec.sanmin.com.tw/product_images/626/626370916.jpg', '這本書的封面照片'),
('黑暗集會03', 2, 1, 1, 99, 150, '這是一本關於黑暗集會的書', '2024-01-01', '2025-02-15', '2022-05-12', 2, 'https://cdnec.sanmin.com.tw/product_images/626/626322134.jpg', '這本書的封面照片'),
('負責任的小猴子', 3, 3, 1, 160, 100, '這是一本關於小猴子的書', '2024-01-01', '2025-02-15', '2024-06-30', 3, 'https://cdnec.sanmin.com.tw/product_images/626/626750224.jpg', '這本書的封面照片'),
('NBA經典進攻戰術解析', 4, 1, 1, 280, 150, '這是一本關於NBA戰術解析的書', '2024-01-01', '2025-02-15', '2024-07-01', 4, 'https://cdnec.sanmin.com.tw/product_images/986/986346338.jpg', '這本書的封面照片'),
('中學＆初級英檢，1500單字 × 文法，一書制霸！（25K+QR碼線上音檔）', 5, 1, 1, 369, 200, '這是一本關於中學＆初級英檢的書', '2024-01-01', '2025-02-15', '2024-02-06', 5, 'https://cdnec.sanmin.com.tw/product_images/986/986246884.jpg', '這本書的封面照片'),
('生活英語，每天看點，每天聽點 閒聊就上手！', 5, 1, 1, 325, 50, '這是一本關於生活英語的書', '2024-01-01', '2025-02-15', '2021-10-11', 6, 'https://cdnec.sanmin.com.tw/product_images/986/986246649.jpg', '這本書的封面照片'),
('風鈴中的刀聲（上）【珍藏限量紀念版】', 6, 3, 1, 340, 150, '這是一本關於風鈴中的刀聲的書', '2024-01-01', '2025-02-15', '2024-02-10', 7, 'https://cdnec.sanmin.com.tw/product_images/626/626751031.jpg', '這本書的封面照片'),
('Sips & Snacks: Food and Drinks for Fun and Festive Occasions', 2, 2, 2, 600, 50, '這是一本關於Food and Drinks的書', '2024-01-01', '2025-02-15', '2024-04-03', 8, 'https://cdnec.sanmin.com.tw/product_images/163/163938833.jpg', '這本書的封面照片'),
('Brain Games - To Go - Stress Free: Sudoku Puzzles (Purple): Volume 2', 6, 4, 2, 380, 200, '這是一本關於Brain Games的書', '2024-01-01', '2025-02-15', '2024-04-03', 4, 'https://cdnec.sanmin.com.tw/product_images/163/163938807.jpg', '這本書的封面照片'),
('Reimagining Environmental Identity: Selected Works by Atelier Ping Jiang Eid Arch 2015-2023', 4, 2, 2, 1000, 100, '這是一本關於Selected Works的書', '2024-01-01', '2025-02-15', '2024-04-01', 3, 'https://cdnec.sanmin.com.tw/product_images/196/196185638.jpg', '這本書的封面照片'),
('Intuitive Eating for Diabetes: The No Shame, No Blame, Non-Diet Approach to Managing Your Blood Sugar', 3, 2, 2, 760, 100, '這是一本關於Managing Your Blood Sugar的書', '2024-01-01', '2025-02-15', '2024-04-01', 7, 'https://cdnec.sanmin.com.tw/product_images/164/164848409.jpg', '這本書的封面照片'),
('Campfire Stories: Chesapeake Bay: Tales & Travel Companion', 5, 4, 2, 720, 50, '這是一本關於Campfire Stories的書', '2024-01-01', '2025-02-15', '2024-04-01', 6, 'https://cdnec.sanmin.com.tw/product_images/168/168051752.jpg', '這本書的封面照片');

--------------------------------------------------------------------------------------------
-- 顧客Insert
USE storedb
INSERT INTO Customers (Username, CustomerName, Password, Email, PhoneNumber, MobileNumber)
VALUES
('Aaa', 'Alex A', 'A', 'Alex@gmail.com', 223456789, 987654321),
('Bbb', 'Barbie B','B', 'Babe@gmail.com', 221314151, 912345678),
('Ccc', 'Carol C', 'C', 'Carol@gmail.com', 233147256, 924682468),
('Ddd', 'David D', 'D', 'David@gmail.com', 43475342, 912341234),
('Eee', 'Ethan E', 'E', 'Ethan@gmail.com', 43758142, 913571357);

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
(11, '已讀'),   -- 通知 的 StatusID 不能變(程式裡有指定查詢 StatusID = 11)
(12, '未讀');   -- 通知 的 StatusID 不能變(程式裡有指定 StatusID = 12)

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

/*
INSERT INTO dbo.HomepageImages(ImageID, BookID)
SELECT 
    ROW_NUMBER() OVER (ORDER BY BookID) + 100 AS ImageID, -- 確保唯一
    BookID
FROM dbo.Books;
*/

--------------------------------------------------------------------------------------------
-- 通知類別Insert
USE storedb
INSERT INTO NotificationTypes(NotificationTypeID, NotificationType)
VALUES
(1, '訂單通知'),
(2, '促銷通知'),
(3, '系統公告'),
(4, '活動提醒');

--------------------------------------------------------------------------------------------
-- 管理員Insert
USE storedb
Insert into Ranks
values
('頭頭'),
('大頭頭'),
('超大頭頭');

INSERT INTO Admins(AdminsAccount, Password, Email, RankID)
VALUES
('Admin A', 'admin1', 'admin1@gmail.com', 1),
('Admin B', 'admin2', 'admin2@gmail.com', 2);

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

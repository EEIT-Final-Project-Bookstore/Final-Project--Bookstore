INSERT INTO Categories (
    CategoryID,   
    CategoryName     
)
VALUES 
(1,'親子共讀'),
(2,'休閒生活'),
(3,'語言學習'),
(4,'文學作品');

INSERT INTO Subcategories (
    SubcategoryID,  -- 子類別ID，手動指定
    CategoryID,     -- 主類別ID，必須在 Categories 表中存在
	SubCategoryName            -- 子類別名稱
)
VALUES 
(1,1,'兒童文學'),(2,1,'童話'),
(3,2,'漫畫'),(4,2,'運動'),
(5,3,'英文'),(6,3,'日文'),
(7,4,'文學'),(8,4,'小說');



INSERT INTO Publishers (
    PublisherID,  -- 出版社ID，手動指定
    PublisherName       -- 出版社名稱
) 
VALUES
(1,'小山丘'),(2,'大山丘'),(3,'青文'),(4,'東立'),(5,'尖端'),(6,'角川');


 

INSERT INTO Authors(
	AuthorID,
	AuthorName,
	AuthorDescription
)
VALUES
(1,'Shai','chill'),(2,'Chet','cool'),(3,'JDub','Strong'),(4,'Hartenstein','Beast'),(5,'Dort','DPOY'),(6,'Caruso','Good');

INSERT INTO Languages(
	LanguageID,
	Language
)
VALUES
(1,'中文'),
(2,'英文');

INSERT INTO Books (BookName, PublisherID, AuthorID, LanguageID, SubcategoryID, Price, 
Stock, Description, ShelfTime, LastUpdated, PublishDate, ImageURL, ImageDescription) 
VALUES 
('Java 入門實戰', 1, 1, 1, 3, 750, 50, '適合初學者的 Java 實戰書籍',
'2025-01-01', '2025-01-01', '2024-01-15', 
'https://cdnec.sanmin.com.tw/product_images/626/626370905.jpg', 'Java 教學封面'),
('book2', 1, 2, 2, 5, 750, 50, '有趣的書',
'2025-01-01', '2025-01-01', '2024-01-15', 
'https://cdnec.sanmin.com.tw/product_images/770/770229735.jpg', '這本書的封面'),
('book3', 1, 2, 2, 7, 750, 50, '有趣的書',
'2025-01-01', '2025-01-01', '2024-01-15', 
'https://cdnec.sanmin.com.tw/product_images/770/770229735.jpg', '這本書的封面'),
('book4', 1, 2, 2, 8, 750, 50, '有趣的書',
'2025-01-01', '2025-01-01', '2024-01-15', 
'https://cdnec.sanmin.com.tw/product_images/770/770229735.jpg', '這本書的封面'),
('book5', 1, 2, 2, 4, 750, 50, '有趣的書',
'2025-01-01', '2025-01-01', '2024-01-15', 
'https://cdnec.sanmin.com.tw/product_images/770/770229735.jpg', '這本書的封面'),
('book6', 1, 2, 2, 2, 750, 50, '有趣的書',
'2025-01-01', '2025-01-01', '2024-01-15', 
'https://cdnec.sanmin.com.tw/product_images/770/770229735.jpg', '這本書的封面'),
('book7', 1, 2, 2, 1, 750, 50, '有趣的書',
'2025-01-01', '2025-01-01', '2024-01-15', 
'https://cdnec.sanmin.com.tw/product_images/626/626023456.jpg', '這本書的封面'),
('book8', 1, 2, 2, 6, 750, 50, '有趣的書',
'2025-01-01', '2025-01-01', '2024-01-15', 
'https://cdnec.sanmin.com.tw/product_images/626/626023456.jpg', '這本書的封面')

INSERT INTO Subcategories (
    SubcategoryID,  -- 子類別ID，手動指定
    CategoryID,     -- 主類別ID，必須在 Categories 表中存在
	SubCategoryName            -- 子類別名稱
)
VALUES 
(1,1,'ChildLiterature')



INSERT INTO Publishers (
    PublisherID,  -- 出版社ID，手動指定
    PublisherName       -- 出版社名稱
) 
VALUES
(1,'小山丘')


INSERT INTO Categories (
    CategoryID,   
    CategoryName     
)
VALUES 
(1,'ParentChild');
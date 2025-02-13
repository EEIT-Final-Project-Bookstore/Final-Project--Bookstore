INSERT INTO Categories (
    CategoryID,   
    CategoryName     
)
VALUES 
(1,'�ˤl�@Ū'),
(2,'�𶢥ͬ�'),
(3,'�y���ǲ�'),
(4,'��ǧ@�~');

INSERT INTO Subcategories (
    SubcategoryID,  -- �l���OID�A��ʫ��w
    CategoryID,     -- �D���OID�A�����b Categories ���s�b
	SubCategoryName            -- �l���O�W��
)
VALUES 
(1,1,'�ൣ���'),(2,1,'����'),
(3,2,'���e'),(4,2,'�B��'),
(5,3,'�^��'),(6,3,'���'),
(7,4,'���'),(8,4,'�p��');



INSERT INTO Publishers (
    PublisherID,  -- �X����ID�A��ʫ��w
    PublisherName       -- �X�����W��
) 
VALUES
(1,'�p�s�C'),(2,'�j�s�C'),(3,'�C��'),(4,'�F��'),(5,'�y��'),(6,'���t');


 

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
(1,'����'),
(2,'�^��');

INSERT INTO Books (BookName, PublisherID, AuthorID, LanguageID, SubcategoryID, Price, 
Stock, Description, ShelfTime, LastUpdated, PublishDate, ImageURL, ImageDescription) 
VALUES 
('Java �J�����', 1, 1, 1, 3, 750, 50, '�A�X��Ǫ̪� Java ��Ԯ��y',
'2025-01-01', '2025-01-01', '2024-01-15', 
'https://cdnec.sanmin.com.tw/product_images/626/626370905.jpg', 'Java �оǫʭ�'),
('book2', 1, 2, 2, 5, 750, 50, '���쪺��',
'2025-01-01', '2025-01-01', '2024-01-15', 
'https://cdnec.sanmin.com.tw/product_images/770/770229735.jpg', '�o���Ѫ��ʭ�'),
('book3', 1, 2, 2, 7, 750, 50, '���쪺��',
'2025-01-01', '2025-01-01', '2024-01-15', 
'https://cdnec.sanmin.com.tw/product_images/770/770229735.jpg', '�o���Ѫ��ʭ�'),
('book4', 1, 2, 2, 8, 750, 50, '���쪺��',
'2025-01-01', '2025-01-01', '2024-01-15', 
'https://cdnec.sanmin.com.tw/product_images/770/770229735.jpg', '�o���Ѫ��ʭ�'),
('book5', 1, 2, 2, 4, 750, 50, '���쪺��',
'2025-01-01', '2025-01-01', '2024-01-15', 
'https://cdnec.sanmin.com.tw/product_images/770/770229735.jpg', '�o���Ѫ��ʭ�'),
('book6', 1, 2, 2, 2, 750, 50, '���쪺��',
'2025-01-01', '2025-01-01', '2024-01-15', 
'https://cdnec.sanmin.com.tw/product_images/770/770229735.jpg', '�o���Ѫ��ʭ�'),
('book7', 1, 2, 2, 1, 750, 50, '���쪺��',
'2025-01-01', '2025-01-01', '2024-01-15', 
'https://cdnec.sanmin.com.tw/product_images/626/626023456.jpg', '�o���Ѫ��ʭ�'),
('book8', 1, 2, 2, 6, 750, 50, '���쪺��',
'2025-01-01', '2025-01-01', '2024-01-15', 
'https://cdnec.sanmin.com.tw/product_images/626/626023456.jpg', '�o���Ѫ��ʭ�')

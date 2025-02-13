-- Insert����ƶ���:
-- 1 ����Insert
USE storedb
INSERT INTO Categories (
    CategoryID,
    CategoryName
)
VALUES 
(1,'ParentChild');

--------------------------------------------------------------------------------------------
-- 2 �l����Insert
USE storedb
INSERT INTO Subcategories (
    SubcategoryID,  -- �l���OID�A��ʫ��w
    CategoryID,     -- �D���OID�A�����b Categories ���s�b
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
-- 3 �X����Insert
USE storedb
INSERT INTO Publishers (
    PublisherID,    -- �X����ID�A��ʫ��w
    PublisherName
) 
VALUES
(1,'�p�s�C'),
(2,'�p�s'),
(3,'�s�C'),
(4,'�p�C'),
(5,'�p�s�C2'),
(6,'�p�s�C3'),
(7,'�p�s2'),
(8,'�p�s�C3'),
(9,'�s�C2'),
(10,'�s�C3');

--------------------------------------------------------------------------------------------
-- 4 �@��Insert
USE storedb
INSERT INTO Authors(AuthorID, AuthorName, AuthorDescription)
VALUES
(1, '�����', ''),
(2, 'Alex A', '');

--------------------------------------------------------------------------------------------
-- 5 �y��Insert
USE storedb
INSERT INTO Languages(LanguageID, Language)
VALUES
(1, '����'),
(2, '�^��');

--------------------------------------------------------------------------------------------
-- 6 ���yInsert
USE storedb
INSERT INTO Books (BookName, PublisherID, AuthorID, LanguageID, Price, Stock, Description, ShelfTime, LastUpdated, PublishDate, SubcategoryID, ImageURL, ImageDescription)
VALUES
('�p���~�~�n�Q��', '1', 1, 1, 350, 70, '', '', '', '2024-02-05', '1', 'https://cdnec.sanmin.com.tw/product_images/626/626370916.jpg', ''),
('�·t���|03', '2', 1, 1, 99, 150, '', '', '', '2022-05-12', '3', 'https://cdnec.sanmin.com.tw/product_images/626/626322134.jpg', ''),
('�t�d�����p�U�l', '3', 1, 1, 160, 100, '', '', '', '2024-06-30', '2', 'https://cdnec.sanmin.com.tw/product_images/626/626750224.jpg', ''),
('NBA�g��i��ԳN�ѪR', '4', 1, 1, 280, 150, '', '', '', '2024-07-01', '4', 'https://cdnec.sanmin.com.tw/product_images/986/986346338.jpg', ''),
('���ǡ���ŭ^�ˡA1500��r �� ��k�A�@�Ѩ��Q�I�]25K+QR�X�u�W���ɡ^', '5', 1, 1, 369, 200, '', '', '', '2024-02-06', '6', 'https://cdnec.sanmin.com.tw/product_images/986/986246884.jpg', ''),
('�ͬ��^�y�A�C�Ѭ��I�A�C��ť�I ����N�W��I', '5', 1, 1, 325, 50, '', '', '', '2021-10-11', '6', 'https://cdnec.sanmin.com.tw/product_images/986/986246649.jpg', ''),
('���a�����M�n�]�W�^�i���í��q�������j', '6', 1, 1, 340, 150, '', '', '', '2024-02-10', '8', 'https://cdnec.sanmin.com.tw/product_images/626/626751031.jpg', ''),
('Sips & Snacks: Food and Drinks for Fun and Festive Occasions', '7', 2, 2, 600, 50, '', '', '', '2024-04-03', '9', 'https://cdnec.sanmin.com.tw/product_images/163/163938833.jpg', ''),
('Brain Games - To Go - Stress Free: Sudoku Puzzles (Purple): Volume 2', '7', 2, 2, 380, 200, '', '', '', '2024-04-03', '11', 'https://cdnec.sanmin.com.tw/product_images/163/163938807.jpg', ''),
('Reimagining Environmental Identity: Selected Works by Atelier Ping Jiang Eid Arch 2015-2023', '8', 2, 2, 2000, 100, '', '', '', '2024-04-01', '12', 'https://cdnec.sanmin.com.tw/product_images/196/196185638.jpg', ''),
('Intuitive Eating for Diabetes: The No Shame, No Blame, Non-Diet Approach to Managing Your Blood Sugar', '9', 2, 2, 760, 100, '', '', '', '2024-04-01', '15', 'https://cdnec.sanmin.com.tw/product_images/164/164848409.jpg', ''),
('Campfire Stories: Chesapeake Bay: Tales & Travel Companion', '10', 2, 2, 720, 50, '', '', '', '2024-04-01', '10', 'https://cdnec.sanmin.com.tw/product_images/168/168051752.jpg', '');

--------------------------------------------------------------------------------------------
-- �U��Insert
USE storedb
INSERT INTO Customers (Username, CustomerName, Password, Email, PhoneNumber, MobileNumber)
VALUES
('Aaa', 'Alex', CONVERT(varbinary, 'A'), 'Alex@gmail.com', 987654321, 223456789),
('Bbb', 'Babe', CONVERT(varbinary, 'B'), 'Babe@gmail.com', 912345678, 221314151),
('Ccc', 'Carol', CONVERT(varbinary, 'C'), 'Carol@gmail.com', 924682468, 233147256),
('Ddd', 'David', CONVERT(varbinary, 'D'), 'David@gmail.com', 912341234, 43475342),
('Eee', 'Ethan', CONVERT(varbinary, 'E'), 'Ethan@gmail.com', 913571357, 43758142);

--------------------------------------------------------------------------------------------
-- ���AInsert
USE storedb
INSERT INTO Statuses(StatusID, DetailedStatus)
VALUES 
(1, '�O�d'),	--�ʪ���
(2, '�w�M��'),	--�ʪ���
(3, '���ϥ�'),	--�u�f��
(4, '�w�ϥ�'),	--�u�f��
(5, '�w�L��'),	--�u�f��
(6, '�ݥI��'),	--�q��
(7, '�w�I��'),	--�q��
(8, '�w����'),	--�q��
(11, '�wŪ'),   -- �q�� -> StatusID������(�{���̦����w)
(12, '��Ū');   -- �q�� -> StatusID������(�{���̦����w)

--------------------------------------------------------------------------------------------
-- �I�ڤ覡Insert
USE storedb
INSERT INTO PaymentMethods(PaymentMethodID, PaymentMethodName)
VALUES 
(1, '�H�Υd');

--------------------------------------------------------------------------------------------
-- �����Ϥ�
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
-- �q�����OInsert
INSERT INTO NotificationTypes(NotificationTypeID, NotificationType)
VALUES
(1, '�q��q��'),
(2, '�P�P�q��'),
(3, '�t�Τ��i'),
(4, '���ʴ���');

--==========================================================================================
-- �u�f��Insert (�|�ѫ�x�޲z���������o�e�u�f�鵹�Ҧ��U��)
/*USE storedb
INSERT INTO Coupons (CouponCode, Discount, StartDate, EndDate, MinimumAmount)
VALUES
('XMAS2024', 66, '2024-12-15', '2024-12-31', 300),         --�w�L��
('WINTERSALE', 50, '2024-12-31', '2025-03-01', 250),
('NEWYEAR2025', 88, '2024-12-31', '2025-02-10', 330),      --�w�L��
('SUMMER2025', 15.0, '2025-06-01', '2025-08-31', 150),
('WINTERSALE', 25.0, '2025-12-01', '2025-12-31', 200),     --�w�L��
('WELCOME10', 10.0, '2025-01-01', '2025-02-28', 150),
('BLACKFRIDAY50', 50.0, '2025-11-25', '2025-11-29', 300),  --�w�L��
('NEWYEAR2025', 5.0, '2025-01-01', '2025-01-31', 120),     --�w�L��
('DISCOUNT30', 30.0, '2025-07-01', '2025-07-31', 150),
('FLASHSALE', 10.0, '2025-01-14', '2025-01-14', 150),      --�w�L��
('HOLIDAY20', 20.0, '2025-12-15', '2025-12-25', 120),      --�w�L��
('VIPCODE', 40.0, '2025-05-01', '2025-05-31', 500),
('EASTER15', 15.0, '2025-04-01', '2025-04-10', 180);
*/
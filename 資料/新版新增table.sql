--1
CREATE TABLE Users (
    CustomerID INT PRIMARY KEY,  
    Username VARCHAR(50) UNIQUE NOT NULL,  
    CustomerName NVARCHAR(100),  
    Password VARCHAR(50) NOT NULL,  
    Email VARCHAR(100) UNIQUE NOT NULL,  
    PhoneNumber INT,  
    RegistrationTime DATETIME DEFAULT GETDATE(),  
    MobileNumber INT  
);

--2
CREATE TABLE Ranks (
    RankID INT PRIMARY KEY, -- �D��
    RankName NVARCHAR(50) NOT NULL -- ���ŦW��
);

--3
CREATE TABLE Admins (
    AdminID INT PRIMARY KEY, -- �D��
    Username VARCHAR(50) NOT NULL, -- �b��
    Password VARCHAR(50) NOT NULL, -- �K�X
    Email VARCHAR(100) NOT NULL, -- �q�l�l��
    RankID INT NOT NULL, -- ����ID�A�~��
    FOREIGN KEY (RankID) REFERENCES Ranks(RankID) -- �~�����p�춥�Ū�
);

--4
CREATE TABLE PasswordResetRequests (
    RequestID INT PRIMARY KEY, -- �D��
    CustomerID INT UNIQUE NOT NULL, -- �~��B�ߤ@�A�P�Ȥ��@��@���Y
    ResetCode VARCHAR(100) NOT NULL, -- �ѧO���]�ШD���N�X
    RequestTime DATETIME NOT NULL, -- �U�ȵo�_���]�ШD���ɶ�
    ExpirationTime DATETIME NOT NULL, -- �ШD�L���ɶ�
    Status NVARCHAR(50) NOT NULL, -- ���]�ШD�����A
    FOREIGN KEY (CustomerID) REFERENCES Users(CustomerID) -- �~�����p��Ȥ��
);

--5
CREATE TABLE Statuses (
    StatusID INT PRIMARY KEY, -- ���AID
    DetailedStatus NVARCHAR(50) NOT NULL -- �ԲӪ��A�A�Ҧp�w�}�ߡB�w�@�o�B�w�ϥ�
);

--6
CREATE TABLE PaymentMethods (
    PaymentMethodID INT PRIMARY KEY, -- ��I�覡ID
    PaymentMethodName VARCHAR(50) NOT NULL -- ��I�覡�W�� (�Ҧp�G�H�Υd�B�f��I��)
);

--7
CREATE TABLE Coupons (
    CouponID INT IDENTITY(1,1) PRIMARY KEY, -- �u�f��ID
    CouponCode VARCHAR(50) NOT NULL, -- �u�f�X
    Discount FLOAT NOT NULL, -- �馩 (% �� -$)
    StartDate DATETIME NOT NULL, -- �}�l���
    EndDate DATETIME NOT NULL, -- �������
    MinimumAmount INT NOT NULL -- �A�Ϊ��̧C���O���B
);

--8
CREATE TABLE CouponOwnership (
    SerialID INT IDENTITY(1,1) PRIMARY KEY, -- �y����
    CustomerID INT NOT NULL, -- �U��ID�A�~��
    CouponID INT NOT NULL, -- �u�f��ID�A�~��
    StatusID INT NOT NULL, -- ���AID�A�~��
    FOREIGN KEY (CustomerID) REFERENCES Users(CustomerID), -- �~�����p��Τ��
    FOREIGN KEY (CouponID) REFERENCES Coupons(CouponID), -- �~�����p���u�f���
    FOREIGN KEY (StatusID) REFERENCES Statuses(StatusID) -- �~�����p�쪬�A��
);

--9
CREATE TABLE Carts (
    CartID INT IDENTITY(1,1) PRIMARY KEY, -- �ʪ���ID
    CustomerID INT UNIQUE NOT NULL, -- �U��ID�A�~�� (�@��@���Y + �ߤ@)
    CreationTime DATETIME NOT NULL DEFAULT GETDATE(), -- �ʪ����Ыخɶ�
    LastUpdatedTime DATETIME NOT NULL DEFAULT GETDATE(), -- �ʪ����̪��s�ɶ�
    Quantity INT NOT NULL, -- �ʶR�ƶq
    StatusID INT, -- ���AID (�O�d�B����)
    FOREIGN KEY (CustomerID) REFERENCES Users(CustomerID) -- �~�����p��Τ��
);

--10
CREATE TABLE EBookshelves (
    EBookshelfID INT PRIMARY KEY, -- �q�l���dID
    CustomerID INT UNIQUE NOT NULL, -- �U��ID�A�~��A�ߤ@�A�@��@���Y
    PurchaseDate DATE NOT NULL, -- �ʶR���
    FOREIGN KEY (CustomerID) REFERENCES Users(CustomerID) -- �~�����p�Τ��
);

--11
CREATE TABLE Wishlists (
    WishlistID INT PRIMARY KEY, -- �@��M��ID
    CustomerID INT UNIQUE NOT NULL, -- �U��ID�A�~��A�ߤ@�A�@��@���Y
    FOREIGN KEY (CustomerID) REFERENCES Users(CustomerID) -- �~�����p��Τ��
);

--12
CREATE TABLE Categories (
    CategoryID INT PRIMARY KEY, -- ����ID
    CategoryName NVARCHAR(100) NOT NULL -- �����W��
);

--13
CREATE TABLE Subcategories (
    SubcategoryID INT PRIMARY KEY, -- �l����ID
    SubcategoryName NVARCHAR(100) NOT NULL, -- �l�����W��
    CategoryID INT NOT NULL, -- ����ID�A�~��
    FOREIGN KEY (CategoryID) REFERENCES Categories(CategoryID) -- �~�����p�������
);

--14
CREATE TABLE Languages (
    LanguageID INT PRIMARY KEY, -- �y��ID
    Language NVARCHAR(100) NOT NULL -- �y���W��
);

--15
CREATE TABLE Authors (
    AuthorID INT PRIMARY KEY, -- �@��ID
    AuthorName NVARCHAR(100) NOT NULL -- �@�̩m�W
);

--16
CREATE TABLE Publishers (
    PublisherID INT PRIMARY KEY, -- �X����ID
    PublisherName NVARCHAR(200) NOT NULL, -- �X�����W��
    PublisherDescription NVARCHAR(MAX) -- �X����²��
);

--17
CREATE TABLE PurchaseableVersions (
    VersionID INT PRIMARY KEY, -- �i�ʶR����ID
    VersionName NVARCHAR(100) NOT NULL -- �i�ʶR�����W��
);

--18
CREATE TABLE NotificationTypes (
    NotificationTypeID INT PRIMARY KEY, -- �q������ID
    NotificationType VARCHAR(100) NOT NULL -- �q�������W�� (�Ҧp�G�q��q���B�S���M��q��)
);

--19
CREATE TABLE Books (
    BookID INT IDENTITY(1,1) PRIMARY KEY, -- ���yID
    BookName NVARCHAR(200) NOT NULL, -- �ѦW
    PublisherID INT NOT NULL, -- �X����ID�A�~��
    Price INT NOT NULL, -- ����
    Stock INT NOT NULL, -- �w�s�q
    Description NVARCHAR(MAX), -- ���y�y�z
    ShelfTime DATETIME NOT NULL DEFAULT GETDATE(), -- ���y�W�[�ɶ�
    LastUpdated DATETIME NOT NULL DEFAULT GETDATE(), -- �̫��s�ɶ�
    PublishDate DATE NOT NULL, -- �X�����
    SubcategoryID INT NOT NULL, -- ���y�l���OID�A�~��
    ImageURL VARCHAR(200), -- �Ϥ�URL
    ImageDescription NVARCHAR(200), -- �Ϥ�����
    FOREIGN KEY (PublisherID) REFERENCES Publishers(PublisherID), -- �~�����p��X������
    FOREIGN KEY (SubcategoryID) REFERENCES Subcategories(SubcategoryID) -- �~�����p��l������
);

--20
CREATE TABLE Orders (
    OrderID INT IDENTITY(1,1) PRIMARY KEY, -- �q��ID
    CustomerID INT NOT NULL, -- �U��ID�A�~��
    TotalAmount INT NOT NULL, -- �q���`���B
    OrderStatus VARCHAR(50) NOT NULL, -- �q�檬�A (�ݳB�z�B�w�I�ڡB�w�X�f)
    OrderCreationTime DATETIME NOT NULL DEFAULT GETDATE(), -- �q��ͦ��ɶ��A�q�{����e�ɶ�
    PaymentMethodID INT NOT NULL, -- ��I�覡ID�A�~��
    StatusID INT NOT NULL, -- ���AID�A�~��
    Address NVARCHAR(200), -- �a�}
    InvoiceNumber VARCHAR(50), -- �o�����X
    IssuedTime DATETIME, -- �}�߮ɶ�
    CouponID INT, -- �u�f��ID�A�~��
    FOREIGN KEY (CustomerID) REFERENCES Users(CustomerID), -- �~�����p�Τ��
    FOREIGN KEY (PaymentMethodID) REFERENCES PaymentMethods(PaymentMethodID), -- �~�����p��I�覡��
    FOREIGN KEY (StatusID) REFERENCES Statuses(StatusID), -- �~�����p���A��
    FOREIGN KEY (CouponID) REFERENCES Coupons(CouponID) -- �~�����p�u�f���
);

--21
CREATE TABLE HomepageImages (
	ID INT IDENTITY(1,1) PRIMARY KEY, --�y����ID,�۰ʻ��W,�@���D��
    ImageID INT NOT NULL UNIQUE, -- �����Ϥ�ID,�ߤ@�Х�
	BookID INT NOT�@NULL, --���p�����yID
    FOREIGN KEY (BookID) REFERENCES Books(BookID) --�~�����s�� Books ��
);

--22
CREATE TABLE Reviews (
    ReviewID INT IDENTITY(1,1) PRIMARY KEY, -- ����ID
    CustomerID INT NOT NULL, -- �U��ID�A�~��
    Rating INT NOT NULL CHECK (Rating BETWEEN 1 AND 5), -- �����]1��5���^
    Content NVARCHAR(MAX), -- ���פ��e
    ReviewTime DATETIME NOT NULL DEFAULT GETDATE(), -- ���׮ɶ�
    BookID INT NOT NULL, -- ���yID�A�~��
    FOREIGN KEY (CustomerID) REFERENCES Users(CustomerID), -- �~�����p��Τ��
    FOREIGN KEY (BookID) REFERENCES Books(BookID) -- �~�����p����y��
);

--23
CREATE TABLE Books_Languages (
    BookID INT NOT NULL, -- ���yID�A�~��
    LanguageID INT NOT NULL, -- �y��ID�A�~��
    PRIMARY KEY (BookID, LanguageID), -- �ƦX�D��
    FOREIGN KEY (BookID) REFERENCES Books(BookID), -- �~�����p����y��
    FOREIGN KEY (LanguageID) REFERENCES Languages(LanguageID) -- �~�����p��y�ت�
);

--24
CREATE TABLE Books_Authors (
    SerialID INT PRIMARY KEY, -- �y����ID
    BookID INT NOT NULL, -- ���yID�A�~��
    AuthorID INT NOT NULL, -- �@��ID�A�~��
    AuthorDescription NVARCHAR(MAX), -- �@��²��
    FOREIGN KEY (BookID) REFERENCES Books(BookID), -- �~�����p����y��
    FOREIGN KEY (AuthorID) REFERENCES Authors(AuthorID) -- �~�����p��@�̪�
);

--25
CREATE TABLE Books_PurchaseableVersions (
    VersionID INT NOT NULL, -- �i�ʶR����ID�A�~��
    BookID INT NOT NULL, -- ���yID�A�~��
    PRIMARY KEY (VersionID, BookID), -- �ƦX�D��
    FOREIGN KEY (VersionID) REFERENCES PurchaseableVersions(VersionID), -- �~�����p��i�ʶR������
    FOREIGN KEY (BookID) REFERENCES Books(BookID) -- �~�����p����y��
);

--26
CREATE TABLE Notifications (
    NotificationID INT IDENTITY(1,1) PRIMARY KEY, -- �q��ID
    NotificationTypeID INT NOT NULL, -- �q������ID�A�~��
    CustomerID INT NOT NULL, -- �U��ID�A�~��
    CreatedTime DATETIME NOT NULL DEFAULT GETDATE(), -- �إ߮ɶ�
    UpdatedTime DATETIME NOT NULL DEFAULT GETDATE(), -- ��s�ɶ�
    Content NVARCHAR(MAX), -- �q�����e
    StatusID INT, -- ���AID�A�~��
    FOREIGN KEY (NotificationTypeID) REFERENCES NotificationTypes(NotificationTypeID), -- �~�����p��q��������
    FOREIGN KEY (CustomerID) REFERENCES Users(CustomerID), -- �~�����p��Τ��
    FOREIGN KEY (StatusID) REFERENCES Statuses(StatusID) -- �~�����p�쪬�A��
);

--27
CREATE TABLE OrderDetails (
    DetailID INT PRIMARY KEY, -- ����ID
    OrderID INT NOT NULL, -- �q��ID�A�~��
    BookID INT NOT NULL, -- ���yID�A�~��
    TotalAmount INT NOT NULL, -- �`���B
    Quantity INT NOT NULL, -- �ƶq
    FOREIGN KEY (OrderID) REFERENCES Orders(OrderID), -- �~�����p��q���
    FOREIGN KEY (BookID) REFERENCES Books(BookID) -- �~�����p����y��
);

--28
CREATE TABLE Cart_Books (
    CartID INT NOT NULL, -- �ʪ���ID�A�~��
    BookID INT NOT NULL, -- ���yID�A�~��
    FOREIGN KEY (CartID) REFERENCES Carts(CartID), -- �~�����p���ʪ�����
    FOREIGN KEY (BookID) REFERENCES Books(BookID) -- �~�����p����y��
);

--29
CREATE TABLE Books_Wishlist (
    BookID INT NOT NULL, -- ���yID�A�~��
    WishlistID INT NOT NULL, -- �@��M��ID�A�~��
    FOREIGN KEY (BookID) REFERENCES Books(BookID), -- �~�����p����y��
    FOREIGN KEY (WishlistID) REFERENCES Wishlists(WishlistID) -- �~�����p���@��M���
);

--30
CREATE TABLE EBooks (
    EBookID INT PRIMARY KEY, -- �q�l��ID
    BookID INT UNIQUE NOT NULL, -- ���yID�A�~��A�ߤ@�A�@��@���Y
    FileFormat VARCHAR(50) NOT NULL, -- �ɮ׮榡
    FileSize FLOAT NOT NULL, -- �ɮפj�p
    FileURL VARCHAR(200) NOT NULL, -- �ɮ�URL
    FOREIGN KEY (BookID) REFERENCES Books(BookID) -- �~�����p���y��
);

--31
CREATE TABLE Books_EBooks (
    SerialID INT PRIMARY KEY, -- �y����ID
    BookID INT NOT NULL, -- ���dID�A�~��
    EBookID INT NOT NULL, -- �q�l��ID�A�~��
    ReadingProgress NVARCHAR(100), -- �\Ū�i�סA��~~��/??%
    FOREIGN KEY (BookID) REFERENCES Books(BookID), -- �~�����p���d
    FOREIGN KEY (EBookID) REFERENCES EBooks(EBookID) -- �~�����p�q�l��
);

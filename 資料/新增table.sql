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
    RankID INT PRIMARY KEY, -- 主鍵
    RankName NVARCHAR(50) NOT NULL -- 階級名稱
);

--3
CREATE TABLE Admins (
    AdminID INT PRIMARY KEY, -- 主鍵
    Username VARCHAR(50) NOT NULL, -- 帳號
    Password VARCHAR(50) NOT NULL, -- 密碼
    Email VARCHAR(100) NOT NULL, -- 電子郵件
    RankID INT NOT NULL, -- 階級ID，外鍵
    FOREIGN KEY (RankID) REFERENCES Ranks(RankID) -- 外鍵關聯到階級表
);

--4
CREATE TABLE PasswordResetRequests (
    RequestID INT PRIMARY KEY, -- 主鍵
    CustomerID INT UNIQUE NOT NULL, -- 外鍵且唯一，與客戶表一對一關係
    ResetCode VARCHAR(100) NOT NULL, -- 識別重設請求的代碼
    RequestTime DATETIME NOT NULL, -- 顧客發起重設請求的時間
    ExpirationTime DATETIME NOT NULL, -- 請求過期時間
    Status NVARCHAR(50) NOT NULL, -- 重設請求的狀態
    FOREIGN KEY (CustomerID) REFERENCES Users(CustomerID) -- 外鍵關聯到客戶表
);

--5
CREATE TABLE Statuses (
    StatusID INT PRIMARY KEY, -- 狀態ID
    DetailedStatus NVARCHAR(50) NOT NULL -- 詳細狀態，例如已開立、已作廢、已使用
);

--6
CREATE TABLE PaymentMethods (
    PaymentMethodID INT PRIMARY KEY, -- 支付方式ID
    PaymentMethodName VARCHAR(50) NOT NULL -- 支付方式名稱 (例如：信用卡、貨到付款)
);

--7
CREATE TABLE Coupons (
    CouponID INT IDENTITY(1,1) PRIMARY KEY, -- 優惠券ID
    CouponCode VARCHAR(50) NOT NULL, -- 優惠碼
    Discount FLOAT NOT NULL, -- 折扣 (% 或 -$)
    StartDate DATETIME NOT NULL, -- 開始日期
    EndDate DATETIME NOT NULL, -- 結束日期
    MinimumAmount INT NOT NULL -- 適用的最低消費金額
);

--8
CREATE TABLE CouponOwnership (
    SerialID INT IDENTITY(1,1) PRIMARY KEY, -- 流水號
    CustomerID INT NOT NULL, -- 顧客ID，外鍵
    CouponID INT NOT NULL, -- 優惠券ID，外鍵
    StatusID INT NOT NULL, -- 狀態ID，外鍵
    FOREIGN KEY (CustomerID) REFERENCES Users(CustomerID), -- 外鍵關聯到用戶表
    FOREIGN KEY (CouponID) REFERENCES Coupons(CouponID), -- 外鍵關聯到優惠券表
    FOREIGN KEY (StatusID) REFERENCES Statuses(StatusID) -- 外鍵關聯到狀態表
);

--9
CREATE TABLE Carts (
    CartID INT IDENTITY(1,1) PRIMARY KEY, -- 購物車ID
    CustomerID INT UNIQUE NOT NULL, -- 顧客ID，外鍵 (一對一關係 + 唯一)
    CreationTime DATETIME NOT NULL DEFAULT GETDATE(), -- 購物車創建時間
    LastUpdatedTime DATETIME NOT NULL DEFAULT GETDATE(), -- 購物車最近更新時間
    Quantity INT NOT NULL, -- 購買數量
    StatusID INT, -- 狀態ID (保留、移除)
    FOREIGN KEY (CustomerID) REFERENCES Users(CustomerID) -- 外鍵關聯到用戶表
);

--10
CREATE TABLE EBookshelves (
    EBookshelfID INT PRIMARY KEY, -- 電子書櫃ID
    CustomerID INT UNIQUE NOT NULL, -- 顧客ID，外鍵，唯一，一對一關係
    PurchaseDate DATE NOT NULL, -- 購買日期
    FOREIGN KEY (CustomerID) REFERENCES Users(CustomerID) -- 外鍵關聯用戶表
);

--11
CREATE TABLE Wishlists (
    WishlistID INT PRIMARY KEY, -- 願望清單ID
    CustomerID INT UNIQUE NOT NULL, -- 顧客ID，外鍵，唯一，一對一關係
    FOREIGN KEY (CustomerID) REFERENCES Users(CustomerID) -- 外鍵關聯到用戶表
);

--12
CREATE TABLE Categories (
    CategoryID INT PRIMARY KEY, -- 分類ID
    CategoryName NVARCHAR(100) NOT NULL -- 分類名稱
);

--13
CREATE TABLE Subcategories (
    SubcategoryID INT PRIMARY KEY, -- 子分類ID
    SubcategoryName NVARCHAR(100) NOT NULL, -- 子分類名稱
    CategoryID INT NOT NULL, -- 分類ID，外鍵
    FOREIGN KEY (CategoryID) REFERENCES Categories(CategoryID) -- 外鍵關聯到分類表
);

--14
CREATE TABLE Languages (
    LanguageID INT PRIMARY KEY, -- 語種ID
    Language NVARCHAR(100) NOT NULL -- 語言名稱
);

--15
CREATE TABLE Authors (
    AuthorID INT PRIMARY KEY, -- 作者ID
    AuthorName NVARCHAR(100) NOT NULL -- 作者姓名
);

--16
CREATE TABLE Publishers (
    PublisherID INT PRIMARY KEY, -- 出版社ID
    PublisherName NVARCHAR(200) NOT NULL, -- 出版社名稱
    PublisherDescription NVARCHAR(MAX) -- 出版社簡介
);

--17
CREATE TABLE PurchaseableVersions (
    VersionID INT PRIMARY KEY, -- 可購買版本ID
    VersionName NVARCHAR(100) NOT NULL -- 可購買版本名稱
);

--18
CREATE TABLE NotificationTypes (
    NotificationTypeID INT PRIMARY KEY, -- 通知類型ID
    NotificationType VARCHAR(100) NOT NULL -- 通知類型名稱 (例如：訂單通知、特價清單通知)
);

--19
CREATE TABLE Books (
    BookID INT IDENTITY(1,1) PRIMARY KEY, -- 書籍ID
    BookName NVARCHAR(200) NOT NULL, -- 書名
    PublisherID INT NOT NULL, -- 出版社ID，外鍵
    Price INT NOT NULL, -- 價格
    Stock INT NOT NULL, -- 庫存量
    Description NVARCHAR(MAX), -- 書籍描述
    ShelfTime DATETIME NOT NULL DEFAULT GETDATE(), -- 書籍上架時間
    LastUpdated DATETIME NOT NULL DEFAULT GETDATE(), -- 最後更新時間
    PublishDate DATE NOT NULL, -- 出版日期
    SubcategoryID INT NOT NULL, -- 書籍子類別ID，外鍵
    ImageURL VARCHAR(200), -- 圖片URL
    ImageDescription NVARCHAR(200), -- 圖片說明
    FOREIGN KEY (PublisherID) REFERENCES Publishers(PublisherID), -- 外鍵關聯到出版社表
    FOREIGN KEY (SubcategoryID) REFERENCES Subcategories(SubcategoryID) -- 外鍵關聯到子分類表
);

--20
CREATE TABLE Orders (
    OrderID INT IDENTITY(1,1) PRIMARY KEY, -- 訂單ID
    CustomerID INT NOT NULL, -- 顧客ID，外鍵
    TotalAmount INT NOT NULL, -- 訂單總金額
    OrderStatus VARCHAR(50) NOT NULL, -- 訂單狀態 (待處理、已付款、已出貨)
    OrderCreationTime DATETIME NOT NULL DEFAULT GETDATE(), -- 訂單生成時間，默認為當前時間
    PaymentMethodID INT NOT NULL, -- 支付方式ID，外鍵
    StatusID INT NOT NULL, -- 狀態ID，外鍵
    Address NVARCHAR(200), -- 地址
    InvoiceNumber VARCHAR(50), -- 發票號碼
    IssuedTime DATETIME, -- 開立時間
    CouponID INT, -- 優惠券ID，外鍵
    FOREIGN KEY (CustomerID) REFERENCES Users(CustomerID), -- 外鍵關聯用戶表
    FOREIGN KEY (PaymentMethodID) REFERENCES PaymentMethods(PaymentMethodID), -- 外鍵關聯支付方式表
    FOREIGN KEY (StatusID) REFERENCES Statuses(StatusID), -- 外鍵關聯狀態表
    FOREIGN KEY (CouponID) REFERENCES Coupons(CouponID) -- 外鍵關聯優惠券表
);

--21
CREATE TABLE HomepageImages (
	ID INT IDENTITY(1,1) PRIMARY KEY, --流水號ID,自動遞增,作為主鍵
    ImageID INT NOT NULL UNIQUE, -- 首頁圖片ID,唯一標示
	BookID INT NOT　NULL, --關聯的書籍ID
    FOREIGN KEY (BookID) REFERENCES Books(BookID) --外鍵關連到 Books 表
);

--22
CREATE TABLE Reviews (
    ReviewID INT IDENTITY(1,1) PRIMARY KEY, -- 評論ID
    CustomerID INT NOT NULL, -- 顧客ID，外鍵
    Rating INT NOT NULL CHECK (Rating BETWEEN 1 AND 5), -- 評分（1到5分）
    Content NVARCHAR(MAX), -- 評論內容
    ReviewTime DATETIME NOT NULL DEFAULT GETDATE(), -- 評論時間
    BookID INT NOT NULL, -- 書籍ID，外鍵
    FOREIGN KEY (CustomerID) REFERENCES Users(CustomerID), -- 外鍵關聯到用戶表
    FOREIGN KEY (BookID) REFERENCES Books(BookID) -- 外鍵關聯到書籍表
);

--23
CREATE TABLE Books_Languages (
    BookID INT NOT NULL, -- 書籍ID，外鍵
    LanguageID INT NOT NULL, -- 語種ID，外鍵
    PRIMARY KEY (BookID, LanguageID), -- 複合主鍵
    FOREIGN KEY (BookID) REFERENCES Books(BookID), -- 外鍵關聯到書籍表
    FOREIGN KEY (LanguageID) REFERENCES Languages(LanguageID) -- 外鍵關聯到語種表
);

--24
CREATE TABLE Books_Authors (
    SerialID INT PRIMARY KEY, -- 流水號ID
    BookID INT NOT NULL, -- 書籍ID，外鍵
    AuthorID INT NOT NULL, -- 作者ID，外鍵
    AuthorDescription NVARCHAR(MAX), -- 作者簡介
    FOREIGN KEY (BookID) REFERENCES Books(BookID), -- 外鍵關聯到書籍表
    FOREIGN KEY (AuthorID) REFERENCES Authors(AuthorID) -- 外鍵關聯到作者表
);

--25
CREATE TABLE Books_PurchaseableVersions (
    VersionID INT NOT NULL, -- 可購買版本ID，外鍵
    BookID INT NOT NULL, -- 書籍ID，外鍵
    PRIMARY KEY (VersionID, BookID), -- 複合主鍵
    FOREIGN KEY (VersionID) REFERENCES PurchaseableVersions(VersionID), -- 外鍵關聯到可購買版本表
    FOREIGN KEY (BookID) REFERENCES Books(BookID) -- 外鍵關聯到書籍表
);

--26
CREATE TABLE Notifications (
    NotificationID INT IDENTITY(1,1) PRIMARY KEY, -- 通知ID
    NotificationTypeID INT NOT NULL, -- 通知類型ID，外鍵
    CustomerID INT NOT NULL, -- 顧客ID，外鍵
    CreatedTime DATETIME NOT NULL DEFAULT GETDATE(), -- 建立時間
    UpdatedTime DATETIME NOT NULL DEFAULT GETDATE(), -- 更新時間
    Content NVARCHAR(MAX), -- 通知內容
    StatusID INT, -- 狀態ID，外鍵
    FOREIGN KEY (NotificationTypeID) REFERENCES NotificationTypes(NotificationTypeID), -- 外鍵關聯到通知類型表
    FOREIGN KEY (CustomerID) REFERENCES Users(CustomerID), -- 外鍵關聯到用戶表
    FOREIGN KEY (StatusID) REFERENCES Statuses(StatusID) -- 外鍵關聯到狀態表
);

--27
CREATE TABLE OrderDetails (
    DetailID INT PRIMARY KEY, -- 明細ID
    OrderID INT NOT NULL, -- 訂單ID，外鍵
    BookID INT NOT NULL, -- 書籍ID，外鍵
    TotalAmount INT NOT NULL, -- 總金額
    Quantity INT NOT NULL, -- 數量
    FOREIGN KEY (OrderID) REFERENCES Orders(OrderID), -- 外鍵關聯到訂單表
    FOREIGN KEY (BookID) REFERENCES Books(BookID) -- 外鍵關聯到書籍表
);

--28
CREATE TABLE Cart_Books (
    CartID INT NOT NULL, -- 購物車ID，外鍵
    BookID INT NOT NULL, -- 書籍ID，外鍵
    FOREIGN KEY (CartID) REFERENCES Carts(CartID), -- 外鍵關聯到購物車表
    FOREIGN KEY (BookID) REFERENCES Books(BookID) -- 外鍵關聯到書籍表
);

--29
CREATE TABLE Books_Wishlist (
    BookID INT NOT NULL, -- 書籍ID，外鍵
    WishlistID INT NOT NULL, -- 願望清單ID，外鍵
    FOREIGN KEY (BookID) REFERENCES Books(BookID), -- 外鍵關聯到書籍表
    FOREIGN KEY (WishlistID) REFERENCES Wishlists(WishlistID) -- 外鍵關聯到願望清單表
);

--30
CREATE TABLE EBooks (
    EBookID INT PRIMARY KEY, -- 電子書ID
    BookID INT UNIQUE NOT NULL, -- 書籍ID，外鍵，唯一，一對一關係
    FileFormat VARCHAR(50) NOT NULL, -- 檔案格式
    FileSize FLOAT NOT NULL, -- 檔案大小
    FileURL VARCHAR(200) NOT NULL, -- 檔案URL
    FOREIGN KEY (BookID) REFERENCES Books(BookID) -- 外鍵關聯書籍表
);

--31
CREATE TABLE Books_EBooks (
    SerialID INT PRIMARY KEY, -- 流水號ID
    BookID INT NOT NULL, -- 書櫃ID，外鍵
    EBookID INT NOT NULL, -- 電子書ID，外鍵
    ReadingProgress NVARCHAR(100), -- 閱讀進度，第~~頁/??%
    FOREIGN KEY (BookID) REFERENCES Books(BookID), -- 外鍵關聯書櫃
    FOREIGN KEY (EBookID) REFERENCES EBooks(EBookID) -- 外鍵關聯電子書
);

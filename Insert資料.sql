-- ==========================================
-- ** 下方書籍圖片之版權聲明 (Image Copyright Disclaimer) **
-- 本 SQL 檔案內的書籍圖片網址 (ImageURL 欄位) 來自「三民書局」官網，
-- 僅作為學術研究與開發測試用途，無任何商業用途。
-- 這些圖片不會儲存在本專案內，僅保留其 URL。
-- 如果有版權問題，請通知我們，我們將立即移除相關資料。
-- 圖片來源: https://www.sanmin.com.tw/
-- ==========================================

-- Insert SQL 表格資料順序:
-- 1 分類Insert
USE storedb
INSERT INTO Categories (CategoryID, CategoryName)
VALUES 
(1,'親子共讀'),
(2,'休閒生活'),
(3,'語言學習'),
(4,'文學作品'),
(5,'生活風格'),
(6,'藝術設計'),
(7,'醫療保健');

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
(8,4,'小說'),
(9,5,'遊戲'),
(10,6,'設計'),
(11,7,'健康與健身');

--------------------------------------------------------------------------------------------
-- 3 出版社Insert
USE storedb
INSERT INTO Publishers (PublisherID, PublisherName) 
VALUES
(1,'小宇宙文化'),
(2,'青文'),
(3,'眾文圖書'),
(4,'皇冠'),
(5,'Pointed Leaf Pr');

--------------------------------------------------------------------------------------------
-- 4 作者Insert
USE storedb
INSERT INTO Authors(AuthorID, AuthorName, AuthorDescription)
VALUES
(1,'大衛.里奇斐德','大衛•里奇斐德（David Litchfield）／文圖

大衛‧里奇斐德很早就開始作畫，他崇拜阿爾伯特‧烏德佐、西維亞‧喬邁、雍‧卡拉森和陳志勇，插畫作品也深受他們影響。他運用Photoshop將各種傳統技法結合在一起，創作出大器、具有戲劇張力的畫面。作品氛圍獨特。繪本首作《森林裡的鋼琴師》已印行12種語言，在世界各地大受讚譽，且榮獲2016年英國水石童書最佳插畫大獎。'),(2,'近藤憲一','近藤憲一。
日本漫畫家。
代表作為《黑暗集會》(青文出版)。'),(3,'松本紀子; 佐佐木仁子','松本紀子‧佐佐木仁子
日本「JLPT日本語能力試驗」教材權威作者，長期研究日檢題型，並出版了十餘冊相關書籍，讀者遍及亞洲各國。'),
(4,'J.K.羅琳','J.K.羅琳 J.K. Rowling
以《哈利波特》系列的作者聞名於世，七集小說陸續在一九九七至二○○七年間出版。哈利波特的點子，是J.K.羅琳從曼徹斯特前往倫敦的誤點火車上想到的。哈利、榮恩、妙麗長年來大受歡迎的冒險，在全球創下五億部的暢銷紀錄，並被翻譯成超過八十種語言，以及改編拍成八部電影。'),
(5,'Lauren Downing Peters','cool');

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
INSERT INTO Books (BookName, PublisherID, AuthorID, LanguageID, SubcategoryID, Price, 
Stock, Description, ShelfTime, LastUpdated, PublishDate, ImageURL, ImageDescription) 
VALUES 
('隔壁的恐龍先生', 1, 1, 1, 1, 380, 15, '莉茲住在一條宜人的街上，這裡有蓊鬱的綠樹、歡唱的鳥兒，還有親切的鄰居們。像是住在隔壁的威爾遜先生，
大家都覺得他和藹可親，但莉茲卻發現一個天大的祕密——威爾遜先生，其實是……一隻恐龍！',
'2025-01-01', '2025-01-01', '2024-02-07', 
'https://cdnec.sanmin.com.tw/product_images/626/626747638.jpg', '這本書的封面'),
('爺爺的神祕巨人', 1, 1, 1, 1, 329, 20, '爺爺說，有個巨人住在小鎮上，為人們做了許多好事。但是，比利不相信，畢竟巨人那麼大，根本無處可藏，
而且他為什麼非要保持神祕不可呢？隨著時間流逝，比利將明白，那麼大的祕密終究無法隱藏……',
'2025-01-01', '2025-01-01', '2017-10-02', 
'https://cdnec.sanmin.com.tw/product_images/957/957146293.jpg', '這本書的封面'),
('籽兒與鳥蛋', 1, 1, 1, 2, 359, 7, '即使不復年少初識時的模樣
友誼卻從沒因時間和距離而改變
即使生活讓彼此分離
真正的朋友總有一天會再相聚
就像籽兒與鳥蛋，他們的友情無與倫比！',
'2025-01-01', '2025-01-01', '2024-01-26', 
'https://cdnec.sanmin.com.tw/product_images/626/626361475.jpg', '這本書的封面'),
('森林小熊的音樂會', 1, 1, 1, 2, 300, 15, '你還記得有一隻熊
在森林裡發現鋼琴的故事嗎？
幾年之後，大熊結束他的夢幻之旅，
回到森林。
舊鋼琴被遺忘在空地裡，
後來才出生的小熊，
可以說服她的爸爸再次彈琴嗎？',
'2025-01-01', '2025-01-01', '2024-01-15', 
'https://cdnec.sanmin.com.tw/product_images/986/986440323.jpg', '這本書的封面'),
('城市裡的提琴手', 1, 1, 1, 2, 300, 15, '海先生是小提琴手，小狗雨果是他最忠實的粉絲。當雨果學會小提琴並獲得巡演機會，海先生心生嫉妒。最終，他能放下驕傲，為好友喝采嗎？
',
'2025-01-01', '2025-01-01', '2018-11-01', 
'https://cdnec.sanmin.com.tw/product_images/986/986440252.jpg', '這本書的封面'),
('黑暗集會01', 2, 2, 1, 3, 99, 0, '因為自己的靈異體質，
過去曾害朋友捲入中邪事件的大學生‧螢多朗。
隨著上了大學，螢多朗決定當家庭教師來打工，
沒想到竟然被他的第一個學生，天才少女‧夜宵看穿自己
擁有靈異體質，甚至還邀請螢多朗一起前往靈異景點…!?
令人顫慄的捉捕惡靈遊戲開幕!!',
'2025-01-01', '2025-01-01', '2021-05-19', 
'https://cdnec.sanmin.com.tw/product_images/986/986548541.jpg', '這本書的封面'),
('黑暗集會02', 2, 2, 1, 3, 99, 0, '夜宵在得知詠子也受到了和螢多朗相同的詛咒後，
便向螢多朗提議一起去殺了元凶的惡靈來解除詛咒。
但接近靈異的行為和他想回歸社會的目標卻是完全相反，
因此他無法立即給出答覆。
與此同時，螢多朗竟在與詠子一起選修的
都市傳說課堂上被惡靈襲擊了‧‧‧!?',
'2025-01-01', '2025-01-01', '2022-01-24', 
'https://cdnec.sanmin.com.tw/product_images/626/626303706.jpg', '這本書的封面'),
('黑暗集會03', 2, 2, 1, 3, 99, 2, '不光為了自己，也為了保護最重要的詠子，
螢多朗決定要和夜宵一同踏上蒐集鬼怪之路。
而他們靈異地點巡禮的第一站便是傳聞中都內最恐怖的地方。
他們究竟在那裡遭遇了什麼‧‧‧!?',
'2025-01-01', '2025-01-01', '2022-05-12', 
'https://cdnec.sanmin.com.tw/product_images/626/626322134.jpg', '這本書的封面'),
('黑暗集會04', 2, 2, 1, 3, 99, 5, '壽命僅剩 4 年的少女愛依，被選為「神之新娘」。
夜宵等人為拯救她，展開狩神計畫，前往靈異地點補充戰力。
然而，他們卻陷入前所未有的恐怖體驗……!?',
'2025-01-01', '2025-01-01', '2022-07-25', 
'https://cdnec.sanmin.com.tw/product_images/626/626322482.jpg', '這本書的封面'),
('黑暗集會05', 2, 2, 1, 3, 99, 13, '為對抗附身於愛依的神，夜宵等人前往危險度 S 級的「舊 F 隧道」。
作為準備，他們先回收安置在外的「畢業生」。
然而，在禁忌的隧道深處，他們目睹了難以想像的恐怖景象……!?',
'2025-01-01', '2025-01-01', '2022-11-16', 
'https://cdnec.sanmin.com.tw/product_images/626/626322866.jpg', '這本書的封面'),
('新日檢完勝500題N4-N5', 3, 3, 1, 6, 350, 8, 'JLPT權威作者出題，大量練習提升實力！
本書由日本專業出版社ASK邀請日檢教材權威執筆，累積多年研究，廣受考生與教師好評。
本書前身為《新日檢500題 N4‧N5》，2015年全新修訂，精選500題與延伸內容，助您高效衝刺！', '2025-01-01', '2025-01-01', '2026-01-10', 
'https://cdnec.sanmin.com.tw/product_images/957/957532473.jpg', '這本書的封面'),
('新日檢完勝500題N3', 3, 3, 1, 6, 350, 7, '本書由日本專業出版社ASK邀請JLPT權威作者執筆，累積多年題型研究，暢銷亞洲各國。
前身為《新日檢500題 N2‧N3》，廣受好評，2015年全新修訂，精選500題與延伸內容，助考生高效衝刺！', '2025-01-01', '2025-01-01', '2015-11-05', 
'https://cdnec.sanmin.com.tw/product_images/957/957532469.jpg', '這本書的封面'),
('新日檢完勝500題N2', 3, 3, 1, 6, 350, 10, '本書由日本專業出版社ASK邀請JLPT權威作者執筆，累積多年題型研究，暢銷亞洲各國。
前身為《新日檢500題 N2‧N3》，廣受好評，2015年全新修訂，精選500題與延伸內容，助考生高效衝刺！','2025-01-01', '2025-01-01', '2015-11-05', 
'https://cdnec.sanmin.com.tw/product_images/957/957532468.jpg', '這本書的封面'),
('新日檢完勝500題N1', 3, 3, 1, 6, 350, 15, '本書由日本專業出版社ASK邀請JLPT權威作者執筆，累積多年題型研究，暢銷亞洲各國。
前身為《新日檢500題 N2‧N3》，廣受好評，2015年全新修訂，精選500題與延伸內容，助考生高效衝刺！','2025-01-01', '2025-01-01', '2016-03-08', 
'https://cdnec.sanmin.com.tw/product_images/626/626023456.jpg', '這本書的封面'),
('新日檢完勝對策N1：漢字•語彙', 3, 3, 1, 6, 580, 3, '「新日檢完勝對策」由JLPT權威執筆，規劃兩個月應考計畫，涵蓋N5至N1，共15冊，奠定四大基礎。

本系列簡明易懂，深受考生喜愛。新版《N1 漢字‧語彙》為《日本語総まとめ》合訂本，更便於學習與應試。
','2025-01-01', '2025-01-01', '2023-11-01', 
'https://cdnec.sanmin.com.tw/product_images/957/957532627.jpg', '這本書的封面'),
('哈利波特01：神秘的魔法石', 4, 4, 1, 8, 420, 20, '十一歲的哈利波特被姨丈一家當成怪胎，無法解釋身邊的奇異現象。直到神秘信件寄來並被姨丈沒收，信件持續湧現，最終巨人海格親自告訴哈利：「你是巫師，霍格華茲等著你！」從此，他開始踏上魔法冒險！',
'2025-01-01', '2025-01-01', '2020-08-10', 
'https://cdnec.sanmin.com.tw/product_images/957/957333556.jpg', '這本書的封面'),
('哈利波特02：消失的密室', 4, 4, 1, 8, 480, 20, '夏天結束前，哈利應該回到霍格華茲，但家庭小精靈多比警告他回學校會有生命危險。多比攔截朋友們的信件，並用魔法陷害哈利，讓他遭到禁足。

然而，哈利渴望回到霍格華茲，想念那座神秘的城堡和魔法課程。聽見內心的呼喚，榮恩駕飛天車把他從姨丈家救了出來。','2025-01-01', '2025-01-01', '2020-09-14', 
'https://cdnec.sanmin.com.tw/product_images/957/957333590.jpg', '這本書的封面'),
('哈利波特03：阿茲卡班的逃犯', 4, 4, 1, 8, 520, 6, '瑪姬姑姑一來，哈利又要面對一週的惡夢。她總愛用手杖敲哈利的腳，還曾讓他因為踩到牛頭犬而在樹上躲過夜。她和德思禮家一樣，總愛貶低哈利，直到她侮辱哈利的父母，被哈利用魔法變成大氣球。','2025-01-01', '2025-01-01', '2020-11-16', 
'https://cdnec.sanmin.com.tw/product_images/957/957333609.jpg', '這本書的封面'),
('哈利波特04：火盃的考驗', 4, 4, 1, 8, 699, 7, '哈利成為巫師後，才明白額頭上的閃電形傷疤是「那個人」用邪咒殺害父母的痕跡。咒語反彈，讓哈利倖免於難，也幫助魔法界擺脫了恐怖統治。然而，這也讓他注定要終生面對黑暗勢力的追殺。
','2025-01-01', '2025-01-01', '2021-03-02', 
'https://cdnec.sanmin.com.tw/product_images/957/957333639.jpg', '這本書的封面'),
('哈利波特05：鳳凰會的密令', 4, 4, 1, 8, 849, 5, '升上五年級前的暑假，哈利面對的依然是痛苦漫長的日子。黑魔王回歸，但他揭露危機後，卻被困在小惠因區一個月。當催狂魔襲擊時，哈利忙著與表哥吵架，直到施展護法咒擊退了它。他知道，這只是危險的開始，黑魔王為了復仇，將不惜一切代價。
',
'2025-01-01', '2025-01-01', '2021-05-17', 
'https://cdnec.sanmin.com.tw/product_images/957/957333692.jpg', '這本書的封面'),

('The Incomplete (Updated Edition): Highsnobiety Guide to Street Fashion and Culture', 5, 5, 2, 10, 2850, 15, 'dfasf','2025-01-01', '2025-01-01', '2024-02-07', 
'https://cdnec.sanmin.com.tw/product_images/396/396704183.jpg', '這本書的封面'),
('Fashion Before Plus-Size: Bodies, Bias, and the Birth of an Industry', 5, 5, 2, 10, 2097, 15, 'dfasf','2025-01-01', '2025-01-01', '2024-02-07', 
'https://cdnec.sanmin.com.tw/product_images/135/135039937.jpg', '這本書的封面'),
('Typophoto: New Typography and the Reinvention of Photography', 5, 5, 2, 10, 1138, 15, 'dfasf','2025-01-01', '2025-01-01', '2024-02-07', 
'https://cdnec.sanmin.com.tw/product_images/151/151791823.jpg', '這本書的封面'),
('El Libro del Diseño (the Design Book)', 5, 5, 2, 10, 1140, 15, 'dfasf','2025-01-01', '2025-01-01', '2024-02-07', 
'https://cdnec.sanmin.com.tw/product_images/059/059384813.jpg', '這本書的封面'),
('Design Unlimited: Visual Communication and Storytelling with Generative Design and Data', 5, 5, 2, 10, 1898, 15, 'dfasf','2025-01-01', '2025-01-01', '2024-02-07', 
'https://cdnec.sanmin.com.tw/product_images/372/372121041.jpg', '這本書的封面'),
('Watch Dogs Legion: Resistance Report ― Companion Book', 5, 5, 2, 9, 1140, 15, 'dfasf','2025-01-01', '2025-01-01', '2024-02-07', 
'https://cdnec.sanmin.com.tw/product_images/168/168383804.jpg', '這本書的封面'),
('Peace, Love, and Coloring', 5, 5, 2, 9, 570, 15, 'dfasf','2025-01-01', '2025-01-01', '2024-02-07', 
'https://cdnec.sanmin.com.tw/product_images/166/166720904.jpg', '這本書的封面'),
('Wordsearch Spanish: The Fun Way to Learn the Language', 5, 5, 2, 9, 380, 15, 'dfasf','2025-01-01', '2025-01-01', '2024-02-07', 
'https://cdnec.sanmin.com.tw/product_images/139/139885219.jpg', '這本書的封面'),
('Mangatopia: Puppies and Popsicles: A Playfully Sweet Coloring Book of Anime and Manga', 5, 5, 2, 9, 608, 15, 'dfasf','2025-01-01', '2025-01-01', '2024-02-07', 
'https://cdnec.sanmin.com.tw/product_images/125/125038915.jpg', '這本書的封面'),
('The Official Wordscapes Puzzle Book Volume 2: Volume 2', 5, 5, 2, 9, 494, 15, 'dfasf','2025-01-01', '2025-01-01', '2024-02-07', 
'https://cdnec.sanmin.com.tw/product_images/152/152489612.jpg', '這本書的封面'),
('Bibliotherapy in the Bronx', 5, 5, 2, 11, 1064, 15, 'dfasf','2025-01-01', '2025-01-01', '2024-02-07', 
'https://cdnec.sanmin.com.tw/product_images/195/195590587.jpg', '這本書的封面'),
('The Bodyweight Blueprint: Over 70 Workouts to Achieve Your Training Goals', 5, 5, 2, 11, 1062, 15, 'dfasf','2025-01-01', '2025-01-01', '2024-02-07', 
'https://cdnec.sanmin.com.tw/product_images/171/171823427.jpg', '這本書的封面'),
('The Beauty Molecule: Introducing Neuroceuticals, the Breakthrough for Ageless Beauty', 5, 5, 2, 11, 1140, 15, 'dfasf','2025-01-01', '2025-01-01', '2024-02-07', 
'https://cdnec.sanmin.com.tw/product_images/125/125028679.jpg', '這本書的封面'),
('Sleep for Dummies', 5, 5, 2, 11, 874, 15, 'dfasf','2025-01-01', '2025-01-01', '2024-02-07', 
'https://cdnec.sanmin.com.tw/product_images/139/139426234.jpg', '這本書的封面'),
('Inteligencia Erotica', 5, 5, 2, 11, 720, 15, 'dfasf','2025-01-01', '2025-01-01', '2024-02-07', 
'https://cdnec.sanmin.com.tw/product_images/841/841016421.jpg', '這本書的封面')
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
(12, 12),
(13, 13);

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
Insert into Ranks values ('經理');  
Insert into Ranks values ('主管');  
Insert into Ranks values ('組長');
Insert into Ranks values ('員工');

Insert into Admins values ('andy','asd123','asd123@gamil.com',1);  
Insert into Admins values ('bella','asd456','asd456@gamil.com',2);  
Insert into Admins values ('cindy','asd789','asd789@gamil.com',3);

---------------------------------------------------------------------------------------------
-- 優惠券Insert (會由後台管理直接發送優惠券給所有顧客)
-- 以下Insert為上台要顯示在畫面上新註冊的顧客會自動領到的券)
USE storedb
INSERT INTO Coupons (CouponCode, Discount, StartDate, EndDate, MinimumAmount)
VALUES
('WELCOME50', 50, '2025-02-01', '2025-02-28', 300);        --新註冊的顧客會自動領到的券

/* 範例參考:
('XMAS2024', 50, '2024-12-20', '2024-12-31', 300),         --已過期
('WINTERSALE', 55, '2025-12-01', '2025-02-28', 300),       --即將過期
('NEWYEAR2025', 88, '2024-12-31', '2025-02-10', 330),      --已過期
('SUMMER2025', 15, '2025-06-01', '2025-08-31', 150),
('WINTERSALE', 25, '2025-12-01', '2025-12-31', 200),     --已過期
('BLACKFRIDAY50', 50, '2025-11-25', '2025-11-29', 300),  --已過期
('NEWYEAR2025', 5, '2025-01-01', '2025-01-31', 120),     --已過期
('DISCOUNT30', 30, '2025-07-01', '2025-07-31', 150),
('FLASHSALE', 10, '2025-01-14', '2025-01-14', 150),      --已過期
('HOLIDAY20', 20, '2025-12-15', '2025-12-25', 120),      --已過期
('VIPCODE', 40, '2025-05-01', '2025-05-31', 500),
('EASTER15', 15, '2025-04-01', '2025-04-10', 180);
*/

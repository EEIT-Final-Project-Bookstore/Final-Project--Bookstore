-- Insert表格資料順序:
﻿INSERT INTO Categories (
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
(1,'小宇宙文化'),(2,'青文'),(3,'眾文圖書'),(4,'皇冠');


 

INSERT INTO Authors(
	AuthorID,
	AuthorName,
	AuthorDescription
)
VALUES
(1,'大衛.里奇斐德','cool
cool 
cool'),(2,'近藤憲一','近藤憲一。\n
日本漫畫家。\n
代表作為《黑暗集會》(青文出版)。'),(3,'松本紀子; 佐佐木仁子','松本紀子‧佐佐木仁子
日本「JLPT日本語能力試驗」教材權威作者，長期研究日檢題型，並出版了十餘冊相關書籍，讀者遍及亞洲各國。'),
(4,'J.K.羅琳','J.K.羅琳 J.K. Rowling
以《哈利波特》系列的作者聞名於世，七集小說陸續在一九九七至二○○七年間出版。哈利波特的點子，是J.K.羅琳從曼徹斯特前往倫敦的誤點火車上想到的。哈利、榮恩、妙麗長年來大受歡迎的冒險，在全球創下五億部的暢銷紀錄，並被翻譯成超過八十種語言，以及改編拍成八部電影。');

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
在森林裡發現鋼琴的故事嗎？\n
幾年之後，大熊結束他的夢幻之旅，\n
回到森林。\n
舊鋼琴被遺忘在空地裡，\n
後來才出生的小熊，\n
可以說服她的爸爸再次彈琴嗎？',
'2025-01-01', '2025-01-01', '2024-01-15', 
'https://cdnec.sanmin.com.tw/product_images/986/986440323.jpg', '這本書的封面'),
('城市裡的提琴手', 1, 1, 1, 2, 300, 15, '海先生是一位小提琴手，小狗雨果是他最忠實的粉絲。無論美好、艱難或是瘋狂的時刻，海先生和雨果一直都是最好的朋友。但是當雨果學會演奏小提琴，眾人的目光便漸漸轉移到雨果身上，甚至還獲得與知名的大熊大樂團合作、參加巡迴演出的機會。\n
在大型音樂會上演奏，曾經是海先生的夢想，但現在這個機會卻不屬於他。海先生漸漸的對雨果燃起嫉妒的火苗，甚至口出惡言，說出傷害他的話。
……海先生是否能放下心中的驕傲，衷心的為雨果喝采呢？
',
'2025-01-01', '2025-01-01', '2018-11-01', 
'https://cdnec.sanmin.com.tw/product_images/986/986440252.jpg', '這本書的封面'),
('黑暗集會01', 2, 2, 1, 3, 99, 0, '因為自己的靈異體質\n，
過去曾害朋友捲入中邪事件的大學生‧螢多朗。\n
隨著上了大學，螢多朗決定當家庭教師來打工，\n
沒想到竟然被他的第一個學生，天才少女‧夜宵看穿自己\n
擁有靈異體質，甚至還邀請螢多朗一起前往靈異景點…!?\n
令人顫慄的捉捕惡靈遊戲開幕!!',
'2025-01-01', '2025-01-01', '2021-05-19', 
'https://cdnec.sanmin.com.tw/product_images/986/986548541.jpg', '這本書的封面'),
('黑暗集會02', 2, 2, 1, 3, 99, 0, '夜宵在得知詠子也受到了和螢多朗相同的詛咒後，\n
便向螢多朗提議一起去殺了元凶的惡靈來解除詛咒。\n
但接近靈異的行為和他想回歸社會的目標卻是完全相反，\n
因此他無法立即給出答覆。\n
與此同時，螢多朗竟在與詠子一起選修的\n
都市傳說課堂上被惡靈襲擊了‧‧‧!?',
'2025-01-01', '2025-01-01', '2022-01-24', 
'https://cdnec.sanmin.com.tw/product_images/626/626303706.jpg', '這本書的封面'),
('黑暗集會03', 2, 2, 1, 3, 99, 2, '不光為了自己，也為了保護最重要的詠子，\n
螢多朗決定要和夜宵一同踏上蒐集鬼怪之路。\n
而他們靈異地點巡禮的第一站便是傳聞中都內最恐怖的地方。\n
他們究竟在那裡遭遇了什麼‧‧‧!?',
'2025-01-01', '2025-01-01', '2022-05-12', 
'https://cdnec.sanmin.com.tw/product_images/626/626322134.jpg', '這本書的封面'),
('黑暗集會04', 2, 2, 1, 3, 99, 5, '做為「神之新娘」且壽命僅剩４年的少女‧愛依。\n
夜宵等人為了將她從既定的命運中拯救出來並抓住依附在她身上的神，\n
便開始著手準備。為了對抗擁有壓倒性力量的神明，\n
夜宵等人便前往傳聞棲息著強大惡靈的全國各大靈異地點補充戰力。\n
然而，他們竟因此遭遇了前所未有的極恐體驗…!?',
'2025-01-01', '2025-01-01', '2022-07-25', 
'https://cdnec.sanmin.com.tw/product_images/626/626322482.jpg', '這本書的封面'),
('黑暗集會05', 2, 2, 1, 3, 99, 13, '為了對抗附在愛依身上的神，打算加強戰力的夜宵\n
等人的下一個目的地，便是神隱和殺人事件等傳言\n
頻傳的危險度等級Ｓ「舊Ｆ隧道」。做為其戰略準備，\n
他們決定先去回收安置在外面的「畢業生」…\n
接著，在生者絕對不可進入的陰暗隧道的深處，\n
夜宵等人所看見的恐怖場景究竟是…!?',
'2025-01-01', '2025-01-01', '2022-11-16', 
'https://cdnec.sanmin.com.tw/product_images/626/626322866.jpg', '這本書的封面'),
('新日檢完勝500題N4-N5', 3, 3, 1, 6, 350, 8, '「JLPT日本語能力試驗」權威作者出題。大量練習，提升考題熟悉度！
本書由日本專業語言教材出版社ASK邀請「JLPT日本語能力試驗」教材權威作者執筆。作者長期研究題型，出版了十餘冊日檢系列暢銷叢書，讀者遍及亞洲各國。本書前身為《 新日檢500題N4‧N5︰文字‧語彙‧文法》，出版至今，由於不斷受到考生及教師們的好評，於2015年推出全新修訂版本，延續「大量練習，提升實力」的宗旨，修訂版更精準掌握最關鍵的500題與其延伸，幫助考生在最短時間內全力衝刺。',
'2025-01-01', '2025-01-01', '2026-01-10', 
'https://cdnec.sanmin.com.tw/product_images/957/957532473.jpg', '這本書的封面'),
('新日檢完勝500題N3', 3, 3, 1, 6, 350, 7, '本書由日本專業語言教材出版社ASK邀請「JLPT日本語能力試驗」教材權威作者執筆。作者長期研究題型，出版了十餘冊日檢系列暢銷叢書，讀者遍及亞洲各國。本書前身為《 新日檢500題N2‧N3︰文字‧語彙‧文法》，出版至今，由於不斷受到考生及教師們的好評，於2015年推出全新修訂版本，延續「大量練習，提升實力」的宗旨，修訂版更精準掌握最關鍵的500題與其延伸，幫助考生在最短時間內全力衝刺。',
'2025-01-01', '2025-01-01', '2015-11-05', 
'https://cdnec.sanmin.com.tw/product_images/957/957532469.jpg', '這本書的封面'),
('新日檢完勝500題N2', 3, 3, 1, 6, 350, 10, '本書由日本專業語言教材出版社ASK邀請「JLPT日本語能力試驗」教材權威作者執筆。作者長期研究題型，出版了十餘冊日檢系列暢銷叢書，讀者遍及亞洲各國。本書前身為《 新日檢500題N2‧N3︰文字‧語彙‧文法》，出版至今，由於不斷受到考生及教師們的好評，於2015年推出全新修訂版本，延續「大量練習，提升實力」的宗旨，修訂版更精準掌握最關鍵的500題與其延伸，幫助考生在最短時間內全力衝刺。',
'2025-01-01', '2025-01-01', '2015-11-05', 
'https://cdnec.sanmin.com.tw/product_images/957/957532468.jpg', '這本書的封面'),
('新日檢完勝500題N1', 3, 3, 1, 6, 350, 15, '本書由日本專業語言教材出版社ASK邀請「JLPT日本語能力試驗」教材權威作者執筆。作者長期研究題型，出版了十餘冊日檢系列暢銷叢書，讀者遍及亞洲各國。本書前身為《 新日檢500題N1︰文字‧語彙‧文法》，出版至今，由於不斷受到考生及教師們的好評，於2015年推出全新修訂版本，延續「大量練習，提升實力」的宗旨，修訂版更精準掌握最關鍵的500題與其延伸，幫助考生在最短時間內全力衝刺。',
'2025-01-01', '2025-01-01', '2016-03-08', 
'https://cdnec.sanmin.com.tw/product_images/626/626023456.jpg', '這本書的封面'),
('新日檢完勝對策N1：漢字•語彙', 3, 3, 1, 6, 580, 3, '「新日檢完勝對策」系列由「JLPT日本語能力試驗」教材權威佐佐木仁子、松本紀子老師共同執筆，為考生規畫兩個月的應考讀書計畫。全套書籍從N5至N1分級共15冊，扎實奠定〈文法〉〈讀解〉〈聽解〉及〈漢字‧語彙〉四大基礎。

本系列因其簡明易懂的特色，引導學習者循序漸進、養成實力，上市以來，一直是日檢類的熱門暢銷書。全新增訂版的《新日検完勝対策N1：漢字‧語彙》為日本アスク出版社所出版的《日本語総まとめ N1漢字［増補改訂版］》及《日本語総まとめ N1語彙［増補改訂版］》的合訂本。考量讀者在學習日文或準備日檢時，漢字、語彙都是必讀基礎項目，因此將兩本合併，方便學習。',
'2025-01-01', '2025-01-01', '2023-11-01', 
'https://cdnec.sanmin.com.tw/product_images/957/957532627.jpg', '這本書的封面'),
('哈利波特01：神秘的魔法石', 4, 4, 1, 8, 420, 20, '十一歲的哈利波特，從小被收養他的姨丈一家當成怪胎，經常得滿屋子躲避表哥達力的追打。雖然無法解釋發生在身邊的各種奇怪現象，但他一直以為自己只是個平凡的男孩。

直到那封信掉落在水蠟樹街四號的踩腳墊上，淡黃色的羊皮紙信封上用綠色墨水書寫著地址，以紫色蠟印彌封。哈利還沒弄清楚那是什麼，就立刻被恐怖的阿姨和姨丈給沒收了。

但寄信的人並沒有就此罷休，陸續寄來了十封、二十封，直到上百封。最後，擁有一頭濃密黑髮和雜亂鬍鬚的巨人海格出現在哈利的面前，直接將信塞給他，並帶來令人震驚的消息：「哈利波特是一名巫師，在霍格華茲魔法與巫術學院擁有一席之地。」哈利顫抖著雙手收下信，這時的他還不知道，他即將踏上全魔法世界最不可思議的冒險旅程！',
'2025-01-01', '2025-01-01', '2020-08-10', 
'https://cdnec.sanmin.com.tw/product_images/957/957333556.jpg', '這本書的封面'),
('哈利波特02：消失的密室', 4, 4, 1, 8, 480, 20, '這個夏天結束前，哈利本來應該能夠順利重返霍格華茲。但人算不如天算，家庭小精靈多比突然造訪，警告他若是回到學校，將面臨生命危險！為了讓哈利徹底「斷念」，多比不但攔截了所有朋友們寄來的信，還用魔法陷害他，害他慘遭禁足。

但哈利始終很清楚，自己渴望回到霍格華茲。他想念那座藏著秘密通道和幽靈的城堡，想念他的魔法課程、由貓頭鷹送達的郵件，以及馳騁天際的魁地奇。彷彿聽到了他內心的呼喚，好友榮恩駕著飛天車，硬是把他從姨丈家給救了出來。',
'2025-01-01', '2025-01-01', '2020-09-14', 
'https://cdnec.sanmin.com.tw/product_images/957/957333590.jpg', '這本書的封面'),
('哈利波特03：阿茲卡班的逃犯', 4, 4, 1, 8, 520, 6, '瑪姬姑姑又要來了！這個名字一出現在德思禮家，就代表著哈利為期一週的惡夢。小時候玩大風吹，她會用手杖猛敲哈利的腳，免得他贏過達力，有一年他不小心踩到了瑪姬養的牛頭犬，只能在樹上躲到三更半夜。瑪姬姑姑跟德思禮家所有人一樣，都以貶低他為樂，直到她侮辱了哈利的父母，被他用魔法變成大氣球……',
'2025-01-01', '2025-01-01', '2020-11-16', 
'https://cdnec.sanmin.com.tw/product_images/957/957333609.jpg', '這本書的封面'),
('哈利波特04：火盃的考驗', 4, 4, 1, 8, 699, 7, '直到成為巫師之後，哈利才明白自己額頭上那個閃電形傷疤的意義。
在他還只有一歲時，「那個人」用邪惡的咒語殘殺了他的父母，但同樣的咒語卻沒有傷到哈利分毫，還反彈到施術者身上。拜這個「奇蹟」所賜，魔法界終於擺脫長久以來的恐怖統治，但這也注定了他終生都得面對黑暗勢力的追殺……',
'2025-01-01', '2025-01-01', '2021-03-02', 
'https://cdnec.sanmin.com.tw/product_images/957/957333639.jpg', '這本書的封面'),
('哈利波特05：鳳凰會的密令', 4, 4, 1, 8, 849, 5, '升上五年級前的暑假，對哈利波特來說一如以往地既痛苦且漫長。黑魔王回來了，但揭露危機的他所得到的獎賞，竟然是受困在小惠因區裡整整一個月！他越想越氣，以至於當催狂魔突然現身襲擊時，他還在忙著跟表哥達力吵架。

情急之下，哈利施展護法咒成功擊退了催狂魔，但他心知這只是危險的開端。黑魔王變得更強大了，為了找到當年重傷他的那個男孩，他將不惜一切代價……',
'2025-01-01', '2025-01-01', '2021-05-17', 
'https://cdnec.sanmin.com.tw/product_images/957/957333692.jpg', '這本書的封面')
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

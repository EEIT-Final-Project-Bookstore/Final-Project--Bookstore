

INSERT INTO dbo.HomepageImages (ImageID, BookID)
SELECT 
    ROW_NUMBER() OVER (ORDER BY BookID) + 100 AS ImageID, -- 確保唯一
    BookID
FROM dbo.Books;


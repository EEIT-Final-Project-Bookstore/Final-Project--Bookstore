export const fetchBookDetail = async (id) => {
    return new Promise((resolve) => {
        setTimeout(() => {
            resolve({
                id,
                title: "Wicked [Movie Tie-In]",
                isbn: "9780062852847",
                author: "Gregory Maguire",
                publisher: "WILLIAM MORROW",
                publishDate: "2024/10/01",
                pages: 480,
                price: 760,
                discount: 66,
                stock: 4,
                description: "這是一本測試書籍的描述。",
                coverImage: null, // 書籍封面圖片
            });
        }, 500); // 模擬延遲
    });
};

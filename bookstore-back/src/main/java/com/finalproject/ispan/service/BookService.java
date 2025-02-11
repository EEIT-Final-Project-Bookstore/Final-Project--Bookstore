package com.finalproject.ispan.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finalproject.ispan.domain.BookBean;
import com.finalproject.ispan.dto.BookDTO;
import com.finalproject.ispan.repository.BookRepository;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private PublisherService publisherService;

    @Autowired
    private LanguageService languageService;

    @Autowired
    private SubcategoryService subcategoryService;

    // 取得所有書籍
    public List<BookDTO> getAllBooks() {
        List<BookBean> books = bookRepository.findAll();
        return books.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // 依 ID 取得單一本書
    public BookDTO getBookById(Integer id) {
        BookBean book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        return convertToDTO(book);
    }

    // 新增書籍
    public BookDTO createBook(BookDTO bookDTO) {
        BookBean book = convertToEntity(bookDTO);
        BookBean savedBook = bookRepository.save(book);
        return convertToDTO(savedBook);
    }

    // 更新書籍
    public BookDTO updateBook(Integer id, BookDTO bookDTO) {
        BookBean book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        // 更新書籍屬性
        book.setBookName(bookDTO.getBookName());
        book.setPrice(bookDTO.getPrice());
        book.setStock(bookDTO.getStock());
        book.setDescription(bookDTO.getDescription());
        book.setShelfTime(bookDTO.getShelfTime());
        book.setLastUpdated(bookDTO.getLastUpdated());
        book.setPublishDate(bookDTO.getPublishDate());
        book.setImageUrl(bookDTO.getImageUrl());
        book.setImageDescription(bookDTO.getImageDescription());

        // 更新關聯對象
        book.setAuthor(authorService.getAuthorById(bookDTO.getAuthorId()));
        book.setPublisher(publisherService.getPublisherById(bookDTO.getPublisherId()));
        book.setLanguage(languageService.getLanguageById(bookDTO.getLanguageId()));
        book.setSubcategory(subcategoryService.getSubcategoryById(bookDTO.getSubcategoryId()));

        BookBean updatedBook = bookRepository.save(book);
        return convertToDTO(updatedBook);
    }

    // 刪除書籍
    public void deleteBook(Integer id) {
        if (!bookRepository.existsById(id)) {
            throw new RuntimeException("Book not found");
        }
        bookRepository.deleteById(id);
    }

    // 將 BookBean 轉換為 BookDTO
    private BookDTO convertToDTO(BookBean book) {
        BookDTO dto = new BookDTO();
        dto.setBookId(book.getBookId());
        dto.setBookName(book.getBookName());
        dto.setAuthorId(book.getAuthor().getAuthorId());
        dto.setPublisherId(book.getPublisher().getPublisherId());
        dto.setLanguageId(book.getLanguage().getLanguageId());
        dto.setSubcategoryId(book.getSubcategory().getSubcategoryId());
        dto.setPrice(book.getPrice());
        dto.setStock(book.getStock());
        dto.setDescription(book.getDescription());
        dto.setShelfTime(book.getShelfTime());
        dto.setLastUpdated(book.getLastUpdated());
        dto.setPublishDate(book.getPublishDate());
        dto.setImageUrl(book.getImageUrl());
        dto.setImageDescription(book.getImageDescription());
        return dto;
    }

    // 將 BookDTO 轉換為 BookBean
    private BookBean convertToEntity(BookDTO bookDTO) {
        BookBean book = new BookBean();
        book.setBookId(bookDTO.getBookId());
        book.setBookName(bookDTO.getBookName());
        book.setPrice(bookDTO.getPrice());
        book.setStock(bookDTO.getStock());
        book.setDescription(bookDTO.getDescription());
        book.setShelfTime(bookDTO.getShelfTime());
        book.setLastUpdated(bookDTO.getLastUpdated());
        book.setPublishDate(bookDTO.getPublishDate());
        book.setImageUrl(bookDTO.getImageUrl());
        book.setImageDescription(bookDTO.getImageDescription());

        // 設定關聯對象
        book.setAuthor(authorService.getAuthorById(bookDTO.getAuthorId()));
        book.setPublisher(publisherService.getPublisherById(bookDTO.getPublisherId()));
        book.setLanguage(languageService.getLanguageById(bookDTO.getLanguageId()));
        book.setSubcategory(subcategoryService.getSubcategoryById(bookDTO.getSubcategoryId()));

        return book;
    }
    
    // 亨
    // 根據 BookID 查詢 BookBean
    public BookBean findBookById(Integer bookId) {
        return bookRepository.findByBookId(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book not found with ID: " + bookId));
    }

 // 透過作者名稱（模糊搜尋）查詢書籍
    public List<BookDTO> findBooksByAuthorName(String authorName) {
        List<BookBean> books = bookRepository.findByAuthor_AuthorNameContainingIgnoreCase(authorName);
        return books.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    // 透過出版社名稱（模糊搜尋）查詢書籍
    public List<BookDTO> findBooksByPublisherName(String publisherName) {
    	System.out.println("Publisher Name: " + publisherName);
    	publisherName = publisherName.trim();
        return bookRepository.findByPublisher_PublisherNameContainingIgnoreCase(publisherName)
                .stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // 透過子分類名稱（模糊搜尋）查詢書籍
    public List<BookDTO> findBooksBySubcategoryName(String subcategoryName) {
    	System.out.println("SubcategoryName: [" + subcategoryName + "]");
    	subcategoryName = subcategoryName.trim();
        
        // 測試查詢結果
        List<BookBean> books = bookRepository.findBySubcategory_SubcategoryNameContainingIgnoreCase(subcategoryName);
        System.out.println("Books found: " + books); // 打印查詢結果

        // 確保將查詢結果轉換為 DTO
        return books.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    
    // 透過類別名稱（模糊搜尋）查詢書籍
    public List<BookDTO> findBooksByCategoryName(String categoryName) {
        List<BookBean> books = bookRepository.findBySubcategory_Category_CategoryNameContainingIgnoreCase(categoryName);
        categoryName = categoryName.trim();
        System.out.println("CategoryName: [" + categoryName + "]");
      
        return books.stream().map(this::convertToDTO).collect(Collectors.toList());
    }
}

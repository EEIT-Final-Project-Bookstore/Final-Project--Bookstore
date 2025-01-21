package bookstore.service;

import bookstore.domain.BookBean;
import bookstore.dto.BookDTO;
import bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
}

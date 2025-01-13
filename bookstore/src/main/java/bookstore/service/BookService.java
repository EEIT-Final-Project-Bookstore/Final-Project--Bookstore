package bookstore.service;

import bookstore.domain.BookBean;
import bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<BookBean> getAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<BookBean> getBookById(Integer id) {
        return bookRepository.findById(id);
    }

    public BookBean createBook(BookBean book) {
        return bookRepository.save(book);
    }

    public BookBean updateBook(Integer id, BookBean updatedBook) {
        return bookRepository.findById(id).map(book -> {
            book.setBookName(updatedBook.getBookName());
            book.setPrice(updatedBook.getPrice());
            book.setStock(updatedBook.getStock());
            book.setDescription(updatedBook.getDescription());
            book.setLastUpdated(updatedBook.getLastUpdated());
            return bookRepository.save(book);
        }).orElseThrow(() -> new RuntimeException("Book not found"));
    }

    public void deleteBook(Integer id) {
        bookRepository.deleteById(id);
    }
}

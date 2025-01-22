package bookstore.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bookstore.domain.AuthorBean;
import bookstore.repository.AuthorRepository;

@Service
public class AuthorService {
    
    @Autowired
    private AuthorRepository authorRepository;

    // 查詢所有作者
    public List<AuthorBean> getAllAuthors() {
        return authorRepository.findAll();
    }

 // 依 ID 查詢作者
    public AuthorBean getAuthorById(Integer id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found"));
    }

    // 新增作者 (手動指定 ID)
    public AuthorBean createAuthor(AuthorBean author) {
        if (authorRepository.existsById(author.getAuthorId())) {
            throw new RuntimeException("Author ID already exists: " + author.getAuthorId());
        }
        return authorRepository.save(author);
    }

    // 修改作者資訊
    public AuthorBean updateAuthor(Integer id, AuthorBean updatedAuthor) {
        return authorRepository.findById(id).map(author -> {
            author.setAuthorName(updatedAuthor.getAuthorName());
            author.setAuthorDescription(updatedAuthor.getAuthorDescription());
            return authorRepository.save(author);
        }).orElseThrow(() -> new RuntimeException("Author not found with ID: " + id));
    }

    // 刪除作者
    public void deleteAuthor(Integer id) {
    	if (!authorRepository.existsById(id)) {
            throw new RuntimeException("Author not found");
        }
        authorRepository.deleteById(id);
    }
}

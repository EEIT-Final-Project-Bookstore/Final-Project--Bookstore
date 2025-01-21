package bookstore1.service;

import bookstore1.domain.LanguageBean;
import bookstore1.repository.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LanguageService {

    @Autowired
    private LanguageRepository languageRepository;

    // 查詢所有語言
    public List<LanguageBean> getAllLanguages() {
        return languageRepository.findAll();
    }

    // 依 ID 查詢語言
    public LanguageBean getLanguageById(Integer id) {
        return languageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Language not found"));
    }

    // 新增語言
    public LanguageBean createLanguage(LanguageBean language) {
        return languageRepository.save(language);
    }

    // 刪除語言
    public void deleteLanguage(Integer id) {
        if (!languageRepository.existsById(id)) {
            throw new RuntimeException("Language not found");
        }
        languageRepository.deleteById(id);
    }
}

package bookstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bookstore.domain.LanguageBean;
import bookstore.service.LanguageService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173") // 確保 CORS
public class LanguageController {

    @Autowired
    private LanguageService languageService;

    @GetMapping("/languages")
    public List<LanguageBean> getAllLanguages() {
        return languageService.getAllLanguages();
    }
}

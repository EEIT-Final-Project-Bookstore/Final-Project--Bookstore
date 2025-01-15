package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.BooksBean;
import com.example.demo.domain.HomepageImagesBean;
import com.example.demo.dto.HomepageImageDTO;
import com.example.demo.repository.HomepageImagesRepository;

import jakarta.transaction.Transactional;
@Transactional
@Service
public class HomepageImagesService {

    @Autowired
    private HomepageImagesRepository homepageImagesRepository;
    @Autowired
    private BooksService booksService;
 // 查詢所有首頁圖片
    public List<HomepageImageDTO> getAllHomepageImages() {
        List<HomepageImagesBean> entities = homepageImagesRepository.findAll();

        return entities.stream().map(entity -> {
            HomepageImageDTO dto = new HomepageImageDTO();
            dto.setId(entity.getId());
            dto.setImageID(entity.getImageID());
            dto.setBookName(entity.getBook().getBookName());
            dto.setDescription(entity.getBook().getDescription());
            dto.setPrice(entity.getBook().getPrice());
            return dto;
        }).collect(Collectors.toList());
    }

    // 根據 BookID 查找單筆首頁圖片
    public HomepageImageDTO getHomepageImageByBookID(Integer bookID) {
        HomepageImagesBean entity = homepageImagesRepository.findFirstByBook_BookId(bookID);

        if (entity == null) {
            throw new RuntimeException("No image found for BookID: " + bookID);
        }

        HomepageImageDTO dto = new HomepageImageDTO();
        dto.setId(entity.getId());
        dto.setImageID(entity.getImageID());
        dto.setBookName(entity.getBook().getBookName());
        dto.setDescription(entity.getBook().getDescription());
        dto.setPrice(entity.getBook().getPrice());
        return dto;
    }
    
    

   
   // 根據 BookID 刪除首頁圖片
    
    public void deleteHomepageImagesByBookId(Integer bookId) {
        homepageImagesRepository.deleteByBook_BookId(bookId);
    }
    
    // 根據 BookID 新增首頁圖片
    public  HomepageImagesBean addHomepageImages(HomepageImagesBean homepageImages) {
    	// 確保必要字段不為 null
    	if(homepageImages.getImageID()==null ) {
			throw new IllegalArgumentException("Image ID cannot be null");
		}
    	if(homepageImages.getBook()==null) {
    		throw new IllegalArgumentException("Book cannot be null");
    	}
    	
    	return homepageImagesRepository.save(homepageImages);
    }
 
    //修改
    public HomepageImagesBean updateHomepageImagesBookId(Integer bookId,HomepageImagesBean updatedData) {
    	 System.out.println("Updating HomepageImagesBean for Book ID: " + bookId);
    	//檢查bookId是否存在
        BooksBean book = booksService.findBookById(bookId);
        System.out.println("Found Book: " + book);
        //查詢與bookId關聯的首頁紀錄
        HomepageImagesBean existingImage = homepageImagesRepository.findFirstByBook_BookId(bookId);
        if(existingImage == null) {
        	throw new IllegalArgumentException("Homepage image not found for book ID:" + bookId);
        }
        System.out.println("Existing HomepageImagesBean: " + existingImage);
        //更新字段
        if(updatedData.getImageID() !=null) {
        	existingImage.setImageID(updatedData.getImageID());
        }
        if(updatedData.getBook()!=null && updatedData.getBook().getBookId() !=null ) {
        	existingImage.setBook(book);//更新書籍關聯
        }
        //保存更新後的數據
        return  homepageImagesRepository.save(existingImage);
    }
    
}

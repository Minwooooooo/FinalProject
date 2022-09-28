package com.example.demo.service;

import com.example.demo.dto.responseDto.CategoryResponseDto;
import com.example.demo.dto.responseDto.ResponseDto;
import com.example.demo.dto.requestDto.CategoryRequestDto;
import com.example.demo.entity.Category;
import com.example.demo.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public ResponseDto<?> SendStories(String category){
        List<Category> categories = getCategories(category);

        CategoryResponseDto responseDto= CategoryResponseDto.builder()
                .categories(categories)
                .build();

        return ResponseDto.success(responseDto);
    }

    @Transactional
    public ResponseDto<?> saveStory(CategoryRequestDto requestDto, HttpServletRequest request){
        Category category = getCategory(requestDto);

        try {
            categoryRepository.save(category);
        }catch (Exception e){
            return ResponseDto.fail("ERR", "스크립트가 저장 안됨");
        }

        return ResponseDto.success("성공적으로 스크립트가 저장되었습니다");
    }



//    Private
    private List<Category> getCategories(String category) {
        Optional<List<Category>> optionalCategoryList= Optional.ofNullable(categoryRepository.findAllByCategory(category));
        List<Category> categories= optionalCategoryList.orElseThrow(
                () -> new IllegalArgumentException("PARSING_ERR")
        );
        return categories;
    }
    private static Category getCategory(CategoryRequestDto requestDto) {
        if (requestDto.getCategory().isEmpty()){
            System.err.println("카테고리 변수가 비어 있음.");
        }
        if (requestDto.getStory().isEmpty()){
            System.err.println("스크립트 변수가 비어 있음.");
        }

        Category category= new Category(requestDto);
        return category;
    }
}

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
        Category category = newCategory(requestDto);

        try {
            categoryRepository.save(category);
        }catch (Exception e){
            return ResponseDto.fail("CANT_SAVE", "Encountering JPA save");
        }

        return ResponseDto.success(category.getId() + ", Save Complete in script");
    }

    public ResponseDto<?> update(Long id, CategoryRequestDto requestDto, HttpServletRequest request) {
        Optional<Category> checkNull = getCategoryFindById(id);

        Category category= null;
        if (checkNull.isPresent()){
            category = getCategoryById(id);
        }else {
            System.err.println("Not Found ID");
        }

        try {
            category.update(requestDto);
            categoryRepository.save(category);
        }catch (Exception e){
            System.err.println("Encountering Update");
        }

        return ResponseDto.success("Updating Complete");
    }


    public ResponseDto<?> delete(Long id) {

        Optional<Category> checkNull = getCategoryFindById(id);
        if (checkNull.isPresent()){
            try {
                categoryRepository.deleteById(id);
            }catch (Exception e){
                return ResponseDto.fail("DELETE_ERR", "JAP deleteById");
            }
        }else{
            System.err.println("Not Found a ID");
        }

        return ResponseDto.success("Deleting Complete");
    }

    private Optional<Category> getCategoryFindById(Long id) {
        var checkNull= categoryRepository.findById(id);
        return checkNull;
    }


    //    Private
    private List<Category> getCategories(String category) {
        Optional<List<Category>> optionalCategoryList= Optional.ofNullable(categoryRepository.findAllByCategory(category));
        List<Category> categories= optionalCategoryList.orElseThrow(
                () -> new IllegalArgumentException("PARSING_ERR")
        );
        return categories;
    }
    private static Category newCategory(CategoryRequestDto requestDto) {
        if (requestDto.getCategory().isEmpty()){
            System.err.println("Null Category value");
        }
        if (requestDto.getStory().isEmpty()){
            System.err.println("Null story value");
        }

        Category category= new Category(requestDto);
        return category;
    }

    private Category getCategoryById(Long id) {
        Optional<Category> categoryOptional= categoryRepository.findById(id);
        Category category = categoryOptional.orElseThrow(
                () -> new IllegalArgumentException("PARSING_ERR")
        );
        return category;
    }
}

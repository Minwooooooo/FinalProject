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
        List<Category> categories = newCategories(category);

        CategoryResponseDto responseDto= CategoryResponseDto.builder()
                .categories(categories)
                .build();

        return ResponseDto.success(responseDto);
    }

    @Transactional
    public ResponseDto<?> saveStory(CategoryRequestDto requestDto, HttpServletRequest request){
        Category category = newCategoryCheckedNull(requestDto);

        try {
            categoryRepository.save(category);
        }catch (Exception e){
            System.err.println(e);
            return ResponseDto.fail("CANT_SAVE", "Encountering JPA save err");
        }

        return ResponseDto.success(category.getId() + " : Save Complete in script");
    }

    public ResponseDto<?> update(Long id, CategoryRequestDto requestDto, HttpServletRequest request) {
        Optional<Category> forNullCheck = new NewOptional().findById(id);
        Category category = getCategoryCheckValidId(id, forNullCheck);

        try {
            category.update(requestDto);
            categoryRepository.save(category);
        }catch (Exception e){
            System.err.println(e);
            return ResponseDto.fail("CANT_UPDATE", "Encountering Update");
        }

        return ResponseDto.success("Updating Complete");
    }

    public ResponseDto<?> delete(Long id) {
        Optional<Category> checkNull = new NewOptional().findById(id);

        if (checkNull.isPresent()){
            try {
                categoryRepository.deleteById(id);
            }catch (Exception e){
                return ResponseDto.fail("JAP_deleteById", e + "");
            }
        }else{
            var MSG= "Not Found a ID";
            System.err.println(MSG);
            return ResponseDto.fail("NULL_ID", MSG);
        }

        return ResponseDto.success("Deleting Complete");
    }


//    Nested class
    class NewOptional{
        private Optional<Category> findById(Long id) {
            return categoryRepository.findById(id);
        }
    }


    //    Private
    private List<Category> newCategories(String category) {
        List<Category> categories= Optional.ofNullable(categoryRepository.findAllByCategory(category)).orElseThrow(
                () -> new IllegalArgumentException("PARSING_ERR")
        );
        return categories;
    }
    private static Category newCategoryCheckedNull(CategoryRequestDto requestDto) {
        if (requestDto.getCategory().isEmpty()){
            System.err.println("Null Category value");
        }
        if (requestDto.getStory().isEmpty()){
            System.err.println("Null story value");
        }

        return new Category(requestDto);
    }

    private Category getCategoryByIdAsOptional(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("PARSING")
        );

        return category;
    }

    private Category getCategoryCheckValidId(Long id, Optional<Category> checkNull) {
        Category category= checkNull.isPresent() ? getCategoryByIdAsOptional(id) : null;

        if (category == null){
            throw new IllegalArgumentException("Not Found ID");
        }

        return category;
    }
}

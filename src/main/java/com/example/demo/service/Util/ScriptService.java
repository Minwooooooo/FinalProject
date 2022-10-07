package com.example.demo.service.Util;

import com.example.demo.dto.CategoryResponseDto;
import com.example.demo.dto.ResponseDto;
import com.example.demo.dto.httpDto.requestDto.CategoryRequestDto;
import com.example.demo.dto.httpDto.responseDto.ScriptResponseDto;
import com.example.demo.dto.httpDto.responseDto.ScriptsResponseDto;
import com.example.demo.entity.util.Script;
import com.example.demo.repository.util.ScriptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScriptService {

    private final ScriptRepository scriptRepository;

    public ResponseDto<?> scriptsList(int type){
        /*
        최초 조회 (type): 해당 타입의 모든 카테고리 리스트 및 스크립트 리스트
         */
        List<Script> scriptList=scriptRepository.findAllByType(type);
        if(scriptList.isEmpty()){
            return ResponseDto.fail("Null_SCRIPT",type+"인 script가 존재하지 않습니다.");
        }

        List<String> categoryList=new ArrayList<>();
        List<ScriptResponseDto> scriptsList = new ArrayList<>();

        for (int i = 0; i < scriptList.size(); i++) {
            List<String> temp_categories=categories(scriptList.get(i).getCategory());

            ScriptResponseDto scriptResponseDto = ScriptResponseDto.builder()
                    .categories(temp_categories)
                    .story(scriptList.get(i).getStory())
                    .build();
            scriptsList.add(scriptResponseDto);

            for (int j = 0; j < temp_categories.size(); j++) {
                if(!categoryList.contains(temp_categories.get(j)))
                categoryList.add(temp_categories.get(j));
            }
        }
        ScriptsResponseDto scriptsResponseDto= ScriptsResponseDto.builder()
                .categories(categoryList)
                .scriptResponseDto(scriptsList)
                .build();
        return ResponseDto.success(scriptsResponseDto);
    }

    @Transactional
    public ResponseDto<?> saveStory(CategoryRequestDto requestDto, HttpServletRequest request){
        Script script = newCategoryCheckedNull(requestDto);

        try {
            scriptRepository.save(script);
        }catch (Exception e){
            System.err.println(e);
            return ResponseDto.fail("CANT_SAVE", "Encountering JPA save err");
        }

        return ResponseDto.success(script.getId() + " : Save Complete in script");
    }

    public ResponseDto<?> update(Long id, CategoryRequestDto requestDto, HttpServletRequest request) {
        Optional<Script> forNullCheck = new NewOptional().findById(id);
        Script script = getCategoryCheckValidId(id, forNullCheck);

        try {
            script.update(requestDto);
            scriptRepository.save(script);
        }catch (Exception e){
            System.err.println(e);
            return ResponseDto.fail("CANT_UPDATE", "Encountering Update");
        }

        return ResponseDto.success("Updating Complete");
    }

    public ResponseDto<?> delete(Long id) {
        Optional<Script> checkNull = new NewOptional().findById(id);

        if (checkNull.isPresent()){
            try {
                scriptRepository.deleteById(id);
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

    // 카테고리 리스트화
    public List<String> categories(String category){
        String[] allCategories=category.split(",");
        return new ArrayList<>(Arrays.asList(allCategories));
    }







//    Nested class
    class NewOptional{
        private Optional<Script> findById(Long id) {
            return scriptRepository.findById(id);
        }
    }


    //    Private
    private List<Script> newCategories(String category) {
        List<Script> categories= Optional.ofNullable(scriptRepository.findAllByCategory(category)).orElseThrow(
                () -> new IllegalArgumentException("PARSING_ERR")
        );
        return categories;
    }
    private static Script newCategoryCheckedNull(CategoryRequestDto requestDto) {
        if (requestDto.getCategory().isEmpty()){
            System.err.println("Null Category value");
        }
        if (requestDto.getStory().isEmpty()){
            System.err.println("Null story value");
        }

        return new Script(requestDto);
    }

    private Script getCategoryByIdAsOptional(Long id) {
        Script script = scriptRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("PARSING")
        );

        return script;
    }

    private Script getCategoryCheckValidId(Long id, Optional<Script> checkNull) {
        Script script = checkNull.isPresent() ? getCategoryByIdAsOptional(id) : null;

        if (script == null){
            throw new IllegalArgumentException("Not Found ID");
        }

        return script;
    }
}

package com.example.demo.controller.util;

import com.example.demo.dto.ResponseDto;
import com.example.demo.service.Util.ScriptService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ScriptController {
    private final ScriptService scriptService;

    @GetMapping(value="/chat/script/{type}")
    public ResponseDto<?> allScriptsList(@PathVariable int type){
        return scriptService.scriptsList(type);
    }
}

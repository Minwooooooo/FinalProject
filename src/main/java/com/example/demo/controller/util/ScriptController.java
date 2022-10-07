package com.example.demo.controller.util;

import com.example.demo.dto.ResponseDto;
import com.example.demo.dummy.ScriptDummy;
import com.example.demo.service.Util.ScriptService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ScriptController {
    private final ScriptService scriptService;
    private final ScriptDummy scriptDummy;

    @GetMapping(value={"/chat/script/{type}"
            ,"/chat/script/{type}/{category1}"
            ,"/chat/script/{type}/{category1}/{category2}"
            ,"/chat/script/{type}/{category1}/{category2}/{category3}"})
    public ResponseDto<?> allScriptsList(@PathVariable int type,
                                         @PathVariable Optional<String> category1,
                                         @PathVariable Optional<String> category2,
                                         @PathVariable Optional<String> category3) throws UnsupportedEncodingException {
        return scriptService.scriptsList(type,category1,category2,category3);
    }

    @GetMapping(value = "/admin/script/{key}")
    public void scriptRunner(@PathVariable String key){
        scriptDummy.setScript(key);
    }
}

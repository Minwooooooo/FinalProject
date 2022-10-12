package com.example.demo.dummy;


import com.example.demo.entity.util.Script;

import com.example.demo.repository.util.ScriptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class AppRunner implements ApplicationRunner {
    private final ScriptRepository scriptRepository;



    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("AppRunner 실행");
    }
}


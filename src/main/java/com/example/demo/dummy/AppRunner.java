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

        Script script;
        script= Script.builder()
                .type(2)
                .category("#미드,#썸,#10대,#하이틴")
                .story("- **A: hi.**\n" +
                        "    - 안녕.\n" +
                        "- **B: bye.**\n" +
                        "    - 안녕.\n")
                .build();
        scriptRepository.save(script);
        script=Script.builder()
                .type(2)
                .category("#예약,#병원,#전화,#진료예약")
                .story("- **A: Dr. Willam’s Office.**\n" +
                        "    - 닥터 윌리엄 병원입니다.\n" +
                        "- **B: Yes.**\n" +
                        "    - 네.\n")
                .build();
        scriptRepository.save(script);
        script=Script.builder()
                .type(2)
                .category("#미드,#연인,#연애")
                .story("- **A: You know,**\n" +
                        "    - 있잖아,\n" +
                        "- **B: Of course!**\n" +
                        "    - 당연하지!?\n")
                .build();
        scriptRepository.save(script);
    }
}


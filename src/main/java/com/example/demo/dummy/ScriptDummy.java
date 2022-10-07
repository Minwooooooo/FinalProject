package com.example.demo.dummy;

import com.example.demo.entity.util.Script;
import com.example.demo.repository.util.ScriptRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ScriptDummy {
    private final ScriptRepository scriptRepository;
    @Value("${script.key}")String Secret;

    public void setScript(String key) {
        System.out.println(key);
        if(key.equals(Secret)){
            setScript2();
            setScript3();
        }
    }

    @Transactional
    public void setScript2() {
        Script script;
        script = Script.builder()
                .type(2)
                .category("#미드 #썸 #10대 #하이틴")
                .story("- **A: Why would I be trying to make you jealous? Besides, you have a girlfriend.**\n" +
                        "    - 내가 뭐 하러 질투 유발을 해? 게다가, 넌 여자친구도 있잖아.\n" +
                        "- **B: Holly? She’s not my girlfriend. We have a movie coming out, so we’ve been hanging out as a publicity thing.**\n" +
                        "    - Holly 말하는 거야? 걔는 내 여자친구가 아니야. 같이 찍은 영화가 곧 개봉하니까 홍보 목적으로 같이 다닌거야.\n" +
                        "- **A: Why didn’t you tell me that before?**\n" +
                        "    - 왜 그걸 이제야 말해?\n" +
                        "- **B: Uh.. Well.. I… I think, I…**\n" +
                        "    - 음… 글쎄… 난… 내 생각에는,,,내가…\n" +
                        "- **A: Wait a minute! I know why. Because you were trying to make me jealous.**\n" +
                        "    - 잠깐만! 이유를 알 거 같아. 너야말로 내가 질투하길 원해서 그랬던 거지.\n" +
                        "- **B: Was not.**\n" +
                        "    - 아니거든.\n" +
                        "- **A: Was too. Admit it, Jake. You like me.**\n" +
                        "    - 맞거든. 그냥 인정해, 제이크. 너 나 좋아하잖아.\n" +
                        "- **B: No, you like me, just say it!**\n" +
                        "    - 아니, 너야말로 날 좋아하잖아, 그냥 말해!\n" +
                        "- **A: No, you say it!**\n" +
                        "    - 아니, 네가 말해!")
                .origin("출처: 한나 몬타나(Hannah Motana)")
                .build();
        scriptRepository.save(script);

        script = Script.builder()
                .type(2)
                .category("#예약 #병원 #전화 #진료예약")
                .story("- **A: Dr. Willam’s Office.**\n" +
                        "    - 닥터 윌리엄 병원입니다.\n" +
                        "- **B: Yes, I’d like to schedule an appointment with Dr. William.**\n" +
                        "    - 네. 윌리엄 선생님께 진료를 예약하고 싶습니다.\n" +
                        "- **A: Is this your first visit?**\n" +
                        "    - 처음 방문하시는 건가요?\n" +
                        "- **B: Yes. I’m a new patient.**\n" +
                        "    - 네. 초진입니다.\n" +
                        "- **A: Could I have your name, please?**\n" +
                        "    - 성함이 어떻게 되시나요?\n" +
                        "- **B: Yes. My name is ____. Do you have any openings today?**\n" +
                        "    - ____입니다. 오늘 예약 가능한 시간이 있나요?\n" +
                        "- **A: Dr. William can see you at 2 o’clock today.**\n" +
                        "    - 오늘 2시에 윌리엄 선생님 예약이 가능합니다.\n" +
                        "- **B: Sounds great!**")
                .build();
        scriptRepository.save(script);

        script = Script.builder()
                .type(2)
                .category("#자기소개 #직장인 #소개팅")
                .story("- **A: I have heard that you’re working in the tourism field. Can I ask which company you’re working for?**\n" +
                        "    - 관광업게에서 일하신다고 들었어요. 어느 회사에서 근무 중이신지 여쭤봐도 될까요?\n" +
                        "- **B: Ah, yes. I’m currently working as a manager at Eng-travel. My major is travel services.**\n" +
                        "    - 아, 네. 저는 현재 Eng-travel에서 매니저로 일하고 있습니다. 제 전공이 여행 서비스라서요.\n" +
                        "- **A: That’s really amazing. I’m a big fan of traveling.**\n" +
                        "    - 정말 멋지네요. 제가 여행하는 걸 정말 좋아하거든요.")
                .build();
        scriptRepository.save(script);

        script = Script.builder()
                .type(2)
                .category("#미드 #라이벌 #변호사 #직장 #법정물")
                .story("- **A: You just want blood.**\n" +
                        "    - 넌 그냥 피를 보고 싶은 거잖아.\n" +
                        "- **B: It’s already there.**\n" +
                        "    - 그건 이미 있는 걸.\n" +
                        "- **A: Because of you.**\n" +
                        "    - 너 때문이잖아.\n" +
                        "- **B: Nonetheless, I smell it. Allison Holt, Robert Zane, both came after you. And now, you have 45 cases you can’t afford. So, pay me 15 million dollars. Fight Monica and lose. Fight Monica and win.**\n" +
                        "    - 그렇긴 하지만, 냄새가 나. Allison Holt, Robert Zane, 둘 다 널 쫓았지. 그리고 지금, 넌 네가 감당할 수 없는 45건의 사건이 생겼네. 그러니까, 나한테 1500만 달러를 주든가. Monica랑 싸워서 지든가. Monica랑 싸워서 이기든가.\n" +
                        "- **A: Doesn’t matter.**\n" +
                        "    - 상관없어.\n" +
                        "- **B: Any way you slice it, I’m gonna bled you dry.**\n" +
                        "    - 네가 어떻게 하던, 난 널 피 말리게 할 거야.")
                .origin("출처: 슈츠(Suits) 시즌 2")
                .build();
        scriptRepository.save(script);


        script = Script.builder()
                .type(2)
                .category("#영화 #친구 #인사 #양해구하기")
                .story("- **A: Excuse me.  Do you mind? Everywhere else is full.**\n" +
                        "    - 실례합니다. 괜찮을까? 다른 곳은 자리가 꽉 차서.\n" +
                        "- **B: Not at all.**\n" +
                        "    - 괜찮아.\n" +
                        "- **A: I’m Ron, by the way. Ron weasley.**\n" +
                        "    - 그나저나, 난 Ron이야. Ron weasley.\n" +
                        "- **B: I’m Harry. Harry Potter.**\n" +
                        "    - 난 Harry야. Harry Potter.\n" +
                        "- **A: So… So it’s true! I mean… Do you really have the, the…**\n" +
                        "    - 그러면… 그러면 진짜구나! 내 말은… 정말 그, 그게 있는 거야..?\n" +
                        "- **B: The what?**\n" +
                        "    - 뭐가 있냐는 거야?\n" +
                        "- **A: Scar.**\n" +
                        "    - 흉터 말이야.\n" +
                        "- **B: Oh.**\n" +
                        "    - 아.\n" +
                        "- **A: Wicked!**\n" +
                        "    - 대박이다!")
                .origin("출처: 해리포터와 마법사의 돌 (Harry Potter And The Sorcerer’s Stone)")
                .build();
        scriptRepository.save(script);


        script = Script.builder()
                .type(2)
                .category("#영화 #가족 #가벼운대화")
                .story("- **A: And What are your faults? I mean, little weaknesses.**\n" +
                        "    - 네 결함은 뭐야? 작은 약점들 말이야.\n" +
                        "- **B: Oh! Uh.. I.. Well, I’m very insecure.**\n" +
                        "    - 오! 어… 저는… 글쎄요, 저는 자신감이 많이 부족해요.\n" +
                        "- **A: Sweet.**\n" +
                        "    - 좋네.\n" +
                        "- **B: Okay. I, um… I have a very bad temper sometimes.**\n" +
                        "    - 그리고, 저는, 음… 저는 가끔 성질이 아주 나빠요.\n" +
                        "- **A: Crucial. How else are you gonna get a fella to do what you want?**\n" +
                        "    - 아주 중요하지. 그렇지 않으면 어떻게 원하는 걸 해주는 남자를 찾겠어.\n" +
                        "- **B: Oh, and of course, I have um… I have a weakness for your son.**\n" +
                        "    - 아, 그리고 물론, 저는… 어머니 아들에 꼼짝을 못 하죠.\n" +
                        "- **A: So do I. But, best no to tell him. Don’t want him getting cocky.**\n" +
                        "    - 나도 그래. 그러나 걔한테는 말을 안 하는 게 좋을 거야. 걔 거만해지는 거 싫거든.")
                .origin("출처: 어바웃 타임(About Time)")
                .build();
        scriptRepository.save(script);


        script = Script.builder()
                .type(2)
                .category("#일상 #가벼운대화 #레스토랑에서")
                .story("- **A: I like the atmosphere here.**\n" +
                        "    - 여기 분위기 좋네요.\n" +
                        "- **B: And the food is delicious!**\n" +
                        "    - 음식도 맛있어요!\n" +
                        "- **A: It’s delicious, but the portions are small.**\n" +
                        "    - 맛은 있는데 양이 적어요.\n" +
                        "- **B: It’s on me.**\n" +
                        "    - 제가 살게요.\n" +
                        "- **A: Are you sure? Let’s split the bill.**\n" +
                        "    - 정말요? 나눠서 계산해요.")
                .build();
        scriptRepository.save(script);


        script = Script.builder()
                .type(2)
                .category("#미드 #난처한상황 #부탁")
                .story("- **A: I really want to my license. I’ve been practicing a lot, I swear.**\n" +
                        "    - 저는 면허가 정말 따고 싶어요. 연습도 많이 했어요. 맹세해요.\n" +
                        "- **B: You gonna drink and drive?**\n" +
                        "    - 술 마시고 운전할 거예요?\n" +
                        "- **A: No, sir.**\n" +
                        "    - 아니요.\n" +
                        "- **B: You gonna text and drive?**\n" +
                        "    - 문자하면서 운전할 거예요?\n" +
                        "- **A: No, sir.**\n" +
                        "    - 아니요.\n" +
                        "- **B: Alright. Let’s start this from the beginning.**\n" +
                        "    - 그래요. 처음부터 다시 시작해보죠.\n" +
                        "- **A: Could you smile, first? I’ll be less nervous if you smile.**\n" +
                        "    - 일단 웃어주실 수 있나요? 웃고 계시면 덜 긴장될 것 같아요.\n" +
                        "- **A: Okay, that didn’t help.**\n" +
                        "    - 음, 별로 도움이 안되네요.\n" +
                        "- **B: just go.**\n" +
                        "    - 그냥 시작하세요.")
                .origin("출처: 모던 패밀리(Modern Family)")
                .build();
        scriptRepository.save(script);


        script = Script.builder()
                .type(2)
                .category("#미드 #연인 #연애")
                .story("- **A: You know, when we move in together, can I get a gumball machine?**\n" +
                        "    - 있잖아, 우리 같이 살게 되면, 사탕 뽑기 기계 사도 돼?\n" +
                        "- **B: Of course! Joey wouldn’t let you have one?**\n" +
                        "    - 당연하지! Joey는 못 사게 했었어?\n" +
                        "- **A: No, When it comes to sweets, he’s surprisingly strict.**\n" +
                        "    - 응, 걔가 단 거에 있어선 의외로 엄격하거든.\n" +
                        "- **B: Hey, have you figured out a way to tell him you’re moving out?**\n" +
                        "    - 그, 걔한테 나간다고 어떻게 말할지 생각해 봤어?\n" +
                        "- **A: No. no, I keep trying, you know? And I can get out, “Joey, I have to…” But then I lose my nerve and I always finish with “… go to the bathroom.”**\n" +
                        "    - 아니, 아니, 계속 시도는 하는데, 알지? “Joey 나…” 까지는 말 할 수 있어. 근데 그 다음엔 주눅이 들어서, 맨날 “화장실 가야 돼.” 라고 말을 끝내")
                .origin("출처: 프렌즈(Friends)")
                .build();
        scriptRepository.save(script);


        script = Script.builder()
                .type(2)
                .category("#일상 #부부 #티격태격")
                .story("- **A: I’d like to put my car on the market.**\n" +
                        "    - 내 차를 시장에 내놓으려고 해.\n" +
                        "- **B: Are you sure? What’s the deal?**\n" +
                        "    - 정말이야? 대체 무슨 일인데?\n" +
                        "- **A: We don’t need two cars anymore.**\n" +
                        "    - 더 이상 자동차가 두 대나 필요 없잖아.\n" +
                        "- **B: What if you have the car, and I have to go shopping?**\n" +
                        "    - 당신이 차를 쓰고 있는데 내가 쇼핑 가야 되면 어떡해?\n" +
                        "- **A: We need to cut back on shopping.**\n" +
                        "    - 우린 쇼핑을 좀 줄여야 해.\n" +
                        "- **B: Honey, I think you should just calm down a little bit.**\n" +
                        "    - 자기야, 당신 잠시 진정 좀 해야 할 것 같아.")
                .build();
        scriptRepository.save(script);


        script = Script.builder()
                .type(2)
                .category("#미드 #화해")
                .story("- **A: Now will you tell me what you’re still upset about?**\n" +
                        "    - 이제 왜 아직도 화나 있는 건지 말해줄래?\n" +
                        "- **B: I’d rather not.**\n" +
                        "    - 싫어.\n" +
                        "- **A: You’re crying about me?**\n" +
                        "    - 혹시 나 때문에 우는 거야?\n" +
                        "- **B: I wasn’t crying about you.**\n" +
                        "    - 너 때문에 운 거 아니야.\n" +
                        "- **A: Oh.**\n" +
                        "    - 아.")
                .origin("출처: 글리(Glee)")
                .build();
        scriptRepository.save(script);


        script = Script.builder()
                .type(2)
                .category("#미드 #연애 #연인 #싸움")
                .story("- **A: But everything I’ve said about how I feel is the truth.**\n" +
                        "    - 하지만 내 감정에 대해 말한 부분은 전부 사실이야.\n" +
                        "- **B: No, that’s not possible. That doesn’t make sense. You couldn’t say these things about me if you loved me.**\n" +
                        "    - 아니, 그건, 그럴 리 없어. 그건 말이 안 돼. 네가 날 사랑한다면 나에 대해 이런 말들을 할 순 없잖아.\n" +
                        "- **B: I invited you into my home, Dan, and my world, and then you just humiliate me.**\n" +
                        "    - Dan, 난 널 우리 집에 초대했고, 내 세상에 초대했는데, 넌 나에게 창피를 줬어\n" +
                        "- **A: That is the issue. It’s “my world.”**\n" +
                        "    - 그게 문제야. “내 세상”이라고 하는거.\n" +
                        "- **B: Oh, come on! It’s a figure of speech.**\n" +
                        "    - 아, 제발! 말이 그렇다는 거잖아.")
                .origin("출처: 가십걸(Gossip Girl)")
                .build();
        scriptRepository.save(script);


        script = Script.builder()
                .type(2)
                .category("#애니메이션 #병원 #가벼운대화")
                .story("- **A: That’s funny.**\n" +
                        "    - 웃기네요.\n" +
                        "- **B: So, you got any plans this weekend?**\n" +
                        "    - 그래서, 주말에 뭐 계획 있으세요?\n" +
                        "- **A: Well, I have a family. so I don’t really make plans.**\n" +
                        "    - 전 가족이 있어서, 계획 같은거 잘 안 세워요.\n" +
                        "- **B: Yeah, I was gonna go to my timeshare in the mountains.**\n" +
                        "    - 그러시군요, 전 산속에 있는 공유 별장에 가려 했었어요.\n" +
                        "- **B: Invited my new hygienist. But she can’t come. And she quit.**\n" +
                        "    - 새로 온 치위생사도 초대했어요. 근데 못 온대요. 그리고 일도 관뒀어요.")
                .origin("출처: 밥스 버거스(Bob’s Burgers)")
                .build();
        scriptRepository.save(script);


        script = Script.builder()
                .type(2)
                .category("#일상 #부탁 #비행기 #여행")
                .story("- **A: Can you put this in the overhead bin?**\n" +
                        "    - 이걸 짐칸에 좀 넣어주시겠어요?\n" +
                        "- **B: All right.**\n" +
                        "    - 알겠습니다.\n" +
                        "- **A: Excuse me, but I feel airsick. Do you have anything to help?**\n" +
                        "    - 실례합니다. 제가 멀미가 나서요. 도와주실 수 있으신지요?\n" +
                        "- **B: Yes. I will bring some medicine over to your seat.**\n" +
                        "    - 네. 자리로 약을 좀 가져다드릴게요.\n" +
                        "- **A: Thank you.**\n" +
                        "    - 감사합니다.\n" +
                        "- **B: Feel free to ask any time.**\n" +
                        "    - 언제든지 말씀 해 주세요.")
                .build();
        scriptRepository.save(script);

        script = Script.builder()
                .type(2)
                .category("#영화 #직장 #상사 #회사")
                .story("- **A: She’s on her way. Tell everyone!**\n" +
                        "    - 그녀가 오고 있어요! 모두에게 알려요!\n" +
                        "- **B: She’s not supposed to be here until 9:00.**\n" +
                        "    - 9시 전에 도착하면 안 되는 거잖아.\n" +
                        "- **A: Her driver just text messaged, and her facialist ruptured a disk.**\n" +
                        "    - 방금 운전기사한테 문자가 왔는데, 피부관리사 디스크가 파열됐다나 봐요.\n" +
                        "- **B: God, these people!**\n" +
                        "    - 진짜, 이 사람들 왜 이럴까!\n" +
                        "- **B: Who’s that?**\n" +
                        "    - 누구야?\n" +
                        "- **A: That I can’t even talk about.**\n" +
                        "    - 말도 하기 싫어요.\n" +
                        "- **B: All right, everyone! Grid your loins!**\n" +
                        "    - 자, 모두들! 단단히 각오하라고!")
                .origin("출처: 악마는 프라다를 입는다 (The Devil Wears Prada)")
                .build();
        scriptRepository.save(script);

        script = Script.builder()
                .type(2)
                .category("#영화 #10대 #하이틴 #썸")
                .story("- **A: So, why ‘d you let him get to you?**\n" +
                        "    - 그래서, 왜 걔가 그러게 내버려 둔 거야?\n" +
                        "- **B: Who?**\n" +
                        "    - 누구?\n" +
                        "- **A: Joey.**\n" +
                        "    - Joey 말이야.\n" +
                        "- **B: I hate him.**\n" +
                        "    - 난 걔가 너무 싫어.\n" +
                        "- **A: Well, you’ve chosen the perfect revenge, mainlining tequila.**\n" +
                        "    - 뭐, 넌 완벽한 복수를 선택했네, 테킬라 들이부으면서.\n" +
                        "- **B: Well, you know what they say.**\n" +
                        "    - 왜, 그런 말 있잖아.\n" +
                        "- **A: Nope, what do they say?**\n" +
                        "    - 몰라, 무슨 말인데?\n" +
                        "- **A: No, no, no, no! Kat, come on, wake up, look at me! Listen to me, Kat! Open your eyes!**\n" +
                        "    - 아니, 안돼, 안돼, 안돼! Kat, 왜 이래, 일어나, 나 좀 봐봐! 내 말 들어, Kat! 눈을 떠!\n" +
                        "- **b: Hey. Your eyes have a little green in them.**\n" +
                        "    - 야. 네 눈동자는 약간 초록빛 띠네.")
                .origin("출처: 내가 널 사랑할 수 없는 10가지 이유(10 Things I Hate About You )")
                .build();
        scriptRepository.save(script);
        script = Script.builder()
                .type(2)
                .category("#영화 #썸 #짝사랑")
                .story("- **A: Do you remember me?**\n" +
                        "    - 나 기억나요?\n" +
                        "- **B: Yeah, sure. Mr. Underwear, was it?**\n" +
                        "    - 네, 그럼요. ‘Underwear’씨였나요?\n" +
                        "- **A: Oh yeah…**\n" +
                        "    - 아 네…\n" +
                        "- **B: How could I forget?**\n" +
                        "    - 제가 어떻게 그걸 잊겠어요?\n" +
                        "- **A: I wanted to clear that up with you, ‘cause I’m really sorry about that.**\n" +
                        "    - 그거에 대해서 당신한테 설명하고 싶었어요, 정말 죄송해서요.\n" +
                        "- **A: That was really stupid thing to do to call the Ferris wheel to talk to somebody. But I had to be next to you. I was being drawn to you.**\n" +
                        "    - 누구랑 얘기하려고 관람차를 멈춘 건 정말 멍청한 짓이었어요. 하지만 당신 곁에 있었어야 했어요. 당신에게 끌리고 있었어요.\n" +
                        "- **B: Oh, geez, what a line. You use that on all the girls.**\n" +
                        "    - 와, 멘트 한번 좋네요. 그거 모든 여자들한테 써먹어요?")
                .origin("출처: 노트북(The Notebook)")
                .build();
        scriptRepository.save(script);

        script = Script.builder()
                .type(2)
                .category("#영화 #썸 #짝사랑 #데이트")
                .story("- **A: What are you doing tonight?**\n" +
                        "    - 오늘 밤에 뭐 해요?\n" +
                        "- **B: What?**\n" +
                        "    - 네?\n" +
                        "- **A: Or tomorrow night or this weekend? Whatever.**\n" +
                        "    - 아니면 내일 저녁이나 이번 주말에는요? 상관없어요.\n" +
                        "- **B: Why?**\n" +
                        "    - 왜요?\n" +
                        "- **A: Why? Our date.**\n" +
                        "    - 왜라뇨? 우리 데이트요.\n" +
                        "- **B: What date?**\n" +
                        "    - 무슨 데이트요?\n" +
                        "- **A: The date that you agreed to?**\n" +
                        "    - 그쪽이 하겠다고 한 그 데이트요?\n" +
                        "- **B: No.**\n" +
                        "    - 그런 적 없어요.\n" +
                        "- **A: Yes, you did. You promised and you swore it.**\n" +
                        "    - 아뇨, 동의했었잖아요. 당신이 약속하고 맹세했었어요.\n" +
                        "- **B: Well, I guess I changed my mind.**\n" +
                        "    - 글쎄요, 제 생각이 바뀌었나 보죠.")
                .origin("출처: 노트북(The Notebook)")
                .build();
        scriptRepository.save(script);

        script = Script.builder()
                .type(2)
                .category("#영화 #가족")
                .story("- **A: Then you and I are like, like sisters!**\n" +
                        "    - 그러면 너와 나는, 자매 같은 거네!\n" +
                        "- **B: Sisters? Hallie, we’re like twins!**\n" +
                        "    - 자매? Hallie, 우린 쌍둥이야!\n" +
                        "- **A: I just don’t know what to say.**\n" +
                        "    - 무슨 말을 해야 할 지 모르겠어.\n" +
                        "- **B: What’s that you’re holding?**\n" +
                        "    - 뭘 들고 있는 거야?\n" +
                        "- **A: My locket. I got it when I was born. It has an H on it.**\n" +
                        "    - 로켓 목걸이, 내가 태어났을 때 받은거야. H가 새겨져 있어.\n" +
                        "- **B: I got mine when I was born, too, except, mine has an A on it.**\n" +
                        "    - 나도 태어날 때 그거 받았어, 내 거에는 A가 새겨져 있지만.\n" +
                        "- **A: Now I’ve got goosebumps.**\n" +
                        "    - 소름 돋았어.")
                .origin("출처: 페어런트 트랩(The Parent Trap)")
                .build();
        scriptRepository.save(script);

        script = Script.builder()
                .type(2)
                .category("#영화 #고백 #썸 #연인 #10대 #하이틴")
                .story("- **A: Is it for me?**\n" +
                        "    - 나 주려고 산 거야?\n" +
                        "- **B: Yeah, I thought you could use it, you know, when you start your band.**\n" +
                        "    - 응, 너 밴드 시작할 때 쓰면 좋을 것 같다고 생각해서.\n" +
                        "- **B: Besides, I had some extra cash, you know. Some asshole paid me to take out this really great girl.**\n" +
                        "    - 게다가, 남는 돈이 좀 있었어. 어떤 짜증 나는 놈이 엄청 멋진 여자 애랑 데이트하는 조건으로 돈을 줬거든.\n" +
                        "- **A: Is that right?**\n" +
                        "    - 그래?\n" +
                        "- **B: Yeah, but I screwed up. I, um… I fell for her.**\n" +
                        "    - 응, 근데 망했어, 내가… 진짜 개한테 반해버렸거든.\n" +
                        "- **A: Really?**\n" +
                        "    - 그래?\n" +
                        "- **B: It’s not everyday you find a girl who flash someone to get you out of detention.**\n" +
                        "    - 학교에 남는 벌 빼주겠다고 티셔츠를 들어 올려 보이는 여자애를 찾는 건 쉽지 않으니까.\n" +
                        "- **A: Oh god.**\n" +
                        "    - 세상에.")
                .origin("출처: 내가 널 사랑할 수 없는 10가지 이유(10 Things I Hate About You )")
                .build();
        scriptRepository.save(script);

        script = Script.builder()
                .type(2)
                .category("#영화 #SF")
                .story("- **A: My boy.**\n" +
                        "    - 얘야.\n" +
                        "- **B: Duncan. can I trust you with something?**\n" +
                        "    - Duncan, 뭐 하나 믿고 말씀드려도 될까요?\n" +
                        "- **A: Yes, always. You know that.**\n" +
                        "    - 그럼, 언제든지. 알잖아.\n" +
                        "- **B: I’ve been having dreams about a girl on Arrakis. I don’t know what it means.**\n" +
                        "    - 요즘 Arrakis에 있는 한 소녀에 대한 꿈을 계속 꿔요. 무슨 뜻인지 모르겠어요.\n" +
                        "- **A: Dreams make good stories, but everything important happens when we’re awake.**\n" +
                        "    - 꿈은 좋은 이야깃거리가 되지만, 중요한 일들은 전부 우리가 깨어있을 때 일어난단다.\n" +
                        "- **A: Look at you. Put on some muscle?**\n" +
                        "    - 얘봐라. 근육 좀 붙었냐?\n" +
                        "- **B: I did?**\n" +
                        "    - 그랬나요?\n" +
                        "- **A: No.**\n" +
                        "    - 아니.")
                .origin("출처: 듄(Dune)")
                .build();
        scriptRepository.save(script);

        script = Script.builder()
                .type(2)
                .category("#영화 #10대 #하이틴")
                .story("- **A: Hey**\n" +
                        "    - 안녕\n" +
                        "- **B: Oh, no. Didn’t anybody tell you? You were supposed to wear a costume.**\n" +
                        "    - 오, 이런. 아무도 얘기 안 해줬어? 분장을 하고 왔어야지.\n" +
                        "- **A: Oh, shut up. I need to talk to you. You know that girl, Candy?**\n" +
                        "    - 조용히 해. 너랑 할 말이 있어. 저 여자애, Candy 알지?\n" +
                        "- **B: Yeah, she’s cool. I invited her tonight.**\n" +
                        "    - 응, 괜찮은 애야. 오늘 밤에 오라고 내가 초대했어.\n" +
                        "- **A: Well, be careful because she has a huge crush on you.**\n" +
                        "    - 글쎄, 조심해, 너한테 홀딱 빠졌거든.\n" +
                        "- **B: Really? How do you know?**\n" +
                        "    - 정말? 네가 어떻게 알아?\n" +
                        "- **A: Because she told me. She tells everybody.**\n" +
                        "    - 나한테 말해줬으니까. 모두한테 말하고 다녀.\n" +
                        "- **B: It’s kind of cute, actually. She’s like a little girl.**\n" +
                        "    - 사실 좀 귀여워. 어린애 같아.\n" +
                        "- **A: She, like, writes all over her notebook, “Mrs. Aaron Samuels.” And she made this T-shirt that says “I heart Aaron” and she wears it under all her clothes.**\n" +
                        "    - 자기 공책에 “Aaron Samuels 부인”이라고 잔뜩 써놓고, “나는 Aaron을 좋아해”라고 적힌 티셔츠를 늘 안에 입고 다녀.\n" +
                        "- **B: Oh, come on.**\n" +
                        "    - 에이, 설마.\n" +
                        "- **A: Well, who can blame her?**\n" +
                        "    - 뭐, 누가 걔를 탓하겠어?\n" +
                        "- **B: I mean, you’re gorgeous.**\n" +
                        "    - 네가 이렇게 멋진걸.")
                .origin("출처: 퀸카로 살아남는 법(Mean Girls)")
                .build();
        scriptRepository.save(script);
    }

    @Transactional
    public void setScript3() {
        Script script;
        script = Script.builder()
                .type(3)
                .category("#미드 #친구 #10대")
                .story("- **A: Alex, you can’t just go around cloning people without their permission.**\n" +
                        "    - Alex, 사람들 허락도 없이 막 복제하고 다니면 안 되지.\n" +
                        "- **B: Well. uh… Well… You borrowed my boots without asking.**\n" +
                        "    - 그게…음…음… 너도 내 허락 없이 부츠 빌려 갔었잖아.\n" +
                        "- **A: Okay, those were footwear. You borrowed a whole person.**\n" +
                        "    - 아니, 그건 신발이었잖아. 넌 지금 사람 자체를 빌려 간 거고.\n" +
                        "- **C: She doesn’t like me.**\n" +
                        "    - 얘가 나 안 좋아하나 봐.\n" +
                        "- **A: No, no, no, no, I do like you. You’re me. Look what you made me do, Alex! I’ve hurt my own feelings!**\n" +
                        "    - 아냐, 아냐, 나 너 좋아해. 네가 나인걸. 너 때문에 이게 뭐야, Alex! 내가 나한테 상처를 줬잖아!")
                .origin("출처: 우리 가족 마법사(Wizards of Waverly Place)")
                .build();
        scriptRepository.save(script);

        script = Script.builder()
                .type(3)
                .category("#영화 #오해 #연인 #친구")
                .story("- **A: Hang on. What is he doing here?**\n" +
                        "    - 잠깐만. 쟤는 여기서 뭐 하는 거야?\n" +
                        "- **B: I believe it’s pronounced, “Thank you.”**\n" +
                        "    - “감사합니다”라고 하는 게 맞는 것 같은데.\n" +
                        "- **A: For what?**\n" +
                        "    - 왜?\n" +
                        "- **B: For saving your life again and again and again.**\n" +
                        "    - 몇 번이고 목숨을 구해줬기 때문이지.\n" +
                        "- **C: You said, “Baby, get me Michael Bryce!”**\n" +
                        "    - 당신이 말했잖아, “자기야, Michael Bryce 데리고 와!”\n" +
                        "- **A: What? No! No, no, no, no, no. I said, “Baby, I need help. Get anyone but Michael Bryce!”**\n" +
                        "    - 뭐? 아니야! 아니, 아니, 아니, 아니, 아니. 내가 말한 건, “자기야, 도움이 필요해. Michael Bryce 뻬고 아무나 데리고 와!”\n" +
                        "- **C: What?**\n" +
                        "    - 뭐?\n" +
                        "- **A: He is the most annoying…**\n" +
                        "    - 쟤는 가장 짜증 나게 하는…\n" +
                        "- **B: Excuse me! I have twenty-twenty hearing. I can hear you. How many times have you nearly got me killed?**\n" +
                        "    - 저기요! 제 청력은 멀쩡하거든요. 다 들린다고요. 네가 날 죽일 뻔한 게 몇 번이지?\n" +
                        "- **A: Not enough!**\n" +
                        "    - 충분치 않았나 봐!")
                .origin("출처: 킬러의 보디가드 2")
                .build();
        scriptRepository.save(script);

        script = Script.builder()
                .type(3)
                .category("#인터뷰 #셀럽 #시상식")
                .story("- **A: Okay, so tell me. presenting tonight,**\n" +
                        "    - 좋아요, 말해보세요, 오늘 밤 시상하는거요,\n" +
                        "- **B: Yeah.**\n" +
                        "    - 네.\n" +
                        "- **A: big, big deal, at the Grammys.**\n" +
                        "    - Grammys에서라니, 정말 대단한 거잖아요.\n" +
                        "- **C: Yeah.**\n" +
                        "    - 네.\n" +
                        "- **A: Coming up, right? This feels good?**\n" +
                        "    - 다가오고 있잖아요, 그렇죠? 기분 좋으시죠?\n" +
                        "- **B: So crazy.**\n" +
                        "    - 미쳤죠.\n" +
                        "- **C: Yeah. We really appreciate this opportunity.**\n" +
                        "    - 네. 이 기회를 주셔서 정말 감사해요.\n" +
                        "- **B: Good, it feels good. Stayed up all night.**\n" +
                        "    - 좋아요, 진짜 기분 좋아요. 어제 밤 새웠어요.\n" +
                        "- **A: Did you?**\n" +
                        "    - 진짜요?\n" +
                        "- **B: Yeah, look at my eyes, like, it’s red. Yeah, so I put a glasses in.**\n" +
                        "    - 네, 제 눈 좀 보세요. 빨갛잖아요. 네, 그래서 안경 썼어요.")
                .build();
        scriptRepository.save(script);

        script = Script.builder()
                .type(3)
                .category("#미드 #친구 #게임")
                .story("- **A: Gentlemen, your pick.**\n" +
                        "    - 신사분들, 골라 주세요.\n" +
                        "- **B: It’s all relative.**\n" +
                        "    - ‘모든 건 친척에 대한 것이다.’\n" +
                        "- **A: Monica and I had a grandmother who died. You both went to her funeral. Name that grandmother?**\n" +
                        "    - Monica와 저에게는 돌아가신 할머니가 계십니다. 두 분 다 장례식에 가셨고요. 그 할머니의 성함은?\n" +
                        "- **B: Nana?**\n" +
                        "    - 할머니?\n" +
                        "- **C: She has a real name.**\n" +
                        "    - 이름은 따로 있으시겠지.\n" +
                        "- **B: Althea!**\n" +
                        "    - Althea!\n" +
                        "- **C: Althea? What are you doing?**\n" +
                        "    - Althea? 뭐 하는 거야?\n" +
                        "- **B: I took a shot.**\n" +
                        "    - 찍어 봤어.\n" +
                        "- **C: You are shooting with Althea?**\n" +
                        "    - Althea로 찍었다고?\n" +
                        "- **A: Althea is correct.**\n" +
                        "    - Althea가 정답입니다.\n" +
                        "- **C: Nice shooting!**\n" +
                        "    - 잘 찍었어!")
                .origin("출처: 프렌즈(Friends)")
                .build();
        scriptRepository.save(script);

        script = Script.builder()
                .type(3)
                .category("#미드 #친구 #연애 #연인 #비밀")
                .story("- **A: Can I talk to you for a second? I need to talk to you.**\n" +
                        "    - 잠깐 얘기 좀 할 수 있을까? 이야기 좀 하자.\n" +
                        "- **B: Oh! oh!**\n" +
                        "    - 헐! 헐!\n" +
                        "- **A: Yes. Yes.**\n" +
                        "    - 그래. 맞아.\n" +
                        "- **B: You? And, and you?**\n" +
                        "    - 네가? 너랑?\n" +
                        "- **C: Yes, but you can not tell anyone. No one knows.**\n" +
                        "    - 맞아. 하지만 아무한테도 말하면 안 돼. 아무도 몰라.\n" +
                        "- **B: How? When?**\n" +
                        "    - 어쩌다가? 언제?\n" +
                        "- **A: It happend it London.**\n" +
                        "    - London에서 그렇게 됐어.\n" +
                        "- **B: In London?**\n" +
                        "    - London에서?\n" +
                        "- **A: The reason we didn’t tell anyone was because we didn’t want to make a big deal out of it.**\n" +
                        "    - 큰일로 만들고 싶지 않아서 아무한테도 안 말한거야.\n" +
                        "- **B: But it is a big deal. I have to tell someone.**\n" +
                        "    - 하지만 큰 일이 맞는걸. 누구한테 말해야겠어.\n" +
                        "- **C: No, no, no. You can’t. Please, please. We just don’t want to deal with telling everyone, okay? Just promise you won’t tell.**\n" +
                        "    - 안돼, 안돼. 그러면 안 돼. 제발, 제발. 모두에게 알리는 수고를 겪고 싶지 않아, 알겠어? 제발 말하지 않겠다고 약속해줘.\n" +
                        "- **B: All right.**\n" +
                        "    - 그래.")
                .build();
        scriptRepository.save(script);

        script = Script.builder()
                .type(3)
                .category("#영화 #친구 #게임 #스포츠")
                .story("- **A: That’s a game!**\n" +
                        "    - 이게 게임이지!\n" +
                        "- **B: Okay, fake block. Stay down, we’ll just take them. okay.**\n" +
                        "    - 자, 수비를 페이크 칠 거야. 자세 낮추고, 한 번 이겨보자고. 알겠어.\n" +
                        "- **C: Mav, Mav! Come on, man. Just one more game. That evens it up.**\n" +
                        "    - Mav, Mav! 왜 그래, 인마. 한 판만 더 하자. 그럼 동점 가능해.\n" +
                        "- **B: Come on, where are you guys going? You walking away?**\n" +
                        "    - 어이, 너네 어디 가? 도망치냐?\n" +
                        "- **C: Come on, we can take them. No, No, We can, We got it!**\n" +
                        "    - 제발, 우리가 이길 수 있어. 아니, 아니야, 할 수 있어, 우리가 이길 수 있다고!\n" +
                        "- **B: We won, we won!**\n" +
                        "    - 우리가 이겼네, 이겼어!\n" +
                        "- **A: I got some things I gotta take care of.**\n" +
                        "    - 처리해야 될 일이 좀 있어.\n" +
                        "- **C: Take care of? Just one more game. Please? For me?**\n" +
                        "    - 처리해야 될 일? 한 판만 더하자. 제발? 날 위해서?\n" +
                        "- **A: Sorry.**\n" +
                        "    - 미안.\n" +
                        "- **C: You’re sorry. Come on!**\n" +
                        "    - 미안하다니. 왜 이래!")
                .origin("출처: 탑건 (Top Gun)")
                .build();
        scriptRepository.save(script);

        script = Script.builder()
                .type(3)
                .category("#영화 #아이 #인사 #첫만남 #소개")
                .story("- **A: Have a seat, Ben. Hey, Paige? This is Ben. he works with Mommy.**\n" +
                        "    - 앉으세요, 벤. 저기, Paige? 여기는 Ben이야. 엄마랑 일하셔.\n" +
                        "- **B: Hi.**\n" +
                        "    - 안녕하세요.\n" +
                        "- **C: Hi.**\n" +
                        "    - 안녕.\n" +
                        "- **B: I’m playing Princess Memory game.**\n" +
                        "    - Princess Memory 게임을 하고 있어요.\n" +
                        "- **C: Oh, hey, looks like you’re about to win.**\n" +
                        "    - 오, 네가 이길 것 같네.\n" +
                        "- **A: So you’re Jules’ new driver?**\n" +
                        "    - 그러면  Jules의 새 운전기사세요?\n" +
                        "- **C: No, actually, I’m her intern.**\n" +
                        "    - 아뇨. 사실 인턴이에요.\n" +
                        "- **B: That’s hysterical.**\n" +
                        "    - 진짜 웃기네요.\n" +
                        "- **A: You know what an intern is?**\n" +
                        "    - 너 인턴이 뭔지 알아?\n" +
                        "- **B: No.**\n" +
                        "    - 아니.\n" +
                        "- **C: That’s okay. Everyone thinks it’s hysterical.**\n" +
                        "    - 괜찮아. 다들 웃기다고 생각하거든.")
                .origin("출처: 인턴(The Intern)")
                .build();
        scriptRepository.save(script);

        script = Script.builder()
                .type(3)
                .category("#영화 #친구 #10대 #하이틴")
                .story("- **A: Regina, we have to talk to you.**\n" +
                        "    - Regina, 너한테 할 말 있어.\n" +
                        "- **B: Is butter a carb?**\n" +
                        "    - 버터가 탄수화물이던가?\n" +
                        "- **C: Gina, you’re wearning sweatpants. It’s Monday.**\n" +
                        "    - Gina, 너 추리닝 입고 있잖아. 오늘 월요일이야.\n" +
                        "- **B: So?**\n" +
                        "    - 그래서?\n" +
                        "- **A: So that’s against the rules and you can’t sit with us.**\n" +
                        "    - 그건 규칙 위반이고, 우리와 같이 앉을 수 없다는 뜻이야.\n" +
                        "- **B: Whatever. Those rules aren’t real.**\n" +
                        "    - 어쩌라고. 진짜 규칙도 아닌데.\n" +
                        "- **C: They were real that day I wore a vest.**\n" +
                        "    - 내가 조끼를 입었던 날에는 충분히 진짜였거든.\n" +
                        "- **B: Because that vest was disgusting.**\n" +
                        "    - 그건 그 조끼가 못생겨서였고.\n" +
                        "- **A: You can’t sit with us!**\n" +
                        "    - 우리랑 같이 앉을 수 없다고!")
                .origin("출처: 퀸카로 살아남는 법(Mean Girls)")
                .build();
        scriptRepository.save(script);

        script = Script.builder()
                .type(3)
                .category("#미드 #아이 #가족 #부부 #육아")
                .story("- **A: How was your school day?**\n" +
                        "    - 학교는 어땠어?\n" +
                        "- **B: Best day ever ‘cause Daddy was there!**\n" +
                        "    - 아빠가 계셔서 최고의 날이었어요!\n" +
                        "- **A: Really? Did you like Daddy being in school?**\n" +
                        "    - 진짜? 아빠가 학교에 가는 게 좋았어?\n" +
                        "- **B: He made snacks, he read books, and now I call Tommy “big ears.”**\n" +
                        "    - 간식도 만들어 주시고, 책도 읽어주시고, 이젠 Tommy를 “대왕 귀”라고 불러요.\n" +
                        "- **C: I’m glad that stuck.**\n" +
                        "    - 별명이 정착됐다니 좋구나.\n" +
                        "- **A: That’s so nice!**\n" +
                        "    - 엄청 좋았겠다!\n" +
                        "- **B: Daddy, can you come back tomorrow?**\n" +
                        "    - 아빠, 내일도 오시면 안 돼요?\n" +
                        "- **C: Do you really want me to come back tomorrow?**\n" +
                        "    - 정말로 내가 갔으면 좋겠어?\n" +
                        "- **B: yeah.**\n" +
                        "    - 네.\n" +
                        "- **C: What do you know? I’m a hit!**\n" +
                        "    - 누가 생각이나 했겠어요? 저 인기 많아요!")
                .origin("출처: 모던 패밀리 (Modern Family)")
                .build();
        scriptRepository.save(script);

        script = Script.builder()
                .type(3)
                .category("#미드 #연인 #오해")
                .story("- **A: I can’t wait to be with you. I’ll sneak over as soon as Ross pics up Ben.**\n" +
                        "    - 너랑 같이 있고 싶어서 못 참겠어. Ross가 Ben 데리러 가자마자 몰래 찾아갈게.\n" +
                        "- **B: I’ll just tell Rachel I’m gonna be doing laundry for a couples hours.**\n" +
                        "    - Rachel한테는 그냥 몇 시간 빨래한다고 말해 둘게.\n" +
                        "- **C: Laundry, huh? Is that my new nickname?**\n" +
                        "    - 빨래라, 흠? 그게 내 새로운 별명이야?\n" +
                        "- **A: Aw.**\n" +
                        "    - 어머.\n" +
                        "- **B: You know what your nickname is, Mr.Big…**\n" +
                        "    - 네 별명이 뭔진 너도 알잖아, Mr.Big…\n" +
                        "- **C: Ugh!**\n" +
                        "    - 으악!")
                .origin("출처: 프렌즈(Friends)")
                .build();
        scriptRepository.save(script);

        script = Script.builder()
                .type(3)
                .category("#영화 #연애 #첫만남 #소개팅")
                .story("- **A: You really like me? Even my frock?**\n" +
                        "    - 제가 정말 마음에 드세요? 제 드레스도요?\n" +
                        "- **B: I love your frock.**\n" +
                        "    - 드레스 너무 마음에 들어요.\n" +
                        "- **A: And, um, my hair? It’s not too brown?**\n" +
                        "    - 그리고, 어, 제 머리도요? 너무 갈색이진 않고요?\n" +
                        "- **B: I love brown.**\n" +
                        "    - 저 갈색 엄청 좋아해요.\n" +
                        "- **A: My fringe is new.**\n" +
                        "    - 앞머리 새로 자른 건데.\n" +
                        "- **B: The fringe is perfect. Fringe is the best bit.**\n" +
                        "    - 앞머리가 완벽하네요. 앞머리가 가장 마음에 드는 걸요.\n" +
                        "- **C: We have to go!**\n" +
                        "    - 우리 가야 돼!\n" +
                        "- **C: I found a cab and his dodgy friend is about to assault me.**\n" +
                        "    - 택시 잡았는데 저 사람의 이상한 친구가 나한테 해코지하려고 해.\n" +
                        "- **A: Okay, okay, I’m… I’m coming!**\n" +
                        "    - 알겠어, 알겠어, 나… 지금 가!\n" +
                        "- **B: Two seconds…**\n" +
                        "    - 2초만…\n" +
                        "- **A: I hope I see you again.**\n" +
                        "    - 또 뵀으면 좋겠네요.\n" +
                        "- **B: You will.**\n" +
                        "    - 또 볼 수 있을 거예요.\n" +
                        "- **A: Okay. Good. Goodnight.**\n" +
                        "    - 그래요. 좋아요. 안녕히 가세요.\n" +
                        "- **B: Goodnight.**\n" +
                        "    - 안녕히 가세요.")
                .origin("출처: 어바웃 타임(About Time)")
                .build();
        scriptRepository.save(script);

        script = Script.builder()
                .type(3)
                .category("#영화 #SF")
                .story("- **A: A giant comet hurtling its way, towards Earth!**\n" +
                        "    - 거대한 혜성이 지구를 향해 돌진하고 있어요!\n" +
                        "- **A: Can you see it?**\n" +
                        "    - 보여?\n" +
                        "- **B: I can’t! My head is in a bag!**\n" +
                        "    - 아니! 머리에 봉지를 씌워놨잖아!\n" +
                        "- **C: I did have the FBI put that bag over your head. They don’t do that. The CIA does, but I made them do it.**\n" +
                        "    - 제가 FBI한테 당신 머리에 봉지를 씌우라고 시켰어요. 원래 안 그러거든요. CIA가 하는 건데, 제가 그러게 만들었어요.\n" +
                        "- **B: You know, I had a feeling.**\n" +
                        "    - 글쎄, 감이 오더라고요.\n" +
                        "- **C: It’s a good feeling, ‘cause that is what I did and it was very funny and cool.**\n" +
                        "    - 감이 좋으시네요, 제가 실제로 그랬고, 재밌고 멋있었으니까요.")
                .origin("출처: 돈 룩 업(Don’t Look Up)")
                .build();
        scriptRepository.save(script);

    }



}

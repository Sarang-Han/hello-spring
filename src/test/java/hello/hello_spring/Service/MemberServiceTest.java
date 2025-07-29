package hello.hello_spring.Service;

import hello.hello_spring.domain.Member;
import hello.hello_spring.repository.MemoryMemberRepository;
import hello.hello_spring.service.MemberService;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MemberServiceTest {
    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach
    public void beforeEach(){
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    public void afterEach(){
        memberRepository.clearStore();
    }

    @Test
    public void 회원가입(){
        //given
        Member member = new Member();
        member.setName("Hello");

        Long saveId = memberService.join(member);

        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void 중복회원예외(){
        Member member1 = new Member();
        member1.setName("Hello");

        Member member2 = new Member();
        member2.setName("Hello");

        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class,
                ()-> memberService.join(member2));

        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원");
    }



}

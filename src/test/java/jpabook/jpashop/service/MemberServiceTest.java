package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    void 회원가입(){
        //given
        Member member = new Member();
        member.setUsername("kim");
        //when
        Long savedId = memberService.join(member);
        //then
        Assertions.assertThat(member).isEqualTo(memberRepository.findOne(savedId));
    
    }
    
    @Test
    void 중복_회원_예외(){
        //given
        Member member1 = new Member();
        member1.setUsername("kim1");
        Member member2 = new Member();
        member2.setUsername("kim1");
        //when
        memberService.join(member1);
        //then
        // IllegalStateException 예외가 발생함
        Assertions.assertThatThrownBy( () -> memberService.join(member2)).isInstanceOf(IllegalStateException.class);
    
    }
}
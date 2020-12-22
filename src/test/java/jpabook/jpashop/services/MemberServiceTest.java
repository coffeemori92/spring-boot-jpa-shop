package jpabook.jpashop.services;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domains.Member;
import jpabook.jpashop.repositories.MemberRepository;

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest
public class MemberServiceTest {

  @Autowired
  MemberService memberService;

  @Autowired
  MemberRepository memberRepository;

  @Test
  public void join() throws Exception {
    Member member = new Member();
    member.setName("coffeemori");

    Long savedId = memberService.join(member);

    assertEquals(member, memberRepository.findOne(savedId));
  }

  @Test(expected = IllegalStateException.class)
  public void validateDuplicatedMember() throws Exception {
    Member member1 = new Member();
    member1.setName("kim1");

    Member member2 = new Member();
    member2.setName("kim1");

    memberService.join(member1);
    memberService.join(member2);
  
    fail("예외가 발생해야 한다.");
  }
  
}

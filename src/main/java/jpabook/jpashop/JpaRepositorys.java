package jpabook.jpashop;

import jpabook.jpashop.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaRepositorys extends JpaRepository<Member, Long> {
}

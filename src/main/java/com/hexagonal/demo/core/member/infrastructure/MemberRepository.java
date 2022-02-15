package com.hexagonal.demo.core.member.infrastructure;

import com.hexagonal.demo.core.member.domain.MemberEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends ReactiveMongoRepository <MemberEntity, String> {
}

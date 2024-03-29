package com.food.chicken.repository;

import com.food.chicken.model.entity.Member;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends CrudRepository<Member, Long> {

    Member findById(String id);
    
}
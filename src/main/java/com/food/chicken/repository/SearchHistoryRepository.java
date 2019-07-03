package com.food.chicken.repository;

import com.food.chicken.model.entity.SearchHistory;
import com.food.chicken.model.entity.SearchHistoryPK;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchHistoryRepository extends CrudRepository<SearchHistory, SearchHistoryPK> {

    SearchHistory findByMemberUidAndKeyword(long memberUid, String keyword);

    Page<SearchHistory> findByMemberUid(long memberUid, Pageable pageable);

}
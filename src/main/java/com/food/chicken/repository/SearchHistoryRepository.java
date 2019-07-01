package com.food.chicken.repository;

import com.food.chicken.model.SearchHistory;
import com.food.chicken.model.SearchHistoryPK;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchHistoryRepository extends CrudRepository<SearchHistory, SearchHistoryPK> {

    SearchHistory findByMemberUidAndKeyword(long memberUid, String keyword);

}
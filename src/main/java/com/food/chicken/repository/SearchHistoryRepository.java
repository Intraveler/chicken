package com.food.chicken.repository;

import com.food.chicken.model.entity.SearchHistory;
import com.food.chicken.model.entity.SearchHistoryPK;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchHistoryRepository extends CrudRepository<SearchHistory, SearchHistoryPK> {

    SearchHistory findByMemberUidAndKeyword(long memberUid, String keyword);

    List<SearchHistory> findByMemberUidOrderByLastSearchDateDesc(long memberUid);

}
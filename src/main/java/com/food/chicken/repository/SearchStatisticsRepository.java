package com.food.chicken.repository;

import com.food.chicken.model.entity.SearchStatistics;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchStatisticsRepository extends CrudRepository<SearchStatistics, String> {

    Page<SearchStatistics> findAll(Pageable pageable);

}
package com.food.chicken.repository;

import com.food.chicken.model.SearchStatistics;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchStatisticsRepository extends CrudRepository<SearchStatistics, String> {

}
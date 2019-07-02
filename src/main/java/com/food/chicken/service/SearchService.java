package com.food.chicken.service;

import com.food.chicken.model.Member;
import com.food.chicken.model.SearchHistory;
import com.food.chicken.model.SearchStatistics;
import com.food.chicken.repository.MemberRepository;
import com.food.chicken.repository.SearchHistoryRepository;
import com.food.chicken.repository.SearchStatisticsRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.NoSuchElementException;

@Service
public class SearchService {

    private final MemberRepository memberRepository;
    private final SearchHistoryRepository searchHistoryRepository;
    private final SearchStatisticsRepository searchStatisticsRepository;

    public SearchService(MemberRepository memberRepository, SearchHistoryRepository searchHistoryRepository, SearchStatisticsRepository searchStatisticsRepository) {
        this.memberRepository = memberRepository;
        this.searchHistoryRepository = searchHistoryRepository;
        this.searchStatisticsRepository = searchStatisticsRepository;
    }

    public void saveHistory(String id, String keyword) {
        Member member = memberRepository.findById(id);
        long memberUid = member.getMemberUid();

        SearchHistory searchHistory = getHistoryData(memberUid, keyword);
        if (searchHistory != null) {
            searchHistory.setKeyword(keyword);
            searchHistory.setMemberUid(memberUid);
            searchHistory.setLastSearchDate(new Date());
        } else {
            searchHistory = new SearchHistory();
            searchHistory.setKeyword(keyword);
            searchHistory.setMemberUid(memberUid);
            searchHistory.setCreatedDate(new Date());
            searchHistory.setLastSearchDate(new Date());
        }
        searchHistoryRepository.save(searchHistory);
    }

    public SearchHistory getHistoryData(long memberUid, String keyword) {
        try {
            return searchHistoryRepository.findByMemberUidAndKeyword(memberUid, keyword);
        } catch (Exception e) {
            return null;
        }
    }

    public void saveStatistics(String keyword) {
        SearchStatistics searchStatistics = getStatisticsData(keyword);
        if (searchStatistics != null) {
            searchStatistics.setCount(searchStatistics.getCount() + 1);
            searchStatisticsRepository.save(searchStatistics);
        } else {
            SearchStatistics createData = new SearchStatistics();
            createData.setKeyword(keyword);
            createData.setCount(1);
            searchStatisticsRepository.save(createData);
        }
    }

    public SearchStatistics getStatisticsData(String keyword) {
        try {
            return searchStatisticsRepository.findById(keyword).get();
        } catch (NoSuchElementException e) {
            return null;
        }
    }
}
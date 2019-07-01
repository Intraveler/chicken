package com.food.chicken.service;

import com.food.chicken.model.Member;
import com.food.chicken.model.SearchHistory;
import com.food.chicken.repository.MemberRepository;
import com.food.chicken.repository.SearchHistoryRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SearchService {

    private final MemberRepository memberRepository;
    private final SearchHistoryRepository searchHistoryRepository;

    public SearchService(MemberRepository memberRepository, SearchHistoryRepository searchHistoryRepository) {
        this.memberRepository = memberRepository;
        this.searchHistoryRepository = searchHistoryRepository;
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
}

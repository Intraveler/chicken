package com.food.chicken.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.food.chicken.model.entity.Member;
import com.food.chicken.model.entity.SearchHistory;
import com.food.chicken.model.entity.SearchStatistics;
import com.food.chicken.model.json.Location;
import com.food.chicken.model.json.MySearchHistory;
import com.food.chicken.repository.MemberRepository;
import com.food.chicken.repository.SearchHistoryRepository;
import com.food.chicken.repository.SearchStatisticsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class SearchService {
    private static final Logger log = LoggerFactory.getLogger(SearchService.class);

    @Value("${kakao.auth.prefix}")
    String kakaoApiAuthPrefix;

    @Value("${kakao.auth.key.rest-api}")
    String kakaoApiAuthKey;

    @Value("${kakao.api.address.location}")
    String requestUrl;

    private final MemberRepository memberRepository;
    private final SearchHistoryRepository searchHistoryRepository;
    private final SearchStatisticsRepository searchStatisticsRepository;
    private final RestTemplate restTemplate;

    public SearchService(MemberRepository memberRepository, SearchHistoryRepository searchHistoryRepository, SearchStatisticsRepository searchStatisticsRepository, RestTemplate restTemplate) {
        this.memberRepository = memberRepository;
        this.searchHistoryRepository = searchHistoryRepository;
        this.searchStatisticsRepository = searchStatisticsRepository;
        this.restTemplate = restTemplate;
    }

    public void saveHistory(String id, String keyword) {
        log.debug("[exec]saveHistory()");

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

    private SearchHistory getHistoryData(long memberUid, String keyword) {
        log.debug("[exec]getHistoryData()");

        try {
            return searchHistoryRepository.findByMemberUidAndKeyword(memberUid, keyword);
        } catch (Exception e) {
            return null;
        }
    }

    public void saveStatistics(String keyword) {
        log.debug("[exec]saveStatistics()");

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

    private SearchStatistics getStatisticsData(String keyword) {
        log.debug("[exec]getStatisticsData()");

        try {
            return searchStatisticsRepository.findById(keyword).get();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    public String callApi(String keyword, String page) throws RestClientException, IOException {
        log.debug("[exec]callApi()");

        String param = "query=" + keyword + "&" + "page=" + page;
        String result = restTemplate.exchange(requestUrl + param, HttpMethod.GET, getHeader(), String.class).getBody();

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = new ObjectMapper().readValue(result, ObjectNode.class);
        JsonNode documents = node.get("documents");
        JsonNode totalCount = node.get("meta").get("total_count");
        JsonNode isEnd = node.get("meta").get("is_end");

        List<Location> locationList = new ArrayList<>();
        ObjectMapper rootObject = new ObjectMapper();

        documents.forEach(item -> {
            Location location = setLocationModel(item, new Location());
            locationList.add(location);
        });
        ArrayNode array = mapper.valueToTree(locationList);

        ObjectNode object = rootObject.createObjectNode();
        object.set("list", array);
        object.set("total_count", totalCount);
        object.set("is_end", isEnd);

        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
    }

    private Location setLocationModel(JsonNode jsonData, Location location) {
        log.debug("[exec]setLocationModel()");

        location.setAddress_name(jsonData.get("address_name").textValue());
        location.setCategory_group_code(jsonData.get("category_group_code").textValue());
        location.setCategory_group_name(jsonData.get("category_group_name").textValue());
        location.setCategory_name(jsonData.get("category_name").textValue());
        location.setDistance(jsonData.get("distance").textValue());
        location.setId(jsonData.get("id").textValue());
        location.setPhone(jsonData.get("phone").textValue());
        location.setPlace_name(jsonData.get("place_name").textValue());
        location.setPlace_url(jsonData.get("place_url").textValue());
        location.setRoad_address_name(jsonData.get("road_address_name").textValue());
        location.setX(jsonData.get("x").textValue());
        location.setY(jsonData.get("y").textValue());

        return location;
    }

    public String getKeywordHistoryByMember(String id) throws IOException {
        log.debug("[exec]getKeywordHistoryByMember()");

        ObjectMapper mapper = new ObjectMapper();
        List<MySearchHistory> mySearchHistoryList = new ArrayList<>();
        Member member = memberRepository.findById(id);
        SimpleDateFormat format = new SimpleDateFormat("yy/MM/dd hh:mm:ss");

        Pageable pageable = PageRequest.of(0, 10, new Sort(Sort.Direction.DESC, "lastSearchDate"));

        searchHistoryRepository
                .findByMemberUid(member.getMemberUid(), pageable)
                .forEach(item -> {
                    MySearchHistory mySearchHistory = new MySearchHistory();
                    mySearchHistory.setKeyword(item.getKeyword());
                    mySearchHistory.setLastSearchDate(format.format(item.getLastSearchDate()));
                    mySearchHistoryList.add(mySearchHistory);
                });

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        mapper.writeValue(out, mySearchHistoryList);

        return new String(out.toByteArray());
    }

    public String getPopulateKeyword() throws IOException {
        log.debug("[exec]getPopulateKeyword()");

        ObjectMapper mapper = new ObjectMapper();
        Pageable pageable = PageRequest.of(0, 10, new Sort(Sort.Direction.DESC, "count"));
        Page<SearchStatistics> populateKeywordList = searchStatisticsRepository.findAll(pageable);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        mapper.writeValue(out, populateKeywordList);

        return new String(out.toByteArray());
    }

    private HttpEntity<String> getHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        headers.set(HttpHeaders.AUTHORIZATION, kakaoApiAuthPrefix + " " + kakaoApiAuthKey);
        return new HttpEntity<>(headers);
    }
}
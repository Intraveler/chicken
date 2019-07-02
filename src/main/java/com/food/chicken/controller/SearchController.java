package com.food.chicken.controller;

import com.food.chicken.exceptions.ExternalException;
import com.food.chicken.exceptions.InternalException;
import com.food.chicken.exceptions.UnknownException;
import com.food.chicken.service.SearchService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

import java.io.IOException;
import java.security.Principal;

@RestController
public class SearchController {
    private static final Logger log = LoggerFactory.getLogger(SearchController.class);
    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword", required = true, value = "조회할 키워드", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "page", required = true, value = "조회할 페이지 (1~45)", paramType = "query", dataType = "int")
    })
    @RequestMapping(value = "/keyword/location", method = RequestMethod.GET)
    public ResponseEntity searchLocation(Principal principal
            , @RequestParam("keyword") String keyword
            , @RequestParam("page") Integer page) {

        String memberId = principal.getName();
        ResponseEntity responseEntity;
        try {
            searchService.saveHistory(memberId, keyword);
            searchService.saveStatistics(keyword);

            responseEntity = ResponseEntity
                    .status(HttpStatus.OK)
                    .body(searchService.callApi(keyword, String.valueOf(page)));

            log.info("[SUCCESS] REST API [/keyword/location] || memberID={} || keyword={}", memberId, keyword);

        } catch (RestClientException e) { // 외부 예외
            log.error("[FAIL] REST API [/keyword/location] || memberID={} || keyword={}", memberId, keyword, e);
            throw new ExternalException(e.getMessage());
        } catch (IOException e) { // 내부에서 알려진 예외
            log.error("[FAIL] REST API [/keyword/location] || memberID={} || keyword={}", memberId, keyword, e);
            throw new InternalException(e.getMessage());
        } catch (Exception e) { // 알려지지 않은 예외
            log.error("[FAIL] REST API [/keyword/location] || memberID={} || keyword={}", memberId, keyword, e);
            throw new UnknownException(e.getMessage());
        }

        return responseEntity;
    }

    @RequestMapping(value = "/keyword/mykeyword", method = RequestMethod.GET)
    public ResponseEntity searchKeywordHistory(Principal principal) {
        String memberId = principal.getName();

        ResponseEntity responseEntity;
        try {

            responseEntity = ResponseEntity
                    .status(HttpStatus.OK)
                    .body(searchService.getKeywordHistoryByMember(memberId));

            log.info("[SUCCESS] REST API [/keyword/mykeyword] || memberID={}", memberId);

        } catch (IOException e) { // 내부에서 알려진 예외
            log.error("[FAIL] REST API [/keyword/mykeyword] || memberID={}", memberId, e);
            throw new InternalException(e.getMessage());
        } catch (Exception e) { // 알려지지 않은 예외
            log.error("[FAIL] REST API [/keyword/mykeyword] || memberID={}", memberId, e);
            throw new UnknownException(e.getMessage());
        }

        return responseEntity;
    }

    @RequestMapping(value = "/keyword/populate", method = RequestMethod.GET)
    public ResponseEntity searchPopulateKeyword() {

        ResponseEntity responseEntity;
        try {

            responseEntity = ResponseEntity
                    .status(HttpStatus.OK)
                    .body(searchService.getPopulateKeyword());

        } catch (IOException e) { // 내부에서 알려진 예외
            log.error("[FAIL] REST API [/keyword/populate]", e);
            throw new InternalException(e.getMessage());
        } catch (Exception e) { // 알려지지 않은 예외
            log.error("[FAIL] REST API [/keyword/populate]", e);
            throw new UnknownException(e.getMessage());
        }
        return responseEntity;
    }
}

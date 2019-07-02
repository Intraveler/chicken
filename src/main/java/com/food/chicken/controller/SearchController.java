package com.food.chicken.controller;

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

import java.security.Principal;

@RestController
public class SearchController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SearchController.class);
    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword", required = true, value = "조회할 키워드", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "page", required = true, value = "조회할 페이지 (1~45)", paramType = "query", dataType = "string")
    })
    @RequestMapping(value = "/keyword/location", method = RequestMethod.GET)
    public ResponseEntity searchLocation(Principal principal
            , @RequestParam("keyword") String keyword
            , @RequestParam("page") String page) {
        String memberId = principal.getName();
        ResponseEntity responseEntity;

        try {
            searchService.saveHistory(memberId, keyword);
            searchService.saveStatistics(keyword);

            responseEntity = ResponseEntity
                    .status(HttpStatus.OK)
                    .body(searchService.callApi(keyword, page));

            LOGGER.info("--success [/keyword/location] api--");

        } catch (Exception exception) {

            responseEntity = ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Sorry, Board Error");

            LOGGER.error("error : ", exception);
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

            LOGGER.info("--success [/keyword/mykeyword] api--");

        } catch (Exception exception) {

            responseEntity = ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Sorry, Board Error");

            LOGGER.error("error : ", exception);
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

            LOGGER.info("--success [/keyword/mykeyword] api--");

        } catch (Exception exception) {

            responseEntity = ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Sorry, Board Error");

            LOGGER.error("error : ", exception);
        }
        return responseEntity;
    }
}

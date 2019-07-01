package com.food.chicken.controller;

import com.food.chicken.service.SearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    @RequestMapping(value = "/keyword/location", method = RequestMethod.GET)
    public void searchLocation(Principal principal, @RequestParam("keyword") String keyword) {
        String memberId = principal.getName();

        try {
            searchService.saveHistory(memberId, keyword);

            LOGGER.info("--success [/keyword/location] api--");

        } catch (Exception exception) {

            LOGGER.error("error : ", exception);
        }
    }
}

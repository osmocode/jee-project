package dev.osmocode.codehub.controller;


import dev.osmocode.codehub.service.QuestionTagService;
import dev.osmocode.codehub.utils.validator.pagination.PaginationValidator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;


@Controller
public class QuestionTagController {

    private final PaginationValidator paginationValidator;
    private final QuestionTagService questionTagService;

    public QuestionTagController(PaginationValidator paginationValidator, QuestionTagService questionTagService) {
        this.paginationValidator = paginationValidator;
        this.questionTagService = questionTagService;
    }

    @GetMapping("/tags")
    public ModelAndView getTag(
            @RequestParam(value = "limit", required = false) Optional<Integer> optionalLimit,
            @RequestParam(value = "offset", required = false) Optional<Integer> optionalOffset,
            @RequestParam(value = "search", required = false) Optional<String> optionalSearch
    ) {
        var limit = paginationValidator.sanitizeLimit(optionalLimit);
        var offset = paginationValidator.sanitizeOffset(optionalOffset);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("tags");
        modelAndView.addObject("tagsPage", questionTagService.getTagsBySearch(optionalSearch.orElse(""), limit, offset));
        modelAndView.addObject("search", optionalSearch.orElse(""));
        return modelAndView;
    }
}

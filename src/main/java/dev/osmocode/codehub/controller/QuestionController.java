package dev.osmocode.codehub.controller;

import dev.osmocode.codehub.service.QuestionService;
import dev.osmocode.codehub.service.QuestionTagService;
import dev.osmocode.codehub.utils.validator.pagination.PaginationValidator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class QuestionController {

    private final PaginationValidator paginationValidator;
    private final QuestionService questionService;
    private final QuestionTagService questionTagService;

    public QuestionController(
            PaginationValidator paginationValidator,
            QuestionService questionService,
            QuestionTagService questionTagService
    ) {
        this.paginationValidator = paginationValidator;
        this.questionService = questionService;
        this.questionTagService = questionTagService;
    }

    @GetMapping("/question")
    public ModelAndView getQuestions(
            @RequestParam(value = "limit", required = false) Optional<Integer> optionalLimit,
            @RequestParam(value = "offset", required = false) Optional<Integer> optionalOffset,
            @RequestParam(value = "search", required = false) Optional<String> optionalSearch
    ) {
        var limit = paginationValidator.sanitizeLimit(optionalLimit);
        var offset = paginationValidator.sanitizeOffset(optionalOffset);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("questions");
        modelAndView.addObject("questions", questionService.getQuestionBySearchTitle(optionalSearch.orElse(""), limit, offset));
        modelAndView.addObject("search", optionalSearch.orElse(""));
        return modelAndView;
    }

    @GetMapping("/question/{id}")
    public ModelAndView getQuestion(@PathVariable long id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("question");
        modelAndView.addObject("question", questionService.findQuestionById(id).orElse(null));
        return modelAndView;
    }

}

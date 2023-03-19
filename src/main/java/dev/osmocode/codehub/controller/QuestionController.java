package dev.osmocode.codehub.controller;

import dev.osmocode.codehub.dto.QuestionAnswerDto;
import dev.osmocode.codehub.dto.QuestionAnswerVoteDto;
import dev.osmocode.codehub.repository.UserRepository;
import dev.osmocode.codehub.service.AnswerScoreService;
import dev.osmocode.codehub.service.QuestionAnswerService;
import dev.osmocode.codehub.service.QuestionService;
import dev.osmocode.codehub.service.QuestionTagService;
import dev.osmocode.codehub.utils.validator.pagination.PaginationValidator;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class QuestionController {

    private final PaginationValidator paginationValidator;
    private final QuestionService questionService;
    private final QuestionTagService questionTagService;

    private final QuestionAnswerService questionAnswerService;
    private final AnswerScoreService answerScoreService;
    private final UserRepository userRepository;

    public QuestionController(
            PaginationValidator paginationValidator,
            QuestionService questionService,
            QuestionTagService questionTagService,
            QuestionAnswerService questionAnswerService,
            UserRepository userRepository,
            AnswerScoreService answerScoreService) {
        this.paginationValidator = paginationValidator;
        this.questionService = questionService;
        this.questionTagService = questionTagService;
        this.questionAnswerService = questionAnswerService;
        this.userRepository = userRepository;
        this.answerScoreService = answerScoreService;
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
        modelAndView.addObject("questionAnswerDto", new QuestionAnswerDto());
        modelAndView.addObject("question", questionService.findQuestionById(id));
        modelAndView.addObject("answers", questionAnswerService.findAnswersById(id));
        return modelAndView;
    }

    @PostMapping("/question/{id}")
    public ModelAndView postQuestionAnswer(
            Authentication authentication,
            @PathVariable long id,
            @Valid @ModelAttribute("questionAnswerDto") QuestionAnswerDto questionAnswerDto,
            BindingResult bindingResult)
    {
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("error");
            return modelAndView;
        }
        questionAnswerService.performAnswerQuestion(questionAnswerDto, authentication.getName(), id);
        modelAndView.setViewName("redirect:/question/" + id);
        return modelAndView;
    }

    @PostMapping("/question/{id}/vote")
    public ModelAndView postAnswerVote(
            Authentication authentication,
            @PathVariable long id,
            @Valid @ModelAttribute("questionAnswerVoteDto") QuestionAnswerVoteDto questionAnswerVoteDto,
            BindingResult bindingResult
    ){
        ModelAndView modelAndView = new ModelAndView();

        if(bindingResult.hasErrors() || null == authentication){
            modelAndView.setViewName("error");
            return modelAndView;
        }

        answerScoreService.performAnswerAddScore(questionAnswerVoteDto, authentication.getName());
        modelAndView.setViewName("redirect:/question/" + id);
        return modelAndView;
    }

}

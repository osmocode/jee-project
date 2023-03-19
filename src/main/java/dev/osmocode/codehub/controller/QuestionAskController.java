package dev.osmocode.codehub.controller;

import dev.osmocode.codehub.dto.QuestionAskedDto;
import dev.osmocode.codehub.entity.Question;
import dev.osmocode.codehub.service.QuestionService;
import dev.osmocode.codehub.service.QuestionTagService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.Set;

@Controller
@RequestMapping("/question/ask")
public class QuestionAskController {

    private final QuestionService questionService;
    private final QuestionTagService questionTagService;

    public QuestionAskController(
            QuestionService questionService,
            QuestionTagService questionTagService
    ) {
        this.questionService = questionService;
        this.questionTagService = questionTagService;
    }

    @GetMapping
    public String getQuestionAsk(Model model) {
        model.addAttribute("questionAskedDto", new QuestionAskedDto());
        model.addAttribute("questionTags", questionTagService.getAll().stream().limit(10));
        return "question-ask";
    }

    @PostMapping
    public ModelAndView postQuestionAsk(
            Authentication authentication,
            @RequestParam(value = "selectedTags", required = false) Set<Long> selectedTags,
            @Valid @ModelAttribute("questionAskedDto") QuestionAskedDto questionAskedDto,
            BindingResult bindingResult
    ) {
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors() || (selectedTags != null && selectedTags.size() > 5)) {
            modelAndView.setViewName("question-ask");
            modelAndView.addObject("questionAskedDto", questionAskedDto);
            return modelAndView;
        }
        Question question = questionService.performAskQuestion(questionAskedDto, authentication.getName(), selectedTags == null ? Collections.emptySet() : selectedTags);
        modelAndView.setViewName("redirect:/question/" + question.getId());
        return modelAndView;
    }
}

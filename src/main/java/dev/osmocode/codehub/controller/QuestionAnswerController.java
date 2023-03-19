package dev.osmocode.codehub.controller;

import dev.osmocode.codehub.dto.QuestionAnswerVoteDto;
import dev.osmocode.codehub.service.AnswerScoreService;
import dev.osmocode.codehub.service.QuestionAnswerService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class QuestionAnswerController {

    private final QuestionAnswerService questionAnswerService;
    private final AnswerScoreService answerScoreService;

    public QuestionAnswerController(QuestionAnswerService questionAnswerService, AnswerScoreService answerScoreService) {
        this.questionAnswerService = questionAnswerService;
        this.answerScoreService = answerScoreService;
    }

    @PostMapping("/answer/{id}/vote")
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

        var answer = questionAnswerService.findById(id).orElse(null);
        if(null == answer) {
            modelAndView.setViewName("error");
            return modelAndView;
        }

        answerScoreService.performAnswerAddScore(questionAnswerVoteDto, authentication.getName(), answer.getSender().getUsername());
        modelAndView.setViewName("redirect:/question/" + id);
        return modelAndView;
    }
}

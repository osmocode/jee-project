package dev.osmocode.codehub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TemplateController {

    @GetMapping("/template")
    public String getTemplate() {
        return "template";
    }

}

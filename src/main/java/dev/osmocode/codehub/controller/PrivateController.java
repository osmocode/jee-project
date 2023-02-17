package dev.osmocode.codehub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PrivateController {

    @GetMapping("/private")
    public String getPrivate() {
        return "private";
    }

}

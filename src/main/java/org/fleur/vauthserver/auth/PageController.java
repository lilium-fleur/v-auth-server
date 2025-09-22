package org.fleur.vauthserver.auth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/auth/register")
    public String registerPage() {
        return "register_page";
    }

    @GetMapping("/auth/login")
    public String loginPage() {
        return "login_page";
    }
}

package com.petrykdesign.budget.Controllers;


import com.petrykdesign.budget.Repositories.User;
import com.petrykdesign.budget.Repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller

public class loginController {

    final private String PASSWORD = "Justin_Jas_Pidoras_Ohuel2019_!_nePlAtIt";
    final private String salt = "a0fa1e9dd2befa92";
    final TextEncryptor encryptor = Encryptors.text(PASSWORD, salt);

    @Autowired
    private UserRepo userRepo;

    @GetMapping("/login")

    public String login(@RequestParam (required = false, defaultValue = "Test@gmail.com") String email, @RequestParam (defaultValue = "email") String password, Model model
            , HttpServletResponse response,  @CookieValue(value="email", defaultValue="terminated")String lastname) {

        String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
        User user = userRepo.findByEmail(email);
        user.setSessionId(sessionId);
        userRepo.save(user);

        System.out.println("id: " + sessionId);
//        System.out.println(System.getenv("COMPUTERNAME"));
//        System.out.println(System.getenv());
//        System.out.println(System.getProperties());
//        System.out.println(System.getProperty("sun.jnu.encoding"));
//        System.out.println(System.getProperty("java.runtime.version"));

        Cookie usernameCookie = new Cookie("email", encryptor.encrypt(email));
        usernameCookie.setHttpOnly(true);
        //usernameCookie.setSecure(true);
        usernameCookie.setMaxAge(60 * 60);
        response.addCookie(usernameCookie);

        model.addAttribute("email", "email");
        return "login";
    }
    @GetMapping("/")

    public String greeting(@RequestParam (required = false, defaultValue = "Test@gmail.com") String email, @RequestParam (defaultValue = "email") String password, Model model
                            , HttpServletResponse response,  @CookieValue(value="email", defaultValue="terminated")String lastname) throws Exception {

        String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
        String sessionIdInDB = userRepo.findByEmail(email).getSessionId();

        if (!sessionId.equals(sessionIdInDB)){
            response.sendRedirect("/logout");
        }
        Cookie usernameCookie = new Cookie("email", encryptor.encrypt(email));
        usernameCookie.setHttpOnly(true);
        //usernameCookie.setSecure(true);
        usernameCookie.setMaxAge(60 * 60);
        response.addCookie(usernameCookie);

        model.addAttribute("email", "email");
        return "mainPage";
    }
}

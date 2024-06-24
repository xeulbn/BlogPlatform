package org.example.blogplatform.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.blogplatform.dto.UserDTO;
import org.example.blogplatform.model.User;
import org.example.blogplatform.repository.UserRepository;
import org.example.blogplatform.service.UserService;
import org.example.blogplatform.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/loginform")
    public String showLoginForm() {
        return "user/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password,
                        HttpServletResponse response, Model model) {
        User user = userService.authenticate(username, password);
        if (user != null) {
            Cookie cookie = new Cookie("auth", user.getUsername());
            cookie.setPath("/");
            response.addCookie(cookie);
            return "redirect:/@" + user.getUsername();
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "user/login";
        }
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "user/register";
    }


    @PostMapping("/userreg")
    public String registerUser(@RequestParam String username, @RequestParam String password, HttpServletResponse response,
                               @RequestParam String name, @RequestParam String email, Model model ) {
        User user = userService.authenticate(username,password);
        if (userService.isUsernameTaken(username)) {
            model.addAttribute("error", "Username is already taken");
            return "error";
        }
        if (userService.isEmailTaken(email)) {
            model.addAttribute("error", "Email is already taken");
            return "error";
        }

        userService.registerNewUser(username, password, name, email);
        return "user/welcome";
    }

    @GetMapping("/@{username}")
    public String showUserPage(@PathVariable String username, Model model) {
        String currentUser = UserContext.getUser();
        if(currentUser == null||!currentUser.equals(username)){
            return "redirect:/loginform";
        }
        User user = userService.findByUsername(username);
        model.addAttribute("user", user);
        return "redirect:/";
    }

}



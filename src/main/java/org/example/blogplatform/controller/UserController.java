package org.example.blogplatform.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.example.blogplatform.dto.UserDTO;
import org.example.blogplatform.model.User;
import org.example.blogplatform.repository.UserRepository;
import org.example.blogplatform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


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
    public String loginform(@ModelAttribute("user") User user, HttpServletResponse response) {

        //사용자가 보낸 username과 패스워드가 서버가 원하는 정보랑 일치하는지 확인하고
        //사용자 정보를 유지하게 하면 된다.
        User byUser = userService.findByUsername(user.getUsername());

        if (byUser != null && user.getPassword().equals(byUser.getPassword())) {
            //실제 서비스에서는 아이디 암호를 검사해서 진행하겠지만...   여기서는 간단하게..
            Cookie cookie = new Cookie("auth", user.getUsername());
            cookie.setPath("/");
            cookie.setHttpOnly(true); //자바 스크립트로는 쿠키에 접근할 수 없당~!

            //이렇게 생성된 쿠키는 클라이언에게 보내져야한다.
            response.addCookie(cookie);
            return "redirect:/";
        } else {
            return "redirect:/loginform";
        }

    }

    @GetMapping("/logout")
    public String logout(HttpServletResponse response){
        //쿠키를 삭제하면 로그아웃이 되는데...
        //브라우저의 쿠키는 서버에서 삭제할 수는 없다.
        Cookie cookie = new Cookie("auth", "");
        cookie.setPath("/");
        cookie.setMaxAge(0); //쿠키 유지시간!!
        response.addCookie(cookie);
        return "redirect:/loginform";
    }


    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "user/register";
    }

    @PostMapping("/userreg")
    public String registerUser(@RequestParam String username, @RequestParam String password,
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
        return "user";
    }

}



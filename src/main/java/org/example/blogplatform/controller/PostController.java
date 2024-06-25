package org.example.blogplatform.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.example.blogplatform.model.Post;
import org.example.blogplatform.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/")
    public String showMainPage(Model model) {
        List<Post> posts = postService.getAllPosts();
        model.addAttribute("posts", posts);
        return "blog/blogmain";
    }

    @GetMapping("/postform")
    public String showPostForm(HttpServletRequest request, Model model) {
        model.addAttribute("post", new Post());
        return "blog/postform";
    }

    @PostMapping("/post")
    public String savePost(@ModelAttribute Post post, @RequestParam("publishStatus") boolean publishStatus){
        post.setPublished(publishStatus);
        postService.createOrUpdatePost(post);
        return "redirect:/";
    }

}

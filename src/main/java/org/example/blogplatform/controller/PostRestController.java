package org.example.blogplatform.controller;

import org.example.blogplatform.model.Post;
import org.example.blogplatform.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostRestController {

    @Autowired
    private PostService postService;

    @GetMapping("/api/posts")
    public List<Post> getPosts(@RequestParam(defaultValue="latest")String sort,
                               @RequestParam(defaultValue = "0")int page,
                               @RequestParam(defaultValue="10")int size){
        return postService.getPosts(sort,page,size);
    }

    @DeleteMapping("/api/posts/{id}")
    @ResponseBody
    public String deletePost(@PathVariable Long id){
        postService.deletePost(id);
        return "{\"message\": \"글 삭제가 완료되었습니다.\"}";
    }
}

package org.example.blogplatform.service;

import org.example.blogplatform.model.Post;
import org.example.blogplatform.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public Post createOrUpdatePost(Post post) {
       post.setUpdatedAt(LocalDateTime.now());
       if(post.getCreatedAt() == null) {
           post.setCreatedAt(LocalDateTime.now());
       }
        return postRepository.save(post);
    }

    public Optional<Post> getPost(Long id) {
        return postRepository.findById(id);
    }

    public List<Post> getPosts(String sort, int page, int size) {
        Pageable pageable;
        if ("popular".equalsIgnoreCase(sort)) {
            pageable = PageRequest.of(page, size, Sort.by("likes").descending());
        } else {
            pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        }
        return postRepository.findAll(pageable).getContent();
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}

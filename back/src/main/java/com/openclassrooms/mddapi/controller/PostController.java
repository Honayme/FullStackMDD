package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.PostResponseDTO;
import com.openclassrooms.mddapi.exception.TopicNotFoundException;
import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.service.IPostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller for handling post-related HTTP requests.
 */
@CrossOrigin(origins = "*")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class PostController {

    private final IPostService postService;

    /**
     * Get all posts.
     *
     * @return ResponseEntity with a list of PostResponseDTOs
     */
    @GetMapping("/posts")
    public ResponseEntity<List<PostResponseDTO>> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        List<PostResponseDTO> responseDTOs = posts.stream()
                .map(PostResponseDTO::new)
                .collect(Collectors.toList());
        log.info("Retrieved all posts");
        return ResponseEntity.ok(responseDTOs);
    }

    /**
     * Get a post by its ID.
     *
     * @param id Post ID
     * @return ResponseEntity with PostResponseDTO
     */
    @GetMapping("/posts/{id}")
    public ResponseEntity<PostResponseDTO> getPostById(@PathVariable Long id) {
        Post post = postService.getPostById(id);
        PostResponseDTO responseDTO = new PostResponseDTO(post);
        log.info("Retrieved post with ID: {}", id);
        return ResponseEntity.ok(responseDTO);
    }

    /**
     * Save a new post.
     *
     * @param post Post data to save
     * @return ResponseEntity with the saved PostResponseDTO
     * @throws TopicNotFoundException if the related topic is not found
     */
    @PostMapping("/posts")
    public ResponseEntity<PostResponseDTO> savePost(@RequestBody PostResponseDTO post) throws TopicNotFoundException {
        PostResponseDTO savedPost = postService.savePost(post);
        log.info("Post saved");
        return ResponseEntity.ok(savedPost);
    }
}

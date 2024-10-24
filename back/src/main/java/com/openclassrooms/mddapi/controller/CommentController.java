package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.CommentResponseDTO;
import com.openclassrooms.mddapi.model.Comment;
import com.openclassrooms.mddapi.service.ICommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Controller for handling comment-related HTTP requests.
 */
@CrossOrigin(origins = "*")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class CommentController {

    private final ICommentService commentService;

    /**
     * Get all comments.
     *
     * @return List of all comments as CommentResponseDTO objects.
     */
    @GetMapping("/comments")
    public ResponseEntity<List<CommentResponseDTO>> retrieveAllComments() {
        List<Comment> comments = commentService.getAllComments();
        List<CommentResponseDTO> responseDTOs = comments.stream()
                .map(CommentResponseDTO::new)
                .collect(Collectors.toList());
        log.info("Retrieved all comments");
        return ResponseEntity.ok(responseDTOs);
    }

    /**
     * Get comments by post ID.
     *
     * @param postId ID of the post.
     * @return List of comments for the given post ID.
     */
    @GetMapping("/comments/{postId}")
    public ResponseEntity<List<CommentResponseDTO>> retrieveCommentsByPostId(@PathVariable Long postId) {
        Optional<List<Comment>> comments = commentService.getCommentsByPostId(postId);
        List<CommentResponseDTO> responseDTOs = comments.orElseThrow().stream()
                .map(CommentResponseDTO::new)
                .collect(Collectors.toList());
        log.info("Retrieved comments for post ID {}", postId);
        return ResponseEntity.ok(responseDTOs);
    }

    /**
     * Save a new comment.
     *
     * @param comment Comment details from the request body.
     * @return The saved comment.
     */
    @PostMapping("/comment")
    public ResponseEntity<Comment> saveComment(@RequestBody @Valid CommentResponseDTO comment) {
        Comment savedComment = commentService.saveComment(comment);
        log.info("Saved a new comment");
        return ResponseEntity.ok(savedComment);
    }
}

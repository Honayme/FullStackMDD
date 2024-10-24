package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.CommentResponseDTO;
import com.openclassrooms.mddapi.model.Comment;
import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.CommentRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Service for managing comments.
 */
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Service
public class CommentServiceImpl implements ICommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private IPostService postService;

    @Autowired
    private IUserService userService;

    /**
     * Get a comment by its ID.
     *
     * @param id the ID of the comment
     * @return the comment with the given ID
     */
    @Override
    public Comment getCommentById(Long id) {
        log.info("Get comment by ID");
        return commentRepository.findById(id).get();
    }

    /**
     * Get all comments for a specific post.
     *
     * @param postId the ID of the post
     * @return a list of comments for the post
     */
    @Override
    public Optional<List<Comment>> getCommentsByPostId(Long postId) {
        if (postService.getPostById(postId) == null) {
            log.error("Post not found");
            return Optional.empty();
        }
        log.info("Get comments by post ID");
        return commentRepository.findByPostsId(postId);
    }

    /**
     * Get all comments.
     *
     * @return a list of all comments
     */
    @Override
    public List<Comment> getAllComments() {
        log.info("Get all comments");
        return commentRepository.findAll();
    }

    /**
     * Save a new comment.
     *
     * @param commentDto data for the new comment
     * @return the saved comment
     */
    @Override
    public Comment saveComment(CommentResponseDTO commentDto) {
        log.info("Save new comment");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.getUserByEmail(userDetails.getUsername());
        Post post = postService.getPostById(commentDto.getPostId());

        Comment comment = Comment.builder()
                .description(commentDto.getDescription())
                .author(user)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .posts(post)
                .build();

        commentRepository.save(comment);
        return comment;
    }

    /**
     * Delete a comment if the user is the author.
     *
     * @param id the ID of the comment to delete
     * @return a message indicating the result
     */
    @Override
    public String deleteComment(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.getUserByEmail(userDetails.getUsername());
        Comment comment = commentRepository.findById(id).orElseThrow();

        if (Objects.equals(comment.getAuthor().getId(), user.getId())) {
            commentRepository.deleteById(id);
            log.info("Comment deleted successfully");
            return "Comment with ID " + id + " deleted successfully";
        } else {
            log.error("User is not the author of the comment");
            return "This user is not the author of the comment";
        }
    }
}

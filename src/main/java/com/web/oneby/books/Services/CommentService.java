package com.web.oneby.books.Services;

import com.web.oneby.books.Repositories.CommentRepository;
import com.web.oneby.commons.Services.UserService;
import org.springframework.stereotype.Service;
import com.web.oneby.books.DTOs.CommentRequest;
import com.web.oneby.books.DTOs.CommentResponse;
import com.web.oneby.books.Models.Comment;
import com.web.oneby.commons.Models.User;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class CommentService {

    private CommentRepository commentRepository;
    private UserService userService;

    public CommentService (
            CommentRepository commentRepository,
            UserService userService
    ) {
        this.commentRepository = commentRepository;
        this.userService = userService;
    }

    public Comment add (CommentRequest commentRequest) {
        User user = userService.getById(commentRequest.getUserId());
        return commentRepository.save(new Comment(user, commentRequest.getComment()));
    }

    public Comment update (Long commentId, CommentRequest commentRequest) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new RuntimeException("Comment does not exist!"));
        comment.setComment(commentRequest.getComment());
        comment.setDateTime(LocalDateTime.now(ZoneId.of("Asia/Almaty")));
        return commentRepository.save(comment);
    }

    public void delete(Long commentId){
        commentRepository.deleteById(commentId);
    }
}

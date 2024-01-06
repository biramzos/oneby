package com.web.oneby.Services;

import com.web.oneby.DTO.CommentRequest;
import com.web.oneby.Models.Comment;
import com.web.oneby.Models.User;
import com.web.oneby.Repositories.CommentRepository;
import org.springframework.stereotype.Service;

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

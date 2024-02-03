package com.web.oneby.books.DTOs;

import com.web.oneby.books.Models.Comment;
import com.web.oneby.commons.DTOs.UserResponse;
import com.web.oneby.commons.Utils.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponse {
    private Long id;
    private UserResponse user;
    private String comment;
    private String dateTime;

    public static CommentResponse fromComment(Comment comment, int language) {
        CommentResponse commentResponse = new CommentResponse();
        commentResponse.id = comment.getId();
        commentResponse.user = UserResponse.fromUser(comment.getSender(), language);
        commentResponse.comment = comment.getComment();
        commentResponse.dateTime = DateUtil.getStringDateTimeFromDateTime(comment.getDateTime(), language);
        return commentResponse;
    }

}

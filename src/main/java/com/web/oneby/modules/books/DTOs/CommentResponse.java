package com.web.oneby.modules.books.DTOs;

import com.web.oneby.modules.books.Models.Comment;
import com.web.oneby.commons.Utils.DateUtil;
import com.web.oneby.modules.users.DTOs.UserResponse;
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

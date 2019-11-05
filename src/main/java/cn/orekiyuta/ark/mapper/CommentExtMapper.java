package cn.orekiyuta.ark.mapper;

import cn.orekiyuta.ark.model.Comment;

public interface CommentExtMapper {
    int incCommentCount(Comment comment);
}
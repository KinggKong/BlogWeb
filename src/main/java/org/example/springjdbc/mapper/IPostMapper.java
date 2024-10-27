package org.example.springjdbc.mapper;

import org.example.springjdbc.entity.Post;
import org.example.springjdbc.model.PostResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IPostMapper {
    PostResponse toPostResponse(Post post);
}

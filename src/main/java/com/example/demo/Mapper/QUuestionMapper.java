package com.example.demo.Mapper;

import com.example.demo.Model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QUuestionMapper {

    @Insert("INSERT INTO  Question(title,description,gmt_create,gmt_modified,creator,tag) VALUES (#{title},#{description},#{gmt_create},#{gmt_modified},#{creator},#{tag})")
    public void create(Question question);

}

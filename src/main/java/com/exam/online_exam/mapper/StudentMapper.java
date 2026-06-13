package com.exam.online_exam.mapper;

import com.exam.online_exam.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface StudentMapper {
    @Select("SELECT * FROM student WHERE student_id=#{studentId} AND password=#{password}")
    Student login(String studentId, String password);
}
package com.exam.online_exam.mapper;

import com.exam.online_exam.entity.Teacher;
import org.apache.ibatis.annotations.*;
import java.util.List;
import java.util.Map;

@Mapper
public interface TeacherMapper {

    @Select("SELECT * FROM teacher WHERE teacher_id=#{teacherId} AND password=#{password}")
    Teacher login(@Param("teacherId") String teacherId,
                  @Param("password") String password);

    @Select("SELECT q.question_id AS questionId, q.content, " +
            "CASE q.question_type WHEN 1 THEN '单选' WHEN 2 THEN '多选' WHEN 3 THEN '判断' END AS typeName, " +
            "q.correct_answer AS correctAnswer, q.score " +
            "FROM question q WHERE q.teacher_id=#{teacherId}")
    List<Map<String, Object>> getQuestionsByTeacher(String teacherId);

    @Insert("INSERT INTO question(teacher_id, question_type, content, " +
            "option_a, option_b, option_c, option_d, correct_answer, score) " +
            "VALUES(#{teacherId}, #{questionType}, #{content}, " +
            "#{optionA}, #{optionB}, #{optionC}, #{optionD}, #{correctAnswer}, #{score})")
    void addQuestion(@Param("teacherId") String teacherId,
                     @Param("questionType") int questionType,
                     @Param("content") String content,
                     @Param("optionA") String optionA,
                     @Param("optionB") String optionB,
                     @Param("optionC") String optionC,
                     @Param("optionD") String optionD,
                     @Param("correctAnswer") String correctAnswer,
                     @Param("score") double score);
}
package com.exam.online_exam.mapper;

import org.apache.ibatis.annotations.*;
import java.util.List;
import java.util.Map;

@Mapper
public interface ExamMapper {

    // 查询所有进行中的考试
    @Select("SELECT e.exam_id AS examId, c.course_name AS courseName, " +
            "e.start_time AS startTime, e.duration AS duration " +
            "FROM exam e JOIN course c ON e.course_id = c.course_id " +
            "WHERE e.status = 1")
    List<Map<String, Object>> getActiveExams();

    // 查询某场考试的所有题目
    @Select("SELECT q.question_id AS questionId, q.content, q.question_type AS questionType, " +
            "q.option_a AS optionA, q.option_b AS optionB, " +
            "q.option_c AS optionC, q.option_d AS optionD, q.score " +
            "FROM paper_question pq " +
            "JOIN question q ON pq.question_id = q.question_id " +
            "JOIN exam e ON e.paper_id = pq.paper_id " +
            "WHERE e.exam_id = #{examId} " +
            "ORDER BY pq.question_order")
    List<Map<String, Object>> getQuestionsByExamId(int examId);

    // 提交一道题的答案
    @Insert("INSERT INTO answer_record(student_id, exam_id, question_id, student_answer) " +
            "VALUES(#{studentId}, #{examId}, #{questionId}, #{answer})")
    void submitAnswer(@Param("studentId") String studentId,
                      @Param("examId") int examId,
                      @Param("questionId") int questionId,
                      @Param("answer") String answer);

    // 检查是否已经参加过该考试
    @Select("SELECT COUNT(*) FROM exam_result " +
            "WHERE student_id=#{studentId} AND exam_id=#{examId}")
    int checkAlreadySubmitted(@Param("studentId") String studentId,
                              @Param("examId") int examId);
}
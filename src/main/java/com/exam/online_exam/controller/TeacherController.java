package com.exam.online_exam.controller;

import com.exam.online_exam.entity.Teacher;
import com.exam.online_exam.mapper.TeacherMapper;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TeacherMapper teacherMapper;

    // 教师登录
    @PostMapping("/login")
    public String login(@RequestParam String teacherId,
                        @RequestParam String password,
                        HttpSession session) {
        Teacher teacher = teacherMapper.login(teacherId, password);
        if (teacher != null) {
            session.setAttribute("teacher", teacher);
            return "redirect:/teacher/index";
        }
        return "redirect:/?error=1";
    }

    // 教师首页
    @GetMapping("/index")
    public String index(HttpSession session) {
        if (session.getAttribute("teacher") == null) return "redirect:/";
        return "teacher_index";
    }

    // 题目管理页
    @GetMapping("/questions")
    public String questions(HttpSession session, Model model) {
        Teacher teacher = (Teacher) session.getAttribute("teacher");
        if (teacher == null) return "redirect:/";
        List<Map<String, Object>> questions =
                teacherMapper.getQuestionsByTeacher(teacher.getTeacherId());
        model.addAttribute("questions", questions);
        return "teacher_questions";
    }

    // 添加题目
    @PostMapping("/question/add")
    public String addQuestion(@RequestParam String content,
                              @RequestParam int questionType,
                              @RequestParam String optionA,
                              @RequestParam String optionB,
                              @RequestParam(required = false) String optionC,
                              @RequestParam(required = false) String optionD,
                              @RequestParam String correctAnswer,
                              @RequestParam double score,
                              HttpSession session) {
        Teacher teacher = (Teacher) session.getAttribute("teacher");
        if (teacher == null) return "redirect:/";
        teacherMapper.addQuestion(teacher.getTeacherId(), questionType,
                content, optionA, optionB, optionC, optionD, correctAnswer, score);
        return "redirect:/teacher/questions";
    }
}
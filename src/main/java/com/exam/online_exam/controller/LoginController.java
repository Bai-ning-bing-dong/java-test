package com.exam.online_exam.controller;

import com.exam.online_exam.entity.Student;
import com.exam.online_exam.mapper.StudentMapper;
import com.exam.online_exam.mapper.ExamResultMapper;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import java.util.List;
import java.util.Map;


@Controller
public class LoginController {

    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private ExamResultMapper examResultMapper;

    // 访问登录页
    @GetMapping("/")
    public String index() {
        return "login";
    }

    // 处理登录
    @PostMapping("/login")
    public String login(@RequestParam String studentId,
                        @RequestParam String password,
                        HttpSession session) {
        Student student = studentMapper.login(studentId, password);
        if (student != null) {
            session.setAttribute("student", student);
            return "redirect:/student/index";
        }
        return "redirect:/?error=1";
    }

    @GetMapping("/student/index")
    public String studentIndex(HttpSession session) {
        if (session.getAttribute("student") == null) {
            return "redirect:/";
        }
        return "student_index";
    }

    @GetMapping("/student/results")
    public String results(HttpSession session, Model model) {
        Student student = (Student) session.getAttribute("student");
        if (student == null) return "redirect:/";
        List<Map<String, Object>> results =
                examResultMapper.getResultsByStudentId(student.getStudentId());
        model.addAttribute("results", results);
        return "student_results";
    }
}
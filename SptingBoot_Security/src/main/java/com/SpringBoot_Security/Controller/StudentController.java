package com.SpringBoot_Security.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.SpringBoot_Security.Model.Student;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class StudentController {

	private List<Student> students = new ArrayList<>(List.of(new Student(1, "raaj", 24), new Student(2, "Sai", 30)));

	@GetMapping("/")
	public List<String> main() {
		return List.of("Hello in Spring Security! Your request comes to the Application Server.", "Available URLs:",
				"/addstudent - Add a new student (POST request, expects a Student object).",
				"/students - Get the list of all students.",
				"/csrf-token - Fetch the CSRF token for security purposes.", "/csrf - Fetch the current session ID.");
	}

	@GetMapping("/students")
	public List<Student> getStudent() {
		System.out.println();
		return students;
	}

	// =======================================================================
	/* (fetching the CSRF token). */
	@GetMapping("/csrf-token")
	public CsrfToken getCsrfToken(HttpServletRequest request) {
		System.out.println((CsrfToken) request.getAttribute("_csrf"));
		return (CsrfToken) request.getAttribute("_csrf");
	}

	/* Fetches session ID from the session. */
	@GetMapping("/csrf")
	public String getCsrf(HttpServletRequest request) {
		return "Hello" + request.getSession().getId();
	}

	// =======================================================================

	@PostMapping("/addstudent")
	public Student addStudent(@RequestBody Student student) {
		students.add(student);
		System.out.println(students);
		return student;
	}

}

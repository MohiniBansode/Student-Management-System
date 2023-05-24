package com.example.student.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.student.entity.Student;
import com.example.student.exception.ResourceNotFoundException;
import com.example.student.repository.StudentRepository;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
//@RequestMapping("/Student")
public class StudentController 
{
	//dependency injection
	@Autowired
	private StudentRepository studentRepository;
	
	//rest api
	//get all students
	@GetMapping("/studentsList")
	public List<Student> getAllStudents()
	{
		return studentRepository.findAll();
	}
	
	//create new student
	@PostMapping("/addNewStudent")
	public Student createStudent(@RequestBody Student s)
	{
		return studentRepository.save(s);
	}
	
	//get student by id
	@GetMapping("/getStudentById/{id}")
	public ResponseEntity<Student> getStudentById(@PathVariable Long id)
	{
		Student s = studentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Student does not exists with this id: "+id));
		return ResponseEntity.ok(s);
	}
	
	//update student by id
	@PutMapping("/updateStudent/{id}")
	public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student studentdetails)
	{
		Student s = studentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Student does not exists with this id: "+id));
		
		s.setRollno(studentdetails.getRollno());
		s.setFirstName(studentdetails.getFirstName());
		s.setLastName(studentdetails.getLastName());
		s.setGender(studentdetails.getGender());
		s.setAge(studentdetails.getAge());
		
		Student updatedStudent = studentRepository.save(s);
		return ResponseEntity.ok(updatedStudent);
	}
	
	//delete student by id
	@DeleteMapping("/deleteStudent/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteStudent(@PathVariable Long id)
	{
		Student s = studentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Student does not exists with this id: "+id));
		
		studentRepository.delete(s);
		Map<String, Boolean> map = new HashMap<>();
		map.put("Student record has been deleted", Boolean.TRUE);
		return ResponseEntity.ok(map);
	}
	
}

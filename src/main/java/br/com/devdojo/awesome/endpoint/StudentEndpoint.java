package br.com.devdojo.awesome.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.devdojo.awesome.model.Student;
import br.com.devdojo.error.CustomErrorType;
import br.com.devdojo.repository.StudentRepository;

@RestController
@RequestMapping("students")
public class StudentEndpoint {
	
	private final StudentRepository studentDao;
	
	@Autowired
	private StudentEndpoint(StudentRepository studentDao) {
		this.studentDao = studentDao;
	}
	
	@GetMapping(path = "/list")
	public ResponseEntity<?> listAll() {
		
		return new ResponseEntity<>(studentDao.findAll(), HttpStatus.OK);
		
	}
	
	@GetMapping(path = "/{id}")
	public ResponseEntity<?> getStudentById(@PathVariable("id") Long id) {
		
		Student student = studentDao.findById(id).get();
		
		if(student == null) {
			return new ResponseEntity<>(new CustomErrorType("Student not found"), HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(student, HttpStatus.OK);
		
	}
	
	@PostMapping
	public ResponseEntity<?> saveStudent(@RequestBody Student student) {		
		return new ResponseEntity<>(studentDao.save(student), HttpStatus.OK);
	}
	
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<?> deleteStudent(@PathVariable Long id) {
		studentDao.deleteById(id);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PutMapping
	public ResponseEntity<?> updateStudent(@RequestBody Student student) {
		studentDao.save(student);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
}

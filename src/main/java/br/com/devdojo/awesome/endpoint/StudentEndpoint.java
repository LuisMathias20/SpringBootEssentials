package br.com.devdojo.awesome.endpoint;

import java.util.List;

import javax.validation.Valid;

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
import br.com.devdojo.dto.StudentDTO;
import br.com.devdojo.error.ResourceNotFoundException;
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
	public ResponseEntity<Iterable<Student>> listAll() {
		
		return new ResponseEntity<>(studentDao.findAll(), HttpStatus.OK);
	}
	
	@GetMapping(path = "/{id}")
	public ResponseEntity<Student> getStudentById(@PathVariable("id") Long id) {		
		verifyIfStudentExists(id);
		
		Student student = studentDao.findById(id).orElse(new Student());
		
		return new ResponseEntity<>(student, HttpStatus.OK);	
	}
	
	@GetMapping(path = "/findByName/{name}")
	public ResponseEntity<List<Student>> findStudentByName(@PathVariable String name) {		
		return new ResponseEntity<>(studentDao.findByName(name), HttpStatus.OK);
	}
	
	@GetMapping(path = "/findByNameContaining/{name}")
	public ResponseEntity<List<Student>> findByNameIgnoreCaseContaining(@PathVariable String name) {		
		return new ResponseEntity<>(studentDao.findByNameIgnoreCaseContaining(name), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Student> saveStudent(@Valid @RequestBody StudentDTO studentParam) {
		Student persistStudent = new Student();
		
		return new ResponseEntity<>(studentDao.save(persistStudent.convertFromDto(studentParam)), HttpStatus.OK);
	}
	
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<HttpStatus> deleteStudent(@PathVariable Long id) {
		verifyIfStudentExists(id);
		
		studentDao.deleteById(id);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PutMapping
	public ResponseEntity<HttpStatus> updateStudent(@RequestBody StudentDTO studentDto) {
		verifyIfStudentExists(studentDto.getId());
		
		Student persistStudent = new Student();
		
		studentDao.save(persistStudent.convertFromDto(studentDto));
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	private void verifyIfStudentExists(Long id) {
		if(studentDao.findById(id).isEmpty()) {
			throw new ResourceNotFoundException("Student not found for Id: "+id);
		}
	}
}

package br.com.devdojo.awesome.endpoint;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.devdojo.awesome.model.Student;
import br.com.devdojo.awesome.util.DateUtil;
import br.com.devdojo.error.CustomErrorType;

@RestController
@RequestMapping("students")
public class StudentEndpoint {

	private final DateUtil dateUtil;
	
	@Autowired
	private StudentEndpoint(DateUtil dateUtil) {
		this.dateUtil = dateUtil;
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/list")
	public ResponseEntity<?> listAll() {
		
		return new ResponseEntity<>(Student.studentList, HttpStatus.OK);
		
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/localDateTime")
	public ResponseEntity<?> printLocalDateTime() {
		
		String dateTimeNow = dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now());
		
		return new ResponseEntity<>(dateTimeNow, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/{id}")
	public ResponseEntity<?> getStudentById(@PathVariable("id") int id) {
		
		Student student = new Student();
		student.setId(id);
		
		int index = Student.studentList.indexOf(student);
		
		if(index == -1) {
			return new ResponseEntity<>(new CustomErrorType("Student not found"), HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(Student.studentList.get(index), HttpStatus.OK);
		
	}
}
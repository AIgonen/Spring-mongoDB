package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import org.springframework.data.mongodb.core.MongoTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(StudentRepository repository, MongoTemplate mongoTemplate) {
		return args -> {
			Address address = new Address("England", "London", "NE98");
			String email = "post@post.com";
			Student student = new Student("Toomas",
					"Tompson",
					email,
					Gender.MALE,
					address,
					List.of("CS", "Math"),
					BigDecimal.TEN,
					LocalDateTime.now());

			//useMongoTemplateAndQuery(repository, mongoTemplate, email, student);

			repository.findStudentByEmail(email)
					.ifPresentOrElse(s-> {
						System.out.println(s + " already exist");
					}, ()-> {
						repository.insert(student);
						System.out.println("Inserting student " + student);
					});
		};
	}

	// private void useMongoTemplateAndQuery(StudentRepository repository, MongoTemplate mongoTemplate, String email, Student student) {
	// 	Query query = new Query();
	// 	query.addCriteria(Criteria.where("email").is(email));
	//
	// 	List<Student> students = mongoTemplate.find(query, Student.class);
	//
	// 	if (students.size() > 1) {
	// 		throw new IllegalStateException("found namy students with email" + email);
	// 	}
	// 	if (students.isEmpty()) {
	// 		repository.insert(student);
	// 	}
	// }
}

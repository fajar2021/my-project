package org.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.model.Stuff;
import org.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class ProjectController {

	@Autowired
	ProjectRepository projectRepo;
	
	@GetMapping("/project")
	public ResponseEntity<List<Stuff>> getAllCoba(@RequestParam(required = false) String title) {
		try {
			List<Stuff> stuff = new ArrayList<Stuff>();

			if (title == null)
				projectRepo.findAll().forEach(stuff::add);
			else
				projectRepo.findByTitleContaining(title).forEach(stuff::add);

			if (stuff.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(stuff, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/project/{id}")
	public ResponseEntity<Stuff> getCobaById(@PathVariable("id") long id) {
		Optional<Stuff> tutorialData = projectRepo.findById(id);

		if (tutorialData.isPresent()) {
			return new ResponseEntity<>(tutorialData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/project")
	public ResponseEntity<Stuff> createCoba(@RequestBody Stuff stuff) {
		try {
			Stuff _stuff = projectRepo.save(new Stuff(stuff.getTitle(), stuff.getDescription(), false));
			return new ResponseEntity<>(_stuff, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/project/{id}")
	public ResponseEntity<Stuff> updateCoba(@PathVariable("id") long id, @RequestBody Stuff stuff) {
		Optional<Stuff> tutorialData = projectRepo.findById(id);

		if (tutorialData.isPresent()) {
			Stuff _stuff = tutorialData.get();
			_stuff.setTitle(stuff.getTitle());
			_stuff.setDescription(stuff.getDescription());
			_stuff.setPublished(stuff.isPublished());
			return new ResponseEntity<>(projectRepo.save(stuff), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/project/{id}")
	public ResponseEntity<HttpStatus> deleteCoba(@PathVariable("id") long id) {
		try {
			projectRepo.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/project")
	public ResponseEntity<HttpStatus> deleteAllCobas() {
		try {
			projectRepo.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/project/published")
	public ResponseEntity<List<Stuff>> findByPublished() {
		try {
			List<Stuff> tutorials = projectRepo.findByPublished(true);

			if (tutorials.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(tutorials, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}

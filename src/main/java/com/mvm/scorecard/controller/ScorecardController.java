package com.mvm.scorecard.controller;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mvm.scorecard.exception.ResourceNotFoundException;
import com.mvm.scorecard.model.Scorecard;
import com.mvm.scorecard.repository.ScorecardRepository;

@Controller
@RequestMapping("/golf")

public class ScorecardController {

	@Autowired
	ScorecardRepository scorecardRepository;

	// Get All Notes
	// Get All Notes
	@GetMapping("/scorecards")
	public String getAllScorecards(Model model) {
		model.addAttribute("scorecards", scorecardRepository.findAll());
		System.out.println("getting cards");
		return "scorecards";
		// return scorecardRepository.findAll();
	}

	@GetMapping("/scorecard/new")
	public String newScorecard(Model model) {
		model.addAttribute("scorecard", new Scorecard());
		return "scorecardform";
	}

	// Create a new Note
	// Create a new Note
	@PostMapping("/scorecards")
	public String createNote(@Valid @RequestParam Long id, String date_played, String course, String score) {
		Scorecard sc = new Scorecard();
		sc.setCourse(course);
		sc.setScore(Integer.parseInt(score));
		sc.setId(id);
		Date newdate = new Date();
		sc.setDate_played(newdate);
		System.out.println("Score:" + id + "  " + date_played);
		scorecardRepository.save(sc);
		return "redirect:/golf/scorecard/" + sc.getId();
	}

	// Get a Single Note
	// Get a Single Note
	@GetMapping("/scorecard/{id}")
	public String getNoteById(@PathVariable(value = "id") Long scoreId, Model model) {
		// return scorecardRepository.findById(scoreId)
		// .orElseThrow(() -> new ResourceNotFoundException("Scorecard", "id",
		// scoreId));
		System.out.println("Getting Score=" + scoreId);
		model.addAttribute("scorecard", scorecardRepository.findById(scoreId).orElse(new Scorecard()));
		return "scorecard";

	}

	// Update a Note
	// Update a Note
	@PostMapping("/update/scorecard")
	public String updateNote(@Valid @RequestParam Long id, String date_played, String course, String score) {

		Scorecard sc = new Scorecard();
		sc.setCourse(course);
		sc.setScore(Integer.parseInt(score));
		sc.setId(id);
		Date newdate = new Date();
		sc.setDate_played(newdate);
		System.out.println("Score:" + id + "  " + date_played);
		scorecardRepository.save(sc);
		return "redirect:/golf/scorecard/" + sc.getId();
	}

	/**
	 * Load the edit widget page for the widget with the specified ID.
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@GetMapping("/scorecard/edit/{id}")
	public String editWidget(@PathVariable Long id, Model model) {
		model.addAttribute("scorecard", scorecardRepository.findById(id).orElse(new Scorecard()));
		return "scorecardform";
	}

	// Delete a Note
	@GetMapping("/scorecard/delete/{id}")
	public String deleteNote(@PathVariable(value = "id") Long scoreId) {
		Scorecard note = scorecardRepository.findById(scoreId)
				.orElseThrow(() -> new ResourceNotFoundException("Note", "id", scoreId));

		scorecardRepository.delete(note);

		// return ResponseEntity.ok().build();

		return "redirect:/golf/scorecards";

	}
}

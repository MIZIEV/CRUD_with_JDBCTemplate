package edu.app.controllers;

import edu.app.dao.PersonDAO;
import edu.app.models.Person;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PersonDAO personDAO;

    public PeopleController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping
    public String getAllPeople(Model model) {
        model.addAttribute("people", personDAO.getAllPeople());
        return "peoplePages/index";
    }

    @GetMapping("/{id}")
    public String getConcretePerson(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personDAO.getConcretePerson(id));
        model.addAttribute("books", personDAO.getBooksByPersonId(id));
        return "peoplePages/concretePerson";
    }

    @GetMapping("/addNewPerson")
    public String addNewPerson(Model model) {
        model.addAttribute("person", new Person());
        return "peoplePages/newPerson";
    }

    @PostMapping
    public String saveNewPerson(@ModelAttribute("person") @Valid Person person, BindingResult error) {
        if (error.hasErrors()) {
            return "peoplePages/newPerson";
        } else {
            personDAO.addNewPerson(person);
            return "redirect:/people";
        }
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personDAO.getConcretePerson(id));
        return "peoplePages/editPerson";
    }

    @PatchMapping("/{id}")
    public String editPerson(@PathVariable("id") int id,
                             @ModelAttribute("person") @Valid Person person, BindingResult error) {
        if (error.hasErrors()) {
            return "peoplePages/editPerson";
        } else {
            personDAO.update(id, person);
            return "redirect:/people";
        }
    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable("id") int id) {
        personDAO.deletePerson(id);
        return "redirect:/people/";
    }
}
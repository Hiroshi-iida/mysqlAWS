package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

	@Autowired
	UserRepository userRepository;
	@Autowired
	CommentRepository commentRepository;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView indexGet(ModelAndView mv) {
		List<User> u = userRepository.findAll();
		List<Comment> c = commentRepository.findAll();

		mv.addObject("user", u);
		mv.addObject("comment", c);
		mv.setViewName("index");
		return mv;
	}

	@RequestMapping(value = "/user", method = RequestMethod.POST)
	public ModelAndView user(@ModelAttribute("formModel") User user, ModelAndView mv) {
		userRepository.saveAndFlush(user);
		return new ModelAndView("redirect:/");
	}

	@RequestMapping(value = "/comment", method = RequestMethod.POST)
	public ModelAndView comment(@ModelAttribute("formModel") Comment comment, ModelAndView mv) {
		comment.setUser(userRepository.findById((long) 1).get());
		commentRepository.saveAndFlush(comment);
		return new ModelAndView("redirect:/");
	}
	
	@RequestMapping("/c/{id}")
	public ModelAndView c(@PathVariable("id")long id, ModelAndView mv) {
		List<User> u = userRepository.findAll();
		Optional<User> x = userRepository.findById(id);
		List<Comment> c = commentRepository.findByUser(x);

		mv.addObject("user", u);
		mv.addObject("comment", c);
		mv.setViewName("index");
		return mv;
	}
	@RequestMapping("/d/{id}")
	public ModelAndView d(@PathVariable("id")long id, ModelAndView mv) {
		List<User> u = userRepository.findAll();
//		Optional<User> x = userRepository.findById(id);
		List<Comment> c = commentRepository.findByUser(userRepository.findById(id).get());
		
		mv.addObject("user", u);
		mv.addObject("comment", c);
		mv.setViewName("index");
		return mv;
	}

	@GetMapping("/uadd/{name}")
	public ModelAndView addNewUser(@PathVariable String name, ModelAndView mv) {

		User n = new User();
		n.setName(name);
		n.setEmail("a.com");
//	    n.setAge(18);
		userRepository.saveAndFlush(n);
		mv.setViewName("index");
		return mv;
	}

	@GetMapping("/cadd/{name}")
	public ModelAndView caddNewUser(@PathVariable String name, ModelAndView mv) {

		Comment n = new Comment();
		n.setText(name);
		n.setUser(userRepository.findById((long) 1).get());
		
		commentRepository.saveAndFlush(n);
		mv.setViewName("index");
		return mv;
	}
	@GetMapping("/cadd/{name}/{id}")
	public ModelAndView caddNewUser(@PathVariable String name, @PathVariable long id ,ModelAndView mv) {
		
		Comment n = new Comment();
		n.setText(name);
		n.setUser(userRepository.findById(id).get());
		
		commentRepository.saveAndFlush(n);
		mv.setViewName("index");
		return mv;
	}

}

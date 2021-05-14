package com.example.demo.controller;

import java.util.Optional;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Accesslist;
import com.example.demo.model.Board;
import com.example.demo.repository.BoardRepository;
import com.example.demo.repository.AccesslistRepository;

@Controller
public class BoardController {
	
	@Autowired
	BoardRepository repository;
	
	@Autowired
	AccesslistRepository AccesslistRepository;
	
	//認証成功後ホーム画面
	@GetMapping("/")
	public String home(Authentication loginUser,@ModelAttribute Board board,Model model,@ModelAttribute Accesslist Accesslist,Model Accessmodel) {
		
		model.addAttribute("comments",repository.findAll());
		
		Accesslist.setDatetime(new Date());
		Accesslist.setUsername(loginUser.getName());
		Accesslist.setAction("login");
		AccesslistRepository.saveAndFlush(Accesslist);
		
		return "list";
	}
	
	@GetMapping("/list")
	public String list(Authentication loginUser,@ModelAttribute Board board,Model model) {
		
		model.addAttribute("comments",repository.findAll());
		return "list";
	}
	
	@GetMapping("/accesslist")
	public String Accesslist(@ModelAttribute Accesslist Accesslist,Model model) {
		
		Iterable<Accesslist> access = AccesslistRepository.findAll();
		model.addAttribute("lists",access);
		return "accesslist";
	}
	
	//新規登録へ遷移
	@GetMapping("/add")
	public String moveAddComment(@ModelAttribute Board board,Model model) {
		
		
		model.addAttribute("comments",repository.findAll());
		return "new";
	}
	
	//既存コメント編集画面へ遷移
	@GetMapping("/edit/{id}")
	public String moveEditComment(@ModelAttribute Board board,@PathVariable Long id,Model model) {
		
		Optional<Board> data = repository.findById(id);
		model.addAttribute("comments",data.get());
		return "edit";		
	}
	
	//既存コメントの削除
	@GetMapping("/delete/{id}")
	public String deletetComment(@PathVariable Long id,Model model) {
		
		repository.deleteById(id);
		return "redirect:/";
	}
	
	@PostMapping("/add")
	public String addComment(Authentication loginUser,@Validated @ModelAttribute Board board,BindingResult result,Model model) {
		
		board.setDatetime(new Date());
		board.setCrateUser(loginUser.getName());
		model.addAttribute("comments",repository.findAll());
		if(result.hasErrors()) {
			return "list";
		}
		
		repository.save(board);
		
		return "redirect:/";
				
	}
	
	@PostMapping("/search")
	public String moveSearchResult(@RequestParam("searchtext")String str,@ModelAttribute Board board,Model model) {
		
		model.addAttribute("comments",repository.findByContentLike("%" + str + "%"));
		return "searchlist";
	}

}

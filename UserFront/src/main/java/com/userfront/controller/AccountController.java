package com.userfront.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.userfront.domain.PrimaryAccount;
import com.userfront.domain.SavingsAccount;
import com.userfront.domain.User;
import com.userfront.service.AccountService;
import com.userfront.service.UserService;

@Controller
@RequestMapping("/account")
public class AccountController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	AccountService accountService;
	
	@RequestMapping("/primaryAccount")
	public String primaryAccount(Model model, Principal principal) {
		
		User user = userService.findByUsername(principal.getName());
		PrimaryAccount primaryAccount = user.getPrimaryAccount();
		
		model.addAttribute("primaryAccount",primaryAccount);
		return "primaryAccount";
	}
	
	@RequestMapping("/savingsAccount")
	public String savingsAccount(Model model, Principal principal) {
		
		User user = userService.findByUsername(principal.getName());
		SavingsAccount savingsAccount = user.getSavingsAccount();
		
		model.addAttribute("savingsAccount",savingsAccount);
		return "savingsAccount";
	}
	
	@RequestMapping(value = "/deposit", method = RequestMethod.GET)
	public String deposit(Model model) {
		model.addAttribute("accountType", "");
		model.addAttribute("amount", "");
		
		return "deposit"; //deposit.html
		
	}
	
	@RequestMapping(value = "/deposit", method = RequestMethod.POST)
	public String depositPOST(@ModelAttribute("amount") String amount, @ModelAttribute("accountType") String accountType, Principal principal) {
		
		accountService.deposit(accountType, Double.parseDouble(amount), principal);
		
		return "redirect:/userFront";
	}
	
	@RequestMapping(value = "/withdraw", method = RequestMethod.GET)
	public String withdraw(Model model) {
		
		return "withdraw"; //deposit.html
	}
	
	

}

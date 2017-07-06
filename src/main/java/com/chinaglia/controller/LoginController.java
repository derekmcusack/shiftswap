package com.chinaglia.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.chinaglia.model.SwapOrig;
import com.chinaglia.model.User;
import com.chinaglia.service.SwapService;
import com.chinaglia.service.UserService;

//implementing ErrorController to handle navigation errors better (404 etc)
@Controller
public class LoginController implements ErrorController {
	
    private static final String PATH = "/error";
	
	@Autowired
	private UserService userService;
	@Autowired
	private SwapService swapService;

	//Serves Login page/view	
	@RequestMapping(value={"/", "/login"}, method = RequestMethod.GET)
	public ModelAndView login(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");
		return modelAndView;
	}

	//Gets Data From Swap Request Table	
//	@RequestMapping(value={"/viewswaps"}, method = RequestMethod.GET)
//	public String viewSwaps(Model model){
//		model.addAttribute("viewswap", swapService.listAllSwaps());	
//		return "viewswaps/list";
//	}		
	
	//Serves View Swaps page/view
	@RequestMapping(value={"/viewswaps"}, method = RequestMethod.GET)
	public ModelAndView viewSwaps(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("viewswaps", swapService.listAllSwaps());
		return modelAndView;
	}
	
	
	//Serves Request a Swap page/view	
	@RequestMapping(value={"/requestaswap"}, method = RequestMethod.GET)
	public ModelAndView requestASwap(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("user", new User());
		modelAndView.addObject("swaporig", new SwapOrig());
		modelAndView.setViewName("requestaswap");
		return modelAndView;
	}	
	
	//Request a Swap - on submit
	@RequestMapping(value = "/requestaswap", method = RequestMethod.POST)
	public ModelAndView createNewSwapRequest(@Valid SwapOrig swaporig, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		swapService.saveSwap(swaporig);
		modelAndView.addObject("successMessage", "Success! You have requested a shift swap!");
		modelAndView.addObject("swaporig", new SwapOrig());
		modelAndView.setViewName("requestaswap");
		return modelAndView;
	}	
	
	//Serves User Registration page/view
	@RequestMapping(value="/registration", method = RequestMethod.GET)
	public ModelAndView registration(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("user", new User());
		modelAndView.setViewName("registration");
		return modelAndView;
	}
	
	//User registration - on submit
	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		User userExists = userService.findUserByEmail(user.getEmail());
		if (userExists != null) {
			bindingResult
					.rejectValue("email", "error.user",
							"Sorry! A user has already registered using that email!");
		}
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("registration");
		} else {
			userService.saveUser(user);
			modelAndView.addObject("successMessage", "Success! You are now registered on the system!");
			modelAndView.addObject("user", new User());
			modelAndView.setViewName("registration");
			
		}
		return modelAndView;
	}
	
	//To Handle Login Process for ADMIN USERS
	@RequestMapping(value="/admin/home", method = RequestMethod.GET)
	public ModelAndView home(){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		modelAndView.addObject("userName", "Welcome " + user.getName() + " " + user.getLastName() + " (ADMIN) " + " (" + user.getEmail() + ")");
		modelAndView.addObject("adminMessage","Admin Users Page");
		modelAndView.setViewName("admin/home");
		return modelAndView;
	}
	
	//To Handle Login Process based on whether user or admin
	@RequestMapping(value="/home", method = RequestMethod.GET)
	public ModelAndView homeUser(){
		ModelAndView modelAndView = new ModelAndView();
		//what role is the user?
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		//if user is Admin
		if(auth.getAuthorities().toString().equals("[ADMIN]")){
			modelAndView = new ModelAndView("redirect:/admin/home");
		} else {
			modelAndView.addObject("userName", "Welcome " + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
			modelAndView.addObject("userMessage","Normal Users Page");
			modelAndView.setViewName("/home");
		}
		return modelAndView;
	}	
	
	//error handling
    @RequestMapping(value = PATH)
    public String error() {
        return "We have a navigation problem";
    }
    
	//error handling
    @Override
    public String getErrorPath() {
        return PATH;
    }	
}
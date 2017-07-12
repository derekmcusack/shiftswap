package com.chinaglia.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.chinaglia.model.ShiftSwap;
import com.chinaglia.model.SwapOrig;
import com.chinaglia.model.User;
import com.chinaglia.repository.SwapRepository;
import com.chinaglia.service.ShiftSwapService;
import com.chinaglia.service.SwapService;
import com.chinaglia.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//implementing ErrorController to handle navigation errors better (404 etc)
@Controller
public class AppController implements ErrorController {
	
    private static final String PATH = "/error";
    
    static final Logger LOG = LoggerFactory.getLogger(AppController.class);
	
	@Autowired
	private UserService userService;
	@Autowired
	private SwapService swapService;
	@Autowired
	private ShiftSwapService shiftSwapService;	
	@Autowired
	SwapRepository swapRepo;

	//Serves Login page/view	
	@RequestMapping(value={"/", "/login"}, method = RequestMethod.GET)
	public ModelAndView login(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");
		return modelAndView;
	}	
	
	//Serves View Swaps page/view
	@RequestMapping(value={"/viewswaps"}, method = RequestMethod.GET)
	public ModelAndView viewSwaps(){
		ModelAndView modelAndView = new ModelAndView();
		//get a list of currently available swaps from SwapOrig entity via swap service
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
		modelAndView.addObject("successMessage", "Success! Your shift swap request has been published!");
		modelAndView.addObject("swaporig", new SwapOrig());
		modelAndView.setViewName("requestaswap");
		return modelAndView;
	}	
	
	//Serves Accept a Swap page/view	
	@RequestMapping(value={"/acceptswap"}, method = RequestMethod.GET)
	public ModelAndView acceptASwap(@RequestParam(value = "id", required =   
			false) String swapid, HttpServletRequest request){
		ModelAndView modelAndView = new ModelAndView();
		//add original swapid to the session
		request.getSession().setAttribute("origswapid", swapid);
		modelAndView.addObject("shiftSwap", new ShiftSwap());
		modelAndView.addObject("user", new User());
		modelAndView.setViewName("acceptswap");
		return modelAndView;
	}	

	@RequestMapping(value={"/acceptswap"}, method = RequestMethod.POST)
	public ModelAndView acceptSwap(@Valid ShiftSwap shiftSwap, HttpServletRequest request,
			BindingResult bindingResult){
		ModelAndView modelAndView = new ModelAndView();
		//retrieve the swapid of the initiated swap request
		String swaporig = (String) request.getSession().getAttribute("origswapid");	
		//set this id in the object
		shiftSwap.setSwapOrigId(Integer.valueOf(swaporig));
		//run query via Repository class to get email of user who initiated swap request
		String emailToSendTo = swapRepo.findUsersEmail(Integer.valueOf(swaporig));
		//save the shift swap, passing in the object and the email of the originator
		shiftSwapService.saveShiftSwap(shiftSwap, emailToSendTo);
		modelAndView.addObject("successMessage", 	
				"Your offer of acceptance has been received and your colleague was notified!");
		modelAndView.addObject("user", new User());		
		modelAndView.setViewName("acceptswap");
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
package com.chinaglia.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.chinaglia.model.ShiftSwap;
import com.chinaglia.model.SwapOrig;
import com.chinaglia.model.User;
import com.chinaglia.repository.SwapRepository;
import com.chinaglia.service.MyMailService;
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
	private MyMailService mailService;
	@Autowired
	SwapRepository swapRepo;
	@Autowired
	HttpServletRequest request;	

	
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
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		//get a list of currently available swaps from SwapOrig entity via swap service
		
		List<SwapOrig> swaps = swapService.listAvailableSwaps(email);
		modelAndView.addObject("viewswaps", swaps);
		return modelAndView;
	}	
	
	//Serves View Swaps page/view
	@RequestMapping(value={"/myswaps"}, method = RequestMethod.GET)
	public ModelAndView mySwaps(){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();		
		String userEmail = auth.getName();
		//get a list of user's swaps and add to model and view
		List<SwapOrig> mySwaps = swapService.listMySwaps(userEmail);
		if(mySwaps !=null){
		modelAndView.addObject("myswaps", mySwaps);
		}
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
		modelAndView.addObject("successMessage", 
				"Success! Your shift swap request has been published!<br /><strong><a href='/home'>HOME</a></strong>");
		modelAndView.addObject("swaporig", new SwapOrig());
		modelAndView.setViewName("requestaswap");
		return modelAndView;
	}	
	
	//Serves Accept a Swap page/view	
	@RequestMapping(value={"/acceptswap"}, method = RequestMethod.GET)
	public ModelAndView acceptASwap(@RequestParam(value = "id", required =   
			false) String swapid, HttpServletRequest request){

		SwapOrig swapOrig = swapService.getSwapOrigById(Integer.valueOf(swapid));	
		ModelAndView modelAndView = new ModelAndView();
		//add original swapid to the session
		request.getSession().setAttribute("origswapid", swapid);	
		modelAndView.addObject("swapOrig", swapOrig);
		modelAndView.addObject("user", new User());
		modelAndView.setViewName("acceptswap");
		return modelAndView;
	}	

	@RequestMapping(value={"/acceptswap"}, method = RequestMethod.POST)
	public ModelAndView acceptSwap(SwapOrig swapOrig, HttpServletRequest request,
			BindingResult bindingResult){
		ModelAndView modelAndView = new ModelAndView();
		//retrieve the swapid of the initiated swap request
		String swaporigid = (String) request.getSession().getAttribute("origswapid");			
		modelAndView.addObject(swapOrig);		
		String shiftDetails = swapOrig.getDate() + ", From " + swapOrig.getStartTime() + " to " 
				+swapOrig.getFinishTime();
		//run query via Repository class to get email of user who initiated swap request
		String emailToSendTo = swapRepo.findUsersEmail(Integer.valueOf(swaporigid));
		//save the shift swap, passing in the object and the email of the originator
		swapService.saveShiftSwap(swapOrig, emailToSendTo, shiftDetails);
		modelAndView.addObject("successMessage", 	
		"Your offer of acceptance has been received and your colleague was notified!<br /><strong><a href='/home'>HOME</a></strong>");
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
			modelAndView.addObject("successMessage", "Success! You are now registered on the system!<br /><strong><a href='/'>LOGIN?</a></strong>");
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
		List<SwapOrig> confirmedSwaps = swapService.returnConfirmedSwaps();
		modelAndView.addObject("myswaps", confirmedSwaps);
		modelAndView.setViewName("admin/home");
		return modelAndView;
	}
	
	//To Handle Login Process based on whether user or admin
	@RequestMapping(value="/home", method = RequestMethod.GET)
	public ModelAndView homeUser(){
		ModelAndView modelAndView = new ModelAndView();
		//retrieve user details from Spring Security
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		String email = user.getEmail();
		//update triggers in database so user can't accept own swaps or
		//confirm swaps which were already confirmed
		swapService.updateUsersSwapStatus(email);	
		
		//update database trigger fields for swaps which user is not involved in
		swapService.updateOtherSwapStatus(email);
		
		//if user is Admin
		if(auth.getAuthorities().toString().equals("[ADMIN]")){
			modelAndView = new ModelAndView("redirect:/admin/home");
		} else {
			modelAndView.addObject("userName", "Welcome " + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
			modelAndView.addObject("userMessage","Normal Users Page");
			modelAndView.setViewName("home");
		}
		return modelAndView;
	}	
	
	//To handle process of user confirming a swap
	@RequestMapping(value = "/confirmswap", method = RequestMethod.GET)
	public ModelAndView afterConfirmSwap(@Valid SwapOrig swapOrig, BindingResult bindingResult,
			@RequestParam(value="id", required=true) String id) throws UnsupportedEncodingException {
		ModelAndView modelAndView = new ModelAndView();
		int intId = Integer.valueOf(id);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();		
		String email = auth.getName();
		SwapOrig origSwap = swapRepo.getOne(intId);
		boolean isOriginator = swapService.isUserOriginator(intId, email);
		if(isOriginator){
			origSwap.setConfirmed(1);
			request.getSession().setAttribute("Cid"+String.valueOf(origSwap.getId()), "isConf");
		} else {
			origSwap.setSwapConfirmed(1);
			request.getSession().setAttribute("Cid"+String.valueOf(origSwap.getId()), "isConf");			
		}
		swapRepo.save(origSwap);
		
//		try{
//			String emailToSendTo;
//			String shiftDetails;
//			if(isOriginator){
//				emailToSendTo = origSwap.getSwappersEmail();
//				shiftDetails = origSwap.getDate() + " " + origSwap.getStartTime() +
//							" " + origSwap.getFinishTime();								
//			}else{
//				emailToSendTo = origSwap.getEmail();
//				shiftDetails = origSwap.getSwapDate() + " " + origSwap.getSwapStartTime() +
//						" " + origSwap.getSwapFinishTime();	
//			}
//			mailService.sendConfirmedEmail(emailToSendTo,shiftDetails);
//		}catch(UnsupportedEncodingException e){
//			LOG.error("mail not sent");
//		}

		List<SwapOrig> mySwaps = swapService.listMySwaps(email);
		if(mySwaps !=null){
		modelAndView.addObject("myswaps", mySwaps);
		}		
		modelAndView.setViewName("myswaps");		
		return modelAndView;
	}		
	
	//To handle process of admin approving a swap
	@RequestMapping(value = "/approveswap", method = RequestMethod.GET)
	public ModelAndView approveASwap(@Valid SwapOrig swapOrig, BindingResult bindingResult,
			@RequestParam(value="id", required=true) String id) throws UnsupportedEncodingException {
		ModelAndView modelAndView = new ModelAndView();
		int intId = Integer.valueOf(id);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();		
		User user = userService.findUserByEmail(auth.getName());	
		SwapOrig swapToBeApproved = swapRepo.getOne(intId);
		//extra validation to ensure that swap has been confirmed 
		if(swapToBeApproved.getConfirmed() == 1 && swapToBeApproved.getSwapConfirmed() == 1){
			swapToBeApproved.setApprovedBy(user.getEmail());
			swapRepo.save(swapToBeApproved);
		} else {
			LOG.error("Seems to be a problem: is swap " + swapToBeApproved.getId() + " approved? ");
		}
		
//			try{
//				String firstEmail = swapToBeApproved.getEmail();
//				String otherEmail = swapToBeApproved.getSwappersEmail();
//				String shiftDetails = "Shift Date: " + swapToBeApproved.getDate() +
//						", From " + swapToBeApproved.getStartTime() + " to " + swapToBeApproved.getFinishTime() +
//						"\r\nSwap Shift Date: " + swapToBeApproved.getSwapDate() +
//						", From " + swapToBeApproved.getSwapStartTime() + " to " + swapToBeApproved.getSwapFinishTime() +
//						"\r\n\r\n";
//				mailService.sendApprovedEmail(firstEmail, otherEmail, shiftDetails);
//		}catch(UnsupportedEncodingException e){
//			LOG.error("mail not sent");
//		}
			
		List<SwapOrig> confirmedSwaps = swapService.returnConfirmedSwaps();
		modelAndView.addObject("myswaps", confirmedSwaps);
		modelAndView.addObject("userName", "Welcome " + user.getName() + " " + user.getLastName() + " (ADMIN) " + " (" + user.getEmail() + ")");
		modelAndView.addObject("adminMessage","Admin Users Page");		
		modelAndView.setViewName("admin/home");
		return modelAndView;
	}
	
	//To handle process of admin disApproving a swap
	@RequestMapping(value = "/disapproveswap", method = RequestMethod.GET)
	public ModelAndView disapproveASwap(@Valid SwapOrig swapOrig, BindingResult bindingResult,
			@RequestParam(value="id", required=true) String id) throws UnsupportedEncodingException {
		ModelAndView modelAndView = new ModelAndView();
		int intId = Integer.valueOf(id);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();		
		User user = userService.findUserByEmail(auth.getName());	
		SwapOrig swapToBeDisapproved = swapRepo.getOne(intId);
		//extra validation to ensure that swap has been confirmed 
		if(swapToBeDisapproved.getConfirmed() == 1 && swapToBeDisapproved.getSwapConfirmed() == 1){
			swapToBeDisapproved.setDisapprovedBy(user.getEmail());
			swapRepo.save(swapToBeDisapproved);
		} else {
			LOG.error("Seems to be a problem: is swap " + swapToBeDisapproved.getId() + " approved? ");
		}
		
//		try{
//			String firstEmail = swapToBeDisapproved.getEmail();
//			String otherEmail = swapToBeDisapproved.getSwappersEmail();
//			String shiftDetails = "Shift Date: " + swapToBeDisapproved.getDate() +
//					", From " + swapToBeDisapproved.getStartTime() + " to " + swapToBeDisapproved.getFinishTime() +
//					"\r\nSwap Shift Date: " + swapToBeDisapproved.getSwapDate() +
//					", From " + swapToBeDisapproved.getSwapStartTime() + " to " + swapToBeDisapproved.getSwapFinishTime() +
//					"\r\n\r\n";			
//			mailService.sendDisapprovedEmail(firstEmail,otherEmail,shiftDetails);
//		}catch(UnsupportedEncodingException e){
//			LOG.error("disapproved mail not sent");
//		}
			
		List<SwapOrig> confirmedSwaps = swapService.returnConfirmedSwaps();
		modelAndView.addObject("myswaps", confirmedSwaps);
		modelAndView.addObject("userName", "Welcome " + user.getName() + " " + user.getLastName() + " (ADMIN) " + " (" + user.getEmail() + ")");
		modelAndView.addObject("adminMessage","Admin Users Page");		
		modelAndView.setViewName("admin/home");
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
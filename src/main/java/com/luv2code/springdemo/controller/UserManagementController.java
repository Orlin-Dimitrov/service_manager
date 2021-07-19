// Controller for user management

package com.luv2code.springdemo.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
//import java.util.logging.Logger;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.luv2code.springdemo.helperObjects.DataTablesUniqueId;
import com.luv2code.springdemo.helperObjects.SimpleUserFormated;
import com.luv2code.springdemo.service.UserService;
import com.luv2code.springdemo.user.FormUser;
import com.luv2code.springdemo.user.FormUserDelete;

@Controller
@RequestMapping("/userManagement")
public class UserManagementController {

	@Autowired
	private UserDetailsManager userDetailsManager;
	
	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
//	private Logger logger = Logger.getLogger(getClass().getName());

	
	// NEW NEW NEW need to inject our user Security service
	@Autowired
	private UserService userService;
	
	@Autowired
	private SessionRegistry sessionRegistry;
	
	@Autowired
	private MessageSource messageSource;
	
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
		
	}

			
	// LISTING ALL THE USERS START
	
	// Method for listing all the users
	@GetMapping("/list")
	public String listSimpleUsers(Model theModel) {
				
		// If the model doesn't contain listUsers model attribute, proceed.
		if (theModel.containsAttribute("listUsers") == false) {
			
			//Generating random UID for DataTables identifier for table state saving.
			UUID uuid = UUID.randomUUID();
			String dataTablesUniqueId = uuid.toString();
			
			//Creating new DataTablesUniqueId object to store the generated dataTablesUniqueId value. Necessary for Multi-Window use.
			DataTablesUniqueId listUsers = new DataTablesUniqueId();
			
			// Setting the generated dataTablesUniqueId value.
			listUsers.setDataTablesUniqueId(dataTablesUniqueId);
			
			// Adding the created object to the model
			theModel.addAttribute("listUsers", listUsers);
			
//			System.out.println("DataTablesID: " + listUsers.getDataTablesUniqueId());
		}
		
		// Creating and adding to the model SimpleUserFormated object for storing user name  and dataTablesUniqueId when getting to change password page
		SimpleUserFormated userChangePassword = new SimpleUserFormated();

		theModel.addAttribute("userChangePassword", userChangePassword);
		
		//Delete user
		SimpleUserFormated userDelete = new SimpleUserFormated();

		theModel.addAttribute("userDelete", userDelete);
		
		//User Status
		SimpleUserFormated userStatus = new SimpleUserFormated();

		theModel.addAttribute("userStatus", userStatus);
		
		//User Status
		SimpleUserFormated userChangeLevel = new SimpleUserFormated();

		theModel.addAttribute("userChangeLevel", userChangeLevel);
				
		return "user-list";
	}
	
	
	// Method for listing all the users Post
	@PostMapping("/list")
	public String listSimpleUsersPost(@ModelAttribute("listUsers") DataTablesUniqueId listUsers,
										RedirectAttributes redirectAttributes,
										Model theModel) {
		
		// Adding Flash Attribute listUsers for PRG
		redirectAttributes.addFlashAttribute("listUsers", listUsers);
		
		return "redirect:/userManagement/list";
	}
	
	// LISTING ALL THE USERS END
	
	
	//----------------------------------------
	
	
	// CHANGE PASSWORD START !!!
	
	// Method for changing password of a user Post (disconnecting of the user NOT ADDED)
	@PostMapping("/changePassword")
	public String changePasswordFormPost(@ModelAttribute("userChangePassword") SimpleUserFormated userChangePassword,
											Model theModel,
											RedirectAttributes redirectAttributes) {

		// Adding SimpleUserFormated object for storing user name and dataTablesUniqueId when getting to change password page
		redirectAttributes.addFlashAttribute("userChangePassword", userChangePassword);
				
		return "redirect:/userManagement/changePassword"; 
	}
	
	
	// Method for changing password of a user 
	@GetMapping("/changePassword")
	public String changePasswordFormGet(Model theModel) {

		// If after redirecting the model contains "userChangePassword" flash attribute display the page.
		if (theModel.containsAttribute("userChangePassword")) {
			
			// Retrieving the flash attribute "userChangePassword" from the model		
			SimpleUserFormated simpleUser = (SimpleUserFormated)theModel.asMap().get("userChangePassword");
			
			// Creating new FormUser for form submission - change password
			FormUser theUser = new FormUser();

			// Setting the user name and DataTablesUniqueId from the SimpleUserFormated object("userChangePassword" flash attribute)
			theUser.setUserName(simpleUser.getUserName());			
			theUser.setDataTablesUniqueIdFormUser(simpleUser.getDataTablesUniqueId());
			
			// NEW NEW NEW 
			theUser.setAccessLevel("notused");
			
			theModel.addAttribute("formUser", theUser);
			
			// Adding DataTablesUniqueId for form submission to go back to list users page
			DataTablesUniqueId listUsers = new DataTablesUniqueId();
			
			theModel.addAttribute("listUsers", listUsers);			
						
			return "user-change-password";		
		}
		
		// If model contains "formUser" model attribute (After FORM SUBBMITION the Binding Result has errors) display the page.
		else if(theModel.containsAttribute("formUser")){
		
			// Adding DataTablesUniqueId for form submission to go back to list users page
			DataTablesUniqueId listUsers = new DataTablesUniqueId();
			
			theModel.addAttribute("listUsers", listUsers);
			
			return "user-change-password";		
		}
		// else redirect to the user list page(if page has been refreshed)
		else {
			return "redirect:/userManagement/list";
		}
		
	}
		
	
	// Processing the change of the user's password 
	@PostMapping("/processChangePassword")
	public String processPasswordFormPost(@Valid @ModelAttribute("formUser") FormUser theUser,
											BindingResult theBindingResult,
											Model theModel,
											RedirectAttributes redirectAttributes) {		
		
		// Form validation
		if (theBindingResult.hasErrors()) {
				
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.formUser", theBindingResult);
			redirectAttributes.addFlashAttribute("formUser", theUser);
			
			return "redirect:/userManagement/changePassword";
		}		
				
		// Check if the user has NOT been deleted
		if(doesUserExists(theUser.getUserName()) == false) {

			String userDeleted = "userDeleted";
			
			// Adding flash attribute "userDeleted" and redirecting to user deleted error page
			redirectAttributes.addFlashAttribute("userDeleted", userDeleted);
			
			return "redirect:/userManagement/errorUserDeleted";
		}
		
		// encrypting the password using spring security
		String encodedPassword = passwordEncoder.encode(theUser.getPassword());
		
		// prepend the encoding algorithm id
		encodedPassword = "{bcrypt}" + encodedPassword;
		
		// loading the user details to obtain the authority list using spring security
		UserDetails tempUserDetails = userDetailsManager.loadUserByUsername(theUser.getUserName());
		
		Collection<? extends GrantedAuthority> grantedAuthorityList = tempUserDetails.getAuthorities();
		
		// create user details object with the New encoded password
		User tempUser = new User(theUser.getUserName(), encodedPassword, grantedAuthorityList);
			
		//updating the desired user with the New changed password using spring security
		userDetailsManager.updateUser(tempUser);

//        logger.info("Successfully changed password for user: " + theUser.getUserName());       
     
        String passwordChanged = "passwordChanged";
        
        // Adding flash attribute "passwordChanged" and redirecting to the user changed password confirmation page
        redirectAttributes.addFlashAttribute("passwordChanged", passwordChanged);
        
		return "redirect:/userManagement/passwordChanged";
	}
	
	
	// User changed password confirmation page
	@GetMapping("/passwordChanged")
	public String processPasswordFormGet(Model theModel, RedirectAttributes redirectAttributes) {
		
		// if the model has "passwordChanged" flash attribute display page , else redirect to user list page
		if (theModel.containsAttribute("passwordChanged")) {
			return "user-change-password-confirmation";
		}else {
			return "redirect:/userManagement/list";
		}		
	}
	
	// CHANGE PASSWORD END !!!
	
	
	// ----------------------------------
	
	
	// DELETE USER START !!!
	
	// Method for deleting user Post (disconnecting of user IS ADDED)
	@PostMapping("/delete")
	public String deleteFormPost(@ModelAttribute("userDelete") SimpleUserFormated userDelete,
											Model theModel,
											RedirectAttributes redirectAttributes) {

		// Adding SimpleUserFormated object for storing user name and dataTablesUniqueId when getting to delete page
		redirectAttributes.addFlashAttribute("userDelete", userDelete);
				
		return "redirect:/userManagement/delete"; 
	}
	
	
	// Method for deleting user 
	@GetMapping("/delete")
	public String deleteFormGet(Model theModel) {

		// If after redirecting the model contains "userDelete" flash attribute display the page.
		if (theModel.containsAttribute("userDelete")) {
			
			// Retrieving the flash attribute "userDelete" from the model		
			SimpleUserFormated simpleUser = (SimpleUserFormated)theModel.asMap().get("userDelete");
			
			// Creating new FormUserDelete for form submission - delete user
			FormUserDelete theUser = new FormUserDelete();

			// Setting the user name and DataTablesUniqueId from the SimpleUserFormated object("userDelete" flash attribute)
			theUser.setUserName(simpleUser.getUserName());			
			theUser.setDataTablesUniqueIdFormUser(simpleUser.getDataTablesUniqueId());
			
			
			theModel.addAttribute("formUserDelete", theUser);
			
			// Adding DataTablesUniqueId for form submission to go back to list users page
			DataTablesUniqueId listUsers = new DataTablesUniqueId();
			
			theModel.addAttribute("listUsers", listUsers);			
						
			return "user-delete";		
		}
		
		// If model contains "formUserDelete" model attribute (After FORM SUBMISSION the Binding Result has errors) display the page.
		else if(theModel.containsAttribute("formUserDelete")){
		
			// Adding DataTablesUniqueId for form submission to go back to list users page
			DataTablesUniqueId listUsers = new DataTablesUniqueId();
			
			theModel.addAttribute("listUsers", listUsers);
			
			return "user-delete";		
		}
		// else redirect to the user list page(if page has been refreshed)
		else {
			return "redirect:/userManagement/list";
		}		
	}
	
	
	// Processing the delete user
	@PostMapping("/processDelete")
	public String processDeletePost(@Valid @ModelAttribute("formUserDelete") FormUserDelete theUser,
											BindingResult theBindingResult,
											Model theModel,
											RedirectAttributes redirectAttributes) {		
		
		// Form validation
		if (theBindingResult.hasErrors()) {
				
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.formUserDelete", theBindingResult);
			redirectAttributes.addFlashAttribute("formUserDelete", theUser);
			
			return "redirect:/userManagement/delete";
		}		
				
		// Check if the user has NOT been deleted
		if(doesUserExists(theUser.getUserName()) == false) {

			String userDeleted = "userDeleted";
			
			// Adding flash attribute "userDeleted" and redirecting to user deleted error page
			redirectAttributes.addFlashAttribute("userDeleted", userDeleted);
			
			return "redirect:/userManagement/errorUserDeleted";
		}
				
		// Check if User is Online
		boolean status = isUserOnLine(theUser.getUserName());
		
		// If online expire all sessions
		if (status == true) {
			
			// Expiring all the sessions for the user
			expireUserSession(theUser.getUserName());
		}
		
		// Deleting the user
		userDetailsManager.deleteUser(theUser.getUserName());   
     
//		System.out.println(">>> USER: " + theUser.getUserName() + " deleted!" );
		
        String deleted = "deleted";
        
        // Adding flash attribute "deleted" and redirecting to the user deleted confirmation page
        redirectAttributes.addFlashAttribute("deleted", deleted);
        
		return "redirect:/userManagement/userDeleted";
	}
	
	
	// User deleted confirmation page
	@GetMapping("/userDeleted")
	public String processDeleteGet(Model theModel, RedirectAttributes redirectAttributes) {
		
		// if the model has "deleted" flash attribute display page , else redirect to user list page
		if (theModel.containsAttribute("deleted")) {
			return "user-delete-confirmation";
		}else {
			return "redirect:/userManagement/list";
		}		
	}

	// DELETE USER END !!!
	
	
	// ----------------------------------
	
	
	// USER STATUS START !!!
	
	// Method to display user status Post (disconnecting of user IS ADDED)
	@PostMapping("/status")
	public String statusFormPost(@ModelAttribute("userStatus") SimpleUserFormated userStatus,
											Model theModel,
											RedirectAttributes redirectAttributes) {

		// Adding SimpleUserFormated object for storing user name and dataTablesUniqueId when getting to status page
		redirectAttributes.addFlashAttribute("userStatus", userStatus);
				
		return "redirect:/userManagement/status"; 
	}
	
	
	// Method to display user status Get
	@GetMapping("/status")
	public String statusFormGet(Model theModel, RedirectAttributes redirectAttributes) {

		// If after redirecting the model contains "userStatus" flash attribute display the page.
		if (theModel.containsAttribute("userStatus")) {
			
			// Retrieving the flash attribute "userDelete" from the model		
			SimpleUserFormated simpleUser = (SimpleUserFormated)theModel.asMap().get("userStatus");
			
			// Creating new SimpleUserFormated for form submission - disconnect user
			SimpleUserFormated theUser = new SimpleUserFormated();

			// Setting the user name and DataTablesUniqueId from the SimpleUserFormated object("userStatus" flash attribute)
			theUser.setUserName(simpleUser.getUserName());			
			theUser.setDataTablesUniqueId(simpleUser.getDataTablesUniqueId());
			
			// Check if the user has NOT been deleted
			if(doesUserExists(theUser.getUserName()) == false) {

				String userDeleted = "userDeleted";
				
				// Adding flash attribute "userDeleted" and redirecting to user deleted error page
				redirectAttributes.addFlashAttribute("userDeleted", userDeleted);
				
				return "redirect:/userManagement/errorUserDeleted";
			}
			
			// Check if User is Online
			boolean status = isUserOnLine(theUser.getUserName());
			
			// Even if the user wont be disconnected the formUserDisconnect model attribute is added to display user name and populate forms(for going back to user list page))
			theModel.addAttribute("formUserDisconnect", theUser);
			
			// !!! NEW Adding last login date - time
			// Retrieving last logging in date - time from the DB for the user
			String lastLoginDateTime = userService.getLastLoginDateTime(theUser.getUserName());
			
			// Adding it to the model
			theModel.addAttribute("lastLoginDateTime", lastLoginDateTime);
			
			
			// Adding DataTablesUniqueId for form submission to go back to list users page
			DataTablesUniqueId listUsers = new DataTablesUniqueId();
			
			theModel.addAttribute("listUsers", listUsers);
			
			if(status == true) {
				return "user-status-online";
			}else {
				return "user-status-offline";
			}
		}

		// else redirect to the user list page(if page has been refreshed)
		else {
			return "redirect:/userManagement/list";
		}		
	}
	
	// Method to disconnect the user Post
	@PostMapping("/disconnect")
	public String disconnectPost(@ModelAttribute("formUserDisconnect") SimpleUserFormated theUser,
											Model theModel,
											RedirectAttributes redirectAttributes) {

		// Adding SimpleUserFormated object for storing user name and dataTablesUniqueId
		redirectAttributes.addFlashAttribute("formUserDisconnect", theUser);
				
		return "redirect:/userManagement/disconnect"; 
	}
	
	// Method to disconnect the user Get
	@GetMapping("/disconnect")
	public String disconnectGet(Model theModel,	RedirectAttributes redirectAttributes) {

		if (theModel.containsAttribute("formUserDisconnect")) {
			
			// Retrieving the flash attribute "formUserDisconnect" from the model		
			SimpleUserFormated simpleUser = (SimpleUserFormated)theModel.asMap().get("formUserDisconnect");
			
			// Check Again if the user has NOT been deleted
			if(doesUserExists(simpleUser.getUserName()) == false) {

				String userDeleted = "userDeleted";
				
				// Adding flash attribute "userDeleted" and redirecting to user deleted error page
				redirectAttributes.addFlashAttribute("userDeleted", userDeleted);
				
				return "redirect:/userManagement/errorUserDeleted";
			}
			
			// Check if User is Online
			boolean status = isUserOnLine(simpleUser.getUserName());
			
			if(status == true) {
				
				// Expiring sessions for the user
				expireUserSession(simpleUser.getUserName()); 
				
				return "user-status-disconnected";
				
			}else {
				// User has went offline, redirect to user list page
//				return "redirect:/userManagement/list";
				
				String userOffline = "userOffline";
				
				// Adding flash attribute "userOffline" and redirecting to user offline error page
				redirectAttributes.addFlashAttribute("userOffline", userOffline);
				
				return "user-error-offline";
			}											
		}

		// else redirect to the user list page(if page has been refreshed)
		else {
			return "redirect:/userManagement/list";
		}	
	}
	
	// USER STATUS END !!!
	
	
	// ----------------------------------
	
	
	// USER CHANGE ACCESS LEVEL  START !!!
	
	
	// Method for changing access level of a user Post (disconnecting of the user IS ADDED)
	@PostMapping("/changeLevel")
	public String changeLevelFormPost(@ModelAttribute("userChangeLevel") SimpleUserFormated userChangeLevel,
											Model theModel,
											RedirectAttributes redirectAttributes) {

		// Adding SimpleUserFormated object for storing user name and dataTablesUniqueId when getting to change level page
		redirectAttributes.addFlashAttribute("userChangeLevel", userChangeLevel);
				
		return "redirect:/userManagement/changeLevel"; 
	}
	
	
	// Method for changing level of a user 
	@GetMapping("/changeLevel")
	public String changeLevelFormGet(Model theModel) {

		// If after redirecting the model contains "userChangeLevel" flash attribute display the page.
		if (theModel.containsAttribute("userChangeLevel")) {
			
			// Retrieving the flash attribute "userChangeLevel" from the model		
			SimpleUserFormated simpleUser = (SimpleUserFormated)theModel.asMap().get("userChangeLevel");
			
			// Creating new FormUser for form submission - change Level
			FormUser theUser = new FormUser();

			// Setting the user name and DataTablesUniqueId from the SimpleUserFormated object("userChangeLevel" flash attribute)
			theUser.setUserName(simpleUser.getUserName());			
			theUser.setDataTablesUniqueIdFormUser(simpleUser.getDataTablesUniqueId());
			
			// NEW NEW NEW Setting DUMMY Password, not real password. Needed for form validation
			theUser.setPassword("dummY_Par0la%");			
			
			theModel.addAttribute("formUser", theUser);
			
			// Adding DataTablesUniqueId for form submission to go back to list users page
			DataTablesUniqueId listUsers = new DataTablesUniqueId();
			
			theModel.addAttribute("listUsers", listUsers);			
						
			return "user-change-level";		
		}
		
		// If model contains "formUser" model attribute (After FORM SUBBMITION the Binding Result has errors) display the page.
		else if(theModel.containsAttribute("formUser")){
		
			// Adding DataTablesUniqueId for form submission to go back to list users page
			DataTablesUniqueId listUsers = new DataTablesUniqueId();
			
			theModel.addAttribute("listUsers", listUsers);
			
			return "user-change-level";		
		}
		// else redirect to the user list page(if page has been refreshed)
		else {
			return "redirect:/userManagement/list";
		}
		
	}
		
	
	// Processing the change of the user's access level 
	@PostMapping("/processChangeLevel")
	public String processLevelFormPost(@Valid @ModelAttribute("formUser") FormUser theUser,
											BindingResult theBindingResult,
											Model theModel,
											RedirectAttributes redirectAttributes) {		
		
		// Form validation
		if (theBindingResult.hasErrors()) {
				
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.formUser", theBindingResult);
			redirectAttributes.addFlashAttribute("formUser", theUser);
			
			return "redirect:/userManagement/changeLevel";
		}		
				
		// Check if the user has NOT been deleted
		if(doesUserExists(theUser.getUserName()) == false) {

			String userDeleted = "userDeleted";
			
			// Adding flash attribute "userDeleted" and redirecting to user deleted error page
			redirectAttributes.addFlashAttribute("userDeleted", userDeleted);
			
			return "redirect:/userManagement/errorUserDeleted";
		}
		
		// loading the details for the user using spring security	
		UserDetails tempUserDetails = userDetailsManager.loadUserByUsername(theUser.getUserName());
			
		// Creating a New authority list for the selected user
		List<GrantedAuthority> authorities = null;
		
		// populating the list using the custom authority 
		if(theUser.getAccessLevel().equals("User")) {
				authorities = AuthorityUtils.createAuthorityList("ROLE_EMPLOYEE");
		
		}else if (theUser.getAccessLevel().equals("Manager")) {
				authorities = AuthorityUtils.createAuthorityList("ROLE_EMPLOYEE", "ROLE_MANAGER");
		
		}else if (theUser.getAccessLevel().equals("Administrator")) {			
			authorities = AuthorityUtils.createAuthorityList("ROLE_EMPLOYEE", "ROLE_ADMIN");			
		}
		else {
			
			// code should not come to here	
			authorities = AuthorityUtils.createAuthorityList("ROLE_EMPLOYEE");
		}
		
		// retrieving the encoded password using spring security
		String password = tempUserDetails.getPassword();
			
		// create user details object with the changed authorities
		User tempUser = new User(theUser.getUserName(), password, authorities);
						
		//updating the desired user with the changed authorities
		userDetailsManager.updateUser(tempUser);   
     
//		System.out.println(">>> Selected Access Level: " + theUser.getAccessLevel());
	
		// Check if User is Online
		boolean status = isUserOnLine(theUser.getUserName());
		
		if(status == true) {
			
			// Expiring sessions for the user
			expireUserSession(theUser.getUserName()); 
		}
		
        String levelChanged = "levelChanged";
        
        // Adding flash attribute "levelChanged" and redirecting to the user changed level confirmation page
        redirectAttributes.addFlashAttribute("levelChanged", levelChanged);
        
		return "redirect:/userManagement/levelChanged";
	}
	
	
	// User changed access level confirmation page
	@GetMapping("/levelChanged")
	public String processLevelFormGet(Model theModel, RedirectAttributes redirectAttributes) {
		
		// if the model has "passwordChanged" flash attribute display page , else redirect to user list page
		if (theModel.containsAttribute("levelChanged")) {
			return "user-change-level-confirmation";
		}else {
			return "redirect:/userManagement/list";
		}		
	}
	
	// USER CHANGE ACCESS LEVEL  END!!!
	
	
	// ----------------------------------	
	
	
	// ADD USER START!!!
		
	// Method for Adding New user 
	@GetMapping("/add")
	public String addFormGet(Model theModel) {

		if(theModel.containsAttribute("formUser")) {
	
			return "user-add";	
		
		}else {
			
			// Creating new FormUser for form submission - add new user
			FormUser theUser = new FormUser();

			theModel.addAttribute("formUser", theUser);
			
			return "user-add";
		}
		
	}
		
	
	// Processing adding new user
	@PostMapping("/processAdd")
	public String processAddFormPost(@Valid @ModelAttribute("formUser") FormUser theUser,
											BindingResult theBindingResult,
											Model theModel,
											RedirectAttributes redirectAttributes) {		
		
		// Form validation
		if (theBindingResult.hasErrors()) {
			
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.formUser", theBindingResult);
			redirectAttributes.addFlashAttribute("formUser", theUser);
			
			return "redirect:/userManagement/add";
		}		
				
		// Check if the user exists
		if(doesUserExists(theUser.getUserName()) == true) {
		
			Locale locale = Locale.getDefault();
			
			// creating custom FieldError
			theBindingResult.addError(new FieldError("formUser", "userName",theUser.getUserName(),false, null, null,
										messageSource.getMessage("formUser.userName.exists",null, locale)));
			
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.formUser", theBindingResult);
			redirectAttributes.addFlashAttribute("formUser", theUser);
						
			return "redirect:/userManagement/add";
			
			
//			String userExists = "userExists";
//			
//			// Adding flash attribute "userDeleted" and redirecting to user deleted error page
//			redirectAttributes.addFlashAttribute("userExists", userExists);
//			
//			return "redirect:/userManagement/errorUserExists";
		}
		
		// encrypt the password
		String encodedPassword = passwordEncoder.encode(theUser.getPassword());
		
		// prepend the encoding algorithm id
		encodedPassword = "{bcrypt}" + encodedPassword;		
		
		// NEW NEW NEW !!! Adding custom authorities to the user
		List<GrantedAuthority> authorities = null;
		
		// Creating authority depending on setted value in jsp for accessLevel
		if(theUser.getAccessLevel().equals("User")) {
			
			authorities = AuthorityUtils.createAuthorityList("ROLE_EMPLOYEE");
			
		}else if (theUser.getAccessLevel().equals("Manager")) {
			
			authorities = AuthorityUtils.createAuthorityList("ROLE_EMPLOYEE", "ROLE_MANAGER");
			
		}else if (theUser.getAccessLevel().equals("Administrator")) {
			
			authorities = AuthorityUtils.createAuthorityList("ROLE_EMPLOYEE", "ROLE_ADMIN");
			
		}
		else {			
			// Code should not come to this
			authorities = AuthorityUtils.createAuthorityList("ROLE_EMPLOYEE");
		}
				
		// create user details object
		User tempUser = new User(theUser.getUserName(), encodedPassword, authorities);
		
		// save user in the database using spring security
		userDetailsManager.createUser(tempUser);

//        logger.info("Successfully created user: " + theUser.getUserName());       
     
        String newUserAdded = "newUserAdded";
        
        // Adding flash attribute "newUserAdded" and redirecting to the user added confirmation page
        redirectAttributes.addFlashAttribute("newUserAdded", newUserAdded);
        
		return "redirect:/userManagement/userAdded";
	}
	
	
	// User added confirmation page
	@GetMapping("/userAdded")
	public String processAddFormGet(Model theModel, RedirectAttributes redirectAttributes) {
		
		// if the model has "newUserAdded" flash attribute display page , else redirect to user list page
		if (theModel.containsAttribute("newUserAdded")) {
			return "user-add-confirmation";
		}else {
			return "redirect:/userManagement/list";
		}		
	}	
	
	// ADD USER END!!!
	
		
	// List of active users	
	@GetMapping("/activeUsers")
	public String activeUsersGet(Model theModel) {
		
		List<String> activeUsers = new ArrayList<String>();
		
		// Retreaving the list of active users
		activeUsers = listActiveUsers();
		
		// Sorting the users alphabetically. Using lambda java 8
		activeUsers.sort(String::compareToIgnoreCase);
		
		// Building string with username divided by comma
		StringBuilder builder = new StringBuilder();
		
		for (String value : activeUsers) {
			
		    builder.append(value);
		    builder.append(", ");
		}
		
		//Removing the last comma and space. ATTENTION !!! if only SUPERADMIN is logged in, he is not displayed in the list, so check must be made.
		if(builder.length() > 2) {
			builder.setLength(builder.length() - 2);
		}
		
		// Adding the string to the model
		theModel.addAttribute("activeUsersList", builder);
		
		return "user-active-list";
	}	
	
	
	
	// ----------------------------------	
	
		
	// ERRORS START !!!
	
	// User has been deleted error page	
	@GetMapping("/errorUserDeleted")
	public String userDeleted(Model theModel) {
		
		if (theModel.containsAttribute("userDeleted")) {
			return "user-error-deleted";
		}else {
			return "redirect:/userManagement/list";
		}
	}
	
	// User went offline	
	@GetMapping("/errorUserOffline")
	public String userOffline(Model theModel) {
		
		if (theModel.containsAttribute("userOffline")) {
			return "user-error-offline";
		}else {
			return "redirect:/userManagement/list";
		}
	}
			
	// ERRORS END!!!
	
	
	//----------------------------------
	
	
	// HELPER METHODS 
	
	// Helper method to check the database IF USER EXISTS.
	private boolean doesUserExists(String userName) {
		
		boolean exists = userDetailsManager.userExists(userName);
		
		return exists;
	}		
	

	// Helper method to check IF USER IS ONLINE.
	private boolean isUserOnLine(String userName) {
		
		boolean onLine = false;
		
		List<Object> principals = sessionRegistry.getAllPrincipals();
		
		for(Object principal : principals) {
			
			UserDetails tempUserDetails = (UserDetails)principal;
						
			String tempUserName = tempUserDetails.getUsername();
			
			if(tempUserName.equals(userName)) {
				
			    List<SessionInformation> sessionInformations = sessionRegistry.getAllSessions(principal, false);
			    
//			    System.out.println(">>> Active sessions for user: " + tempUserName + " -> " + sessionInformations.size());
			    
			    if(sessionInformations.isEmpty() == false) {
			    	onLine = true;
			    }
			    break;
			}				
		}		
		return onLine;
	}
	
	
	// Helper method to EXPIRE USER SESSION.
	private void expireUserSession(String theUser) {
		
		// TESTING EXPIRING OF A SESSION
		List<Object> principals = sessionRegistry.getAllPrincipals();
		
		for(Object principal : principals) {
			
			UserDetails tempUserDetails = (UserDetails)principal;
						
			String tempUserName = tempUserDetails.getUsername();
			
			if(tempUserName.equals(theUser)) {
				
			    List<SessionInformation> sessionInformations = sessionRegistry.getAllSessions(principal, false);
			    
//			    System.out.println("Expiring number of sessions: " + sessionInformations.size() + ">>> User: " + tempUserName);
			    
			    for (SessionInformation sessionInformation : sessionInformations) {
			        
			    	sessionInformation.expireNow();
			    	
			    }
			    break;
			}			
		}		
	}
	
	
	// Helper method to retrieve LIST of ACTIVE USERS.
	private List<String> listActiveUsers() {
		
		List<String> activeUsers = new ArrayList<String>();
		
		// Getting all principals from the session registry
		List<Object> principals = sessionRegistry.getAllPrincipals();
		
		for(Object principal : principals) {
			
			//Getting all sessions for specific principal
			List<SessionInformation> sessionInformations = sessionRegistry.getAllSessions(principal, false);
			
			// If there are active sessions
			if(sessionInformations.isEmpty() == false) {
				
				UserDetails tempUserDetails = (UserDetails)principal;
				
				// If user details is not empty, and the user does not have role "ROLE_SUADMIN" - superadmin, add the username to the active users list
				// Using Java 8 Stream
			    if ((tempUserDetails != null && tempUserDetails.getAuthorities().stream()
			    	      .anyMatch(a -> a.getAuthority().equals("ROLE_SUADMIN"))) == false) {
			    
					String tempUserName = tempUserDetails.getUsername();
					
					activeUsers.add(tempUserName);
			    }				
			}	
		}
		
		return activeUsers;
	}
	
	
}

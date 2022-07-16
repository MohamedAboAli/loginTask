package controllers;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import MessageResponse.messege;
import Request.UserLoginReq;
import model.User;
import services.UserService;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	private HttpHeaders headers;

	private messege msg = new messege();

	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = "application/json")

	public ResponseEntity<?> validateUser(@RequestBody UserLoginReq UserLoginReq) {

		try {
			User u = userService.validateUserNameAndPassword(UserLoginReq);
			System.out.println(u);
			if (u != null && u.getType() == true && UserLoginReq.getStorageid() == null) {
				msg.setMessage("welcome Admin to vodafone ");
				return new ResponseEntity<messege>(msg, HttpStatus.OK);
			} else if (u != null && u.getType() == false && u.getStorageid() == UserLoginReq.getStorageid()) {
				msg.setMessage("welcome user to vodafone");
				return new ResponseEntity<messege>(msg, HttpStatus.OK);

			} else if (u != null && u.getType() == false && u.getStorageid() != UserLoginReq.getStorageid()
					&& UserLoginReq.getStorageid() == null) {
				msg.setMessage("please Enter storage id");
				return new ResponseEntity<messege>(msg, HttpStatus.OK);

			} else if (u != null && u.getType() == false && u.getStorageid() != UserLoginReq.getStorageid()) {
				msg.setMessage("invalid storage id");
				return new ResponseEntity<messege>(msg, HttpStatus.FORBIDDEN);

			}

			else if (u != null && u.getType() == true && u.getStorageid() == UserLoginReq.getStorageid()) {
				msg.setMessage("welcome Admin to vodafone you dont need to Add storage id ");
				return new ResponseEntity<messege>(msg, HttpStatus.OK);

			} else if (u == null) {

				msg.setMessage("Invalid UserName or Password ");
				return new ResponseEntity<messege>(msg, HttpStatus.FORBIDDEN);

			}

			else {
				msg.setMessage(" try to login again without storage id");
				return new ResponseEntity<messege>(msg, HttpStatus.FORBIDDEN);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		}

	}

	@RequestMapping(method = RequestMethod.POST, value = "/insert", consumes = "application/json")
	public void adduser(@RequestBody User user) {

		if (user != null) {
			userService.saveuser(user);
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/updateuser", consumes = "application/json")
	public void updateuser(@RequestBody User user) {

		if (user != null) {
			userService.updateuserobject(user);
		}
	}

	@RequestMapping(value = "updateListUser", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> updatelistofuser(@RequestBody List<User> users) {
		try {
			userService.updatelistofuser(users);

			return new ResponseEntity<>(HttpStatus.OK);

		}

		catch (Exception e) {

			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		}

	}

	@RequestMapping(method = RequestMethod.POST, value = "/update", consumes = "application/json")
	// @PutMapping(value="/update/{name}/{id}")
	// public void update(@PathVariable("name") String name,@PathVariable("id")
	// Long id){
	// Long id ){
	public ResponseEntity<?> update(@RequestBody User user) {
		try {
			userService.updateuser(user.getName(), user.getRecid(), user.getPassword());
			msg.setMessage(" the updated is successful ");

			return new ResponseEntity<messege>(msg, HttpStatus.OK);
		} catch (Exception xe) {

			xe.printStackTrace();
			msg.setMessage("the  updatedis failed");

			return new ResponseEntity<messege>(msg, HttpStatus.FORBIDDEN);
		}
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		try {

			if (id != null) {
				userService.delete(id);
				msg.setMessage("record is deleted successfull ");

				return new ResponseEntity<messege>(msg, HttpStatus.OK);

			}
			return new ResponseEntity<messege>(msg, HttpStatus.BAD_REQUEST);

		}

		catch (Exception e) {
			msg.setMessage("the deleted is failed");

			return new ResponseEntity<messege>(msg, HttpStatus.FORBIDDEN);
		}

	}

	@RequestMapping(method = RequestMethod.GET, value = "search/{id}")
	public ResponseEntity<?> searchById(@PathVariable("id") Long id) {
		try {

			if (id != null) {

				return new ResponseEntity<Optional<User>>(userService.searchById(id), HttpStatus.OK);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);

	}

	@RequestMapping(method = RequestMethod.GET, value = "/getuser", consumes = "application/json")
	public List<User> getAllUser() {
		return userService.getAlluser();

	}

	@RequestMapping(method = RequestMethod.GET, value = "/getAlluser", consumes = "application/json")
	public ResponseEntity<?> getAllofUser() {

		return new ResponseEntity<List<User>>(userService.getAllofuser(), HttpStatus.OK);

	}

	@RequestMapping(method = RequestMethod.POST, value = "/register", consumes = "application/json")

	public ResponseEntity<?> regestartion(@RequestBody User userreq) {

		User u = userService.finduserbyname(userreq);

		if (u == null) {

			userService.saveuser(userreq);

			msg.setMessage("regestration done successfully");

			return new ResponseEntity<messege>(msg, HttpStatus.ACCEPTED);

		} else {

			msg.setMessage("User Alrady exist");

			return new ResponseEntity<messege>(msg, HttpStatus.EXPECTATION_FAILED);

		}

	}

	@RequestMapping(method = RequestMethod.POST, value = "/upload")
	public ResponseEntity<?> uploadfile(@RequestParam("file") MultipartFile reapExcelDataFile) {

		if (reapExcelDataFile != null) {
			try {
				userService.mapReapExcelDatatoDB(reapExcelDataFile);

				msg.setMessage("file uploaded successfully successfully");

				return new ResponseEntity<messege>(msg, HttpStatus.OK);
			} catch (IOException e) {

			}

			msg.setMessage("upload file failed to upload");

			return new ResponseEntity<messege>(msg, HttpStatus.EXPECTATION_FAILED);

		}

		return new ResponseEntity<messege>(msg, HttpStatus.BAD_REQUEST);

	}

}
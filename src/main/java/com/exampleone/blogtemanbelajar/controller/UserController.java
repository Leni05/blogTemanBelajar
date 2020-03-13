package com.exampleone.blogtemanbelajar.controller; 

import com.exampleone.blogtemanbelajar.model.User;
import com.exampleone.blogtemanbelajar.model.ResponseBaseDTO;
import com.exampleone.blogtemanbelajar.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @RequestMapping(value="/user", method = RequestMethod.GET)
    public ResponseEntity<ResponseBaseDTO> listUser(){ 
        ResponseBaseDTO response = new ResponseBaseDTO(); 
        try
        {         
         List<User> userList = userService.findAll();
         response.setStatus(true);
         response.setCode("200");
         response.setMessage("success");
         response.setData(userList);         
         
         return new ResponseEntity<>(response ,HttpStatus.OK);
        }
        catch(Exception e)
        {
         // catch error when get user
         response.setStatus(false);
         response.setCode("500");
         response.setMessage(e.getMessage());
        }
        
        return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
        
        // return userService.findAll();
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<ResponseBaseDTO> create(@RequestBody User user){
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        User result = new User();
       
        ResponseBaseDTO response = new ResponseBaseDTO(); 

        if(user.getEmail().isEmpty() || user.getFirstname().isEmpty() || user.getNohp().isEmpty()|| user.getLastname().isEmpty()|| user.getPassword().isEmpty())
        {
            // System.out.println(user.getEmail());
            response.setMessage("column is null");
            return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
      
        }
        
        try
        {
         
            result =  userService.save(user);
            response.setStatus(true);
            response.setCode("200");
            response.setMessage("success");
            response.setData(result);           
            
            return new ResponseEntity<>(response ,HttpStatus.OK);
        }
        catch(Exception e)
        {
         // catch error when get user
            response.setStatus(false);
            response.setCode("500");
            response.setMessage(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
        }
       
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public  ResponseEntity<ResponseBaseDTO> delete(@PathVariable(value = "id") Long id){       
       
        ResponseBaseDTO response = new ResponseBaseDTO(); 

        try
        {         
            userService.delete(id);
            response.setStatus(true);
            response.setCode("200");
            response.setMessage("success");           
            
            return new ResponseEntity<>(response ,HttpStatus.OK);
        }
        catch(Exception e)
        {
         // catch error when get user
            response.setStatus(false);
            response.setCode("500");
            response.setMessage( "id " + id + " not exists! " );
            return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
        }
      
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public ResponseEntity<ResponseBaseDTO> updateUser(@PathVariable("id") long id, @RequestBody User user) {
     
       
        ResponseBaseDTO response = new ResponseBaseDTO();
        try {
            Optional<User> userData = userService.findById(id);
            if (userData.isPresent()) {
                User _user = userData.get();
                _user.setFirstname(user.getFirstname());
                _user.setLastname(user.getLastname());
                _user.setEmail(user.getEmail());
                _user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
                _user.setNohp(user.getNohp());

                // userData = userService.save(_user)
                response.setStatus(true);
                response.setCode("200");
                response.setMessage("success");  
                response.setData(userService.save(_user));            
                
            }
            return new ResponseEntity<>( response, HttpStatus.OK);
          
        } catch (Exception e) {
            // catch error when get user
            response.setStatus(false);
            response.setCode("500");
            response.setMessage( "id " + id + " not exists! " );
            return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
        }
       
    }


    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
	public ResponseEntity<ResponseBaseDTO> getTutorialById(@PathVariable("id") long id) {

        ResponseBaseDTO response = new ResponseBaseDTO(); 

        try
        {     
            Optional<User> userList = userService.findById(id); 
            if (userList.isPresent()) {           
                response.setStatus(true);
                response.setCode("200");
                response.setMessage("success");
                response.setData(userList);     
                
            }
            return new ResponseEntity<>( response, HttpStatus.OK);
        }
        catch(Exception e)
        {
            // catch error when get user
            response.setStatus(false);
            response.setCode("500");
            response.setMessage(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
        }
    }

}

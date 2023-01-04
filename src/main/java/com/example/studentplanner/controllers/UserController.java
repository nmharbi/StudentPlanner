package com.example.studentplanner.controllers;

import com.example.studentplanner.models.User;
import com.example.studentplanner.service.PlanService;
import com.example.studentplanner.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "api/v1/user")
public class UserController {
    private  final UserService userService;

    @Autowired
    private  ScheduleController scheduleController;

    @Autowired
    private PlanService planService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/getAll")
    public List<User> getAll(){
        return userService.getAll();
    }

    @GetMapping(path = "/get/{id}")
    public User get(@PathVariable Long id){
        return userService.get(id);
    }

    @GetMapping(path = "/getByEmail/{email}")
    public User getByEmail(@PathVariable String email){
        return userService.getByEmail(email);
    }

    @PostMapping(path = "/register")
    public User register(@RequestBody User user, @Param("url") String url) throws MessagingException, UnsupportedEncodingException {
        if(userService.checkIfExists(user.getEmail())){
            user.setCommentCertified(true);
            User user1 = userService.add(user, url);
            scheduleController.create(user1.getId());
            planService.save(user1);
            return user1;
        }else
            throw new IllegalStateException("email already in use");
    }

    @GetMapping(path = "/login/{email}/{password}")
    public List<User> login(@PathVariable String email, @PathVariable String password ){
        User s = userService.login(email,password);
        if(!s.isEnabled())
            throw new IllegalStateException("Email is not verified");
        List<User> ss = new ArrayList<>();
        ss.add(s);
        return ss;
    }

    @DeleteMapping(path = "/delete/{id}")
    public void delete(@PathVariable Long id){
        userService.delete(id);
    }

    @GetMapping(path = "/check/{email}")
    public boolean checkIfExists(@PathVariable String email){
        return userService.checkIfExists(email);
    }

    @GetMapping(path = "/commentCertified/{id}")
    public boolean commentCertified(@PathVariable Long id){
        if(userService.get(id).getCommentCertified())
            return true;
        return false;
    }

    @PostMapping(path = "/update/{id}")
    public User update(@PathVariable Long id, @RequestBody User user){
        return userService.update(user,id);
    }

    @PostMapping(path = "/resetPassword")
    public String resetPassword(@Param("token") String token, @Param("password") String password){
        userService.resetPassword(token,password);
        return "Congratulations, your password has been reset";
    }

    @PostMapping(path = "/blockUser/{id}")
    public void blockUser(@PathVariable Long id){
        userService.blockUser(id);
    }

    @PostMapping(path = "/unblockUser/{id}")
    public void unblockUser(@PathVariable Long id){
        userService.unblockUser(id);
    }

    @GetMapping(path = "/getBlockedUsers")
    public List<User> getBlockedUsers(){
        return userService.getBlockedUsers();
    }

    @GetMapping(path = "/verify")
    public String verifyUser(@Param("code") String code){
        if (userService.verify(code))
            return "Congratulations, your account has been verified.";
        else
            return "Sorry, we could not verify account. It maybe already verified, or verification code is incorrect.";

    }

    @PostMapping(path = "/sendResetPasswordEmail/{email}")
    public void sendResetPasswordEmail(@PathVariable String email,@Param("url") String url) throws MessagingException, UnsupportedEncodingException {
        System.out.println(url);
        userService.sendResetPasswordEmail(userService.getByEmail(email),url);
    }

    @GetMapping(path = "/reset")
    public String reset(@Param("url") String url){
        List<String> s = List.of(url.split("token="));
        return resetPage(s.get(0),s.get(1));
    }

    private String resetPage(String url,String token){
        String s= "\n" +
                "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                "<style>\n" +
                "/* Style all input fields */\n" +
                "input {\n" +
                "  width: 100%;\n" +
                "  padding: 12px;\n" +
                "  border: 1px solid #ccc;\n" +
                "  border-radius: 4px;\n" +
                "  box-sizing: border-box;\n" +
                "  margin-top: 6px;\n" +
                "  margin-bottom: 16px;\n" +
                "}\n" +
                "\n" +
                "/* Style the submit button */\n" +
                "input[type=submit] {\n" +
                "  background-color: #04AA6D;\n" +
                "  color: white;\n" +
                "}\n" +
                "\n" +
                "/* Style the container for inputs */\n" +
                ".container {\n" +
                "  background-color: #f1f1f1;\n" +
                "  padding: 20px;\n" +
                "}\n" +
                "\n" +
                "/* The message box is shown when the user clicks on the password field */\n" +
                "#message {\n" +
                "  /* display:none; */\n" +
                "  background: #f1f1f1;\n" +
                "  color: #000;\n" +
                "  position: relative;\n" +
                "  padding: 20px;\n" +
                "  margin-top: 10px;\n" +
                "}\n" +
                "\n" +
                "#message p {\n" +
                "  padding: 10px 35px;\n" +
                "  font-size: 18px;\n" +
                "}\n" +
                "\n" +
                "/* Add a green text color and a checkmark when the requirements are right */\n" +
                ".valid {\n" +
                "  color: green;\n" +
                "}\n" +
                "\n" +
                ".valid:before {\n" +
                "  position: relative;\n" +
                "  left: -35px;\n" +
                "}\n" +
                "\n" +
                "/* Add a red text color and an \"x\" when the requirements are wrong */\n" +
                ".invalid {\n" +
                "  color: red;\n" +
                "}\n" +
                "\n" +
                ".invalid:before {\n" +
                "  position: relative;\n" +
                "  left: -35px;\n" +
                "}\n" +
                "</style>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "<h3>Password Validation</h3>\n" +
                "<p>Try to submit the form.</p>\n" +
                "\n" +
                "<div class=\"container\">\n" +
                "  <form method=\"post\" action=\"[[action]]\" onsubmit=\"required()\" >\n" +
                "\n" +
                "    <label for=\"password\">Password</label>\n" +
                "    <input type=\"password\" id=\"password\" name=\"password\" pattern=\"(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}\" title=\"Must contain at least one number and one uppercase and lowercase letter, and at least 8 or more characters and match repeted password\" required>\n" +
                "\n" +
                "    <label for=\"repetePassword\">repete password</label>\n" +
                "    <input type=\"password\" id=\"repetePassword\" name=\"Repete password\" pattern=\"(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}\" title=\"Must contain at least one number and one uppercase and lowercase letter, and at least 8 or more characters and match repeted password\" required>\n" +
                "\n" +
                "    \n" +
                "    <input type=\"submit\" value=\"Submit\">\n" +
                "  </form>\n" +
                "</div>\n" +
                "\n" +
                "<div id=\"message\">\n" +
                "  <h3>Password must contain the following:</h3>\n" +
                "  <p id=\"letter\" class=\"invalid\">A <b>lowercase</b> letter</p>\n" +
                "  <p id=\"capital\" class=\"invalid\">A <b>capital (uppercase)</b> letter</p>\n" +
                "  <p id=\"number\" class=\"invalid\">A <b>number</b></p>\n" +
                "  <p id=\"length\" class=\"invalid\">Minimum <b>8 characters</b></p>\n" +
                "  <p id=\"repeted\" class=\"invalid\"><b>Repeted password match</b></p>\n" +
                "\n" +
                "</div>\n" +
                "\t\t\t\t\n" +
                "<script>\n" +
                "\n" +
                "var myInput = document.getElementById(\"password\");\n" +
                "var myInput2 = document.getElementById(\"repetePassword\");\n" +
                "\n" +
                "var letter = document.getElementById(\"letter\");\n" +
                "var capital = document.getElementById(\"capital\");\n" +
                "var number = document.getElementById(\"number\");\n" +
                "var length = document.getElementById(\"length\");\n" +
                "var repeted = document.getElementById(\"repeted\");\n" +
                "\n" +
                "// When the user starts to type something inside the password field\n" +
                "myInput.onkeyup = function() {\n" +
                "  //Validate rpeted password \n" +
                "  if(myInput.value == myInput2.value) {\n" +
                "    repeted.classList.remove(\"invalid\");\n" +
                "    repeted.classList.add(\"valid\");\n" +
                "  } else {\n" +
                "    repeted.classList.remove(\"valid\");\n" +
                "    repeted.classList.add(\"invalid\");\n" +
                "  }\n" +
                "  // Validate lowercase letters\n" +
                "  var lowerCaseLetters = /[a-z]/g;\n" +
                "  if(myInput.value.match(lowerCaseLetters)) {  \n" +
                "    letter.classList.remove(\"invalid\");\n" +
                "    letter.classList.add(\"valid\");\n" +
                "  } else {\n" +
                "    letter.classList.remove(\"valid\");\n" +
                "    letter.classList.add(\"invalid\");\n" +
                "  }\n" +
                "  \n" +
                "  // Validate capital letters\n" +
                "  var upperCaseLetters = /[A-Z]/g;\n" +
                "  if(myInput.value.match(upperCaseLetters)) {  \n" +
                "    capital.classList.remove(\"invalid\");\n" +
                "    capital.classList.add(\"valid\");\n" +
                "  } else {\n" +
                "    capital.classList.remove(\"valid\");\n" +
                "    capital.classList.add(\"invalid\");\n" +
                "  }\n" +
                "\n" +
                "  // Validate numbers\n" +
                "  var numbers = /[0-9]/g;\n" +
                "  if(myInput.value.match(numbers)) {  \n" +
                "    number.classList.remove(\"invalid\");\n" +
                "    number.classList.add(\"valid\");\n" +
                "  } else {\n" +
                "    number.classList.remove(\"valid\");\n" +
                "    number.classList.add(\"invalid\");\n" +
                "  }\n" +
                "\n" +
                "  // Validate length\n" +
                "  if(myInput.value.length >= 8) {\n" +
                "    length.classList.remove(\"invalid\");\n" +
                "    length.classList.add(\"valid\");\n" +
                "  } else {\n" +
                "    length.classList.remove(\"valid\");\n" +
                "    length.classList.add(\"invalid\");\n" +
                "  }\n" +
                "\n" +
                "\n" +
                "}\n" +
                "myInput2.onkeyup = function() {\n" +
                "      //Validate rpeted password \n" +
                "  if(myInput.value == myInput2.value) {\n" +
                "    repeted.classList.remove(\"invalid\");\n" +
                "    repeted.classList.add(\"valid\");\n" +
                "  } else {\n" +
                "    repeted.classList.remove(\"valid\");\n" +
                "    repeted.classList.add(\"invalid\");\n" +
                "  }\n" +
                "\n" +
                "}\n" +
                "\n" +
                "</script>\n" +
                "\n" +
                "</body>\n" +
                "</html>\n";
        url+="/resetPassword?token="+token;
        s=s.replace("[[action]]", url);
        return s;
    }

}

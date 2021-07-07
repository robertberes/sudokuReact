package sk.tuke.colorsudoku.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.colorsudoku.entity.Users;
import sk.tuke.colorsudoku.service.UserService;

import java.util.Date;


@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserService userService;

    private Users loggedUsers;

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/registration")
    public String registration() {  return "registration"; }


    @RequestMapping("/login")
    public String login(String username, String password) {
        for (String temp : userService.getUsernames()){
            if (temp.equals(username)) {
                if (userService.getPassword(temp).equals(password)){
                    loggedUsers = new Users(username,password,true, new Date());
                    return "redirect:/colorsudoku/new?difficulty=1";
                }

            }
        }


        return "redirect:/";
    }
    @RequestMapping("/reg")
    public String registration(String username, String password, String password2){
        if(password.equals(password2)){
            try {
                userService.addUser(new Users(username,password,true,new Date()));
            } catch (Exception e) {
                return "redirect:/registration";
            }

        }

        return "redirect:/";
    }

    @RequestMapping("/logout")
    public String logout() {
        loggedUsers = null;
        return "redirect:/";
    }

    public Users getLoggedUsers() {
        return loggedUsers;
    }

    public boolean isLogged() {
        return loggedUsers != null;
    }

    public UserService getUserService() {
        return userService;
    }
}

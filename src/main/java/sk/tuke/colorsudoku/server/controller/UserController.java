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


    @RequestMapping("/login")
    public String login(String login, String password) {
        if ("heslo".equals(password)) {
            loggedUsers = new Users(login,password,true, new Date());
            return "redirect:/colorsudoku/new?difficulty=1";
        }

        return "redirect:/";
    }
    @RequestMapping("/registration")
    public String registration(String login, String password){
        return "redirect:/login";
    }

    @RequestMapping("/logout")
    public String logout() {
        loggedUsers = null;
        return "redirect:/";
    }

    public Users getLoggedUser() {
        return loggedUsers;
    }

    public boolean isLogged() {
        return loggedUsers != null;
    }

    public UserService getUserService() {
        return userService;
    }
}

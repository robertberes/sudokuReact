package sk.tuke.colorsudoku.server.webservice;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sk.tuke.colorsudoku.entity.Users;
import sk.tuke.colorsudoku.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserServiceRest {

    @Autowired
    UserService userService;

    @PostMapping
    public void addUser(@RequestBody Users user){
        userService.addUser(user);
    }

    @GetMapping("/{username}")
    public String getPassword(@PathVariable String username){
        return userService.getPassword(username);
    }
}

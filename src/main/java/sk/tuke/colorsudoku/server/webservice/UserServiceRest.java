package sk.tuke.colorsudoku.server.webservice;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sk.tuke.colorsudoku.entity.Users;
import sk.tuke.colorsudoku.service.UserService;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/users")
public class UserServiceRest {

    @Autowired
    UserService userService;

    @CrossOrigin(origins = "http://localhost:8080")
    @PostMapping
    public void addUser(@RequestBody Users user){
        userService.addUser(user);
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/{username}")
    public String getPassword(@PathVariable String username){
        return userService.getPassword(username);
    }
}

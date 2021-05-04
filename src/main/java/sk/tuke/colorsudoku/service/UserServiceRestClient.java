package sk.tuke.colorsudoku.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import sk.tuke.colorsudoku.entity.Users;

import java.util.Arrays;
import java.util.List;

public class UserServiceRestClient implements UserService{
    private final String url = "http://localhost:8080/api/users";

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void addUser(Users users) throws UserException {
        restTemplate.postForEntity(url, users, Users.class);
    }

    @Override
    public String getPassword(String username) throws UserException {
        return restTemplate.getForEntity(url + '/' + username, String.class).getBody();
    }

    @Override
    public void reset() {
        throw new UnsupportedOperationException("Not supported via web service");
    }

    @Override
    public List<String> getUsernames() throws UserException {
        return Arrays.asList(restTemplate.getForEntity(url,String[].class).getBody());
    }
}

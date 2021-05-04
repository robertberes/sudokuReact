package sk.tuke.colorsudoku.service;

import sk.tuke.colorsudoku.entity.Users;

import java.util.List;


public interface UserService {
    void addUser(Users users) throws UserException;
    String getPassword(String username) throws UserException;
    void reset() throws UserException;
    List<String> getUsernames() throws UserException;
}

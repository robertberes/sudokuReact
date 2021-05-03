package sk.tuke.colorsudoku.service;

import sk.tuke.colorsudoku.entity.Users;

public interface UserService {
    void addUser(Users users) throws UserException;
    String getPassword(String username) throws UserException;
    void reset() throws UserException;
}

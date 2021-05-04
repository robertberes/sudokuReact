package sk.tuke.colorsudoku.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import java.io.Serializable;
import java.util.Date;

@Entity
@NamedQuery( name = "Users.getPassword",
        query = "SELECT u from Users u where u.username=:username")
@NamedQuery( name = "Users.reset",
        query = "DELETE FROM Users")
@NamedQuery( name = "Users.getUsernames",
        query = "SELECT username FROM Users")
public class Users implements Serializable {
    @Id
    @GeneratedValue
    private int ident;

    private String username;

    private String passwords;

    private boolean show_tips;

    private Date registered_on;

    public Users(String username, String passwords, boolean show_tips, Date registered_on) {
        this.username = username;
        this.passwords = passwords;
        this.show_tips = show_tips;
        this.registered_on = registered_on;
    }

    public Users() {}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return passwords;
    }

    public void setPassword(String passwords) {
        this.passwords = passwords;
    }

    public boolean isShow_tips() {
        return show_tips;
    }

    public void setShow_tips(boolean show_tips) {
        this.show_tips = show_tips;
    }

    public Date getRegistered_on() {
        return registered_on;
    }

    public void setRegistered_on(Date registered_on) {
        this.registered_on = registered_on;
    }

    public int getIdent() {
        return ident;
    }

    public void setIdent(int ident) {
        this.ident = ident;
    }

    @Override
    public String toString(){
        return "User{" +
                "ident=" + ident +
                ", username='" + username + '\'' +
                ", password='" + passwords + '\'' +
                ", show_tips=" + show_tips +
                ", registered_on=" + registered_on +
                '}';
    }
}


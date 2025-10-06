package vn.ledeem.jobhunter.controller;

import org.springframework.web.bind.annotation.RestController;

import vn.ledeem.jobhunter.domain.User;
import vn.ledeem.jobhunter.service.UserService;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
public class UserController {

    private final UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    // @GetMapping("/user/create")
    @PostMapping("/user")
    public User createNewUser(
            @RequestBody User postManUser) {
        User deUser = this.userService.handleCreateUser(postManUser);
        return deUser;
    }

    // delete user by id
    @DeleteMapping("/user/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        this.userService.handleDeleteUser(id);
        return "delete user";
    }

    // fetch user by id
    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable("id") Long id) {
        return this.userService.fetchUserById(id);
    }

    // fetch all users
    @GetMapping("/user")
    public List<User> getAllUsers() {
        return this.userService.fetchAllUsers();
    }

    // update user by id
    @PutMapping("/user")
    public User updateNewUser(
            @RequestBody User user) {
        User deUser = this.userService.handleUpdateUser(user);
        return deUser;
    }

}

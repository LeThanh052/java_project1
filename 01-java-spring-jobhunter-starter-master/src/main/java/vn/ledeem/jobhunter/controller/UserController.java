package vn.ledeem.jobhunter.controller;

import org.springframework.web.bind.annotation.RestController;

import vn.ledeem.jobhunter.domain.User;
import vn.ledeem.jobhunter.service.UserService;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class UserController {

    private final UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/create")
    public String createNewUser() {
        User user = new User();
        user.setEmail("thanh@gmail.com");
        user.setName("thanh dep trai");
        user.setPassword("4303203");

        this.userService.handleCreateUser(user);
        return "Hello Viet Nam";
    }

}

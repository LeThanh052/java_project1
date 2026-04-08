package vn.ledeem.jobhunter.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.ledeem.jobhunter.service.EmailService;
import vn.ledeem.jobhunter.ultil.annotation.ApiMessage;

@RestController
@RequestMapping("/api/v1")
public class Emailcontroller {

    private final EmailService emailService;

    public Emailcontroller(EmailService emailService) {
        this.emailService = emailService;
    }

    // http://127.0.0.1:8080/api/v1/email
    @GetMapping("/email")
    @ApiMessage("Send simple email")
    public String sendSimpleEmail() {
        this.emailService.sendSimpleEmail();
        return "ok";
    }
}

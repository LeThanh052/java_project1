package vn.ledeem.jobhunter.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import vn.ledeem.jobhunter.domain.Subscriber;
import vn.ledeem.jobhunter.domain.response.subscriber.ResCreateSubscriberDTO;
import vn.ledeem.jobhunter.domain.response.subscriber.ResUpdateSubscriberDTO;
import vn.ledeem.jobhunter.service.SubscriberService;
import vn.ledeem.jobhunter.ultil.annotation.ApiMessage;
import vn.ledeem.jobhunter.ultil.error.IdInvalidException;

@RestController
@RequestMapping("/api/v1")
public class SubscriberController {
    private final SubscriberService subscriberService;

    public SubscriberController(SubscriberService subscriberService) {
        this.subscriberService = subscriberService;
    }

    @PostMapping("/subscribers")
    @ApiMessage("Create a subscriber")
    public ResponseEntity<ResCreateSubscriberDTO> create(@Valid @RequestBody Subscriber subscriber)
            throws IdInvalidException {
        // Check if email already exists
        if (this.subscriberService.isEmailExist(subscriber.getEmail())) {
            throw new IdInvalidException(
                    "Email " + subscriber.getEmail() + " đã tồn tại, vui lòng sử dụng email khác.");
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.subscriberService.create(subscriber));
    }

    @PutMapping("/subscribers")
    @ApiMessage("Update a subscriber")
    public ResponseEntity<ResUpdateSubscriberDTO> update(
            @Valid @RequestBody Subscriber subscriber) throws IdInvalidException {

        Optional<Subscriber> currentSubscriber = this.subscriberService.fetchSubscriberById(subscriber.getId());
        if (!currentSubscriber.isPresent()) {
            throw new IdInvalidException("Subscriber not found");
        }

        return ResponseEntity.ok()
                .body(this.subscriberService.update(subscriber, currentSubscriber.get()));
    }
}

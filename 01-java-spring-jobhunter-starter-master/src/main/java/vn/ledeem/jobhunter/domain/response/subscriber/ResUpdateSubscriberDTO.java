package vn.ledeem.jobhunter.domain.response.subscriber;

import java.time.Instant;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResUpdateSubscriberDTO {
    private Long id;
    private String email;
    private String name;
    private List<String> skills;
    private Instant createdAt;
    private Instant updatedAt;
    private String updatedBy;
}

package vn.ledeem.jobhunter.domain.response.job;

import java.time.Instant;

import lombok.Getter;
import lombok.Setter;
import vn.ledeem.jobhunter.ultil.constant.LevelEnum;

@Getter
@Setter

public class ResUpdateJobDTO {

    private Long id;
    private String name;
    private Double salary;
    private Integer quantity;
    private String location;
    private LevelEnum level;
    private Instant startDate;
    private Instant endDate;
    private boolean isActive;

    private Instant createdAt;
}

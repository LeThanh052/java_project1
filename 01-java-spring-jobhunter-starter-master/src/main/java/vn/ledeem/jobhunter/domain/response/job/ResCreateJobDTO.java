package vn.ledeem.jobhunter.domain.response.job;

import java.time.Instant;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import vn.ledeem.jobhunter.ultil.constant.LevelEnum;

@Getter
@Setter

public class ResCreateJobDTO {

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
    private String createdBy;

    private List<String> skills;
}

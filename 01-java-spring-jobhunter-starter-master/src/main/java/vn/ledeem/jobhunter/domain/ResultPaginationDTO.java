package vn.ledeem.jobhunter.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ResultPaginationDTO {
    private Meta meta;
    private Object result;
}

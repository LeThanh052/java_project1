package vn.ledeem.jobhunter.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Meta {
    private int page;
    private int pageSize;
    private int pages;
    private long total;
}

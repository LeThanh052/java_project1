package vn.ledeem.jobhunter.domain.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ResultPaginationDTO {
    private Meta meta;
    private Object result;

    @Getter
    @Setter
    @ToString
    public static class Meta {
        private int page;
        private int pageSize;
        private int pages;
        private long total;
    }
}

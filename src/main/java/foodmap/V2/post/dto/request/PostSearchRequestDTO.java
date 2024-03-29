package foodmap.V2.post.dto.request;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import static java.lang.Math.max;
import static java.lang.Math.min;

@Getter
@Setter
public class PostSearchRequestDTO {

    private static final int MAX_SIZE = 2000;

    private Integer page;

    private Integer size;

    private String search;

    private String sort;
    @Builder
    public PostSearchRequestDTO() {
        this.page = 1;
        this.size = 6;
        this.sort = "createdAt";
        this.search="";
    }
    public long getOffset() {
        return (long) (max(1, page) - 1) * min(size, MAX_SIZE);
    }

}

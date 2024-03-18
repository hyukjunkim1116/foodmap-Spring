package foodmap.V2.dto.response.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostAuthorResponseDTO {
    private String username;
    private Long uid;
    private String image;
}
package sc.senai.twetter2.dto;

import java.util.List;

public record FeedDTO(List<FeedItemDTO> feedItems,
                      Integer page,
                      Integer pageSize,
                      Integer totalPages,
                      Long totalElements) {
}

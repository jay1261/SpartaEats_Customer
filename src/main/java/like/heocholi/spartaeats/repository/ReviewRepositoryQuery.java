package like.heocholi.spartaeats.repository;

import like.heocholi.spartaeats.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReviewRepositoryQuery {
    List<Review> getReviewsUserLikedWithPage(Long userId, Pageable pageable);

    Long getReviewUserLikedCount(Long userId);
}

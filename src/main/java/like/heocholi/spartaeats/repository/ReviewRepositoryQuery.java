package like.heocholi.spartaeats.repository;

import like.heocholi.spartaeats.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewRepositoryQuery {
    Page<Review> getReviewsUserLikedWithPage(Long userId, Pageable pageable);
}

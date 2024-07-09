package like.heocholi.spartaeats.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import like.heocholi.spartaeats.entity.QLike;
import like.heocholi.spartaeats.entity.QReview;
import like.heocholi.spartaeats.entity.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class ReviewRepositoryQueryImpl implements ReviewRepositoryQuery {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Review> getReviewsUserLikedWithPage(Long userId, Pageable pageable) {
        QReview qReview = QReview.review;
        QLike qLike = QLike.like;

        List<Review> reviews = jpaQueryFactory.select(qReview)
                .from(qReview)
                .leftJoin(qLike).on(qReview.id.eq(qLike.review.id))
                .where(qLike.customer.id.eq(userId).and(qLike.isLike.isTrue()))
                .orderBy(qReview.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return reviews;
    }

    public Long getReviewUserLikedCount(Long userId){
        QLike qLike = QLike.like;

        Long count = jpaQueryFactory
                .select(qLike.count())
                .from(qLike)
                .where(qLike.customer.id.eq(userId).and(qLike.isLike.isTrue()))
                .fetchOne();

        return count;
    }
}

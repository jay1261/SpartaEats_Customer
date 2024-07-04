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
    public Page<Review> getReviewsUserLikedWithPage(Long userId, Pageable pageable) {
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

        // 전체 리뷰 수를 가져오는 쿼리
        long count = jpaQueryFactory
                .select(qReview)
                .from(qReview)
                .leftJoin(qLike).on(qReview.id.eq(qLike.review.id))
                .where(qLike.customer.id.eq(userId).and(qLike.isLike.isTrue()))
                .fetchCount();

        return new PageImpl<Review>(reviews, pageable, count);
    }

}

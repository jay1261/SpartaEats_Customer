package like.heocholi.spartaeats.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import like.heocholi.spartaeats.entity.QFollow;
import like.heocholi.spartaeats.entity.QStore;
import like.heocholi.spartaeats.entity.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class FollowRepositoryQueryImpl implements FollowRepositoryQuery {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Store> getFollowedManagersStoresWithPage(Long userId, Pageable pageable) {
        QStore qStore = QStore.store;
        QFollow qFollow = QFollow.follow;

        return jpaQueryFactory
                .select(qStore)
                .from(qFollow)
                .leftJoin(qStore).on(qStore.manager.id.eq(qFollow.manager.id))
                .where(qFollow.customer.id.eq(userId).and(qStore.id.isNotNull()))
                .orderBy(qStore.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public Long getFollowedManagersStoresCount(Long userId) {
        QStore qStore = QStore.store;
        QFollow qFollow = QFollow.follow;

        return jpaQueryFactory
                .select(qStore.count())
                .from(qFollow)
                .leftJoin(qStore).on(qStore.manager.eq(qFollow.manager))
                .where(qFollow.customer.id.eq(userId))
                .fetchOne();
    }

}

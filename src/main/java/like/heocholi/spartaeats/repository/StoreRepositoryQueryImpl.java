package like.heocholi.spartaeats.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import like.heocholi.spartaeats.entity.QPick;
import like.heocholi.spartaeats.entity.QStore;
import like.heocholi.spartaeats.entity.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class StoreRepositoryQueryImpl implements StoreRepositoryQuery {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Store> getStoresUserLikedWithPage(Long userId, Pageable pageable) {
        QStore qStore = QStore.store;
        QPick qPick = QPick.pick;

        List<Store> storeList = jpaQueryFactory
                .select(qStore)
                .from(qStore)
                .join(qPick).on(qStore.id.eq(qPick.store.id))
                .where(qPick.customer.id.eq(userId).and(qPick.isPick.isTrue()))
                .orderBy(qStore.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return storeList;
    }
}

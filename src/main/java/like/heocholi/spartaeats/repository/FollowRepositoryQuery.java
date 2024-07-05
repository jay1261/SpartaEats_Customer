package like.heocholi.spartaeats.repository;

import like.heocholi.spartaeats.entity.Store;


import org.springframework.data.domain.Pageable;
import java.util.List;

public interface FollowRepositoryQuery {
    List<Store> getFollowedManagersStoresWithPage(Long userId, Pageable pageable);

    Long getFollowedManagersStoresCount(Long userId);
}

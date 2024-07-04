package like.heocholi.spartaeats.repository;

import like.heocholi.spartaeats.constants.RestaurantType;
import like.heocholi.spartaeats.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StoreRepositoryQuery {
    List<Store> getStoresUserLikedWithPage(Long userId, Pageable pageable);
}

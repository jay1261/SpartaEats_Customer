package like.heocholi.spartaeats.service;

import like.heocholi.spartaeats.constants.ErrorType;
import like.heocholi.spartaeats.entity.Customer;
import like.heocholi.spartaeats.entity.Follow;
import like.heocholi.spartaeats.entity.Manager;
import like.heocholi.spartaeats.exception.FollowException;
import like.heocholi.spartaeats.repository.FollowRepository;
import like.heocholi.spartaeats.repository.ManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final ManagerRepository managerRepository;

    @Transactional
    public String toggleFollow(Customer customer, Long targetId) {
        // 게시글이 없어서 store 가 게시글
        // 팔로우 대상이 매니저이기 때문에 나에게 팔로우는 불가능 함.
        // 팔로우 대상이 존재하는지 확인
        Manager manager = managerRepository.findById(targetId).orElseThrow(
                () -> new FollowException(ErrorType.NOT_FOUND_FOLLOW_TARGET_USER)
        );

        // 이미 팔로우 했는지 확인
        Optional<Follow> follow = followRepository.findByCustomerAndManager(customer, manager);
        if(follow.isPresent()){
            //팔로우 되어있다면 삭제
            followRepository.delete(follow.get());
            return  "id: "+ follow.get().getManager().getId() + " 매니저를 팔로우 취소 했습니다.";
        }

        Follow newFollow = new Follow(customer, manager);
        Follow savedFollow = followRepository.save(newFollow);
        return  "id: "+  savedFollow.getManager().getId() + " 매니저를 팔로우 했습니다.";
    }

}

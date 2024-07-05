package like.heocholi.spartaeats.controller;

import like.heocholi.spartaeats.dto.ResponseMessage;
import like.heocholi.spartaeats.dto.StoreResponseDto;
import like.heocholi.spartaeats.entity.Customer;
import like.heocholi.spartaeats.security.UserDetailsImpl;
import like.heocholi.spartaeats.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/follows")
public class FollowController {

    private final FollowService followService;

    @PostMapping("/{id}")
    public ResponseMessage<?> followManager(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        System.out.println("tq");
        Customer customer = userDetails.getCustomer();
        System.out.println(customer.getId());
        String result = followService.toggleFollow(customer, id);

        ResponseMessage<String> responseMessage = ResponseMessage.<String>builder()
                .message(result)
                .statusCode(HttpStatus.OK.value())
                .build();

        return responseMessage;
    }

    @GetMapping("/stores")
    public ResponseMessage<?> getFollowedUsersStores(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size,
            @AuthenticationPrincipal UserDetailsImpl userDetails){
        Customer customer = userDetails.getCustomer();
        List<StoreResponseDto> followedUsersStores = followService.getFollowedUsersStores(customer.getId(), page-1, size);

        ResponseMessage<List<StoreResponseDto>> responseMessage = ResponseMessage.<List<StoreResponseDto>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("팔로우한 매니저들의 가게 조회 성공")
                .data(followedUsersStores)
                .build();

        return responseMessage;
    }


}

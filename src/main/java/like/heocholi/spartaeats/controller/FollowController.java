package like.heocholi.spartaeats.controller;

import like.heocholi.spartaeats.dto.ResponseMessage;
import like.heocholi.spartaeats.entity.Customer;
import like.heocholi.spartaeats.security.UserDetailsImpl;
import like.heocholi.spartaeats.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/follow")
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
}

package hello.advanced.app.v0;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service    // @Service 안에 @Component가 있어 자동으로 컴포넌트 스캔의 대상이 됨(자동으로 스프링 빈 등록)
@RequiredArgsConstructor    // 생성자 자동 생성
public class OrderServiceV0 {

    private final OrderRepositoryV0 orderRepository;

    public void orderItem(String itemId) {
        orderRepository.save(itemId);
    }
}

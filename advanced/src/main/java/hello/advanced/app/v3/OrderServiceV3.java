package hello.advanced.app.v3;

import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service    // @Service 안에 @Component가 있어 자동으로 컴포넌트 스캔의 대상이 됨(자동으로 스프링 빈 등록)
@RequiredArgsConstructor    // 생성자 자동 생성
public class OrderServiceV3 {

    private final OrderRepositoryV3 orderRepository;
    private final LogTrace trace;

    public void orderItem(String itemId) {

        TraceStatus status = null;
        try {   // 정상 실행시
            status = trace.begin("OrderService.orderItem()");

            orderRepository.save(itemId);
            trace.end(status);
        } catch (Exception e) { // 예외 발생시
            trace.exception(status, e); // -> 예외를 먹음(?)

            throw e;    // (먹었으니 정상 흐름이 되어버림 그래서 )예외를 꼭 다시 던져주어야 한다.
        }
    }
}

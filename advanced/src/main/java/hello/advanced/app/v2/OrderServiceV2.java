package hello.advanced.app.v2;

import hello.advanced.trace.TraceId;
import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.hellotrace.HelloTraceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service    // @Service 안에 @Component가 있어 자동으로 컴포넌트 스캔의 대상이 됨(자동으로 스프링 빈 등록)
@RequiredArgsConstructor    // 생성자 자동 생성
public class OrderServiceV2 {

    private final OrderRepositoryV2 orderRepository;
    private final HelloTraceV2 trace;

    public void orderItem(TraceId traceId, String itemId) {

        TraceStatus status = null;
        try {   // 정상 실행시
            status = trace.beginSync(traceId, "OrderService.orderItem()");

            orderRepository.save(status.getTraceId(), itemId);
            trace.end(status);
        } catch (Exception e) { // 예외 발생시
            trace.exception(status, e); // -> 예외를 먹음(?)

            throw e;    // (먹었으니 정상 흐름이 되어버림 그래서 )예외를 꼭 다시 던져주어야 한다.
        }
    }
}

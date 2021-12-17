package hello.advanced.app.v1;

import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.hellotrace.HelloTraceV1;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository // 안에 @Component가 있어 자동으로 컴포넌트 스캔 대상이 됨(스프링 빈 등록)
@RequiredArgsConstructor
public class OrderRepositoryV1 {

    private final HelloTraceV1 trace;

    public void save(String itemId) {

        TraceStatus status = null;

        try {
            status = trace.begin("OrderRepository.save()");

            // 저장 로직
            if(itemId.equals("ex")) {   // (다양한 예제를 위해)"ex"라는 것이 넘어오면 예외 발생시킬
                throw new IllegalStateException("예외 발생!");
            }
            sleep(1000);    // 상품을 저장하는데 1초 정도 걸린다고 가정

            trace.end(status);
        } catch (Exception e) {
            trace.exception(status, e);
            throw  e;
        }

    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

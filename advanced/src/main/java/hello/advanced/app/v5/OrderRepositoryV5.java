package hello.advanced.app.v5;

import hello.advanced.trace.callback.TraceTemplate;
import hello.advanced.trace.logtrace.LogTrace;
import hello.advanced.trace.template.AbstractTemplate;
import org.springframework.stereotype.Repository;

@Repository // 안에 @Component가 있어 자동으로 컴포넌트 스캔 대상이 됨(스프링 빈 등록)
public class OrderRepositoryV5 {

    private final TraceTemplate template;

    public OrderRepositoryV5(LogTrace trace) {
        this.template = new TraceTemplate(trace);
    }

    public void save(String itemId) {
        template.execute("OrderRepository.save()", () -> {
            // 저장 로직
            if (itemId.equals("ex")) {   // (다양한 예제를 위해)"ex"라는 것이 넘어오면 예외 발생시킬
                throw new IllegalStateException("예외 발생!");
            }
            sleep(1000);    // 상품을 저장하는데 1초 정도 걸린다고 가정

            return null;
        });
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

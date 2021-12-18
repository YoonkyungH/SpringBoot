package hello.advanced.app.v3;

import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // @Controller + @ResponseBody
@RequiredArgsConstructor
public class OrderControllerV3 {

    private final OrderServiceV3 orderService;
    private final LogTrace trace;

    @GetMapping("/v3/request")
    public String request(String itemId) {

        TraceStatus status = null;
        try {   // 정상 실행시
            status = trace.begin("OrderController.request()");

            orderService.orderItem(itemId);
            trace.end(status);
            return "ok";
        } catch (Exception e) { // 예외 발생시
            trace.exception(status, e); // -> 예외를 먹음(?)

            throw e;    // (먹었으니 정상 흐름이 되어버림 그래서 )예외를 꼭 다시 던져주어야 한다.
        }

    }
}


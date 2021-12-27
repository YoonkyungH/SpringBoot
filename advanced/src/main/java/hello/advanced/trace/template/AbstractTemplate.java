package hello.advanced.trace.template;

import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.logtrace.LogTrace;

public abstract class AbstractTemplate<T> { // <T>: 타입에 대한 정보를 객체를 생성하는 시점으로 미룸

    private final LogTrace trace;

    protected AbstractTemplate(LogTrace trace) {
        this.trace = trace;
    }

    public T execute(String message) {

        TraceStatus status = null;
        try {   // 정상 실행시
            status = trace.begin(message);

            // 로직 호출
            T result = call();
            trace.end(status);
            return result;
        } catch (Exception e) { // 예외 발생시
            trace.exception(status, e); // -> 예외를 먹음(?)

            throw e;    // (먹었으니 정상 흐름이 되어버림 그래서 )예외를 꼭 다시 던져주어야 한다.
        }
    }

    protected abstract T call();
}

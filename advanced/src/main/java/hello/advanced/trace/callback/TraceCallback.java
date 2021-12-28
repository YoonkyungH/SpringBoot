package hello.advanced.trace.callback;

public interface TraceCallback<T> { // 제네릭 사용
    T call();
}

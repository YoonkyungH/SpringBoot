package hello.advanced.trace.logtrace;

import hello.advanced.trace.TraceId;
import hello.advanced.trace.TraceStatus;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FieldLogTrace implements LogTrace {

    private static final String START_PREFIX = "-->";
    private static final String COMPLETE_PREFIX = "<--";
    private static final String EX_PREFIX = "<X-";

    // (기존에는 파라미터로 넘겼다면 이제 여기에 보관해두고 쓰는)
    private TraceId traceIdHolder;  // traceId 동기화, !동시성 이슈 발생!

    @Override
    public TraceStatus begin(String message) {
        syncTraceId();
        TraceId traceId = this.traceIdHolder;

        Long startTimeMs = System.currentTimeMillis();

        // 로그 출력
        log.info("[{}] {}{}", traceId.getId(), addSpace(START_PREFIX, traceId.getLevel()), message);

        return new TraceStatus(traceId, startTimeMs, message);
    }

    private void syncTraceId() {
        if(traceIdHolder == null) { // 맨 처음이라면 새로 생성
            traceIdHolder = new TraceId();
        } else {
            traceIdHolder = traceIdHolder.createNextId();
        }
    }

    @Override
    public void end(TraceStatus status) {
        complete(status, null);
    }

    @Override
    public void exception(TraceStatus status, Exception e) {
        complete(status, e);
    }

    private void complete(TraceStatus status, Exception e) {
        Long stopTimeMs = System.currentTimeMillis();
        long resultTimeMs = stopTimeMs - status.getStartTimeMs();
        TraceId traceId = status.getTraceId();

        if (e == null) {
            log.info("[{}] {}{} time={}ms", traceId.getId(),
                    addSpace(COMPLETE_PREFIX, traceId.getLevel()), status.getMessage(),
                    resultTimeMs);
        } else {
            log.info("[{}] {}{} time={}ms ex={}", traceId.getId(),
                    addSpace(EX_PREFIX, traceId.getLevel()), status.getMessage(), resultTimeMs,
                    e.toString());
        }

        releaseTraceId();
    }

    private void releaseTraceId() {
        if(traceIdHolder.isFirstLevel()) {  // 들어갔다 나오는. 마지막 로그인 상태
            traceIdHolder = null;   // destroy 파괴
        } else {
            // 1 2 3 3 2 1 이니까
            traceIdHolder = traceIdHolder.createPreviousId();
        }
    }

    /**
     * level=0:
     * level=1: |-->
     * level=2: |   |-->
     * level=2와 예외 발생: |   |<X-
     * level=1와 예외 발생: |<X-
     */
    private static String addSpace(String prefix, int level) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < level; i++) {
            sb.append( (i == level - 1) ? "|" + prefix : "|   ");
        }

        return sb.toString();
    }
}

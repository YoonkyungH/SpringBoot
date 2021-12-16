package hello.advanced.trace;

import java.util.UUID;

public class TraceId {

    private String id;
    private int level;

    public TraceId() {
        this.id = createId();
        this.level = 0;
    }

    private TraceId(String id, int level) {
        this.id = id;
        this.level = level;
    }

    private String createId() {
        return UUID.randomUUID().toString().substring(0, 8);
        // 원래 randomUUID()로 뽑으면 엄청 긺.
        // 그래서 toString()을 통해 문자열로 뽑고,
        // substring(0, 8)로 앞 8자리만 자르는 과정.
    }

    public TraceId createNextId() {
        return new TraceId(id, level + 1);  // id는 똑같고 level만 하나씩 증가하는 로직
    }

    public TraceId createPreviousId() {
        return new TraceId(id, level - 1);
    }

    public boolean isFirstLevel() { // 첫번째 레벨인지 조금 더 편리하게 판별하기 위한 메소드
        return level == 0;
    }

    public String getId() {
        return id;
    }

    public int getLevel() {
        return level;
    }
}

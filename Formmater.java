import java.time.Duration;
import java.util.List;
import java.util.Objects;

/**
 * - 상단 주석으로 파일 목적을 분명히 밝히기.
 * - Import는 표준 라이브러리 → 서드파티 → 내부 패키지 순서(여기선 표준만 사용).
 * - 클래스 내 요소(상수 → 필드 → 생성자/정적 팩토리 → 공개 API → 비공개 헬퍼) 순서 유지.
 * - 수직 밀도: 논리 블록 사이에 한 줄 공백으로 구분.
 * - 수평 밀도: 연산자 주변 공백, 쉼표 뒤 공백, 불필요한 정렬/세로 맞춤 금지.
 * - 100~120자 전후에서 줄바꿈(여기서는 110자 정도 기준으로 감각적 개행 예시).
 * - 의미 있는 이름 사용, 불필요한 약어 지양.
 * - Guard Clause(빠른 반환)로 중첩 최소화.
 * - 메서드는 위에서 아래로(상위 수준 → 세부) 읽히게 배치.
 */
public final class FormattingExample {

    // ─────────────────────────────────────────────────────────────────────────────
    // 1) 상수(Constant): UPPER_SNAKE_CASE, 도메인 의미가 드러나는 이름
    // ─────────────────────────────────────────────────────────────────────────────
    private static final int DEFAULT_RETRY_LIMIT = 3;
    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(2);

    // ─────────────────────────────────────────────────────────────────────────────
    // 2) 필드(Field): 접근 제어자 명확히, 불변성은 final로 표현
    // ─────────────────────────────────────────────────────────────────────────────
    private final int retryLimit;
    private final Duration timeout;

    // ─────────────────────────────────────────────────────────────────────────────
    // 3) 생성자 / 정적 팩토리: 유효성 검사와 Guard Clause로 중첩 최소화
    // ─────────────────────────────────────────────────────────────────────────────
    private FormattingExample(int retryLimit, Duration timeout) {
        // 의미 있는 매개변수 검사 메시지. 한 줄이 길어질 때는 적절히 개행.
        if (retryLimit < 0) {
            throw new IllegalArgumentException("retryLimit must be >= 0: " + retryLimit);
        }
        this.retryLimit = retryLimit;
        this.timeout = Objects.requireNonNull(timeout, "timeout must not be null");
    }

    public static FormattingExample withDefaults() {
        return new FormattingExample(DEFAULT_RETRY_LIMIT, DEFAULT_TIMEOUT);
    }

    public static FormattingExample of(int retryLimit, Duration timeout) {
        return new FormattingExample(retryLimit, timeout);
    }

    // ─────────────────────────────────────────────────────────────────────────────
    // 4) 공개 API: 상위 수준 정책 → 상세 구현 순서, 짧고 한 가지 일만
    // ─────────────────────────────────────────────────────────────────────────────

    /**
     * 주어진 작업 목록을 순서대로 처리합니다.
     * - 빠른 실패(Guard Clause)로 예외 상황을 초기에 처리해 들여쓰기 최소화.
     * - 변수는 사용 지점 가까이에서 선언.
     */
    public ProcessingResult processTasks(List<String> tasks) {
        if (tasks == null || tasks.isEmpty()) {
            // 세로 밀도: 빠른 반환 전/후에 불필요한 빈 줄을 남발하지 않음
            return ProcessingResult.empty();
        }

        int success = 0;
        int failure = 0;

        for (String task : tasks) {
            if (isBlank(task)) {
                // 수평 밀도: 연산자/쉼표/괄호 주위 공백으로 가독성 확보
                failure++;
                continue;
            }

            // 길어지는 조건/인자는 매개변수 별로 줄바꿈하여 수평 스크롤 방지
            boolean ok = runWithRetry(
                    /* name= */ task,
                    /* maxRetry= */ retryLimit,
                    /* timeout= */ timeout
            );

            if (ok) success++;
            else     failure++;
        }

        return new ProcessingResult(success, failure);
    }

    // ─────────────────────────────────────────────────────────────────────────────
    // 5) 비공개 헬퍼: 상세 구현은 아래로. 이름으로 의도를 드러내고, 정렬로 "모양 맞추기" 금지
    // ─────────────────────────────────────────────────────────────────────────────

    private boolean runWithRetry(String name, int maxRetry, Duration timeout) {
        // 변수 줄맞춤(세로 정렬)은 금지 → 변경 시 확산 비용과 diff 노이즈 증가
        int attempt = 0;
        while (attempt <= maxRetry) {
            attempt++;
            if (invoke(name, timeout)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 실제 작업 호출을 흉내내는 메서드.
     * 한 줄이 너무 길어질 때는, 인자 목록/메서드 체이닝에서 합리적으로 개행합니다.
     */
    private boolean invoke(String name, Duration timeout) {
        // 예시: 이름 길이가 짝수면 성공, 홀수면 실패(단순 예시)
        return name.length() % 2 == 0 && timeout.toMillis() >= 500;
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    // ─────────────────────────────────────────────────────────────────────────────
    // 6) 값 객체/DTO: 불변, 명확한 equals/hashCode/toString. 한 파일에 너무 많이 담지 말 것.
    //    (여기서는 예시를 위해 내부 클래스로 배치)
    // ─────────────────────────────────────────────────────────────────────────────
    public static final class ProcessingResult {
        private final int successCount;
        private final int failureCount;

        public static ProcessingResult empty() {
            return new ProcessingResult(0, 0);
        }

        public ProcessingResult(int successCount, int failureCount) {
            if (successCount < 0 || failureCount < 0) {
                throw new IllegalArgumentException("counts must be >= 0");
            }
            this.successCount = successCount;
            this.failureCount = failureCount;
        }

        public int successCount() {
            return successCount;
        }

        public int failureCount() {
            return failureCount;
        }

        public int total() {
            return successCount + failureCount;
        }

        @Override
        public String toString() {
            return "ProcessingResult{" +
                    "success=" + successCount +
                    ", failure=" + failureCount +
                    ", total=" + total() +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ProcessingResult)) return false;
            ProcessingResult that = (ProcessingResult) o;
            return successCount == that.successCount &&
                    failureCount == that.failureCount;
        }

        @Override
        public int hashCode() {
            return 31 * successCount + failureCount;
        }
    }
}

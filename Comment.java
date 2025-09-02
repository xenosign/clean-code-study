/**
 * Comment
 *  - 주석은 나쁜 코드를 보완하는 수단이 아니라,
 *    코드 자체가 충분히 설명할 수 없을 때만 사용한다.
 *  - 불필요하거나 중복된 주석은 오히려 코드를 해친다.
 *  - 좋은 주석은 "왜(Why)"를 설명하고, 나쁜 주석은 "무엇(What)"을 반복한다.
 */
public class Comment {

    // 나쁜 주석 예시
    // 변수 i에 1을 더한다. (코드만 봐도 알 수 있는 사실을 반복)
    private int counter = 0;

    // 좋은 주석 예시
    // 이 값은 외부 시스템 API의 호출 제한 때문에
    // 1분에 100회 이상 증가하지 않도록 설계되었다.
    private int apiCallCount = 0;

    /**
     * 카운터 증가 메서드
     *
     * 나쁜 주석 예시:
     * // counter 변수를 1 증가시킨다. (코드 자체로 충분히 설명됨)
     *
     * 좋은 주석 예시:
     * 외부 시스템에서 호출 제한이 있기 때문에
     * 일정 조건을 만족할 때만 증가시킨다.
     */
    public void increaseCounter() {
        // 불필요한 주석
        // counter = counter + 1;
        counter++;

        // "왜 이렇게 동작하는지" 설명하는 주석
        // 외부 API 호출 횟수 제한을 피하기 위해
        // 초당 최대 10회까지만 증가를 허용한다.
        if (apiCallCount < 10) {
            apiCallCount++;
        }
    }

    /**
     * 주석으로 코드의 "버그 회피"를 설명하는 경우
     * -> 주석을 달기 전에 코드를 리팩터링하는 것이 우선이다.
     *
     * 올바른 접근:
     *   - 메서드 이름과 구조를 개선해서 주석을 줄인다.
     */
    public boolean isUserAdult(int age) {
        // 나쁜 주석
        // 나이가 18 이상이면 true 반환
        // return age >= 18;

        // 코드 자체로 의미가 명확하면 주석 불필요
        return age >= 18;
    }

    /**
     * 좋은 주석 예시: TODO / FIXME
     * 아직 구현되지 않은 부분이나
     * 리팩터링 대상임을 명확히 표시하는 주석은 유용하다.
     */
    public void paymentProcess() {
        // TODO: 결제 로직 구현 필요 (외부 PG사 API 연동 예정)
        // FIXME: 현재는 mock 데이터만 반환 중
        System.out.println("결제 처리 중... (mock 데이터)");
    }

    // Getter 메서드들
    public int getCounter() {
        return counter;
    }

    public int getApiCallCount() {
        return apiCallCount;
    }

    /**
     * 실행 가능한 main 메서드
     * 각 메서드의 동작을 확인할 수 있다.
     */
    public static void main(String[] args) {
        Comment example = new Comment();

        System.out.println("=== 주석 작성 가이드 예시 실행 ===\n");

        // 초기 상태 출력
        System.out.println("1. 초기 상태:");
        System.out.println("   Counter: " + example.getCounter());
        System.out.println("   API Call Count: " + example.getApiCallCount());
        System.out.println();

        // 카운터 증가 테스트
        System.out.println("2. increaseCounter() 메서드 5번 호출:");
        for (int i = 0; i < 5; i++) {
            example.increaseCounter();
            System.out.println("   호출 " + (i+1) + "회 후 - Counter: " +
                    example.getCounter() + ", API Call Count: " +
                    example.getApiCallCount());
        }
        System.out.println();

        // 성인 여부 확인 테스트
        System.out.println("3. isUserAdult() 메서드 테스트:");
        int[] testAges = {15, 18, 25};
        for (int age : testAges) {
            boolean isAdult = example.isUserAdult(age);
            System.out.println("   나이 " + age + "세: " +
                    (isAdult ? "성인" : "미성년자"));
        }
        System.out.println();

        // 결제 프로세스 테스트
        System.out.println("4. paymentProcess() 메서드 테스트:");
        example.paymentProcess();
        System.out.println();

        // API 호출 제한 테스트
        System.out.println("5. API 호출 제한 테스트 (15번 호출):");
        Comment limitTest = new Comment();
        for (int i = 0; i < 15; i++) {
            limitTest.increaseCounter();
            System.out.printf("   %2d회 호출 후 - API Call Count: %d%n",
                    i+1, limitTest.getApiCallCount());
        }

        System.out.println("\n=== 실행 완료 ===");
        System.out.println("주석 작성 원칙:");
        System.out.println("• 좋은 주석은 '왜(Why)'를 설명한다");
        System.out.println("• 나쁜 주석은 '무엇(What)'을 반복한다");
        System.out.println("• 코드 자체로 설명 가능하면 주석은 불필요하다");
    }
}
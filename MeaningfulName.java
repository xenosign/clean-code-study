import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Queue;
import java.time.Duration;
import java.time.Period;

public class MeaningfulName {
    // 변수 명 - 나쁜 예와 좋은 예
    int d; // 경과 시간 - 의미가 불분명

    // 개선된 변수명들
    int elapsedTime;
    int daysSinceCreation;
    int elapsedTimeInDays;

    // 함수 명 - 나쁜 예
    int[][] theList;

    public List<int[]> getThem() {
        List<int[]> list1 = new ArrayList<int[]>();

        for (int[] x : theList) {
            if (x[0] == 4) list1.add(x);
        }

        return list1;
    }

    // 의미 있는 이름 사용 - 개선된 예
    int STATUS_VALUE = 0;
    int FLAGGED = 1;
    int[][] gameBoard;

    public List<int[]> getFlaggedCells() {
        List<int[]> flaggedCells = new ArrayList<int[]>();

        for (int[] cell : gameBoard) {
            if (cell[STATUS_VALUE] == FLAGGED) flaggedCells.add(cell);
        }

        return flaggedCells;
    }

    // 별도의 클래스를 만들어 사용 - 더욱 개선된 예
    class Cell {
        int[] cell;

        public Cell(int[] cell) {
            this.cell = cell;
        }

        public boolean isFlagged() {
            int STATUS_VALUE = 0;
            int FLAGGED = 1;
            return cell[STATUS_VALUE] == FLAGGED;
        }

        public boolean isMine() {
            int MINE_VALUE = 1;
            int IS_MINE = 1;
            return cell[MINE_VALUE] == IS_MINE;
        }
    }

    List<Cell> gameBoardCells;

    public List<Cell> getFlaggedCells2() {
        List<Cell> flaggedCells = new ArrayList<>();

        for (Cell cell : gameBoardCells) {
            if (cell.isFlagged()) {
                flaggedCells.add(cell);
            }
        }

        return flaggedCells;
    }

    // 나쁜 클래스명과 변수명
    class DtaRcrd102 {
        private Date genymdhms;
        private Date modymdhms;
        private final String pszqint = "102";
    }

    // 의미 있는 클래스명과 변수명으로 개선
    class Customer {
        private Date generationTimestamp;
        private Date modificationTimestamp;
        private final String recordId = "102";
    }

    // 더 많은 의미 있는 이름 예제들
    class User {
        private String firstName;
        private String lastName;
        private String emailAddress;
        private Date registrationDate;
        private boolean isActiveAccount;
        private double totalPurchaseAmount;

        public String getFullName() {
            return firstName + " " + lastName;
        }

        public boolean hasValidEmail() {
            return emailAddress != null && emailAddress.contains("@");
        }

        public String getName() { return firstName + " " + lastName; }
        public String getEmail() { return emailAddress; }
        public double getTotalPurchaseAmount() { return totalPurchaseAmount; }
    }

    // 검색하기 어려운 이름 vs 검색하기 쉬운 이름
    public void easyToSearch() {
        // 나쁜 예 - 매직 넘버와 의미없는 변수명
        int s = 0;
        int[] t = {1, 2, 3, 4};

        for (int j = 0; j < 34; j++) {
            s += (t[j] * 4) / 5;
        }

        // 개선된 예 - 의미 있는 상수와 변수명
        int realDaysPerIdealDay = 4;
        final int WORK_DAYS_PER_WEEK = 5;
        final int NUMBER_OF_TASKS = 5;
        int[] taskEstimate = new int[NUMBER_OF_TASKS];
        int sum = 0;

        for (int j = 0; j < NUMBER_OF_TASKS; j++) {
            int realTaskDays = taskEstimate[j] * realDaysPerIdealDay;
            int realTaskWeeks = (realTaskDays / WORK_DAYS_PER_WEEK);
            sum += realTaskWeeks;
        }
    }

    // 조건문 명확화, 매직 넘버 제거
    public class ScheduleCalculator {

        private static final int REAL_DAYS_PER_IDEAL_DAY = 4;
        private static final int WORK_DAYS_PER_WEEK = 5;
        private static final int NUMBER_OF_TASKS = 5;

        private int[] taskEstimates = new int[NUMBER_OF_TASKS];

        /**
         * 전체 작업 기간(주 단위) 계산
         */
        public int calculateTotalRealTaskWeeks() {
            int totalWeeks = 0;

            for (int estimate : taskEstimates) {
                totalWeeks += calculateRealWeeks(estimate);
            }

            return totalWeeks;
        }

        /**
         * 하나의 작업에 대한 실제 소요 주 계산
         */
        private int calculateRealWeeks(int idealDays) {
            int realTaskDays = idealDays * REAL_DAYS_PER_IDEAL_DAY;
            return realTaskDays / WORK_DAYS_PER_WEEK;
        }
    }

    // 발음하기 어려운 이름 vs 발음하기 쉬운 이름
    class BadNamingExample {
        private Date genymdhms;    // generation year, month, day, hour, minute, second
        private Date modymdhms;    // modification year, month, day, hour, minute, second
        private String pszqint;    // ???
    }

    class GoodNamingExample {
        private Date generationTimestamp;
        private Date modificationTimestamp;
        private String recordId;
    }

    // 클래스 이름은 명사나 명사구를 사용
    class AccountManager {      // 좋은 예
        private List<Account> managedAccounts;

        public void addAccount(Account account) {
            managedAccounts.add(account);
        }

        public Account findAccountById(String accountId) {
            return managedAccounts.stream()
                    .filter(account -> account.getId().equals(accountId))
                    .findFirst()
                    .orElse(null);
        }
    }

    // 메서드 이름은 동사나 동사구를 사용
    class PaymentProcessor {

        public boolean processPayment(Payment payment) {
            return validatePayment(payment) && executeTransaction(payment);
        }

        private boolean validatePayment(Payment payment) {
            return payment.getAmount() > 0 && payment.getAccount() != null;
        }

        private boolean executeTransaction(Payment payment) {
            // 결제 처리 로직
            return true;
        }
    }

    public class OrderProcessor {

        public void processOrder(Order order) {
            if (isHighValueAndUrgent(order)) {
                expediteOrder(order);
            } else {
                processNormally(order);
            }
        }

        /**
         * 고가이며 긴급한 주문인지 여부
         */
        private boolean isHighValueAndUrgent(Order order) {
            final int HIGH_VALUE_THRESHOLD = 1_000_000; // 100만원
            return order.getPrice() > HIGH_VALUE_THRESHOLD && order.isUrgent();
        }

        private void expediteOrder(Order order) {
            System.out.println("고가 & 긴급 주문 → 빠른 처리");
        }

        private void processNormally(Order order) {
            System.out.println("일반 처리");
        }

        static class Order {
            private final int price;
            private final boolean urgent;
            private final String customerId;
            private final List<OrderItem> items;
            private final ShippingAddress shippingAddress;
            private final int quantity;

            public Order(int price, boolean urgent) {
                this.price = price;
                this.urgent = urgent;
                this.customerId = "";
                this.items = new ArrayList<>();
                this.shippingAddress = null;
                this.quantity = 0;
            }

            public Order(String customerId, List<OrderItem> items, ShippingAddress address) {
                this.customerId = customerId;
                this.items = items;
                this.shippingAddress = address;
                this.price = 0;
                this.urgent = false;
                this.quantity = items.size();
            }

            public int getPrice() { return price; }
            public boolean isUrgent() { return urgent; }
            public int getQuantity() { return quantity; }
        }
    }

    // 불용어(noise words) 피하기
    class ProductData {     // 나쁜 예: Data는 불용어
        // ...
    }

    class Product {         // 좋은 예: 간결하고 명확
        private String name;
        private double price;
        private String category;

        public boolean isExpensive() {
            final double EXPENSIVE_THRESHOLD = 100.0;
            return price > EXPENSIVE_THRESHOLD;
        }
    }

    // 검색 가능한 이름 사용하기
    class Constants {
        // 나쁜 예: 매직 넘버
        // if (status == 7) { ... }

        // 좋은 예: 의미 있는 상수
        public static final int WORK_DAYS_PER_WEEK = 5;
        public static final int MAX_STUDENTS_PER_CLASS = 30;
        public static final double DEFAULT_TAX_RATE = 0.1;
        public static final String DEFAULT_ENCODING = "UTF-8";
    }

    // 인터페이스와 구현체 이름 짓기
    interface ShapeFactory {    // 인터페이스는 접두사 I 없이
        Shape createShape(String type);
    }

    class ShapeFactoryImpl implements ShapeFactory {    // 구현체에 Impl 접미사
        @Override
        public Shape createShape(String shapeType) {
            switch (shapeType.toLowerCase()) {
                case "circle":
                    return new Circle();
                case "rectangle":
                    return new Rectangle();
                default:
                    throw new IllegalArgumentException("Unknown shape type: " + shapeType);
            }
        }
    }

    // 도메인별 전용 언어 사용
    class BankAccount {
        private double balance;
        private String accountNumber;
        private AccountType accountType;

        public void deposit(double amount) {
            if (amount > 0) {
                balance += amount;
            }
        }

        public boolean withdraw(double amount) {
            if (canWithdraw(amount)) {
                balance -= amount;
                return true;
            }
            return false;
        }

        private boolean canWithdraw(double amount) {
            return balance >= amount && amount > 0;
        }

        public double getAvailableBalance() {
            return balance;
        }
    }

    enum AccountType {
        SAVINGS,
        CHECKING,
        INVESTMENT
    }

    // 추상적인 이름들
    abstract class Shape {
        abstract double calculateArea();
        abstract double calculatePerimeter();
    }

    class Circle extends Shape {
        private double radius;

        public Circle() {
            this.radius = 1.0;
        }

        public Circle(double radius) {
            this.radius = radius;
        }

        @Override
        double calculateArea() {
            return Math.PI * radius * radius;
        }

        @Override
        double calculatePerimeter() {
            return 2 * Math.PI * radius;
        }
    }

    class Rectangle extends Shape {
        private double width;
        private double height;

        public Rectangle() {
            this.width = 1.0;
            this.height = 1.0;
        }

        public Rectangle(double width, double height) {
            this.width = width;
            this.height = height;
        }

        @Override
        double calculateArea() {
            return width * height;
        }

        @Override
        double calculatePerimeter() {
            return 2 * (width + height);
        }
    }

    // ================ 추가된 내용들 ================

    // 1. 컨텍스트를 명확하게 하는 이름들
    class Address {
        // 나쁜 예: 컨텍스트 불분명
        String firstName;
        String lastName;
        String street;
        String city;
        String state;
        
        // 좋은 예: 컨텍스트가 명확한 이름들
        String accountFirstName;
        String accountLastName;
        String accountStreet;
        String accountCity;
        String accountState;
        
        // 더 좋은 예: 클래스로 컨텍스트 분리
        class AccountHolder {
            String firstName;
            String lastName;
        }
        
        class AddressInfo {
            String street;
            String city;
            String state;
            String zipCode;
        }
    }

    // 2. 부정 조건문 피하기
    class UserValidator {
        // 나쁜 예: 부정문 사용
        public boolean isNotValid(User user) {
            return user.getEmail() == null || user.getName() == null;
        }
        
        public void processUser(User user) {
            if (!isNotValid(user)) {  // 이중 부정으로 혼란
                // 처리 로직
            }
        }
        
        // 좋은 예: 긍정문 사용
        public boolean isValid(User user) {
            return user.getEmail() != null && user.getName() != null;
        }
        
        public void processUserImproved(User user) {
            if (isValid(user)) {  // 훨씬 명확
                // 처리 로직
            }
        }
    }

    // 3. 길이에 따른 변수명 규칙
    class VariableNamingByScope {
        // 짧은 스코프에서는 짧은 이름도 괜찮음
        public int sum(int[] numbers) {
            int s = 0;  // 짧은 스코프에서는 s도 acceptable
            for (int n : numbers) {
                s += n;
            }
            return s;
        }
        
        // 긴 스코프에서는 서술적인 이름 필요
        private UserPreferences globalUserPreferences;  // 클래스 레벨은 명확하게
        private DatabaseConnectionPool connectionPool;
        
        public void processLargeDataset() {
            int totalProcessedRecords = 0;      // 긴 함수에서는 서술적으로
            int failedValidationCount = 0;
            int successfulInsertions = 0;
            
            // 긴 처리 로직...
        }
    }

    // 4. 동사/명사 구분 명확히
    class VerbNounDistinction {
        // 명사형 - 상태나 데이터를 나타냄
        private List<Customer> customerList;
        private PaymentStatus paymentStatus;
        private UserAccount userAccount;
        
        // 동사형 - 액션을 나타냄
        public void validatePayment(Payment payment) { }
        public Customer retrieveCustomer(String id) { return null; }
        public void updateAccount(Account account) { }
        public boolean canProcessOrder(Order order) { return true; }
        
        // boolean 값은 is/has/can 등으로 시작
        public boolean isActiveUser(User user) { return true; }
        public boolean hasValidEmail(String email) { return true; }
        public boolean canAccessResource(User user, Resource resource) { return true; }
    }

    // 5. 도메인 특화 용어 vs 기술 용어 구분
    class DomainVsTechnicalTerms {
        // 나쁜 예: 기술적 용어만 사용
        class DataAccessObject {
            Map<String, Object> entityMap;
            List<Map<String, Object>> resultSet;
            
            public void persistEntity(Map<String, Object> entity) { }
        }
        
        // 좋은 예: 도메인 용어 사용
        class CustomerRepository {
            Map<String, Customer> customers;
            List<Customer> searchResults;
            
            public void saveCustomer(Customer customer) { }
            public Customer findByEmail(String email) { return null; }
            public List<Customer> findActiveCustomers() { return null; }
        }
    }

    // 6. 약어와 줄임말 사용 지침
    class AbbreviationGuidelines {
        // 나쁜 예: 의미 불분명한 약어
        String usrNm;           // user name?
        int calcTtl;            // calculate total?
        Date lstModDt;          // last modified date?
        
        // 좋은 예: 명확한 전체 이름
        String userName;
        int calculatedTotal;
        Date lastModifiedDate;
        
        // 예외: 널리 알려진 약어는 사용 가능
        String httpUrl;         // HTTP는 널리 알려진 약어
        String xmlDocument;     // XML도 마찬가지
        int maxRetryCount;      // max는 maximum의 표준 약어
        String idNumber;        // id는 identifier의 표준 약어
    }

    // 7. 단수/복수 구분 명확히
    class SingularPluralClarity {
        // 명확한 단수/복수 구분
        Customer customer;              // 단일 객체
        List<Customer> customers;       // 복수 객체
        Set<String> uniqueEmails;       // 복수 객체
        
        // 컬렉션 타입별 명명
        List<Order> orderList;          // 순서가 중요한 경우
        Set<String> emailSet;           // 중복 제거가 중요한 경우
        Map<String, User> userMap;      // 키-값 매핑이 중요한 경우
        Queue<Task> taskQueue;          // FIFO가 중요한 경우
        
        public void processOrder(Order order) { }           // 단일 처리
        public void processOrders(List<Order> orders) { }   // 복수 처리
    }

    // 8. 상태를 나타내는 이름들
    enum OrderStatus {
        // 나쁜 예: 상태가 불분명
        // STATUS_1, STATUS_2, STATUS_3,
        
        // 좋은 예: 명확한 상태명
        PENDING,
        CONFIRMED,
        PROCESSING,
        SHIPPED,
        DELIVERED,
        CANCELLED
    }
    
    class OrderStateMachine {
        public boolean canTransitionTo(OrderStatus from, OrderStatus to) {
            // 상태 전환 로직
            return switch (from) {
                case PENDING -> to == OrderStatus.CONFIRMED || to == OrderStatus.CANCELLED;
                case CONFIRMED -> to == OrderStatus.PROCESSING || to == OrderStatus.CANCELLED;
                case PROCESSING -> to == OrderStatus.SHIPPED || to == OrderStatus.CANCELLED;
                case SHIPPED -> to == OrderStatus.DELIVERED;
                case DELIVERED, CANCELLED -> false;
            };
        }
    }

    // 9. 예외 상황을 명확히 하는 이름
    class ExceptionNaming {
        // 명확한 예외 상황 표현
        public class InvalidEmailFormatException extends Exception {
            public InvalidEmailFormatException(String email) {
                super("Invalid email format: " + email);
            }
        }
        
        public class InsufficientFundsException extends Exception {
            private final double requestedAmount;
            private final double availableBalance;
            
            public InsufficientFundsException(double requested, double available) {
                super(String.format("Insufficient funds: requested=%.2f, available=%.2f", 
                                  requested, available));
                this.requestedAmount = requested;
                this.availableBalance = available;
            }
        }
        
        public class AccountNotFoundException extends Exception {
            public AccountNotFoundException(String accountId) {
                super("Account not found: " + accountId);
            }
        }
    }

    // 10. 시간 관련 명명 규칙
    class TimeRelatedNaming {
        // 명확한 시간 단위 표시
        private static final int SESSION_TIMEOUT_MINUTES = 30;
        private static final long CACHE_EXPIRY_SECONDS = 300;
        private static final int MAX_RETRY_ATTEMPTS = 3;
        private static final long RETRY_DELAY_MILLISECONDS = 1000;
        
        // 시점을 명확히 구분
        Date orderCreatedAt;        // 생성 시점
        Date orderUpdatedAt;        // 수정 시점
        Date orderExpiredAt;        // 만료 시점
        Date orderProcessedAt;      // 처리 시점
        
        // 기간을 명확히 표현
        Duration processingDuration;
        Period subscriptionPeriod;
        long sessionLifetimeMinutes;
    }

    // 11. 비즈니스 규칙을 이름에 반영
    class BusinessRuleNaming {
        private static final double VIP_CUSTOMER_THRESHOLD = 1_000_000.0;
        private static final int BULK_ORDER_MINIMUM_QUANTITY = 100;
        private static final double PREMIUM_SHIPPING_COST = 15.99;
        
        public boolean isVipCustomer(Customer customer) {
            return customer.getTotalPurchaseAmount() >= VIP_CUSTOMER_THRESHOLD;
        }
        
        public boolean qualifiesForBulkDiscount(Order order) {
            return order.getQuantity() >= BULK_ORDER_MINIMUM_QUANTITY;
        }
        
        public double calculateShippingCost(Order order, ShippingOption option) {
            return switch (option) {
                case STANDARD -> 0.0;
                case EXPRESS -> PREMIUM_SHIPPING_COST;
                case OVERNIGHT -> PREMIUM_SHIPPING_COST * 2;
            };
        }
    }

    // 12. Factory 패턴에서의 명명
    class FactoryNaming {
        interface NotificationFactory {
            Notification createNotification(NotificationType type, String message);
        }
        
        class EmailNotificationFactory implements NotificationFactory {
            @Override
            public Notification createNotification(NotificationType type, String message) {
                return new EmailNotification(message);
            }
        }
        
        class SmsNotificationFactory implements NotificationFactory {
            @Override
            public Notification createNotification(NotificationType type, String message) {
                return new SmsNotification(message);
            }
        }
        
        // Builder 패턴 명명
        class OrderBuilder {
            private String customerId;
            private List<OrderItem> items = new ArrayList<>();
            private ShippingAddress shippingAddress;
            
            public OrderBuilder forCustomer(String customerId) {
                this.customerId = customerId;
                return this;
            }
            
            public OrderBuilder addItem(OrderItem item) {
                this.items.add(item);
                return this;
            }
            
            public OrderBuilder withShippingAddress(ShippingAddress address) {
                this.shippingAddress = address;
                return this;
            }
            
            public Order build() {
                return new Order(customerId, items, shippingAddress);
            }
        }
    }

    // 지원 클래스들
    static class Account {
        private String id;
        private String ownerName;
        private double balance;

        public Account(String id, String ownerName, double balance) {
            this.id = id;
            this.ownerName = ownerName;
            this.balance = balance;
        }

        public String getId() { return id; }
        public String getOwnerName() { return ownerName; }
        public double getBalance() { return balance; }
    }

    static class Payment {
        private double amount;
        private Account account;

        public Payment(double amount, Account account) {
            this.amount = amount;
            this.account = account;
        }

        public double getAmount() { return amount; }
        public Account getAccount() { return account; }
    }

    // 추가 지원 클래스들
    static class OrderItem { }
    static class ShippingAddress { }
    static class Resource { }
    static class UserAccount { }
    static class UserPreferences { }
    static class DatabaseConnectionPool { }
    static class Task { }
    
    enum PaymentStatus { PENDING, COMPLETED, FAILED }
    enum ShippingOption { STANDARD, EXPRESS, OVERNIGHT }
    enum NotificationType { EMAIL, SMS, PUSH }
    
    interface Notification { }
    static class EmailNotification implements Notification {
        public EmailNotification(String message) { }
    }
    static class SmsNotification implements Notification {
        public SmsNotification(String message) { }
    }
}
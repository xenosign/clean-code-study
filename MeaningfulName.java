import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.Queue;
import java.util.LinkedList;
import java.time.Duration;
import java.time.Period;
import java.util.stream.Collectors;

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

        // Constructor 추가
        public Customer() {
            this.generationTimestamp = new Date();
            this.modificationTimestamp = new Date();
        }

        // Getters 추가
        public Date getGenerationTimestamp() { return generationTimestamp; }
        public Date getModificationTimestamp() { return modificationTimestamp; }
        public String getRecordId() { return recordId; }

        // Business method 추가
        public void updateRecord() {
            this.modificationTimestamp = new Date();
        }
    }

    // 더 많은 의미 있는 이름 예제들
    class User {
        private String firstName;
        private String lastName;
        private String emailAddress;
        private Date registrationDate;
        private boolean isActiveAccount;
        private double totalPurchaseAmount;

        // Constructor 추가
        public User(String firstName, String lastName, String emailAddress) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.emailAddress = emailAddress;
            this.registrationDate = new Date();
            this.isActiveAccount = true;
            this.totalPurchaseAmount = 0.0;
        }

        public String getFullName() {
            return firstName + " " + lastName;
        }

        public boolean hasValidEmail() {
            return emailAddress != null && emailAddress.contains("@");
        }

        public String getName() { return firstName + " " + lastName; }
        public String getEmail() { return emailAddress; }
        public double getTotalPurchaseAmount() { return totalPurchaseAmount; }

        // 추가 getters/setters
        public String getFirstName() { return firstName; }
        public String getLastName() { return lastName; }
        public Date getRegistrationDate() { return registrationDate; }
        public boolean isActive() { return isActiveAccount; }

        public void deactivateAccount() { this.isActiveAccount = false; }
        public void addPurchase(double amount) { this.totalPurchaseAmount += amount; }
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

        // Constructor 추가
        public ScheduleCalculator() {
            // 기본 추정치로 초기화
            for (int i = 0; i < NUMBER_OF_TASKS; i++) {
                taskEstimates[i] = 1; // 1일로 기본 설정
            }
        }

        public ScheduleCalculator(int[] estimates) {
            this.taskEstimates = estimates.clone();
        }

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

        // 추가 유틸리티 메서드
        public void setTaskEstimate(int taskIndex, int days) {
            if (taskIndex >= 0 && taskIndex < NUMBER_OF_TASKS) {
                taskEstimates[taskIndex] = days;
            }
        }

        public int getTaskEstimate(int taskIndex) {
            return (taskIndex >= 0 && taskIndex < NUMBER_OF_TASKS) ?
                    taskEstimates[taskIndex] : 0;
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

        // Constructor와 메서드 추가
        public GoodNamingExample(String recordId) {
            this.recordId = recordId;
            this.generationTimestamp = new Date();
            this.modificationTimestamp = new Date();
        }

        public void touch() {
            this.modificationTimestamp = new Date();
        }

        // Getters
        public Date getGenerationTimestamp() { return generationTimestamp; }
        public Date getModificationTimestamp() { return modificationTimestamp; }
        public String getRecordId() { return recordId; }
    }

    // 클래스 이름은 명사나 명사구를 사용
    class AccountManager {
        private List<Account> managedAccounts;

        // Constructor 추가
        public AccountManager() {
            this.managedAccounts = new ArrayList<>();
        }

        public void addAccount(Account account) {
            if (account != null && !managedAccounts.contains(account)) {
                managedAccounts.add(account);
            }
        }

        public Account findAccountById(String accountId) {
            return managedAccounts.stream()
                    .filter(account -> account.getId().equals(accountId))
                    .findFirst()
                    .orElse(null);
        }

        // 추가 메서드들
        public boolean removeAccount(String accountId) {
            return managedAccounts.removeIf(account -> account.getId().equals(accountId));
        }

        public List<Account> getAllAccounts() {
            return new ArrayList<>(managedAccounts);
        }

        public int getAccountCount() {
            return managedAccounts.size();
        }
    }

    // 메서드 이름은 동사나 동사구를 사용
    class PaymentProcessor {

        public boolean processPayment(Payment payment) {
            return validatePayment(payment) && executeTransaction(payment);
        }

        private boolean validatePayment(Payment payment) {
            return payment != null &&
                    payment.getAmount() > 0 &&
                    payment.getAccount() != null;
        }

        private boolean executeTransaction(Payment payment) {
            // 실제 결제 처리 로직 시뮬레이션
            try {
                // 결제 처리 로직
                Thread.sleep(100); // 처리 시간 시뮬레이션
                return true;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return false;
            }
        }

        // 추가 메서드들
        public boolean refundPayment(Payment payment) {
            if (validatePayment(payment)) {
                // 환불 처리 로직
                return true;
            }
            return false;
        }

        public List<Payment> getFailedPayments(List<Payment> payments) {
            return payments.stream()
                    .filter(payment -> !validatePayment(payment))
                    .collect(Collectors.toList());
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
            // 실제 expedite 로직
        }

        private void processNormally(Order order) {
            System.out.println("일반 처리");
            // 실제 일반 처리 로직
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
                this.items = new ArrayList<>(items);
                this.shippingAddress = address;
                this.price = calculateTotalPrice(items);
                this.urgent = false;
                this.quantity = items.size();
            }

            // 가격 계산 메서드 추가
            private int calculateTotalPrice(List<OrderItem> items) {
                return items.stream()
                        .mapToInt(item -> item.getPrice() * item.getQuantity())
                        .sum();
            }

            public int getPrice() { return price; }
            public boolean isUrgent() { return urgent; }
            public int getQuantity() { return quantity; }
            public String getCustomerId() { return customerId; }
            public List<OrderItem> getItems() { return new ArrayList<>(items); }
            public ShippingAddress getShippingAddress() { return shippingAddress; }
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

        // Constructor 추가
        public Product(String name, double price, String category) {
            this.name = name;
            this.price = price;
            this.category = category;
        }

        public boolean isExpensive() {
            final double EXPENSIVE_THRESHOLD = 100.0;
            return price > EXPENSIVE_THRESHOLD;
        }

        // Getters and setters 추가
        public String getName() { return name; }
        public double getPrice() { return price; }
        public String getCategory() { return category; }

        public void setPrice(double price) { this.price = price; }
        public void setCategory(String category) { this.category = category; }
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
        public static final int MAX_LOGIN_ATTEMPTS = 3;
        public static final long SESSION_TIMEOUT_MINUTES = 30;
    }

    // 인터페이스와 구현체 이름 짓기
    interface ShapeFactory {    // 인터페이스는 접두사 I 없이
        Shape createShape(String type);
    }

    class ShapeFactoryImpl implements ShapeFactory {    // 구현체에 Impl 접미사
        @Override
        public Shape createShape(String shapeType) {
            if (shapeType == null || shapeType.trim().isEmpty()) {
                throw new IllegalArgumentException("Shape type cannot be null or empty");
            }

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

        // Constructor 추가
        public BankAccount(String accountNumber, AccountType accountType) {
            this.accountNumber = accountNumber;
            this.accountType = accountType;
            this.balance = 0.0;
        }

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

        // 추가 메서드들
        public String getAccountNumber() { return accountNumber; }
        public AccountType getAccountType() { return accountType; }

        public boolean transfer(BankAccount targetAccount, double amount) {
            if (withdraw(amount)) {
                targetAccount.deposit(amount);
                return true;
            }
            return false;
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

        // 공통 메서드 추가
        public String getShapeInfo() {
            return String.format("Area: %.2f, Perimeter: %.2f",
                    calculateArea(), calculatePerimeter());
        }
    }

    class Circle extends Shape {
        private double radius;

        public Circle() {
            this.radius = 1.0;
        }

        public Circle(double radius) {
            if (radius <= 0) {
                throw new IllegalArgumentException("Radius must be positive");
            }
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

        public double getRadius() { return radius; }
        public void setRadius(double radius) {
            if (radius <= 0) {
                throw new IllegalArgumentException("Radius must be positive");
            }
            this.radius = radius;
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
            if (width <= 0 || height <= 0) {
                throw new IllegalArgumentException("Width and height must be positive");
            }
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

        public double getWidth() { return width; }
        public double getHeight() { return height; }

        public void setDimensions(double width, double height) {
            if (width <= 0 || height <= 0) {
                throw new IllegalArgumentException("Width and height must be positive");
            }
            this.width = width;
            this.height = height;
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

            public AccountHolder(String firstName, String lastName) {
                this.firstName = firstName;
                this.lastName = lastName;
            }

            public String getFullName() {
                return firstName + " " + lastName;
            }
        }

        class AddressInfo {
            String street;
            String city;
            String state;
            String zipCode;

            public AddressInfo(String street, String city, String state, String zipCode) {
                this.street = street;
                this.city = city;
                this.state = state;
                this.zipCode = zipCode;
            }

            public String getFormattedAddress() {
                return String.format("%s, %s, %s %s", street, city, state, zipCode);
            }
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
                System.out.println("Processing user: " + user.getName());
            }
        }

        // 좋은 예: 긍정문 사용
        public boolean isValid(User user) {
            return user != null && user.getEmail() != null && user.getName() != null;
        }

        public void processUserImproved(User user) {
            if (isValid(user)) {  // 훨씬 명확
                // 처리 로직
                System.out.println("Processing user: " + user.getName());
            }
        }

        // 추가 검증 메서드들
        public boolean hasValidEmailFormat(User user) {
            return isValid(user) && user.hasValidEmail();
        }

        public List<String> getValidationErrors(User user) {
            List<String> errors = new ArrayList<>();
            if (user == null) {
                errors.add("User cannot be null");
                return errors;
            }
            if (user.getName() == null || user.getName().trim().isEmpty()) {
                errors.add("User name is required");
            }
            if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
                errors.add("Email is required");
            }
            if (!user.hasValidEmail()) {
                errors.add("Email format is invalid");
            }
            return errors;
        }
    }

    // 3. 길이에 따른 변수명 규칙
    class VariableNamingByScope {
        // 긴 스코프에서는 서술적인 이름 필요
        private UserPreferences globalUserPreferences;  // 클래스 레벨은 명확하게
        private DatabaseConnectionPool connectionPool;

        // 짧은 스코프에서는 짧은 이름도 괜찮음
        public int sum(int[] numbers) {
            int s = 0;  // 짧은 스코프에서는 s도 acceptable
            for (int n : numbers) {
                s += n;
            }
            return s;
        }

        public void processLargeDataset(List<DataRecord> records) {
            int totalProcessedRecords = 0;      // 긴 함수에서는 서술적으로
            int failedValidationCount = 0;
            int successfulInsertions = 0;

            for (DataRecord record : records) {
                if (validateRecord(record)) {
                    if (insertRecord(record)) {
                        successfulInsertions++;
                    }
                } else {
                    failedValidationCount++;
                }
                totalProcessedRecords++;
            }

            System.out.printf("Processed: %d, Success: %d, Failed: %d%n",
                    totalProcessedRecords, successfulInsertions, failedValidationCount);
        }

        private boolean validateRecord(DataRecord record) {
            return record != null && record.isValid();
        }

        private boolean insertRecord(DataRecord record) {
            // 데이터베이스 삽입 시뮬레이션
            return true;
        }
    }

    // 4. 동사/명사 구분 명확히
    class VerbNounDistinction {
        // 명사형 - 상태나 데이터를 나타냄
        private List<Customer> customerList;
        private PaymentStatus paymentStatus;
        private UserAccount userAccount;

        public VerbNounDistinction() {
            this.customerList = new ArrayList<>();
            this.paymentStatus = PaymentStatus.PENDING;
            this.userAccount = new UserAccount();
        }

        // 동사형 - 액션을 나타냄
        public void validatePayment(Payment payment) {
            System.out.println("Validating payment: " + payment.getAmount());
        }

        public Customer retrieveCustomer(String id) {
            return customerList.stream()
                    .filter(c -> c.getRecordId().equals(id))
                    .findFirst()
                    .orElse(null);
        }

        public void updateAccount(Account account) {
            System.out.println("Updating account: " + account.getId());
        }

        public boolean canProcessOrder(Order order) {
            return order != null && order.getPrice() > 0;
        }

        // boolean 값은 is/has/can 등으로 시작
        public boolean isActiveUser(User user) {
            return user != null && user.isActive();
        }

        public boolean hasValidEmail(String email) {
            return email != null && email.contains("@") && email.contains(".");
        }

        public boolean canAccessResource(User user, Resource resource) {
            return user != null && user.isActive() && resource != null;
        }
    }

    // 5. 도메인 특화 용어 vs 기술 용어 구분
    class DomainVsTechnicalTerms {
        // 나쁜 예: 기술적 용어만 사용
        class DataAccessObject {
            Map<String, Object> entityMap;
            List<Map<String, Object>> resultSet;

            public DataAccessObject() {
                this.entityMap = new HashMap<>();
                this.resultSet = new ArrayList<>();
            }

            public void persistEntity(Map<String, Object> entity) {
                entityMap.putAll(entity);
            }
        }

        // 좋은 예: 도메인 용어 사용
        class CustomerRepository {
            Map<String, Customer> customers;
            List<Customer> searchResults;

            public CustomerRepository() {
                this.customers = new HashMap<>();
                this.searchResults = new ArrayList<>();
            }

            public void saveCustomer(Customer customer) {
                customers.put(customer.getRecordId(), customer);
            }

            public Customer findByEmail(String email) {
                return customers.values().stream()
                        .filter(customer -> customer.getRecordId().equals(email))
                        .findFirst()
                        .orElse(null);
            }

            public List<Customer> findActiveCustomers() {
                return customers.values().stream()
                        .filter(customer -> customer.getRecordId() != null)
                        .collect(Collectors.toList());
            }
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

        // Constructor와 메서드 추가
        public AbbreviationGuidelines() {
            this.userName = "";
            this.calculatedTotal = 0;
            this.lastModifiedDate = new Date();
            this.httpUrl = "";
            this.xmlDocument = "";
            this.maxRetryCount = 3;
            this.idNumber = "";
        }

        public void updateUserInfo(String name) {
            this.userName = name;
            this.lastModifiedDate = new Date();
        }
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

        public SingularPluralClarity() {
            this.customers = new ArrayList<>();
            this.uniqueEmails = new HashSet<>();
            this.orderList = new ArrayList<>();
            this.emailSet = new HashSet<>();
            this.userMap = new HashMap<>();
            this.taskQueue = new LinkedList<>();
        }

        public void processOrder(Order order) {
            if (order != null) {
                orderList.add(order);
            }
        }

        public void processOrders(List<Order> orders) {
            if (orders != null) {
                orderList.addAll(orders);
            }
        }

        public void addUniqueEmail(String email) {
            if (email != null && !email.trim().isEmpty()) {
                uniqueEmails.add(email.toLowerCase());
                emailSet.add(email.toLowerCase());
            }
        }

        public User findUser(String userId) {
            return userMap.get(userId);
        }

        public void addUser(String userId, User user) {
            userMap.put(userId, user);
        }

        public Task getNextTask() {
            return taskQueue.poll();
        }

        public void addTask(Task task) {
            taskQueue.offer(task);
        }
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

        public List<OrderStatus> getValidNextStates(OrderStatus currentStatus) {
            List<OrderStatus> validStates = new ArrayList<>();
            for (OrderStatus status : OrderStatus.values()) {
                if (canTransitionTo(currentStatus, status)) {
                    validStates.add(status);
                }
            }
            return validStates;
        }

        public boolean isTerminalState(OrderStatus status) {
            return status == OrderStatus.DELIVERED || status == OrderStatus.CANCELLED;
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

            public double getRequestedAmount() { return requestedAmount; }
            public double getAvailableBalance() { return availableBalance; }
            public double getShortfall() { return requestedAmount - availableBalance; }
        }

        public class AccountNotFoundException extends Exception {
            private final String accountId;

            public AccountNotFoundException(String accountId) {
                super("Account not found: " + accountId);
                this.accountId = accountId;
            }

            public String getAccountId() { return accountId; }
        }

        public class DuplicateEmailException extends Exception {
            private final String email;

            public DuplicateEmailException(String email) {
                super("Email already exists: " + email);
                this.email = email;
            }

            public String getEmail() { return email; }
        }

        public class InvalidTransactionAmountException extends Exception {
            private final double amount;

            public InvalidTransactionAmountException(double amount) {
                super("Invalid transaction amount: " + amount);
                this.amount = amount;
            }

            public double getAmount() { return amount; }
        }
    }

    // 10. 시간 관련 명명 규칙
    class TimeRelatedNaming {
        // 명확한 시간 단위 표시
        private static final int SESSION_TIMEOUT_MINUTES = 30;
        private static final long CACHE_EXPIRY_SECONDS = 300;
        private static final int MAX_RETRY_ATTEMPTS = 3;
        private static final long RETRY_DELAY_MILLISECONDS = 1000;
        private static final int PASSWORD_EXPIRY_DAYS = 90;
        private static final long DATABASE_CONNECTION_TIMEOUT_SECONDS = 30;

        // 시점을 명확히 구분
        Date orderCreatedAt;        // 생성 시점
        Date orderUpdatedAt;        // 수정 시점
        Date orderExpiredAt;        // 만료 시점
        Date orderProcessedAt;      // 처리 시점
        Date lastLoginAt;           // 마지막 로그인 시점
        Date passwordChangedAt;     // 비밀번호 변경 시점

        // 기간을 명확히 표현
        Duration processingDuration;
        Period subscriptionPeriod;
        long sessionLifetimeMinutes;

        public TimeRelatedNaming() {
            Date now = new Date();
            this.orderCreatedAt = now;
            this.orderUpdatedAt = now;
            this.lastLoginAt = now;
            this.passwordChangedAt = now;
            this.processingDuration = Duration.ofMinutes(5);
            this.subscriptionPeriod = Period.ofMonths(12);
            this.sessionLifetimeMinutes = SESSION_TIMEOUT_MINUTES;
        }

        public boolean isSessionExpired() {
            long currentTime = System.currentTimeMillis();
            long lastActivity = lastLoginAt.getTime();
            long sessionDurationMs = sessionLifetimeMinutes * 60 * 1000;
            return (currentTime - lastActivity) > sessionDurationMs;
        }

        public boolean isPasswordExpired() {
            long currentTime = System.currentTimeMillis();
            long passwordAge = passwordChangedAt.getTime();
            long expiryDurationMs = PASSWORD_EXPIRY_DAYS * 24 * 60 * 60 * 1000;
            return (currentTime - passwordAge) > expiryDurationMs;
        }

        public void updateLastActivity() {
            this.lastLoginAt = new Date();
        }

        public void changePassword() {
            this.passwordChangedAt = new Date();
        }
    }

    // 11. 비즈니스 규칙을 이름에 반영
    class BusinessRuleNaming {
        private static final double VIP_CUSTOMER_THRESHOLD = 1_000_000.0;
        private static final int BULK_ORDER_MINIMUM_QUANTITY = 100;
        private static final double PREMIUM_SHIPPING_COST = 15.99;
        private static final double FREE_SHIPPING_THRESHOLD = 50.0;
        private static final double VIP_DISCOUNT_RATE = 0.15;
        private static final double BULK_DISCOUNT_RATE = 0.10;

        public boolean isVipCustomer(Customer customer) {
            // VIP 고객 여부 판단
            return customer != null;  // 실제로는 구매 금액 등을 확인
        }

        public boolean qualifiesForBulkDiscount(Order order) {
            return order != null && order.getQuantity() >= BULK_ORDER_MINIMUM_QUANTITY;
        }

        public boolean qualifiesForFreeShipping(Order order) {
            return order != null && order.getPrice() >= FREE_SHIPPING_THRESHOLD;
        }

        public double calculateShippingCost(Order order, ShippingOption option) {
            if (qualifiesForFreeShipping(order)) {
                return 0.0;
            }

            return switch (option) {
                case STANDARD -> 0.0;
                case EXPRESS -> PREMIUM_SHIPPING_COST;
                case OVERNIGHT -> PREMIUM_SHIPPING_COST * 2;
            };
        }

        public double calculateDiscountAmount(Customer customer, Order order) {
            if (isVipCustomer(customer)) {
                return order.getPrice() * VIP_DISCOUNT_RATE;
            } else if (qualifiesForBulkDiscount(order)) {
                return order.getPrice() * BULK_DISCOUNT_RATE;
            }
            return 0.0;
        }

        public double calculateFinalPrice(Customer customer, Order order, ShippingOption shippingOption) {
            double basePrice = order.getPrice();
            double discount = calculateDiscountAmount(customer, order);
            double shipping = calculateShippingCost(order, shippingOption);
            return basePrice - discount + shipping;
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

        class PushNotificationFactory implements NotificationFactory {
            @Override
            public Notification createNotification(NotificationType type, String message) {
                return new PushNotification(message);
            }
        }

        // Factory Provider 패턴
        class NotificationFactoryProvider {
            private Map<NotificationType, NotificationFactory> factories;

            public NotificationFactoryProvider() {
                factories = new HashMap<>();
                factories.put(NotificationType.EMAIL, new EmailNotificationFactory());
                factories.put(NotificationType.SMS, new SmsNotificationFactory());
                factories.put(NotificationType.PUSH, new PushNotificationFactory());
            }

            public NotificationFactory getFactory(NotificationType type) {
                NotificationFactory factory = factories.get(type);
                if (factory == null) {
                    throw new IllegalArgumentException("Unsupported notification type: " + type);
                }
                return factory;
            }
        }

        // Builder 패턴 명명
        class OrderBuilder {
            private String customerId;
            private List<OrderItem> items = new ArrayList<>();
            private ShippingAddress shippingAddress;
            private boolean isUrgent = false;
            private OrderStatus status = OrderStatus.PENDING;

            public OrderBuilder forCustomer(String customerId) {
                this.customerId = customerId;
                return this;
            }

            public OrderBuilder addItem(OrderItem item) {
                if (item != null) {
                    this.items.add(item);
                }
                return this;
            }

            public OrderBuilder addItems(List<OrderItem> items) {
                if (items != null) {
                    this.items.addAll(items);
                }
                return this;
            }

            public OrderBuilder withShippingAddress(ShippingAddress address) {
                this.shippingAddress = address;
                return this;
            }

            public OrderBuilder markAsUrgent() {
                this.isUrgent = true;
                return this;
            }

            public OrderBuilder withStatus(OrderStatus status) {
                this.status = status;
                return this;
            }

            public Order build() {
                if (customerId == null || customerId.trim().isEmpty()) {
                    throw new IllegalStateException("Customer ID is required");
                }
                if (items.isEmpty()) {
                    throw new IllegalStateException("At least one item is required");
                }
                if (shippingAddress == null) {
                    throw new IllegalStateException("Shipping address is required");
                }

                return new Order(customerId, items, shippingAddress);
            }

            public void reset() {
                this.customerId = null;
                this.items.clear();
                this.shippingAddress = null;
                this.isUrgent = false;
                this.status = OrderStatus.PENDING;
            }
        }
    }

    // 지원 클래스들
    static class Account {
        private String id;
        private String ownerName;
        private double balance;

        public Account(String id, String ownerName, double balance) {
            if (id == null || id.trim().isEmpty()) {
                throw new IllegalArgumentException("Account ID cannot be null or empty");
            }
            if (ownerName == null || ownerName.trim().isEmpty()) {
                throw new IllegalArgumentException("Owner name cannot be null or empty");
            }
            if (balance < 0) {
                throw new IllegalArgumentException("Initial balance cannot be negative");
            }

            this.id = id;
            this.ownerName = ownerName;
            this.balance = balance;
        }

        public String getId() { return id; }
        public String getOwnerName() { return ownerName; }
        public double getBalance() { return balance; }

        public void updateBalance(double newBalance) {
            if (newBalance < 0) {
                throw new IllegalArgumentException("Balance cannot be negative");
            }
            this.balance = newBalance;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Account account = (Account) obj;
            return id.equals(account.id);
        }

        @Override
        public int hashCode() {
            return id.hashCode();
        }
    }

    static class Payment {
        private double amount;
        private Account account;
        private Date processedAt;
        private PaymentStatus status;

        public Payment(double amount, Account account) {
            if (amount <= 0) {
                throw new IllegalArgumentException("Payment amount must be positive");
            }
            if (account == null) {
                throw new IllegalArgumentException("Account cannot be null");
            }

            this.amount = amount;
            this.account = account;
            this.processedAt = new Date();
            this.status = PaymentStatus.PENDING;
        }

        public double getAmount() { return amount; }
        public Account getAccount() { return account; }
        public Date getProcessedAt() { return processedAt; }
        public PaymentStatus getStatus() { return status; }

        public void markAsCompleted() {
            this.status = PaymentStatus.COMPLETED;
            this.processedAt = new Date();
        }

        public void markAsFailed() {
            this.status = PaymentStatus.FAILED;
            this.processedAt = new Date();
        }

        public boolean isCompleted() {
            return status == PaymentStatus.COMPLETED;
        }

        public boolean isFailed() {
            return status == PaymentStatus.FAILED;
        }
    }

    // 추가 지원 클래스들
    static class OrderItem {
        private String productId;
        private int quantity;
        private int price;

        public OrderItem(String productId, int quantity, int price) {
            this.productId = productId;
            this.quantity = quantity;
            this.price = price;
        }

        public String getProductId() { return productId; }
        public int getQuantity() { return quantity; }
        public int getPrice() { return price; }
        public int getTotalPrice() { return quantity * price; }
    }

    static class ShippingAddress {
        private String street;
        private String city;
        private String state;
        private String zipCode;
        private String country;

        public ShippingAddress(String street, String city, String state, String zipCode, String country) {
            this.street = street;
            this.city = city;
            this.state = state;
            this.zipCode = zipCode;
            this.country = country;
        }

        public String getFormattedAddress() {
            return String.format("%s, %s, %s %s, %s", street, city, state, zipCode, country);
        }
    }

    static class Resource {
        private String resourceId;
        private String resourceType;
        private boolean isPublic;

        public Resource(String resourceId, String resourceType, boolean isPublic) {
            this.resourceId = resourceId;
            this.resourceType = resourceType;
            this.isPublic = isPublic;
        }

        public String getResourceId() { return resourceId; }
        public String getResourceType() { return resourceType; }
        public boolean isPublic() { return isPublic; }
    }

    static class UserAccount {
        private String accountId;
        private Date createdAt;
        private boolean isActive;

        public UserAccount() {
            this.accountId = generateAccountId();
            this.createdAt = new Date();
            this.isActive = true;
        }

        private String generateAccountId() {
            return "ACC_" + System.currentTimeMillis();
        }

        public String getAccountId() { return accountId; }
        public Date getCreatedAt() { return createdAt; }
        public boolean isActive() { return isActive; }
    }

    static class UserPreferences {
        private String language;
        private String timezone;
        private boolean emailNotifications;

        public UserPreferences() {
            this.language = "en";
            this.timezone = "UTC";
            this.emailNotifications = true;
        }

        public String getLanguage() { return language; }
        public String getTimezone() { return timezone; }
        public boolean isEmailNotificationsEnabled() { return emailNotifications; }

        public void setLanguage(String language) { this.language = language; }
        public void setTimezone(String timezone) { this.timezone = timezone; }
        public void setEmailNotifications(boolean enabled) { this.emailNotifications = enabled; }
    }

    static class DatabaseConnectionPool {
        private int maxConnections;
        private int activeConnections;

        public DatabaseConnectionPool(int maxConnections) {
            this.maxConnections = maxConnections;
            this.activeConnections = 0;
        }

        public boolean hasAvailableConnection() {
            return activeConnections < maxConnections;
        }

        public void acquireConnection() {
            if (hasAvailableConnection()) {
                activeConnections++;
            }
        }

        public void releaseConnection() {
            if (activeConnections > 0) {
                activeConnections--;
            }
        }
    }

    static class Task {
        private String taskId;
        private String description;
        private TaskPriority priority;
        private Date createdAt;

        public Task(String taskId, String description, TaskPriority priority) {
            this.taskId = taskId;
            this.description = description;
            this.priority = priority;
            this.createdAt = new Date();
        }

        public String getTaskId() { return taskId; }
        public String getDescription() { return description; }
        public TaskPriority getPriority() { return priority; }
        public Date getCreatedAt() { return createdAt; }
    }

    static class DataRecord {
        private String recordId;
        private Map<String, Object> data;
        private boolean isValid;

        public DataRecord(String recordId) {
            this.recordId = recordId;
            this.data = new HashMap<>();
            this.isValid = true;
        }

        public String getRecordId() { return recordId; }
        public Map<String, Object> getData() { return new HashMap<>(data); }
        public boolean isValid() { return isValid; }

        public void addData(String key, Object value) {
            data.put(key, value);
        }

        public void markAsInvalid() {
            this.isValid = false;
        }
    }

    // Enums
    enum PaymentStatus { PENDING, COMPLETED, FAILED }
    enum ShippingOption { STANDARD, EXPRESS, OVERNIGHT }
    enum NotificationType { EMAIL, SMS, PUSH }
    enum TaskPriority { LOW, MEDIUM, HIGH, URGENT }

    // Notification interfaces and implementations
    interface Notification {
        void send();
        String getMessage();
    }

    static class EmailNotification implements Notification {
        private String message;

        public EmailNotification(String message) {
            this.message = message;
        }

        @Override
        public void send() {
            System.out.println("Sending email: " + message);
        }

        @Override
        public String getMessage() {
            return message;
        }
    }

    static class SmsNotification implements Notification {
        private String message;

        public SmsNotification(String message) {
            this.message = message;
        }

        @Override
        public void send() {
            System.out.println("Sending SMS: " + message);
        }

        @Override
        public String getMessage() {
            return message;
        }
    }

    static class PushNotification implements Notification {
        private String message;

        public PushNotification(String message) {
            this.message = message;
        }

        @Override
        public void send() {
            System.out.println("Sending push notification: " + message);
        }

        @Override
        public String getMessage() {
            return message;
        }
    }

    static class Order {
        private int quantity;
        private int price;
        private String customerId;
        private List<MeaningfulName.OrderItem> items;
        private ShippingAddress shippingAddress;


        public Order(String customerId, List<MeaningfulName.OrderItem> items, ShippingAddress shippingAddress) {
            this.customerId = customerId;
            this.items = items;
            this.shippingAddress = shippingAddress;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getCustomerId() {
            return customerId;
        }

        public void setCustomerId(String customerId) {
            this.customerId = customerId;
        }

        public List<OrderItem> getItems() {
            return items;
        }

        public void setItems(List<OrderItem> items) {
            this.items = items;
        }

        public ShippingAddress getShippingAddress() {
            return shippingAddress;
        }

        public void setShippingAddress(ShippingAddress shippingAddress) {
            this.shippingAddress = shippingAddress;
        }
    }
}
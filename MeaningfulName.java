import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

        public String getFullName() {
            return firstName + " " + lastName;
        }

        public boolean hasValidEmail() {
            return emailAddress != null && emailAddress.contains("@");
        }
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

            public Order(int price, boolean urgent) {
                this.price = price;
                this.urgent = urgent;
            }

            public int getPrice() {
                return price;
            }

            public boolean isUrgent() {
                return urgent;
            }
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
}
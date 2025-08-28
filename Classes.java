import java.util.*;
import java.util.stream.Collectors;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Clean Code - Classes (클래스) 챕터 예제들
 *
 * 주요 원칙:
 * 1. 클래스는 작아야 한다 (Small Classes)
 * 2. 단일 책임 원칙 (Single Responsibility Principle)
 * 3. 응집도를 높여라 (Cohesion)
 * 4. 결합도를 낮춰라 (Coupling)
 * 5. 변경하기 쉬운 클래스 (Classes Should Be Open for Extension)
 * 6. 변경으로부터 격리 (Isolating from Change)
 */
public class Classes {

    // ========== 1. 클래스는 작아야 한다 ==========

    // 나쁜 예: 너무 많은 책임을 가진 거대한 클래스
    static class EmployeeManagerBAD {
        private List<Employee> employees;
        private DatabaseConnection database;
        private EmailService emailService;
        private PayrollCalculator payrollCalculator;
        // ... 너무 많은 책임들

        // 직원 관리, 급여 계산, 이메일 발송, 리포트 생성, DB 작업, 감사 로깅 등
        // 모든 것을 하나의 클래스에서 처리하는 나쁜 예제
    }

    // 좋은 예: 작고 단일 책임을 가진 클래스들

    // ========== 2. 단일 책임 원칙 (SRP) ==========

    /**
     * 직원 정보만 관리하는 클래스
     */
    static class Employee {
        private final String id;
        private final String name;
        private final String email;
        private final String department;
        private BigDecimal baseSalary;
        private int performanceRating;
        private LocalDateTime hireDate;
        private EmployeeStatus status;

        public Employee(String id, String name, String email, String department, BigDecimal baseSalary) {
            validateConstructorParams(id, name, email, department, baseSalary);
            this.id = id;
            this.name = name;
            this.email = email;
            this.department = department;
            this.baseSalary = baseSalary;
            this.hireDate = LocalDateTime.now();
            this.status = EmployeeStatus.ACTIVE;
            this.performanceRating = 0;
        }

        private void validateConstructorParams(String id, String name, String email, String department, BigDecimal baseSalary) {
            if (id == null || id.trim().isEmpty()) {
                throw new IllegalArgumentException("Employee ID cannot be null or empty");
            }
            if (name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("Employee name cannot be null or empty");
            }
            if (email == null || !email.contains("@")) {
                throw new IllegalArgumentException("Valid email is required");
            }
            if (department == null || department.trim().isEmpty()) {
                throw new IllegalArgumentException("Department cannot be null or empty");
            }
            if (baseSalary == null || baseSalary.compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("Base salary must be non-negative");
            }
        }

        // 직원 정보 관련 메서드들만 포함
        public boolean isActive() {
            return status == EmployeeStatus.ACTIVE;
        }

        public boolean hasGoodPerformance() {
            return performanceRating >= 3;
        }

        public int getYearsOfService() {
            return LocalDateTime.now().getYear() - hireDate.getYear();
        }

        // Getters
        public String getId() { return id; }
        public String getName() { return name; }
        public String getEmail() { return email; }
        public String getDepartment() { return department; }
        public BigDecimal getBaseSalary() { return baseSalary; }
        public int getPerformanceRating() { return performanceRating; }
        public LocalDateTime getHireDate() { return hireDate; }
        public EmployeeStatus getStatus() { return status; }

        // Setters (필요한 것만)
        public void setBaseSalary(BigDecimal baseSalary) {
            if (baseSalary == null || baseSalary.compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("Base salary must be non-negative");
            }
            this.baseSalary = baseSalary;
        }

        public void setPerformanceRating(int rating) {
            if (rating < 0 || rating > 5) {
                throw new IllegalArgumentException("Performance rating must be between 0 and 5");
            }
            this.performanceRating = rating;
        }

        public void setStatus(EmployeeStatus status) {
            if (status == null) {
                throw new IllegalArgumentException("Status cannot be null");
            }
            this.status = status;
        }

        @Override
        public String toString() {
            return String.format("Employee{id='%s', name='%s', department='%s', status=%s}",
                    id, name, department, status);
        }
    }

    enum EmployeeStatus {
        ACTIVE, INACTIVE, TERMINATED, ON_LEAVE
    }

    /**
     * 직원 저장소 관리만 담당
     */
    static class EmployeeRepository {
        private final Map<String, Employee> employees;
        private final DatabaseConnection database;

        public EmployeeRepository(DatabaseConnection database) {
            this.database = database;
            this.employees = new HashMap<>();
        }

        public void save(Employee employee) {
            validateEmployee(employee);
            employees.put(employee.getId(), employee);
            database.execute("INSERT INTO employees ...", employee);
        }

        public Optional<Employee> findById(String id) {
            validateId(id);
            return Optional.ofNullable(employees.get(id));
        }

        public List<Employee> findByDepartment(String department) {
            validateDepartment(department);
            return employees.values().stream()
                    .filter(emp -> department.equals(emp.getDepartment()))
                    .collect(Collectors.toList());
        }

        public List<Employee> findAll() {
            return new ArrayList<>(employees.values());
        }

        public void delete(String id) {
            validateId(id);
            employees.remove(id);
            database.execute("DELETE FROM employees WHERE id = ?", id);
        }

        private void validateEmployee(Employee employee) {
            if (employee == null) {
                throw new IllegalArgumentException("Employee cannot be null");
            }
        }

        private void validateId(String id) {
            if (id == null || id.trim().isEmpty()) {
                throw new IllegalArgumentException("Employee ID cannot be null or empty");
            }
        }

        private void validateDepartment(String department) {
            if (department == null || department.trim().isEmpty()) {
                throw new IllegalArgumentException("Department cannot be null or empty");
            }
        }
    }

    /**
     * 급여 계산만 담당
     */
    static class PayrollCalculator {
        private static final BigDecimal TAX_RATE_LOW = new BigDecimal("0.10");
        private static final BigDecimal TAX_RATE_MEDIUM = new BigDecimal("0.20");
        private static final BigDecimal TAX_RATE_HIGH = new BigDecimal("0.30");
        private static final BigDecimal HIGH_INCOME_THRESHOLD = new BigDecimal("100000");
        private static final BigDecimal MEDIUM_INCOME_THRESHOLD = new BigDecimal("50000");

        public PayrollResult calculatePayroll(Employee employee) {
            validateEmployee(employee);

            BigDecimal grossSalary = calculateGrossSalary(employee);
            BigDecimal tax = calculateTax(grossSalary);
            BigDecimal netSalary = grossSalary.subtract(tax);

            return new PayrollResult(employee.getId(), grossSalary, tax, netSalary);
        }

        private BigDecimal calculateGrossSalary(Employee employee) {
            BigDecimal baseSalary = employee.getBaseSalary();
            BigDecimal bonus = calculateBonus(employee);
            return baseSalary.add(bonus);
        }

        private BigDecimal calculateBonus(Employee employee) {
            if (!employee.hasGoodPerformance()) {
                return BigDecimal.ZERO;
            }

            BigDecimal bonusRate = getBonusRate(employee.getPerformanceRating());
            return employee.getBaseSalary().multiply(bonusRate);
        }

        private BigDecimal getBonusRate(int performanceRating) {
            switch (performanceRating) {
                case 5: return new BigDecimal("0.15");
                case 4: return new BigDecimal("0.10");
                case 3: return new BigDecimal("0.05");
                default: return BigDecimal.ZERO;
            }
        }

        private BigDecimal calculateTax(BigDecimal grossSalary) {
            if (grossSalary.compareTo(HIGH_INCOME_THRESHOLD) > 0) {
                return grossSalary.multiply(TAX_RATE_HIGH);
            } else if (grossSalary.compareTo(MEDIUM_INCOME_THRESHOLD) > 0) {
                return grossSalary.multiply(TAX_RATE_MEDIUM);
            } else {
                return grossSalary.multiply(TAX_RATE_LOW);
            }
        }

        private void validateEmployee(Employee employee) {
            if (employee == null) {
                throw new IllegalArgumentException("Employee cannot be null");
            }
            if (!employee.isActive()) {
                throw new IllegalArgumentException("Cannot calculate payroll for inactive employee");
            }
        }
    }

    /**
     * 급여 계산 결과를 담는 불변 클래스
     */
    static class PayrollResult {
        private final String employeeId;
        private final BigDecimal grossSalary;
        private final BigDecimal tax;
        private final BigDecimal netSalary;
        private final LocalDateTime calculatedAt;

        public PayrollResult(String employeeId, BigDecimal grossSalary, BigDecimal tax, BigDecimal netSalary) {
            this.employeeId = employeeId;
            this.grossSalary = grossSalary;
            this.tax = tax;
            this.netSalary = netSalary;
            this.calculatedAt = LocalDateTime.now();
        }

        // Getters만 제공 (불변 객체)
        public String getEmployeeId() { return employeeId; }
        public BigDecimal getGrossSalary() { return grossSalary; }
        public BigDecimal getTax() { return tax; }
        public BigDecimal getNetSalary() { return netSalary; }
        public LocalDateTime getCalculatedAt() { return calculatedAt; }

        @Override
        public String toString() {
            return String.format("PayrollResult{employeeId='%s', grossSalary=%s, tax=%s, netSalary=%s}",
                    employeeId, grossSalary, tax, netSalary);
        }
    }

    // ========== 3. 응집도를 높여라 ==========

    /**
     * 높은 응집도를 가진 클래스 - 모든 메서드가 모든 인스턴스 변수를 사용
     */
    static class Stack<T> {
        private T[] elements;
        private int size;
        private int capacity;

        @SuppressWarnings("unchecked")
        public Stack(int capacity) {
            if (capacity <= 0) {
                throw new IllegalArgumentException("Capacity must be positive");
            }
            this.capacity = capacity;
            this.elements = (T[]) new Object[capacity];
            this.size = 0;
        }

        public void push(T element) {
            if (isFull()) {
                throw new IllegalStateException("Stack is full");
            }
            elements[size++] = element;
        }

        public T pop() {
            if (isEmpty()) {
                throw new IllegalStateException("Stack is empty");
            }
            T element = elements[--size];
            elements[size] = null; // 메모리 누수 방지
            return element;
        }

        public T peek() {
            if (isEmpty()) {
                throw new IllegalStateException("Stack is empty");
            }
            return elements[size - 1];
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public boolean isFull() {
            return size == capacity;
        }

        public int size() {
            return size;
        }

        public int capacity() {
            return capacity;
        }
    }

    // ========== 4. 결합도를 낮춰라 ==========

    /**
     * 이메일 발송 인터페이스 - 구현체와의 결합도를 낮춤
     */
    interface EmailService {
        void sendEmail(String to, String subject, String body);
    }

    /**
     * 실제 이메일 발송 구현체
     */
    static class SMTPEmailService implements EmailService {
        private final String smtpServer;
        private final int port;
        private final String username;
        private final String password;

        public SMTPEmailService(String smtpServer, int port, String username, String password) {
            this.smtpServer = smtpServer;
            this.port = port;
            this.username = username;
            this.password = password;
        }

        @Override
        public void sendEmail(String to, String subject, String body) {
            // SMTP 서버를 통한 실제 이메일 발송 로직
            System.out.printf("Sending email via SMTP (%s:%d) to %s: %s%n",
                    smtpServer, port, to, subject);
        }
    }

    /**
     * 테스트용 이메일 서비스
     */
    static class MockEmailService implements EmailService {
        private final List<EmailMessage> sentEmails = new ArrayList<>();

        @Override
        public void sendEmail(String to, String subject, String body) {
            sentEmails.add(new EmailMessage(to, subject, body));
            System.out.printf("Mock email sent to %s: %s%n", to, subject);
        }

        public List<EmailMessage> getSentEmails() {
            return new ArrayList<>(sentEmails);
        }

        public void clearSentEmails() {
            sentEmails.clear();
        }

        static class EmailMessage {
            private final String to;
            private final String subject;
            private final String body;
            private final LocalDateTime sentAt;

            public EmailMessage(String to, String subject, String body) {
                this.to = to;
                this.subject = subject;
                this.body = body;
                this.sentAt = LocalDateTime.now();
            }

            // Getters
            public String getTo() { return to; }
            public String getSubject() { return subject; }
            public String getBody() { return body; }
            public LocalDateTime getSentAt() { return sentAt; }
        }
    }

    /**
     * 이메일 서비스에 의존하지만 구체적인 구현체와는 결합되지 않음
     */
    static class EmployeeNotificationService {
        private final EmailService emailService;
        private final EmployeeRepository employeeRepository;

        public EmployeeNotificationService(EmailService emailService, EmployeeRepository employeeRepository) {
            this.emailService = emailService;
            this.employeeRepository = employeeRepository;
        }

        public void sendWelcomeEmail(String employeeId) {
            Employee employee = getEmployee(employeeId);

            String subject = "Welcome to the Company!";
            String body = buildWelcomeMessage(employee);

            emailService.sendEmail(employee.getEmail(), subject, body);
        }

        public void sendPayrollNotification(PayrollResult payrollResult) {
            Employee employee = getEmployee(payrollResult.getEmployeeId());

            String subject = "Your Payroll is Ready";
            String body = buildPayrollMessage(employee, payrollResult);

            emailService.sendEmail(employee.getEmail(), subject, body);
        }

        private Employee getEmployee(String employeeId) {
            return employeeRepository.findById(employeeId)
                    .orElseThrow(() -> new IllegalArgumentException("Employee not found: " + employeeId));
        }

        private String buildWelcomeMessage(Employee employee) {
            return String.format(
                    "Dear %s,\n\nWelcome to our company! We're excited to have you join the %s department.\n\nBest regards,\nHR Team",
                    employee.getName(), employee.getDepartment()
            );
        }

        private String buildPayrollMessage(Employee employee, PayrollResult payrollResult) {
            return String.format(
                    "Dear %s,\n\nYour payroll for this period:\nGross Salary: $%s\nTax: $%s\nNet Salary: $%s\n\nBest regards,\nPayroll Team",
                    employee.getName(),
                    payrollResult.getGrossSalary().setScale(2, RoundingMode.HALF_UP),
                    payrollResult.getTax().setScale(2, RoundingMode.HALF_UP),
                    payrollResult.getNetSalary().setScale(2, RoundingMode.HALF_UP)
            );
        }
    }

    // ========== 5. 변경하기 쉬운 클래스 ==========

    /**
     * 리포트 생성기 인터페이스 - 새로운 리포트 타입 추가 시 기존 코드 변경 없이 확장 가능
     */
    interface ReportGenerator {
        String generateReport(List<Employee> employees);
        String getReportType();
    }

    /**
     * CSV 형식 리포트 생성기
     */
    static class CSVReportGenerator implements ReportGenerator {
        @Override
        public String generateReport(List<Employee> employees) {
            StringBuilder report = new StringBuilder();
            report.append("ID,Name,Department,Salary,Status\n");

            for (Employee employee : employees) {
                report.append(String.format("%s,%s,%s,%s,%s\n",
                        employee.getId(),
                        employee.getName(),
                        employee.getDepartment(),
                        employee.getBaseSalary(),
                        employee.getStatus()
                ));
            }

            return report.toString();
        }

        @Override
        public String getReportType() {
            return "CSV";
        }
    }

    /**
     * HTML 형식 리포트 생성기
     */
    static class HTMLReportGenerator implements ReportGenerator {
        @Override
        public String generateReport(List<Employee> employees) {
            StringBuilder report = new StringBuilder();
            report.append("<html><body>\n");
            report.append("<h1>Employee Report</h1>\n");
            report.append("<table border='1'>\n");
            report.append("<tr><th>ID</th><th>Name</th><th>Department</th><th>Salary</th><th>Status</th></tr>\n");

            for (Employee employee : employees) {
                report.append(String.format("<tr><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td></tr>\n",
                        employee.getId(),
                        employee.getName(),
                        employee.getDepartment(),
                        employee.getBaseSalary(),
                        employee.getStatus()
                ));
            }

            report.append("</table>\n");
            report.append("</body></html>");

            return report.toString();
        }

        @Override
        public String getReportType() {
            return "HTML";
        }
    }

    /**
     * JSON 형식 리포트 생성기 - 새로 추가되어도 기존 코드에 영향 없음
     */
    static class JSONReportGenerator implements ReportGenerator {
        @Override
        public String generateReport(List<Employee> employees) {
            StringBuilder report = new StringBuilder();
            report.append("{\n  \"employees\": [\n");

            for (int i = 0; i < employees.size(); i++) {
                Employee employee = employees.get(i);
                report.append(String.format(
                        "    {\n      \"id\": \"%s\",\n      \"name\": \"%s\",\n      \"department\": \"%s\",\n      \"salary\": %s,\n      \"status\": \"%s\"\n    }",
                        employee.getId(),
                        employee.getName(),
                        employee.getDepartment(),
                        employee.getBaseSalary(),
                        employee.getStatus()
                ));

                if (i < employees.size() - 1) {
                    report.append(",");
                }
                report.append("\n");
            }

            report.append("  ]\n}");
            return report.toString();
        }

        @Override
        public String getReportType() {
            return "JSON";
        }
    }

    /**
     * 리포트 서비스 - 팩토리 패턴으로 확장성 확보
     */
    static class ReportService {
        private final Map<String, ReportGenerator> generators;
        private final EmployeeRepository employeeRepository;

        public ReportService(EmployeeRepository employeeRepository) {
            this.employeeRepository = employeeRepository;
            this.generators = new HashMap<>();

            // 기본 리포트 생성기들 등록
            registerGenerator(new CSVReportGenerator());
            registerGenerator(new HTMLReportGenerator());
            registerGenerator(new JSONReportGenerator());
        }

        public void registerGenerator(ReportGenerator generator) {
            generators.put(generator.getReportType().toUpperCase(), generator);
        }

        public String generateReport(String reportType) {
            return generateReport(reportType, null);
        }

        public String generateReport(String reportType, String department) {
            ReportGenerator generator = getGenerator(reportType);
            List<Employee> employees = getEmployees(department);

            return generator.generateReport(employees);
        }

        public Set<String> getSupportedReportTypes() {
            return new HashSet<>(generators.keySet());
        }

        private ReportGenerator getGenerator(String reportType) {
            ReportGenerator generator = generators.get(reportType.toUpperCase());
            if (generator == null) {
                throw new IllegalArgumentException("Unsupported report type: " + reportType);
            }
            return generator;
        }

        private List<Employee> getEmployees(String department) {
            if (department == null || department.trim().isEmpty()) {
                return employeeRepository.findAll();
            } else {
                return employeeRepository.findByDepartment(department);
            }
        }
    }

    // ========== 6. 변경으로부터 격리 ==========

    /**
     * 데이터베이스 연결 인터페이스 - 구체적인 DB 구현체로부터 격리
     */
    interface DatabaseConnection {
        void execute(String sql, Object... parameters);
        <T> List<T> query(String sql, Class<T> resultType, Object... parameters);
    }

    /**
     * MySQL 구현체
     */
    static class MySQLConnection implements DatabaseConnection {
        private final String connectionString;

        public MySQLConnection(String connectionString) {
            this.connectionString = connectionString;
        }

        @Override
        public void execute(String sql, Object... parameters) {
            System.out.printf("Executing MySQL query: %s with params: %s%n", sql, Arrays.toString(parameters));
        }

        @Override
        public <T> List<T> query(String sql, Class<T> resultType, Object... parameters) {
            System.out.printf("Querying MySQL: %s with params: %s%n", sql, Arrays.toString(parameters));
            return new ArrayList<>();
        }
    }

    /**
     * 테스트용 인메모리 DB 구현체
     */
    static class InMemoryDatabaseConnection implements DatabaseConnection {
        private final List<String> executedQueries = new ArrayList<>();

        @Override
        public void execute(String sql, Object... parameters) {
            String query = String.format("SQL: %s, Params: %s", sql, Arrays.toString(parameters));
            executedQueries.add(query);
            System.out.println("InMemory DB: " + query);
        }

        @Override
        public <T> List<T> query(String sql, Class<T> resultType, Object... parameters) {
            String query = String.format("QUERY: %s, Params: %s", sql, Arrays.toString(parameters));
            executedQueries.add(query);
            System.out.println("InMemory DB Query: " + query);
            return new ArrayList<>();
        }

        public List<String> getExecutedQueries() {
            return new ArrayList<>(executedQueries);
        }

        public void clearQueries() {
            executedQueries.clear();
        }
    }

    /**
     * 통합된 직원 관리 서비스 - 모든 클래스들을 조합하여 사용
     */
    static class EmployeeManagementService {
        private final EmployeeRepository employeeRepository;
        private final PayrollCalculator payrollCalculator;
        private final EmployeeNotificationService notificationService;
        private final ReportService reportService;

        public EmployeeManagementService(
                EmployeeRepository employeeRepository,
                PayrollCalculator payrollCalculator,
                EmployeeNotificationService notificationService,
                ReportService reportService) {
            this.employeeRepository = employeeRepository;
            this.payrollCalculator = payrollCalculator;
            this.notificationService = notificationService;
            this.reportService = reportService;
        }

        public void hireEmployee(Employee employee) {
            employeeRepository.save(employee);
            notificationService.sendWelcomeEmail(employee.getId());
        }

        public void processPayroll(String employeeId) {
            Employee employee = employeeRepository.findById(employeeId)
                    .orElseThrow(() -> new IllegalArgumentException("Employee not found: " + employeeId));

            PayrollResult payrollResult = payrollCalculator.calculatePayroll(employee);
            notificationService.sendPayrollNotification(payrollResult);
        }

        public String generateEmployeeReport(String reportType, String department) {
            return reportService.generateReport(reportType, department);
        }

        public void terminateEmployee(String employeeId) {
            Employee employee = employeeRepository.findById(employeeId)
                    .orElseThrow(() -> new IllegalArgumentException("Employee not found: " + employeeId));

            employee.setStatus(EmployeeStatus.TERMINATED);
            employeeRepository.save(employee);
        }

        public Optional<Employee> findEmployee(String employeeId) {
            return employeeRepository.findById(employeeId);
        }

        public List<Employee> findEmployeesByDepartment(String department) {
            return employeeRepository.findByDepartment(department);
        }
    }

    // ========== 데모용 Stack 테스트 ==========

    public static void demonstrateStack() {
        System.out.println("=== Stack Demonstration (High Cohesion Example) ===");
        Stack<String> stack = new Stack<>(3);

        System.out.println("Initial state - Empty: " + stack.isEmpty() + ", Size: " + stack.size());

        stack.push("First");
        stack.push("Second");
        stack.push("Third");

        System.out.println("After pushing 3 items - Full: " + stack.isFull() + ", Size: " + stack.size());
        System.out.println("Peek: " + stack.peek());
        System.out.println("Pop: " + stack.pop());
        System.out.println("Pop: " + stack.pop());
        System.out.println("After popping 2 items - Empty: " + stack.isEmpty() + ", Size: " + stack.size());
        System.out.println();
    }

    // ========== 메인 데모 메서드 ==========

    public static void main(String[] args) {
        System.out.println("=== Clean Code Classes Demo ===\n");

        // Stack 데모
        demonstrateStack();

        // 의존성 설정 - 인터페이스를 통한 낮은 결합도 구현
        DatabaseConnection database = new InMemoryDatabaseConnection();
        EmployeeRepository employeeRepository = new EmployeeRepository(database);
        PayrollCalculator payrollCalculator = new PayrollCalculator();
        EmailService emailService = new MockEmailService();
        EmployeeNotificationService notificationService =
                new EmployeeNotificationService(emailService, employeeRepository);
        ReportService reportService = new ReportService(employeeRepository);

        EmployeeManagementService managementService = new EmployeeManagementService(
                employeeRepository, payrollCalculator, notificationService, reportService);

        // 직원 생성 및 고용
        Employee john = new Employee("EMP001", "John Doe", "john.doe@company.com",
                "Engineering", new BigDecimal("75000"));
        john.setPerformanceRating(4);

        Employee jane = new Employee("EMP002", "Jane Smith", "jane.smith@company.com",
                "Marketing", new BigDecimal("65000"));
        jane.setPerformanceRating(5);

        Employee bob = new Employee("EMP003", "Bob Wilson", "bob.wilson@company.com",
                "Engineering", new BigDecimal("80000"));
        bob.setPerformanceRating(3);

        System.out.println("=== Hiring Employees ===");
        managementService.hireEmployee(john);
        managementService.hireEmployee(jane);
        managementService.hireEmployee(bob);
        System.out.println();

        // 급여 처리
        System.out.println("=== Processing Payroll ===");
        managementService.processPayroll("EMP001");
        managementService.processPayroll("EMP002");
        managementService.processPayroll("EMP003");
        System.out.println();

        // 리포트 생성 - 확장성 있는 설계로 새로운 형식 추가 가능
        System.out.println("=== Generating Reports (Extensible Design) ===");

        System.out.println("Supported report types: " + reportService.getSupportedReportTypes());
        System.out.println();

        System.out.println("--- CSV Report (All Employees) ---");
        System.out.println(managementService.generateEmployeeReport("CSV", null));
        System.out.println();

        System.out.println("--- HTML Report (Engineering Department Only) ---");
        System.out.println(managementService.generateEmployeeReport("HTML", "Engineering"));
        System.out.println();

        System.out.println("--- JSON Report (All Employees) ---");
        System.out.println(managementService.generateEmployeeReport("JSON", null));
        System.out.println();

        // 부서별 직원 조회
        System.out.println("=== Department-based Employee Search ===");
        List<Employee> engineeringEmployees = managementService.findEmployeesByDepartment("Engineering");
        System.out.println("Engineering Department Employees:");
        engineeringEmployees.forEach(emp -> System.out.println("  " + emp));
        System.out.println();

        // 직원 상태 변경
        System.out.println("=== Employee Status Management ===");
        System.out.println("Terminating employee EMP003...");
        managementService.terminateEmployee("EMP003");

        Optional<Employee> terminatedEmployee = managementService.findEmployee("EMP003");
        if (terminatedEmployee.isPresent()) {
            System.out.println("Employee status after termination: " + terminatedEmployee.get().getStatus());
        }
        System.out.println();

        // Clean Code 원칙들 설명
        System.out.println("=== Clean Code Classes Principles Demonstrated ===");
        System.out.println("1. Small Classes: Each class has a single, focused responsibility");
        System.out.println("2. Single Responsibility: Employee, PayrollCalculator, ReportService each do one thing");
        System.out.println("3. High Cohesion: Stack class - all methods use all instance variables");
        System.out.println("4. Low Coupling: Services depend on interfaces, not concrete implementations");
        System.out.println("5. Open for Extension: New report types can be added without changing existing code");
        System.out.println("6. Isolated from Change: Database and email implementations can be swapped easily");
        System.out.println();

        // 런타임에 새로운 리포트 타입 추가 데모
        System.out.println("=== Runtime Extension Demo ===");
        System.out.println("Adding new XML report generator at runtime...");
        reportService.registerGenerator(new XMLReportGenerator());
        System.out.println("Updated supported report types: " + reportService.getSupportedReportTypes());
        System.out.println();

        System.out.println("--- XML Report (All Employees) ---");
        System.out.println(managementService.generateEmployeeReport("XML", null));
    }

    /**
     * 런타임에 추가할 수 있는 XML 리포트 생성기 - 확장성 데모
     */
    static class XMLReportGenerator implements ReportGenerator {
        @Override
        public String generateReport(List<Employee> employees) {
            StringBuilder report = new StringBuilder();
            report.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            report.append("<employees>\n");

            for (Employee employee : employees) {
                report.append("  <employee>\n");
                report.append(String.format("    <id>%s</id>\n", employee.getId()));
                report.append(String.format("    <name>%s</name>\n", employee.getName()));
                report.append(String.format("    <department>%s</department>\n", employee.getDepartment()));
                report.append(String.format("    <salary>%s</salary>\n", employee.getBaseSalary()));
                report.append(String.format("    <status>%s</status>\n", employee.getStatus()));
                report.append("  </employee>\n");
            }

            report.append("</employees>");
            return report.toString();
        }

        @Override
        public String getReportType() {
            return "XML";
        }
    }
}
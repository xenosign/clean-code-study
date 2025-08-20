import java.util.*;
import java.util.stream.Collectors;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Clean Code - Functions (함수) 챕터 예제들
 *
 * 주요 원칙:
 * 1. 작게 만들어라 (Small)
 * 2. 한 가지만 해라 (Do One Thing)
 * 3. 함수당 추상화 수준은 하나로 (One Level of Abstraction)
 * 4. Switch 문은 피해라
 * 5. 서술적인 이름을 사용해라
 * 6. 함수 인수는 적게 (최대 3개)
 * 7. 부수 효과를 일으키지 마라
 * 8. 명령과 조회를 분리해라
 * 9. 오류 코드보다 예외를 사용해라
 * 10. 반복하지 마라 (DRY)
 */

public class Functions {
    private static final Logger logger = Logger.getLogger(Functions.class.getName());
    private static final double HIGH_SALARY_THRESHOLD = 50000.0;
    private static final int MINIMUM_EXPERIENCE_YEARS = 2;
    private static final int GOOD_PERFORMANCE_RATING = 3;
    private static final int EXCELLENT_PERFORMANCE_RATING = 4;

    // 의존성 주입을 위한 필드들
    private final EmployeeRepository employeeRepository;
    private final EmailService emailService;
    private final AuditService auditService;
    private final SessionManager sessionManager;

    public Functions() {
        this.employeeRepository = new InMemoryEmployeeRepository();
        this.emailService = new SimpleEmailService();
        this.auditService = new SimpleAuditService();
        this.sessionManager = new SimpleSessionManager();
    }

    // 의존성 주입을 위한 생성자
    public Functions(EmployeeRepository employeeRepository,
                     EmailService emailService,
                     AuditService auditService,
                     SessionManager sessionManager) {
        this.employeeRepository = employeeRepository;
        this.emailService = emailService;
        this.auditService = auditService;
        this.sessionManager = sessionManager;
    }

    // ========== 1. 작게 만들어라 (Small Functions) ==========

    // 나쁜 예: 너무 긴 함수
    public void processEmployeesBAD(List<Employee> employees) {
        for (Employee employee : employees) {
            // 직원 정보 검증
            if (employee.getName() == null || employee.getName().trim().isEmpty()) {
                System.out.println("Invalid employee name");
                continue;
            }
            if (employee.getAge() < 18 || employee.getAge() > 65) {
                System.out.println("Invalid employee age");
                continue;
            }
            if (employee.getSalary() < 0) {
                System.out.println("Invalid salary");
                continue;
            }

            // 급여 계산
            double baseSalary = employee.getSalary();
            double bonus = 0;
            if (employee.getPerformanceRating() >= 4) {
                bonus = baseSalary * 0.1;
            } else if (employee.getPerformanceRating() >= 3) {
                bonus = baseSalary * 0.05;
            }

            double totalSalary = baseSalary + bonus;

            // 세금 계산
            double tax = 0;
            if (totalSalary > 100000) {
                tax = totalSalary * 0.3;
            } else if (totalSalary > 50000) {
                tax = totalSalary * 0.2;
            } else {
                tax = totalSalary * 0.1;
            }

            double netSalary = totalSalary - tax;

            // 결과 출력
            System.out.println("Employee: " + employee.getName());
            System.out.println("Net Salary: " + netSalary);
        }
    }

    // 좋은 예: 작은 함수들로 분해
    public void processEmployees(List<Employee> employees) {
        employees.stream()
                .filter(this::isValidEmployee)
                .forEach(this::processEmployee);
    }

    private boolean isValidEmployee(Employee employee) {
        return isValidName(employee.getName()) &&
                isValidAge(employee.getAge()) &&
                isValidSalary(employee.getSalary());
    }

    private void processEmployee(Employee employee) {
        try {
            double totalSalary = calculateTotalSalary(employee);
            double netSalary = calculateNetSalary(totalSalary);
            printEmployeePayroll(employee, netSalary);
        } catch (Exception e) {
            logger.log(Level.WARNING, "Error processing employee: " + employee.getName(), e);
        }
    }

    // ========== 2. 한 가지만 해라 (Do One Thing) ==========

    private boolean isValidName(String name) {
        return name != null && !name.trim().isEmpty();
    }

    private boolean isValidAge(int age) {
        return age >= 18 && age <= 65;
    }

    private boolean isValidSalary(double salary) {
        return salary >= 0;
    }

    private double calculateTotalSalary(Employee employee) {
        double baseSalary = employee.getSalary();
        double bonus = calculateBonus(employee);
        return baseSalary + bonus;
    }

    private double calculateBonus(Employee employee) {
        double baseSalary = employee.getSalary();
        int rating = employee.getPerformanceRating();

        if (rating >= EXCELLENT_PERFORMANCE_RATING) return baseSalary * 0.1;
        if (rating >= GOOD_PERFORMANCE_RATING) return baseSalary * 0.05;
        return 0;
    }

    private double calculateNetSalary(double totalSalary) {
        double tax = calculateTax(totalSalary);
        return totalSalary - tax;
    }

    private double calculateTax(double totalSalary) {
        if (totalSalary > 100000) return totalSalary * 0.3;
        if (totalSalary > 50000) return totalSalary * 0.2;
        return totalSalary * 0.1;
    }

    private void printEmployeePayroll(Employee employee, double netSalary) {
        System.out.println("Employee: " + employee.getName());
        System.out.println("Net Salary: " + String.format("%.2f", netSalary));
    }

    // ========== 3. 함수당 추상화 수준은 하나로 ==========

    // 나쁜 예: 여러 추상화 수준이 섞임
    public void generateReportBAD(List<Employee> employees) {
        // 높은 추상화 수준
        System.out.println("=== Employee Report ===");

        // 중간 추상화 수준
        for (Employee emp : employees) {
            // 낮은 추상화 수준
            if (emp != null && emp.getName() != null && !emp.getName().trim().isEmpty()) {
                String formatted = emp.getName().substring(0, 1).toUpperCase() +
                        emp.getName().substring(1).toLowerCase();
                System.out.println("Name: " + formatted);
            }
        }
    }

    // 좋은 예: 각 함수가 하나의 추상화 수준만 가짐
    public void generateReport(List<Employee> employees) {
        printReportHeader();
        printEmployeeList(employees);
        printReportFooter();
    }

    private void printReportHeader() {
        System.out.println("=== Employee Report ===");
        System.out.println("Generated: " + getCurrentDateTime());
    }

    private void printEmployeeList(List<Employee> employees) {
        employees.stream()
                .filter(Objects::nonNull)
                .forEach(this::printEmployeeInfo);
    }

    private void printEmployeeInfo(Employee employee) {
        String formattedName = formatEmployeeName(employee.getName());
        System.out.println("Name: " + formattedName);
        System.out.println("Department: " + employee.getDepartment());
    }

    private String formatEmployeeName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return "Unknown";
        }
        String trimmedName = name.trim();
        return trimmedName.substring(0, 1).toUpperCase() +
                trimmedName.substring(1).toLowerCase();
    }

    private void printReportFooter() {
        System.out.println("=== End of Report ===");
    }

    private String getCurrentDateTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    // ========== 4. Switch 문은 피해라 ==========

    // 나쁜 예: Switch 문 사용
    public double calculateEmployeeBenefitsBAD(Employee employee) {
        double benefits = 0;
        switch (employee.getEmployeeType()) {
            case "FULL_TIME":
                benefits = employee.getSalary() * 0.2;
                break;
            case "PART_TIME":
                benefits = employee.getSalary() * 0.1;
                break;
            case "CONTRACT":
                benefits = 0;
                break;
            case "INTERN":
                benefits = employee.getSalary() * 0.05;
                break;
            default:
                benefits = 0;
        }
        return benefits;
    }

    // 좋은 예: 다형성 활용
    public double calculateEmployeeBenefits(Employee employee) {
        return employee.getBenefitsCalculator().calculate(employee.getSalary());
    }

    // ========== 5. 서술적인 이름을 사용해라 ==========

    // 나쁜 예: 의미 없는 함수명
    public List<Employee> get(List<Employee> list) {
        return list.stream()
                .filter(e -> e.getSalary() > HIGH_SALARY_THRESHOLD)
                .collect(Collectors.toList());
    }

    // 좋은 예: 서술적인 함수명
    public List<Employee> getHighSalaryEmployees(List<Employee> employees) {
        return employees.stream()
                .filter(this::isHighSalaryEmployee)
                .collect(Collectors.toList());
    }

    private boolean isHighSalaryEmployee(Employee employee) {
        return employee.getSalary() > HIGH_SALARY_THRESHOLD;
    }

    // 더 구체적인 서술적 이름들
    public List<Employee> getEmployeesEligibleForPromotion(List<Employee> employees) {
        return employees.stream()
                .filter(this::hasMinimumExperience)
                .filter(this::hasGoodPerformanceRating)
                .collect(Collectors.toList());
    }

    private boolean hasMinimumExperience(Employee employee) {
        return employee.getYearsOfExperience() >= MINIMUM_EXPERIENCE_YEARS;
    }

    private boolean hasGoodPerformanceRating(Employee employee) {
        return employee.getPerformanceRating() >= GOOD_PERFORMANCE_RATING;
    }

    // ========== 6. 함수 인수는 적게 (최대 3개) ==========

    // 나쁜 예: 인수가 너무 많음
    public void createEmployeeReportBAD(String name, int age, String department,
                                        double salary, String email, String phone,
                                        String address, LocalDateTime hireDate,
                                        int performanceRating) {
        // 구현 생략
    }

    // 좋은 예: 객체로 묶어서 전달
    public void createEmployeeReport(Employee employee) {
        generateDetailedReport(employee);
    }

    // 좋은 예: 필요한 최소한의 인수만 전달
    public double calculateMonthlyPayment(double principal, double rate, int months) {
        validateLoanParameters(principal, rate, months);
        return principal * (rate * Math.pow(1 + rate, months)) / (Math.pow(1 + rate, months) - 1);
    }

    private void validateLoanParameters(double principal, double rate, int months) {
        if (principal <= 0) throw new IllegalArgumentException("Principal must be positive");
        if (rate < 0) throw new IllegalArgumentException("Rate cannot be negative");
        if (months <= 0) throw new IllegalArgumentException("Months must be positive");
    }

    // Flag 인수는 피하고 메서드를 분리
    // 나쁜 예
    public void printEmployeeInfoBAD(Employee employee, boolean detailed) {
        if (detailed) {
            printEmployeeDetailedInfo(employee);
        } else {
            printEmployeeSummary(employee);
        }
    }

    // 좋은 예: 메서드 분리
    public void printEmployeeSummary(Employee employee) {
        System.out.println(employee.getName() + " - " + employee.getDepartment());
    }

    public void printEmployeeDetailedInfo(Employee employee) {
        System.out.println("Name: " + employee.getName());
        System.out.println("Department: " + employee.getDepartment());
        System.out.println("Salary: " + String.format("%.2f", employee.getSalary()));
        System.out.println("Email: " + employee.getEmail());
        System.out.println("Experience: " + employee.getYearsOfExperience() + " years");
    }

    // ========== 7. 부수 효과를 일으키지 마라 ==========

    // 나쁜 예: 함수명과 다른 부수 효과 발생
    public boolean checkPasswordBAD(String userName, String password) {
        boolean isValid = authenticateUser(userName, password);
        if (isValid) {
            sessionManager.createSession(userName); // 부수 효과!
            auditService.logActivity(userName, "LOGIN"); // 부수 효과!
        }
        return isValid;
    }

    // 좋은 예: 부수 효과 없이 순수 함수로 만들기
    public boolean isValidPassword(String userName, String password) {
        return authenticateUser(userName, password);
    }

    // 부수 효과가 필요하다면 명시적으로 분리
    public void loginUser(String userName, String password) throws AuthenticationException {
        if (isValidPassword(userName, password)) {
            sessionManager.createSession(userName);
            auditService.logActivity(userName, "LOGIN");
        } else {
            throw new AuthenticationException("Invalid credentials for user: " + userName);
        }
    }

    // ========== 8. 명령과 조회를 분리해라 ==========

    // 나쁜 예: 명령과 조회가 섞임
    public boolean setAndCheckEmployeeStatusBAD(Employee employee, String status) {
        employee.setStatus(status); // 명령 (상태 변경)
        return "ACTIVE".equals(status); // 조회 (상태 확인)
    }

    // 좋은 예: 명령과 조회 분리
    public void setEmployeeStatus(Employee employee, String status) {
        validateStatus(status);
        employee.setStatus(status);
        auditService.logActivity(employee.getName(), "STATUS_CHANGED_TO_" + status);
    }

    public boolean isEmployeeActive(Employee employee) {
        return EmployeeStatus.ACTIVE.name().equals(employee.getStatus());
    }

    private void validateStatus(String status) {
        if (!isValidStatus(status)) {
            throw new IllegalArgumentException("Invalid status: " + status);
        }
    }

    private boolean isValidStatus(String status) {
        return Arrays.stream(EmployeeStatus.values())
                .anyMatch(validStatus -> validStatus.name().equals(status));
    }

    // 사용 예시
    public void activateEmployee(Employee employee) {
        setEmployeeStatus(employee, EmployeeStatus.ACTIVE.name());
        if (isEmployeeActive(employee)) {
            System.out.println("Employee " + employee.getName() + " is now active");
        }
    }

    // ========== 9. 오류 코드보다 예외를 사용해라 ==========

    // 나쁜 예: 오류 코드 반환
    public int deleteEmployeeBAD(String employeeId) {
        if (employeeId == null || employeeId.trim().isEmpty()) {
            return -1; // 잘못된 ID
        }

        Employee employee = employeeRepository.findById(employeeId);
        if (employee == null) {
            return -2; // 직원을 찾을 수 없음
        }

        if (EmployeeStatus.ACTIVE.name().equals(employee.getStatus())) {
            return -3; // 활성 직원은 삭제 불가
        }

        employeeRepository.delete(employee);
        return 0; // 성공
    }

    // 좋은 예: 예외 사용
    public void deleteEmployee(String employeeId) throws EmployeeException {
        validateEmployeeId(employeeId);

        Employee employee = getEmployeeById(employeeId);
        validateEmployeeCanBeDeleted(employee);

        employeeRepository.delete(employee);
        auditService.logActivity(employee.getName(), "EMPLOYEE_DELETED");
    }

    private void validateEmployeeId(String employeeId) throws EmployeeException {
        if (employeeId == null || employeeId.trim().isEmpty()) {
            throw new EmployeeException("Employee ID cannot be null or empty");
        }
    }

    private Employee getEmployeeById(String employeeId) throws EmployeeException {
        Employee employee = employeeRepository.findById(employeeId);
        if (employee == null) {
            throw new EmployeeException("Employee not found with ID: " + employeeId);
        }
        return employee;
    }

    private void validateEmployeeCanBeDeleted(Employee employee) throws EmployeeException {
        if (EmployeeStatus.ACTIVE.name().equals(employee.getStatus())) {
            throw new EmployeeException("Cannot delete active employee: " + employee.getName());
        }
    }

    // ========== 10. 반복하지 마라 (DRY) ==========

    // 나쁜 예: 중복 코드
    public void sendWelcomeEmailBAD(Employee employee) {
        String subject = "Welcome to Company";
        String body = "Dear " + employee.getName() + ",\n" +
                "Welcome to our company!\n" +
                "Best regards,\n" +
                "HR Team";
        emailService.send(employee.getEmail(), subject, body);
        auditService.logActivity(employee.getEmail(), "EMAIL_SENT: " + subject);
    }

    public void sendGoodbyeEmailBAD(Employee employee) {
        String subject = "Thank you for your service";
        String body = "Dear " + employee.getName() + ",\n" +
                "Thank you for your service at our company!\n" +
                "Best regards,\n" +
                "HR Team";
        emailService.send(employee.getEmail(), subject, body);
        auditService.logActivity(employee.getEmail(), "EMAIL_SENT: " + subject);
    }

    // 좋은 예: 중복 제거
    public void sendWelcomeEmail(Employee employee) {
        EmailTemplate template = EmailTemplate.WELCOME;
        sendEmployeeEmail(employee, template);
    }

    public void sendGoodbyeEmail(Employee employee) {
        EmailTemplate template = EmailTemplate.GOODBYE;
        sendEmployeeEmail(employee, template);
    }

    private void sendEmployeeEmail(Employee employee, EmailTemplate template) {
        validateEmail(employee.getEmail());

        String subject = template.getSubject();
        String body = buildEmailBody(employee, template);

        emailService.send(employee.getEmail(), subject, body);
        auditService.logActivity(employee.getEmail(), "EMAIL_SENT: " + subject);
    }

    private void validateEmail(String email) {
        if (email == null || email.trim().isEmpty() || !email.contains("@")) {
            throw new IllegalArgumentException("Invalid email address: " + email);
        }
    }

    private String buildEmailBody(Employee employee, EmailTemplate template) {
        return String.format("Dear %s,\n%s\nBest regards,\nHR Team",
                employee.getName(), template.getContent());
    }

    // Helper 메서드들
    private boolean authenticateUser(String userName, String password) {
        // 실제 구현에서는 보안 라이브러리 사용
        return userName != null && password != null && password.length() >= 8;
    }

    private void generateDetailedReport(Employee employee) {
        System.out.println("=== Detailed Employee Report ===");
        printEmployeeDetailedInfo(employee);
        System.out.println("Benefits: " + String.format("%.2f", calculateEmployeeBenefits(employee)));
        System.out.println("================================");
    }

    // ========== 열거형들 ==========

    enum EmailTemplate {
        WELCOME("Welcome to Company", "Welcome to our company!"),
        GOODBYE("Thank you for your service", "Thank you for your service at our company!");

        private final String subject;
        private final String content;

        EmailTemplate(String subject, String content) {
            this.subject = subject;
            this.content = content;
        }

        public String getSubject() { return subject; }
        public String getContent() { return content; }
    }

    enum EmployeeStatus {
        ACTIVE, INACTIVE, TERMINATED, ON_LEAVE
    }

    // ========== 예외 클래스들 ==========

    static class EmployeeException extends Exception {
        public EmployeeException(String message) {
            super(message);
        }
    }

    static class AuthenticationException extends Exception {
        public AuthenticationException(String message) {
            super(message);
        }
    }

    // ========== 인터페이스들 ==========

    interface BenefitsCalculator {
        double calculate(double salary);
    }

    interface EmployeeRepository {
        Employee findById(String id);
        void save(Employee employee);
        void delete(Employee employee);
        List<Employee> findAll();
    }

    interface EmailService {
        void send(String to, String subject, String body);
    }

    interface AuditService {
        void logActivity(String user, String activity);
    }

    interface SessionManager {
        void createSession(String userName);
        void invalidateSession(String userName);
        boolean isSessionValid(String userName);
    }

    // ========== 구현 클래스들 ==========

    static class FullTimeBenefitsCalculator implements BenefitsCalculator {
        @Override
        public double calculate(double salary) {
            return salary * 0.2;
        }
    }

    static class PartTimeBenefitsCalculator implements BenefitsCalculator {
        @Override
        public double calculate(double salary) {
            return salary * 0.1;
        }
    }

    static class ContractBenefitsCalculator implements BenefitsCalculator {
        @Override
        public double calculate(double salary) {
            return 0;
        }
    }

    static class InternBenefitsCalculator implements BenefitsCalculator {
        @Override
        public double calculate(double salary) {
            return salary * 0.05;
        }
    }

    // 간단한 구현체들 (실제 프로젝트에서는 더 복잡한 구현 필요)
    static class InMemoryEmployeeRepository implements EmployeeRepository {
        private final Map<String, Employee> employees = new HashMap<>();

        @Override
        public Employee findById(String id) {
            return employees.get(id);
        }

        @Override
        public void save(Employee employee) {
            employees.put(employee.getId(), employee);
        }

        @Override
        public void delete(Employee employee) {
            employees.remove(employee.getId());
        }

        @Override
        public List<Employee> findAll() {
            return new ArrayList<>(employees.values());
        }
    }

    static class SimpleEmailService implements EmailService {
        @Override
        public void send(String to, String subject, String body) {
            System.out.println("Email sent to: " + to + " | Subject: " + subject);
        }
    }

    static class SimpleAuditService implements AuditService {
        private final List<String> auditLog = new ArrayList<>();

        @Override
        public void logActivity(String user, String activity) {
            String logEntry = String.format("[%s] %s: %s",
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                    user, activity);
            auditLog.add(logEntry);
            System.out.println("AUDIT: " + logEntry);
        }
    }

    static class SimpleSessionManager implements SessionManager {
        private final Set<String> activeSessions = new HashSet<>();

        @Override
        public void createSession(String userName) {
            activeSessions.add(userName);
        }

        @Override
        public void invalidateSession(String userName) {
            activeSessions.remove(userName);
        }

        @Override
        public boolean isSessionValid(String userName) {
            return activeSessions.contains(userName);
        }
    }

    // ========== Employee 클래스 ==========

    static class Employee {
        private String id;
        private String name;
        private int age;
        private String department;
        private double salary;
        private String email;
        private String phone;
        private String address;
        private LocalDateTime hireDate;
        private int performanceRating;
        private String employeeType;
        private String status;
        private int yearsOfExperience;
        private BenefitsCalculator benefitsCalculator;

        // 생성자
        public Employee(String id, String name, int age, String department, double salary,
                        String employeeType, BenefitsCalculator benefitsCalculator) {
            this.id = id;
            this.name = name;
            this.age = age;
            this.department = department;
            this.salary = salary;
            this.employeeType = employeeType;
            this.benefitsCalculator = benefitsCalculator;
            this.status = EmployeeStatus.INACTIVE.name();
            this.hireDate = LocalDateTime.now();
        }

        // Getters and Setters
        public String getId() { return id; }
        public void setId(String id) { this.id = id; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public int getAge() { return age; }
        public void setAge(int age) { this.age = age; }

        public String getDepartment() { return department; }
        public void setDepartment(String department) { this.department = department; }

        public double getSalary() { return salary; }
        public void setSalary(double salary) { this.salary = salary; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }

        public String getAddress() { return address; }
        public void setAddress(String address) { this.address = address; }

        public LocalDateTime getHireDate() { return hireDate; }
        public void setHireDate(LocalDateTime hireDate) { this.hireDate = hireDate; }

        public int getPerformanceRating() { return performanceRating; }
        public void setPerformanceRating(int performanceRating) {
            this.performanceRating = performanceRating;
        }

        public String getEmployeeType() { return employeeType; }
        public void setEmployeeType(String employeeType) {
            this.employeeType = employeeType;
        }

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }

        public int getYearsOfExperience() { return yearsOfExperience; }
        public void setYearsOfExperience(int yearsOfExperience) {
            this.yearsOfExperience = yearsOfExperience;
        }

        public BenefitsCalculator getBenefitsCalculator() { return benefitsCalculator; }
        public void setBenefitsCalculator(BenefitsCalculator benefitsCalculator) {
            this.benefitsCalculator = benefitsCalculator;
        }

        @Override
        public String toString() {
            return "Employee{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", department='" + department + '\'' +
                    ", status='" + status + '\'' +
                    '}';
        }
    }

    // ========== 사용 예시를 위한 메인 메서드 ==========

    public static void main(String[] args) {
        Functions functions = new Functions();

        // 테스트 직원들 생성
        Employee employee1 = new Employee("E001", "John Doe", 30, "Engineering", 75000.0,
                "FULL_TIME", new FullTimeBenefitsCalculator());
        employee1.setEmail("john.doe@company.com");
        employee1.setPerformanceRating(4);
        employee1.setYearsOfExperience(3);

        Employee employee2 = new Employee("E002", "Jane Smith", 28, "Marketing", 45000.0,
                "PART_TIME", new PartTimeBenefitsCalculator());
        employee2.setEmail("jane.smith@company.com");
        employee2.setPerformanceRating(3);
        employee2.setYearsOfExperience(2);

        List<Employee> employees = Arrays.asList(employee1, employee
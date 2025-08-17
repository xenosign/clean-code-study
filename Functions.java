import java.util.*;
import java.util.stream.Collectors;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
        double totalSalary = calculateTotalSalary(employee);
        double netSalary = calculateNetSalary(totalSalary);
        printEmployeePayroll(employee, netSalary);
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

        if (rating >= 4) return baseSalary * 0.1;
        if (rating >= 3) return baseSalary * 0.05;
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
        return name.trim().substring(0, 1).toUpperCase() +
                name.trim().substring(1).toLowerCase();
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

    // 좋은 예: 다형성 활용 (인터페이스와 구현체들)
    public double calculateEmployeeBenefits(Employee employee) {
        return employee.getBenefitsCalculator().calculate(employee.getSalary());
    }

    // ========== 5. 서술적인 이름을 사용해라 ==========

    // 나쁜 예: 의미 없는 함수명
    public List<Employee> get(List<Employee> list) {
        return list.stream()
                .filter(e -> e.getSalary() > 50000)
                .collect(Collectors.toList());
    }

    // 좋은 예: 서술적인 함수명
    public List<Employee> getHighSalaryEmployees(List<Employee> employees) {
        return employees.stream()
                .filter(this::isHighSalaryEmployee)
                .collect(Collectors.toList());
    }

    private boolean isHighSalaryEmployee(Employee employee) {
        return employee.getSalary() > 50000;
    }

    // 더 구체적인 서술적 이름들
    public List<Employee> getEmployeesEligibleForPromotion(List<Employee> employees) {
        return employees.stream()
                .filter(this::hasMinimumExperience)
                .filter(this::hasGoodPerformanceRating)
                .collect(Collectors.toList());
    }

    private boolean hasMinimumExperience(Employee employee) {
        return employee.getYearsOfExperience() >= 2;
    }

    private boolean hasGoodPerformanceRating(Employee employee) {
        return employee.getPerformanceRating() >= 3;
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
        return principal * (rate * Math.pow(1 + rate, months)) / (Math.pow(1 + rate, months) - 1);
    }

    // Flag 인수는 피하고 메서드를 분리
    // 나쁜 예
    public void printEmployeeInfoBAD(Employee employee, boolean detailed) {
        if (detailed) {
            // 상세 정보 출력
        } else {
            // 요약 정보 출력
        }
    }

    // 좋은 예: 메서드 분리
    public void printEmployeeSummary(Employee employee) {
        System.out.println(employee.getName() + " - " + employee.getDepartment());
    }

    public void printEmployeeDetailedInfo(Employee employee) {
        System.out.println("Name: " + employee.getName());
        System.out.println("Department: " + employee.getDepartment());
        System.out.println("Salary: " + employee.getSalary());
        System.out.println("Email: " + employee.getEmail());
    }

    // ========== 7. 부수 효과를 일으키지 마라 ==========

    private String currentUserSession = "";
    private List<String> auditLog = new ArrayList<>();

    // 나쁜 예: 함수명과 다른 부수 효과 발생
    public boolean checkPasswordBAD(String userName, String password) {
        boolean isValid = authenticateUser(userName, password);
        if (isValid) {
            currentUserSession = userName; // 부수 효과!
            auditLog.add("User logged in: " + userName); // 부수 효과!
        }
        return isValid;
    }

    // 좋은 예: 부수 효과 없이 순수 함수로 만들기
    public boolean isValidPassword(String userName, String password) {
        return authenticateUser(userName, password);
    }

    // 부수 효과가 필요하다면 명시적으로 분리
    public void loginUser(String userName, String password) {
        if (isValidPassword(userName, password)) {
            initializeUserSession(userName);
            logUserActivity(userName, "LOGIN");
        }
    }

    private void initializeUserSession(String userName) {
        currentUserSession = userName;
    }

    private void logUserActivity(String userName, String activity) {
        auditLog.add(activity + ": " + userName + " at " + LocalDateTime.now());
    }

    // ========== 8. 명령과 조회를 분리해라 ==========

    // 나쁜 예: 명령과 조회가 섞임
    public boolean setAndCheckEmployeeStatusBAD(Employee employee, String status) {
        employee.setStatus(status); // 명령 (상태 변경)
        return "ACTIVE".equals(status); // 조회 (상태 확인)
    }

    // 좋은 예: 명령과 조회 분리
    public void setEmployeeStatus(Employee employee, String status) {
        employee.setStatus(status);
    }

    public boolean isEmployeeActive(Employee employee) {
        return "ACTIVE".equals(employee.getStatus());
    }

    // 사용 예시
    public void updateEmployeeToActive(Employee employee) {
        setEmployeeStatus(employee, "ACTIVE");
        if (isEmployeeActive(employee)) {
            System.out.println("Employee is now active");
        }
    }

    // ========== 9. 오류 코드보다 예외를 사용해라 ==========

    // 나쁜 예: 오류 코드 반환
    public int deleteEmployeeBAD(String employeeId) {
        if (employeeId == null || employeeId.trim().isEmpty()) {
            return -1; // 잘못된 ID
        }

        Employee employee = findEmployeeById(employeeId);
        if (employee == null) {
            return -2; // 직원을 찾을 수 없음
        }

        if ("ACTIVE".equals(employee.getStatus())) {
            return -3; // 활성 직원은 삭제 불가
        }

        // 삭제 로직
        removeEmployee(employee);
        return 0; // 성공
    }

    // 좋은 예: 예외 사용
    public void deleteEmployee(String employeeId) throws EmployeeException {
        validateEmployeeId(employeeId);

        Employee employee = getEmployeeById(employeeId);
        validateEmployeeCanBeDeleted(employee);

        removeEmployee(employee);
    }

    private void validateEmployeeId(String employeeId) throws EmployeeException {
        if (employeeId == null || employeeId.trim().isEmpty()) {
            throw new EmployeeException("Employee ID cannot be null or empty");
        }
    }

    private Employee getEmployeeById(String employeeId) throws EmployeeException {
        Employee employee = findEmployeeById(employeeId);
        if (employee == null) {
            throw new EmployeeException("Employee not found with ID: " + employeeId);
        }
        return employee;
    }

    private void validateEmployeeCanBeDeleted(Employee employee) throws EmployeeException {
        if ("ACTIVE".equals(employee.getStatus())) {
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
        sendEmail(employee.getEmail(), subject, body);
        logEmailSent(employee.getEmail(), subject);
    }

    public void sendGoodbyeEmailBAD(Employee employee) {
        String subject = "Thank you for your service";
        String body = "Dear " + employee.getName() + ",\n" +
                "Thank you for your service at our company!\n" +
                "Best regards,\n" +
                "HR Team";
        sendEmail(employee.getEmail(), subject, body);
        logEmailSent(employee.getEmail(), subject);
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
        String subject = template.getSubject();
        String body = buildEmailBody(employee, template);

        sendEmail(employee.getEmail(), subject, body);
        logEmailSent(employee.getEmail(), subject);
    }

    private String buildEmailBody(Employee employee, EmailTemplate template) {
        return String.format("Dear %s,\n%s\nBest regards,\nHR Team",
                employee.getName(), template.getContent());
    }

    // Helper 메서드들 (실제 구현은 생략)
    private boolean authenticateUser(String userName, String password) {
        // 인증 로직
        return true;
    }

    private Employee findEmployeeById(String employeeId) {
        // 직원 검색 로직
        return null;
    }

    private void removeEmployee(Employee employee) {
        // 직원 삭제 로직
    }

    private void sendEmail(String email, String subject, String body) {
        // 이메일 발송 로직
        System.out.println("Email sent to: " + email);
    }

    private void logEmailSent(String email, String subject) {
        // 이메일 발송 로그
        System.out.println("Email logged: " + subject + " to " + email);
    }

    private void generateDetailedReport(Employee employee) {
        // 상세 리포트 생성 로직
    }

    // 이메일 템플릿 열거형
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

    // 커스텀 예외 클래스
    static class EmployeeException extends Exception {
        public EmployeeException(String message) {
            super(message);
        }
    }
}

// ========== 관련 인터페이스와 클래스들 ==========

interface BenefitsCalculator {
    double calculate(double salary);
}

class FullTimeBenefitsCalculator implements BenefitsCalculator {
    @Override
    public double calculate(double salary) {
        return salary * 0.2;
    }
}

class PartTimeBenefitsCalculator implements BenefitsCalculator {
    @Override
    public double calculate(double salary) {
        return salary * 0.1;
    }
}

class ContractBenefitsCalculator implements BenefitsCalculator {
    @Override
    public double calculate(double salary) {
        return 0;
    }
}

class Employee {
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
    public Employee(String name, int age, String department, double salary,
                    String employeeType, BenefitsCalculator benefitsCalculator) {
        this.name = name;
        this.age = age;
        this.department = department;
        this.salary = salary;
        this.employeeType = employeeType;
        this.benefitsCalculator = benefitsCalculator;
        this.status = "INACTIVE";
    }

    // Getters and Setters
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
}
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

public class CleanFunctions {

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
}
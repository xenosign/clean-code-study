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
}
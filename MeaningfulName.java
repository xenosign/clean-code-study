import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MeaningfulName {
    // 변수 명
    int d; // 경과 시간

    int elapsedTime;
    int daysSinceCreation;

    
    // 함수 명
    int[][] theList;

    public List<int[]> getThem() {
        List<int[]> list1 = new ArrayList<int[]>();

        for (int[] x : theList) {
            if (x[0] == 4) list1.add(x);
        }

        return list1;
    }

    // 의미 있는 이름 사용
    int STAUS_VALUE = 1;
    int FLAGGED = 1;
    int[][] gameBoard;

    public List<int[]> getFlaggedCells() {
        List<int[]> flaggedCells = new ArrayList<int[]>();

        for (int[] cell : gameBoard) {
            if (cell[STAUS_VALUE] == FLAGGED) flaggedCells.add(cell);
        }

        return flaggedCells;
    }

    // 별도의 클래스를 만들어 사용
    class Cell {
        int[] cell;

        public Cell(int[] cell) {
            this.cell = cell;
        }

        public boolean isFlagged() {
            int STATUS_VALUE = 1;
            int FLAGGED = 1;
            return cell[STATUS_VALUE] == FLAGGED;
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

    class DtaRcrd102 {
        private Date genymdhms;
        private Date modymdhms;
        private final String pszqint = "102";
    }

    class Customer {
        private Date generationTimestamp;
        private Date modificationTimestamp;
        private final String recordId = "102";
    }

    public void easyToSearch() {

        int s = 0;
        int[] t = {1, 2, 3, 4};

        for (int j = 0; j < 34; j++) {
            s += (t[j] * 4) / 5;
        }

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

}

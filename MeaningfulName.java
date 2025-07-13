import java.util.ArrayList;
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
}

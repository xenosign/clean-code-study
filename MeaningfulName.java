import java.util.ArrayList;
import java.util.List;

public class MeaningfulName {
    int d; // 경과 시간

    int elapsedTime;
    int daysSinceCreation;

    int[][] theList;

    public List<int[]> getThem() {
        List<int[]> list1 = new ArrayList<int[]>();

        for (int[] x : theList) {
            if (x[0] == 4) list1.add(x);
        }

        return list1;
    }

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
}

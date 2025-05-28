import java.util.*;

public class Client {

    static ArrayList<ArrayList<Integer>> A = new ArrayList<>(
        Arrays.asList(
            new ArrayList<>(Arrays.asList(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1)),
            new ArrayList<>(Arrays.asList(1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1)),
            new ArrayList<>(Arrays.asList(1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0)),
            new ArrayList<>(Arrays.asList(1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0)),
            new ArrayList<>(Arrays.asList(1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0)),
            new ArrayList<>(Arrays.asList(1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0)),
            new ArrayList<>(Arrays.asList(1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0))
        )
    );

    public static void main(String[] args) {
        solve();
    }

    public static void solve() {
        ArrayList<String> answerList = new ArrayList<>();
        boolean[][] visited = new boolean[A.size()][A.get(0).size()];
        int[] start = findStart(A);
        if (start != null) {
            dfs(start[0], start[1], visited, answerList);
        }

        if (!hasTurn(answerList)) {
            System.out.println("Invalid path: No 90-degree turn.");
            return;
        }

        for (String step : answerList) {
            System.out.println(step);
        }

        printPathMap(answerList);
    }

    public static int[] findStart(ArrayList<ArrayList<Integer>> map) {
        int rows = map.size();
        int cols = map.get(0).size();
        for (int r = 0; r < rows; r++) {
            if (map.get(r).get(0) == 1) return new int[]{r, 0};
            if (map.get(r).get(cols - 1) == 1) return new int[]{r, cols - 1};
        }
        for (int c = 0; c < cols; c++) {
            if (map.get(0).get(c) == 1) return new int[]{0, c};
            if (map.get(rows - 1).get(c) == 1) return new int[]{rows - 1, c};
        }
        return null;
    }

    public static void dfs(int r, int c, boolean[][] visited, ArrayList<String> path) {
        if (r < 0 || c < 0 || r >= A.size() || c >= A.get(0).size()) return;
        if (A.get(r).get(c) != 1 || visited[r][c]) return;

        visited[r][c] = true;
        path.add("A[" + r + "][" + c + "]");

        dfs(r, c + 1, visited, path); 
        dfs(r + 1, c, visited, path); 
        dfs(r, c - 1, visited, path); 
        dfs(r - 1, c, visited, path); 
    }

    public static boolean hasTurn(ArrayList<String> path) {
        if (path.size() < 3) return false;
        int[] dir1 = getDirection(path.get(0), path.get(1));
        for (int i = 1; i < path.size() - 1; i++) {
            int[] dir2 = getDirection(path.get(i), path.get(i + 1));
            if (dir1[0] != dir2[0] || dir1[1] != dir2[1]) {
                return true;
            }
            dir1 = dir2;
        }
        return false;
    }

    public static int[] getDirection(String a, String b) {
        int r1 = Integer.parseInt(a.substring(a.indexOf('[') + 1, a.indexOf(']')));
        int c1 = Integer.parseInt(a.substring(a.lastIndexOf('[') + 1, a.lastIndexOf(']')));
        int r2 = Integer.parseInt(b.substring(b.indexOf('[') + 1, b.indexOf(']')));
        int c2 = Integer.parseInt(b.substring(b.lastIndexOf('[') + 1, b.lastIndexOf(']')));
        return new int[]{r2 - r1, c2 - c1};
    }

    public static void printPathMap(ArrayList<String> path) {
        int rows = A.size();
        int cols = A.get(0).size();
        String[][] visual = new String[rows][cols];
        for (String[] row : visual) Arrays.fill(row, " ");

        for (String coord : path) {
            int r = Integer.parseInt(coord.substring(coord.indexOf('[') + 1, coord.indexOf(']')));
            int c = Integer.parseInt(coord.substring(coord.lastIndexOf('[') + 1, coord.lastIndexOf(']')));
            visual[r][c] = "*";
        }

        for (String[] row : visual) {
            for (String s : row) {
                System.out.print("[" + s + "]");
            }
            System.out.println();
        }
    }
}

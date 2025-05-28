import java.util.*;

public class Client {

    static ArrayList<ArrayList<Integer>> A = new ArrayList<>(Arrays.asList(
        new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 1)),
        new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 1)),
        new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 1)),
        new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 1)),
        new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 1)),
        new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 1)),
        new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 1)),
        new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 1)),
        new ArrayList<>(Arrays.asList(1, 1, 1, 1, 1, 1, 1, 1, 1))
    ));

    static boolean reachedExit = false;

    public static void main(String[] args) {
        ArrayList<String> path = new ArrayList<>();
        boolean[][] visited = new boolean[A.size()][A.get(0).size()];
        int[] start = findStart();

        if (start != null && dfs(start[0], start[1], visited, path, -1, -1)) {
            if (!hasTurn(path)) {
                System.out.println("Path has no 90-degree turn.");
                return;
            }
            printPath(path);
            printVisual(path);
        } else {
            System.out.println("No valid path found.");
        }
    }

    public static int[] findStart() {
        int rows = A.size(), cols = A.get(0).size();
        for (int r = 0; r < rows; r++) {
            if (A.get(r).get(0) == 1) return new int[]{r, 0};
            if (A.get(r).get(cols - 1) == 1) return new int[]{r, cols - 1};
        }
        for (int c = 0; c < cols; c++) {
            if (A.get(0).get(c) == 1) return new int[]{0, c};
            if (A.get(rows - 1).get(c) == 1) return new int[]{rows - 1, c};
        }
        return null;
    }

    public static boolean dfs(int r, int c, boolean[][] visited, ArrayList<String> path, int pr, int pc) {
        if (r < 0 || c < 0 || r >= A.size() || c >= A.get(0).size()) return false;
        if (A.get(r).get(c) != 1 || visited[r][c]) return false;

        visited[r][c] = true;
        path.add("A[" + r + "][" + c + "]");

        if (isOnEdge(r, c) && path.size() > 1 && !isSameEdge(r, c, path.get(0))) {
            reachedExit = true; 
        }

        int[][] dirs = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        for (int[] d : dirs) {
            int nr = r + d[0], nc = c + d[1];
            if (nr == pr && nc == pc) continue; 
            if (dfs(nr, nc, visited, path, r, c)) return true;
        }

        if (reachedExit) return true;

        path.remove(path.size() - 1);
        return false;
    }

    public static boolean isOnEdge(int r, int c) {
        return r == 0 || c == 0 || r == A.size() - 1 || c == A.get(0).size() - 1;
    }

    public static boolean isSameEdge(int r, int c, String start) {
        int sr = Integer.parseInt(start.substring(start.indexOf('[') + 1, start.indexOf(']')));
        int sc = Integer.parseInt(start.substring(start.lastIndexOf('[') + 1, start.lastIndexOf(']')));
        return (sr == 0 && r == 0) || (sr == A.size() - 1 && r == A.size() - 1)
            || (sc == 0 && c == 0) || (sc == A.get(0).size() - 1 && c == A.get(0).size() - 1);
    }

    public static boolean hasTurn(ArrayList<String> path) {
        if (path.size() < 3) return false;
        int[] a = getCoords(path.get(0)), b = getCoords(path.get(1));
        int dx = b[0] - a[0], dy = b[1] - a[1];
        for (int i = 2; i < path.size(); i++) {
            int[] c = getCoords(path.get(i));
            int ndx = c[0] - b[0], ndy = c[1] - b[1];
            if (dx != ndx || dy != ndy) return true;
            b = c;
            dx = ndx;
            dy = ndy;
        }
        return false;
    }

    public static int[] getCoords(String s) {
        int r = Integer.parseInt(s.substring(s.indexOf('[') + 1, s.indexOf(']')));
        int c = Integer.parseInt(s.substring(s.lastIndexOf('[') + 1, s.lastIndexOf(']')));
        return new int[]{r, c};
    }

    public static void printPath(ArrayList<String> path) {
        System.out.println("Path Coordinates:");
        for (String s : path) System.out.println(s);
    }

    public static void printVisual(ArrayList<String> path) {
        int rows = A.size(), cols = A.get(0).size();
        String[][] grid = new String[rows][cols];
        for (String[] row : grid) Arrays.fill(row, " ");
        for (String s : path) {
            int[] rc = getCoords(s);
            grid[rc[0]][rc[1]] = "1";
        }

        System.out.println("\nVisual Map:");
        for (String[] row : grid) {
            for (String cell : row) {
                System.out.print("[" + cell + "]");
            }
            System.out.println();
        }
    }
}

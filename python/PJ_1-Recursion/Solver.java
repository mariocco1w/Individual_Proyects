/*
    Esta es su clase principal. El unico metodo que debe implementar es
    public String[] solve(Maze maze)
    pero es libre de crear otros metodos y clases en este u otro archivo que desee.
*/
import java.util.*;

public class Solver{
    private static final char[] DIRECTIONS = {'N', 'S', 'W', 'E', 'U', 'D'};

    private List<Character> bestPath;
    private Set<Node> visited;

    public String solve(Maze maze) {
        bestPath = new ArrayList<>();
        visited = new HashSet<>();
        
        Node start = maze.getStartingSpace();
        List<Character> currentPath = new ArrayList<>();
        
        dfs(maze, start, currentPath);
        
        StringBuilder path = new StringBuilder();
        path.append("[");
        for(int i = 0; i < bestPath.size(); i++) {
            path.append(bestPath.get(i));
            if(i < bestPath.size() - 1) {
                path.append(",");
            }
        }
        path.append("]");
        return path.toString();
    }

    private void dfs(Maze maze, Node current, List<Character> path) {
        if (current.isExit) {
            if (bestPath.isEmpty() || path.size() < bestPath.size()) {
                bestPath = new ArrayList<>(path);
            }
            return;
        }

        visited.add(current);
        
        for (int i = 0; i < DIRECTIONS.length; i++) {
            Node next = move(maze, current, i);
            if (next != current && !visited.contains(next) && !next.danger) {
                path.add(DIRECTIONS[i]);
                dfs(maze, next, path);
                path.remove(path.size() - 1);
            }
        }
        visited.remove(current);
    }

    private Node move(Maze maze, Node current, int directionIndex) {
        return switch (DIRECTIONS[directionIndex]) {
            case 'N' -> maze.moveNorth(current);
            case 'S' -> maze.moveSouth(current);
            case 'W' -> maze.moveWest(current);
            case 'E' -> maze.moveEast(current);
            case 'U' -> maze.moveUp(current);
            case 'D' -> maze.moveDown(current);
            default -> current;
        };
    }
}



    

public class CampusWalk {
    private Map map;
    
    public CampusWalk(String filename, boolean showMap) {
        try {
            map = new Map(filename);
            if (showMap) {
                map.showGUI();
            } else {
                map.hideGUI();
            }
        } catch (Exception e) {
            System.out.println("Error occurred");
        }
    }
    
    public int neighbourGooseCount(Hexagon cell) {
        int count = 0;
        for (int i = 0; i < 6; i++) {
            Hexagon neighbor = cell.getNeighbour(i);
            if (neighbor != null && neighbor.isGooseCell()) {
                count++;
            }
        }
        return count;
    }
    
    public Hexagon findBest(Hexagon cell) {
        Hexagon best = null;
        int lowestGooseCount = Integer.MAX_VALUE;
        
        for (int i = 0; i < 6; i++) {
            Hexagon neighbor = cell.getNeighbour(i);
            if (neighbor == null || neighbor.isMarked() || neighbor.isGooseCell()) {
                continue;
            }
            
            if (neighbor.isEnd()) {
                return neighbor;
            }
            if (neighbor.isBookCell()) {
                return neighbor;
            }
            if (neighbor.isGrassCell() && neighbourGooseCount(neighbor) < lowestGooseCount) {
                lowestGooseCount = neighbourGooseCount(neighbor);
                best = neighbor;
            }
            if (best == null && neighbor.isSnowCell()) {
                best = neighbor;
            }
        }
        return best;
    }
    
    public String findPath() {
        ArrayStack<Hexagon> stack = new ArrayStack<>();
        Hexagon start = map.getStart();
        stack.push(start);
        start.markInStack();
        
        StringBuilder path = new StringBuilder();
        boolean running = true;
        
        while (!stack.isEmpty() && running) {
            Hexagon curr = stack.peek();
            path.append(curr.getID()).append(" ");
            
            if (curr.isEnd()) {
                running = false;
                break;
            }
            
            Hexagon next = findBest(curr);
            if (next == null) {
                stack.pop();
                curr.markOutStack();
            } else {
                stack.push(next);
                next.markInStack();
            }
        }
        return running ? path.toString() : "No path found";
    }
    
    public void exit() {
        map.exit();
    }
    
    public static void main(String[] args) {
        Hexagon.TIME_DELAY = 500;
        CampusWalk walk = new CampusWalk("map1.txt", true);
        System.out.println(walk.findPath());
    }
}
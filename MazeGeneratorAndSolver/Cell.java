import java.util.LinkedList;

public class Cell {

    LLAddOnly head;
    Cell next;

    boolean inSolution=false;
    Cell parent=null;
    boolean visited;
    Wall down;
    Wall up;
    Wall left;
    Wall right;

    LinkedList<Cell> edges = new LinkedList<Cell>();

    public LinkedList<Cell> getSuccessors(){
        return edges;
    }

}
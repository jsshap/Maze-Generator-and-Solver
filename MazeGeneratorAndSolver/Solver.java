import java.util.LinkedList;
public class Solver{

    static Cell source;
    static Cell target;

    public static void solve(Cell[][] mazeMap, Wall[] walls){
        source = mazeMap [0][0];
        target = mazeMap [mazeMap.length-1][mazeMap.length-1];

        for (Wall w : walls){
            if (!w.visible && !(w.first==null || w.second ==null)){
                w.first.edges.add(w.second);
                w.second.edges.add(w.first);
            }
        }
        LinkedList<Cell> q = new LinkedList<Cell>();
        Cell current = source;
        //source.visited=true;
        q.add(source);
        while (!q.isEmpty()){
            current = q.removeLast();
            if (current.equals(target))
                break;
            LinkedList<Cell> succ = current.getSuccessors();
            for (Cell c : succ){
                if (! c.visited){
                    c.visited=true;
                    c.parent=current;
                    q.add(c);
                }
            }  
        }
        backtrack();
    }
    public static void backtrack(){
        Cell current = target;
        current.inSolution=true;
	    source.inSolution=true;
        while (!current.equals(source)){
            current.inSolution=true;
            current=current.parent;
        }
    }
}

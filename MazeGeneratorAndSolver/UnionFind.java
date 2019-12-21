public class UnionFind{


    public static void makeset (Cell c){
        LLAddOnly newSet = new LLAddOnly();
        newSet.add(c);
    }

    public static LLAddOnly find(Cell c){

        return c.head;
    }

    public static void union (Cell a, Cell b){
        LLAddOnly llToAdd;
        LLAddOnly addingTo;
        if (find(a).size<find(b).size) {
            llToAdd = find(a);
            addingTo = find(b);
        }
        else {
            llToAdd = find(b);
            addingTo = find(a);
        }
        Cell adding=llToAdd.first;
        Cell temp;
        while (adding!=null){
            temp=adding;
            adding=adding.next;
            addingTo.add(temp);
        }
    }
}
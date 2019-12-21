public class LLAddOnly {

    Cell first;
    Cell last;
    public int size=0;


    //for debug purposes
    int id;
    static int total=0;



    public void add(Cell x) {
		if(first == null) {
			first = x;
			last = x;
			x.head = this;
			size++;


			id=total;
			total++;

		}
		else {
			x.next = first;
			first = x;
			x.head = this;
			size++;
		}
    }

}
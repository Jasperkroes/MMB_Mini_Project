import com.sun.istack.internal.FinalArrayList;
import sun.awt.image.ImageWatched;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * Created by Jasper on 25-1-2018.
 */
public class Node {
	private Queue<Cell> prolif;
	private Cell north;
	private Cell east;
	private Cell south;
	private Cell west;
	private int beta;
	//used to update the node
	private Queue<Cell> newCells;

	public Node(int b) {
		this.beta = b;
		this.prolif = new ArrayBlockingQueue<>(beta);
		this.north = null;
		this.east = null;
		this.south = null;
		this.west = null;
		this.newCells = new ArrayBlockingQueue<>(beta+4);
	}

	public void Update() {
		//puts all proliferative cells in the newcells queue, that way they will stay in the same node the next timestep
		//updates the cell to give it a new state
		while(!prolif.isEmpty()){
//			System.out.println(newCells.size()+" "+prolif.size());
			newCells.add(prolif.remove());
		}
		prolif.clear();

		//at this point newCells contains at max beta+4 cells

		LinkedList<Cell> ll = new LinkedList<>();
		ll.add(north);
		ll.add(east);
		ll.add(south);
		ll.add(west);

		//reassign cells to new position
		while (! newCells.isEmpty() ) {
			//update the cell to get possibly a new state
			Cell c = newCells.remove().Update();

			//Assign prolif cells to prolif spots if possible
			if (c.getState()==State.PROLIFERATION) {
				if(prolif.size()<beta){
					prolif.add(c);
				}
				else{
					SetRandomDirection(c,ll);
					c.setState(State.MIGRATION);
				}
			}
			// Assign migrative cells to migration spots
			else {
				//migrative cells get randomly assigned to a movement channel
				if (!SetRandomDirection(c,ll)) {
					//if there are more migrative cells than migration spots turn them into proliferative cells
					c.setState(State.PROLIFERATION);
					if(prolif.size()<beta){
						prolif.add(c);
					}
				}
			}
		}
	}

	public boolean addCellToNode(Cell c){
		if(prolif.size()<beta){
			prolif.add(c);
			return true;
		}
		return false;
	}

	private boolean SetRandomDirection(Cell c, List<Cell> l) {
		if(l.size()==0){
			return false;
		}
		Random rand = new Random();
		Cell pos = l.remove(rand.nextInt(l.size()));
		if(pos == north) {
			north = c;
		}
		else if(pos == south) {
			south = c;
		}
		else if(pos == east) {
			east = c;
		}
		else {
			west = c;
		}

		return true;
	}

	//<DSA<F<FAS<F<ASFD<ASDFSADPF
	//312558 proliferation
	//5087 migration
	private void Proliferate2(Queue<Cell> queue) {
		int k = Math.min(queue.size(),beta/2);
		LinkedList<Cell> list = new LinkedList<>();
		for(int i = 0;i<k;i++){
			list.add(queue.peek());
		}
		for(int j = 0;j<k;j++){
			if(queue.size()<beta) {
				queue.offer(list.get(j));
			}
		}
	}

	public void Proliferate(){
		int size = prolif.size()*2;
		if(size==0){
			return;
		}
		//copy all elements into a list.
		LinkedList<Cell> l = new LinkedList<>();
		Iterator<Cell> it = prolif.iterator();
		while(it.hasNext()) {
			l.add(it.next());
		}

		//add clones of the old cells to the next timestep of the node
		//unless the size is larger than beta
		Iterator<Cell> it2 = l.iterator();
		while(it2.hasNext()){
			if(prolif.size()>=size||prolif.size()>=beta){
				return;
			}
			prolif.add(it2.next());
		}
	}

	public int[][] CountStatesPerType(int[][] count) {
		//prolif count
		int state = 0;
		for (Cell c : prolif) {
			if (c.getType() == Type.A) {
				count[0][state]++;
			} else if (c.getType() == Type.B) {
				count[1][state]++;
			} else
				count[2][state]++;
		}

		state = 1;
		//north count
		if(north!=null){
			if (north.getType() == Type.A) {
				count[0][state]++;
			} else if (north.getType() == Type.B) {
				count[1][state]++;
			} else
				count[2][state]++;
		}
		//east count
		if(east!=null){
			if (east.getType() == Type.A) {
				count[0][state]++;
			} else if (east.getType() == Type.B) {
				count[1][state]++;
			} else
				count[2][state]++;
		}
		//south count
		if(south!=null){
			if (south.getType() == Type.A) {
				count[0][state]++;
			} else if (south.getType() == Type.B) {
				count[1][state]++;
			} else
				count[2][state]++;
		}
		//west count
		if(west!=null){
			if (west.getType() == Type.A) {
				count[0][state]++;
			} else if (west.getType() == Type.B) {
				count[1][state]++;
			} else
				count[2][state]++;
		}

		return count;
	}

	public Cell getEast() {
		return east;
	}

	public Cell getNorth() {
		return north;
	}

	public Cell getSouth() {
		return south;
	}

	public Cell getWest() {
		return west;
	}

	public void setNorth(Cell c) {
		north = c;
	}

	public void setEast(Cell c) {
		east = c;
	}

	public void setSouth(Cell c) {
		south = c;
	}

	public void setWest(Cell c) {
		west = c;
	}

	public void addToNewCells(Cell c){
		newCells.add(c);
	}

//	public ArrayList<Cell> getProlif(){
//		return prolif;
//	}

}

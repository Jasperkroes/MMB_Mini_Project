import java.util.Comparator;
import java.util.PriorityQueue;

public class Main {
	private static final int WIDTH = 100;
	private static final int HEIGT = 100;
	private static final int BETA = 8;
	private static final int TIMESTEPS = 2000;
	private static final int TypeACells = 100;
	private static final int TypeBCells = 100;
	private static final int TypeCCells = 100;
	//proliferation rate is a double in the range of 0-1
	//where 0 is only migration and 1 is only proliferation
	private static final double ProlifRateA = 1;
	private static final double ProlifRateB = 0.61;
	private static final double ProlifRateC = 0.23;

    public static void main(String[] args) {
		LGCA lgca = new LGCA(WIDTH,HEIGT,BETA,ProlifRateA,ProlifRateB,ProlifRateC);


		//Cells get randomly assigned to a point on the grid
		//add A type cells to the grid
		for(int i = 0;i<TypeACells;i++) {
			lgca.addToGrid(new Cell(Type.A,ProlifRateA));
		}
		//add B type cells to the grid
		for(int i = 0;i<TypeBCells;i++) {
			lgca.addToGrid(new Cell(Type.B,ProlifRateB));
		}
		//add C type cells to the grid
		for(int i = 0;i<TypeCCells;i++) {
			lgca.addToGrid(new Cell(Type.C,ProlifRateC));
		}


		for(int i = 0;i<TIMESTEPS;i++){
			lgca.Update();
			int l = 100*i/TIMESTEPS;
			System.out.println(l+"% Done");
		}

		System.out.println(lgca.ShowResults());

		/*
		Comparator<Cell> comp = new CellComparator();
		PriorityQueue<Cell> kk = new PriorityQueue<>(3, comp);
		Cell b = new Cell(Type.B,0.8);
		b.setState(State.MIGRATION);
		Cell a = new Cell(Type.A,0.5);
		a.setState(State.PROLIFERATION);
		Cell c = new Cell(Type.C,0.5);
		c.setState(State.MIGRATION);
		kk.add(b);
		kk.add(a);
		kk.add(c);

		while (kk.size() != 0)
		{
			System.out.printf("%s\n",kk.remove());
		}
		*/
    }
}

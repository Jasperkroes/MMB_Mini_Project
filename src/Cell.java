import java.util.Random;

/**
 * Created by Jasper on 25-1-2018.
 */
public class Cell {
	private Type type;
	private State state;
	private double proliferationRate;

	public Cell(Type t, double rate){
		this.type = t;
		this.proliferationRate = rate;
		this.state = State.PROLIFERATION;
	}

	public Cell Update() {
		Random r = new Random();
		if(r.nextDouble()<proliferationRate)
			this.state = State.PROLIFERATION;
		else
			this.state = State.MIGRATION;

		return this;
	}

	public Type getType() {
		return this.type;
	}

	public State getState() {
		return this.state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public String toString() {
		return "Type: "+this.type+"\t State: "+this.state;
	}

	public double getProliferationRate() {
		return proliferationRate;
	}
}

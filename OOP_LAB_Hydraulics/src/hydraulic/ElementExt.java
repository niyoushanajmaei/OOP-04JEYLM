package hydraulic;

public abstract class ElementExt extends Element{
	
	double maxFlow;
	
	public ElementExt(String name) {
		super(name);
		maxFlow = Double.MAX_VALUE;
	}

	public void setMaxFlow(double maxFlow) {
		this.maxFlow = maxFlow;
	}
	
}

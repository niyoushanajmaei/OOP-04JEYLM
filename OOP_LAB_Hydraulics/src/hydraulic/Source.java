package hydraulic;

/**
 * Represents a source of water, i.e. the initial element for the simulation.
 *
 * The status of the source is defined through the method
 * {@link #setFlow(double) setFlow()}.
 */
public class Source extends ElementExt {
	

	public Source(String name) {
		super(name);
	}

	/**
	 * defines the flow produced by the source
	 * 
	 * @param flow
	 */
	public void setFlow(double flow){
		this.outFlow[0] = flow;
	}

	@Override
	public String toString() {
		return "source";
	}
	
	@Override
	public void setMaxFlow(double maxFlow) {
	}
	

}

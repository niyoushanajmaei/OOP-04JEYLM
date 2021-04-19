package hydraulic;

/**
 * Represents a split element, a.k.a. T element
 * 
 * During the simulation each downstream element will
 * receive a stream that is half the input stream of the split.
 */

public class Split extends ElementExt {
	
	int numOuput = 2;
	double[] proportions;
	

	/**
	 * Constructor
	 * @param name
	 */
	public Split(String name) {
		super(name);
		outFlow = new double[2];
		output = new Element[2];
		proportions = new double[2];
		for(int i=0;i<2;i++) {
			outFlow[i] = NO_FLOW;
			output[i] = null;
			proportions[i] = 0.5;
		}
	}
    
	/**
	 * returns the downstream elements
	 * @return array containing the two downstream element
	 */
    public Element[] getOutputs(){
        return output;
    }

    /**
     * connect one of the outputs of this split to a
     * downstream component.
     * 
     * @param elem  the element to be connected downstream
     * @param noutput the output number to be used to connect the element
     */
	public void connect(Element elem, int noutput){
		output[noutput] = elem;
		elem.input = this;
	}

	@Override
	public String toString() {
		return "split" ;
	}

	public int getNumOutput() {
		return 2;
	}

	public double[] getProportion() {
		return proportions;
	}
}

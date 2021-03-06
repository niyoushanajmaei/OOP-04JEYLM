package hydraulic;

/**
 * Represents a multisplit element, an extension of the Split that allows many outputs
 * 
 * During the simulation each downstream element will
 * receive a stream that is determined by the proportions.
 */

public class Multisplit extends Split {

	int numOutput = 0;

	/**
	 * Constructor
	 * @param name
	 * @param numOutput
	 */
	public Multisplit(String name, int numOutput) {
		super(name);
		this.numOutput = numOutput;
		output = new Element[numOutput];
		outFlow = new double[numOutput];
		for (int i=0;i<numOutput;i++) {
			output[i] = null;
			outFlow[i] = NO_FLOW;
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
		elem.input = this;
		output[noutput] = elem;
	}
	
	/**
	 * Define the proportion of the output flows w.r.t. the input flow.
	 * 
	 * The sum of the proportions should be 1.0 and 
	 * the number of proportions should be equals to the number of outputs.
	 * Otherwise a check would detect an error.
	 * 
	 * @param proportions the proportions of flow for each output
	 */
	public void setProportions(double... proportions) {
		this.proportions = proportions;
	}
	
	@Override
	public int getNumOutput() {
		return numOutput;
	}
}

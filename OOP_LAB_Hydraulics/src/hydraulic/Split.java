package hydraulic;

/**
 * Represents a split element, a.k.a. T element
 * 
 * During the simulation each downstream element will
 * receive a stream that is half the input stream of the split.
 */

public class Split extends Element {
	
	int nOutput = 0;

	/**
	 * Constructor
	 * @param name
	 */
	public Split(String name) {
		super(name);
		outFlow = new double[2];
		outFlow[0] = NO_FLOW;
		outFlow[1] = NO_FLOW;
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
		if(nOutput == 0) {
			output = new Element[2];
			output[noutput] = elem;
		}else {
			output[noutput] = elem;
		}
		nOutput++;
	}
}
package hydraulic;

/**
 * Main class that act as a container of the elements for
 * the simulation of an hydraulics system 
 * 
 */
public class HSystem {
	
	Element[] elements = new Element[0];
	int nElements = 0;
	
	/**
	 * Adds a new element to the system
	 * @param elem
	 */
	public void addElement(Element elem){
		Element[] newElements;
		if (nElements >= elements.length) {
			newElements = new Element[elements.length+1];
			for(int i=0;i<nElements;i++) {
				newElements[i] = elements[i];
			}
			elements = newElements;
		}
		elements[nElements] = elem;
		nElements++;
	}
	
	/**
	 * returns the element added so far to the system.
	 * If no element is present in the system an empty array (length==0) is returned.
	 * 
	 * @return an array of the elements added to the hydraulic system
	 */
	public Element[] getElements(){
		return elements;
	}
	
	/**
	 * starts the simulation of the system
	 */
	public void simulate(SimulationObserver observer){
		for(int i=0;i<nElements;i++) {
			if(!(elements[i] instanceof Sink)) {
				setFlows(elements[i]);
				if(elements[i] instanceof Source) {
					observer.notifyFlow("Source", elements[i].name, elements[i].inFlow, elements[i].outFlow);
				}else if(elements[i] instanceof Tap) {
					observer.notifyFlow("Tap", elements[i].name, elements[i].inFlow, elements[i].outFlow);
				}else if(elements[i] instanceof Split) {
					observer.notifyFlow("Split", elements[i].name, elements[i].inFlow, ((Split)elements[i]).outFlow);
				}
			}else {
				observer.notifyFlow("Sink", elements[i].name, elements[i].inFlow, elements[i].outFlow);
			}
		}
	}
	
	private void setFlows(Element elem) {
		if(elem instanceof Source) {
			elem.output[0].inFlow = elem.outFlow[0];
		}else if(elem instanceof Tap) {
			if(((Tap) elem).open) {
				elem.outFlow[0] = elem.inFlow;
				elem.output[0].inFlow = elem.outFlow[0];
			}else {
				elem.outFlow[0] = 0;
				elem.output[0].inFlow = 0;
			}
		}else if(elem instanceof Split) {
			for(int j=0;j<((Split)elem).getNumOutput();j++) {
				elem.outFlow[j] = elem.inFlow * ((Split)elem).getProportion()[j];
				((Split) elem).output[j].inFlow = elem.outFlow[j];
			}
		}
	}
	
}

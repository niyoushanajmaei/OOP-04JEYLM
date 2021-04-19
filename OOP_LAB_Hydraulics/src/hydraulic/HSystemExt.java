package hydraulic;

/**
 * Main class that act as a container of the elements for
 * the simulation of an hydraulics system 
 * 
 */
public class HSystemExt extends HSystem{
	
	/**
	 * Prints the layout of the system starting at each Source
	 */
	public String layout(){
		String str = "";
		int p=0;
		Element e  = super.getElements()[0];
		str = str + "["+ e.getName() +"]"+ e.toString()+" ";
		
		while(e!=null) {
			p = str.length();
			str += printElements(e.output,p);
				
			if(e.output.length>0) {
				e=e.output[0];
			}else {
				e = null;
			}
		}
		
		return str;
	}
	
	private String printElements(Element[] output, int p) {
		String str = "";
		int i = 0;
		
		if(output.length==0) {
		}else if (output.length == 1) {
			Element o = output[0];
			str = str + " -> [" + o.getName() +"]"+ o.toString()+" ";
		}else {
			for (Element o : output) {
				if(i>0) {
					for (int k=0;k<p;k++) {
						str += ' ';
					}
					str+="|\n";
					for (int k=0;k<p;k++) {
						str += ' ';
					}
				}
				str = str + "+-> [" + o.toString() +" "+ (char)('A'+i) +"]"+ o.name+'\n';
				i++;
			}
		}
		
		return str;
	}

	/**
	 * Deletes a previously added element with the given name from the system
	 */
	public Boolean deleteElement(String name) {
		 //System.out.println(elements.length);
		// System.out.println(name);
		Element e = findElement(name);
		if ((e instanceof Split && ((Split)e).getNumOutput() >1)) {
			return false;
		}
		if (e instanceof Source) {
			return false;
		}else if(e instanceof Sink) {
			return false;
		}else {
			Element i = findInput(e.getName());
			i.output = e.output;
			e.output[0].input = i;
		}
		return true;
	}

	private Element findInput(String name) {
		for (Element e : elements) {
			if (name.equals(e.output[0].name))
				return e;
		}
		return null;
	}

	private Element findElement(String name) {
		// System.out.println(elements[0].name);
		for (Element e : elements) {
			// System.out.println(e.name);
			if (name.equals(e.name))
				return e;
		}
		return null;
	}

	/**
	 * starts the simulation of the system; if enableMaxFlowCheck is true,
	 * checks also the elements maximum flows against the input flow
	 */
	public void simulate(SimulationObserverExt observer, boolean enableMaxFlowCheck) {
		if (enableMaxFlowCheck) {
			for (Element e: elements) {
				if(e instanceof ElementExt) {
					if (e.inFlow > ((ElementExt)e).maxFlow) {
						observer.notifyFlowError(e.toString(), e.name, e.inFlow, ((ElementExt)e).maxFlow);
					}
				}
			}
		}
	}
	
}

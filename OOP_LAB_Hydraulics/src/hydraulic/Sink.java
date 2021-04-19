package hydraulic;

/**
 * Represents the sink, i.e. the terminal element of a system
 *
 */
public class Sink extends ElementExt {

	/**
	 * Constructor
	 * @param name
	 */
	public Sink(String name) {
		super(name);
		output = new Element[0];
	}
	
	@Override
	public void connect(Element elem) {
		return;
	}

	@Override
	public String toString() {
		return "sink";
	}

	
	
}

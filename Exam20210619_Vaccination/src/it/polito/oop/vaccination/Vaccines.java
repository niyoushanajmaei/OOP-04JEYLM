package it.polito.oop.vaccination;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.time.temporal.TemporalAccessor;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import it.polito.oop.vaccination.Person.STATUS;

import java.util.*;
public class Vaccines {

    public final static int CURRENT_YEAR = java.time.LocalDate.now().getYear();
    
    SortedMap<String,Person> people = new TreeMap<>();
    List<Integer> intervals = new LinkedList<>();
    Map<String,Hub> hubs = new LinkedHashMap<>();
	List<Integer> hours = new LinkedList<>();
	BiConsumer<Integer, String> lst;

    // R1
    /**
     * Add a new person to the vaccination system.
     *
     * Persons are uniquely identified by SSN (italian "codice fiscale")
     *
     * @param firstName first name
     * @param lastName last name
     * @param ssn italian "codice fiscale"
     * @param year birth year
     * @return {@code false} if ssn is duplicate,
     */
    public boolean addPerson(String firstName, String lastName, String ssn, int year) {
    	if(people.containsKey(ssn)) {
    		return false;
    	}
    	people.put(ssn,new Person(firstName,lastName,ssn,year));
        return true;
    }

    /**
     * Count the number of people added to the system
     *
     * @return person count
     */
    public int countPeople() {
        return people.size();
    }

    /**
     * Retrieves information about a person.
     * Information is formatted as ssn, last name, and first name
     * separate by {@code ','} (comma).
     *
     * @param ssn "codice fiscale" of person searched
     * @return info about the person
     */
    public String getPerson(String ssn) {
        return people.get(ssn).toString();
    }

    /**
     * Retrieves of a person given their SSN (codice fiscale).
     *
     * @param ssn "codice fiscale" of person searched
     * @return age of person (in years)
     */
    public int getAge(String ssn) {
    	Person p = people.get(ssn);
        return CURRENT_YEAR - p.getYear();
    }

    /**
     * Define the age intervals by providing the breaks between intervals.
     * The first interval always start at 0 (non included in the breaks)
     * and the last interval goes until infinity (not included in the breaks).
     * All intervals are closed on the lower boundary and open at the upper one.
     * <p>
     * For instance {@code setAgeIntervals(40,50,60)}
     * defines four intervals {@code "[0,40)", "[40,50)", "[50,60)", "[60,+)"}.
     *
     * @param breaks the array of breaks
     */
    public void setAgeIntervals(int... breaks) {
    	for (int i:breaks) {
    		intervals.add(i);
    	}
    }

    /**
     * Retrieves the labels of the age intervals defined.
     *
     * Interval labels are formatted as {@code "[0,10)"},
     * if the upper limit is infinity {@code '+'} is used
     * instead of the number.
     *
     * @return labels of the age intervals
     */
    public Collection<String> getAgeIntervals() {
    	intervals.sort(Comparator.naturalOrder());
    	Collection<String> res = new LinkedList<>();
    	res.add("[0," +intervals.get(0)+")");
    	for (int i=0;i<intervals.size()-1;i++) {
    		res.add("["+intervals.get(i)+","+intervals.get(i+1)+")");
    	}
    	res.add("[" +intervals.get(intervals.size()-1)+",+)");
        return res;
    }

    /**
     * Retrieves people in the given interval.
     *
     * The age of the person is computed by subtracting
     * the birth year from current year.
     *
     * @param range age interval label
     * @return collection of SSN of person in the age interval
     */
    public Collection<String> getInInterval(String range) {
    	Collection<String> res = new LinkedList<>();
    	int start =0;
    	int end = 0;
    	int c = 0;
    	int k = 0;
    	int t = 0;
    	for (int i=0;i<range.length();i++) {
    		if(range.substring(i,i+1).equals("[")) {
    			c = i;
    		}else if(range.substring(i,i+1).equals(",")) {
    			k = i;
    		}else if(range.substring(i,i+1).equals(")")) {
    			t = i;
    		}
    	}
    	start = Integer.parseInt(range.substring(c+1,k));
    	if (!range.substring(k+1,t).equals("+")) {
    		end = Integer.parseInt(range.substring(k+1,t));
    	}else {
    		end = Integer.MAX_VALUE;
    	}
    	
    	
    	for (Person p :people.values()) {
    		int age = getAge(p.getSsn());
    		if(age >=start && age <end) {
    			res.add(p.getSsn());
    		}
    	}
        return res;
    }

    // R2
    /**
     * Define a vaccination hub
     *
     * @param name name of the hub
     * @throws VaccineException in case of duplicate name
     */
    public void defineHub(String name) throws VaccineException {
    	if (hubs.containsKey(name)) {
    		throw new VaccineException();
    	}
    	hubs.put(name,new Hub(name));
    }

    /**
     * Retrieves hub names
     *
     * @return hub names
     */
    public Collection<String> getHubs() {
        return hubs.keySet();
    }

    /**
     * Define the staffing of a hub in terms of
     * doctors, nurses and other personnel.
     *
     * @param name name of the hub
     * @param countDoctors number of doctors
     * @param countNurse number of nurses
     * @param o number of other personnel
     * @throws VaccineException in case of undefined hub, or any number of personnel not greater than 0.
     */
    public void setStaff(String name, int countDoctors, int countNurse, int o) throws VaccineException {
    	if (countDoctors<=0 ||   countNurse <= 0 ||  o <=0 || !hubs.containsKey(name)) {
    		throw new VaccineException();
    	}
    	Hub h = hubs.get(name);
    	h.setDoc(countDoctors);
    	h.setNurse(countNurse);
    	h.setOther(o);
    }

    /**
     * Estimates the hourly vaccination capacity of a hub
     *
     * The capacity is computed as the minimum among
     * 10*number_doctor, 12*number_nurses, 20*number_other
     *
     * @param hubName name of the hub
     * @return hourly vaccination capacity
     * @throws VaccineException in case of undefined or hub without staff
     */
    public int estimateHourlyCapacity(String hubName) throws VaccineException {
    	if(!hubs.containsKey(hubName)|| hubs.get(hubName).getDoc()==0) {
    		throw new VaccineException();
    	}
        return hubs.get(hubName).getCapacity();
    }

    // R3
    /**
     * Load people information stored in CSV format.
     *
     * The header must start with {@code "SSN,LAST,FIRST"}.
     * All lines must have at least three elements.
     *
     * In case of error in a person line the line is skipped.
     *
     * @param people {@code Reader} for the CSV content
     * @return number of correctly added people
     * @throws IOException in case of IO error
     * @throws VaccineException in case of error in the header
     */
    public long loadPeople(Reader people) throws IOException, VaccineException {
    	List<String> line = new ArrayList<>();
        BufferedReader buffReader = null;
        long c =0;
		try {
			buffReader= new BufferedReader(people);
			//checking if the line is not empty
			while (line.add(buffReader.readLine()) && line.get(line.size()-1)!=null && !line.get(line.size()-1).equals("")) {
				//System.out.println(line.get(line.size()-1));
	
				String[] data = line.get(line.size()-1).split(",");
				if (line.size()==1) {
					if (data.length!=4 ||!data[0].equals("SSN") || !data[1].equals("LAST") || !data[2].equals("FIRST") || !data[3].equals("YEAR")) {
						if(lst!=null) lst.accept(line.size(),line.get(line.size()-1));
						throw new VaccineException();
					}
				}
				else {
					if (data.length!=4 ||data[0].equals("") || data[1].equals("") || data[2].equals("") || data[3].equals("") || this.people.containsKey(data[0]) ) {
						if(lst!=null) lst.accept(line.size(),line.get(line.size()-1));
					}else {
						addPerson(data[2],data[1],data[0],Integer.parseInt(data[3]));
						c++;
					}
				}
				
			}
		}catch (IOException e) {
			throw new IOException();
		}finally {
			people.close();
			buffReader.close();
		}
        return c;
    }

    // R4
    /**
     * Define the amount of working hours for the days of the week.
     *
     * Exactly 7 elements are expected, where the first one correspond to Monday.
     *
     * @param hours workings hours for the 7 days.
     * @throws VaccineException if there are not exactly 7 elements or if the sum of all hours is less than 0 ore greater than 24*7.
     */
    public void setHours(int... hours) throws VaccineException {
    	if(hours.length!=7) {
    		throw new VaccineException();
    	}
    	for (int i:hours) {
    		if(i>12) {
    			throw new VaccineException();
    		}
    		this.hours.add(i);
    	}
    }

    /**
     * Returns the list of standard time slots for all the days of the week.
     *
     * Time slots start at 9:00 and occur every 15 minutes (4 per hour) and
     * they cover the number of working hours defined through method {@link #setHours}.
     * <p>
     * Times are formatted as {@code "09:00"} with both minuts and hours on two
     * digits filled with leading 0.
     * <p>
     * Returns a list with 7 elements, each with the time slots of the corresponding day of the week.
     *
     * @return the list hours for each day of the week
     */
    public List<List<String>> getHours() {
    	List<List<String>> res = new LinkedList<>();
    	for(int i=0;i<7;i++) {
    		List<String> times = new LinkedList<>();
    		for (int j=0;j<hours.get(i);j++) {
    			String st = String.format("%02d:%02d",9+j,00);
    			times.add(st);
    			st = String.format("%02d:%02d",9+j,15);
    			times.add(st);
    			st = String.format("%02d:%02d",9+j,30);
    			times.add(st);
    			st = String.format("%02d:%02d",9+j,45);
    			times.add(st);
    		}
    		res.add(times);
    	}
    	
        return res;
    }

    /**
     * Compute the available vaccination slots for a given hub on a given day of the week
     * <p>
     * The availability is computed as the number of working hours of that day
     * multiplied by the hourly capacity (see {@link #estimateCapacity} of the hub.
     *
     * @return
     */
    public int getDailyAvailable(String hubName, int d) {
    	Hub h = hubs.get(hubName);
        return h.getCapacity()*hours.get(d);
    }

    /**
     * Compute the available vaccination slots for each hub and for each day of the week
     * <p>
     * The method returns a map that associates the hub names (keys) to the lists
     * of number of available hours for the 7 days.
     * <p>
     * The availability is computed as the number of working hours of that day
     * multiplied by the capacity (see {@link #estimateCapacity} of the hub.
     *
     * @return
     */
    public Map<String, List<Integer>> getAvailable() {
    	Map<String,List<Integer>> res = hubs.values().stream()
    									.collect(Collectors.toMap(Hub::getName,h -> getWeeklyCap(h)));
        return res;
    }

    
    public List<Integer> getWeeklyCap(Hub h){
    	List<Integer> res = new LinkedList<>();
    	for (int i=0;i<7;i++) {
    		res.add(getDailyAvailable(h.getName(),i));
    	}
    	return res;
    }
    
    /**
     * Computes the general allocation plan a hub on a given day.
     * Starting with the oldest age intervals 40%
     * of available places are allocated
     * to persons in that interval before moving the the next
     * interval and considering the remaining places.
     * <p>
     * The returned value is the list of SSNs (codice fiscale) of the
     * persons allocated to that day
     * <p>
     * <b>N.B.</b> no particular order of allocation is guaranteed
     *
     * @param hubName name of the hub
     * @param d day of week index (0 = Monday)
     * @return the list of daily allocations
     */
    public List<String> allocate(String hubName, int d) {
    	List<String> res = new LinkedList<>();
    	Hub h = hubs.get(hubName);
    	int cap = getDailyAvailable(hubName,d);
		List<String> slots = getHours().get(d);
		int totAllocated = 0;
		List<String> intervals = (List<String>) getAgeIntervals();
		for (int i=intervals.size()-1;i>=0;i--) {
			int c =0, trun = truncate((cap - totAllocated) *0.4);
			String interval =  intervals.get(i);
			for(String ssn:getInInterval(interval)) {
				Person p = people.get(ssn);
				if ( p.getSt().equals(STATUS.NOT_ALLOCATED) &&c<trun) {
					p.setSt(STATUS.ALLOCATED);
					p.setHub(h);
					c++;
					totAllocated++;
					res.add(p.getSsn());
				}
			}
		}
	
		if(totAllocated<cap) {
			for (int i=intervals.size()-1;i>=0;i--) {
				String interval =  intervals.get(i);
				for(String ssn:getInInterval(interval)) {
					Person p = people.get(ssn);
					if (p.getSt().equals(STATUS.NOT_ALLOCATED) && totAllocated<cap) {
						p.setSt(STATUS.ALLOCATED);
						p.setHub(h);
						totAllocated++;
						res.add(p.getSsn());
					}
				}
			}
		}
		
		return res;
    }
    
    
    	private int truncate(Double d) {
    		int intValue = d.intValue();
    		Double floating = d-intValue;
    		int perc = ((Double)(floating*10)).intValue();
    		return  (intValue + perc/10);
    	}
   


    /**
     * Removes all people from allocation lists and
     * clears their allocation status
     */
    public void clearAllocation() {
    	for (Person p:people.values()) {
    		p.setSt(STATUS.NOT_ALLOCATED);
    	}
    }

    /**
     * Computes the general allocation plan for the week.
     * For every day, starting with the oldest age intervals
     * 40% available places are allocated
     * to persons in that interval before moving the the next
     * interval and considering the remaining places.
     * <p>
     * The returned value is a list with 7 elements, one
     * for every day of the week, each element is a map that
     * links the name of each hub to the list of SSNs (codice fiscale)
     * of the persons allocated to that day in that hub
     * <p>
     * <b>N.B.</b> no particular order of allocation is guaranteed
     * but the same invocation (after {@link #clearAllocation}) must return the same
     * allocation.
     *
     * @return the list of daily allocations
     */
    public List<Map<String, List<String>>> weekAllocate() {
    	List<Map<String,List<String>>> res = new LinkedList<>();
    	for (int i =0;i<7;i++) {
    		Map<String,List<String>> m = new LinkedHashMap<>();
    		for (String name:hubs.keySet()) {
    			m.put(name,allocate(name,i));
    		}
    		res.add(m);
    	}
        return res;
    }

    // R5
    /**
     * Returns the proportion of allocated people
     * w.r.t. the total number of persons added
     * in the system
     *
     * @return proportion of allocated people
     */
    public double propAllocated() {
    	int c  = 0;
    	List<Map<String, List<String>>> tmp = weekAllocate();
    	for (int i=0;i<7;i++) {
    		for (List<String> list : tmp.get(i).values()) {
    			c+=list.size();
    		}
    	}
        return 1.0*c/people.size();
    }

    /**
     * Returns the proportion of allocated people
     * w.r.t. the total number of persons added
     * in the system, divided by age interval.
     * <p>
     * The map associates the age interval label
     * to the proportion of allocates people in that interval
     *
     * @return proportion of allocated people by age interval
     */
    public Map<String, Double> propAllocatedAge() {
    	Map<String,Double> res = new LinkedHashMap<>();
    	List<String> intervals = (List<String>) getAgeIntervals();
    	int c=0;
    	int t=people.size();
		for (int i=intervals.size()-1;i>=0;i--) {
			c=0;
			String interval =  intervals.get(i);
			for(String st:getInInterval(interval)) {
				Person p = people.get(st);
				if (p.getSt().equals(STATUS.ALLOCATED) ) {
					c++;
				}
			}
			res.put(interval,1.0*c/t);
		}
        return res;
    }

    /**
     * Retrieves the distribution of allocated persons
     * among the different age intervals.
     * <p>
     * For each age intervals the map reports the
     * proportion of allocated persons in the corresponding
     * interval w.r.t the total number of allocated persons
     *
     * @return
     */
    public Map<String, Double> distributionAllocated() {
    	Map<String,Double> res = new LinkedHashMap<>();
    	List<String> intervals = (List<String>) getAgeIntervals();
    	int c=0;
    	int t=0;
    	for(Person p:people.values()) if(p.getSt().equals(STATUS.ALLOCATED)) t++;
		for (int i=intervals.size()-1;i>=0;i--) {
			String interval =  intervals.get(i);
			c=0;
			for(String st:getInInterval(interval)) {
				Person p = people.get(st);
				if (p.getSt().equals(STATUS.ALLOCATED) ) {
					c++;
				}	
			}
			res.put(interval,1.0*c/t);
		}
        return res;
    }

    // R6
    /**
     * Defines a listener for the file loading method.
     * The {@ accept()} method of the listener is called
     * passing the line number and the offending line.
     * <p>
     * Lines start at 1 with the header line.
     *
     * @param lst the listener for load errors
     */
    public void setLoadListener(BiConsumer<Integer, String> lst) {
    	this.lst = lst;
    }
}

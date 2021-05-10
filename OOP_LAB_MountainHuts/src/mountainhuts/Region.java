package mountainhuts;

import static java.util.stream.Collectors.toList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;

/**
 * Class {@code Region} represents the main facade
 * class for the mountains hut system.
 * 
 * It allows defining and retrieving information about
 * municipalities and mountain huts.
 *
 */
public class Region {
	
	String name;
	LinkedList<String> altitudeRanges; 
	Set<Municipality> municipalities;
	LinkedList<MountainHut> mountainHuts;
	
	/**
	 * Create a region with the given name.
	 * 
	 * @param name
	 *            the name of the region
	 */
	public Region(String name) {
		this.name = name;
		altitudeRanges = new LinkedList<String>();
		municipalities = new LinkedHashSet<Municipality>();
		mountainHuts = new LinkedList<MountainHut>();
	}

	/**
	 * Return the name of the region.
	 * 
	 * @return the name of the region
	 */
	public String getName() {
		return  name;
	}

	/**
	 * Create the ranges given their textual representation in the format
	 * "[minValue]-[maxValue]".
	 * 
	 * @param ranges
	 *            an array of textual ranges
	 */
	public void setAltitudeRanges(String... ranges) {
		for (String st:ranges) {
			altitudeRanges.add(st);
		}
	}

	/**
	 * Return the textual representation in the format "[minValue]-[maxValue]" of
	 * the range including the given altitude or return the default range "0-INF".
	 * 
	 * @param altitude
	 *            the geographical altitude
	 * @return a string representing the range
	 */
	public String getAltitudeRange(Integer altitude) {
		for(String st : altitudeRanges) {
			//c is the position of - in the string altitude
			int i,f,c=0;
			for(c=0;!st.substring(c,c+1).equals("-");c++);
			i = Integer.parseInt(st.substring(0, c));
			f = Integer.parseInt(st.substring(c+1,st.length()));
			if (altitude > i && altitude <=f) {
				return st;
			}
			// 0 is included in the interval containing 0
			if (i==0 && altitude==0) {
				return st;
			}
		}
		return "0-INF";
	}

	/**
	 * Create a new municipality if it is not already available or find it.
	 * Duplicates must be detected by comparing the municipality names.
	 * 
	 * @param name
	 *            the municipality name
	 * @param province
	 *            the municipality province
	 * @param altitude
	 *            the municipality altitude
	 * @return the municipality
	 */
	public Municipality createOrGetMunicipality(String name, String province, Integer altitude) {
		Municipality newm;
		for (Municipality m : municipalities) {
			if(name.equals(m.getName())) {
				return m;
			}
		}
		newm = new Municipality(name, province, altitude);
		municipalities.add(newm);
		return newm;
	}

	/**
	 * Return all the municipalities available.
	 * 
	 * @return a collection of municipalities
	 */
	public Collection<Municipality> getMunicipalities() {
		return municipalities;
	}

	/**
	 * Create a new mountain hut if it is not already available or find it.
	 * Duplicates must be detected by comparing the mountain hut names.
	 *
	 * @param name
	 *            the mountain hut name
	 * @param category
	 *            the mountain hut category
	 * @param bedsNumber
	 *            the number of beds in the mountain hut
	 * @param municipality
	 *            the municipality in which the mountain hut is located
	 * @return the mountain hut
	 */
	public MountainHut createOrGetMountainHut(String name, String category, Integer bedsNumber,
			Municipality municipality) {
		MountainHut newmh;
		for (MountainHut mh : mountainHuts) {
			if(name.equals(mh.getName())) {
				return mh;
			}
		}
		newmh = new MountainHut(name,category,bedsNumber,municipality);
		mountainHuts.add(newmh);
		return newmh;
	}

	/**
	 * Create a new mountain hut if it is not already available or find it.
	 * Duplicates must be detected by comparing the mountain hut names.
	 * 
	 * @param name
	 *            the mountain hut name
	 * @param altitude
	 *            the mountain hut altitude
	 * @param category
	 *            the mountain hut category
	 * @param bedsNumber
	 *            the number of beds in the mountain hut
	 * @param municipality
	 *            the municipality in which the mountain hut is located
	 * @return a mountain hut
	 */
	public MountainHut createOrGetMountainHut(String name, Integer altitude, String category, Integer bedsNumber,
			Municipality municipality) {
		MountainHut newmh;
		for (MountainHut mh : mountainHuts) {
			if(name.equals(mh.getName())) {
				return mh;
			}
		}
		newmh = new MountainHut(name,altitude,category,bedsNumber,municipality);
		mountainHuts.add(newmh);
		return newmh;
	}

	/**
	 * Return all the mountain huts available.
	 * 
	 * @return a collection of mountain huts
	 */
	public Collection<MountainHut> getMountainHuts() {
		return  mountainHuts;
	}

	/**
	 * Factory methods that creates a new region by loadomg its data from a file.
	 * 
	 * The file must be a CSV file and it must contain the following fields:
	 * <ul>
	 * <li>{@code "Province"},
	 * <li>{@code "Municipality"},
	 * <li>{@code "MunicipalityAltitude"},
	 * <li>{@code "Name"},
	 * <li>{@code "Altitude"},
	 * <li>{@code "Category"},
	 * <li>{@code "BedsNumber"}
	 * </ul>
	 * 
	 * The fields are separated by a semicolon (';'). The field {@code "Altitude"}
	 * may be empty.
	 * 
	 * @param name
	 *            the name of the region
	 * @param file
	 *            the path of the file
	 */
	public static Region fromFile(String name, String file) {
		List<String> data = readData(file);
		Region r = new Region(name);
		data.remove(0);
		for (String st:data) {
			String[] columns = st.split(";");
			Municipality m = r.createOrGetMunicipality(columns[1], columns[0], Integer.parseInt(columns[2]));
			if (columns[4].length()>0) {
				//with altitude
				r.createOrGetMountainHut(columns[3],Integer.parseInt(columns[4]), columns[5], Integer.parseInt(columns[6]), m);
			}else {
				//without altitude
				r.createOrGetMountainHut(columns[3], columns[5], Integer.parseInt(columns[6]), m);
			}

		}
		return r;
	}


	/**
	 * Internal class that can be used to read the lines of
	 * a text file into a list of strings.
	 * 
	 * When reading a CSV file remember that the first line
	 * contains the headers, while the real data is contained
	 * in the following lines.
	 * 
	 * @param file the file name
	 * @return a list containing the lines of the file
	 */
	@SuppressWarnings("unused")
	private static List<String> readData(String file) {
		try (BufferedReader in = new BufferedReader(new FileReader(file))) {
			return in.lines().collect(toList());
		} catch (IOException e) {
			System.err.println(e.getMessage());
			return null;
		}
	}

	/**
	 * Count the number of municipalities with at least a mountain hut per each
	 * province.
	 * 
	 * @return a map with the province as key and the number of municipalities as
	 *         value
	 */
	public Map<String, Long> countMunicipalitiesPerProvince() {
		Map<String,Long> res = new LinkedHashMap<String,Long>();
		for(Municipality m:municipalities) {
			if (res.containsKey(m.getProvince())) {
				Long t = res.get(m.getProvince());
				res.put(m.getProvince(), t+1);
			}else {
				res.put(m.getProvince(),(long) 1);
			}
		}
		return res;
	}

	/**
	 * Count the number of mountain huts per each municipality within each province.
	 * 
	 * @return a map with the province as key and, as value, a map with the
	 *         municipality as key and the number of mountain huts as value
	 */

	public Map<String, Map<String, Long>> countMountainHutsPerMunicipalityPerProvince() {
		Map<String,Map<String,Long>> res = new LinkedHashMap<String,Map<String,Long>>();
		for(MountainHut mh:mountainHuts) {
			String mun = mh.getMunicipality().getName();
			String prov = mh.getMunicipality().getProvince();
			if (res.containsKey(prov)){
				if (res.get(prov).containsKey(mun)) {
					Long t = res.get(prov).get(mun);
					res.get(prov).put(mun, t+1);
				}else {
					res.get(prov).put(mun,(long) 1);
				}
			}else {
				Map<String,Long> t = new LinkedHashMap<String,Long>();
				t.put(mun, (long) 1);
				res.put(prov,t);
			}
		}
		return res;
	}

	/**
	 * Count the number of mountain huts per altitude range. If the altitude of the
	 * mountain hut is not available, use the altitude of its municipality.
	 * 
	 * @return a map with the altitude range as key and the number of mountain huts
	 *         as value
	 */
	public Map<String, Long> countMountainHutsPerAltitudeRange() {
		String t;
		Integer alt;
		Map<String,Long> res = new TreeMap<String,Long>();
		for(MountainHut mh: mountainHuts) {
			if (mh.getAltitude().isPresent()){
				alt = mh.getAltitude().get();
			}else {
				alt = mh.getMunicipality().getAltitude();
			}
			t = this.getAltitudeRange(alt);
			if (res.containsKey(t)) {
				Long tmp = res.get(t);
				res.put(t, tmp+1);
			}else {
				res.put(t, (long) 1);
			}
			
			/*
			if (t.equals("0-1000")) {
				if (mh.getAltitude().isPresent()){
					System.out.println("present altitude");
				}
				System.out.println("altitude is: "+alt);
				System.out.println("Altitude Range: " + t);
				System.out.println(res.get(t)+"\n");
			}
			*/
			
		}
		return res;
	}

	/**
	 * Compute the total number of beds available in the mountain huts per each
	 * province.
	 * 
	 * @return a map with the province as key and the total number of beds as value
	 */
	public Map<String, Integer> totalBedsNumberPerProvince() {
		Map<String, Integer> res = new LinkedHashMap<>();
		for (MountainHut mh: mountainHuts) {
			String prov = mh.getMunicipality().getProvince();
			if (res.containsKey(prov)) {
				int nb = res.get(prov);
				res.put(prov, nb+mh.getBedsNumber());
			}else {
				res.put(prov, mh.getBedsNumber());
			}
		}
		return res;
	}

	/**
	 * Compute the maximum number of beds available in a single mountain hut per
	 * altitude range. If the altitude of the mountain hut is not available, use the
	 * altitude of its municipality.
	 * 
	 * @return a map with the altitude range as key and the maximum number of beds
	 *         as value
	 */
	public Map<String, Optional<Integer>> maximumBedsNumberPerAltitudeRange() {
		Map<String,Optional<Integer>> res = new TreeMap<String,Optional<Integer>>();
		for(MountainHut mh: mountainHuts) {
			int alt;
			if (mh.getAltitude().isPresent()){
				alt = mh.getAltitude().get();
			}else {
				alt = mh.getMunicipality().getAltitude();
			}
			String t = this.getAltitudeRange(alt);
			if (res.containsKey(t)) {
				if(mh.getBedsNumber() > res.get(t).get()) {
					res.put(t,Optional.ofNullable(mh.getBedsNumber()));
				}
			}else {
				res.put(t, Optional.ofNullable(mh.getBedsNumber()));
			}
		}
		for(String st:altitudeRanges) {
			if(!res.containsKey(st)) {
				res.put(st,Optional.ofNullable(null));
			}
		}
		return res;
	}

	/**
	 * Compute the municipality names per number of mountain huts in a municipality.
	 * The lists of municipality names must be in alphabetical order.
	 * 
	 * @return a map with the number of mountain huts in a municipality as key and a
	 *         list of municipality names as value
	 */
	public Map<Long, List<String>> municipalityNamesPerCountOfMountainHuts() {
		Map<Long,List<String>> res = new LinkedHashMap<Long,List<String>>();
		
		Map<String,Long> numberOfMountainHutsPerMunicipality = new LinkedHashMap<String,Long>();
		for (MountainHut mh: mountainHuts) {
			String mun = mh.getMunicipality().getName();
			if(numberOfMountainHutsPerMunicipality.containsKey(mun)) {
				long num = numberOfMountainHutsPerMunicipality.get(mun);
				numberOfMountainHutsPerMunicipality.put(mun, num+1);
			}else {
				numberOfMountainHutsPerMunicipality.put(mun,(long) 1);
			}
		}
		
		Set<Long> numbers = new LinkedHashSet<Long>();
		for (Long l:numberOfMountainHutsPerMunicipality.values()) {
			numbers.add(l);
		}
		
		for(Long k:numbers) {
			for (Map.Entry<String,Long> e:numberOfMountainHutsPerMunicipality.entrySet()) {
				if (k==e.getValue()) {
					if(res.containsKey(k)) {
						List<String> values = res.get(k);
						values.add(e.getKey());
						values.sort(Comparator.naturalOrder());
						res.put(k, values);
					}else {
						List<String> values = new LinkedList<String>();
						values.add(e.getKey());
						res.put(k, values);
					}
				}
			}
		}
		
		return res;
	}

}

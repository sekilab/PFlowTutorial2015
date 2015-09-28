package utokyo.csis.pflow.tutorial2015.exec;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;

/**
 * class for converting PID based PFLOW data into compressed CSV data for PostgreSQL
 * @author H.Kanasugi@EDITORIA. UTokyo.
 * @since 2015/09/27
 * @version 0.0.0.1
 */
public class PFlowData2Pg {
	/**
	 * entry point
	 * @param args 0:input directory path, 1:output file path
	 */
	public static void main(String[] args) throws IOException {
		// input parameters ///////////////////////////////
		File   inputDir   = new File(args[0]);
		File   outputFile = new File(args[1]);
		
		// load data folder //////////////////////////////
		File[] files = inputDir.listFiles(new FileFilter() { 
			public boolean accept(File f) { return f.getName().endsWith(".csv"); } 
		});
		
		// open destination file //////////////////////////
		try( BufferedWriter bw  = new BufferedWriter(new FileWriter(outputFile)) ) {
			// list each csv file =========================
			for(File f:files) {
				System.out.println("\t" + f.getName());
				Map<Integer,List<PFlowData>> res = load(f);
				
				for(List<PFlowData> val:res.values()) {
					for(String output:toString(val)) {
						bw.write(output); 
						bw.newLine();
					}
				}
			}
		}
		catch(IOException exp) { exp.printStackTrace(); }
	}
	
	/**
	 * create output strings
	 * @param list PFLOW data list for conversion
	 * @return output CSV strings
	 */
	private static List<String> toString(List<PFlowData> list) {
		List<String> output = new ArrayList<String>();
		PFlowData    data0 = list.get(0);
		int          len   = list.size();
		// case: stay /////////////////////////////////////
		// merge stay period in one record
		if( data0.isStay() ) {
			PFlowData data1 = list.get(list.size()-1);
			output.add(toCsvString(data0,data1));
		}
		// case: move /////////////////////////////////////
		// export all time stamp in minute
		else {
			for(int i=1;i<len;i++) {
				PFlowData data1 = list.get(i);
				output.add(toCsvString(data0,data1));
				data0 = data1;
			}
			output.add(toCsvString(data0,data0));
		}
		return output;
	}
	
	/**
	 * create output CSV String from the consecutive PFLOW data
	 * @param data0 first pflow data
	 * @param data1 second pflow data
	 * @return output CSV string
	 */
	private static String toCsvString(PFlowData data0,PFlowData data1) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String[] tokens = {	
			data0.getPID(),
			data0.getTripNo(),
			data0.getSubTripNo(),
			sdf.format(data0.getTimeStamp()),
			sdf.format(data1.getTimeStamp()),
			String.format("%.06f",data0.getLongitude()),
			String.format("%.06f",data0.getLatitude()),
			String.format("%02d",data0.getSex()),
			String.format("%02d",data0.getAge()),
			data0.getAddress(),
			String.format("%02d",data0.getWork()),
			String.format("%02d",data0.getPurpose()),
			String.format("%d",  data0.getExfactor1()),
			String.format("%d",  data0.getExfactor2()),
			String.format("%02d",data0.getTransport())
		};
		return StringUtils.join(tokens, ",");
	}
	
	/**
	 * load PID base trip data from the indicated file
	 * @param infile PID based trip file
	 * @return trip map (key=trip ID, value=PFLOW data within the trip)
	 * @throws IOException exception on file access
	 */
	public static Map<Integer,List<PFlowData>> load(File infile) throws IOException { 
		Map<Integer,List<PFlowData>> tripMap = new TreeMap<Integer,List<PFlowData>>();
		
		try(BufferedReader br = new BufferedReader(new FileReader(infile))) {
			String line = null;
			while ( (line=br.readLine())!=null ) {
				// parse each line ========================
				PFlowData data = PFlowData.parseCsv(line);
				if( data == null ) { continue; }
				
				int tripid = Integer.parseInt(data.getTripNo());
				List<PFlowData> list = tripMap.get(tripid);
				if( list == null ) {
					list = new ArrayList<PFlowData>();
					tripMap.put(tripid,list); 
				}		
				tripMap.get(tripid).add(data);
			}
		}
		catch(IOException exp) { exp.printStackTrace(); }
		
		return tripMap;
	}
}

package utokyo.csis.pflow.tutorial2015.exec;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.text.StrTokenizer;

/**
 * Class for PFLOW data
 * @author KNSG@UTokyo
 * @version 2009/07/20
 */
public class PFlowData {	
	/* ==============================================================
	 * static fields 
	 * ============================================================== */
	/** stay	*/	public static final int TRANSPORT_STAY = 97;
	/** unknown	*/	public static final int PURPOSE_UNKNOWN= 99;

	
	/* ==============================================================
	 * static methods 
	 * ============================================================== */
	/**
	 * parse CSV line 
	 * @param line CSV string
	 * @return PFLOW data instance, return null if failed
	 */
	public static PFlowData parseCsv(String line) {
		// set values
		PFlowData res = new PFlowData();
		try {
			// parse csv; csv line is divided into some tokens
			String tokens[] = StrTokenizer.getCSVInstance(line).getTokenArray(); 
			res._pid = tokens[0].trim();
			res._tno = tokens[1].trim();
			res._sno = tokens[2].trim();
			res._timestamp = parseDate(tokens[3].trim());
			res._longitude = Double.parseDouble(tokens[4].trim());
			res._latitude  = Double.parseDouble(tokens[5].trim());
			res._sex       = NumberUtils.toInt( tokens[6].trim() );
			res._age       = NumberUtils.toInt( tokens[7].trim() );
			res._padd      = tokens[8].trim();
			res._work      = NumberUtils.toInt( tokens[9].trim() );
			res._purpose   = NumberUtils.toInt( tokens[10].trim() );
			res._magfac1   = NumberUtils.toInt( tokens[11].trim() );
			res._magfac2   = NumberUtils.toInt( tokens[12].trim() );
			res._transport = NumberUtils.toInt( tokens[13].trim() );
		}
		catch(Exception exp){ exp.printStackTrace(); res = null; }
		return res;
	}
	
	/**
	 * parse time-stamp string
	 * @param datetime time-stamp string
	 * @return date instance, return null if failed
	 */
	public static Date parseDate(String datetime) {
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		
		Date date = null;
		// case yyyy/MM/dd HH:mm:ss
		try { date = sdf1.parse(datetime); }
		catch(ParseException exp) { 
			// case yyyy-MM-dd HH:mm:ss
			try { date = sdf2.parse(datetime); }
			catch(ParseException exp2) { 
				// case yyyy-MM-dd HH:mm
				try { date = sdf3.parse(datetime); }
				catch(ParseException exp3) { date = null; } 
			} 
		}
		return date;
	}
	
	
	/* ==============================================================
	 * instance variables
	 * ============================================================== */
	/** person ID	*/	private String  _pid;		// Person ID
	/** Trip No		*/	private String	_tno;		// Trip ID
	/** Sub-trip No	*/	private String	_sno;		// Sub-trip ID
	/** time		*/	private Date    _timestamp;	// time-stamp
	/** longitude	*/	private double  _longitude;
	/** latitude	*/	private double  _latitude;
	/** sex code	*/	private int		_sex;		// sex/gender
	/** age code	*/	private int		_age;		// age
	/** address code*/	private String	_padd;		// person address
	/** work code	*/	private int		_work;		// work/job
	/** purpose code*/	private int		_purpose;	// trip purpose
	/** expansion 1	*/	private int		_magfac1;	// expansion factor1
	/** expansion 2	*/	private int		_magfac2;	// expansion factor2
	/** transport	*/	private int		_transport;	// transportation
	
	
	/* ==============================================================
	 * constructor 
	 * ============================================================== */
	/**
	 * create PFLOW data instance
	 */
	private PFlowData() {
		super();
		_pid = "";
		_tno = "";
		_sno = "";
		_timestamp = null;
		_longitude = 0;
		_latitude  = 0;
		_sex       = 9;
		_age       = 99;
		_padd      = "";
		_work      = 99;
		_purpose   = 99;
		_magfac1   = 0;
		_magfac2   = 0;
		_transport = 99;
	}
	
	
	/* ==============================================================
	 * instance methods  
	 * ============================================================== */
	/**
	 * get Person ID(PID)
	 * @return PID
	 */
	public String getPID() {
		return _pid;
	}

	/**
	 * get trip No
	 * @return trip No
	 */
	public String getTripNo() {
		return _tno;
	}
	
	/**
	 * get sub-trip No
	 * @return sub-trip No
	 */
	public String getSubTripNo() {
		return _sno;
	}

	/**
	 * get gender code
	 * @return gender code
	 */
	public int getSex() {
		return _sex;
	}
	
	/**
	 * get age code
	 * @return age code
	 */
	public int getAge() {
		return _age;
	}
	
	/**
	 * get address code
	 * @return address code
	 */
	public String getAddress() {
		return _padd;
	}
	
	/**
	 * get work code
	 * @return work code
	 */
	public int getWork() {
		return _work;
	}
	
	/**
	 * get trip purpose code
	 * @return trip purpose code
	 */
	public int getPurpose() {
		return _purpose;
	}

	/**
	 * get magnification factor No1
	 * @return magnification factor No1
	 */
	public int getExfactor1() {
		return _magfac1;
	}

	/**
	 * get magnification factor No2
	 * @return magnification factor No2
	 */
	public int getExfactor2() {
		return _magfac2;
	}
		
	/**
	 * get time stamp
	 * @return time stamp
	 */
	public Date getTimeStamp() {
		return _timestamp;
	}

	/**
	 * get longitude
	 * @return longitude
	 */
	public double getLongitude() {
		return _longitude;
	}
	
	/**
	 * get latitude
	 * @return latitude
	 */
	public double getLatitude() { 
		return _latitude;
	}
	
	/**
	 * get transport code
	 * @return transport code
	 */
	public int getTransport() {
		return _transport;
	}
	
	/**
	 * check if stay or not
	 * @return result
	 */
	public boolean isStay() {
		return getTransport() == TRANSPORT_STAY;
	}
	
	/**
	 * get String in CSV format
	 * @return CSV string
	 */
	public String getCsvString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String[] tokens = {	
			_pid,
			_tno,
			_sno,
			sdf.format(_timestamp),
			String.format("%.08f",_longitude),
			String.format("%.08f",_latitude),
			String.format("%02d", _sex),
			String.format("%02d", _age),
			_padd,
			String.format("%02d",_work),
			String.format("%02d",_purpose),
			String.format("%d",  _magfac1),
			String.format("%d",  _magfac2),
			String.format("%02d",_transport)
		};
		return StringUtils.join(tokens, ",");
	}
}

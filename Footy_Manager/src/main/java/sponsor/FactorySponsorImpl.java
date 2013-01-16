package sponsor;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import readers.TextToIterableImpl;


public class FactorySponsorImpl implements FactorySponsor {

	private static final String dir_fich = "src/main/resources/Sponsors";	
	
	private static final Iterable<String> itNames = new TextToIterableImpl(dir_fich);
	private static Iterator<String> it = itNames.iterator();

	private static final Integer POS_NAME = 0;
	private static final Integer POS_ONE_YEAR = 1;
	private static final Integer POS_TWO_YEAR = 2;
	
	@Override
	public Sponsor getSponsor() {
		String line = getNewLine();
		Sponsor res = null;

		if(line != null)
		{
			List<String> l= new LinkedList<String>();
			
		    StringTokenizer st=new StringTokenizer(line,";");
		    while(st.hasMoreTokens()){
		    	l.add(st.nextToken());
		    }
		
		    String name = l.get(POS_NAME);
		    Double oneCash = Double.parseDouble(l.get(POS_ONE_YEAR));
		    Double twoCash = Double.parseDouble(l.get(POS_TWO_YEAR));
		    
		    res = new SponsorImpl(name, oneCash, twoCash);
		}
		
		return res;
	}

	private String getNewLine() {
		String res = null;
		
		if(it.hasNext())
		{
			res = it.next();
		}
		
		return res;
	}
}

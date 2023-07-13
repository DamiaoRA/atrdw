package atrdw;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.Properties;

import atrdw.datasource.AspectDAOIF;
import atrdw.datasource.ETL;
import atrdw.datasource.InputMessageIF;
import atrdw.model.Message;
import atrdw.util.StringUtils;

/**
 * O que é necessário para o ETL:
 * - entrada de dados
 * - quais são os aspectos
 * - saída de dados
 *
 */
public class Main_Input {
	private InputMessageIF inputPoi;
	private AspectDAOIF aspectDao;
	private ETL etl;
	private String separator;

	public Main_Input() throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException, SQLException {
		etl = new ETL();
		readProperties();
	}
	public Object getInstance(String className) throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		Class<?> o = Class.forName(className);
	    Constructor<?> oCon = o.getConstructor();
        Object p = oCon.newInstance();
        return p;
	}
	public void readProperties() throws IOException, ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException {
	    InputStream is =
	    		Main_Input.class.getResourceAsStream("/input.properties");
	    Properties properties = new Properties();
	    properties.load(new InputStreamReader(is, Charset.forName("UTF-8")));
	    String inputClassName = properties.getProperty("input_class");
	    String aspectDaoClassName = properties.getProperty("aspectDao_class");
	    
	    inputPoi = (InputMessageIF)getInstance(inputClassName);
	    aspectDao = (AspectDAOIF)getInstance(aspectDaoClassName);
	    separator = properties.getProperty("separator");
	    if(StringUtils.isEmpty(separator))
	    	separator = " ";

	    etl.setAspectDAO(aspectDao);
	}

	public void start() throws Exception {
		Message m = inputPoi.nextMessage();

		while(m != null) {
			etl.nextMessage(m);
			m = inputPoi.nextMessage();
		}
		etl.finish();
	}


	public static void main(String[] args) throws Exception {
		long t1 = System.currentTimeMillis();
		
		Main_Input main = new Main_Input();
		main.start();

//		ETL etl = new ETL();
//		etl.setInput(null);
//		etl.setAspectDAO(null);
//		etl.setInput(new FoursquareInput());
//		etl.setAspectDAO(new FoursquareAspectDAO());
//		etl.start();

		/*ETL etl = new ETL();
		etl.setInput(new TripBuilderInput());
		etl.setAspectDAO(new TripBuilderAspectDAO());
		etl.start();*/

		long t2 = System.currentTimeMillis();
		
		System.out.println("Time " + (t2-t1)/1000 + " s");
	}
}

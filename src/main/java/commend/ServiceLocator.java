package commend;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ServiceLocator {
	
	private Context initalContext;

	private static ServiceLocator serviceLocator = new ServiceLocator();
	
	//呼叫此靜態方法時new一次ServiceLocator物件
	public static ServiceLocator getInstance() {
		return serviceLocator;
	}
	
	//當新增建構子時自動new一次InitialContext物件
	public ServiceLocator() {
		try {
			this.initalContext = new InitialContext();
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	public DataSource getDataSource() {
		DataSource dataSource = null;
		try {
			//"java:comp/env"->代表當前J2EE應用的環境項目
			Context ctx = (Context) initalContext.lookup("java:comp/env");
			//context中的Resource name
			dataSource = (DataSource) ctx.lookup("jdbc/example");
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return dataSource;
	}
	
	public DataSource getDataSource(String dataSourceName) {
		DataSource datasource = null;
		try {
			Context ctx = (Context) initalContext.lookup("java:comp/env");
			datasource = (DataSource) ctx.lookup(dataSourceName);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return datasource;
	}
}



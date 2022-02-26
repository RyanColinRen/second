package com.ny.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

public class JdbcUtils {
	//JDK自带的日志类, 在java.util.logging包中
	private static Logger log = Logger.getLogger("logger");
	
	//JDBC连接参数
	static String url;//连接协议
	static String username;//数据库账号
	static String password;//数据库密码
	static String driverName;//数据库驱动名
	
	//加载驱动的静态内部类, 在程序运行时就会被读取并执行, 且只会被执行一次
	static {
		//{01}获取mysql.properties配置信息的数据流is。
		//配置文件所在的config文件夹一定要设置成项目资源包, 否则不能读取
		ClassLoader loader = JdbcUtils.class.getClassLoader();//ClassLoader介绍：https://blog.csdn.net/nanhuaibeian/article/details/105773504
		InputStream is = loader.getResourceAsStream("mysql.properties");//getResourceAsStream介绍：https://www.cnblogs.com/macwhirr/p/8116583.html

		//{02}加载数据库驱动类
		try {
			Properties prop = new Properties();
			prop.load( is );//Properties.load介绍：https://www.cnblogs.com/printN/p/6557486.html
			//从数据流中读取信息
			url = prop.getProperty("jdbc.url");
			username = prop.getProperty("jdbc.username");
			password = prop.getProperty("jdbc.password");
			driverName = prop.getProperty("jdbc.driverName");
			log.info("driverName:" + driverName);
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch (IOException e) {
			log.warning("mysql.properties 找不到。");
		}catch (ClassNotFoundException e) {
			log.warning("mysql 驱动无法找到, 请确认你的类地址是否正确, 或是导入库是否成功。");
		}
	}

	//ThreadLocal是除了加锁这种同步方式以外的另一种保证线程安全的方法
	//它的方法是，每个线程访问相同变量名时，都是访问属于线程自己的变量
	//介绍：https://www.cnblogs.com/fsmly/p/11020641.html
	//例子：com.web.test.ThreadLocalTest
	static ThreadLocal<Connection> local = new ThreadLocal<Connection>();
	
	//获取连接
	public static Connection getConnection() throws SQLException {
		Connection conn = local.get();//获取当前线程的conn
		if(conn == null) {//假如当前线程没有conn就在总连接池里查找连接
			conn = DriverManager.getConnection(url, username, password);
			local.set( conn );
			log.info("成功获取连接: " + conn);
		}
		return conn;
	}
	
	//关闭连接
	public static void closeConnection() throws SQLException {
		Connection conn = local.get();
		if(conn != null){
			conn.close();
			local.remove();
		}
	}

//	public static void closeRsAndPsmt(Statement smt, ResultSet rs) throws SQLException {
//		if( rs!=null ){
//			rs.close();
//		}
//		if( smt!=null ){
//			smt.close();
//		}
//	}
//
//	public static void setParas( PreparedStatement psmt, Object[] data ) throws SQLException {
//		int index = 1;
//		for ( Object o : data ) {
//			psmt.setObject( index++, o );
//		}
//	}
	
	/* -------------------- [QueryRunner] -------------------------
	 * QueryRunner使用总结
	 * https://blog.csdn.net/qq_27869123/article/details/81138638
	 */
	
	static QueryRunner QR = new QueryRunner();
	//增删改
	public static int update(String sql, Object...args) throws SQLException {
		Connection conn = getConnection();
		return QR.update( conn, sql, args );
	}
		
	//查询列表
	public static <E> List<E> queryList(String sql, Class<E> clazz, Object...args) throws SQLException {
		Connection conn = getConnection();
		return QR.query( conn, sql, new BeanListHandler<E>(clazz), args);
	}
		
	//查询对象
	public static <E> E queryObject(String sql, Class<E> clazz, Object...args) throws SQLException {
		Connection conn = getConnection();
		return QR.query( conn, sql, new BeanHandler<E>(clazz), args );
	}
		
	//查询只有一列一行的值, 比如COUNT, SUN, MAX, MIN等
	public static Object queryValue(String sql, Object...args) throws SQLException {
		Connection conn = getConnection();
		return QR.query( conn, sql, new ScalarHandler<Object>(), args);
	}
	
	public static void main(String[] args) {
		
	}
}
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
	//JDK�Դ�����־��, ��java.util.logging����
	private static Logger log = Logger.getLogger("logger");
	
	//JDBC���Ӳ���
	static String url;//����Э��
	static String username;//���ݿ��˺�
	static String password;//���ݿ�����
	static String driverName;//���ݿ�������
	
	//���������ľ�̬�ڲ���, �ڳ�������ʱ�ͻᱻ��ȡ��ִ��, ��ֻ�ᱻִ��һ��
	static {
		//{01}��ȡmysql.properties������Ϣ��������is��
		//�����ļ����ڵ�config�ļ���һ��Ҫ���ó���Ŀ��Դ��, �����ܶ�ȡ
		ClassLoader loader = JdbcUtils.class.getClassLoader();//ClassLoader���ܣ�https://blog.csdn.net/nanhuaibeian/article/details/105773504
		InputStream is = loader.getResourceAsStream("mysql.properties");//getResourceAsStream���ܣ�https://www.cnblogs.com/macwhirr/p/8116583.html

		//{02}�������ݿ�������
		try {
			Properties prop = new Properties();
			prop.load( is );//Properties.load���ܣ�https://www.cnblogs.com/printN/p/6557486.html
			//���������ж�ȡ��Ϣ
			url = prop.getProperty("jdbc.url");
			username = prop.getProperty("jdbc.username");
			password = prop.getProperty("jdbc.password");
			driverName = prop.getProperty("jdbc.driverName");
			log.info("driverName:" + driverName);
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch (IOException e) {
			log.warning("mysql.properties �Ҳ�����");
		}catch (ClassNotFoundException e) {
			log.warning("mysql �����޷��ҵ�, ��ȷ��������ַ�Ƿ���ȷ, ���ǵ�����Ƿ�ɹ���");
		}
	}

	//ThreadLocal�ǳ��˼�������ͬ����ʽ�������һ�ֱ�֤�̰߳�ȫ�ķ���
	//���ķ����ǣ�ÿ���̷߳�����ͬ������ʱ�����Ƿ��������߳��Լ��ı���
	//���ܣ�https://www.cnblogs.com/fsmly/p/11020641.html
	//���ӣ�com.web.test.ThreadLocalTest
	static ThreadLocal<Connection> local = new ThreadLocal<Connection>();
	
	//��ȡ����
	public static Connection getConnection() throws SQLException {
		Connection conn = local.get();//��ȡ��ǰ�̵߳�conn
		if(conn == null) {//���統ǰ�߳�û��conn���������ӳ����������
			conn = DriverManager.getConnection(url, username, password);
			local.set( conn );
			log.info("�ɹ���ȡ����: " + conn);
		}
		return conn;
	}
	
	//�ر�����
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
	 * QueryRunnerʹ���ܽ�
	 * https://blog.csdn.net/qq_27869123/article/details/81138638
	 */
	
	static QueryRunner QR = new QueryRunner();
	//��ɾ��
	public static int update(String sql, Object...args) throws SQLException {
		Connection conn = getConnection();
		return QR.update( conn, sql, args );
	}
		
	//��ѯ�б�
	public static <E> List<E> queryList(String sql, Class<E> clazz, Object...args) throws SQLException {
		Connection conn = getConnection();
		return QR.query( conn, sql, new BeanListHandler<E>(clazz), args);
	}
		
	//��ѯ����
	public static <E> E queryObject(String sql, Class<E> clazz, Object...args) throws SQLException {
		Connection conn = getConnection();
		return QR.query( conn, sql, new BeanHandler<E>(clazz), args );
	}
		
	//��ѯֻ��һ��һ�е�ֵ, ����COUNT, SUN, MAX, MIN��
	public static Object queryValue(String sql, Object...args) throws SQLException {
		Connection conn = getConnection();
		return QR.query( conn, sql, new ScalarHandler<Object>(), args);
	}
	
	public static void main(String[] args) {
		
	}
}
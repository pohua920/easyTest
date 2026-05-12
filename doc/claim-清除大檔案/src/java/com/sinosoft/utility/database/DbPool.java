package com.sinosoft.utility.database;

import com.sinosoft.sysframework.common.datatype.DateTime;
import com.sinosoft.sysframework.reference.DBDataSource;
import com.sinosoft.sysframework.reference.DBFactory;
import com.sinosoft.sysframework.reference.DBManager;
import com.sinosoft.utility.string.ChgData;
import com.sinosoft.utility.string.Str;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.HashMap;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class DbPool {
	private Connection con = null;
	private Statement stmt = null;
	private ResultSet resultSet = null;
	private PreparedStatement preparedStatement = null;
	private boolean useTrim = true;
	private String name;
	private HashMap dataSources = new HashMap();

	public Statement open(String name) throws SQLException, ClassNotFoundException, Exception {
		DataSource dataSource = getDataSource(name);
		// 包装dataSource用於与Spring整合
		this.con = dataSource.getConnection();
		try {
			this.stmt = this.con.createStatement(1004, 1007);
		} catch (SQLException e) {
			this.con.close();
			throw e;
		}
		return this.stmt;
	}

	public DBManager getDBManager(String name) throws Exception {
		DBManager dbManager = new DBManager();
		dbManager.open(name, this.con);
		return dbManager;
	}

	public void setDBManager(DBManager dbManager) throws Exception {
		this.con = dbManager.getConnection();
		this.stmt = dbManager.getStatement();
	}

	/** @deprecated */
	public Statement openOra(String iUrl, String iUser, String iPassword) throws SQLException, ClassNotFoundException, Exception {
		return open("oracle.jdbc.driver.OracleDriver", iUrl, iUser, iPassword);
	}

	public Statement open(String driverName, String iUrl, String iUser, String iPassword) throws SQLException, ClassNotFoundException, Exception {
		Class.forName(driverName);
		this.con = DriverManager.getConnection(iUrl, iUser, iPassword);
		// this.con = getDataSource(driverName).getConnection();
		try {
			this.stmt = this.con.createStatement(1004, 1007);
		} catch (SQLException e) {
			this.con.close();
			throw e;
		}
		return this.stmt;
	}

	public void prepareStatement(String strSql) throws SQLException {
		if (this.preparedStatement != null) {
			this.preparedStatement.close();
		}
		try {
			this.preparedStatement = this.con.prepareStatement(strSql, 1004, 1007);
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public PreparedStatement getPrepareStatement(String strSql) throws SQLException {
		PreparedStatement pstmt = null;
		try {
			pstmt = this.con.prepareStatement(strSql, 1004, 1007);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			return pstmt;
		}
	}

	public void prepareInnerStatement(String strSql) throws SQLException {
		if (this.preparedStatement != null)
			this.preparedStatement.close();
		try {
			this.preparedStatement = this.con.prepareStatement(strSql, 1004, 1008);
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public int executePreparedUpdate() throws Exception {
		int result = this.preparedStatement.executeUpdate();
		return result;
	}

	public ResultSet executePreparedQuery() throws Exception {
		this.resultSet = this.preparedStatement.executeQuery();
		return this.resultSet;
	}

	public void addBatch() throws Exception {
		this.preparedStatement.addBatch();
	}

	public int[] executePreparedUpdateBatch() throws Exception {
		return this.preparedStatement.executeBatch();
	}

	public void setString(int index, String value) throws Exception {
		value = ChgData.nullToString(value);

		if (this.useTrim) {
			value = Str.rightTrim(value);
		}
		this.preparedStatement.setString(index, value);
	}

	public void setInt(int index, int value) throws Exception {
		this.preparedStatement.setInt(index, value);
	}

	public void setLong(int index, long value) throws Exception {
		this.preparedStatement.setLong(index, value);
	}

	public void setDouble(int index, double value) throws Exception {
		this.preparedStatement.setDouble(index, value);
	}

	public void setDateTime(int index, DateTime value) throws Exception {
		if (value != null)
			if (value.isEmpty()) {
				this.preparedStatement.setNull(index, 93);
			} else {
				Timestamp time = new Timestamp(value.getTime());
				this.preparedStatement.setTimestamp(index, time);
			}
	}

	public void setBytes(int index, byte[] value) throws Exception {
		this.preparedStatement.setBytes(index, value);
	}

	public String getString(ResultSet resultSet, int index) throws Exception {
		String value = resultSet.getString(index);
		value = ChgData.nullToString(value);

		if (this.useTrim) {
			value = Str.rightTrim(value);
		}
		return value;
	}

	public int getInt(ResultSet resultSet, int index) throws Exception {
		return resultSet.getInt(index);
	}

	public long getLong(ResultSet resultSet, int index) throws Exception {
		return resultSet.getLong(index);
	}

	public double getDouble(ResultSet resultSet, int index) throws Exception {
		return resultSet.getDouble(index);
	}

	public DateTime getDateTime(ResultSet resultSet, int type, int index) throws Exception {
		Timestamp time = resultSet.getTimestamp(index);
		DateTime d = null;
		if (time == null)
			d = new DateTime();
		else {
			d = new DateTime(time, type);
		}
		return d;
	}

	/** @deprecated */
	public DateTime getDateTime(ResultSet resultSet, int index) throws Exception {
		Timestamp time = resultSet.getTimestamp(index);
		DateTime d = null;
		if (time == null)
			d = new DateTime();
		else {
			d = new DateTime(time, 13);
		}
		return d;
	}

	/** @deprecated */
	public DateTime getDateTime(ResultSet resultSet, String name) throws Exception {
		Timestamp time = resultSet.getTimestamp(name);
		DateTime d = null;
		if (time == null)
			d = new DateTime();
		else {
			d = new DateTime(time, 13);
		}
		return d;
	}

	public byte[] getBytes(ResultSet resultSet, int index) throws Exception {
		return resultSet.getBytes(index);
	}

	public String getString(ResultSet resultSet, String name) throws Exception {
		String value = resultSet.getString(name);
		value = ChgData.nullToString(value);

		if (this.useTrim) {
			value = Str.rightTrim(value);
		}
		return value;
	}

	public int getInt(ResultSet resultSet, String name) throws Exception {
		return resultSet.getInt(name);
	}

	public long getLong(ResultSet resultSet, String name) throws Exception {
		return resultSet.getLong(name);
	}

	public double getDouble(ResultSet resultSet, String name) throws Exception {
		return resultSet.getDouble(name);
	}

	public DateTime getDateTime(ResultSet resultSet, int type, String name) throws Exception {
		Timestamp time = resultSet.getTimestamp(name);
		DateTime d = null;
		if (time == null)
			d = new DateTime();
		else {
			d = new DateTime(time, type);
		}
		return d;
	}

	public byte[] getBytes(ResultSet resultSet, String name) throws Exception {
		return resultSet.getBytes(name);
	}

	public void close() throws SQLException {
		SQLException sqle = null;
		if (this.stmt != null) {
			try {
				this.stmt.close();
				this.stmt = null;
			} catch (SQLException e) {
				sqle = e;
			}
		}
		if (this.preparedStatement != null) {
			try {
				this.preparedStatement.close();
				this.preparedStatement = null;
			} catch (SQLException e) {
				sqle = e;
			}
		}
		if (this.con != null) {
			this.con.close();
			this.con = null;
		}
		if (sqle != null)
			throw sqle;
	}

	public void closePreparedStatement() throws SQLException {
		if (this.preparedStatement != null)
			try {
				this.preparedStatement.close();
				this.preparedStatement = null;
			} catch (SQLException e) {
				throw e;
			}
	}

	public void beginTransaction() throws Exception {
		try {
			if (notUsedSpringTranscation() && this.con.getAutoCommit() == true) {
				this.con.setAutoCommit(false);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public void commitTransaction() throws Exception {
		try {
			if (notUsedSpringTranscation() && this.con != null) {
				this.con.commit();
				this.con.setAutoCommit(true);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public void rollbackTransaction() throws Exception {
		try {
			if (notUsedSpringTranscation() && this.con != null) {
				this.con.rollback();
				this.con.setAutoCommit(true);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public ResultSet executeQuery(String sql) throws SQLException {
		ResultSet rs = null;
		try {
			rs = this.stmt.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			return rs;
		}
	}

	public int getCount(String statement) throws SQLException {
		int intCount = 0;
		try {
			ResultSet rs = this.stmt.executeQuery(statement);

			while (rs.next()) {
				intCount = rs.getInt(1);
			}

			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			return intCount;
		}
	}

	public int executeUpdate(String statement) throws SQLException, Exception {
		int intFlag = 0;
		try {
			intFlag = this.stmt.executeUpdate(statement);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			return intFlag;
		}
	}

	public ResultSet query(String sql) throws SQLException {
		ResultSet rs = null;
		try {
			rs = this.stmt.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return rs;
	}

	public void insert(String sql) throws SQLException, Exception {
		try {
			int rowCount = this.stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public void update(String sql) throws SQLException, Exception {
		try {
			int rowCount = this.stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public void delete(String sql) throws SQLException, Exception {
		try {
			int rowCount = this.stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public void clearBatch() throws SQLException {
		try {
			this.stmt.clearBatch();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public void setValue(String[][] condition) throws Exception {
		int index = 0;
		for (index = 0; index < condition.length; index++)
			if (condition[index][0].trim().equals("String")) {
				setString(index + 1, condition[index][1]);
			} else if (condition[index][0].trim().equals("int")) {
				int tmp = Integer.parseInt(condition[index][1]);
				setInt(index + 1, tmp);
			} else if (condition[index][0].trim().equals("double")) {
				double tmp = Double.parseDouble(condition[index][1]);
				setDouble(index + 1, tmp);
			} else if (condition[index][0].trim().equals("long")) {
				long tmp = Long.parseLong(condition[index][1]);
				setDouble(index + 1, tmp);
			} else if (condition[index][0].trim().equals("byte")) {
				byte[] tmp = new byte[condition[index][1].length()];
				for (int j = 0; j < condition[index][1].length(); j++) {
					tmp[j] = Byte.parseByte(condition[index][1].substring(j, j + 1));
				}
				setBytes(index + 1, tmp);
			} else {
				setString(index + 1, condition[index][1]);
			}
	}

	public void addBatch(String sql) throws SQLException {
		try {
			this.stmt.addBatch(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public boolean commitBatch() throws SQLException {
		try {
			int[] updateCounts = this.stmt.executeBatch();
			if (updateCounts.length >= 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// label29: return true;
		}
		return true;
	}

	public DataSource getDataSource(String name) throws NamingException {
		Object object = this.dataSources.get(name);
		if (object != null) {
			this.name = name;
			return (DataSource) object;
		}
		Context context = new InitialContext();
		DBDataSource dbDataSource = DBFactory.getDB(name);
		DataSource dataSource;
		try {
			dataSource = (DataSource) context.lookup(dbDataSource.getJndiName());

		} catch (NamingException e) {

			dataSource = (DataSource) context.lookup("java:comp/env/" + dbDataSource.getJndiName());
		}
		// 包装dataSource用於与Spring整合
		dataSource = new TransactionAwareDataSourceProxy(dataSource);
		this.dataSources.put(name, dataSource);
		this.name = name;
		return dataSource;
	}

	private boolean notUsedSpringTranscation() throws NamingException {
		return !DataSourceUtils.isConnectionTransactional(this.con, this.getDataSource(this.name)) && !TransactionSynchronizationManager.isSynchronizationActive();
	}
}
package atrdw.datasource;

import java.sql.Connection;
import java.sql.SQLException;

import atrdw.model.Message;

public interface AspectDAOIF {
	/**
	 * Aspects columns pk
	 * @return
	 */
	public abstract String[] columnsAspectsId();

	/**
	 * Aspects values in the same order of the columnsAspectsId() function return
	 * @param ps
	 * @param parameterIndex
	 * @param m
	 * @return the aspect id
	 * @throws SQLException 
	 */
	public abstract int putAspectsValues(Connection con, Message m) throws SQLException;
	
	public abstract void finish() throws SQLException;
}

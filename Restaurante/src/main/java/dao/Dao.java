package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface Dao {

    ResultSet getResource(String table, ArrayList<String> fields, ArrayList<String> values) throws SQLException;

    ResultSet getTable (String table) throws  SQLException;

    ResultSet getTableMetadata (String table) throws SQLException;

    ResultSet getColumnMetadata (String table) throws SQLException;

    void addResource(String table, ArrayList<String> fields, ArrayList<Object> values) throws SQLException;

    void executeStatement (String statement) throws SQLException;
}

package dao;

import dao.utils.UtilsDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.IntStream;

public class DaoImpl implements Dao{
    private Connection connection;
    private UtilsDao utilsDao;

    public DaoImpl(Connection connection) {
        this.connection = connection;
        utilsDao = new UtilsDao();
    }

    @Override
    public ResultSet getResource(String table, ArrayList<String> fields, ArrayList<String> values) throws SQLException {
        String query = "select * from " + table + " where ";
        query += utilsDao.serializeParams(fields);
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        IntStream.range(0, values.size())
                .forEach(i -> {
                    try {
                        preparedStatement.setString(i + 1, values.get(i));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
        return preparedStatement.executeQuery();
    }

    @Override
    public ResultSet getTable(String table) throws SQLException {
        String query = "select * from " + table;
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        return preparedStatement.executeQuery();
    }

    @Override
    public ResultSet getTableMetadata(String table) throws SQLException {
        return connection.getMetaData().getTables(null, null, table, null);
    }

    @Override
    public ResultSet getColumnMetadata(String table) throws SQLException {
        return connection.getMetaData().getColumns(null, null, table, null);
    }

    @Override
    public void addResource(String table, ArrayList<String> fields, ArrayList<Object> values) throws SQLException{
        int numberOfValues = values.size();
        String statement = "insert into " + table + utilsDao.serializeFields(fields) + " values" +
                utilsDao.createValuesWithQuoteMarks(numberOfValues);
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        IntStream.range(0, values.size())
                .forEach(i -> {
                    try {
                        if(values.get(i) instanceof Integer) {
                            preparedStatement.setInt(i + 1, (int) values.get(i));
                        } else if(values.get(i) instanceof Double) {
                            preparedStatement.setDouble(i + 1, (double) values.get(i));
                        } else if(values.get(i) instanceof java.sql.Date) {
                            preparedStatement.setDate(i + 1, (java.sql.Date) values.get(i));
                        } else {
                            preparedStatement.setString(i + 1, (String) values.get(i));
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
        preparedStatement.execute();
    }

    @Override
    public void executeStatement(String statement) throws SQLException {
        connection.prepareStatement(statement).execute();
    }
}

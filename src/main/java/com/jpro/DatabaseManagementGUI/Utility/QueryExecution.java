package com.jpro.DatabaseManagementGUI.Utility;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Jeremy Hennessy
 *
 * Class for doing Queries on the Database
 */
public class QueryExecution {
    private static ResultSet result;

    /**
     * Queries the passed sql String in the database
     * Updates result
     *
     * @param sql sql to be queried
     */
    public static void query(String sql)  {
        PreparedStatement statement;
        try{
            statement = DBConnection.conn.prepareStatement(sql);

            if(statement.execute()){
                result = statement.getResultSet();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     *
     * @return resultSet of query
     */
    public static ResultSet getResult(){
        return result;
    }

}

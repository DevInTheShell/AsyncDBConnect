package asyncdbconnect;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OracleDatabaseConnect
{
    public static List getAllRowsFromUsersTable()
    {
        List rows = new ArrayList();
        
        try
        {
            Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "DEVELOPER", "P@ssw0rd");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
            for(int i=0; resultSet.next(); i++)
            {
                String[] row = new String[4];
                row[0] = String.valueOf(resultSet.getInt(1));
                row[1] = resultSet.getString(2);
                row[2] = resultSet.getString(3);
                row[3] = resultSet.getString(4);
                
                rows.add(row);
            }
            
            return rows;
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
            return null;
        }
    }
}

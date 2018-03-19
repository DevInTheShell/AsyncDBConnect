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
            try (Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "DEVELOPER", "P@ssw0rd"))
            {
                try (Statement statement = connection.createStatement())
                {
                    try (ResultSet resultSet = statement.executeQuery("select * from users"))
                    {
                        while(resultSet.next())
                        {
                            String[] row = new String[4];
                            row[0] = resultSet.getString(1);
                            row[1] = resultSet.getString(2);
                            row[2] = resultSet.getString(3);
                            row[3] = resultSet.getString(4);

                            rows.add(row);
                        }
                    }
                }
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

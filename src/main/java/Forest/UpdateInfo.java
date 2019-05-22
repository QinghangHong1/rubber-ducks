package Forest;


//UpdateInfo.java
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
@SuppressWarnings("serial")
public class UpdateInfo extends HttpServlet {
 
    public UpdateInfo() { }
 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
                                             throws ServletException, IOException {
        String message = null;
        try {
            System.out.println("Connect to updateInfo");
            Connection myConn = DriverManager.getConnection("jdbc:mysql://forest1.ccryyxtawuoq.us-west-1.rds.amazonaws.com/innodb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "admin", "cs48rubber");
            String userName = null;
            String userEmail = null;
            int money = 0;
            int health = 0;
            int attack = 0;
            int level = 0;
            if (request.getParameterMap().containsKey("user_name") && request.getParameterMap().containsKey("money") && request.getParameterMap().containsKey("game_level") && request.getParameterMap().containsKey("health") && request.getParameterMap().containsKey("attack")) {
                userName = request.getParameter("user_name");
                money = Integer.parseInt(request.getParameter("money"));
                attack = Integer.parseInt(request.getParameter("attack"));
                health = Integer.parseInt(request.getParameter("health"));
                level = Integer.parseInt(request.getParameter("game_level"));

                boolean result = updateData(myConn, userName, money, health, attack, level);
                if(result){
                    response.setStatus(HttpServletResponse.SC_OK);
                    message = "successfully updated the info";
                }else{
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    message = "USER NOT FOUND";
                }
            }else{
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                message = "BAD request. Check your parameter list";
            }
        }catch(SQLException e){
            e.printStackTrace();
        }catch (Exception ex) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
 
        } finally {
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("");
            response.getWriter().println(message);
            response.getWriter().close();
        }
    }
    public static boolean updateData(Connection myConn, String userName, int money, int health, int attack, int level) throws SQLException{
        //Enable update
        if(!checkExist(myConn, userName)){
            return false;
        }
        //enable change to the database
        String init = "SET SQL_SAFE_UPDATES=0;";
        PreparedStatement statement= myConn.prepareStatement(init);
        statement.executeUpdate();
        //execute the update statement
        String updateStatement = "UPDATE user_data SET money = ?, health = ?, attack = ?, game_level = ? WHERE user_name=?;";
        statement = myConn.prepareStatement(updateStatement);
        statement.setInt(1, money);
        statement.setInt(2, health);
        statement.setInt(3, attack);
        statement.setInt(4, level);
        statement.setString(5, userName);
        statement.executeUpdate();
        return true;
    }
    public static boolean checkExist(Connection myConn, String userName) throws SQLException{ 
        //check if the user exist
        String checkStatement = "SELECT * FROM user_data WHERE user_name=?;";
        PreparedStatement statement = myConn.prepareStatement(checkStatement);
        statement.setString(1, userName);
        ResultSet myRs = statement.executeQuery();
        return myRs.next();
    }
 
}

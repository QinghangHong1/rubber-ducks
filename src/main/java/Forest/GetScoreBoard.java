package Forest;


import com.google.gson.*; //for JSON
import java.util.*;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
 
@SuppressWarnings("serial")
public class GetScoreBoard extends HttpServlet {
 
    public GetScoreBoard() { }
 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
                                            throws ServletException, IOException{
        try{
            System.out.println("Connect to getScoreBoard");
            Connection myConn = DriverManager.getConnection("jdbc:mysql://forest1.ccryyxtawuoq.us-west-1.rds.amazonaws.com/innodb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "admin", "cs48rubber");
            String userName = null;
            String userPassword = null;
            ArrayList<JsonObject> result = new ArrayList<JsonObject>();
            try { 
                result= getQuery(myConn);
            } catch (SQLException ex) {
                ex.printStackTrace();
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            } finally {
                response.addHeader("Access-Control-Allow-Origin", "*");      
                response.setContentType("application/json; charset=utf-8");
                response.getWriter().println(result);
                response.getWriter().close();
            }
        }catch(SQLException e){
            System.out.println("Failed to connect to database");
        }
    }
    
    public static ArrayList<JsonObject> getQuery(Connection myConn) throws SQLException{
        
        String  getStatement= "SELECT user_name, game_level FROM innodb.user_data ORDER BY game_level DESC;";
        PreparedStatement statement= myConn.prepareStatement(getStatement);
        ResultSet myRs = statement.executeQuery();
        ArrayList<JsonObject> result = new ArrayList<JsonObject>();
        while(myRs.next()){
            JsonObject item = new JsonObject();
            item.addProperty("user_name", myRs.getString("user_name"));
            item.addProperty("game_level",myRs.getString("game_level"));
            result.add(item);
        }
        return result;
    }
 
}
package Forest;


//Jetty.java
import com.google.gson.*;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
 
public class Jetty extends Server {
 
    public Jetty(int port) {
        super(port);
 
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler
        .NO_SESSIONS);
        context.setContextPath("/forest");
        context.addServlet(new ServletHolder(new GetInfo()), "/getInfo/*");
        context.addServlet(new ServletHolder(new UpdateInfo()), "/updateInfo/*");
        context.addServlet(new ServletHolder(new CreateInfo()), "/createInfo/*");
        context.addServlet(new ServletHolder(new GetScoreBoard()), "/getScoreBoard/*");
        this.setHandler(context);
        this.setStopAtShutdown(true);
    }
 
}

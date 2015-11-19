package help.mygod.rest;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/api")
public class Jersey  extends ResourceConfig{

	public Jersey() {
		super();
		// TODO Auto-generated constructor stub
		packages("help.mygod.rest.weixin");
		register(LoggingFilter.class);
	}

}

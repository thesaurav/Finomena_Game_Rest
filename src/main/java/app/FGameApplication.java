package app;

import apiResource.FRestApiCallLanding;
import config.FGameRestConfig;
import io.dropwizard.Application;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Environment;
import jdbi.PlayerDAO;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.skife.jdbi.v2.DBI;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;

/**
 * Created by kumar on 28-10-2016.
 */
public class FGameApplication extends Application<FGameRestConfig>
{
    public static void main(String[] args)
    {
        try {
            new FGameApplication().run(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run(FGameRestConfig fGameRestConfig, Environment environment) throws Exception
    {
        final DBIFactory factory = new DBIFactory();
        final DBI jdbi = factory.build(environment, fGameRestConfig.getDataSourceFactory(), "postgresql");
        final PlayerDAO dao = jdbi.onDemand(PlayerDAO.class);
//        //final UserDAO dao1 = new UserDAO(hibernate.getSessionFactory());
//        environment.jersey().register(new FRestApiCallLanding(fGameRestConfig, dao));


        final FilterRegistration.Dynamic filter = environment.servlets().addFilter("CORS", CrossOriginFilter.class);


        // Configure CORS parameters
        filter.setInitParameter("allowedOrigins", "*");
        filter.setInitParameter("allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin");
        filter.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD");

        // Add URL mapping
        filter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
        environment.jersey().register(RolesAllowedDynamicFeature.class);
        environment.jersey().register(new FRestApiCallLanding(fGameRestConfig, dao));
    }
}

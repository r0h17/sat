package com.sch.sat;


import com.sch.sat.api.*;
import com.sch.sat.auth.SatAuthenticator;
import com.sch.sat.auth.SatAuthorizer;
import com.sch.sat.dao.*;
import com.sch.sat.resource.*;
import com.sch.sat.service.MessageService;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.hibernate.UnitOfWorkAwareProxyFactory;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.server.DefaultServerFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import java.util.EnumSet;

import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration.Dynamic;

public class SatApplication extends Application<SatConfiguration> {
	public static void main(String[] args) throws Exception {
		new SatApplication().run(args);
	}
	
	private final HibernateBundle<SatConfiguration> hibernateBundle =
            new HibernateBundle<SatConfiguration>(
					User.class,
					Role.class,
					Grades.class,
					PracticeTest.class,
					Course.class,
					Exam.class) {
                @Override
                public DataSourceFactory getDataSourceFactory(SatConfiguration configuration) {
                    return configuration.getDataSourceFactory();
                }
            };
            
    private final MigrationsBundle<SatConfiguration> migrationBundle =
    		new MigrationsBundle<SatConfiguration>() {
		        @Override
		        public DataSourceFactory getDataSourceFactory(SatConfiguration configuration) {
		            return configuration.getDataSourceFactory();
		        }
		    };

	@Override
	public void initialize(Bootstrap<SatConfiguration> bootstrap) {
		bootstrap.addBundle(migrationBundle);
        bootstrap.addBundle(hibernateBundle);
        // with this, we're adding the webapp folder (located in our resources)
        // as root of our webapp.
        bootstrap.addBundle(new AssetsBundle("/webapp", "/", "index.html"));

		bootstrap.setConfigurationSourceProvider(
				new SubstitutingSourceProvider(bootstrap.getConfigurationSourceProvider(),
						new EnvironmentVariableSubstitutor()
				)
		);
		//bootstrap.addBundle(producerBundle);

	}

	@Override
	public void run(SatConfiguration config, Environment environment) throws ClassNotFoundException {

		final MessageService emailService = new MessageService(config.getSmtpHost(), config.getSmtpPort(), config.getTemplate());

		final UserDao userDao = new UserDao(hibernateBundle.getSessionFactory());
		final RoleDao rolesDao = new RoleDao(hibernateBundle.getSessionFactory());
		final CoursesDao coursesDao = new CoursesDao(hibernateBundle.getSessionFactory());
		final ExamDao examDao = new ExamDao(hibernateBundle.getSessionFactory());
		final PracticeTestDao practiceTestDao = new PracticeTestDao(hibernateBundle.getSessionFactory());
		final GradesDao gradesDao = new GradesDao(hibernateBundle.getSessionFactory());

		final SatAuthenticator authenticator = new UnitOfWorkAwareProxyFactory(hibernateBundle)
				.create(SatAuthenticator.class, UserDao.class, userDao);

    	((DefaultServerFactory) config.getServerFactory()).setJerseyRootPath("/api/*");

		environment.jersey().register(new AuthDynamicFeature(new BasicCredentialAuthFilter.Builder<User>()
				.setAuthenticator(authenticator)
				.setAuthorizer(new SatAuthorizer())
				.setRealm("SUPER SECRET STUFF")
				.buildAuthFilter()));
		environment.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));
		environment.jersey().register(RolesAllowedDynamicFeature.class);

/*		UnauthorizedHandler unauthorizedHandler = new UnAuthorizedResourceHandler();
		AuthFilter basicAuthFilter = new BasicCredentialAuthFilter.Builder<User>()
				.setAuthenticator(authenticator)
				.setAuthorizer(new SatAuthorizer())
				.setRealm("SUPER SECRET STUFF")
				.setUnauthorizedHandler(unauthorizedHandler)
				.setPrefix("Basic")
				.buildAuthFilter();

		environment.jersey().register(new AuthDynamicFeature(basicAuthFilter));
		environment.jersey().register(RolesAllowedDynamicFeature.class);
		environment.jersey().register(new AuthValueFactoryProvider.Binder(User.class));

		environment.jersey().register(unauthorizedHandler);*/


		environment.jersey().register(new LoginResource(authenticator));
		environment.jersey().register(new RegistrationResource(userDao, rolesDao));
		environment.jersey().register(new ScheduleTestResource(practiceTestDao, userDao, examDao, coursesDao));
		environment.jersey().register(new GradesResource(gradesDao, userDao, practiceTestDao, emailService));
		environment.jersey().register(new UserResource(userDao));
		environment.jersey().register(new ExamResource(examDao));
		environment.jersey().register(new CourseResource(coursesDao));

		environment.healthChecks().register("SAT", new SatHealthCheck());


		// CORS
    	configureCors(environment);
    }
    
    private void configureCors(Environment environment) {
        Dynamic filter = environment.servlets().addFilter("CORS", CrossOriginFilter.class);
        filter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
        filter.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "GET,PUT,POST,DELETE,OPTIONS");
        filter.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
        filter.setInitParameter(CrossOriginFilter.ACCESS_CONTROL_ALLOW_ORIGIN_HEADER, "*");
        filter.setInitParameter("allowedHeaders", "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin");
        filter.setInitParameter("allowCredentials", "true");
      }

}

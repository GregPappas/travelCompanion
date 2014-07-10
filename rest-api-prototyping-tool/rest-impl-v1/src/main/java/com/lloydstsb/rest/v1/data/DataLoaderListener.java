package com.lloydstsb.rest.v1.data;

import javax.sql.DataSource;

import com.lloydstsb.rest.v1.factory.BeanFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletContextEvent;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.FileSystemResourceAccessor;

public class DataLoaderListener implements ServletContextListener {

	private static final String DATABASE_CHANGELOG_FILE_LOCATION = "webInfDatabseChangeLogFileName";
	private static final String SPRING_BEANS_FILE_LOCATION = "springBeansFileName";

	public void contextInitialized(ServletContextEvent servletContextEvent) {
		ServletContext servletContext = servletContextEvent.getServletContext();
		if (servletContext != null) {
			String changeLogLocation = servletContext.getRealPath("/WEB-INF") + "/" + servletContext.getInitParameter(DATABASE_CHANGELOG_FILE_LOCATION);
			String beansFileLocation = servletContext.getInitParameter(SPRING_BEANS_FILE_LOCATION);
			BeanFactory beanFactory = new BeanFactory(beansFileLocation);
			DataSource dataSource = beanFactory.getBean("dataSource", DataSource.class);
			Database database;
			try {
				database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(dataSource.getConnection()));
				Liquibase liquibase = new Liquibase(changeLogLocation,new FileSystemResourceAccessor(), database);
				liquibase.update(null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public void contextDestroyed(ServletContextEvent ce) {
	}
}

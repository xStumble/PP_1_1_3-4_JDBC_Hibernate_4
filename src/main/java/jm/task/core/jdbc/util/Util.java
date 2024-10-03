package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.*;
import java.util.Properties;

public class Util {
    private static final String hostName = "localhost";
    private static final String dbName = "pp1134";
    private static final String userName = "root";
    private static final String password = "root";

    private static Connection connection;
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        if (connection == null) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String connectionUrl = "jdbc:mysql://" + hostName + ":3306/" + dbName;
            connection = DriverManager.getConnection(connectionUrl, userName, password);
        }

        return connection;
    }

    public static void closeConnection() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    private static SessionFactory sessionFactory;
    public static SessionFactory getSessionFactory() throws HibernateException {
        if (sessionFactory == null) {

            Configuration configuration = new Configuration();

            Properties settings = new Properties();
            settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
            settings.put(Environment.URL, "jdbc:mysql://" + hostName + ":3306/" + dbName);
            settings.put(Environment.USER, userName);
            settings.put(Environment.PASS, password);
            settings.put(Environment.DIALECT, org.hibernate.dialect.MySQLDialect.class.getName());

            settings.put(Environment.SHOW_SQL, "true");

            settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

            settings.put(Environment.HBM2DDL_AUTO, "");

            configuration.setProperties(settings);

            configuration.addAnnotatedClass(User.class);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();

            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        }

        return sessionFactory;
    }

    public static void closeSessionFactory() throws SQLException {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}

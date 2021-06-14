package jm.task.core.jdbc.util;

import jm.task.core.jdbc.dao.exceptions.DBServiceException;
import jm.task.core.jdbc.dao.exceptions.UtilException;
import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    private HibernateUtil() {
    }

    public static SessionFactory getSessionFactory() throws DBServiceException {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration().configure();
                configuration.addAnnotatedClass(User.class);
                StandardServiceRegistryBuilder builder =
                        new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(builder.build());
            } catch (Exception exception) {
                throw new DBServiceException(UtilException.NO_CONNECTION_MSG, exception);
            }
        }
        return sessionFactory;
    }

    public static void shutdown() {
        try {
            getSessionFactory().close();
        } catch (DBServiceException e) {
            e.printStackTrace();
        }
    }
}

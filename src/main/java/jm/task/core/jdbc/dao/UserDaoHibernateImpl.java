package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.dao.exceptions.DBServiceException;
import jm.task.core.jdbc.dao.exceptions.UtilException;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() throws DBServiceException {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS users" +
                    "( id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                    "name VARCHAR (20) NOT NULL," +
                    "lastName VARCHAR (20) NOT NULL," +
                    "age TINYINT UNSIGNED NOT NULL" +
                    ");")
                    .executeUpdate();
            transaction.commit();
            System.out.println("Table users was created successfully");
            session.close();
        } catch (DBServiceException e) {
            HibernateUtil.shutdown();
            throw new DBServiceException(UtilException.BAD_CREATE_USERS_MSG, e);
        }
    }

    @Override
    public void dropUsersTable() throws DBServiceException {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS users;")
                    .executeUpdate();
            transaction.commit();
            System.out.println("Table users was dropped successfully");
            session.close();
        } catch (DBServiceException e) {
            HibernateUtil.shutdown();
            throw new DBServiceException(UtilException.BAD_DROP_USERS_MSG, e);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) throws DBServiceException {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery(String.format("INSERT INTO users (name, lastName, age) "
                    + " VALUES (\"%s\", \"%s\", \"%d\");", name, lastName, age))
                    .executeUpdate();
            transaction.commit();
            System.out.printf("User %s %s was successfully saved.%n", name, lastName);
            session.close();
        } catch (DBServiceException e) {
            HibernateUtil.shutdown();
            throw new DBServiceException(UtilException.BAD_SAVE_USER_MSG, e);
        }
    }

    @Override
    public void removeUserById(long id) throws DBServiceException {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery(String.format("DELETE FROM users" +
                    " WHERE id= %d;", id))
                    .executeUpdate();
            transaction.commit();
            System.out.printf("User ID %d was successfully removed.\n", id);
            session.close();
        } catch (DBServiceException e) {
            HibernateUtil.shutdown();
            throw new DBServiceException(UtilException.BAD_REMOVE_USER_MSG, e);
        }
    }

    @Override
    public List<User> getAllUsers() throws DBServiceException {
        List<User> out = new ArrayList<>();
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            out = session.createSQLQuery("SELECT * FROM users")
                    .addEntity(User.class)
                    .list();
            transaction.commit();
            System.out.printf("Got %d users successfully\n", out.size());
            session.close();
        } catch (DBServiceException e) {
            HibernateUtil.shutdown();
            throw new DBServiceException(UtilException.BAD_GET_ALL_USERS_MSG, e);
        }
        return out;
    }

    @Override
    public void cleanUsersTable() throws DBServiceException {
        try {
            List<User> allUsers = getAllUsers();
            for (User user : allUsers) {
                removeUserById(user.getId());
            }
            System.out.printf("All %d users were removed. DB is clean\n", allUsers.size());
        } catch (DBServiceException e) {
            HibernateUtil.shutdown();
            throw new DBServiceException(UtilException.BAD_CLEAN_USERS_TABLE_MSG, e);
        }
    }
}

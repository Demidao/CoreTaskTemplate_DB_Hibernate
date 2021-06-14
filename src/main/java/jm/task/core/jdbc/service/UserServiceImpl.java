package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.dao.exceptions.DBServiceException;
import jm.task.core.jdbc.model.User;

import java.util.List;

public class UserServiceImpl implements UserService {

    private UserDao userDao = new UserDaoHibernateImpl();

    public UserServiceImpl()  {
    }

    public void createUsersTable() throws DBServiceException {
        userDao.createUsersTable();
    }

    public void dropUsersTable() throws DBServiceException {
        userDao.dropUsersTable();
    }

    public void saveUser(String name, String lastName, byte age) throws DBServiceException {
        userDao.saveUser(name, lastName, age);
    }

    public void removeUserById(long id) throws DBServiceException {
        userDao.removeUserById(id);
    }

    public List<User> getAllUsers() throws DBServiceException {
        return userDao.getAllUsers();
    }

    public void cleanUsersTable() throws DBServiceException {
        userDao.cleanUsersTable();
    }
}

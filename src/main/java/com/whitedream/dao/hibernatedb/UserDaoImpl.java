package com.whitedream.dao.hibernatedb;

import com.whitedream.dao.util.HibernateSessionFactoryUtil;
import com.whitedream.dao.UserDao;
import com.whitedream.dao.exception.EntityAlreadyExistsException;
import com.whitedream.dao.exception.NoEntityExistsException;
import com.whitedream.model.Role;
import com.whitedream.model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoImpl implements UserDao {
    @Override
    public User getUser(int id) throws NoEntityExistsException {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(User.class, id);
    }

    @Override
    public User getUser(String userName) throws NoEntityExistsException {

        return null;
    }

    @Override
    public User createUser(User user) throws EntityAlreadyExistsException {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(user);
        transaction.commit();
        session.close();
        return null;
    }

    @Override
    public void updatePassword(User user, String newPassword) throws NoEntityExistsException {

    }

    @Override
    public void setRoleToUser(User user, Role role) throws NoEntityExistsException {

    }

    @Override
    public void deleteUser(User user) throws NoEntityExistsException {

    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }
}

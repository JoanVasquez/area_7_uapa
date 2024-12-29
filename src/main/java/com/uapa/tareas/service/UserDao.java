package com.uapa.tareas.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.uapa.tareas.models.User;
import com.uapa.tareas.utils.HibernateUtil;

public class UserDao {
	
	public void saveEntity(User user) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Error when saving user:: " + e.getMessage());
        }
    }

    public List<User> getEntities() {
        List<User> users = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<User> query = session.createQuery("FROM User", User.class);
            users = query.getResultList();
        } catch (Exception e) {
            System.out.println("Error when retrieving users:: " + e.getMessage());
        }
        return users;
    }
	
}

package com.dropwizard.jdbi;

import com.dropwizard.core.User;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

/**
 * Created by SauravKumar on 20-06-2016.
 */
public class PersonDAO extends AbstractDAO<User>
{
    public PersonDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}

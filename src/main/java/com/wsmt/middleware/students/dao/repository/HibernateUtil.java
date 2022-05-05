package com.wsmt.middleware.students.dao.repository;

import com.wsmt.middleware.students.dao.entity.StudentEntity;
import com.wsmt.middleware.students.dao.entity.GradeEntity;
import com.wsmt.middleware.students.dao.entity.SchoolObjectEntity;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.io.InputStream;
import java.util.Properties;

@Slf4j
public class HibernateUtil {
    private static final Properties properties = new Properties();
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                loadProperties();
                configuration.setProperties(properties);
                configuration.addAnnotatedClass(StudentEntity.class);
                configuration.addAnnotatedClass(SchoolObjectEntity.class);
                configuration.addAnnotatedClass(GradeEntity.class);
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties())
                        .build();
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                log.error(e.getMessage());
                log.error("Error in session factory");
            }
        }
        return sessionFactory;
    }

    public static void loadProperties() {
        InputStream input = null;
        try {
            input = HibernateUtil.class.getClassLoader().getResourceAsStream("application.properties");
            properties.load(input);
        } catch (Exception e) {
            log.error("Error when trying to load application.properties");
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (Exception e) {
                    log.error("Error when trying to close the stream");
                }
            }
        }
    }
}

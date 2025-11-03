package ma.projet.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import ma.projet.beans.Femme;
import ma.projet.beans.Homme;
import ma.projet.beans.Mariage;
import ma.projet.beans.Personne;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                
                // Charger les propriétés depuis application.properties
                Properties settings = loadProperties();
                
                configuration.setProperties(settings);
                
                // Ajouter les entités
                configuration.addAnnotatedClass(Personne.class);
                configuration.addAnnotatedClass(Homme.class);
                configuration.addAnnotatedClass(Femme.class);
                configuration.addAnnotatedClass(Mariage.class);
                
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties())
                        .build();
                
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }

    private static Properties loadProperties() {
        Properties properties = new Properties();
        try (InputStream inputStream = HibernateUtil.class.getClassLoader()
                .getResourceAsStream("application.properties")) {
            if (inputStream == null) {
                throw new IOException("Fichier application.properties introuvable");
            }
            properties.load(inputStream);
            
            // Convertir les propriétés personnalisées en format Hibernate
            Properties hibernateProperties = new Properties();
            hibernateProperties.setProperty("hibernate.connection.driver_class", properties.getProperty("jdbc.driver"));
            hibernateProperties.setProperty("hibernate.connection.url", properties.getProperty("jdbc.url"));
            hibernateProperties.setProperty("hibernate.connection.username", properties.getProperty("jdbc.user"));
            hibernateProperties.setProperty("hibernate.connection.password", properties.getProperty("jdbc.password"));
            hibernateProperties.setProperty("hibernate.dialect", properties.getProperty("hibernate.dialect"));
            hibernateProperties.setProperty("hibernate.show_sql", properties.getProperty("hibernate.show_sql"));
            hibernateProperties.setProperty("hibernate.format_sql", properties.getProperty("hibernate.format_sql"));
            hibernateProperties.setProperty("hibernate.hbm2ddl.auto", properties.getProperty("hibernate.hbm2ddl.auto"));
            hibernateProperties.setProperty("hibernate.connection.pool_size", properties.getProperty("hibernate.connection.pool_size"));
            
            return hibernateProperties;
        } catch (IOException e) {
            e.printStackTrace();
            return properties;
        }
    }

    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}

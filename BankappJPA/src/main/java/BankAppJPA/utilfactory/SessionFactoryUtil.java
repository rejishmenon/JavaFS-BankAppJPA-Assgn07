package BankAppJPA.utilfactory;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class SessionFactoryUtil {
    private static SessionFactory sf = null;

    public static SessionFactory getSessionFactory() {
        StandardServiceRegistry reg = new StandardServiceRegistryBuilder()
                .configure("hibernate.config.xml")
                .build();
        sf = new MetadataSources(reg)
                .buildMetadata()
                .buildSessionFactory();
        return sf;
    }
}

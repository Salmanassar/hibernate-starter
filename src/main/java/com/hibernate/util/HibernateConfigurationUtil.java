package com.hibernate.util;

import com.hibernate.coverter.BirthdayConvertor;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.experimental.UtilityClass;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@UtilityClass
public class HibernateConfigurationUtil {
    public static SessionFactory buildSessionFactory(){
        Configuration configuration = buildConfiguration();
        return configuration.buildSessionFactory();
    }

    public static Configuration buildConfiguration() {
        Configuration configuration = new Configuration();
        configuration.addAttributeConverter(new BirthdayConvertor(),true);
        configuration.registerTypeOverride(new JsonBinaryType());
        configuration.configure("hibernate.cfg.xml");
        return configuration;
    }

}

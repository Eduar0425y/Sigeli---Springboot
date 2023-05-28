package com.example.fesc.sigeliFesc.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class AppContexto  implements ApplicationContextAware {

    private static  ApplicationContext contexto;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        contexto = applicationContext;

    }

    public static Object getBean(String nombre){
        return contexto.getBean(nombre);
    }
}

package com.klub.entrypoint.api.helper.utils;

import java.lang.reflect.Method;

public class MethodInvocationUtils {

    /**
     * Invoke a set method on an attribute of an object. In case the method does not exist
     * it just pass.
     *
     * The attribute name bust be in camel case format and the set method must respect
     * the format set + Attribute.
     *
     * Exemple: setDate, setBookAuthor
     *
     * @param objectClass the object on which the invocation will occur class
     * @param object the object itself
     * @param attribute the object attribute
     * @param param the parameter to pass to the method that will be invoked
     */
    public static void set(Class<?> objectClass, Object object, String attribute, Object param){
        try {
            Method m = objectClass.getDeclaredMethod(
                    String.format("set%s", attribute), param.getClass());
            m.invoke(object, param);
        } catch (Exception e){
            System.err.println(e.getMessage());
            //e.printStackTrace();
        }
    }
}

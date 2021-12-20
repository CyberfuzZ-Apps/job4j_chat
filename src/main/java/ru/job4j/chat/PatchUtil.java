package ru.job4j.chat;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Класс PatchUtil
 *
 * @author Evgeniy Zaytsev
 * @version 1.0
 */
public class PatchUtil {

    public static <T>  T patch(T sent, T founded) throws InvocationTargetException, IllegalAccessException {
        Method[] methods = founded.getClass().getDeclaredMethods();
        Map<String, Method> namePerMethod = new HashMap<>();
        for (Method method : methods) {
            String name = method.getName();
            if (name.startsWith("get") || name.startsWith("set")) {
                namePerMethod.put(name, method);
            }
        }
        for (String name : namePerMethod.keySet()) {
            if (name.startsWith("get")) {
                Method getMethod = namePerMethod.get(name);
                Method setMethod = namePerMethod.get(name.replace("get", "set"));
                if (setMethod == null) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Недопустимое свойство!!!");
                }
                Object newValue = getMethod.invoke(sent);
                if (newValue != null) {
                    setMethod.invoke(founded, newValue);
                }
            }
        }
        return founded;
    }
}

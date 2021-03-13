package pers.hyu.tyche.utils;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.Random;

/**
 * This is a util class for entity object.
 * This util class provide the function that generate the unique id for the entity;
 * and the function that check if all the fields of the entity have value.
 *
 * @author Heng Yu
 * @version 1.0
 */
@Component
public class EntityUtils {
    private static final String STRINGS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * Generate an length of 30 unique id for the object.
     *
     * @return The generated id.
     * @throws RuntimeException Throw the exception, if the length of the id is less than 13.
     */
    public String generateId() {
        Date date = new Date();
        String dateStr = String.valueOf(date.getTime());
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 17; i++) {
            stringBuilder.append(STRINGS.charAt(new Random().nextInt(STRINGS.length())));
        }
        return stringBuilder.toString() + dateStr;

    }


    /**
     * Check if all of the fields of the object have value.
     *
     * @param obj           The object which need to be checked.
     * @param allowEmptyStr A boolean param, true means the field can be ""; otherwise choose false.
     * @return If the allowEmptyStr param is true; return true if any of the field only is null; otherwise return false.
     * If the allowEmptyStr param is false; return true if any of the field is null or is the empty value--""; otherwise return false.
     * @throws IllegalAccessException
     */
    public boolean isNotAllFieldSet(Object obj, boolean allowEmptyStr) throws IllegalAccessException {

        // allow the filed has the empty string
        if (allowEmptyStr) {
            for (Field f : obj.getClass().getDeclaredFields()) {
                f.setAccessible(true); // make the private field accessible
                if (f.get(obj) == null) {
                    return true;
                }
            }

        } else {
            for (Field f : obj.getClass().getDeclaredFields()) {
                f.setAccessible(true); // make the private field accessible
                if (StringUtils.isEmpty(f.get(obj))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Check if all of the fields of the object have text.
     *
     * @param obj  The object which need to be checked.
     * @return     False if all the fields has a valid text value; otherwise, return true.
     * @throws IllegalAccessException
     */
    public boolean isNotAllFieldHasText(Object obj) throws IllegalAccessException {

        for (Field f : obj.getClass().getDeclaredFields()) {
            f.setAccessible(true); // make the private field accessible
            if (!StringUtils.hasText((CharSequence) f.get(obj))) {
                return true;
            }
        }
        return false;
    }
}

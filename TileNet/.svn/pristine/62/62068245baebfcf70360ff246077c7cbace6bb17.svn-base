package com.putable.tilenet.client;

import java.awt.event.KeyEvent;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * A little all-static 'let's reflect on it' class to extract the
 * <code>VK_</code> 'virtual key' constants from java.awt.event.KeyEvent and
 * provide them in a String<->Integer mappable form. May be used by students
 * enrolled in UNM CS 351 Fall 2013, as long as they are so enrolled.
 * 
 * @author ackley
 * @version 1.3
 * @see java.awt.event.KeyEvent
 * @throws IllegalStateException
 *             during class loading, if anything goes wrong during
 *             initialization of the mapping -- but hopefully, modulo strange
 *             security issues anyway, nothing will go wrong.
 */
public final class VKMap {
    private static final Map<String, Integer> vkMap = new HashMap<String, Integer>();
    private static final Map<Integer, String> reverseMap = new HashMap<Integer, String>();

    /*
     * Initialize the maps eagerly to avoid any worries about thread safety
     * later.
     */
    static {
        initMap();
    }

    /**
     * Map from the name of 'virtual keycode' -- such as "VK_A" or "VK_HOME" --
     * to the corresponding Integer value as declared in the class
     * {@link java.awt.event.KeyEvent}. For example, if keyName is "VK_X", this
     * method returns 88, the value of {@link java.awt.event.KeyEvent#VK_X}. If
     * the supplied keyName does not match a VK_* constant from KeyEvent,
     * returns null.
     * 
     * @param keyName
     *            The name of the key constant to look up (including the "VK_"
     *            part -- so "VK_ENTER" is right but "ENTER" is wrong).
     * @return the value of the corresponding constant if it exists, or null if
     *         no such public static constant of that name is declared in class
     *         {@link java.awt.event.KeyEvent}
     */
    public static Integer getKeyCode(String keyName) {
        return vkMap.get(keyName);
    }

    /**
     * Map from an Integer value representing a 'virtual keycode' to the
     * corresponding String name of the keycode as defined by
     * {@link java.awt.event.KeyEvent}, if one exists. For example, if keyCode
     * is 88, this method returns "VK_X", corresponding to
     * {@link java.awt.event.KeyEvent#VK_X}. If the supplied keyCode is null or
     * there is no matching 'VK_' constant, returns null
     * 
     * @param keyCode
     *            value to map
     * @return A String beginning with "VK_" if that name is defined in
     *         {@link java.awt.event.KeyEvent} to have value keyCode, or null
     * 
     */
    public static String getKeyName(Integer keyCode) {
        return reverseMap.get(keyCode);
    }

    private VKMap() { /* Don't make any of these */
    }

    private static void initMap() {
        try {
            Field[] fields = KeyEvent.class.getDeclaredFields();
            for (Field f : fields) {
                int modifiers = f.getModifiers();
                if (Modifier.isPublic(modifiers)
                        && Modifier.isStatic(modifiers)) {
                    String fieldName = f.getName();
                    if (fieldName.startsWith("VK_")) {
                        vkMap.put(fieldName, f.getInt(null));
                        reverseMap.put(f.getInt(null), fieldName);
                    }
                }
            }
        } catch (SecurityException e) {
            throw new IllegalStateException("Problem accessing KeyEvent:" + e);
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("Problem accessing KeyEvent:" + e);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("Problem accessing KeyEvent:" + e);
        } catch (ExceptionInInitializerError e) {
            throw new IllegalStateException("Problem accessing KeyEvent:" + e);
        }
    }

    /**
     * A little test driver. Supply VK_ keynames to look up on the command line,
     * or supply no arguments to get a representative smattering of defined and
     * undefined names tested.
     * 
     * @param args
     *            Strings to look up as VK_ keynames.
     */
    public static void main(String[] args) {
        String[] defaultNames = { "VK_X", "VK_ENTER",
                "VK_DEAD_SEMIVOICED_SOUND", "VK_REST_FAIL", "Q", "KEY_PRESSED",
                "X" };
        if (args.length == 0)
            args = defaultNames;
        for (String a : args) {
            System.out.println(a + " maps to " + VKMap.getKeyCode(a));
        }
    }
}

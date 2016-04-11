package com.putable.deathbox;

/**
 * An implementation of a DeathBox, attempting to follow all the DeathBox rules
 * exactly correctly.
 * 
 * <p>
 * <i>Note! There may still be one or perhaps more tiny bugs in this
 * implementation!</i>
 * 
 * @author ackley
 * 
 */
public class DeathBoxImpl implements DeathBox {

    public boolean putInto(Object o) {
        int num;
        if (o instanceof String) {
            num = Integer.parseInt((String) o);
        } else if (o instanceof Integer) {
            num = (Integer) o;
        } else
            return false;

        // Check if in range
        if (num <= MIN || num >= MAX)
            return false;

        // Check if even..
        if ((num & 1) == 1)
            return false;

        // Check if already seen
        if (_seen[num])
            return false;
        _seen[num] = true;
        return true;
    }

    private final int MAX = 60, MIN = 6;
    private boolean _seen[] = new boolean[MAX + 1];
}

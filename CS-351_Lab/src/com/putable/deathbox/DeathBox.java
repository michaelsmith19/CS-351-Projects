package com.putable.deathbox;

/**
 * The interface presented by a DeathBox, into which only some objects may be
 * safely put; otherwise you die.
 * 
 * <p>
 * Here are the rules for putting things into a DeathBox:
 * <ol>
 * <li>If you put anything that's not an integer, or a String containing an
 * integer, into the DeathBox, you die.
 * <li>If you put anything outside 6 to 60 into the DeathBox, you die.
 * <li>If you put anything into the DeathBox that's more than three times the
 * last thing put into the DeathBox, you die.
 * <li>If you put the same number into the DeathBox more than three times, you
 * die.
 * <li>You can't put anything into the DeathBox after you've died.
 * <li>If the thing you put into the DeathBox doesn't cause you to die according
 * to the rules 1-5, then you live.
 * </ol>
 * 
 * @author ackley
 * @version 1.0
 * 
 */
public interface DeathBox {

    /**
     * Attempt to put another object into a DeathBox.
     * 
     * @param o
     *            the object to attempt to place in the DeathBox
     * @return true if o was put into the DeathBox successfully, and false if
     *         'you died' attempting to put it in (or were already dead before
     *         the last putInto attempt.)
     */
    public boolean putInto(Object o);

}

package com.gamexisg.multijugadorconframework.shooter.utils;

public class ContainsId {
    public static boolean evalue(int[] ids, int id) {
        for (int validId : ids) {
            if (validId == id) {
                return true;
            }
        }
        return false;
    }
}

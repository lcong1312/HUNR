package com.ngocrong.model;

public class LegacySideTaskTemplate {

    public final int id;
    public final String name;
    public final int[][] counts;

    public LegacySideTaskTemplate(int id, String name, int[][] counts) {
        this.id = id;
        this.name = name;
        this.counts = counts;
    }

    public int getMinCount(int level) {
        return counts[level][0];
    }

    public int getMaxCount(int level) {
        return counts[level][1];
    }
}

package com.crawn.game.model.content;

final class Statistics {
    static {
        preffix = new String[]{"", "K", "M", "B", "T"};
    }

    Statistics(long value) {
        this.value = value;
    }

    void increment(int value) {
        this.prefixIndex = 0;
        this.value += value;
        long tmpVal = this.value;
        while ((tmpVal /= 1000) >= 1) {
            this.prefixIndex++;
        }
    }

    String getPrefix() {
        if (prefixIndex <= preffix.length) {
            return preffix[prefixIndex];
        }
        return "ALOT";
    }

    long getValueByModule() {
        return value / (int) Math.pow(1000, prefixIndex);
    }

    long getValue() {
        return value;
    }


    private long value;
    private int prefixIndex;

    final static private String[] preffix;
}

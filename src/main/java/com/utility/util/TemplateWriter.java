package com.utility.util;

public class TemplateWriter {

    private final StringBuilder builder;

    public TemplateWriter(String base) {
        this.builder = new StringBuilder(base);
    }

    public void replace(String target, String replacement) {
        int start = builder.indexOf(target);
        int end = start + target.length();

        builder.replace(start, end, replacement);
    }

    public boolean contains(String pattern) {
        return builder.toString().contains(pattern);
    }

    @Override
    public String toString() {
        return builder.toString();
    }
}

package com.sample.config;

public class DIYDatabaseContextHolder {
	private static final ThreadLocal<DIYDatabase> CONTEXT = new ThreadLocal<>();

    public static void set(DIYDatabase diyDatabase) {
        CONTEXT.set(diyDatabase);
    }

    public static DIYDatabase getDiyDatabase() {
        return CONTEXT.get();
    }

    public static void clear() {
        CONTEXT.remove();
    }

}

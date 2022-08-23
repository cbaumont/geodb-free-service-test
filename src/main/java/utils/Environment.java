package utils;

public class Environment {

    private Environment() {
    }

    public static String getCurrentEnv() {
        String env = System.getenv("ENV");
        if (env == null) {
            env = "prd";
        }
        return System.getProperty("env", env);
    }
}

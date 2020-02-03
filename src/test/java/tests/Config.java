package tests;

public class Config {
    public static final String baseUrl = System.getProperty("baseUrl", "https://www.autobilis.lt");
    public static final String host = System.getProperty("host", "localhost");
    public static final String browserName = System.getProperty("browserName", "chrome");
    public static final String browserVersion = System.getProperty("browserVersion", "latest");
    public static final String platformName = System.getProperty("platformName", "macOS 10.14");
    public static final String sauceUser = System.getenv("SAUCE_USERNAME");
    public static final String sauceKey = System.getenv("SAUCE_ACCESS_KEY");
}

package httpserver.config;

public class ConfigurationManager
{
    private static ConfigurationManager myConfigurationManager;
    private static Configuration myCurrentConfiguration;

    private ConfigurationManager()
    {

    }

    public static ConfigurationManager getInstance()
    {
        if(myConfigurationManager==null)
        {
            myConfigurationManager = new ConfigurationManager();
        }
        return myConfigurationManager;
    }
    //used to load a configuration file by the path provided
    public void loadConfiguration(String filePath)
    {

    }
    //returns the current loaded configuration
    public void getCurrentConfiguration()
    {

    }

}

package plugins.Fofa.Config;

import plugins.Fofa.DataType.Config;

public class GlobalConfig {
    public static String VERSION = "1.0.2";

//    // fofa email
//    public static String EMAIL;
//
//    // fofa key
//    public static String KEY;
//
//    public static Map<String, Advanced> ADVANCEDS = new HashMap<>();

    public static Config CONFIG;

    // 获取fofa用户信息的请求
    public static String GET_FOFA_USER_INFO = "https://fofa.info/api/v1/info/my?email=%s&key=%s";

    // fofa查询接口搜索
    public static String GET_FOFA_SEARCH_ALL = "https://fofa.info/api/v1/search/all";

    // fofa翻页查询接口
    public static String GET_FOFA_SEARCH_NEXT = "https://fofa.info/api/v1/search/next";

    // 保存路径
//    public static String CONFIG_PATH = "config/config.json";
    public static String CONFIG_PATH = "plugins/Fofa/config/config.json";
}

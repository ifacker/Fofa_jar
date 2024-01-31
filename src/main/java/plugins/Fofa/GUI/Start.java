package plugins.Fofa.GUI;

import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import plugins.Fofa.Config.GlobalConfig;
import plugins.Fofa.DataType.Advanced;
import plugins.Fofa.DataType.Config;
import plugins.Fofa.Util.FileIO;

import java.io.File;

public class Start {
    public Node show(Stage primaryStage){
        // 初始化配置文件
        initConfig();

        // 顶部的标签
        // 创建选项卡
        TabPane tabPane = new TabPane();
        // 设置每个 tab 最少 50 个单位的宽度
        tabPane.setTabMinWidth(50);

        // 查询主界面
        Tab tabRoot = new Tab("查询");
        tabRoot.setClosable(false);
        tabRoot.setContent(new RootPage().show(primaryStage));

        // 语法界面
        Tab tabRules = new Tab("语法");
        tabRules.setClosable(false);
        tabRules.setContent(new RulesPage().show());

        // 配置界面
        Tab tabConfig = new Tab("配置");
        tabConfig.setClosable(false);
        tabConfig.setContent(new ConfigPage().show());

        // about 界面
        Tab tabAbout = new Tab("关于");
        tabAbout.setClosable(false);
        tabAbout.setContent(new AboutPage().show());


        tabPane.getTabs().addAll(tabRoot, tabRules, tabConfig, tabAbout);

        VBox root = new VBox(10);
        root.getChildren().addAll(tabPane);

        return root;
    }

    void initConfig(){
        GlobalConfig.CONFIG = new Config();
        if (new File(GlobalConfig.CONFIG_PATH).exists()) {
            GlobalConfig.CONFIG = (Config) FileIO.readObject(GlobalConfig.CONFIG_PATH, Config.class);
        } else {
            Advanced size = new Advanced();
            size.setValue("100");
            GlobalConfig.CONFIG.getAdvanceds().put("size", size);
            Advanced next = new Advanced();
            next.setValue("1");
            GlobalConfig.CONFIG.getAdvanceds().put("next", next);
            Advanced fields = new Advanced();
            fields.setOtherValue("false");
            GlobalConfig.CONFIG.getAdvanceds().put("fields", fields);
            FileIO.saveObject(GlobalConfig.CONFIG_PATH, GlobalConfig.CONFIG);
        }
        GlobalConfig.CONFIG.getAdvanceds().get("next").setOtherValue("");
        FileIO.saveObject(GlobalConfig.CONFIG_PATH, GlobalConfig.CONFIG);
    }
}

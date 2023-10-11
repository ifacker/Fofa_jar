package plugins.Fofa.GUI;

import Internet.ProxyInternet;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import plugins.Fofa.Config.GlobalConfig;
import plugins.Fofa.Util.FileIO;
import plugins.Fofa.Util.MsgBox;

import java.io.IOException;

public class ConfigPage {
    Label labelEmail;

    Label labelKey;

    TextField textFieldEmail;

    TextField textFieldKey;

    Button buttonSave;

    Button buttonReset;

    VBox vBoxConfig;

    HBox hBoxButton;

    BorderPane borderPaneRoot;

    Separator separator;    // 分割线

    Label labelShowEmail;   //邮箱地址
    Label labelShowUname;   // 用户名
    Label labelShowCategory;    // 用户种类
    Label labelShowFcoin;   // F币
    Label labelShowFofa_point;  // F点
    Label labelShowRemain_api_query;    // API月度剩余查询次数
    Label labelShowRemain_api_data; // API月度剩余返回数量
    Label labelShowIsvip;   // 是否是会员
    Label labelShowVip_level;   // vip等级



    public Node show() {

        labelEmail = new Label("Email:");

        textFieldEmail = new TextField(GlobalConfig.CONFIG.getEmail());

        labelKey = new Label("Key:");

        textFieldKey = new TextField(GlobalConfig.CONFIG.getKey());

        // 分割线
        separator = new Separator();
        separator.setPadding(new Insets(20, 0, 0, 0));

        labelShowEmail = new Label("邮箱地址：");
        labelShowUname = new Label("用户名：");
        labelShowCategory = new Label("用户种类：");
        labelShowFcoin = new Label("F币：");
        labelShowFofa_point = new Label("F点：");
        labelShowRemain_api_query = new Label("API月度剩余查询次数：");
        labelShowRemain_api_data = new Label("API月度剩余返回数量：");
        labelShowIsvip = new Label("是否是会员：");
        labelShowVip_level = new Label("vip等级：");

        getInfo(GlobalConfig.CONFIG.getEmail(), GlobalConfig.CONFIG.getKey());

        vBoxConfig = new VBox(10);
        vBoxConfig.setPadding(new Insets(10));
        vBoxConfig.setAlignment(Pos.BASELINE_LEFT);
        vBoxConfig.getChildren().addAll(labelEmail, textFieldEmail, labelKey, textFieldKey, separator,
                labelShowEmail, labelShowUname, labelShowCategory, labelShowFcoin, labelShowFofa_point,
                labelShowRemain_api_query, labelShowRemain_api_data, labelShowIsvip, labelShowVip_level);

        buttonReset = new Button("恢复保存前");
        buttonReset.setOnAction(this::buttonResetAction);

        buttonSave = new Button("保存");
        buttonSave.setMinWidth(100);
        buttonSave.setOnAction(this::buttonSaveAction);

        hBoxButton = new HBox(10);
        hBoxButton.setPadding(new Insets(10));
        hBoxButton.setAlignment(Pos.CENTER_RIGHT);
        hBoxButton.getChildren().addAll(buttonReset, buttonSave);

        borderPaneRoot = new BorderPane();
        borderPaneRoot.setCenter(vBoxConfig);
        borderPaneRoot.setBottom(hBoxButton);
        return borderPaneRoot;
    }

    void buttonResetAction(ActionEvent event) {
        textFieldEmail.setText(GlobalConfig.CONFIG.getEmail());
        textFieldKey.setText(GlobalConfig.CONFIG.getKey());
    }

    boolean getInfo(String email, String key) {
        String url = String.format(GlobalConfig.GET_FOFA_USER_INFO, email, key);
        OkHttpClient client = new ProxyInternet().newClient();
        // 创建 HTTP 请求
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = null;
        try {
            // 发送请求
            response = client.newCall(request).execute();
            // 处理响应
//            System.out.println(response.body().string());

            //解析 json
            JsonElement element = JsonParser.parseString(response.body().string());
            JsonObject jsonData = element.getAsJsonObject();

            // 检测账号是否异常
            if ("true".equals(jsonData.get("error").getAsString())) {
                return false;
            }

            labelShowEmail.setText(String.format("邮箱地址：%s", jsonData.get("email").getAsString()));
            labelShowUname.setText(String.format("用户名：%s", jsonData.get("username").getAsString()));
            labelShowCategory.setText(String.format("用户种类：%s", jsonData.get("category").getAsString()));
            labelShowFcoin.setText(String.format("F币：%s", jsonData.get("fcoin").getAsString()));
            labelShowFofa_point.setText(String.format("F点：%s", jsonData.get("fofa_point").getAsString()));
            labelShowRemain_api_query.setText(String.format("API月度剩余查询次数：%s", jsonData.get("remain_api_query").getAsString()));
            labelShowRemain_api_data.setText(String.format("API月度剩余返回数量：%s", jsonData.get("remain_api_data").getAsString()));
            labelShowIsvip.setText(String.format("是否是会员：%s", jsonData.get("isvip").getAsString()));
            labelShowVip_level.setText(String.format("vip等级：%s", jsonData.get("vip_level").getAsString()));

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }



    void buttonSaveAction(ActionEvent event) {
        GlobalConfig.CONFIG.setEmail(textFieldEmail.getText());
        GlobalConfig.CONFIG.setKey(textFieldKey.getText());
        // 检测
        if (!getInfo(textFieldEmail.getText(), textFieldKey.getText())) {
            MsgBox.showConfirmation("警告", "账号验证未通过，是否强制保存？", r -> {
                if (r == ButtonType.OK) {
                    // 保存
                    FileIO.saveObject(GlobalConfig.CONFIG_PATH, GlobalConfig.CONFIG);
                    MsgBox.sendSystemInfo("温馨提示", "保存成功");
                }
            });
        } else {
            // 保存
            FileIO.saveObject(GlobalConfig.CONFIG_PATH, GlobalConfig.CONFIG);
            MsgBox.sendSystemInfo("温馨提示", "保存成功");
        }
    }
}

package plugins.PluginDemo;

import Internet.ProxyInternet;
import Plugin.Plugin;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;


public class Main implements Plugin {
    @Override
    public String getName(){
        return "demo插件";
    }
    @Override
    public Node getContent(){
        // 创建插件的内容
        VBox content = new VBox();
        Label label = new Label("demo");
        content.getChildren().add(label);

        OkHttpClient client = new ProxyInternet().newClient();

        // 创建 HTTP 请求
        Request request = new Request.Builder()
                .url("https://www.baidu.com")
                .build();

        Response response = null;
        try {
            // 发送请求
            response = client.newCall(request).execute();
            // 处理响应
            System.out.println(response.body().string());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return  content;
    }
}

package plugins.Fofa.GUI;

import Internet.ProxyInternet;
import com.google.gson.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import plugins.Fofa.Config.GlobalConfig;
import plugins.Fofa.Util.Base64ED;
import plugins.Fofa.Util.FileIO;
import plugins.Fofa.Util.MsgBox;

import java.io.File;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

public class RootPage {

    BorderPane borderPaneRoot;

    VBox vBoxCenter;

    VBox vBoxSearch;

    VBox vBoxFunc;

    HBox hBoxBottom;

    GridPane gridPaneShowAndFunc;

    ScrollPane scrollPaneFunc;

    Label labelSearch;

    // 显示查询到了多少条数据
    Label labelIndex;

    TextField textFieldSearch;

    TextArea textAreaShow;

    Button buttonSearch;

    Button buttonSearchOutputTxt;
    Button buttonSearchOutputCsv;
    ProgressIndicator progressIndicator;

    String reqURL;
    String fieldStr;
    String csvTitle;


    public Node show(Stage primaryStage) {
        // 输入查询语句处
        labelSearch = new Label("请输入查询语句：");

        textFieldSearch = new TextField();
        textFieldSearch.setPrefWidth(10000);

        vBoxSearch = new VBox(10);
        vBoxSearch.setAlignment(Pos.CENTER_LEFT);
        vBoxSearch.getChildren().addAll(labelSearch, textFieldSearch);

        // 显示区
        textAreaShow = new TextArea();
        textAreaShow.setEditable(false);
        textAreaShow.setPromptText("提示：因为 java 控件性能羸弱，所以最多只能显示 1000 条信息 或 第一页。超过 1000 条，建议选择\"搜索并导出\"，虽然只展示 1000 条，但是会全部导出至指定位置。");
        // 功能区
        vBoxFunc = new VBox();
        vBoxFunc.getChildren().addAll(new RootPageAdvanced().show());

        scrollPaneFunc = new ScrollPane();
        scrollPaneFunc.setContent(vBoxFunc);

        // showAndFunc
        gridPaneShowAndFunc = new GridPane();
        gridPaneShowAndFunc.setHgap(10);
        gridPaneShowAndFunc.setAlignment(Pos.CENTER);
        ColumnConstraints col1 = new ColumnConstraints();
        ColumnConstraints col2 = new ColumnConstraints();
        col1.setHgrow(Priority.ALWAYS); // 第一列宽度自动适应
        col2.setHgrow(Priority.ALWAYS); // 第二列宽度自动适应
        col2.setMinWidth(300); // 第二列宽度为最小300
//        gridPaneShowAndFunc.getColumnConstraints().addAll(col1, col2);
        gridPaneShowAndFunc.add(textAreaShow, 0, 0);
        gridPaneShowAndFunc.add(scrollPaneFunc, 1, 0);
        // 创建行约束
        RowConstraints row = new RowConstraints();
        row.setVgrow(Priority.ALWAYS);
        row.setMinHeight(300);
        gridPaneShowAndFunc.getColumnConstraints().addAll(col1, col2);
        gridPaneShowAndFunc.getRowConstraints().add(row);


        // center
        vBoxCenter = new VBox(10);
        vBoxCenter.setPadding(new Insets(10));
        vBoxCenter.setAlignment(Pos.CENTER);
        vBoxCenter.getChildren().addAll(vBoxSearch, gridPaneShowAndFunc);


        // index label
        labelIndex = new Label("共 0 条 | 第 1 页");

        // 进度条
        progressIndicator = new ProgressIndicator();
        progressIndicator.setVisible(false);
        progressIndicator.setPrefSize(30, 30);

        // 按钮
        buttonSearch = new Button("搜索");
        buttonSearch.setMinWidth(100);
        buttonSearch.setOnAction(this::buttonSearchAction);

        buttonSearchOutputTxt = new Button("搜索并导出 txt");
        buttonSearchOutputTxt.setMinWidth(120);
        buttonSearchOutputTxt.setOnAction(event -> buttonSearchAction2txt(event, primaryStage));

        buttonSearchOutputCsv = new Button("搜索并导出 csv");
        buttonSearchOutputCsv.setMinWidth(120);
        buttonSearchOutputCsv.setOnAction(event -> buttonSearchAction2csv(event, primaryStage));

        // bottom
        hBoxBottom = new HBox(10);
        hBoxBottom.setPadding(new Insets(0, 10, 10, 10));
        hBoxBottom.setAlignment(Pos.CENTER);
        // 创建占位区
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        hBoxBottom.getChildren().addAll(labelIndex, spacer,progressIndicator, buttonSearchOutputTxt, buttonSearchOutputCsv, buttonSearch);

        borderPaneRoot = new BorderPane();
        borderPaneRoot.setCenter(vBoxCenter);
        borderPaneRoot.setBottom(hBoxBottom);

        return borderPaneRoot;
    }

    void buttonSearchAction2csv(ActionEvent event, Stage primaryStage) {
        if (saveOutPath(primaryStage) == -1) {
            return;
        }
        if ("".equals(GlobalConfig.CONFIG.getOutPath()) || GlobalConfig.CONFIG.getOutPath() == null) {
            return;
        }

        new Thread(() -> {
            closeButton();

            String savePath = GlobalConfig.CONFIG.getOutPath() + "/" + FileIO.getFileName4Time() + ".csv";

            // 写入表格第一行，标题
            csvTitle = "";
            GlobalConfig.CONFIG.getAdvanceds().get("fields").getAdvanceds().forEach((subkey, subvalue) -> {
                if (subvalue.getStatus()) {
                    csvTitle += subvalue.getName() + ",";
                }
            });
            if (csvTitle != null && csvTitle.endsWith(",")) {
                csvTitle = csvTitle.substring(0, csvTitle.length() - 1);
            }
            FileIO.writeFileA(savePath, csvTitle + "\n");

            for (int i = 0; i < Integer.parseInt(GlobalConfig.CONFIG.getAdvanceds().get("next").getValue()); i++) {

//                System.out.println(String.format("%d -> %s", i, GlobalConfig.CONFIG.getAdvanceds().get("next").getOtherValue()));

                if (i != 0 && (GlobalConfig.CONFIG.getAdvanceds().get("next").getOtherValue() == null ||
                        "".equals(GlobalConfig.CONFIG.getAdvanceds().get("next").getOtherValue()))) {
                    break;
                }
                JsonObject jsonData = sendReq();
                if (jsonData == null) {
                    return;
                }

                if (i == 0) {
                    new Thread(() -> showTextArea(jsonData)).start();
                }

                setLabelIndex(jsonData, i + 1);

                List<Object> results = getResults(jsonData);
                String content;
                for (Object result : results) {
                    content = "";
                    if (result instanceof String) {
                        content += result + "\n";
                    } else if (result instanceof List<?>) {
                        for (Object object : (List<?>) result) {
                            if (object instanceof String){
                                content += object + ",";
                            }
                        }
                        if (content != null && content.endsWith(",")) {
                            content = content.substring(0, content.length() - 1);
                        }
                        content += "\n";
                    }
                    FileIO.writeFileA(savePath, content);
                }
                // 判断查询页数功能是否开启
                if (!GlobalConfig.CONFIG.getAdvanceds().get("next").getStatus()) {
                    break;
                }
            }

            openButton();
        }).start();
    }

    void buttonSearchAction(ActionEvent event) {
        new Thread(() -> {
            closeButton();
            JsonObject jsonData = sendReq();
            if (jsonData == null) {
                return;
            }
            showTextArea(jsonData);
            openButton();
        }).start();
    }

    private void buttonSearchAction2txt(ActionEvent event, Stage primaryStage) {
        saveOutPath(primaryStage);
        if ("".equals(GlobalConfig.CONFIG.getOutPath()) || GlobalConfig.CONFIG.getOutPath() == null) {
            return;
        }

        new Thread(() -> {
            closeButton();

            String savePath = GlobalConfig.CONFIG.getOutPath() + "/" + FileIO.getFileName4Time() + ".txt";

            for (int i = 0; i < Integer.parseInt(GlobalConfig.CONFIG.getAdvanceds().get("next").getValue()); i++) {

//                System.out.println(String.format("%d -> %s", i, GlobalConfig.CONFIG.getAdvanceds().get("next").getOtherValue()));

                if (i != 0 && (GlobalConfig.CONFIG.getAdvanceds().get("next").getOtherValue() == null ||
                        "".equals(GlobalConfig.CONFIG.getAdvanceds().get("next").getOtherValue()))) {
                    break;
                }

                JsonObject jsonData = sendReq();
                if (jsonData == null) {
                    return;
                }

                if (i == 0) {
                    new Thread(() -> showTextArea(jsonData)).start();
                }

                setLabelIndex(jsonData, i + 1);

                List<Object> results = getResults(jsonData);

                for (Object result : results) {
                    if (result instanceof String) {
                        FileIO.writeFileA(savePath, result + "\n");
                    } else if (result instanceof List<?>) {
                        for (Object object : (List<?>) result) {
                            if (object instanceof String){
                                FileIO.writeFileA(savePath, object + "\n");
                            }
                        }
                        FileIO.writeFileA(savePath, "\n");
                    }
                }
                // 判断查询页数功能是否开启
                if (!GlobalConfig.CONFIG.getAdvanceds().get("next").getStatus()) {
                    break;
                }
            }

            openButton();
        }).start();
    }

    // 导出路径的方法
    int saveOutPath(Stage primaryStage) {
        if ("".equals(textFieldSearch.getText())) {
            MsgBox.sendSystemInfo("提示", "请输入查询语句！");
            return -1;
        }

        // 打开目录选择器
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("文件保存至...");
        if (GlobalConfig.CONFIG.getOutPath() != null && !GlobalConfig.CONFIG.getOutPath().isEmpty()) {
            // 设置上次使用过的路径
            directoryChooser.setInitialDirectory(new File(GlobalConfig.CONFIG.getOutPath()));
        }
        File selectedDirectory = directoryChooser.showDialog(primaryStage);
        if (selectedDirectory != null) {
            // 保存路径
            GlobalConfig.CONFIG.setOutPath(selectedDirectory.getPath());
            // 保存
            FileIO.saveObject(GlobalConfig.CONFIG_PATH, GlobalConfig.CONFIG);
//            System.out.printf(selectedDirectory.getPath());
        } else {
            return -1;
        }
        return 0;
    }

    // 发送请求
    JsonObject sendReq() {
        reqURL = String.format("%s?email=%s&key=%s&qbase64=%s",
//                GlobalConfig.GET_FOFA_SEARCH_ALL,
                GlobalConfig.GET_FOFA_SEARCH_NEXT,
                GlobalConfig.CONFIG.getEmail(),
                GlobalConfig.CONFIG.getKey(),
                Base64ED.encode(textFieldSearch.getText()));

        // 遍历 map
        GlobalConfig.CONFIG.getAdvanceds().forEach((key, value) -> {
            if ("next".equals(key) && value.getStatus()) {
                if (value.getOtherValue() == null || "".equals(value.getOtherValue())) {
                    return;
                }
                reqURL += String.format("&%s=%s", key, value.getOtherValue());
                value.setOtherValue("");
            } else if (value.getStatus()) {
                reqURL += String.format("&%s=%s", key, value.getValue());
            } else if ("fields".equals(key)) {
                fieldStr = "";
                GlobalConfig.CONFIG.getAdvanceds().get("fields").getAdvanceds().forEach((subkey, subvalue) -> {
                    if (subvalue.getStatus()) {
                        fieldStr += subkey + ",";
                    }
                });
                // 取出末尾的逗号
                if (fieldStr != null && fieldStr.endsWith(",")) {
                    fieldStr = fieldStr.substring(0, fieldStr.length() - 1);
                }
                if (!"".equals(fieldStr)) {
                    reqURL += String.format("&%s=%s", key, fieldStr);
                }
            }
        });

        // 发起请求
        OkHttpClient client = new ProxyInternet().newClient();
        // 创建 HTTP 请求
        Request request = new Request.Builder()
                .url(reqURL)
                .build();

        Response response = null;

        try {
            // 发送请求
            response = client.newCall(request).execute();
            //解析 json
            JsonElement element = JsonParser.parseString(response.body().string());
            JsonObject jsonData = element.getAsJsonObject();
            if (!jsonData.has("error")) {
                return null;
            } else {
                if ("true".equals(jsonData.get("error").getAsString())){
                    MsgBox.sendSystemInfo("error", jsonData.get("errmsg").getAsString());
                    openButton();
                    return null;
                }
            }
            if (jsonData.has("next")) {
                GlobalConfig.CONFIG.getAdvanceds().get("next").setOtherValue(jsonData.get("next").getAsString());
            }
            return jsonData;
        } catch (SocketTimeoutException e) {
            MsgBox.sendSystemInfo("提示", "请求超时，请重试！");
            openButton();
        } catch (IOException e) {
            e.printStackTrace();
            openButton();
        }
        return null;
    }
    
    void closeButton(){
        progressIndicator.setVisible(true);
        buttonSearch.setDisable(true);
        buttonSearchOutputTxt.setDisable(true);
        buttonSearchOutputCsv.setDisable(true);
    }
    
    void openButton(){
        progressIndicator.setVisible(false);
        buttonSearch.setDisable(false);
        buttonSearchOutputTxt.setDisable(false);
        buttonSearchOutputCsv.setDisable(false);
        GlobalConfig.CONFIG.getAdvanceds().get("next").setOtherValue("");
        FileIO.saveObject(GlobalConfig.CONFIG_PATH, GlobalConfig.CONFIG);
    }

    // 统计查询到的数量 并通过 label 展示出来
    void setLabelIndex(JsonObject jsonData, int page) {
        // 统计查询到的数量
        Platform.runLater(() -> {
            String sizeAll = jsonData.get("size").getAsString();
            int size = getResults(jsonData).size();
            labelIndex.setText(String.format("共 %s 条 | 第 %d 页", String.format("%d/%s",
                    size, sizeAll), page));
        });
    }

    List<Object> getResults(JsonObject jsonData) {
        JsonArray results = jsonData.getAsJsonObject().getAsJsonArray("results");

        List<Object> resultList = new ArrayList<>();

        if (results.get(0) instanceof JsonArray) {
            // 第一种结构，每个元素是一个数组
            for (JsonElement element : results) {
                JsonArray array = element.getAsJsonArray();
                List<String> subList = new ArrayList<>();
                for (JsonElement subElement : array) {
                    subList.add(subElement.getAsString());
                }
                resultList.add(subList);
            }
        } else if (results.get(0) instanceof JsonElement) {
            // 第二种结构，每个元素是一个字符串
            for (JsonElement element : results) {
                resultList.add(element.getAsString());
            }
        }
        return resultList;
    }

    // 搜索查询
    void showTextArea(JsonObject jsonData) {
        if (jsonData == null || "true".equals(jsonData.get("error").getAsString())) {
            return;
        }

        textAreaShow.clear();

        List<Object> results = getResults(jsonData);


        // 统计查询到的数量
        setLabelIndex(jsonData, 1);

        int index = 0;
        int indextmp = 0;
        for (Object result : results) {
            index++;
            if (index > 1000) {
                break;
            }
            if (result instanceof String) {
                indextmp++;
                Platform.runLater(() -> {
                    textAreaShow.appendText(result + "\n");
                });

            } else if (result instanceof List<?>) {
                for (Object object : (List<?>) result) {
                    if (object instanceof String) {
                        indextmp++;
                        Platform.runLater(() -> {
                            textAreaShow.appendText(object + "\n");
                        });
                    }
                }
                indextmp++;
                Platform.runLater(() -> {
                    textAreaShow.appendText("\n");
                });
            }
            if (indextmp > 200) {
                indextmp = 0;
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
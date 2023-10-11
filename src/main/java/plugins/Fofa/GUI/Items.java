package plugins.Fofa.GUI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import plugins.Fofa.Config.GlobalConfig;
import plugins.Fofa.DataType.Advanced;
import plugins.Fofa.Util.FileIO;

public class Items {


    public Node item(String title, String name) {
        CheckBox checkBox = new CheckBox(name);
        if (GlobalConfig.CONFIG.getAdvanceds().get(title) != null) {
            checkBox.setSelected(GlobalConfig.CONFIG.getAdvanceds().get(title).getStatus());
        } else {
            Advanced advanced = new Advanced();
            advanced.setName(name);
            GlobalConfig.CONFIG.getAdvanceds().put(title, advanced);
        }

        // 监听事件，用于保存状态
        checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            // 直接存储 newValue 的状态，他是 Bool 类型的
            GlobalConfig.CONFIG.getAdvanceds().get(title).setStatus(newValue);
            FileIO.saveObject(GlobalConfig.CONFIG_PATH, GlobalConfig.CONFIG);
        });

        HBox hBoxItem = new HBox(10);
        hBoxItem.getChildren().addAll(checkBox);
        hBoxItem.setAlignment(Pos.CENTER_LEFT);
        hBoxItem.setPadding(new Insets(5, 10, 5, 10));
        return hBoxItem;
    }

    public Node itemSpinner(String title, String name, Integer min, Integer max, Integer def, Integer step) {

        Spinner<Integer> spinner = new Spinner<>(min, max, def, step);
        spinner.setPrefWidth(75);
        spinner.setEditable(true);
        if (GlobalConfig.CONFIG.getAdvanceds().get(title) != null) {
            GlobalConfig.CONFIG.getAdvanceds().get(title).setValue(Integer.toString(def));
        } else {
            Advanced advanced = new Advanced();
            advanced.setName(name);
            advanced.setValue(Integer.toString(def));
            GlobalConfig.CONFIG.getAdvanceds().put(title, advanced);
        }
        spinner.valueProperty().addListener((observable, oldValue, newValue) -> {
            GlobalConfig.CONFIG.getAdvanceds().get(title).setValue(Integer.toString(newValue));
            FileIO.saveObject(GlobalConfig.CONFIG_PATH, GlobalConfig.CONFIG);
        });


        HBox hbox = (HBox) item(title, name);
        hbox.getChildren().add(spinner);
        return hbox;
    }

    public Node itemTextField(String title, String name, String value) {
        TextField textField = new TextField(value);
        textField.setPrefWidth(75);
        textField.textProperty().addListener(((observable, oldValue, newValue) -> {
            GlobalConfig.CONFIG.getAdvanceds().get(title).setValue(newValue);
            FileIO.saveObject(GlobalConfig.CONFIG_PATH, GlobalConfig.CONFIG);
        }));

        HBox hbox = (HBox) item(title, name);
        hbox.getChildren().add(textField);
        return hbox;
    }

    public Node itemTitledPane(String title, String name, Node node) {
        TitledPane titledPane = new TitledPane(name, node);
        titledPane.setCollapsible(true); // 允许崩溃
        titledPane.setExpanded(Boolean.parseBoolean(GlobalConfig.CONFIG.getAdvanceds().get("fields").getOtherValue()));     // 设置默认是否展开
        titledPane.setOnMouseClicked(event -> {
            GlobalConfig.CONFIG.getAdvanceds().get(title).setOtherValue(String.valueOf(titledPane.isExpanded()));
            FileIO.saveObject(GlobalConfig.CONFIG_PATH, GlobalConfig.CONFIG);
        });

        VBox vBox = new VBox(10);
        vBox.setPadding(new Insets(5, 10, 5, 10));
        vBox.getChildren().addAll(titledPane);
        return vBox;
    }

    public Node itemSub(String title, String name) {
        CheckBox checkBox = new CheckBox(name);
        if (GlobalConfig.CONFIG.getAdvanceds().get("fields").getAdvanceds().get(title) != null) {
            checkBox.setSelected(GlobalConfig.CONFIG.getAdvanceds().get("fields").getAdvanceds().get(title).getStatus());
        } else {
            Advanced advanced = new Advanced();
            advanced.setName(name);
            GlobalConfig.CONFIG.getAdvanceds().get("fields").getAdvanceds().put(title, advanced);
        }

        // 监听事件，用于保存状态
        checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            // 直接存储 newValue 的状态，他是 Bool 类型的
            GlobalConfig.CONFIG.getAdvanceds().get("fields").getAdvanceds().get(title).setStatus(newValue);
            FileIO.saveObject(GlobalConfig.CONFIG_PATH, GlobalConfig.CONFIG);
        });

        HBox hBoxItem = new HBox(10);
        hBoxItem.getChildren().addAll(checkBox);
        hBoxItem.setAlignment(Pos.CENTER_LEFT);
        hBoxItem.setPadding(new Insets(5, 10, 5, 10));
        return hBoxItem;
    }
}

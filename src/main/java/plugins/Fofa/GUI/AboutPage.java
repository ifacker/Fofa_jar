package plugins.Fofa.GUI;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import plugins.Fofa.Config.GlobalConfig;
import plugins.Fofa.Util.MsgBox;

public class AboutPage {

    VBox vboxRoot;

    Label labelName;
    Label labelVersion;
    Label labelAuthor;
    Label labelLink1;
    Label labelLink2;
    Label labelLink3;

    public Node show(){

        labelName = new Label("Name: Fofa");
        labelVersion = new Label(String.format("Version: %s", GlobalConfig.VERSION));
        labelAuthor = new Label("Author: ifacker");
        labelLink1 = new Label("https://github.com/ifacker");
        labelLink2 = new Label("https://github.com/ifacker/ToolsKing");
        labelLink3 = new Label("https://github.com/ifacker/Fofa_jar");

        copy(labelAuthor);
        copy(labelLink1);
        copy(labelLink2);
        copy(labelLink3);

        vboxRoot = new VBox(10);
        vboxRoot.setAlignment(Pos.CENTER);
        vboxRoot.getChildren().addAll(labelName, labelVersion, labelAuthor, labelLink1, labelLink2, labelLink3);
        return vboxRoot;
    }

    private void copy(Label label) {
        label.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                Clipboard clipboard = Clipboard.getSystemClipboard();
                ClipboardContent content = new ClipboardContent();
                content.putString(label.getText());
                clipboard.setContent(content);
                MsgBox.sendSystemInfo("提示", String.format("复制成功：%s", label.getText()));
            }
        });
    }
}

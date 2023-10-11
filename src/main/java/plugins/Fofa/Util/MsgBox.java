package plugins.Fofa.Util;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.controlsfx.control.Notifications;

import java.util.function.Consumer;

public class MsgBox {
    /**
     * 展示二次确认信息，使用方法：
     * showConfiramtion("title", "message", response -> {
     * if (response == ButtonType.OK) {
     * System.out.println("用户点击了确定按钮。");
     * } else {
     * System.out.println("用户点击了取消按钮。");
     * }
     * });
     *
     * @param title
     * @param message
     * @param action  传入的方法
     */
    public static void showConfirmation(String title, String message, Consumer<ButtonType> action) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(title);
            alert.setContentText(message);
            alert.showAndWait().ifPresent(action);
        });
    }

    public static void sendSystemInfo(String title, String text) {
        Platform.runLater(() -> {
            Notifications.create().title(title).text(text).showInformation();
        });
    }
}

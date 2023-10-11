package plugins.Fofa;

import Plugin.Plugin;
import javafx.scene.Node;
import javafx.stage.Stage;
import plugins.Fofa.GUI.Start;


public class Main implements Plugin {
    @Override
    public String getName() {
        return "Fofa";
    }

    @Override
    public Node getContent(Stage primaryStage) {
        return new Start().show(primaryStage);
    }
}

package uireturns.controllers;

import com.filemanager.ReadSpreadSheet;
import com.filemanager.ReaderFactory;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.StatusBar;

public class AppController {
    //FXML variables
    public AnchorPane container;
    //Parser Library Variables
    static ReaderFactory readerFactory = new ReaderFactory();
    static ReadSpreadSheet reader = null;
    //Status Bar
    static StatusBar statusBar = new StatusBar();

    //Util Variables
    static BooleanProperty indexing = new SimpleBooleanProperty(false);

    public void initialize(){
        addStatusBar();
    }

    private void addStatusBar(){
        AnchorPane.setBottomAnchor(statusBar, 0.0);
        AnchorPane.setLeftAnchor(statusBar, 0.0);
        AnchorPane.setRightAnchor(statusBar, 0.0);
        container.getChildren().add(statusBar);
    }
}

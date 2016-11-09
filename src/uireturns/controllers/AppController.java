package uireturns.controllers;

import com.FastSearch.FastSearch;
import com.filemanager.ReadSpreadSheet;
import com.filemanager.ReaderFactory;
import com.xpathgenerator.Tag;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.controlsfx.control.StatusBar;

public class AppController {
    //FXML variables
    public AnchorPane container;
    //Parser Library Variables
    static FastSearch fastSearch = new FastSearch();
    static ReaderFactory readerFactory = new ReaderFactory();
    static ReadSpreadSheet reader = null;
    static Tag tag = new Tag();
    //Status Bar
    static StatusBar statusBar = new StatusBar();

    //Util Variables
    static BooleanProperty indexing = new SimpleBooleanProperty(false);
    public VBox logicBox;

    @FXML
     bottomPaneController bottomPaneController;
    @FXML
     MenuBarController menuBarController;
    @FXML
     outputReportController outputReportController;
    @FXML
    projectConfigController projectConfigController;
    @FXML
     spreadsheetController spreadsheetController;
    @FXML
     tagsController tagsController;

    public void initialize(){
        bottomPaneController.init(this);
        menuBarController.init(this);
        projectConfigController.init(this);
        outputReportController.init(this);
        spreadsheetController.init(this);
        tagsController.init(this);
        addStatusBar();
    }

    private void addStatusBar(){
        AnchorPane.setBottomAnchor(statusBar, 0.0);
        AnchorPane.setLeftAnchor(statusBar, 0.0);
        AnchorPane.setRightAnchor(statusBar, 0.0);
        container.getChildren().add(statusBar);
    }
}

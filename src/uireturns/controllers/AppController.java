package uireturns.controllers;

import com.FastSearch.FastSearch;
import com.filemanager.ReadSpreadSheet;
import com.filemanager.ReaderFactory;
import com.xpathgenerator.Tag;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.StatusBar;

/**
 * <h1>AppController</h1>
 * <p>This is the base Controller class for the whole UI Screen. All other controllers of other included  components of FXML,
 * are initialized with Appcontroller instance from here.</p>
 * @author Devansh and Avinash
 * @since 2016-11-14
 */
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
    public Accordion leftpane;

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
    @FXML
    logicController logicController;

    public void initialize(){

        /**
         * All the other controllers are being passed with the instance of this controller.
         */
        bottomPaneController.init(this);
        menuBarController.init(this);
        logicController.init(this);
        projectConfigController.init(this);
        outputReportController.init(this);
        spreadsheetController.init(this);
        tagsController.init(this);
        addStatusBar();
        logicController.addCsvView(false);
    }

    private void addStatusBar(){
        AnchorPane.setBottomAnchor(statusBar, 0.0);
        AnchorPane.setLeftAnchor(statusBar, 0.0);
        AnchorPane.setRightAnchor(statusBar, 0.0);
        container.getChildren().add(statusBar);
    }
}

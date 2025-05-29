module team.group.myforbidden {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.pdfbox;
    requires javafx.swing;

    opens team.group.myforbidden.view to javafx.fxml;
    opens team.group.myforbidden.controller to javafx.fxml;

    exports team.group.myforbidden.view;
    exports team.group.myforbidden.controller to javafx.fxml;
    exports team.group.myforbidden.model;
}

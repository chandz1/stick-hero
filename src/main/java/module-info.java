module stickhero.stickhero {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires transitive javafx.graphics;
    requires annotations;

    exports stickhero;

    opens stickhero to javafx.fxml;
}

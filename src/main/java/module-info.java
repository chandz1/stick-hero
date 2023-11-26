module stickhero.stickhero {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires transitive javafx.graphics;

    exports stickhero;

    opens stickhero to javafx.fxml;
}

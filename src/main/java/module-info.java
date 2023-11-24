module stickhero.stickhero {
    requires javafx.controls;
    requires javafx.fxml;

    requires transitive com.almasb.fxgl.all;

    exports stickhero;

    opens stickhero to javafx.fxml;
}

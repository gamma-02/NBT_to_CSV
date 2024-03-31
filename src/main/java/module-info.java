module net.gneisscode.nbttocsv {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires com.google.common;
    requires datafixerupper;
    requires it.unimi.dsi.fastutil;
    requires jsr305;
    requires org.apache.commons.lang3;
    requires NBT;
    requires com.opencsv;
    requires java.desktop;

    opens net.gneisscode.nbttocsv to javafx.fxml;
    exports net.gneisscode.nbttocsv;
    exports net.gneisscode.nbttocsv.utils;
    exports net.gneisscode.nbttocsv.JavaFXThings;
    opens net.gneisscode.nbttocsv.JavaFXThings to javafx.fxml;
}
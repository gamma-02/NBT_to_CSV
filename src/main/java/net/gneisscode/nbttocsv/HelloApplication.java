package net.gneisscode.nbttocsv;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import net.gneisscode.nbttocsv.utils.Util;
import net.querz.nbt.tag.*;
import org.kordamp.bootstrapfx.BootstrapFX;

public class HelloApplication extends Application {

    public static CompoundTag NBTFile = new CompoundTag();

    public static CopyOnWriteArrayList<BlockContainer> BLOCKS = new CopyOnWriteArrayList<>();


    @Override
    public void start(Stage stage) throws IOException, URISyntaxException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), -1, -1);


        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());

        scene.getStylesheets().add(HelloApplication.class.getResource("default-thingies.css").toExternalForm());


        stage.setScene(scene);

        stage.setTitle("Gneiss's NBT structure utilities");
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }


    public static CompoundTag readFile(Path path) throws IOException {
        return Util.readCompressed(path.toFile());
    }


    public static void readAllBlocks(CompoundTag structure){
        if(structure.containsKey("blocks")){
            ListTag<CompoundTag> blocks = structure.getListTag("blocks").asCompoundTagList();
            //iterate over all of the blocks in the tag and add the blockContainer for each
            for (int i = 0; i < blocks.size(); i++) {
                BLOCKS.add(new BlockContainer(i));
            }
        }
    }
}
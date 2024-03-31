package net.gneisscode.nbttocsv;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

import net.gneisscode.nbttocsv.utils.Util;
import net.querz.nbt.tag.*;
import org.kordamp.bootstrapfx.BootstrapFX;

public class HelloApplication extends Application {

    public static CompoundTag NBTFile = null;

    public static ArrayList<BlockContainer> BLOCKS = new ArrayList<>();


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 450, 350);


        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());



        stage.setScene(scene);

        stage.setTitle("NBT to CSV");
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
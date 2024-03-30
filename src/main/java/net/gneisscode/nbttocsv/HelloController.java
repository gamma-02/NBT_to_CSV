package net.gneisscode.nbttocsv;

import com.opencsv.CSVWriter;
import com.opencsv.CSVWriterBuilder;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import net.querz.nbt.tag.CompoundTag;
import org.controlsfx.control.ToggleSwitch;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    public Label outputCsvButton;
    @FXML
    public ToggleSwitch writeAir;
    @FXML
    public TextField csvPathInput;
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
//        welcomeText.setText("Welcome to JavaFX Application!");
        String path = inputArea.getText();

        Path filePath = FileSystems.getDefault().getPath(path);

        if(!Files.exists(filePath))
            welcomeText.setText("File not found!");

        try {
            CompoundTag NBTFile = HelloApplication.readFile(filePath);
            welcomeText.setText("Processing input!");
            HelloApplication.NBTFile = NBTFile;
        } catch (IOException e) {
            welcomeText.setText("Input errored!");
            throw new RuntimeException(e);
        }

        HelloApplication.readAllBlocks(HelloApplication.NBTFile);
        welcomeText.setText("Done!");

    }

    @FXML
    protected void onOutputClick(){
        if(HelloApplication.BLOCKS.isEmpty())
            return;

        for(BlockContainer block : HelloApplication.BLOCKS){
            block.resolve();
            if(!writeAir.isSelected())
                HelloApplication.BLOCKS.remove(block);
        }

        FileWriter outputFile = null;
        try {

            outputFile = new FileWriter(FileSystems.getDefault().getPath(csvPathInput.getText()).toFile());
        } catch (IOException e) {
            outputCsvButton.setText("Path invalid!");
            throw new RuntimeException(e);
        }

        CSVWriter outputWriter = (CSVWriter) new CSVWriterBuilder(outputFile).build();
        ArrayList<String> header = new ArrayList<>(List.of(new String[]{"X", "Y", "Z", "Block id", "NBT data", "Properties..."}));

        int longestRow = 0;

        ArrayList<String[]> lines = new ArrayList<>();
        for(BlockContainer b : HelloApplication.BLOCKS){

            ArrayList<String> row = b.getCsvRow();


            String[] rowArr = row.toArray(new String[0]);
            if(rowArr.length > longestRow)
                longestRow = rowArr.length;

            lines.add(rowArr);
        }
        while(header.size() < longestRow){
            header.add(" ");
        }
        outputWriter.writeNext(header.toArray(new String[0]));
        outputWriter.writeAll(lines);

        outputCsvButton.setText("Successfully written!");

    }

    @FXML
    private TextField inputArea;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        outputCsvButton.setText("Output controls");
    }
}
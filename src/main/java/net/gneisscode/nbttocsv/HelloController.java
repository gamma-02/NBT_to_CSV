package net.gneisscode.nbttocsv;

import com.opencsv.CSVWriter;
import com.opencsv.CSVWriterBuilder;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import net.gneisscode.nbttocsv.JavaFXThings.NumericInputField;
import net.gneisscode.nbttocsv.utils.Vec3i;
import net.querz.nbt.tag.CompoundTag;
import org.apache.commons.lang3.StringUtils;
import org.controlsfx.control.ToggleSwitch;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    public TabPane fullPanel;



    // NBT input tab

    @FXML
    private Label welcomeText;
    @FXML
    private TextField inputArea;
    @FXML
    public Button inputButton;
    @FXML
    protected void onHelloButtonClick() {
//        welcomeText.setText("Welcome to JavaFX Application!");
        String path = inputArea.getText();

        Path filePath = FileSystems.getDefault().getPath(path.replace("\"", ""));

        if(!Files.exists(filePath))
            welcomeText.setText("File not found!");

        //clear the tag list and the blocks list if they aren't empty
        if(!HelloApplication.NBTFile.values().isEmpty())
            HelloApplication.NBTFile.clear();

        if(!HelloApplication.BLOCKS.isEmpty())
            HelloApplication.BLOCKS.clear();

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



    //CSV output tab

    @FXML
    public Label outputCsvInfo;
    @FXML
    public ToggleSwitch writeAir;
    @FXML
    public TextField csvPathInput;
    @FXML
    public Label outputCsvTitle;
    @FXML
    protected void onOutputClick(){
        if(HelloApplication.BLOCKS.isEmpty())
            return;

        ArrayList<BlockContainer> blocks = new ArrayList<>();

        for(BlockContainer block : HelloApplication.BLOCKS){
            block.resolve();
            if(!writeAir.isSelected() && !block.location.equals("minecraft:air"))
                blocks.add(block);
        }

        FileWriter outputFile = null;
        try {

            outputFile = new FileWriter(FileSystems.getDefault().getPath(csvPathInput.getText()).toFile());
        } catch (IOException e) {
            outputCsvInfo.setText("Path invalid!");
            throw new RuntimeException(e);
        }

        CSVWriter outputWriter = (CSVWriter) new CSVWriterBuilder(outputFile).build();
        ArrayList<String> header = new ArrayList<>(List.of(new String[]{"X", "Y", "Z", "Block id", "NBT data", "Properties..."}));

        int longestRow = 0;

        ArrayList<String[]> lines = new ArrayList<>();
        for(BlockContainer b : blocks){

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

        outputCsvInfo.setText("Successfully written!");

//        HelloApplication.BLOCKS.clear();//Clear the list of blocks
//        HelloApplication.NBTFile = new CompoundTag();//clear the NBT tag

        try {
            outputWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    @FXML
    public Label outputSetblocksTitle;

    @FXML
    public ToggleSwitch writeBlockstateListAir;

    @FXML
    public TextArea setblockListOutput;

    @FXML
    public TextField cornerPos;
    @FXML
    public TextField cornerPosX;
    @FXML
    public TextField cornerPosY;
    @FXML
    public TextField cornerPosZ;

    @FXML
    protected void onSetblockOutputClick() {
        if(HelloApplication.BLOCKS.isEmpty())
            return;

        //resolve blocks and handle air functionality
        ArrayList<BlockContainer> blocks = new ArrayList<>();

        for(BlockContainer block : HelloApplication.BLOCKS){
            block.resolve();
            if(!writeAir.isSelected() && !block.location.equals("minecraft:air"))
                blocks.add(block);
        }

        StringBuilder setblocks = new StringBuilder();

        for(BlockContainer block : blocks){
            setblocks.append(FunctionConverter.getPlaceBlockCommand(block, new Vec3i(cornerPosX.getText().isEmpty() ? 0 : Integer.parseInt(cornerPosX.getText()), cornerPosY.getText().isEmpty() ? 0 : Integer.parseInt(cornerPosY.getText()), cornerPosZ.getText().isEmpty() ? 0 : Integer.parseInt(cornerPosZ.getText())))).append("\n");
        }

        setblockListOutput.setText(new String(setblocks));



    }


    //Function output tab

//    @FXML
//    private Label WIP;
    @FXML
    public Label outputFunctionTitle;
    @FXML
    public Label outputFunctionInfo;
    @FXML
    public TextField functionNameInput;
    @FXML
    public TextField functionOutputLocation;
    @FXML
    public ToggleSwitch writeFunctionToZip;
    @FXML
    public TextField lengthOfExecution;
    @FXML
    public TextField buildDirection;

    public void outputFunction() {

        String buildStartFunction = FunctionConverter.getStructureBuildStartFunction(functionNameInput.getText());
        System.out.println(buildStartFunction);


    }









    private ChangeListener<Boolean> listener;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fullPanel.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent;");

//        inputButton.getStyleClass().setAll("btn","btn-danger");
        inputArea.setPrefHeight(30);
        csvPathInput.setPrefHeight(30);

        lengthOfExecution.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {

                    lengthOfExecution.setText(newValue.replaceAll("\\D", ""));

                }
            }
        });

        cornerPosX.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {

                    cornerPosX.setText(newValue.replaceAll("\\D", ""));

                }
            }
        });

        setblockListOutput.setEditable(false);

        listener = new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                writeAir.setText("Write air states to CSV: " + (newValue ? "Yes" : "No"));

            }

        };
        writeAir.selectedProperty().addListener(listener);

    }

    public void onFunctionTabSelected(Event event) {


    }



}
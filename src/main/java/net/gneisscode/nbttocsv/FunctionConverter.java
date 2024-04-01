package net.gneisscode.nbttocsv;

import net.gneisscode.nbttocsv.utils.BlockPos;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FunctionConverter {
    /**
     * index 1: block x<br>
     * index 2: block y<br>
     * index 3: block z<br>
     * index 4: block id<br>
     * index 5: block state<br>
     * index 6: block NBT(optional)<br>
     * index 7: optional
     */
    public static final String PLACE_BLOCK_COMMAND = "setblock $7%s$1%d $7%s$2%d $7%s$3%d $4%s$5%s$6%s";


    /**
     * index 1: random block place delay
     * index 2: the name of the structure/function so multiple can execute at once
     */
    public static final String EXECUTE_COMMAND = "execute if score Dataman count matches $1%d run execute at @e[tag=auto_$2%s,limit=1] run ";


    /**
     * This method formats the {@link #PLACE_BLOCK_COMMAND} string into a minecraft readable command
     * @param fromBlock The block to be converted
     * @return The formatted string
     */
    public static String getPlaceBlockCommand(BlockContainer fromBlock, boolean relative){
        BlockPos pos = fromBlock.pos;

        HashMap<String, String> props = fromBlock.properties;
        StringBuilder builder = new StringBuilder("[");
        props.forEach((name, value) ->{
            builder.append(name).append("=").append(value);
        });
        builder.append("]");

        return PLACE_BLOCK_COMMAND.formatted(pos.getX(), pos.getY(), pos.getZ(), fromBlock.location, builder.toString(), fromBlock.nbt, (relative ? "~" : ""));
    }

    /**
     * This method formats and concats the execute command and place block command
     * @param fromBlock
     * @param relative
     * @param delay
     * @param functionName
     * @return formatted function
     */
    public static String getExecuteCommand(BlockContainer fromBlock, boolean relative, int delay, String functionName){
        return EXECUTE_COMMAND.formatted(delay, functionName).concat(getPlaceBlockCommand(fromBlock, relative));
    }


    /**
     * Formats the default start function with the function name
     * @param functionName The name of the function as specified
     * @return The formatted function file
     */
    public static String getStructureBuildStartFunction(String functionName){
        URL defaultFunction = FunctionConverter.class.getResource("build_start.txt");
        if (defaultFunction == null) {
            return "ERROR";
        }
        Path defaultFunctionPath;
        try {

            defaultFunctionPath = Path.of(defaultFunction.toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        List<String> function;
        try {
            function = Files.readAllLines(defaultFunctionPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        StringBuilder finalBuilder = new StringBuilder();
        for (int i = 0; i < function.size(); i++) {
            finalBuilder.append(function.get(i).formatted(functionName)).append('\n');
        }
        return new String(finalBuilder);

    }


    public static List<String> getStructureEndFunctionFragment(String functionName, int executionLength){

        URL defaultFunction = FunctionConverter.class.getResource("build_end.txt");
        if (defaultFunction == null) {
            return List.of("ERROR");
        }
        Path defaultFunctionPath;
        try {

            defaultFunctionPath = Path.of(defaultFunction.toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        List<String> function;
        try {
            function = Files.readAllLines(defaultFunctionPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ArrayList<String> finalBuilder = new ArrayList<>();
        for (int i = 0; i < function.size(); i++) {
            finalBuilder.add(function.get(i).formatted(functionName, executionLength, executionLength - 1));
        }

        return finalBuilder;
    }







}

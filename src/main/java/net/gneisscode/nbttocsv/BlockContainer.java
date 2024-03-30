package net.gneisscode.nbttocsv;

import com.google.common.base.Strings;
import com.google.common.primitives.Ints;
import net.gneisscode.nbttocsv.utils.BlockPos;
import net.querz.nbt.tag.CompoundTag;
import net.querz.nbt.tag.IntArrayTag;
import net.querz.nbt.tag.IntTag;
import net.querz.nbt.tag.ListTag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;

public class BlockContainer {
    public HashMap<String, String> properties = new HashMap<>();
    public String nbt = "";
    public String location;
    public BlockPos pos;
    int pallateIndex = Integer.MIN_VALUE;
    int blockIndex;

    public BlockContainer(int index){
        this.blockIndex = index;
    }


    /**
     * This method retrieves the data of the block represented by this object and sets the various fields.
     */
    public void resolve(){
        CompoundTag nbtFile = HelloApplication.NBTFile;


        //Getting the block and pallate lists
        ListTag<CompoundTag> blockList = nbtFile.getListTag("blocks").asCompoundTagList();
        ListTag<CompoundTag> pallate = nbtFile.getListTag("palette").asCompoundTagList();

        //get the compound tag representing our block
        CompoundTag blockEntry = blockList.get(this.blockIndex);


        //set the position of our block
        if(blockEntry.containsKey("pos")){
            ListTag<IntTag> posTag = blockEntry.getListTag("pos").asIntTagList();
            this.pos = new BlockPos(posTag.get(0).asInt(), posTag.get(1).asInt(), posTag.get(2).asInt());
        }


        //set the index of our block in the pallate
        if(blockEntry.containsKey("state")){
            IntTag stateIndex = blockEntry.getIntTag("state");
            int state = stateIndex.asInt();
            this.pallateIndex = state;
        }

        //set the NBT of the block(entity)
        if(blockEntry.containsKey("nbt")){
            CompoundTag nbtTag = blockEntry.getCompoundTag("nbt");
            this.nbt = nbtTag.valueToString();
        }

        //get and set the values stored in the pallate
        if(pallateIndex != Integer.MIN_VALUE){
            CompoundTag pallateEntry = pallate.get(pallateIndex);
            //set the registry ID of the block
            if(pallateEntry.containsKey("Name")){
                this.location = pallateEntry.getString("Name");
            }

            //set the blockstate properties
            if(pallateEntry.containsKey("Properties")){
                CompoundTag properties = pallateEntry.getCompoundTag("Properties");

                properties.forEach((name, tag) -> this.properties.put(name, tag.valueToString()));
            }
        }



    }

    public ArrayList<String> getCsvRow(){
        ArrayList<String> List = new ArrayList<>();

        List.addAll(Arrays.asList(this.pos.getXYZ()));
        List.add(this.location);
        List.add(this.nbt);
        this.properties.forEach((name, value) -> List.add(name+'='+value));

        return List;
    }




}

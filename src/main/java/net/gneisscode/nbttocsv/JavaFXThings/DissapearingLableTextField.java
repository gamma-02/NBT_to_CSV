package net.gneisscode.nbttocsv.JavaFXThings;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class DissapearingLableTextField extends TextField {
    DissapearingLableTextField(){
        this("");
    }

    DissapearingLableTextField(String text){
        super(text);


    }


}

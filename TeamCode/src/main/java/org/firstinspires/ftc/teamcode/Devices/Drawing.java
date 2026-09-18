package org.firstinspires.ftc.teamcode.Devices;

import com.bylazar.field.FieldManager;
import com.bylazar.field.PanelsField;
import com.bylazar.field.Style;

import java.util.UUID;


public class Drawing {
    private static final FieldManager field = PanelsField.INSTANCE.getField();
    private static final UUID image = field.registerImage("TeamCode/src/main/res/Images/BioBuzz.png");

    public static void main(){
        field.setOffsets(PanelsField.INSTANCE.getPresets().getDEFAULT_FTC());

        field.setStyle("red", "blue",2);

        field.setStyle(new Style("red", "blue", 2));

        field.moveCursor(0,0);

        field.img(10,10,image);

    }



}

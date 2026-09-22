package org.firstinspires.ftc.teamcode.Simulations;

import com.bylazar.field.FieldManager;
import com.bylazar.field.PanelsField;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import java.util.UUID;

@TeleOp
public class QuinticSplineSim extends OpMode {

    FieldManager field = PanelsField.INSTANCE.getField();
    UUID image = field.registerImage("TeamCode/images/BioBuzz.png");
    @Override
    public void init() {
        field.setOffsets(PanelsField.INSTANCE.getPresets().getDEFAULT_FTC());
        field.img(10,10,image);
    }

    @Override
    public void loop() {

    }
}

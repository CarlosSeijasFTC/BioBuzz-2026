package org.firstinspires.ftc.teamcode.Opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Devices.Devices;

@TeleOp
public class Tele extends OpMode {

    boolean wasA2;

    int intake;

    Devices hw = new Devices();
    @Override
    public void init() {
        hw.init(hardwareMap);
    }

    @Override
    public void loop() {
        hw.driveField(gamepad1.left_stick_x, -gamepad1.left_stick_y, gamepad1.right_stick_x);

        if(gamepad2.a && !wasA2){
            intake ++;
        }

        hw.intake(intake);

        wasA2 = gamepad2.a;
    }
}

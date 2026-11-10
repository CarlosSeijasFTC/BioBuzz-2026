package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Devices.Devices;

@TeleOp
public class DriveTest extends OpMode {

    Devices hw = new Devices();
    boolean wasRB1 = false;
    int drive;

    @Override
    public void init() {
        hw.init(hardwareMap);
    }

    @Override
    public void loop() {
        if(gamepad1.right_bumper && !wasRB1){
            drive ++;
        }

        if(drive % 2 == 0) {
            hw.drive(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
            telemetry.addData("Drive", "Bot Centric");
        }
        else {
            hw.driveField(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
            telemetry.addData("Drive", "Field Centric");
        }

        telemetry.addLine(hw.motorStatus());
        telemetry.addLine(hw.controller1Status(gamepad1));


        wasRB1 = gamepad1.right_bumper;
    }
}

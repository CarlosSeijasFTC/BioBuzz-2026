package org.firstinspires.ftc.teamcode.Opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Devices.Devices;
import org.firstinspires.ftc.teamcode.Devices.Odometry;

@TeleOp
public class Tele extends OpMode {

    boolean wasA2;
    boolean wasRS1;
    boolean wasLS1;

    int intake;

    Odometry odo = new Odometry();

    Devices hw = new Devices();
    @Override
    public void init() {
        hw.init(hardwareMap);
    }

    @Override
    public void loop() {
        hw.driveField(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
       odo.update(hw.getRightEncTicks(), hw.getLeftEncTicks(), hw.getNormalEncTicks(), hw.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS));

        telemetry.addLine(odo.toString());

        telemetry.addData("rightEnc", hw.getRightEncTicks());
        telemetry.addData("leftEnc", hw.getLeftEncTicks());
        telemetry.addData("normalEnc", hw.getNormalEncTicks());

        if(gamepad2.a && !wasA2){
            intake ++;
        }
        if(gamepad1.right_stick_button && !wasRS1){
            hw.imu.resetYaw();
        }
        if (gamepad1.left_stick_button && !wasLS1){
            odo.resetOdometry();
        }


        hw.intake(intake);

        wasA2 = gamepad2.a;
        wasRS1 = gamepad1.right_stick_button;
        wasLS1 = gamepad1.left_stick_button;
    }
}

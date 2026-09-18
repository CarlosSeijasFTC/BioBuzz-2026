package org.firstinspires.ftc.teamcode.Opmodes;

import com.bylazar.field.PanelsField;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Devices.Devices;
import org.firstinspires.ftc.teamcode.Devices.Odometry;

import java.util.List;

@TeleOp
public class Tele extends OpMode {

    boolean wasA2;
    boolean wasRS1;
    boolean wasLS1;

    int intake;

    Odometry odo = new Odometry();

   List<LynxModule> hubs;

    Devices hw = new Devices();


    @Override
    public void init() {
        hw.init(hardwareMap);
        hubs = hardwareMap.getAll(LynxModule.class);

        for(LynxModule hub : hubs){
            hub.setBulkCachingMode(LynxModule.BulkCachingMode.MANUAL);
        }
        for(LynxModule hub : hubs){
            hub.clearBulkCache();
        }
    }

    @Override
    public void loop() {
        for(LynxModule hub : hubs){
            hub.clearBulkCache();
        }

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

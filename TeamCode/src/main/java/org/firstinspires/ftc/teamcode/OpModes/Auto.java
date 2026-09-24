package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Devices.Devices;
import org.firstinspires.ftc.teamcode.Devices.GVFNavigation;
import org.firstinspires.ftc.teamcode.Devices.Odometry;
import org.firstinspires.ftc.teamcode.Devices.QuinticSpline;


@Autonomous
public class Auto extends OpMode {
    Devices hw = new Devices();
    Odometry odo = new Odometry();
    GVFNavigation navigation  = new GVFNavigation();
    ElapsedTime time = new ElapsedTime();
    double[][] initialPose = {
            {0,0},
            {0,0},
            {0,0}
    };

    double[][] finalPose = {
            {150,100},
            {0,0},
            {0,0}
    };

    QuinticSpline movement = new QuinticSpline(initialPose, finalPose);

    @Override
    public void init() {
        hw.init(hardwareMap);
    }

    @Override
    public void start(){
        time.reset();
    }


    @Override
    public void loop() {
        if(time.milliseconds() > 2000 && time.milliseconds() < 5000){
            hw.driveField(navigation.calculateGuidanceVector(movement,odo.get2DPose()).getX(), navigation.calculateGuidanceVector(movement,odo.get2DPose()).getY(), 0);
            odo.update(hw.getRightEncTicks(), hw.getLeftEncTicks(), hw.getNormalEncTicks(), hw.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS));
            telemetry.addLine(odo.toString());
        }
        else {
            hw.driveField(0,0,0);
        }
        telemetry.addData("Direction", navigation.calculateGuidanceVector(movement,odo.get2DPose()).toString());
    }
}

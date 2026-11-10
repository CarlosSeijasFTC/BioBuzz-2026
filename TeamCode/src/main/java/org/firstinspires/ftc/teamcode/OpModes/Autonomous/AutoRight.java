package org.firstinspires.ftc.teamcode.OpModes.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Devices.Devices;

public class AutoRight extends LinearOpMode {
    Devices hw = new Devices();
    @Override
    public void runOpMode() throws InterruptedException {
        hw.init(hardwareMap);
        telemetry.addData("Status", "Init Done!");
        telemetry.update();

        waitForStart();

        //*****    instructions   ******

        //Time is in milliseconds.
        //Speed is -1,1.
        //Angles are -180,180.
        //Negative speed indicate reverse action (e.g. moveForward(-1,1000) will move backward for 1 second)

        /*Possible commands
            moveForward(speed, time)
            strafeRight(speed, time)
            turnAngle(angle)
            turn(speed, time)
         */

        /*Objectives:
            1. Leave Initial state by completely stick out of wallAutoRight
            2. During last second of the auto part, partially park in the parking spot
         */


    }

    public void moveForward(double speed, double time){
        hw.drive(speed, 0, 0);
        telemetry.addData("Forward", "\nspeed: " + speed + "\nTime: " + time);
        telemetry.update();
        sleep((long) time);
        hw.drive(0,0,0);
        sleep(500);
        telemetry.clearAll();
        telemetry.update();
    }
    public void strafeRight(double speed, double time){
        hw.drive(0,speed,0);
        telemetry.addData("Strafe", "\nspeed: " + speed + "\nTime: " + time);
        telemetry.update();
        sleep((long) time);
        hw.drive(0,0,0);
        sleep(500);
        telemetry.clearAll();
        telemetry.update();
    }
    public void turnAngle(double angle){
        double currentAngle = AngleUnit.normalizeDegrees(hw.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES));
        double targetAngle = AngleUnit.normalizeDegrees(currentAngle + angle);
        double MOE = 5;
        telemetry.addData("Turning", "\nAngle: " + angle);
        telemetry.update();
        if(angle < 0){
            while (Math.abs(AngleUnit.normalizeDegrees(hw.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES)-targetAngle)) <= MOE){
                hw.drive(0,0,-1);
            }
            hw.drive(0,0,0);
            sleep(500);
        }
        else if (angle > 0){
            while(Math.abs(AngleUnit.normalizeDegrees(hw.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES)-targetAngle)) <= MOE){
                hw.drive(0,0,1);
            }
            hw.drive(0,0,0);
            sleep(500);
        }
        telemetry.clearAll();
        telemetry.update();
    }
    public void turn(double speed, double time){
        hw.drive(0,0,speed);
        telemetry.addData("Turning", "\nspeed: " + speed + "\nTime: " + time);
        telemetry.update();
        sleep((long) time);
        hw.drive(0,0,0);
        sleep(500);
        telemetry.clearAll();
        telemetry.update();
    }
}

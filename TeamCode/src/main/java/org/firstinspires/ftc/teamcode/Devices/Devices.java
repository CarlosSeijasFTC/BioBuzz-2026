package org.firstinspires.ftc.teamcode.Devices;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcontroller.internal.FtcRobotControllerActivity;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.opencv.core.Mat;

public class Devices {

    private DcMotor frontRight;
    private DcMotor backRight;
    private DcMotor frontLeft;
    private DcMotor backLeft;
    private DcMotor intake;

    public IMU imu;

    private double maxPower = 1;

    private final double MAX_SPEED = 1; //change for outreach

    public void init(HardwareMap hwmp){
        frontRight = hwmp.get(DcMotor.class, "fR");
        backRight = hwmp.get(DcMotor.class, "bR");
        frontLeft = hwmp.get(DcMotor.class, "fL");
        backLeft = hwmp.get(DcMotor.class, "bL");
        intake = hwmp.get(DcMotor.class, "intake");

        imu = hwmp.get(IMU.class, "imu");

        RevHubOrientationOnRobot orientation = new RevHubOrientationOnRobot(RevHubOrientationOnRobot.LogoFacingDirection.RIGHT, RevHubOrientationOnRobot.UsbFacingDirection.UP);
        imu.initialize(new IMU.Parameters(orientation));
        imu.resetYaw();


        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);


        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    }

    public void setFrontRight(double a){
        frontRight.setPower(a);
    }

    public void setBackRight(double a){
        backRight.setPower(a);
    }

    public void setFrontLeft(double a){
        frontLeft.setPower(a);
    }

    public void setBackLeft(double a){
        backLeft.setPower(a);
    }

    public void intake(int a){
        if((a % 2) == 0){
            intake.setPower(0);
        }
        else {
            intake.setPower(1);
        }
    }

    //driving
    public void drive(double x, double y, double r){

        y = y * 1.1;

        maxPower = 1;

        double fL = x + y + r;
        double fR = x - y - r;
        double bL = x - y + r;
        double bR = x + y - r;

        maxPower = Math.max(fL, maxPower);
        maxPower = Math.max(fR, maxPower);
        maxPower = Math.max(bL, maxPower);
        maxPower = Math.max(bR, maxPower);

        frontRight.setPower((fR/maxPower)*MAX_SPEED);
        frontLeft.setPower((fL/maxPower)*MAX_SPEED);
        backRight.setPower((bR/maxPower)*MAX_SPEED);
        backLeft.setPower((bL/maxPower)*MAX_SPEED);

    }

    public void driveField(double x, double y, double r){
        double angle = -imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
        angle = AngleUnit.normalizeRadians(angle);

        double cos = Math.cos(angle);
        double sin = Math.sin(angle);

        double newY = x*cos + y*(-sin);
        double newX = x*(sin) + y*cos;

        drive(newX, newY, r);

    }


}

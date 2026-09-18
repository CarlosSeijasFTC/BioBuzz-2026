package org.firstinspires.ftc.teamcode.Devices;


import org.ejml.simple.SimpleMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class Odometry {
    double x,y,theta;
    double lastR,lastL,lastN;
    double[] pos = {x,y,theta};
    SimpleMatrix Position = new SimpleMatrix(pos);

    final double WHEEL_DIAMETER = 4.8;
    final double WHEEL_RADIUS = WHEEL_DIAMETER/2;
    final double TICKS_PER_REVOLUTION = 2000;
    final double ENCODER_DISTANCE = 40; //change
    final double NORMAL_ENCODER_OFFSET = 20; //change

    public Odometry(double x, double y, double theta){
        this.x = x;
        this.y = y;
        this.theta =theta;
    }


    public Odometry(double x, double y){
        this(x,y,0);
    }
    public Odometry(double theta){
        this(0,0,theta);
    }
    public Odometry(){
        this(0,0,0);
    }
    public void resetOdometry(){
        x = 0;
        y = 0;
        theta =0;
        lastR = 0;
        lastL = 0;
        lastN = 0;
        for(int i =0; i <=2; i++){
            pos[i]=0;
        }
        Position = new SimpleMatrix(pos);
    }

    /**
     * Update position for odometry instance. Using Pose exponential.
     * @param r Right Encoder Ticks Positive
     * @param l Left Encoder Ticks Positive
     * @param n Normal Encoder Ticks Positive
     * @param ang Gyroscope sensor yaw angle in radians
     */
    public void update(double r, double l, double n, double ang){
        double changeRTicks = r - lastR;
        double changeLTicks = l - lastL;
        double changeNTicks = n - lastN;

        double changeRAngle = (changeRTicks * 2 * Math.PI)/TICKS_PER_REVOLUTION;
        double changeLAngle = (changeLTicks * 2 * Math.PI)/TICKS_PER_REVOLUTION;
        double changeNAngle = (changeNTicks * 2 * Math.PI)/TICKS_PER_REVOLUTION;

        double changeXRobot = ((WHEEL_RADIUS * changeRAngle) + (WHEEL_RADIUS * changeLAngle))/2;
        double changeYRobot = WHEEL_RADIUS*((NORMAL_ENCODER_OFFSET/ENCODER_DISTANCE)*(changeRAngle - changeLAngle) + changeNAngle);
        double changeThetaRobot = (WHEEL_RADIUS/ENCODER_DISTANCE) * (changeLAngle - changeRAngle);

        double[] BotChange = {changeXRobot, changeYRobot, changeThetaRobot};

        ang = AngleUnit.normalizeRadians(ang);

        SimpleMatrix RobotChange = new SimpleMatrix(BotChange);
        Position = Position.plus(RotationMatrix(ang).mult(IntegralMatrix(changeThetaRobot)).mult(RobotChange));
        double[][] array = Position.toArray2();
        for (int i = 0; i <= 2; i++){
            pos[i] = array[i][0];
        }

        x = pos[0];
        y = pos[1];
        theta = pos[2];

        lastR = r;
        lastL = l;
        lastN = n;
    }

    public SimpleMatrix RotationMatrix(double angle){
        double[][] array = {
                {Math.cos(angle), -Math.sin(angle), 0},
                {Math.sin(angle), Math.cos(angle), 0},
                {0, 0, 1}
        };
        return new SimpleMatrix(array);
    }

    public SimpleMatrix IntegralMatrix(double deltaAngle){
        if(deltaAngle != 0){
            double[][] array = {
                    {Math.sin(deltaAngle)/deltaAngle, -(1-Math.cos(deltaAngle))/deltaAngle, 0},
                    {(1-Math.cos(deltaAngle))/deltaAngle, Math.sin(deltaAngle)/deltaAngle, 0},
                    {0, 0, 1}
            };
            return new SimpleMatrix(array);
        }
        else return SimpleMatrix.identity(3);
    }

    @Override
    public String toString(){

        return "x: " + x + "\ny: " + y + "\nθ: " + theta;
    }



}

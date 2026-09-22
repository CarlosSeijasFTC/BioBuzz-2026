package org.firstinspires.ftc.teamcode.Devices;

public class GVFNavigation {

    public Vector2 calculateGuidanceVector(QuinticSpline spline, Vector2 location){
        double closestT = findClosestPoint(spline,location);
        Vector2 closestPoint = spline.tParametricQuinticSpline(closestT);
        Vector2 curveDerivative = spline.tDerivativeValue(closestT);
        Vector2 robotToClosestPoint = closestPoint.subtract(location);
        double CORRECTION_DISTANCE = 5;
        Vector2 endPoint = spline.tParametricQuinticSpline(1);
        double SAVING_THROW_DISTANCE = 10;
        double directPursuitThreshold = 1;
        {
            for(double i = 1; i>=0; i -= 1/200.0){
                double dist = endPoint.subtract(spline.tParametricQuinticSpline(i)).getMagSquared();
                if(dist > SAVING_THROW_DISTANCE){
                    directPursuitThreshold = i;
                    break;
                }
            }
        }
        Vector2 robotToEnd = endPoint.subtract(location);
        double correctionFactor = Math.min(1, robotToClosestPoint.getMag()/CORRECTION_DISTANCE);
        double movementDirection = hlerp(curveDerivative.getHeading(), robotToClosestPoint.getHeading(), correctionFactor);
        if((closestT == 1 && Math.abs(location.subtract(closestPoint).getHeading() - curveDerivative.getHeading()) <= 0.5 * Math.PI) || closestT >= directPursuitThreshold){
            movementDirection = endPoint.subtract(location).getHeading();
        }

        Vector2 movementVector = new Vector2(Math.cos(movementDirection), Math.sin(movementDirection));
        double speed = 1;

        if(robotToEnd.getMag() <50){
            speed = lerp(0.2, speed, robotToEnd.getMag()/50);
        }

        movementVector = movementVector.scalarMultiply(speed);

        return movementVector;
    }




    public double hlerp(double a, double b, double t) {
        double diff = b - a;
        diff %= 2 * Math.PI;
        if (Math.abs(diff) > Math.PI) {
            if (diff > 0) {
                diff -= 2 * Math.PI;
            } else {
                diff += 2 * Math.PI;
            }
        }
        return a + t * diff;
    }
    private  double lerp(double a, double b, double t){
        return (1-t)*a + t*b;
    }
    public double findClosestPoint(QuinticSpline spline, Vector2 point){
        double minT = -1;
        double minDist = Double.POSITIVE_INFINITY;
        int sampleDensity = 500;
        for(int i = 0; i<=sampleDensity; i++){
            double t = i/ (double) sampleDensity;
            double dist = minimizationFunction(spline,point,t);
            if(dist < minDist){
                minDist = dist;
                minT = t;
            }
        }
        return minT;
    }

    public double minimizationFunction(QuinticSpline spline, Vector2 point, double t){
        return spline.tParametricQuinticSpline(t).subtract(point).getMagSquared();
    }
}

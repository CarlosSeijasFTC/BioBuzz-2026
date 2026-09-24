package org.firstinspires.ftc.teamcode.Devices;

public class PolynomialFunction {
    double[] coefficients = new double[6];

    public PolynomialFunction(double[] coefficients){
       for(int i=0; i<=5; i++){
           this.coefficients[i] = coefficients[i];
       }
    }

    public double value(double t){
        return coefficients[0] + coefficients[1] * t + coefficients[2]*t*t + coefficients[3]*t*t*t + coefficients[4]*t*t*t*t + coefficients[5]*t*t*t*t*t;
    }
}

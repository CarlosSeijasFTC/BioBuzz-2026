package org.firstinspires.ftc.teamcode.Devices;

import org.apache.commons.math4.legacy.analysis.polynomials.PolynomialFunction;
import org.ejml.simple.SimpleMatrix;

public class QuinticSpline {
    public double[] position1;
    public double[] position2;
    public double[] firstDif1;
    public double[] firstDif2;
    public double[] secondDif1;
    public double[] secondDif2;
    public double[][] masterArray = new double[6][2];
    public double[][] constantArray = {
            {0,0,0,0,0,1},
            {0,0,0,0,1,0},
            {0,0,0,2,0,0},
            {1,1,1,1,1,1},
            {5,4,3,2,1,0},
            {20,12,6,2,0,0}
    };
    public SimpleMatrix constantMatrix = new SimpleMatrix(constantArray);
    public double[] xArray = new double[6];
    public double[] yArray = new double[6];
    public SimpleMatrix xMatrix;
    public SimpleMatrix yMatrix;
    public SimpleMatrix resultXCoefficients;
    public SimpleMatrix resultYCoefficients;
    public PolynomialFunction xSplineComponent;
    public PolynomialFunction ySplineComponent;
    public double[] derivativeXCoefficients = new double[5];
    public double[] derivativeYCoefficients = new double[5];
    private PolynomialFunction xDerivative;
    private PolynomialFunction yDerivative;




    /**
     * Constructor for Quintic Splines
     * @param initialState 2d array. First array initial position. Second array initial change. Third array initial change of change.
     * @param finalState 2d array. First array final position. Second array final change. Third array final change of change.
     */
    public QuinticSpline(double[][] initialState, double[][] finalState ){
        position1 = initialState[0];
        position2 = finalState[0];
        firstDif1 = initialState[1];
        firstDif2 = finalState[1];
        secondDif1 = initialState[2];
        secondDif2 = finalState[2];
        for(int i = 0; i<=2; i++){
            masterArray[i] = initialState[i];
            masterArray[i+3] = finalState[i];
        }
        for(int i = 0; i <= 5; i++){
            xArray[i] = masterArray[i][0];
        }
        for(int i = 0; i <= 5; i++){
            yArray[i] = masterArray[i][1];
        }
        xMatrix = new SimpleMatrix(xArray);
        yMatrix = new SimpleMatrix(yArray);
        resultXCoefficients = constantMatrix.solve(xMatrix);
        resultYCoefficients = constantMatrix.solve(yMatrix);
        xSplineComponent = new PolynomialFunction(columnVectorToPolynomialArray(resultXCoefficients));
        ySplineComponent = new PolynomialFunction(columnVectorToPolynomialArray(resultYCoefficients));
        for(int i = 0; i<=4; i++){
            derivativeXCoefficients[i] = resultXCoefficients.get(5-i,1) * i;
        }
        for(int i = 0; i<=4; i++){
            derivativeYCoefficients[i] = resultYCoefficients.get(5-i,1) * i;
        }
        xDerivative = new PolynomialFunction(derivativeXCoefficients);
        yDerivative = new PolynomialFunction(derivativeYCoefficients);
    }

    protected double[] columnVectorToPolynomialArray(SimpleMatrix matrix){
        double[] result = new double[6];
        for(int i = 0; i<=5; i++){
            result[i] = matrix.get(6-i,1);
        }
        return result;
    }

    public Vector2 tParametricQuinticSpline(double t){
        double x = xSplineComponent.value(t);
        double y = ySplineComponent.value(t);
        return new Vector2(x,y);
    }
    public Vector2 tDerivativeValue(double t){
        double x = xDerivative.value(t);
        double y = yDerivative.value(t);
        return new Vector2(x,y);
    }
}

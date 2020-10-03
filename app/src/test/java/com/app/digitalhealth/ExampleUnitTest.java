package com.app.digitalhealth;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

     private  BodyFatCal bodyFatCal;


    //IT19204680 (BODY FAT CALCULATOR)
    @Before
    public void setup(){
        bodyFatCal = new BodyFatCal();
    }

    @Test
    public void test1() {
        double age = 18;
        double BMI = 21;
        String gender ="male";

        double expectedResult = 16.91;
        double result = bodyFatCal.calculateFAT(age,BMI,gender);
        Assert.assertEquals(expectedResult, result, 0);
    }

    @Test
    public void test2() {
        double age = 18;
        double BMI = 21;
        String gender ="female";

        double expectedResult = 20.51;
        double result = bodyFatCal.calculateFAT(age,BMI,gender);
        Assert.assertEquals(expectedResult, result, 0);
    }

    @Test
    public void test3() {
        double age = 23;
        double BMI = 13;
        String gender ="female";

        double expectedResult = 4.93;
        double result = bodyFatCal.calculateFAT(age,BMI,gender);
        Assert.assertEquals(expectedResult, result, 0);
    }

    @Test
    public void test4() {
        double age = 23;
        double BMI = 20;
        String gender ="male";

        double expectedResult = 13.09;
        double result = bodyFatCal.calculateFAT(age,BMI,gender);
        Assert.assertEquals(expectedResult, result, 0);
    }

    @Test
    public void test5() {
        double age = 23;
        double BMI = 30;
        String gender ="female";

        double expectedResult = 30.60;
        double result = bodyFatCal.calculateFAT(age,BMI,gender);
        Assert.assertEquals(expectedResult, result, 0);
    }




    //IT 19 1616 48 - S. M. Jayasekara
    // BMI Calculator

    private BMICalculatorActivity bmiCalculatorActivity;

    @Before
    public void setUp(){
        bmiCalculatorActivity = new BMICalculatorActivity();
    }

    @Test
    public void test6(){
        float expectedValue = (float) 19.814053;
        float result = bmiCalculatorActivity.calculateBMI(162, 52);
        Assert.assertEquals(expectedValue, result, 0.001);
    }

    @Test
    public void test7(){
        float expectedValue = (float) 16.444445;
        float result = bmiCalculatorActivity.calculateBMI(150, 37);
        Assert.assertEquals(expectedValue, result, 0.001);
    }

    @Test
    public void test8(){
        float expectedValue = (float) 26.315779;
        float result = bmiCalculatorActivity.calculateBMI(190, 95);
        Assert.assertEquals(expectedValue, result, 0.001);
    }

}
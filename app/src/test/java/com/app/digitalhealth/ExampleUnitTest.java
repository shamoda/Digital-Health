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


}
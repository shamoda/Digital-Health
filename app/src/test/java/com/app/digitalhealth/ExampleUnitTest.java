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


    //IT19185026(BMR Calculator)

    private  BMRcalculatorActivity bmr;

    @Before
    public void set(){

        bmr = new BMRcalculatorActivity();
    }

    @Test
    public void testa1() {

        float age = 22;
        float height = 110;
        float weight = 40;
        String gender ="female";

       float Result = (float) 816.5;
       float res = BMRcalculatorActivity.calculateBMR(weight,height,age,gender);


       Assert.assertEquals(Result,res, 0);

    }

    @Test
    public void testa2() {

        float age = 22;
        float height = 110;
        float weight = 40;
        String gender ="male";

        float Result = (float) 982.5;
        float res = BMRcalculatorActivity.calculateBMR(weight,height,age,gender);


        Assert.assertEquals(Result,res, 0);

    }

    @Test
    public void testa3() {

        float age = 22;
        float height = 110;
        float weight = 55;
        String gender ="female";

        float Result = (float) 966.5;
        float res = BMRcalculatorActivity.calculateBMR(weight,height,age,gender);


        Assert.assertEquals(Result,res, 0);

    }

    @Test
    public void testa4() {

        float age = 22;
        float height = 110;
        float weight = 55;
        String gender ="male";

        float Result = (float) 1132.5;
        float res = BMRcalculatorActivity.calculateBMR(weight,height,age,gender);


        Assert.assertEquals(Result,res, 0);

    }

    @Test
    public void testa5() {

        float age = 22;
        float height = 120;
        float weight = 40;
        String gender ="female";

        float Result = (float) 879.0;
        float res = BMRcalculatorActivity.calculateBMR(weight,height,age,gender);


        Assert.assertEquals(Result,res, 0);

    }

    @Test
    public void testa6() {

        float age = 22;
        float height = 120;
        float weight = 40;
        String gender ="male";

        float Result = (float) 1045.0;
        float res = BMRcalculatorActivity.calculateBMR(weight,height,age,gender);


        Assert.assertEquals(Result,res, 0);

    }

    @Test
    public void testa7() {

        float age = 32;
        float height = 120;
        float weight = 40;
        String gender ="female";

        float Result = (float) 829.0;
        float res = BMRcalculatorActivity.calculateBMR(weight,height,age,gender);


        Assert.assertEquals(Result,res, 0);

    }

    @Test
    public void testa8() {

        float age = 32;
        float height = 120;
        float weight = 40;
        String gender ="male";

        float Result = (float) 995.0;
        float res = BMRcalculatorActivity.calculateBMR(weight,height,age,gender);


        Assert.assertEquals(Result,res, 0);

    }
}
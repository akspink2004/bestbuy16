package com.bestbuy.storeinfo;

import com.bestbuy.model.StorePojo;
import com.bestbuy.testbase.TestBase;
import com.bestbuy.utils.Utils;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Title;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class StoreCrudTest extends TestBase {
        static String name ="Hoover"+ Utils.getRandomValue();
        static String type = "dyson"+Utils.getRandomValue();
        static String add = "abc"+Utils.getRandomValue();
        static String state = "united kingdom";
        static String zip = "abc123";
        static String city = "london";
        static int storeID;
        @Title("This is will get all information of all store")
        @Test
        public void test001(){
            SerenityRest. given().log().all()
                    .when().get()
                    .then()
                    .log().all()
                    .statusCode(200);
        }
}

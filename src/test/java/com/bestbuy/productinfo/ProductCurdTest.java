package com.bestbuy.productinfo;

import com.bestbuy.constants.EndPoints;
import com.bestbuy.model.ProductPojo;
import com.bestbuy.testbase.TestBase;
import com.bestbuy.utils.Utils;
import io.restassured.http.ContentType;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;


@RunWith(SerenityRunner.class)
public class ProductCurdTest extends TestBase {
     static String name = "akshit" + Utils.getRandomValue();
        static String type = "standar" + Utils.getRandomValue();
        static String model = "ytedghf" + Utils.getRandomValue();
        static Object productID;
        @Test // get all list
        public void test001() {

            SerenityRest.given()
                    .when()
                    .log().all()
                    .get(EndPoints.GET_ALL_PRODUCTS)
                    .then().log().all().statusCode(200);
        }

        @Test // post new and retrive id
        public void test002() {

            ProductPojo pojo = new ProductPojo();
            pojo.setName(name);
            pojo.setType(type);
            pojo.setPrice(1700);
            pojo.setShipping(15);
            pojo.setUpc("af");
            pojo.setDescription("egwe");
            pojo.setManufacturer("regsd");
            pojo.setModel(model);
            pojo.setUrl("aerg");
            pojo.setImage("yjnyu");


            SerenityRest.given()
                    .log().all()
                    .contentType(ContentType.JSON)
                    .body(pojo)
                    .when()
                    .post(EndPoints.CREATE_NEW_PRODUCTS)
                    .then().log().all()
                    .statusCode(201);
            System.out.println(name);
        }
    @Title("Verify if product was created")
    @Test
    public void test003() {

        String p1 = "data.findAll{it.name='";
        String p2 = "'}.get(0)";
        HashMap<String,?> productMap = SerenityRest.given().log().all()
                .when()
                .get(EndPoints.GET_ALL_PRODUCTS)
                .then().statusCode(200)
                .extract()
                .path(p1 + name + p2);
        System.out.println();
        String name1 = "sdcy";
        //Assert.assertThat(productMap,hasValue(name));
        productID = productMap.get("id");
        System.out.println("ID =" + productID);
        System.out.println("name = " + productMap);
    }

    @Title("update the product and verify the update information")
    @Test
    public void test004() {
        name = name +"abc";
        type = type + "patel";

        ProductPojo pojo = new ProductPojo();
        pojo.setName(name);
        pojo.setType(type);
        pojo.setPrice(1800);
        pojo.setShipping(10);
        pojo.setUpc("af");
        pojo.setDescription("egwe");
        pojo.setManufacturer("regsd");
        pojo.setModel(model);
        pojo.setUrl("aerg");
        pojo.setImage("yjnyu");

        SerenityRest.given().log().all()
                .header("Content-Type", "application/json; charset=utf-8")
                .pathParam("productID", productID)
                .body(pojo)
                .when()
                .patch(EndPoints.UPDATE_PRODUCT_BY_ID)
                .then().log().all().statusCode(200);


        String p1 = "data.findAll{it.name='";
        String p2 = "'}.get(0)";
        HashMap<String, Object> promap = SerenityRest.given().log().all()
                .when()
                .get()
                .then().statusCode(200)
                .extract()
                .path(p1 + name + p2);
        System.out.println(promap);
       // Assert.assertThat(promap, hasValue(name));
    }

    @Title("Delete the student and verify if the student is deleted")
    @Test
    public void test005(){

        SerenityRest.given()
                .pathParam("productID",productID)
                .when()
                .delete(EndPoints.DELETE_PRODUCT_BY_ID)
                .then().log().all().statusCode(200);

        SerenityRest.given()
                .pathParam("productID",productID)
                .when()
                .get(EndPoints.GET_SINGLE_PRODUCT_BY_ID)
                .then().log().all().statusCode(404);

    }
}
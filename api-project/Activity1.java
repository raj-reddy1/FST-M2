package com.ibm;

import static io.restassured.RestAssured.given;

import static io.restassured.RestAssured.oauth2;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class Activity1 {

    // Declare request specification
    RequestSpecification requestSpec;
    // Declare response specification
    ResponseSpecification responseSpec;

    String SSH_Key= "ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAAAgQCxHfxolWYIqrAlZzCV/M56VBB19lzXJ0+5pgGs60jTNxOu+9LG8B9p5tR4WmAUjxkjlCTgmuBwpDEkp3fuvAtg4bEfZMU++WRkNNoInnG9Wt1nsMMMUGVQC+lbRt9yaify/ZvBlWH64OnVCM38AvpnhlUjbtjC4M44uMgAnp01nQ==";
    int id;

    @BeforeClass
    public void setUp() {
        // Create request specification
        requestSpec = new RequestSpecBuilder()
                // Set content type
                .setContentType(ContentType.JSON)
                // Set Auth Token
                .setAuth(oauth2("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"))
                // Set base URL
                .setBaseUri("https://api.github.com")
                // Build request specification
                .build();
    }

    @Test(priority=1)
    // Test case to add SSH Key
    public void addSSHKey() {
        String ROOT_URL = "/user/keys";
        String reqBody =  "{\"title\": \"TestAPIKey\", \"key\": \"ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAAAgQCxHfxolWYIqrAlZzCV/M56VBB19lzXJ0+5pgGs60jTNxOu+9LG8B9p5tR4WmAUjxkjlCTgmuBwpDEkp3fuvAtg4bEfZMU++WRkNNoInnG9Wt1nsMMMUGVQC+lbRt9yaify/ZvBlWH64OnVCM38AvpnhlUjbtjC4M44uMgAnp01nQ==\"}";
        Response response = given().spec(requestSpec) // Use requestSpec
                .body(reqBody) // Send request body
                .when().post(ROOT_URL); // Send POST request
        // Print Response
        System.out.println("addSSHKey() Response: " + response.asPrettyString());
        //Assign the id of the SSH key
        //id =Integer.parseInt(response.then().extract().path("[0].id").toString());
        id = response.body().jsonPath().get("id");
        System.out.println("id from Response is =>  " + id);
        // Assertions
        response.then().statusCode(201); // assert status code
    }

    // Test case to get SSH Key
    @Test(priority=2)
    public void getSSHKey() {
        String ROOT_URL = "/user/keys";
        Response response = given().spec(requestSpec) // Use requestSpec
                .when().get(ROOT_URL + "/" + id); // Send GET request

        // Print response
        System.out.println("getSSHKey() Response: " + response.asPrettyString());
        System.out.println("getSSHKey() Status Code: " + response.getStatusCode());
        // Assertions
        response.then().statusCode(200); // assert status code
    }

    // Test case to delete SSH Key
    @Test(priority=3)
    public void deleteSSHKey() {
        String ROOT_URL = "/user/keys/{keyId}";
        Response response = given().spec(requestSpec) // Use requestSpec
                        .pathParam("keyId", id) // Add path parameter
                        .when().delete(ROOT_URL); // Send POST request

        // Print response
        System.out.println("deleteSSHKey() Response: " + response.asPrettyString());
        System.out.println("deleteSSHKey() Status Code: " + response.getStatusCode());

        // Assertions
        response.then().statusCode(204); // assert status code
    }
}
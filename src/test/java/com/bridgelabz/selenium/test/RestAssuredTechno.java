package com.bridgelabz.selenium.test;

import io.restassured.filter.cookie.CookieFilter;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

// Swagger : http://kvmnode2.technoforte.co.in:5002/Swagger/index.html
public class RestAssuredTechno {
        public static String token, publicKey;
        private final String JSON = "application/json";
        public static Response generateResponse,authResponse,selectTResponse,selectTResponse1, selectTResponse2, selectTResponse3;
        public static CookieFilter cookie = new CookieFilter();
        String s2, en, k;

        @Test(priority = 1)
        public void generateKey() {
                generateResponse = given()
                        .accept(JSON)
                        .contentType(JSON)
                        .pathParam("userName", "admin")
                        .pathParam("password","Techno@123")
                        .when()
                        .post("http://kvmnode2.technoforte.co.in:5002/api/GenerateClientKeyPair?userName={userName}&password={password}");
                generateResponse.prettyPrint();
                generateResponse.then().assertThat().statusCode(200);
        }

        @Test(priority = 2)
        public void authentication() {
                authResponse = given()
                        .accept(JSON)
                        .contentType(JSON)
                        .body(generateResponse.asString())
                        .filter(cookie)
                        .when()
                        .post("http://kvmnode2.technoforte.co.in:5002/v2/api/Authenticate");
                authResponse.prettyPrint();
                authResponse.then().assertThat().statusCode(200);
                token = authResponse.path("response.authenticationToken");
                System.out.println("Token : "+token);
                publicKey = authResponse.path("response.serverPublicKey");
                System.out.println("Public Key : "+publicKey);
        }


        @Test(priority = 3)
        public void selectTenant() {
                selectTResponse = given()
                        .accept(JSON)
                        .contentType(JSON)
                        .auth()
                        .oauth2(token)
                        .body("{\n" +
                                "  \"id\": \"string\",\n" +
                                "  \"version\": \"string\",\n" +
                                "  \"requesttime\": \"string\",\n" +
                                "  \"metadata\": \"\",\n" +
                                "  \"request\": {\n" +
                                "    \"countryName\": \"India\",\n" +
                                "    \"partnerName\": \"babu\"\n" +
                                "  }\n" +
                                "}")
                        .when()
                        .post("http://kvmnode2.technoforte.co.in:5002/v2/api/SelectTenant");
                selectTResponse.prettyPrint();
                Assert.assertEquals(selectTResponse.getStatusCode(),200);
        }

        @Test(priority = 4)
        public void getEncryptedtext() {
                selectTResponse1 = given()
                        .accept(JSON)
                        .contentType(JSON)
                        .baseUri("http://kvmnode2.technoforte.co.in:5002/v2/api/Authenticate")
                        .auth()
                        .oauth2(token)
                        .cookie("serverPublicKey",publicKey)
                        .body("{\"ConfigKey\":\"KeyCombination\", \"ConfigValue\":\"PerSbi\"}")
                        .when()
                        .post("http://kvmnode2.technoforte.co.in:5002/api/GetEncryptedRequest");
                selectTResponse1.prettyPrint();
                Assert.assertEquals(selectTResponse1.getStatusCode(),200);
        }

        @Test(priority = 5)
        public void configsPost() {
                selectTResponse2 = given()
                        .accept(JSON)
                        .contentType(JSON)
                        .baseUri("http://kvmnode2.technoforte.co.in:5002/v2/api/Authenticate")
                        .auth()
                        .oauth2(token)
                        .cookie("serverPublicKey",publicKey)
                        .body(selectTResponse1.asString())
                        .filter(cookie)
                        .when()
                        .post("http://kvmnode2.technoforte.co.in:5002/v2/api/Configs");
                selectTResponse2.prettyPrint();
                Assert.assertEquals(selectTResponse2.getStatusCode(),200);
                //String s1= selectTResponse2.asString().replace("\"","");
                //s2 = s1.replace("\\","");
                //System.out.print(s2);
        }

        @Test(priority = 6)
        public void configsDecrypt() {
                selectTResponse3 = given()
                        .accept(JSON)
                        .contentType(JSON)
                        .baseUri("http://kvmnode2.technoforte.co.in:5002/v2/api/Authenticate")
                        .auth()
                        .oauth2(token)
                        .cookie("serverPublicKey",publicKey)
                        .body(selectTResponse2.asString())
                        .when()
                        .post("http://kvmnode2.technoforte.co.in:5002/api/GetDecryptedResponse");
                selectTResponse3.prettyPrint();
                Assert.assertEquals(selectTResponse3.getStatusCode(),200);
        }
}

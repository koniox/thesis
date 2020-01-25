/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.debinski.konrad.test;

import static io.restassured.RestAssured.*;
import io.restassured.http.ContentType;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Konrad Dębiński
 * @version 1.0
 */
public class ResourceServiceTest {
    @BeforeClass
    public static void init(){
        baseURI = "http://localhost";
        port = 8080;
        basePath = "/Thesis-web/api";
    }
    @Test
    public void testResourcesStatus(){
        given()
                .when()
                .get("/resources")
                .then()
                .statusCode(200);                       
    }
    
    @Test
    public void testRequestStatusCodeForUnexistingResource(){
        given()
                .when()
                .get("/resources/999")
                .then()
                .statusCode(204);
    }  
    
    @Test
    public void testResourcesPartialBody(){
        given()
                .when()
                .get("/resources")
                .then()
                .body("resourceSlices.Resource.find { it.@id == '4'} ", 
                        hasItems("Monitoring chemizmu gleb ornych 2015 - wyniki",
                        "https://dane.gov.pl/dataset/778,panstwowy-monitoring-sro"
                                + "dowiska-monitoring-chemizmu-gleb-ornych-polski") );
    }
    
    @Test
    public void testSpecificResourceBody(){
        given()
                .accept(ContentType.XML)
                .when()
                .get("/resources/29")
                .then()
                .assertThat()
                .body("resource.id", equalTo("29"),
                      "resource.title", equalTo("Dane ze stacji synpotycznych"),
                      "resource.addressURL", equalTo("https://dane.gov.pl/dataset/729,dane-ze-stacji-synoptycznych"),
                      "resource.labels", equalTo("\"id_stacji\",\"stacja\",\"data_pomiaru\","
                              + "\"godzina_pomiaru\",\"temperatura\",\"predkosc_wiatru\","
                              + "\"kierunek_wiatru\",\"wilgotnosc_wzgledna\",\"suma_opadu\",\"cisnienie\"") );
    }  
}

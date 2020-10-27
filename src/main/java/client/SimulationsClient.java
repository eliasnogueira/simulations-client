/*
 * MIT License
 *
 * Copyright (c) 2020 Elias Nogueira
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package client;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

import config.ConfigurationManager;
import exception.ConflictException;
import exception.NotFoundException;
import exception.SimulationException;
import exception.UnprocessableEntityException;
import io.restassured.http.ContentType;
import io.restassured.http.Headers;
import model.Simulation;
import org.apache.http.HttpStatus;

public class SimulationsClient extends RestClient {

    public static final String BASE_URI = ConfigurationManager.getConfiguration().baseUri();
    public static final String BASE_PATH = ConfigurationManager.getConfiguration().basePath();
    public static final String PORT = String.valueOf(ConfigurationManager.getConfiguration().port());

    public static final String VERSION = "/v1";
    public static final String SIMULATIONS = "/simulations/";

    public SimulationsClient() {
        super(BASE_URI, PORT, BASE_PATH, VERSION);
    }

    public Simulation[] getAllSimulations() {
        return
            when().
                get(getPath(SIMULATIONS)).
            then().
                statusCode(HttpStatus.SC_OK).
                extract().
                    as(Simulation[].class);
    }

    public Headers createNewSimulation(Simulation simulation) {
        return
            given().
                contentType(ContentType.JSON).
                body(simulation).
            when().
                post(getPath(SIMULATIONS)).
            then().
                statusCode(HttpStatus.SC_CREATED).
                    extract().
                        headers();
    }

    public Simulation getSimulationBySocialSecurityNumber(String cpf) {
        return
            given().
                pathParam("cpf", cpf).
            when().
                get(getPath(SIMULATIONS + "{cpf}")).
            then().
                statusCode(HttpStatus.SC_OK).
                extract().
                    body().
                        as(Simulation.class);
    }

    public Simulation[] getSimulationByName(String name) {
        return
            given().
                queryParam("name", name).
            when().
                get(getPath(SIMULATIONS)).
            then().
                statusCode(HttpStatus.SC_OK).
                extract().
                    as(Simulation[].class);
    }

    public NotFoundException getSimulationByNameAndExpectNotFound(String name) {
        return
            given().
                queryParam("name", name).
            when().
                get(getPath(SIMULATIONS)).
            then().
                statusCode(HttpStatus.SC_NOT_FOUND).
                extract().
                    as(NotFoundException.class);
    }

    public Simulation updateSimulation(String cpf, Simulation simulation) {
        return
            given().
                contentType(ContentType.JSON).
                pathParam("cpf", cpf).
                body(simulation).
            when().
                put(getPath(SIMULATIONS + "{cpf}")).
            then().
                statusCode(HttpStatus.SC_OK).
                extract().
                    as(Simulation.class);
    }

    public ConflictException updateSimulationAndExpectConflictException(Simulation simulation) {
        return
            given().
                contentType(ContentType.JSON).
                body(simulation).
            when().
                post(getPath(SIMULATIONS)).
            then().
                statusCode(HttpStatus.SC_CONFLICT).
                extract().
                    as(ConflictException.class);
    }

    public NotFoundException updateSimulationAndExpectNotFoundException(String cpf, Simulation simulation) {
        return
            given().
                contentType(ContentType.JSON).
                pathParam("cpf", cpf).
                body(simulation).
            when().
                put(getPath(SIMULATIONS + "{cpf}")).
            then().
                statusCode(HttpStatus.SC_NOT_FOUND).
                extract().
                    as(NotFoundException.class);
    }

    public UnprocessableEntityException updateSimulationAndExpectUnprocessableEntityException(Simulation simulation) {
        return
            given().
                contentType(ContentType.JSON).
                body(simulation).
            when().
                post(getPath(SIMULATIONS)).
            then().
                statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY).
                extract().
                    as(UnprocessableEntityException.class);
    }

    public void deleteSimulation(String cpf) {
        given().
            pathParam("cpf", cpf).
        when().
            delete(getPath(SIMULATIONS + "{cpf}")).
        then().
            statusCode(HttpStatus.SC_NO_CONTENT);
    }

    public NotFoundException deleteSimulationAndReturnNotFound(String cpf) {
        return
            given().
                pathParam("cpf", cpf).
            when().
                delete(getPath(SIMULATIONS + "{cpf}")).
            then().
                statusCode(HttpStatus.SC_NOT_FOUND).
                extract().
                    as(NotFoundException.class);
    }
}

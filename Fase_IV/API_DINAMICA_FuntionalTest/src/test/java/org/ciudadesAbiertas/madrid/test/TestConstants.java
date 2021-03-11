/**
 * Copyright 2021 Ayuntamiento de A Coruña, Ayuntamiento de Madrid, Ayuntamiento de Santiago de Compostela, Ayuntamiento de Zaragoza, Entidad Pública Empresarial Red.es
 * 
 * This file is part of the Open Cities API, developed within the "Ciudades Abiertas" project (https://ciudadesabiertas.es/).
 * 
 * Licensed under the EUPL, Version 1.2 or – as soon they will be approved by the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 * 
 * https://joinup.ec.europa.eu/software/page/eupl
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and limitations under the Licence.
 */
package org.ciudadesAbiertas.madrid.test;

/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
public class TestConstants {

public static String HOST = "http://127.0.0.1:8080";
public static String CONTEXT = "/dynamicAPI";
public static String DRIVER_SELECTED = "firefox";
public static int TIMEOUT = 10;
public static String PASSWORD_OK = "123456";
public static String USER_OK = "localidata";
public static final String LOGIN_URL = HOST + CONTEXT + "/login";
public static final String LOGIN_OUT = LOGIN_URL + "?logout";
public static final String JSON_DEFINITION = "{{}";
public static final String SWAGGER_URL= HOST + CONTEXT + "/swagger/index.html";


}

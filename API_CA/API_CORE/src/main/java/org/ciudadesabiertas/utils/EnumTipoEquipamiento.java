/**
 * Copyright 2019 Ayuntamiento de A Coruña, Ayuntamiento de Madrid, Ayuntamiento de Santiago de Compostela, Ayuntamiento de Zaragoza, Entidad Pública Empresarial Red.es
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

package org.ciudadesabiertas.utils;

/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
public enum EnumTipoEquipamiento
{	
	EQUIPAMIENTO("equipamiento municipal","/equipamiento/equipamiento"),
	WIFI("punto wifi","/puntoWifi/puntoWifi"),
    APARCAMIENTO("aparcamiento publico", "/aparcamiento/aparcamiento"),
    DEPORTES("instalacion deportiva", "/instalacionDeportiva/instalacionDeportiva");
	
	
	private final String tipo;
    private final String path;

    EnumTipoEquipamiento(String tipo, String path) {
        this.tipo = tipo;
        this.path = path;
    }

	public String getTipo()
	{
		return tipo;
	}

	public String getPath()
	{
		return path;
	}

    
	
}

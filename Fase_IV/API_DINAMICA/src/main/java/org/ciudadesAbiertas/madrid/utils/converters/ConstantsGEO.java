/**
 * 
 */
package org.ciudadesAbiertas.madrid.utils.converters;

import java.util.ArrayList;
import java.util.List;

import org.ciudadesAbiertas.madrid.utils.constants.Constants;

/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
public class ConstantsGEO {
	
	public static List <String> ignoreFields=new ArrayList<String>();
	
	static
	{
		ignoreFields.add("ikey");
		ignoreFields.add("class");
		ignoreFields.add("distance");
		ignoreFields.add("x");
		ignoreFields.add("y");
		ignoreFields.add("latitud");
		ignoreFields.add("longitud");
		ignoreFields.add("hasgeometry");	
		ignoreFields.add(Constants.XETRS89.toLowerCase());
		ignoreFields.add(Constants.YETRS89.toLowerCase());
		ignoreFields.add("lat");
		ignoreFields.add("lon");
		ignoreFields.add(Constants.hasGeometry.toLowerCase());	
	}

}

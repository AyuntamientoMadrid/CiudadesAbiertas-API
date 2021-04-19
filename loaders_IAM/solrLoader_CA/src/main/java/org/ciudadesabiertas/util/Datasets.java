/**
 * 
 */
package org.ciudadesabiertas.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author localidata
 *
 */
public class Datasets
{
	private Map<String,Boolean> datasetsToLoad=new HashMap<String,Boolean>();
	
	public Datasets()
	{
		super();
		datasetsToLoad=new HashMap<String,Boolean>();	
	
		datasetsToLoad.put("agendaCultural", false);
		datasetsToLoad.put("alojamiento", false);
		datasetsToLoad.put("aparcamiento", false);
		datasetsToLoad.put("avisoQuejaSugerencia", false);
		datasetsToLoad.put("calidadAireEstacion", false);
		datasetsToLoad.put("calidadAireObservacion", false);
		datasetsToLoad.put("calidadAireSensor", false);
		datasetsToLoad.put("callejeroPortal", false);
		datasetsToLoad.put("callejeroTramoVia", false);
		datasetsToLoad.put("callejeroVia", false);
		datasetsToLoad.put("equipamiento", false);
		datasetsToLoad.put("instalacionDeportiva", false);
		datasetsToLoad.put("localComercialAgrupacionComercial", false);
		datasetsToLoad.put("localComercialLicenciaActividad", false);
		datasetsToLoad.put("localComercial", false);
		datasetsToLoad.put("localComercialTerraza", false);
		datasetsToLoad.put("monumento", false);
		datasetsToLoad.put("organigrama", false);
		datasetsToLoad.put("puntoWifi", false);
		datasetsToLoad.put("puntoInteresTuristico", false);		
		datasetsToLoad.put("tramite", false);
		
		datasetsToLoad.put("dsd", false);
		datasetsToLoad.put("dsdDimension", false);
		datasetsToLoad.put("dsdDimensionValue", false);
		datasetsToLoad.put("dsdMeasure", false);
		
		datasetsToLoad.put("padronCuboEdad", false);
		datasetsToLoad.put("padronCuboEstudios", false);
		datasetsToLoad.put("padronCuboIndicadores", false);
		datasetsToLoad.put("padronCuboNacionalidad", false);
		datasetsToLoad.put("padronCuboProcedencia", false);
		datasetsToLoad.put("padronCuboEdadGrupoQuinquenal", false);
		datasetsToLoad.put("padronCuboPaisNacimiento", false);

		datasetsToLoad.put("territorioPais", false);
		datasetsToLoad.put("territorioAutonomia", false);
		datasetsToLoad.put("territorioProvincia", false);
		datasetsToLoad.put("territorioMunicipio", false);
		datasetsToLoad.put("territorioDistrito", false);
		datasetsToLoad.put("territorioBarrio", false);
		datasetsToLoad.put("territorioSeccion", false);

		datasetsToLoad.put("agendaMunicipalEvento", false);
		datasetsToLoad.put("agendaMunicipalDocumento", false);
		datasetsToLoad.put("agendaMunicipalRolEvento", false);

		datasetsToLoad.put("contratosAward", false);
		datasetsToLoad.put("contratosItem", false);
		datasetsToLoad.put("contratosLot", false);
		datasetsToLoad.put("contratosLotRelItem", false);
		datasetsToLoad.put("contratosOrganization", false);
		datasetsToLoad.put("contratosProcess", false);
		datasetsToLoad.put("contratosTender", false);
		datasetsToLoad.put("contratosTenderRelItem", false);
		
		datasetsToLoad.put("bicicletaPublicaAnclaje", false);
		datasetsToLoad.put("bicicletaPublicaBicicleta", false);
		datasetsToLoad.put("bicicletaPublicaEstacion", false);
		datasetsToLoad.put("bicicletaPublicaObservacion", false);
		datasetsToLoad.put("bicicletaPublicaPuntoPaso", false);
		datasetsToLoad.put("bicicletaPublicaTrayecto", false);
		datasetsToLoad.put("bicicletaPublicaUsuario", false);
		
		//Convenios
		datasetsToLoad.put("convenio", false);
		datasetsToLoad.put("convenioDocumento", false);
		datasetsToLoad.put("convenioOrganization", false);
		datasetsToLoad.put("convenioSuscEntidad", false);
		datasetsToLoad.put("convRelFirmanteAyto", false);
		datasetsToLoad.put("convRelFirmanteEntidad", false);
		
		datasetsToLoad.put("presupuesto", false);
		datasetsToLoad.put("presupuestoLiquidacion", false);
		datasetsToLoad.put("presupuestoGasto", false);
		datasetsToLoad.put("presupuestoIngreso", false);
		datasetsToLoad.put("ejecucionGasto", false);
		datasetsToLoad.put("ejecucionIngreso", false);
		
		datasetsToLoad.put("traficoDispositivoMedicion", false);
		datasetsToLoad.put("traficoEquipo", false);
		datasetsToLoad.put("traficoIncidencia", false);
		datasetsToLoad.put("traficoObservacion", false);
		datasetsToLoad.put("traficoObservacionDispostivo", false);
		datasetsToLoad.put("traficoProperInterval", false);
		datasetsToLoad.put("traficoPropiedadMedicion", false);
		datasetsToLoad.put("traficoTramo", false);
		datasetsToLoad.put("traficoTramoVia", false);
		
		
		datasetsToLoad.put("contAcusticaEstacionMedida", false);
		datasetsToLoad.put("contAcusticaObservacion", false);
		datasetsToLoad.put("contAcusticaPropiedad", false);
		
		
		datasetsToLoad.put("busAuthority", false);
		datasetsToLoad.put("busDayType", false);
		datasetsToLoad.put("busDayTypeAssignment", false);
		datasetsToLoad.put("busHeadwayInterval", false);
		datasetsToLoad.put("busHeadwayJourneyGroup", false);
		datasetsToLoad.put("busIncidencia", false);
		datasetsToLoad.put("busJourneyPattern", false);
		datasetsToLoad.put("busLinea", false);
		datasetsToLoad.put("busOperator", false);
		datasetsToLoad.put("busParada", false);
		datasetsToLoad.put("busPointOnRoute", false);
		datasetsToLoad.put("busRealTimePassingTime", false);
		datasetsToLoad.put("busRelLineaIncidencia", false);
		datasetsToLoad.put("busRoute", false);
		datasetsToLoad.put("busScheduledStopPoint", false);
		datasetsToLoad.put("busServiceCalendar", false);
		datasetsToLoad.put("busStopPointInJourneyPattern", false);
		datasetsToLoad.put("busVehicleJourney", false);
		
		datasetsToLoad.put("boletinOficial", false);
		datasetsToLoad.put("convocatoriaEmpleoPublico", false);
		datasetsToLoad.put("ofertaEmpleoPublico", false);
		datasetsToLoad.put("plazaPorTurno", false);
		datasetsToLoad.put("relBoletinConvoca", false);
		datasetsToLoad.put("relOfertaConvoca", false);
		
		datasetsToLoad.put("deudaComercialInformeMorosidadTrimestral", false);
		datasetsToLoad.put("deudaComercialInformePMPMensual", false);
		datasetsToLoad.put("deudaComercialInformePMPMensualGlobal", false);
		datasetsToLoad.put("deudaComercialProperInterval", false);
		datasetsToLoad.put("deudaFinancieraAmortizacion", false);
		datasetsToLoad.put("deudaFinancieraAnual", false);
		datasetsToLoad.put("deudaFinancieraCapitalVivo", false);
		datasetsToLoad.put("deudaFinancieraCarga", false);
		datasetsToLoad.put("deudaFinancieraEmision", false);
		datasetsToLoad.put("deudaFinancieraInstrumentoFinanciacion", false);
		datasetsToLoad.put("deudaFinancieraPrestamo", false);
		datasetsToLoad.put("deudaFinancieraRelPrestamoEntidad", false);
		datasetsToLoad.put("deudaOrganization", false);
		
		
		datasetsToLoad.put("subvencionV1", false);
		datasetsToLoad.put("subvencionConvocatoria", false);
		datasetsToLoad.put("subvencionConcesion", false);
		datasetsToLoad.put("subvencionOrganization", false);		
	}
	
	public List<String> getDatasetNames()
	{			
		List<String> keys = new ArrayList<String>(datasetsToLoad.keySet());		
		return keys;
	}
	
	public boolean getDataset(String name) throws Exception
	{
		if (datasetsToLoad.containsKey(name)==false)
		{
			throw new Exception("Dataset not Found: "+name);
		}
		return datasetsToLoad.get(name);		
	}
	
	public boolean setDataset(String name, boolean status) throws Exception
	{
		if (datasetsToLoad.containsKey(name)==false)
		{
			throw new Exception("Dataset not Found: "+name);
		}
		return datasetsToLoad.put(name,status);
	}

	@Override
	public String toString()
	{
		String response="";
		List<String> keys =	new ArrayList<String>(datasetsToLoad.keySet());
		
		for (String key:keys)
		{
			response+=key+":"+datasetsToLoad.get(key)+"\n";
		}
		
		return "Datasets [datasetsToLoad=" + datasetsToLoad + "]";
	}
	
	
	
}

package org.ciudadesabiertas.bigFilesGenerator;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import javax.persistence.Table;

import org.apache.commons.io.FileUtils;
import org.ciudadesabiertas.dataset.model.*;
import org.ciudadesabiertas.dataset.model.v1.SubvencionV1;
import org.ciudadesabiertas.dataset.model.Process;
import org.ciudadesabiertas.dataset.utils.AparcamientoConstants;
import org.ciudadesabiertas.dataset.utils.EquipamientoConstants;
import org.ciudadesabiertas.dataset.utils.InstalacionDepConstants;
import org.ciudadesabiertas.dataset.utils.MonumentoConstants;
import org.ciudadesabiertas.dataset.utils.PuntoInteresTuristicoConstants;
import org.ciudadesabiertas.dataset.utils.PuntoWifiConstants;
import org.ciudadesabiertas.model.RDFModel;
import org.ciudadesabiertas.utils.StartVariables;
import org.ciudadesabiertas.utils.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;

public class MainBigFiles {
	private static final String RDF_OUTPUT = "rdfOutput";
	private static final String JSON_OUTPUT = "jsonOutput";
	private static final String HTML_OUTPUT = "htmlOutput";
	private static final String TO = " to ";
	private static final String ITEMS_FROM = "Items from ";
	private static final String TERRITORIO_SECCION = "territorioSeccion";
	private static final String TERRITORIO_BARRIO = "territorioBarrio";
	private static final String TERRITORIO_DISTRITO = "territorioDistrito";
	private static final String TERRITORIO_MUNICIPIO = "territorioMunicipio";
	private static final String TERRITORIO_PROVINCIA = "territorioProvincia";
	private static final String TERRITORIO_AUTONOMIA = "territorioAutonomia";
	private static final String TERRITORIO_PAIS = "territorioPais";

	private static final String METAVARIABLE_ID = "{ID}";
	private static final String METAVARIABLE_EXTENSION = "{EXTENSION}";

	private static final Logger log = initLogBack();

	private static Configuration hibernateConfiguration;
	private static SessionFactory sessions;

	private static final int limiteRDF = 10000;
	private static final int limiteJSON = 10000;

	private static Map<String, List<String>> territorioIdentifiers = new HashMap<String, List<String>>();
	private static Map<String, String> datasetURL = new HashMap<String, String>();

	private static boolean integracionCallejeroActiva = false;
	private static boolean integracionEquipamientoActiva = false;
	private static boolean integracionTraficoTramoActiva = false;
	
	
	
	private static String htmlInit3 =  "<!doctype html><html lang='es'><head><meta charset='utf-8'><title>Modelado de base de datos</title><!-- CSS -->    <link rel=\"stylesheet\" href=\"assets\\css\\main.css\"><link rel=\"stylesheet\" href=\"assets\\css\\ie_style.css\"><link rel=\"stylesheet\" href=\"assets\\css\\responsive_home.css\"><link rel=\"stylesheet\" href=\"assets\\css\\old_ie_style.css\"><!--Favicon--><link rel=\"shortcut icon\" type=\"image/x-icon\" href=\"favicon.ico\"><!-- Google Fonts --><link href=\"//fonts.googleapis.com/css?family=Source+Sans+Pro:400,700,700italic,400italic\" rel=\"stylesheet\" type=\"text/css\"><style>.noticia-destacada {    margin-bottom: 40px;    margin-top: 40px;    padding-bottom: 30px;    border-bottom: 1px solid #CED2D9;}.red__title {    color: #B73E01;    text-transform: uppercase;    margin-bottom: 15px;}</style></head><body><header class=\"navbar\"><div class=\"navbar__content\"><!-- Logo and title --><div class=\"branding\"><h1 class=\"branding__title\"><a href=\"modelado.html\" title=\"Inicio\" rel=\"home\" class=\"main-logo\"><img title=\"Logo de Ciudades abiertas. Ir a inicio\" class=\"branding__logo\" src=\"assets\\img\\cabiertas\\logo.svg\" alt=\"Logo de Ciudades abiertas. Ir a inicio\"></a></h1></div></div></header><div class=\"home\"><!-- header --><div class=\"home-header\"><img class=\"home-header__bg-img\" src=\"assets\\img\\cabiertas\\home_header-bg.png\" alt=\"Fondo\"><div class=\"home-header__content\"><div >";
	private static String htmlInit1 = "<!doctype html><html lang='es'><head><meta charset='utf-8'><title>";
	private static String htmlInit2 = "</title></head><body>";
	private static String tableInit = "<table style=\"width:100%\"><tr><th>Campo</th><th>Tipo</th><th>Ejemplo</th></tr>"+System.lineSeparator();
	private static String tableEnd = "</table>";
	private static String htmlContent = "";
	private static String htmlIndiceInit = "<h1 class=\"red__title\">Modelado de base de datos</h1><div class=\"noticia-destacada\"><ol style=\"columns: 4\">";
	private static String htmlIndiceEnd = "</ol></div>";
	private static String htmlEnd = "</div><div class=\"clear\"></div></div></div><img class='lateral-shapes lateral-shapes__left' src='assets\\img\\cabiertas\\left-shape.svg' alt='Imágen decorativa en el lateral izquierdo de la pantalla'><img class='lateral-shapes lateral-shapes__right' src='assets\\img\\cabiertas\\right-shape.svg' alt='Imágen decorativa en el lateral derecho de la pantalla'></div><footer class=\"footer\"><div class=\"footer__content\"><div class=\"footer__logos-container\"><div class=\"footer__logos-box\"><ul class=\"footer__logos-top\"><a href=\"https://www.upm.es/\" target=\"upm\"><li class=\"footer__logos footer__logoGobEspaña\"><img title=\"Logo Universidad Politécnica de Madrid\" src=\"logosUPM\\escupm01_new.jpg\" alt=\"Logo Universidad Politécnica de Madrid\"></li></a><a href=\"https://www.fi.upm.es/\" target=\"fiupm\"><li class=\"footer__logos footer__logoGobEspaña\"><img title=\"Logo Universidad Facultad de Informática\" src=\"logosUPM\\FacInformatica_new.jpg\" alt=\"Logo Facultad de Informática\"></li></a><a href=\"https://www.oeg-upm.net/\" target=\"oeg\"><li class=\"footer__logos footer__logoGobEspaña\"><img title=\"Logo Ontology Engineering Group\" src=\"logosUPM\\Logo_OEG.gif\" alt=\"Logo Ontology Engineering Group\"></li></a></ul></div></div></div></footer></body></html>";
	private static String htmlEnd2 = "</body></html>";
	public static String htmlFull = htmlInit1+"Modelado de base de datos"+htmlInit2;
	private static Set<String> keySet;

	static {
		datasetURL.put(TERRITORIO_SECCION, "/territorio/seccion-censal/" + METAVARIABLE_ID + METAVARIABLE_EXTENSION);
		datasetURL.put(TERRITORIO_BARRIO, "/territorio/barrio/" + METAVARIABLE_ID + METAVARIABLE_EXTENSION);
		datasetURL.put(TERRITORIO_DISTRITO, "/territorio/distrito/" + METAVARIABLE_ID + METAVARIABLE_EXTENSION);
		datasetURL.put(TERRITORIO_MUNICIPIO, "/territorio/municipio/" + METAVARIABLE_ID + METAVARIABLE_EXTENSION);
		datasetURL.put(TERRITORIO_PROVINCIA,
				"/territorio/provincia" + METAVARIABLE_EXTENSION + "?id=" + METAVARIABLE_ID);
		datasetURL.put(TERRITORIO_AUTONOMIA,
				"/territorio/autonomia" + METAVARIABLE_EXTENSION + "?id=" + METAVARIABLE_ID);
		datasetURL.put(TERRITORIO_PAIS, "/territorio/pais" + METAVARIABLE_EXTENSION + "?id=" + METAVARIABLE_ID);
		
		
		htmlIndiceInit=htmlIndiceInit.replace("Modelado de base de datos", "Modelado de base de datos ("+Util.dateTimeFormatterWithoutT.format(new Date())+")");
	}

	public static void initSesionFactory() {
		if (sessions == null) {
			Map<String, String> readHibernateProperties = new HashMap<String, String>();
			try {
				readHibernateProperties = readHibernateProperties();
			} catch (Exception e) {
				log.error("Error reading hibernate.properties", e);
			}

			hibernateConfiguration.setProperty("hibernate.dialect", readHibernateProperties.get("hibernate.dialect"));
			hibernateConfiguration.setProperty("hibernate.connection.url",
					readHibernateProperties.get("hibernate.connection.url"));
			hibernateConfiguration.setProperty("hibernate.connection.username",
					readHibernateProperties.get("hibernate.connection.username"));
			hibernateConfiguration.setProperty("hibernate.connection.password",
					UtilBigFiles.checkAndSetEncodedProperty(readHibernateProperties.get("hibernate.connection.password")));
			hibernateConfiguration.setProperty("hibernate.connection.driver_class",
					readHibernateProperties.get("hibernate.connection.driver_class"));
			
			if (Util.validValue(readHibernateProperties.get("hibernate.default_schema")) )
			{
			  hibernateConfiguration.setProperty("hibernate.default_schema",readHibernateProperties.get("hibernate.default_schema"));
			}
			
			
			sessions = hibernateConfiguration.buildSessionFactory();
		}

	}

	public static void initDatasetClasses(Configuration config) {
		
		config.addAnnotatedClass(SubvencionV1.class);
		config.addAnnotatedClass(SubvencionConvocatoria.class);
		config.addAnnotatedClass(SubvencionConcesion.class);
		config.addAnnotatedClass(SubvencionOrganization.class);				
				
		config.addAnnotatedClass(Agenda.class);
		config.addAnnotatedClass(Alojamiento.class);
		config.addAnnotatedClass(Equipamiento.class);
		config.addAnnotatedClass(AvisoQuejaSug.class);
		config.addAnnotatedClass(CalidadAireEstacion.class);
		config.addAnnotatedClass(CalidadAireObservacion.class);
		config.addAnnotatedClass(CalidadAireSensor.class);
		config.addAnnotatedClass(CallejeroPortal.class);
		config.addAnnotatedClass(CallejeroTramoVia.class);
		config.addAnnotatedClass(CallejeroVia.class);
		config.addAnnotatedClass(Equipamiento.class);
		config.addAnnotatedClass(AgrupacionComercial.class);
		config.addAnnotatedClass(LicenciaActividad.class);
		config.addAnnotatedClass(LocalComercial.class);
		config.addAnnotatedClass(Terraza.class);
		config.addAnnotatedClass(Organigrama.class);
		config.addAnnotatedClass(PuntoInteresTuristico.class);
		config.addAnnotatedClass(Tramite.class);
		// CUBOS y DSD
		config.addAnnotatedClass(CubeDsd.class);
		config.addAnnotatedClass(CubeDsdDimension.class);
		config.addAnnotatedClass(CubeDsdDimensionValue.class);
		config.addAnnotatedClass(CubeDsdMeasure.class);
		config.addAnnotatedClass(CuboEdad.class);
		config.addAnnotatedClass(CuboEstudios.class);
		config.addAnnotatedClass(CuboIndicadores.class);
		config.addAnnotatedClass(CuboNacionalidad.class);
		config.addAnnotatedClass(CuboProcedencia.class);
		config.addAnnotatedClass(CuboPaisNacimiento.class);
		config.addAnnotatedClass(CuboEdadGrupoQuinquenal.class);
		// FIN CUBOS Y DSD
		config.addAnnotatedClass(Pais.class);
		config.addAnnotatedClass(Autonomia.class);
		config.addAnnotatedClass(Provincia.class);
		config.addAnnotatedClass(Municipio.class);
		config.addAnnotatedClass(Distrito.class);
		config.addAnnotatedClass(Barrio.class);
		config.addAnnotatedClass(SeccionCensal.class);
		config.addAnnotatedClass(AgendaMEvento.class);
		config.addAnnotatedClass(AgendaMDocumento.class);
		config.addAnnotatedClass(AgendaMRolEvento.class);
		config.addAnnotatedClass(Award.class);
		config.addAnnotatedClass(Item.class);
		config.addAnnotatedClass(Lot.class);
		config.addAnnotatedClass(LotRelItem.class);
		config.addAnnotatedClass(Organization.class);
		config.addAnnotatedClass(Process.class);
		config.addAnnotatedClass(Tender.class);
		config.addAnnotatedClass(TenderRelItem.class);

		config.addAnnotatedClass(BicicletaPublicaAnclaje.class);
		config.addAnnotatedClass(BicicletaPublicaBicicleta.class);
		config.addAnnotatedClass(BicicletaPublicaEstacion.class);
		config.addAnnotatedClass(BicicletaPublicaObservacion.class);
		config.addAnnotatedClass(BicicletaPublicaPuntoPaso.class);
		config.addAnnotatedClass(BicicletaPublicaTrayecto.class);
		config.addAnnotatedClass(BicicletaPublicaUsuario.class);

		// Convenio
		config.addAnnotatedClass(Convenio.class);
		config.addAnnotatedClass(ConvenioDocumento.class);
		config.addAnnotatedClass(ConvenioOrganization.class);
		config.addAnnotatedClass(ConvenioSuscEntidad.class);
		config.addAnnotatedClass(ConvRelFirmanteAyto.class);
		config.addAnnotatedClass(ConvRelFirmanteEntidad.class);

		config.addAnnotatedClass(Presupuesto.class);
		config.addAnnotatedClass(PresupuestoLiquidacion.class);
		config.addAnnotatedClass(PresupuestoGasto.class);
		config.addAnnotatedClass(PresupuestoIngreso.class);
		config.addAnnotatedClass(EjecucionGasto.class);
		config.addAnnotatedClass(EjecucionIngreso.class);

		config.addAnnotatedClass(TraficoDispositivoMedicion.class);
		config.addAnnotatedClass(TraficoEquipo.class);
		config.addAnnotatedClass(TraficoIncidencia.class);
		config.addAnnotatedClass(TraficoObservacion.class);
		config.addAnnotatedClass(TraficoObservacionDispostivo.class);
		config.addAnnotatedClass(TraficoProperInterval.class);
		config.addAnnotatedClass(TraficoPropiedadMedicion.class);
		config.addAnnotatedClass(TraficoTramo.class);
		config.addAnnotatedClass(TraficoTramoVia.class);

		config.addAnnotatedClass(ContAcusticaEstacionMedida.class);
		config.addAnnotatedClass(ContAcusticaObservacion.class);
		config.addAnnotatedClass(ContAcusticaPropiedad.class);

		config.addAnnotatedClass(Authority.class);
		config.addAnnotatedClass(DayType.class);
		config.addAnnotatedClass(DayTypeAssignment.class);
		config.addAnnotatedClass(HeadwayInterval.class);
		config.addAnnotatedClass(HeadwayJourneyGroup.class);
		config.addAnnotatedClass(Incidencia.class);
		config.addAnnotatedClass(JourneyPattern.class);
		config.addAnnotatedClass(Linea.class);
		config.addAnnotatedClass(Operator.class);
		config.addAnnotatedClass(Parada.class);
		config.addAnnotatedClass(PointOnRoute.class);
		config.addAnnotatedClass(RealTimePassingTime.class);
		config.addAnnotatedClass(RelLineaIncidencia.class);
		config.addAnnotatedClass(Route.class);
		config.addAnnotatedClass(ScheduledStopPoint.class);
		config.addAnnotatedClass(ServiceCalendar.class);
		config.addAnnotatedClass(StopPointInJourneyPattern.class);
		config.addAnnotatedClass(VehicleJourney.class);

		
		config.addAnnotatedClass(BoletinOficial.class);
		config.addAnnotatedClass(ConvocatoriaEmpleoPublico.class);
		config.addAnnotatedClass(OfertaEmpleoPublico.class);
		config.addAnnotatedClass(PlazaPorTurno.class);
		config.addAnnotatedClass(RelBoletinConvoca.class);
		config.addAnnotatedClass(RelOfertaConvoca.class);
		
		
		config.addAnnotatedClass(DeudaComercialInformeMorosidadTrimestral.class);
		config.addAnnotatedClass(DeudaComercialInformePMPMensual.class);
		config.addAnnotatedClass(DeudaComercialInformePMPMensualGlobal.class);
		config.addAnnotatedClass(DeudaComercialProperInterval.class);
		config.addAnnotatedClass(DeudaFinancieraAmortizacion.class);
		config.addAnnotatedClass(DeudaFinancieraAnual.class);
		config.addAnnotatedClass(DeudaFinancieraCapitalVivo.class);
		config.addAnnotatedClass(DeudaFinancieraCarga.class);
		config.addAnnotatedClass(DeudaFinancieraEmision.class);
		config.addAnnotatedClass(DeudaFinancieraInstrumentoFinanciacion.class);
		config.addAnnotatedClass(DeudaFinancieraPrestamo.class);
		config.addAnnotatedClass(DeudaFinancieraRelPrestamoEntidad.class);
		config.addAnnotatedClass(DeudaOrganization.class);
		
				
		
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> List<T> dataset(String dataset) throws Exception {
		List<T> result = new ArrayList<T>();

		Session session = sessions.openSession();

		String queryText = "";

		if (dataset.equals("subvencionV1")) {
			queryText = "From SubvencionV1";
		}else if (dataset.equals("subvencionConvocatoria")) {
			queryText = "From SubvencionConvocatoria";
		}else if (dataset.equals("subvencionConcesion")) {
			queryText = "From SubvencionConcesion";
		}else if (dataset.equals("subvencionOrganization")) {	
			queryText = "From SubvencionOrganization";
		} else if (dataset.equals("agendaCultural")) {
			queryText = "From Agenda";
		} else if (dataset.equals("alojamiento")) {
			queryText = "From Alojamiento";
		} else if (dataset.equals("aparcamiento")) {
			queryText = "From Equipamiento";
			queryText += " where " + AparcamientoConstants.TIPO_EQUIPAMIENTO_FIELD + " like '"
					+ AparcamientoConstants.TIPO_EQUIPAMIENTO + "'";
		} else if (dataset.equals("avisoQuejaSugerencia")) {
			queryText = "From AvisoQuejaSug";
		} else if (dataset.equals("localComercial")) {
			queryText = "From LocalComercial";
		} else if (dataset.equals("localComercialAgrupacionComercial")) {
			queryText = "From AgrupacionComercial";
		} else if (dataset.equals("localComercialLicenciaActividad")) {
			queryText = "From LicenciaActividad";
		} else if (dataset.equals("localComercialTerraza")) {
			queryText = "From Terraza";
		} else if (dataset.equals("puntoInteresTuristico")) {
			queryText = "From PuntoInteresTuristico";
			queryText += " where " + PuntoInteresTuristicoConstants.CATEGORY_FIELD + " like '"
					+ PuntoInteresTuristicoConstants.CATEGORY + "'";
		} else if (dataset.equals("monumento")) {
			queryText = "From PuntoInteresTuristico";
			queryText += " where " + PuntoInteresTuristicoConstants.CATEGORY_FIELD + " like '"
					+ MonumentoConstants.CATEGORY + "'";
		} else if (dataset.equals("calidadAireEstacion")) {
			queryText = "From CalidadAireEstacion";
		} else if (dataset.equals("calidadAireObservacion")) {
			queryText = "From CalidadAireObservacion";
		} else if (dataset.equals("calidadAireSensor")) {
			queryText = "From CalidadAireSensor";
		} else if (dataset.equals("callejeroPortal")) {
			queryText = "From CallejeroPortal";
		} else if (dataset.equals("callejeroTramoVia")) {
			queryText = "From CallejeroTramoVia";
		} else if (dataset.equals("callejeroVia")) {
			queryText = "From CallejeroVia";
		} else if (dataset.equals("equipamiento")) {
			queryText = "From Equipamiento";
			queryText += " where " + EquipamientoConstants.TIPO_EQUIPAMIENTO_FIELD + " like '"
					+ EquipamientoConstants.TIPO_EQUIPAMIENTO + "' ";
		} else if (dataset.equals("instalacionDeportiva")) {
			queryText = "From Equipamiento";
			queryText += " where " + EquipamientoConstants.TIPO_EQUIPAMIENTO_FIELD + " like '"
					+ InstalacionDepConstants.TIPO_EQUIPAMIENTO + "' ";
		} else if (dataset.equals("organigrama")) {
			queryText = "From Organigrama";
		} else if (dataset.equals("puntoWifi")) {
			queryText = "From Equipamiento";
			queryText += " where " + EquipamientoConstants.TIPO_EQUIPAMIENTO_FIELD + " like '"
					+ PuntoWifiConstants.TIPO_EQUIPAMIENTO + "' ";
		} else if (dataset.equals("tramite")) {
			queryText = "From Tramite";
		}
		// INICIO CUBOS Y DSD
		else if (dataset.equals("dsd")) {
			queryText = "From CubeDsd";
		} else if (dataset.equals("dsdDimension")) {
			queryText = "From CubeDsdDimension";
		} else if (dataset.equals("dsdDimensionValue")) {
			queryText = "From CubeDsdDimensionValue";
		} else if (dataset.equals("dsdMeasure")) {
			queryText = "From CubeDsdMeasure";
		} else if (dataset.equals("dsdRelDimension")) {
			queryText = "From CubeDsdRelDimension";
		} else if (dataset.equals("padronCuboEdad")) {
			queryText = "From CuboEdad";
		} else if (dataset.equals("padronCuboEstudios")) {
			queryText = "From CuboEstudios";
		} else if (dataset.equals("padronCuboIndicadores")) {
			queryText = "From CuboIndicadores";
		} else if (dataset.equals("padronCuboNacionalidad")) {
			queryText = "From CuboNacionalidad";
		} else if (dataset.equals("padronCuboProcedencia")) {
			queryText = "From CuboProcedencia";
		} else if (dataset.equals("padronCuboEdadGrupoQuinquenal")) {
			queryText = "From CuboEdadGrupoQuinquenal";
		} else if (dataset.equals("padronCuboPaisNacimiento")) {
			queryText = "From CuboPaisNacimiento";
		}

		// FIN CUBOS Y DSD
		else if (dataset.equals(TERRITORIO_PAIS)) {
			queryText = "From Pais";
		} else if (dataset.equals(TERRITORIO_AUTONOMIA)) {
			queryText = "From Autonomia";
		} else if (dataset.equals(TERRITORIO_PROVINCIA)) {
			queryText = "From Provincia";
		} else if (dataset.equals(TERRITORIO_MUNICIPIO)) {
			queryText = "From Municipio";
		} else if (dataset.equals(TERRITORIO_DISTRITO)) {
			queryText = "From Distrito";
		} else if (dataset.equals(TERRITORIO_BARRIO)) {
			queryText = "From Barrio";
		} else if (dataset.equals(TERRITORIO_SECCION)) {
			queryText = "From SeccionCensal";
		} else if (dataset.equals("agendaMunicipalEvento")) {
			queryText = "From AgendaMEvento";
		} else if (dataset.equals("agendaMunicipalDocumento")) {
			queryText = "From AgendaMDocumento";
		} else if (dataset.equals("agendaMunicipalRolEvento")) {
			queryText = "From AgendaMRolEvento";
		} else if (dataset.equals("contratosAward")) {
			queryText = "From Award";
		} else if (dataset.equals("contratosItem")) {
			queryText = "From Item";
		} else if (dataset.equals("contratosOrganization")) {
			queryText = "From Organization";
		} else if (dataset.equals("contratosLot")) {
			queryText = "From Lot";
		} else if (dataset.equals("contratosLotRelItem")) {
			queryText = "From LotRelItem";
		} else if (dataset.equals("contratosProcess")) {
			queryText = "From Process";
		} else if (dataset.equals("contratosTender")) {
			queryText = "From Tender";
		} else if (dataset.equals("contratosTenderRelItem")) {
			queryText = "From TenderRelItem";
		} else if (dataset.equals("bicicletaPublicaAnclaje")) {
			queryText = "From BicicletaPublicaAnclaje";
		} else if (dataset.equals("bicicletaPublicaBicicleta")) {
			queryText = "From BicicletaPublicaBicicleta";
		} else if (dataset.equals("bicicletaPublicaEstacion")) {
			queryText = "From BicicletaPublicaEstacion";
		} else if (dataset.equals("bicicletaPublicaObservacion")) {
			queryText = "From BicicletaPublicaObservacion";
		} else if (dataset.equals("bicicletaPublicaPuntoPaso")) {
			queryText = "From BicicletaPublicaPuntoPaso";
		} else if (dataset.equals("bicicletaPublicaTrayecto")) {
			queryText = "From BicicletaPublicaTrayecto";
		} else if (dataset.equals("bicicletaPublicaUsuario")) {
			queryText = "From BicicletaPublicaUsuario";
		}
		// CONVENIOS
		else if (dataset.equals("convenio")) {
			queryText = "From Convenio";
		} else if (dataset.equals("convenioDocumento")) {
			queryText = "From ConvenioDocumento";
		} else if (dataset.equals("convenioOrganization")) {
			queryText = "From ConvenioOrganization";
		} else if (dataset.equals("convenioSuscEntidad")) {
			queryText = "From ConvenioSuscEntidad";
		} else if (dataset.equals("convRelFirmanteAyto")) {
			queryText = "From ConvRelFirmanteAyto";
		} else if (dataset.equals("convRelFirmanteEntidad")) {
			queryText = "From ConvRelFirmanteEntidad";
		}

		else if (dataset.equals("presupuesto")) {
			queryText = "From Presupuesto";
		} else if (dataset.equals("presupuestoLiquidacion")) {
			queryText = "From PresupuestoLiquidacion";
		} else if (dataset.equals("presupuestoGasto")) {
			queryText = "From PresupuestoGasto";
		} else if (dataset.equals("presupuestoIngreso")) {
			queryText = "From PresupuestoIngreso";
		} else if (dataset.equals("ejecucionGasto")) {
			queryText = "From EjecucionGasto";
		} else if (dataset.equals("ejecucionIngreso")) {
			queryText = "From EjecucionIngreso";
		}

		else if (dataset.equals("traficoDispositivoMedicion")) {
			queryText = "From TraficoDispositivoMedicion";
		} else if (dataset.equals("traficoEquipo")) {
			queryText = "From TraficoEquipo";
		} else if (dataset.equals("traficoIncidencia")) {
			queryText = "From TraficoIncidencia";
		} else if (dataset.equals("traficoObservacion")) {
			queryText = "From TraficoObservacion";
		} else if (dataset.equals("traficoObservacionDispostivo")) {
			queryText = "From TraficoObservacionDispostivo";
		} else if (dataset.equals("traficoProperInterval")) {
			queryText = "From TraficoProperInterval";
		} else if (dataset.equals("traficoPropiedadMedicion")) {
			queryText = "From TraficoPropiedadMedicion";
		} else if (dataset.equals("traficoTramo")) {
			queryText = "From TraficoTramo";
		} else if (dataset.equals("traficoTramoVia")) {
			queryText = "From TraficoTramoVia";
		} else if (dataset.equals("contAcusticaEstacionMedida")) {
			queryText = "From ContAcusticaEstacionMedida";
		} else if (dataset.equals("contAcusticaObservacion")) {
			queryText = "From ContAcusticaObservacion";
		} else if (dataset.equals("contAcusticaPropiedad")) {
			queryText = "From ContAcusticaPropiedad";
		}
		// Autobus
		else if (dataset.equals("busAuthority")) {
			queryText = "From Authority";
		} else if (dataset.equals("busDayType")) {
			queryText = "From DayType";
		} else if (dataset.equals("busDayTypeAssignment")) {
			queryText = "From DayTypeAssignment";
		} else if (dataset.equals("busHeadwayInterval")) {
			queryText = "From HeadwayInterval";
		} else if (dataset.equals("busHeadwayJourneyGroup")) {
			queryText = "From HeadwayJourneyGroup";
		} else if (dataset.equals("busIncidencia")) {
			queryText = "From Incidencia";
		} else if (dataset.equals("busJourneyPattern")) {
			queryText = "From JourneyPattern";
		} else if (dataset.equals("busLinea")) {
			queryText = "From Linea";
		} else if (dataset.equals("busOperator")) {
			queryText = "From Operator";
		} else if (dataset.equals("busParada")) {
			queryText = "From Parada";
		} else if (dataset.equals("busPointOnRoute")) {
			queryText = "From PointOnRoute";
		} else if (dataset.equals("busRealTimePassingTime")) {
			queryText = "From RealTimePassingTime";
		} else if (dataset.equals("busRelLineaIncidencia")) {
			queryText = "From RelLineaIncidencia";
		} else if (dataset.equals("busRoute")) {
			queryText = "From Route";
		} else if (dataset.equals("busScheduledStopPoint")) {
			queryText = "From ScheduledStopPoint";
		} else if (dataset.equals("busServiceCalendar")) {
			queryText = "From ServiceCalendar";
		} else if (dataset.equals("busStopPointInJourneyPattern")) {
			queryText = "From StopPointInJourneyPattern";
		} else if (dataset.equals("busVehicleJourney")) {
			queryText = "From VehicleJourney";			
		//Empleo
		} else if (dataset.equals("boletinOficial")) {
			queryText = "From BoletinOficial";
		} else if (dataset.equals("convocatoriaEmpleoPublico")) {
			queryText = "From ConvocatoriaEmpleoPublico";
		} else if (dataset.equals("ofertaEmpleoPublico")) {
			queryText = "From OfertaEmpleoPublico";
		} else if (dataset.equals("plazaPorTurno")) {
			queryText = "From PlazaPorTurno";
		} else if (dataset.equals("relBoletinConvoca")) {
			queryText = "From RelBoletinConvoca";
		} else if (dataset.equals("relOfertaConvoca")) {
			queryText = "From RelOfertaConvoca";		
		//Deuda
		} else if (dataset.equals("deudaComercialInformeMorosidadTrimestral")) {
			queryText = "From DeudaComercialInformeMorosidadTrimestral";
		} else if (dataset.equals("deudaComercialInformePMPMensual")) {
			queryText = "From DeudaComercialInformePMPMensual";
		} else if (dataset.equals("deudaComercialInformePMPMensualGlobal")) {
			queryText = "From DeudaComercialInformePMPMensualGlobal";
		} else if (dataset.equals("deudaComercialInformePMPMensualGlobal")) {
			queryText = "From DeudaComercialInformePMPMensualGlobal";
		} else if (dataset.equals("deudaComercialProperInterval")) {
			queryText = "From DeudaComercialProperInterval";
		} else if (dataset.equals("deudaFinancieraAmortizacion")) {
			queryText = "From DeudaFinancieraAmortizacion";
		} else if (dataset.equals("deudaFinancieraAnual")) {
			queryText = "From DeudaFinancieraAnual";
		} else if (dataset.equals("deudaFinancieraCapitalVivo")) {
			queryText = "From DeudaFinancieraCapitalVivo";
		} else if (dataset.equals("deudaFinancieraCarga")) {
			queryText = "From DeudaFinancieraCarga";
		} else if (dataset.equals("deudaFinancieraEmision")) {
			queryText = "From DeudaFinancieraEmision";
		} else if (dataset.equals("deudaFinancieraInstrumentoFinanciacion")) {
			queryText = "From DeudaFinancieraInstrumentoFinanciacion";
		} else if (dataset.equals("deudaFinancieraPrestamo")) {
			queryText = "From DeudaFinancieraPrestamo";
		} else if (dataset.equals("deudaFinancieraRelPrestamoEntidad")) {
			queryText = "From DeudaFinancieraRelPrestamoEntidad";
		} else if (dataset.equals("deudaOrganization")) {
			queryText = "From DeudaOrganization";
		} 	
		
		if (queryText.equals("")) {
			throw new Exception("Dataset without control: "+dataset);
		}

		Query query = session.createQuery(queryText);
		result = query.list();

		log.info("There are " + result.size() + " items");

		session.close();

		return result;
	}

	public static <T> void generarJSON(List<T> listado, String path, String jsonFileName) {
		log.info("JSON");
		int limite = limiteJSON;
		String extension = ".json";
		File filePath = new File(path);
		if (filePath.exists() == false) {
			filePath.mkdir();
		}

		if (listado.size() <= limite) {
			UtilBigFiles.toJSON(listado, path + File.separator + jsonFileName + extension);
		} else {
			int numFiles = listado.size() / limite;
			int rest = listado.size() % limite;

			for (int i = 0; i < numFiles; i++) {
				int start = i * limite;
				int stop = ((i + 1) * limite) - 1;
				log.info(ITEMS_FROM + start + TO + stop);
				UtilBigFiles.toJSON(listado.subList(start, stop), path + File.separator + jsonFileName + i + extension);

			}
			if (rest > 0) {
				int start = numFiles * limite;
				int stop = start + rest;
				log.info(ITEMS_FROM + start + TO + stop);
				UtilBigFiles.toJSON(listado.subList(start, stop),
						path + File.separator + jsonFileName + (numFiles + 1) + extension);
			}
		}

	}

	public static <T> void generarRDF(List<T> listado, String path, String turtleFileName, String URIBase,
			String context) {
		log.info("RDF");
		int limite = limiteRDF;
		String extension = ".ttl";
		File filePath = new File(path);
		if (filePath.exists() == false) {
			filePath.mkdir();
		}

		// Util.deleteFilesInFolder(path);
		// CMG: 08-10-2020  integración callejero
		if (UtilBigFiles.isObjectICallejero(listado)) {
			UtilBigFiles.getInfoCallejero(listado, integracionCallejeroActiva);
		}
		
		
		// CMG: 15-10-2020  integración callejeroVia
		if (UtilBigFiles.isObjectICallejeroVia(listado)) {
			UtilBigFiles.getInfoCallejeroVia(listado, integracionCallejeroActiva);
		}

		// Util.deleteFilesInFolder(path);
		// CMG: 09-10-2020  integración equipamiento
		if (UtilBigFiles.isObjectIEquipamiento(listado)) {
			UtilBigFiles.getInfoEquipamiento(listado, integracionEquipamientoActiva);
		}
		
		//CMG: 30-11-2020  integración con traficoTramo
		if (UtilBigFiles.isObjectITramoIncidencia(listado)) {
			UtilBigFiles.getInfoITramoIncidencia(listado, integracionTraficoTramoActiva);
		}
		
		//CMG: 17-12-2020  integración con GestionaPor
		if (UtilBigFiles.isObjectGestionadoPor(listado)) {
			UtilBigFiles.getGestionadoPor(listado);
		}

		//CMG: 17-12-2020  integración con Convenio
		if (UtilBigFiles.isObjectIConvenio(listado)) {
			UtilBigFiles.getIntegraConvenio(listado);
		}

		if (listado.size() <= limite) {
			UtilBigFiles.toRDF(listado, path + File.separator + turtleFileName + extension, URIBase, context);
		} else {
			int numFiles = listado.size() / limite;
			int rest = listado.size() % limite;

			for (int i = 0; i < numFiles; i++) {
				int start = i * limite;
				int stop = ((i + 1) * limite) - 1;
				log.info(ITEMS_FROM + start + TO + stop);
				UtilBigFiles.toRDF(listado.subList(start, stop), path + File.separator + turtleFileName + i + extension,
						URIBase, context);

			}
			if (rest > 0) {
				int start = numFiles * limite;
				int stop = start + rest;
				log.info(ITEMS_FROM + start + TO + stop);

				UtilBigFiles.toRDF(listado.subList(start, stop),
						path + File.separator + turtleFileName + (numFiles + 1) + extension, URIBase, context);

			}
		}
	}
	
	public static <T> void generarHTML(List<T> listado, String path, String htmlFileName) throws Exception {
		log.info("HTML");		
		List<T> resultColumnas = new ArrayList<T>();
		List<T> resultEjemplos = new ArrayList<T>();

		String extension = ".html";
		File filePath = new File(path);
		if (filePath.exists() == false) {
			filePath.mkdir();
		}
						
		Table  table = listado.get(0).getClass().getAnnotation(Table.class);
		String nameEntity = table.name();
		
		if(nameEntity.equals("equipamiento") || nameEntity.equals("punto_interes_turistico")) {
			htmlIndiceInit = htmlIndiceInit + "<li><a href=\"#"+nameEntity+"\">"+nameEntity+" ("+htmlFileName+")</a></li>";
		}else {
			htmlIndiceInit = htmlIndiceInit + "<li><a href=\"#"+nameEntity+"\">"+nameEntity+"</a></li>";
		}
		
		
		String htmlEntity = "";
		String tableLine = "";
		Session session = sessions.openSession();		
		if(hibernateConfiguration.getProperty("hibernate.connection.driver_class").equals("com.mysql.jdbc.Driver"))	{
			String queryColums1 = "SHOW COLUMNS FROM ";
			
			String queryExample = "select * from ";
			String queryLimit = " LIMIT 50";
			
			Query queryColumnas = session.createNativeQuery(queryColums1+nameEntity);
			resultColumnas = queryColumnas.list();
			log.info("columns size: "+resultColumnas.size());
			
			Query queryEjemplos = session.createNativeQuery(queryExample+nameEntity+queryLimit);
			resultEjemplos = queryEjemplos.list();
			log.info("examples size: "+resultEjemplos.size());
			
			
			for (int i = 0; i < resultColumnas.size(); i++) {
				Object[] o= (Object[]) resultColumnas.get(i);
				
				
				String field = "";
				if(o[0] != null)
				{
					field = o[0].toString();
				}
				
				String campo = "";
				if(o[1] != null)
				{
					campo = o[1].toString();
				}
				
				String ejemplo = "";
				for (int h = 0; h < resultEjemplos.size(); h++) {
					Object[] o2= (Object[]) resultEjemplos.get(h);
					if(o2[i] != null)
					{
						ejemplo = o2[i].toString();
						break;
					}
				}
				
				
				tableLine = tableLine + "<tr><td>"+field+"</td><td>"+campo+"</td><td>"+ejemplo+"</td></tr>"+System.lineSeparator();
			}
			
			
		}else if(hibernateConfiguration.getProperty("hibernate.connection.driver_class").toLowerCase().contains("sqlserver"))	{
			
			String queryColumns="";
			queryColumns+="SELECT OBJECT_SCHEMA_NAME (c.object_id) SchemaName, ";
			queryColumns+="o.Name AS Table_Name, ";
			queryColumns+="c.Name AS Field_Name, ";
			queryColumns+=" t.Name AS Data_Type, ";
			queryColumns+="c.precision AS Precision, ";
			queryColumns+="c.scale AS Scale, ";
			queryColumns+="c.max_length AS Length_Size ";
			queryColumns+="FROM sys.columns c ";
			queryColumns+="     INNER JOIN sys.objects o ON o.object_id = c.object_id ";
			queryColumns+="     LEFT JOIN  sys.types t on t.user_type_id  = c.user_type_id   ";
			queryColumns+="WHERE o.type = 'U' ";
			queryColumns+="and  OBJECT_SCHEMA_NAME (c.object_id) like '"+hibernateConfiguration.getProperty("hibernate.default_schema")+"' ";
			queryColumns+="and o.Name = '"+nameEntity+"' ";
			//queryColumns+="ORDER BY o.Name, c.Name";
			
			String queryExample = "select TOP(50) * from "+nameEntity;
			
			
			Query queryColumnas = session.createNativeQuery(queryColumns);
			resultColumnas = queryColumnas.list();
			//log.info("columns size: "+resultColumnas.size());
			
			//log.info(queryExample);
			Query queryEjemplos = session.createNativeQuery(queryExample);
			resultEjemplos = queryEjemplos.list();
			//log.info("examples size: "+resultEjemplos.size());
			
			for (int i = 0; i < resultColumnas.size(); i++) {
				Object[] o= (Object[]) resultColumnas.get(i);
				
				String field = "";
				if(o[0] != null)
				{
					field = o[2].toString();
				}
				
				String tipoCampo = "";
				if(o[1] != null)
				{
					tipoCampo = o[3].toString();
				}
				if (tipoCampo.toLowerCase().equals("varchar"))
				{
					tipoCampo+= "("+o[6].toString();
				}
				else 
				{
					if(o[2] != null)
					{
						tipoCampo+= "("+o[4].toString();
					}
					if(o[3] != null)
					{
						tipoCampo+= ","+o[5].toString();
					}				
				}
				tipoCampo+=")";
				
				String ejemplo = "";
				for (int h = 0; h < resultEjemplos.size(); h++) {
					Object[] o2= (Object[]) resultEjemplos.get(h);
					if(i<o2.length && o2[i] != null)
					{
						ejemplo = o2[i].toString();
						break;
					}
				}
				
				
				tableLine = tableLine + "<tr><td>"+field+"</td><td>"+tipoCampo+"</td><td>"+ejemplo+"</td></tr>"+System.lineSeparator();
			}
					
		} else {
			throw new Exception ("Database not supported");
		}

		htmlContent = htmlContent + "<h2 class=\"red__title\" id='"+nameEntity+"'>"+nameEntity+"</h2><div class='noticia-destacada'>" + tableInit + tableLine + tableEnd + "</div>";
//		htmlEnd = "<h1 id='"+nameEntity+"'>"+nameEntity+"</h1><div>" + tableInit + tableLine + tableEnd + "</div>" + htmlEnd;
		htmlEntity = htmlInit1+nameEntity+htmlInit2+tableInit+tableLine+tableEnd+htmlEnd2;
		
		try {
			FileUtils.write(new File(path + File.separator + htmlFileName + extension), htmlEntity, "UTF-8");
		} catch (IOException e) {
			log.error("Error writting html file",e);
		}
		
		session.close();
		
	}

	private static Map<String, String> readConfigProperties() throws Exception {
		Map<String, String> config = new HashMap<String, String>();

		InputStream input = new FileInputStream("config.properties");

		Properties prop = new Properties();
		prop.load(input);

		Set<Entry<Object, Object>> entrySet = prop.entrySet();

		Iterator<Entry<Object, Object>> iterator = entrySet.iterator();

		while (iterator.hasNext()) {
			Entry<Object, Object> next = iterator.next();

			// Boolean value=Boolean.parseBoolean( (String) next.getValue());

			config.put((String) next.getKey(), next.getValue().toString().trim());
		}

		return config;

	}

	private static Map<String, String> readHibernateProperties() throws Exception {
		Map<String, String> config = new HashMap<String, String>();

		InputStream input = new FileInputStream("hibernate.properties");

		Properties prop = new Properties();
		prop.load(input);

		Set<Entry<Object, Object>> entrySet = prop.entrySet();

		Iterator<Entry<Object, Object>> iterator = entrySet.iterator();

		while (iterator.hasNext()) {
			Entry<Object, Object> next = iterator.next();

			config.put((String) next.getKey(), (String) next.getValue());
		}

		return config;

	}

	public static Logger initLogBack() {
		Logger log = null;
		try {
			LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
			context.reset();
			JoranConfigurator configurator = new JoranConfigurator();
			configurator.setContext(context);
			configurator.doConfigure(new File(System.getProperty("user.dir") + File.separator + "logback.xml"));
			log = LoggerFactory.getLogger(MainBigFiles.class);
		} catch (Exception e) {
			log.error("Errore reading logbax.xml", e);
		}
		return log;
	}

	public static void main(String[] args) {
		String URIBase = "https://ciudadesabiertas.es";
		String context = "/OpenCitiesAPI";
		boolean formatoJSON = false;
		boolean formatoRDF = false;
		boolean formatoHTML = false;
		boolean deleteFolder = false;
		boolean territorioUpdate = false;

		log.info("start");
		hibernateConfiguration = new Configuration();

		Map<String, String> configProperties = new HashMap<String, String>();

		try {
			configProperties = readConfigProperties();
		} catch (Exception e) {
			log.error("Error reading config.properties", e);
			return;
		}

		try {
			URIBase = configProperties.get("URIBase");
			configProperties.remove("URIBase");
			context = configProperties.get("context");
			configProperties.remove("context");
			formatoRDF = Boolean.parseBoolean(configProperties.get("formatoRDF"));
			configProperties.remove("formatoRDF");
			formatoJSON = Boolean.parseBoolean(configProperties.get("formatoJSON"));
			configProperties.remove("formatoJSON");
			formatoHTML = Boolean.parseBoolean(configProperties.get("formatoHTML"));
			configProperties.remove("formatoHTML");
			deleteFolder = Boolean.parseBoolean(configProperties.get("deleteOutputFolderOnInit"));
			configProperties.remove("deleteOutputFolderOnInit");
			territorioUpdate = Boolean.parseBoolean(configProperties.get("territorioUpdate"));
			configProperties.remove("territorioUpdate");
			integracionEquipamientoActiva = Boolean.parseBoolean(configProperties.get("equipamiento"));
			integracionCallejeroActiva = Boolean.parseBoolean(configProperties.get("callejeroPortal"));
			integracionTraficoTramoActiva = Boolean.parseBoolean(configProperties.get("traficoTramo"));
			
		} catch (Exception e) {
			log.error("Error with configuration info", e);
			return;
		}

		if ((formatoJSON == false) && (formatoRDF == false) && (formatoHTML == false)) {
			log.info("Both format are setted as false, I can't work");
			return;
		}

		if (deleteFolder) {
			log.info("old files clean activated");
			if ((formatoJSON) || (formatoRDF) || (formatoHTML)) {
				if (formatoJSON) {
					File filePath = new File(JSON_OUTPUT);
					if (filePath.exists()) {
						UtilBigFiles.deleteFilesInFolder(JSON_OUTPUT);
					}
				}
				if (formatoRDF) {
					File filePath = new File(RDF_OUTPUT);
					if (filePath.exists()) {
						UtilBigFiles.deleteFilesInFolder(RDF_OUTPUT);
					}
				}
				if (formatoHTML) {
					File filePath = new File(HTML_OUTPUT);
					if (filePath.exists()) {
						UtilBigFiles.deleteFilesInFolder(HTML_OUTPUT, "html");
					}
				}
			}
		}

		if (configProperties.size() > 0) {
			initDatasetClasses(hibernateConfiguration);
			initSesionFactory();

			keySet = configProperties.keySet();
			ArrayList<String> list = new ArrayList<String>(keySet);
			Collections.sort(list);
			
			for (String dataset : list) {
				if (configProperties.get(dataset).toString().equals("true")) {
					log.info("......................" + dataset);
					List listado = new ArrayList();
					try {
						listado = dataset(dataset);
					} catch (Exception e) {
						log.error("Error retrieving data", e);
					}
					if (listado.size() > 0) {
						// JCBH: 02-10-2020 integración con territorio
						if (dataset.toLowerCase().contains("territorio")) {
							List<String> identifiers = new ArrayList<String>();
							for (Object obj : listado) {
								identifiers.add(((RDFModel) obj).getId());
							}
							territorioIdentifiers.put(dataset, identifiers);
						}

						// CMG: 05-08-2020 tratamiento de coordenadas
						UtilBigFiles.generaCoordenadasAll(StartVariables.SRID_XY_APP, StartVariables.SRID_LAT_LON_APP, listado);
						if (formatoJSON) {
							generarJSON(listado, JSON_OUTPUT, dataset);
						}
						if (formatoRDF) {
							UtilBigFiles.generaDataCubeInfo(listado);
							generarRDF(listado, RDF_OUTPUT, dataset, URIBase, context);
						}
						if (formatoHTML) {
							try {
								generarHTML(listado, HTML_OUTPUT, dataset);
							} catch (Exception e) {
								log.error("Error reading database at generate html",e);
							}
						}
					}
				}
			}
			if (formatoHTML) {
				try {
					htmlFull = htmlInit3+htmlIndiceInit+htmlIndiceEnd+htmlContent+htmlEnd;
					FileUtils.write(new File(HTML_OUTPUT + File.separator + "modelado.html"), htmlFull, "UTF-8");
				} catch (IOException e) {
					log.error("Error writting full html output",e);
				}
			}
		}

		sessions.close();

		// CMG: 07/10/2020 : Borramos los directorios si existieran
		if (territorioUpdate) {
			UtilBigFiles.deleteAllInFolder("extras" + File.separator + "json");
			UtilBigFiles.deleteAllInFolder("extras" + File.separator + "rdf");

		}

		// Iteramos por los distintos datasets de territorio
		for (Map.Entry<String, List<String>> entry : territorioIdentifiers.entrySet()) {
			log.info(entry.getKey());
			List<String> value = entry.getValue();

			if (territorioUpdate) {
				for (Iterator iterator = value.iterator(); iterator.hasNext();) {
					String id = (String) iterator.next();

					if (formatoJSON) {
						String dynamicURL = datasetURL.get(entry.getKey()).replace(METAVARIABLE_ID, id)
								.replace(METAVARIABLE_EXTENSION, ".json");
						String address = URIBase + context + dynamicURL;
						String filename = "extras" + File.separator + "json" + File.separator + entry.getKey()
								+ File.separator + id + ".json";

						log.info(address);

						UtilBigFiles.readAndWriteURL(address, filename);

						UtilBigFiles.sleep(500);
					}
					if (formatoRDF) {
						String dynamicURL = datasetURL.get(entry.getKey()).replace(METAVARIABLE_ID, id)
								.replace(METAVARIABLE_EXTENSION, ".ttl");
						String address = URIBase + context + dynamicURL;
						String filename = "extras" + File.separator + "rdf" + File.separator + entry.getKey()
								+ File.separator + entry.getKey() + "_" + id + ".ttl";

						log.info(address);

						UtilBigFiles.readAndWriteURL(address, filename);

						UtilBigFiles.sleep(500);
					}

				}
				// debemos procesar los ficheros JSON y generarlos igual que los que vienen de
				// BBDD
				if (formatoJSON) {
					File dir = new File("extras" + File.separator + "json" + File.separator + entry.getKey());
					if (dir.exists()) {
						JSONArray territorioArray = new JSONArray();
						File[] directoryListing = dir.listFiles();
						for (File child : directoryListing) {
							JSONObject jsonObj = null;
							try {
								String content = FileUtils.readFileToString(child, "utf8");
								jsonObj = UtilBigFiles.parseJSON(content);
							} catch (IOException e) {
								log.error("Error reading file", e);
							}
							if (jsonObj != null) {
								JSONArray records = (JSONArray) jsonObj.get("records");
								JSONObject object = (JSONObject) records.get(0);
								territorioArray.add(object);
							}
						}
						if (territorioArray.size() > 0) {
							try {
								log.info("Writting file with " + territorioArray.size() + " records");
								File output = new File(
										"extras" + File.separator + "json" + File.separator + entry.getKey() + ".json");

								// FileUtils.writeStringToFile(output, territorioArray.toJSONString(), "utf8");
								FileUtils.writeStringToFile(output,
										UtilBigFiles.jsonPrettyPrintArray(territorioArray.toJSONString()), "utf8");

							} catch (IOException e) {
								log.error("Error writting file", e);
							}
						}
					}
				}
			}

			if (formatoJSON) {
				File source = new File("extras" + File.separator + "json" + File.separator + entry.getKey() + ".json");
				File destiny = new File("jsonOutput" + File.separator + File.separator + entry.getKey() + ".json");
				try {
					FileUtils.copyFile(source, destiny);
				} catch (IOException e) {
					log.error("Error updating file from terrtorio", e);
				}
			}
			if (formatoRDF) {
				File source = new File("extras" + File.separator + "rdf" + File.separator + entry.getKey());
				File destiny = new File("rdfOutput");
				try {
					FileUtils.copyDirectory(source, destiny);
				} catch (IOException e) {
					log.error("Error updating files from terrtorio", e);
				}
			}

		}

		log.info("exit");

	}

}

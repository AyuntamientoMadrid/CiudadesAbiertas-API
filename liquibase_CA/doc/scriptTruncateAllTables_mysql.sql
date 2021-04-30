ALTER TABLE deuda_f_inst_financiacion DROP FOREIGN KEY d_f_inst_financiacion_ibfk_8;
ALTER TABLE deuda_f_inst_financiacion DROP FOREIGN KEY d_f_inst_financiacion_ibfk_7;
ALTER TABLE deuda_f_carga_financiera DROP FOREIGN KEY d_f_carga_financiera_ibfk_6;
ALTER TABLE deuda_f_capital_vivo DROP FOREIGN KEY deuda_f_capital_vivo_ibfk_5;
ALTER TABLE deuda_f_amortizacion DROP FOREIGN KEY deuda_f_amortizacion_ibfk_4;
ALTER TABLE deuda_f_rel_prestamo_entidad DROP FOREIGN KEY d_f_rel_prestamo_ent_ibfk_3;
ALTER TABLE deuda_f_rel_prestamo_entidad DROP FOREIGN KEY d_f_rel_prestamo_ent_ibfk_2;
ALTER TABLE deuda_f_inst_financiacion DROP FOREIGN KEY deuda_f_inst_fin_ibfk_1;
ALTER TABLE deuda_c_inf_pmp_mes DROP FOREIGN KEY deudac_inf_mes_ibfk_5;
ALTER TABLE deuda_c_inf_pmp_mes DROP FOREIGN KEY deudac_inf_mes_ibfk_4;
ALTER TABLE deuda_c_inf_pmp_mes_global DROP FOREIGN KEY deudac_inf_global_ibfk_3;
ALTER TABLE deuda_c_inf_morosidad_tri DROP FOREIGN KEY deudac_morosidad_tri_ibfk_2;
ALTER TABLE deuda_c_inf_morosidad_tri DROP FOREIGN KEY deudac_morosidad_tri_ibfk_1;
ALTER TABLE subvencion_convocatoria DROP FOREIGN KEY subvencion_ibfk_5;
ALTER TABLE subvencion_convocatoria DROP FOREIGN KEY subvencion_ibfk_3;
ALTER TABLE subvencion_convocatoria DROP FOREIGN KEY subvencion_ibfk_2;
ALTER TABLE subvencion_convocatoria DROP FOREIGN KEY subvencion_ibfk_1;
ALTER TABLE subvencion_concesion DROP FOREIGN KEY subvencion_beneficiario_ibfk_2;
ALTER TABLE subvencion_concesion DROP FOREIGN KEY subvencion_beneficiario_ibfk_1;
ALTER TABLE empleo_rel_oferta_convoca DROP FOREIGN KEY empleo_rel_ofer_conv_ibfk_2;
ALTER TABLE empleo_rel_oferta_convoca DROP FOREIGN KEY empleo_rel_ofer_conv_ibfk_1;
ALTER TABLE empleo_rel_boletin_convoca DROP FOREIGN KEY empleo_rel_bole_conv_ibfk_2;
ALTER TABLE empleo_rel_boletin_convoca DROP FOREIGN KEY empleo_rel_bole_conv_ibfk_1;
ALTER TABLE empleo_plaza_turno DROP FOREIGN KEY empleo_plaza_turno_ibfk_1;
ALTER TABLE empleo_oferta_publica DROP FOREIGN KEY empleo_oferta_publica_ibfk_1;
ALTER TABLE bus_stoppointinjourneypattern DROP FOREIGN KEY bus_route_p_ibfk_1;
ALTER TABLE bus_operator DROP FOREIGN KEY bus_ope_ibfk_1;
ALTER TABLE bus_vehiclejourney DROP FOREIGN KEY bus_vehijour_ibfk_3;
ALTER TABLE bus_vehiclejourney DROP FOREIGN KEY bus_vehijour_ibfk_2;
ALTER TABLE bus_vehiclejourney DROP FOREIGN KEY bus_vehijour_ibfk_1;
ALTER TABLE bus_scheduled_stop_point DROP FOREIGN KEY bus_stoppoinjourpatt_ibfk_2;
ALTER TABLE bus_stoppointinjourneypattern DROP FOREIGN KEY bus_stoppoinjourpatt_ibfk_1;
ALTER TABLE bus_route DROP FOREIGN KEY bus_route_ibfk_1;
ALTER TABLE bus_rel_linea_incidencia DROP FOREIGN KEY bus_rel_line_inci_ibfk_2;
ALTER TABLE bus_realtime_passing_time DROP FOREIGN KEY bus_realpasstime_ibfk_1;
ALTER TABLE bus_pointonroute DROP FOREIGN KEY bus_poinonrout_ibfk_2;
ALTER TABLE bus_pointonroute DROP FOREIGN KEY bus_poinonrout_ibfk_1;
ALTER TABLE bus_linea DROP FOREIGN KEY bus_linea_ibfk_3;
ALTER TABLE bus_linea DROP FOREIGN KEY bus_linea_ibfk_2;
ALTER TABLE bus_linea DROP FOREIGN KEY bus_linea_ibfk_1;
ALTER TABLE bus_journeypattern DROP FOREIGN KEY bus_jourpatt_ibfk_2;
ALTER TABLE bus_journeypattern DROP FOREIGN KEY bus_jourpatt_ibfk_1;
ALTER TABLE bus_headwayjourneygroup DROP FOREIGN KEY bus_headjourgrou_ibfk_1;
ALTER TABLE bus_daytypeassignment DROP FOREIGN KEY bus_daytypeassi_ibfk_2;
ALTER TABLE bus_daytypeassignment DROP FOREIGN KEY bus_daytypassi_ibfk_1;
ALTER TABLE cont_acus_observacion DROP FOREIGN KEY cont_acustica_ibfk_3;
ALTER TABLE cont_acus_observacion DROP FOREIGN KEY cont_acustica_ibfk_2;
ALTER TABLE cont_acus_estacion_medida DROP FOREIGN KEY cont_acustica_ibfk_1;
ALTER TABLE trafico_tramo_via DROP FOREIGN KEY trafico_tramo_via_ibfk_1;
ALTER TABLE trafico_observacion_disp DROP FOREIGN KEY traf_obse_dispostivo_ibfk_2;
ALTER TABLE trafico_observacion_disp DROP FOREIGN KEY traf_obse_dispostivo_ibfk_1;
ALTER TABLE trafico_observacion DROP FOREIGN KEY trafico_observacion_ibfk_1;
ALTER TABLE trafico_incidencia DROP FOREIGN KEY trafico_incidencia_ibfk_1;
ALTER TABLE trafico_dispositivo_medicion DROP FOREIGN KEY traf_disp_medicion_ibfk_1;
ALTER TABLE presupuesto_ejecucion_ingreso DROP FOREIGN KEY pres_ejecucion_ingreso_ibfk_1;
ALTER TABLE presupuesto_ejecucion_gasto DROP FOREIGN KEY pres_ejecucion_gasto_ibfk_1;
ALTER TABLE presupuesto_ingreso DROP FOREIGN KEY presupuesto_ingreso_ibfk_1;
ALTER TABLE presupuesto_gasto DROP FOREIGN KEY presupuesto_gasto_ibfk_1;
ALTER TABLE presupuesto DROP FOREIGN KEY presupuesto_liquidacion_ibfk_1;
ALTER TABLE convenio DROP FOREIGN KEY fk_convenio_org_obliga;
ALTER TABLE convenio DROP FOREIGN KEY fk_convenio_org;
ALTER TABLE conv_rel_firmante_entidad DROP FOREIGN KEY fk_con_rel_firma_ent_org;
ALTER TABLE conv_rel_firmante_entidad DROP FOREIGN KEY fk_con_rel_firma_ent_id;
ALTER TABLE conv_rel_firmante_ayto DROP FOREIGN KEY fk_con_rel_firma_ayto_org;
ALTER TABLE conv_rel_firmante_ayto DROP FOREIGN KEY fk_con_rel_firma_ayto_id;
ALTER TABLE convenio_susc_entidad DROP FOREIGN KEY fk_convenio_susc_org_id;
ALTER TABLE convenio_susc_entidad DROP FOREIGN KEY fk_convenio_susc_entidad_id;
ALTER TABLE convenio DROP FOREIGN KEY fk_convenio_prorroga;
ALTER TABLE convenio_documentacion DROP FOREIGN KEY fk_convenio_doc_id;
ALTER TABLE bici_observacion DROP FOREIGN KEY bicicleta_observacion_ibfk_1;
ALTER TABLE bici_punto_paso DROP FOREIGN KEY bicicleta_punto_de_paso_ibfk_1;
ALTER TABLE bici_trayecto DROP FOREIGN KEY bicicleta_trayecto_ibfk_6;
ALTER TABLE bici_trayecto DROP FOREIGN KEY bicicleta_trayecto_ibfk_5;
ALTER TABLE bici_trayecto DROP FOREIGN KEY bicicleta_trayecto_ibfk_4;
ALTER TABLE bici_trayecto DROP FOREIGN KEY bicicleta_trayecto_ibfk_3;
ALTER TABLE bici_trayecto DROP FOREIGN KEY bicicleta_trayecto_ibfk_2;
ALTER TABLE bici_trayecto DROP FOREIGN KEY bicicleta_trayecto_ibfk_1;
ALTER TABLE bici_anclaje DROP FOREIGN KEY bibileta_anclaje_ibfk_1;
ALTER TABLE territorio_seccion DROP FOREIGN KEY t_seccion_ibfk_5;
ALTER TABLE territorio_seccion DROP FOREIGN KEY t_seccion_ibfk_4;
ALTER TABLE territorio_seccion DROP FOREIGN KEY t_seccion_ibfk_3;
ALTER TABLE territorio_seccion DROP FOREIGN KEY t_seccion_ibfk_2;
ALTER TABLE territorio_seccion DROP FOREIGN KEY t_seccion_ibfk_1;
ALTER TABLE territorio_provincia DROP FOREIGN KEY t_provincia_ibfk_2;
ALTER TABLE territorio_provincia DROP FOREIGN KEY t_provincia_ibfk_1;
ALTER TABLE territorio_municipio DROP FOREIGN KEY t_municipio_ibfk_3;
ALTER TABLE territorio_municipio DROP FOREIGN KEY t_municipio_ibfk_2;
ALTER TABLE territorio_municipio DROP FOREIGN KEY t_municipio_ibfk_1;
ALTER TABLE territorio_distrito DROP FOREIGN KEY t_distrito_ibfk_4;
ALTER TABLE territorio_distrito DROP FOREIGN KEY t_distrito_ibfk_3;
ALTER TABLE territorio_distrito DROP FOREIGN KEY t_distrito_ibfk_2;
ALTER TABLE territorio_distrito DROP FOREIGN KEY t_distrito_ibfk_1;
ALTER TABLE territorio_barrio DROP FOREIGN KEY t_barrio_ibfk_5;
ALTER TABLE territorio_barrio DROP FOREIGN KEY t_barrio_ibfk_4;
ALTER TABLE territorio_barrio DROP FOREIGN KEY t_barrio_ibfk_3;
ALTER TABLE territorio_barrio DROP FOREIGN KEY t_barrio_ibfk_2;
ALTER TABLE territorio_barrio DROP FOREIGN KEY t_barrio_ibfk_1;
ALTER TABLE territorio_autonomia DROP FOREIGN KEY t_autonomia_ibfk_1;
ALTER TABLE cube_dsd_rel_dimension DROP FOREIGN KEY dsd_rel_dim_ibfk_2;
ALTER TABLE cube_dsd_dimension_value DROP FOREIGN KEY dsd_dim_value_ibfk_1;
ALTER TABLE cube_dsd_rel_measure DROP FOREIGN KEY cube_dsd_rel_mea_ibfk_2;
ALTER TABLE cube_dsd_rel_measure DROP FOREIGN KEY cube_dsd_rel_mea_ibfk_1;
ALTER TABLE cube_dsd_rel_dimension DROP FOREIGN KEY cube_dsd_rel_dim_ibfk_1;
ALTER TABLE contratos_tender_rel_item DROP FOREIGN KEY cont_tender_r_item_ibfk_2;
ALTER TABLE contratos_tender_rel_item DROP FOREIGN KEY cont_tender_r_item_ibfk_1;
ALTER TABLE contratos_tender DROP FOREIGN KEY contratos_tender_ibfk_1;
ALTER TABLE contratos_process DROP FOREIGN KEY contratos_process_ibfk_2;
ALTER TABLE contratos_process DROP FOREIGN KEY contratos_process_ibfk_1;
ALTER TABLE contratos_lot_rel_item DROP FOREIGN KEY contratos_lot_rel_item_ibfk_2;
ALTER TABLE contratos_lot_rel_item DROP FOREIGN KEY contratos_lot_rel_item_ibfk_1;
ALTER TABLE contratos_lot DROP FOREIGN KEY contratos_lot_ibfk_2;
ALTER TABLE contratos_lot DROP FOREIGN KEY contratos_lot_ibfk_1;
ALTER TABLE contratos_award DROP FOREIGN KEY contratos_award_ibfk_1;
ALTER TABLE agenda_m_evento DROP FOREIGN KEY fk_evento_to_evento_super;
ALTER TABLE agenda_m_rolintegranteevento DROP FOREIGN KEY fk_rol_i_evento_to_evento;
ALTER TABLE agenda_m_document DROP FOREIGN KEY fk_documento_to_evento;
ALTER TABLE callejero_portal DROP FOREIGN KEY callejero_portal_ibfk_1;
ALTER TABLE callejero_tramo_via DROP FOREIGN KEY callejero_tramo_via_ibfk_1;
ALTER TABLE organigrama DROP FOREIGN KEY fk_unit_of;
ALTER TABLE organigrama DROP FOREIGN KEY fk_unidad_raiz;
ALTER TABLE calidad_aire_sensor DROP FOREIGN KEY fk_estacion_sensor;
ALTER TABLE calidad_aire_observacion DROP FOREIGN KEY fk_estacion;
ALTER TABLE local_comercial DROP FOREIGN KEY fk_local_comercial_licencia;
ALTER TABLE local_comercial DROP FOREIGN KEY fk_local_comercial_terraza;
ALTER TABLE local_comercial DROP FOREIGN KEY fk_local_comercial_agrupacion;



DELETE FROM deuda_f_carga_financiera;
DELETE FROM deuda_f_capital_vivo;
DELETE FROM deuda_f_amortizacion;
DELETE FROM deuda_f_emision;
DELETE FROM deuda_f_rel_prestamo_entidad;
DELETE FROM deuda_f_prestamo;
DELETE FROM deuda_f_inst_financiacion;
DELETE FROM deuda_f_anual;
DELETE FROM deuda_c_inf_pmp_mes;
DELETE FROM deuda_c_inf_pmp_mes_global;
DELETE FROM deuda_c_publica_interval;
DELETE FROM deuda_organization;
DELETE FROM deuda_c_inf_morosidad_tri;
DELETE FROM subvencion_organization;
DELETE FROM subvencion_concesion;
DELETE FROM subvencion_convocatoria;
DELETE FROM empleo_rel_oferta_convoca;
DELETE FROM empleo_rel_boletin_convoca;
DELETE FROM empleo_plaza_turno;
DELETE FROM empleo_oferta_publica;
DELETE FROM empleo_convocatoria_publica;
DELETE FROM empleo_boletin_oficial;
DELETE FROM bus_vehiclejourney;
DELETE FROM bus_scheduled_stop_point;
DELETE FROM bus_stoppointinjourneypattern;
DELETE FROM bus_servicecalendar;
DELETE FROM bus_route;
DELETE FROM bus_rel_linea_incidencia;
DELETE FROM bus_realtime_passing_time;
DELETE FROM bus_pointonroute;
DELETE FROM bus_parada;
DELETE FROM bus_operator;
DELETE FROM bus_linea;
DELETE FROM bus_journeypattern;
DELETE FROM bus_incidencia;
DELETE FROM bus_headwayjourneygroup;
DELETE FROM bus_headwayinterval;
DELETE FROM bus_daytypeassignment;
DELETE FROM bus_daytype;
DELETE FROM bus_authority;
DELETE FROM cont_acus_propiedad;
DELETE FROM cont_acus_observacion;
DELETE FROM cont_acus_estacion_medida;
DELETE FROM trafico_proper_interval;
DELETE FROM trafico_propiedad_medicion;
DELETE FROM trafico_equipo;
DELETE FROM trafico_tramo_via;
DELETE FROM trafico_tramo;
DELETE FROM trafico_observacion;
DELETE FROM trafico_incidencia;
DELETE FROM trafico_dispositivo_medicion;
DELETE FROM trafico_observacion_disp;
DELETE FROM presupuesto_ejecucion_ingreso;
DELETE FROM presupuesto_ejecucion_gasto;
DELETE FROM presupuesto_ingreso;
DELETE FROM presupuesto_gasto;
DELETE FROM presupuesto_liquidacion;
DELETE FROM presupuesto;
DELETE FROM convenio_organization;
DELETE FROM conv_rel_firmante_entidad;
DELETE FROM conv_rel_firmante_ayto;
DELETE FROM convenio_susc_entidad;
DELETE FROM convenio_documentacion;
DELETE FROM convenio;
DELETE FROM bici_observacion;
DELETE FROM bici_punto_paso;
DELETE FROM bici_trayecto;
DELETE FROM bici_usuario;
DELETE FROM bici_anclaje;
DELETE FROM bici_estacion;
DELETE FROM bici_bicicleta;
DELETE FROM contratos_tender_rel_item;
DELETE FROM contratos_tender;
DELETE FROM contratos_process;
DELETE FROM contratos_organization;
DELETE FROM contratos_lot_rel_item;
DELETE FROM contratos_lot;
DELETE FROM contratos_item;
DELETE FROM contratos_award;
DELETE FROM agenda_m_rolintegranteevento;
DELETE FROM agenda_m_document;
DELETE FROM agenda_m_evento;
DELETE FROM padron_pais_nacimiento;
DELETE FROM padron_edad;
DELETE FROM padron_indicadores;
DELETE FROM padron_procedencia;
DELETE FROM padron_nacionalidad;
DELETE FROM padron_estudios;
DELETE FROM padron_edad_g_quiquenal;
DELETE FROM plantilla;
DELETE FROM tramite;
DELETE FROM callejero_via;
DELETE FROM callejero_tramo_via;
DELETE FROM callejero_portal;
DELETE FROM alojamiento;
DELETE FROM organigrama;
DELETE FROM calidad_aire_sensor;
DELETE FROM calidad_aire_observacion;
DELETE FROM calidad_aire_estacion;
DELETE FROM aviso_queja_sug;
DELETE FROM local_comercial_terraza;
DELETE FROM local_comercial_licencia;
DELETE FROM local_comercial_agrupacion;
DELETE FROM local_comercial;
DELETE FROM agenda;
DELETE FROM cube_dsd_rel_measure;
DELETE FROM cube_dsd_rel_dimension;
DELETE FROM cube_dsd_measure;
DELETE FROM cube_dsd_dimension_value;
DELETE FROM cube_dsd_dimension;
DELETE FROM cube_dsd;
DELETE FROM territorio_seccion;
DELETE FROM territorio_barrio;
DELETE FROM territorio_distrito;
DELETE FROM territorio_municipio;
DELETE FROM territorio_provincia;
DELETE FROM territorio_autonomia;
DELETE FROM territorio_pais;


ALTER TABLE local_comercial ADD CONSTRAINT fk_local_comercial_agrupacion FOREIGN KEY (agrupacion_comercial) REFERENCES local_comercial_agrupacion (id);
ALTER TABLE local_comercial ADD CONSTRAINT fk_local_comercial_terraza FOREIGN KEY (tiene_terraza) REFERENCES local_comercial_terraza (id);
ALTER TABLE local_comercial ADD CONSTRAINT fk_local_comercial_licencia FOREIGN KEY (tiene_licencia_apertura) REFERENCES local_comercial_licencia (id);
ALTER TABLE calidad_aire_observacion ADD CONSTRAINT fk_estacion FOREIGN KEY (made_by_sensor) REFERENCES calidad_aire_estacion (id);
ALTER TABLE calidad_aire_sensor ADD CONSTRAINT fk_estacion_sensor FOREIGN KEY (is_hosted_by) REFERENCES calidad_aire_estacion (id);
ALTER TABLE organigrama ADD CONSTRAINT fk_unidad_raiz FOREIGN KEY (unidad_raiz) REFERENCES organigrama (id);
ALTER TABLE organigrama ADD CONSTRAINT fk_unit_of FOREIGN KEY (unit_of) REFERENCES organigrama (id);
ALTER TABLE callejero_tramo_via ADD CONSTRAINT callejero_tramo_via_ibfk_1 FOREIGN KEY (via) REFERENCES callejero_via (id);
ALTER TABLE callejero_portal ADD CONSTRAINT callejero_portal_ibfk_1 FOREIGN KEY (via) REFERENCES callejero_via (id);
ALTER TABLE agenda_m_document ADD CONSTRAINT fk_documento_to_evento FOREIGN KEY (event_id) REFERENCES agenda_m_evento (id);
ALTER TABLE agenda_m_rolintegranteevento ADD CONSTRAINT fk_rol_i_evento_to_evento FOREIGN KEY (event_id) REFERENCES agenda_m_evento (id);
ALTER TABLE agenda_m_evento ADD CONSTRAINT fk_evento_to_evento_super FOREIGN KEY (super_event_id) REFERENCES agenda_m_evento (id);
ALTER TABLE contratos_award ADD CONSTRAINT contratos_award_ibfk_1 FOREIGN KEY (is_supplier_for) REFERENCES contratos_organization (id);
ALTER TABLE contratos_lot ADD CONSTRAINT contratos_lot_ibfk_1 FOREIGN KEY (tender_id) REFERENCES contratos_tender (id);
ALTER TABLE contratos_lot ADD CONSTRAINT contratos_lot_ibfk_2 FOREIGN KEY (has_supplier) REFERENCES contratos_award (id);
ALTER TABLE contratos_lot_rel_item ADD CONSTRAINT contratos_lot_rel_item_ibfk_1 FOREIGN KEY (lot_id) REFERENCES contratos_lot (id);
ALTER TABLE contratos_lot_rel_item ADD CONSTRAINT contratos_lot_rel_item_ibfk_2 FOREIGN KEY (item_id) REFERENCES contratos_item (id);
ALTER TABLE contratos_process ADD CONSTRAINT contratos_process_ibfk_1 FOREIGN KEY (has_tender) REFERENCES contratos_tender (id);
ALTER TABLE contratos_process ADD CONSTRAINT contratos_process_ibfk_2 FOREIGN KEY (is_buyer_for) REFERENCES contratos_organization (id);
ALTER TABLE contratos_tender ADD CONSTRAINT contratos_tender_ibfk_1 FOREIGN KEY (has_supplier) REFERENCES contratos_award (id);
ALTER TABLE contratos_tender_rel_item ADD CONSTRAINT cont_tender_r_item_ibfk_1 FOREIGN KEY (tender_id) REFERENCES contratos_tender (id);
ALTER TABLE contratos_tender_rel_item ADD CONSTRAINT cont_tender_r_item_ibfk_2 FOREIGN KEY (item_id) REFERENCES contratos_item (id);
ALTER TABLE cube_dsd_rel_dimension ADD CONSTRAINT cube_dsd_rel_dim_ibfk_1 FOREIGN KEY (cube_key) REFERENCES cube_dsd (ikey) ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cube_dsd_rel_measure ADD CONSTRAINT cube_dsd_rel_mea_ibfk_1 FOREIGN KEY (cube_key) REFERENCES cube_dsd (ikey) ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cube_dsd_rel_measure ADD CONSTRAINT cube_dsd_rel_mea_ibfk_2 FOREIGN KEY (measure_key) REFERENCES cube_dsd_measure (ikey) ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cube_dsd_dimension_value ADD CONSTRAINT dsd_dim_value_ibfk_1 FOREIGN KEY (dimension_key) REFERENCES cube_dsd_dimension (ikey) ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cube_dsd_rel_dimension ADD CONSTRAINT dsd_rel_dim_ibfk_2 FOREIGN KEY (dimension_key) REFERENCES cube_dsd_dimension (ikey) ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE territorio_autonomia ADD CONSTRAINT t_autonomia_ibfk_1 FOREIGN KEY (pais) REFERENCES territorio_pais (ikey) ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE territorio_barrio ADD CONSTRAINT t_barrio_ibfk_1 FOREIGN KEY (pais) REFERENCES territorio_pais (ikey) ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE territorio_barrio ADD CONSTRAINT t_barrio_ibfk_2 FOREIGN KEY (autonomia) REFERENCES territorio_autonomia (ikey) ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE territorio_barrio ADD CONSTRAINT t_barrio_ibfk_3 FOREIGN KEY (provincia) REFERENCES territorio_provincia (ikey) ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE territorio_barrio ADD CONSTRAINT t_barrio_ibfk_4 FOREIGN KEY (municipio) REFERENCES territorio_municipio (ikey) ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE territorio_barrio ADD CONSTRAINT t_barrio_ibfk_5 FOREIGN KEY (distrito) REFERENCES territorio_distrito (ikey) ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE territorio_distrito ADD CONSTRAINT t_distrito_ibfk_1 FOREIGN KEY (pais) REFERENCES territorio_pais (ikey) ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE territorio_distrito ADD CONSTRAINT t_distrito_ibfk_2 FOREIGN KEY (autonomia) REFERENCES territorio_autonomia (ikey) ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE territorio_distrito ADD CONSTRAINT t_distrito_ibfk_3 FOREIGN KEY (provincia) REFERENCES territorio_provincia (ikey) ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE territorio_distrito ADD CONSTRAINT t_distrito_ibfk_4 FOREIGN KEY (municipio) REFERENCES territorio_municipio (ikey) ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE territorio_municipio ADD CONSTRAINT t_municipio_ibfk_1 FOREIGN KEY (pais) REFERENCES territorio_pais (ikey) ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE territorio_municipio ADD CONSTRAINT t_municipio_ibfk_2 FOREIGN KEY (autonomia) REFERENCES territorio_autonomia (ikey) ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE territorio_municipio ADD CONSTRAINT t_municipio_ibfk_3 FOREIGN KEY (provincia) REFERENCES territorio_provincia (ikey) ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE territorio_provincia ADD CONSTRAINT t_provincia_ibfk_1 FOREIGN KEY (pais) REFERENCES territorio_pais (ikey) ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE territorio_provincia ADD CONSTRAINT t_provincia_ibfk_2 FOREIGN KEY (autonomia) REFERENCES territorio_autonomia (ikey) ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE territorio_seccion ADD CONSTRAINT t_seccion_ibfk_1 FOREIGN KEY (pais) REFERENCES territorio_pais (ikey) ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE territorio_seccion ADD CONSTRAINT t_seccion_ibfk_2 FOREIGN KEY (autonomia) REFERENCES territorio_autonomia (ikey) ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE territorio_seccion ADD CONSTRAINT t_seccion_ibfk_3 FOREIGN KEY (provincia) REFERENCES territorio_provincia (ikey) ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE territorio_seccion ADD CONSTRAINT t_seccion_ibfk_4 FOREIGN KEY (municipio) REFERENCES territorio_municipio (ikey) ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE territorio_seccion ADD CONSTRAINT t_seccion_ibfk_5 FOREIGN KEY (distrito) REFERENCES territorio_distrito (ikey) ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE bici_anclaje ADD CONSTRAINT bibileta_anclaje_ibfk_1 FOREIGN KEY (estacion_bicicleta_id) REFERENCES bici_estacion (id);
ALTER TABLE bici_trayecto ADD CONSTRAINT bicicleta_trayecto_ibfk_1 FOREIGN KEY (usuario_id) REFERENCES bici_usuario (id);
ALTER TABLE bici_trayecto ADD CONSTRAINT bicicleta_trayecto_ibfk_2 FOREIGN KEY (bicicleta_id) REFERENCES bici_bicicleta (id);
ALTER TABLE bici_trayecto ADD CONSTRAINT bicicleta_trayecto_ibfk_3 FOREIGN KEY (estacion_bicicleta_origen_id) REFERENCES bici_estacion (id);
ALTER TABLE bici_trayecto ADD CONSTRAINT bicicleta_trayecto_ibfk_4 FOREIGN KEY (estacion_bicicleta_destino_id) REFERENCES bici_estacion (id);
ALTER TABLE bici_trayecto ADD CONSTRAINT bicicleta_trayecto_ibfk_5 FOREIGN KEY (anclaje_origen_id) REFERENCES bici_anclaje (id);
ALTER TABLE bici_trayecto ADD CONSTRAINT bicicleta_trayecto_ibfk_6 FOREIGN KEY (anclaje_destino_id) REFERENCES bici_anclaje (id);
ALTER TABLE bici_punto_paso ADD CONSTRAINT bicicleta_punto_de_paso_ibfk_1 FOREIGN KEY (trayecto_id) REFERENCES bici_trayecto (id);
ALTER TABLE bici_observacion ADD CONSTRAINT bicicleta_observacion_ibfk_1 FOREIGN KEY (made_by_sensor) REFERENCES bici_estacion (id);
ALTER TABLE convenio_documentacion ADD CONSTRAINT fk_convenio_doc_id FOREIGN KEY (convenio_id) REFERENCES convenio (id);
ALTER TABLE convenio ADD CONSTRAINT fk_convenio_prorroga FOREIGN KEY (es_variacion_id) REFERENCES convenio (id);
ALTER TABLE convenio_susc_entidad ADD CONSTRAINT fk_convenio_susc_entidad_id FOREIGN KEY (convenio_id) REFERENCES convenio (id);
ALTER TABLE convenio_susc_entidad ADD CONSTRAINT fk_convenio_susc_org_id FOREIGN KEY (organization_id) REFERENCES convenio_organization (id);
ALTER TABLE conv_rel_firmante_ayto ADD CONSTRAINT fk_con_rel_firma_ayto_id FOREIGN KEY (convenio_id) REFERENCES convenio (id);
ALTER TABLE conv_rel_firmante_ayto ADD CONSTRAINT fk_con_rel_firma_ayto_org FOREIGN KEY (organization_id) REFERENCES convenio_organization (id);
ALTER TABLE conv_rel_firmante_entidad ADD CONSTRAINT fk_con_rel_firma_ent_id FOREIGN KEY (entidad_id) REFERENCES convenio_susc_entidad (id);
ALTER TABLE conv_rel_firmante_entidad ADD CONSTRAINT fk_con_rel_firma_ent_org FOREIGN KEY (organization_id) REFERENCES convenio_organization (id);
ALTER TABLE convenio ADD CONSTRAINT fk_convenio_org FOREIGN KEY (organization_id) REFERENCES convenio_organization (id);
ALTER TABLE convenio ADD CONSTRAINT fk_convenio_org_obliga FOREIGN KEY (org_id_obligado_presta) REFERENCES convenio_organization (id);
ALTER TABLE presupuesto ADD CONSTRAINT presupuesto_liquidacion_ibfk_1 FOREIGN KEY (liquidacion) REFERENCES presupuesto_liquidacion (id);
ALTER TABLE presupuesto_gasto ADD CONSTRAINT presupuesto_gasto_ibfk_1 FOREIGN KEY (presupuesto) REFERENCES presupuesto (id);
ALTER TABLE presupuesto_ingreso ADD CONSTRAINT presupuesto_ingreso_ibfk_1 FOREIGN KEY (presupuesto) REFERENCES presupuesto (id);
ALTER TABLE presupuesto_ejecucion_gasto ADD CONSTRAINT pres_ejecucion_gasto_ibfk_1 FOREIGN KEY (presupuesto_gasto) REFERENCES presupuesto_gasto (id);
ALTER TABLE presupuesto_ejecucion_ingreso ADD CONSTRAINT pres_ejecucion_ingreso_ibfk_1 FOREIGN KEY (presupuesto_ingreso) REFERENCES presupuesto_ingreso (id);
ALTER TABLE trafico_dispositivo_medicion ADD CONSTRAINT traf_disp_medicion_ibfk_1 FOREIGN KEY (monitorea) REFERENCES trafico_tramo (id);
ALTER TABLE trafico_incidencia ADD CONSTRAINT trafico_incidencia_ibfk_1 FOREIGN KEY (incidencia_tramo) REFERENCES trafico_tramo (id);
ALTER TABLE trafico_observacion ADD CONSTRAINT trafico_observacion_ibfk_1 FOREIGN KEY (has_feature_interest) REFERENCES trafico_tramo (id);
ALTER TABLE trafico_observacion_disp ADD CONSTRAINT traf_obse_dispostivo_ibfk_1 FOREIGN KEY (id_observacion) REFERENCES trafico_observacion (id);
ALTER TABLE trafico_observacion_disp ADD CONSTRAINT traf_obse_dispostivo_ibfk_2 FOREIGN KEY (made_by_sensor) REFERENCES trafico_dispositivo_medicion (id);
ALTER TABLE trafico_tramo_via ADD CONSTRAINT trafico_tramo_via_ibfk_1 FOREIGN KEY (id_tramo) REFERENCES trafico_tramo (id);
ALTER TABLE cont_acus_estacion_medida ADD CONSTRAINT cont_acustica_ibfk_1 FOREIGN KEY (observes) REFERENCES cont_acus_propiedad (id);
ALTER TABLE cont_acus_observacion ADD CONSTRAINT cont_acustica_ibfk_2 FOREIGN KEY (made_by_sensor) REFERENCES cont_acus_estacion_medida (id);
ALTER TABLE cont_acus_observacion ADD CONSTRAINT cont_acustica_ibfk_3 FOREIGN KEY (observed_property) REFERENCES cont_acus_propiedad (id);
ALTER TABLE bus_daytypeassignment ADD CONSTRAINT bus_daytypassi_ibfk_1 FOREIGN KEY (specifying) REFERENCES bus_daytype (id);
ALTER TABLE bus_daytypeassignment ADD CONSTRAINT bus_daytypeassi_ibfk_2 FOREIGN KEY (for_the_definition_of) REFERENCES bus_servicecalendar (id);
ALTER TABLE bus_headwayjourneygroup ADD CONSTRAINT bus_headjourgrou_ibfk_1 FOREIGN KEY (determined_by) REFERENCES bus_headwayinterval (id);
ALTER TABLE bus_journeypattern ADD CONSTRAINT bus_jourpatt_ibfk_1 FOREIGN KEY (on_id) REFERENCES bus_route (id);
ALTER TABLE bus_journeypattern ADD CONSTRAINT bus_jourpatt_ibfk_2 FOREIGN KEY (generado_por_incidencia) REFERENCES bus_incidencia (id);
ALTER TABLE bus_linea ADD CONSTRAINT bus_linea_ibfk_1 FOREIGN KEY (operating) REFERENCES bus_operator (id);
ALTER TABLE bus_linea ADD CONSTRAINT bus_linea_ibfk_2 FOREIGN KEY (cabecera_linea) REFERENCES bus_parada (id);
ALTER TABLE bus_linea ADD CONSTRAINT bus_linea_ibfk_3 FOREIGN KEY (final_linea) REFERENCES bus_parada (id);
ALTER TABLE bus_pointonroute ADD CONSTRAINT bus_poinonrout_ibfk_1 FOREIGN KEY (in_id) REFERENCES bus_route (id);
ALTER TABLE bus_pointonroute ADD CONSTRAINT bus_poinonrout_ibfk_2 FOREIGN KEY (functional_centroid_for) REFERENCES bus_parada (id);
ALTER TABLE bus_realtime_passing_time ADD CONSTRAINT bus_realpasstime_ibfk_1 FOREIGN KEY (has_feature_of_interest) REFERENCES bus_pointonroute (id);
ALTER TABLE bus_rel_linea_incidencia ADD CONSTRAINT bus_rel_line_inci_ibfk_2 FOREIGN KEY (afectada_incidencia) REFERENCES bus_incidencia (id);
ALTER TABLE bus_route ADD CONSTRAINT bus_route_ibfk_1 FOREIGN KEY (on_id) REFERENCES bus_linea (id);
ALTER TABLE bus_stoppointinjourneypattern ADD CONSTRAINT bus_stoppoinjourpatt_ibfk_1 FOREIGN KEY (in_id) REFERENCES bus_journeypattern (id);
ALTER TABLE bus_scheduled_stop_point ADD CONSTRAINT bus_stoppoinjourpatt_ibfk_2 FOREIGN KEY (functional_centroid_for) REFERENCES bus_parada (id);
ALTER TABLE bus_vehiclejourney ADD CONSTRAINT bus_vehijour_ibfk_1 FOREIGN KEY (made_using) REFERENCES bus_journeypattern (id);
ALTER TABLE bus_vehiclejourney ADD CONSTRAINT bus_vehijour_ibfk_2 FOREIGN KEY (worked_on) REFERENCES bus_daytype (id);
ALTER TABLE bus_vehiclejourney ADD CONSTRAINT bus_vehijour_ibfk_3 FOREIGN KEY (composed_of) REFERENCES bus_headwayjourneygroup (id);
ALTER TABLE bus_operator ADD CONSTRAINT bus_ope_ibfk_1 FOREIGN KEY (serving_pt_for) REFERENCES bus_authority (id);
ALTER TABLE bus_stoppointinjourneypattern ADD CONSTRAINT bus_route_p_ibfk_1 FOREIGN KEY (viewed_as) REFERENCES bus_scheduled_stop_point (id);
ALTER TABLE empleo_oferta_publica ADD CONSTRAINT empleo_oferta_publica_ibfk_1 FOREIGN KEY (boletin_oficial_id) REFERENCES empleo_boletin_oficial (id);
ALTER TABLE empleo_plaza_turno ADD CONSTRAINT empleo_plaza_turno_ibfk_1 FOREIGN KEY (convocatoria_id) REFERENCES empleo_convocatoria_publica (id);
ALTER TABLE empleo_rel_boletin_convoca ADD CONSTRAINT empleo_rel_bole_conv_ibfk_1 FOREIGN KEY (boletin_id) REFERENCES empleo_boletin_oficial (id);
ALTER TABLE empleo_rel_boletin_convoca ADD CONSTRAINT empleo_rel_bole_conv_ibfk_2 FOREIGN KEY (convocatoria_id) REFERENCES empleo_convocatoria_publica (id);
ALTER TABLE empleo_rel_oferta_convoca ADD CONSTRAINT empleo_rel_ofer_conv_ibfk_1 FOREIGN KEY (oferta_id) REFERENCES empleo_oferta_publica (id);
ALTER TABLE empleo_rel_oferta_convoca ADD CONSTRAINT empleo_rel_ofer_conv_ibfk_2 FOREIGN KEY (convocatoria_id) REFERENCES empleo_convocatoria_publica (id);
ALTER TABLE subvencion_concesion ADD CONSTRAINT subvencion_beneficiario_ibfk_1 FOREIGN KEY (beneficiario) REFERENCES subvencion_organization (id);
ALTER TABLE subvencion_concesion ADD CONSTRAINT subvencion_beneficiario_ibfk_2 FOREIGN KEY (convocatoria) REFERENCES subvencion_convocatoria (id);
ALTER TABLE subvencion_convocatoria ADD CONSTRAINT subvencion_ibfk_1 FOREIGN KEY (area_id) REFERENCES subvencion_organization (id);
ALTER TABLE subvencion_convocatoria ADD CONSTRAINT subvencion_ibfk_2 FOREIGN KEY (entidad_financiadora_id) REFERENCES subvencion_organization (id);
ALTER TABLE subvencion_convocatoria ADD CONSTRAINT subvencion_ibfk_3 FOREIGN KEY (servicio_id) REFERENCES subvencion_organization (id);
ALTER TABLE subvencion_convocatoria ADD CONSTRAINT subvencion_ibfk_5 FOREIGN KEY (organization_id) REFERENCES subvencion_organization (id);
ALTER TABLE deuda_c_inf_morosidad_tri ADD CONSTRAINT deudac_morosidad_tri_ibfk_1 FOREIGN KEY (entidad) REFERENCES deuda_organization (id);
ALTER TABLE deuda_c_inf_morosidad_tri ADD CONSTRAINT deudac_morosidad_tri_ibfk_2 FOREIGN KEY (periodo) REFERENCES deuda_c_publica_interval (id);
ALTER TABLE deuda_c_inf_pmp_mes_global ADD CONSTRAINT deudac_inf_global_ibfk_3 FOREIGN KEY (periodo) REFERENCES deuda_c_publica_interval (id);
ALTER TABLE deuda_c_inf_pmp_mes ADD CONSTRAINT deudac_inf_mes_ibfk_4 FOREIGN KEY (entidad) REFERENCES deuda_organization (id);
ALTER TABLE deuda_c_inf_pmp_mes ADD CONSTRAINT deudac_inf_mes_ibfk_5 FOREIGN KEY (periodo) REFERENCES deuda_c_publica_interval (id);
ALTER TABLE deuda_f_inst_financiacion ADD CONSTRAINT deuda_f_inst_fin_ibfk_1 FOREIGN KEY (deuda_anual) REFERENCES deuda_f_anual (id);
ALTER TABLE deuda_f_rel_prestamo_entidad ADD CONSTRAINT d_f_rel_prestamo_ent_ibfk_2 FOREIGN KEY (prestamo_id) REFERENCES deuda_f_prestamo (id);
ALTER TABLE deuda_f_rel_prestamo_entidad ADD CONSTRAINT d_f_rel_prestamo_ent_ibfk_3 FOREIGN KEY (entidad_prestamista) REFERENCES deuda_organization (id);
ALTER TABLE deuda_f_amortizacion ADD CONSTRAINT deuda_f_amortizacion_ibfk_4 FOREIGN KEY (instrumenta_financiacion) REFERENCES deuda_f_inst_financiacion (id);
ALTER TABLE deuda_f_capital_vivo ADD CONSTRAINT deuda_f_capital_vivo_ibfk_5 FOREIGN KEY (instrumenta_financiacion) REFERENCES deuda_f_inst_financiacion (id);
ALTER TABLE deuda_f_carga_financiera ADD CONSTRAINT d_f_carga_financiera_ibfk_6 FOREIGN KEY (instrumenta_financiacion) REFERENCES deuda_f_inst_financiacion (id);
ALTER TABLE deuda_f_inst_financiacion ADD CONSTRAINT d_f_inst_financiacion_ibfk_7 FOREIGN KEY (prestamo) REFERENCES deuda_f_prestamo (id);
ALTER TABLE deuda_f_inst_financiacion ADD CONSTRAINT d_f_inst_financiacion_ibfk_8 FOREIGN KEY (emision) REFERENCES deuda_f_emision (id);


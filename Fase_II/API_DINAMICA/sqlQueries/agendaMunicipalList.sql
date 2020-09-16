--Listado de Agenda Municipal

SELECT 
	evento.title,
	evento.description,
	evento.url,
	evento.start_date,
	evento.end_date,
	evento.location_title,
	evento.street_address,
	evento.postal_code,
	evento.portal_id,
	evento.municipio_id,
	evento.municipio_title,
	evento.distrito_id,
	evento.distrito_title,
	evento.barrio_title,
	evento.equipamiento_id,
	evento.equipamiento_title,
	evento.super_event_id,
	evento.tipo_evento,
	evento.tipo_acceso,
	evento.tipo_sesion,
	evento.canal,
	doc.encoding_format as doc_encoding_format,
	doc.name as doc_name,
	doc.url as doc_url,
	rol.agent_name as rol_agent_name,
	rol.nombre as rol_nombre,
	rol.apellido1 as rol_apellido1,
	rol.apellido2 as rol_apellido2,
	rol.organization_title as rol_organization_title,
	rol.organization_id as rol_organization_id,
	rol.role as rol_role,
	rol.rol as rol_rol,
	rol.inicio_asistencia as rol_inicio_asistencia,
	rol.fin_asistencia as rol_fin_asistencia
 FROM 
 	agenda_m_evento evento, 
 	agenda_m_document doc, 
 	agenda_m_rolintegranteevento rol 
 WHERE 
 	evento.id like doc.event_id 
 	and evento.id like rol.event_id 
SELECT 
	equipamiento.id
	,equipamiento.title
    ,equipamiento.description
    ,equipamiento.tipo_equipamiento as tipoEquipamiento
    ,equipamiento.municipio_id as municipioId
    ,equipamiento.municipio_title as municipioTitle
    ,equipamiento.provincia_id as provinciaId
    ,equipamiento.autonomia_id as autonomiaId
    ,equipamiento.pais_id as paisId
    ,equipamiento.street_address as streetAddress
    ,equipamiento.portal_id as portalId
    ,equipamiento.postal_code as postalCode
    ,equipamiento.barrio_id as barrioId
    ,equipamiento.distrito_id as distritoId
    ,equipamiento.email
    ,equipamiento.telephone
    ,equipamiento.url
    ,equipamiento.titularidad_publica as titularidadPublica
    ,equipamiento.opening_hours as openingHours
    ,equipamiento.x_etrs89 AS xETRS89
    ,equipamiento.y_etrs89 AS yETRS89
FROM equipamiento
WHERE equipamiento.tipo_equipamiento LIKE 'equipamiento municipal'
SELECT *
FROM local_comercial
WHERE agrupacion_comercial IN (
		SELECT id
		FROM local_comercial_agrupacion
		WHERE tipo_agrupacion_comercial LIKE 'galeria de alimentacion'
		);

 SELECT
        id        ,
        title        ,
        description        ,
        municipio_id        ,
        municipio_title        ,
        street_address        ,
        postal_code        ,
        distrito_id        ,
        barrio_id        ,
        x_etrs89        ,
        y_etrs89        ,
        telephone        ,
        url        ,
        tipo_actividad_economica        ,
        nombre_comercial        ,
        rotulo        ,
        aforo        ,
        tipo_situacion        ,
        tipo_acceso        ,
        referencia_catastral        ,
        tiene_licencia_apertura        ,
        tiene_terraza        ,
        agrupacion_comercial        ,
        portal_id,
        SQRT((x_etrs89-param_x_etrs89)*(x_etrs89-param_x_etrs89)+(y_etrs89-param_y_etrs89)*(y_etrs89-param_y_etrs89)) as distance                             
    FROM
        local_comercial                            
    where
        (
            SQRT((x_etrs89-441201.60999147)*(x_etrs89-441201.60999147)+(y_etrs89-4479589.52997752)*(y_etrs89-4479589.52997752)) <=param_meters          
        )
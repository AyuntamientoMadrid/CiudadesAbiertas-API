SELECT id
      ,title
      ,area_id as areaId
      ,area_title as areaTitle
      ,municipio_id as municipioId
      ,municipio_title as municipioTitle
      ,adjudicatario_id as adjudicatarioId
      ,adjudicatario_title as adjudicatarioTitle
      ,entidad_financiadora_id as entidadFinanciadoraId
      ,entidad_financiadora_title as entidadFinanciadoraTitle
      ,importe
      ,fecha_adjudicacion as fechaAdjudicacion
      ,linea_financiacion as lineaFinanciacion
      ,bases_reguladoras as basesReguladoras
      ,tipo_instrumento as tipoInstrumento
      ,aplicacion_presupuestaria as aplicacionPresupuestaria
  FROM subvencion
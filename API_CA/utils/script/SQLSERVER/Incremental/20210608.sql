USE [bbdd_ciudadesAbiertas];
GO

ALTER TABLE [schema_ciudadesAbiertas].[trafico_observacion] ALTER COLUMN [phenomenon_time] [varchar](50)
GO

ALTER TABLE [schema_ciudadesAbiertas].[trafico_dispositivo_medicion] ALTER COLUMN [observes] [varchar](50)
GO

ALTER TABLE [schema_ciudadesAbiertas].[deuda_c_inf_pmp_mes] DROP COLUMN [importe_operaciones_pagadas]
GO

ALTER TABLE [schema_ciudadesAbiertas].[deuda_c_inf_pmp_mes] DROP COLUMN [imp_operaciones_pdte_pago]
GO

exec sp_rename '[schema_ciudadesAbiertas].[deuda_f_anual].[endeudamientoPDE]', 'endeudamiento'
GO

exec sp_rename '[schema_ciudadesAbiertas].[deuda_f_inst_financiacion].[tasa_fija]', 'tipo_fijo'
GO

exec sp_rename '[schema_ciudadesAbiertas].[deuda_f_carga_financiera].[anio_fiscal]', 'anio'
GO

ALTER TABLE [schema_ciudadesAbiertas].[padron_edad_g_quiquenal] ADD [pais_id] [varchar](50)
GO

ALTER TABLE [schema_ciudadesAbiertas].[padron_edad_g_quiquenal] ADD [pais_title] [varchar](400)
GO

ALTER TABLE [schema_ciudadesAbiertas].[padron_edad_g_quiquenal] ADD [autonomia_id] [varchar](50)
GO

ALTER TABLE [schema_ciudadesAbiertas].[padron_edad_g_quiquenal] ADD [autonomia_title] [varchar](400)
GO

ALTER TABLE [schema_ciudadesAbiertas].[padron_edad] ADD [pais_id] [varchar](50)
GO

ALTER TABLE [schema_ciudadesAbiertas].[padron_edad] ADD [pais_title] [varchar](400)
GO

ALTER TABLE [schema_ciudadesAbiertas].[padron_edad] ADD [autonomia_id] [varchar](50)
GO

ALTER TABLE [schema_ciudadesAbiertas].[padron_edad] ADD [autonomia_title] [varchar](400)
GO

ALTER TABLE [schema_ciudadesAbiertas].[padron_indicadores] ADD [pais_id] [varchar](50)
GO

ALTER TABLE [schema_ciudadesAbiertas].[padron_indicadores] ADD [pais_title] [varchar](400)
GO

ALTER TABLE [schema_ciudadesAbiertas].[padron_indicadores] ADD [autonomia_id] [varchar](50)
GO

ALTER TABLE [schema_ciudadesAbiertas].[padron_indicadores] ADD [autonomia_title] [varchar](400)
GO

ALTER TABLE [schema_ciudadesAbiertas].[padron_nacionalidad] ADD [pais_id] [varchar](50)
GO

ALTER TABLE [schema_ciudadesAbiertas].[padron_nacionalidad] ADD [pais_title] [varchar](400)
GO

ALTER TABLE [schema_ciudadesAbiertas].[padron_nacionalidad] ADD [autonomia_id] [varchar](50)
GO

ALTER TABLE [schema_ciudadesAbiertas].[padron_nacionalidad] ADD [autonomia_title] [varchar](400)
GO

ALTER TABLE [schema_ciudadesAbiertas].[padron_pais_nacimiento] ADD [pais_id] [varchar](50)
GO

ALTER TABLE [schema_ciudadesAbiertas].[padron_pais_nacimiento] ADD [pais_title] [varchar](400)
GO

ALTER TABLE [schema_ciudadesAbiertas].[padron_pais_nacimiento] ADD [autonomia_id] [varchar](50)
GO

ALTER TABLE [schema_ciudadesAbiertas].[padron_pais_nacimiento] ADD [autonomia_title] [varchar](400)
GO

ALTER TABLE [schema_ciudadesAbiertas].[padron_procedencia] ADD [pais_id] [varchar](50)
GO

ALTER TABLE [schema_ciudadesAbiertas].[padron_procedencia] ADD [pais_title] [varchar](400)
GO

ALTER TABLE [schema_ciudadesAbiertas].[padron_procedencia] ADD [autonomia_id] [varchar](50)
GO

ALTER TABLE [schema_ciudadesAbiertas].[padron_procedencia] ADD [autonomia_title] [varchar](400)
GO

UPDATE [schema_ciudadesAbiertas].[padron_edad_g_quiquenal] SET [autonomia_id] = '13', [autonomia_title] = 'Comunidad de Madrid', [pais_id] = '724', [pais_title] = N'España'
GO

UPDATE [schema_ciudadesAbiertas].[padron_edad] SET [autonomia_id] = '13', [autonomia_title] = 'Comunidad de Madrid', [pais_id] = '724', [pais_title] = N'España'
GO

UPDATE [schema_ciudadesAbiertas].[padron_indicadores] SET [autonomia_id] = '13', [autonomia_title] = 'Comunidad de Madrid', [pais_id] = '724', [pais_title] = N'España'
GO

UPDATE [schema_ciudadesAbiertas].[padron_nacionalidad] SET [autonomia_id] = '13', [autonomia_title] = 'Comunidad de Madrid', [pais_id] = '724', [pais_title] = N'España'
GO

UPDATE [schema_ciudadesAbiertas].[padron_pais_nacimiento] SET [autonomia_id] = '13', [autonomia_title] = 'Comunidad de Madrid', [pais_id] = '724', [pais_title] = N'España'
GO

UPDATE [schema_ciudadesAbiertas].[padron_procedencia] SET [autonomia_id] = '13', [autonomia_title] = 'Comunidad de Madrid', [pais_id] = '724', [pais_title] = N'España'
GO

ALTER TABLE [schema_ciudadesAbiertas].[contratos_tender] ALTER COLUMN [title] [varchar](2000) NOT NULL
GO

ALTER TABLE [schema_ciudadesAbiertas].[contratos_process] ALTER COLUMN [title] [varchar](2000) NOT NULL
GO

ALTER TABLE [schema_ciudadesAbiertas].[contratos_lot] ALTER COLUMN [title] [varchar](2000) NOT NULL
GO

ALTER TABLE [schema_ciudadesAbiertas].[trafico_dispositivo_medicion] ADD CONSTRAINT [traf_disp_medicion_ibfk_2] FOREIGN KEY ([observes]) REFERENCES [schema_ciudadesAbiertas].[trafico_propiedad_medicion] ([id])
GO

ALTER TABLE [schema_ciudadesAbiertas].[trafico_observacion] ADD CONSTRAINT [trafico_observacion_ibfk_2] FOREIGN KEY ([phenomenon_time]) REFERENCES [schema_ciudadesAbiertas].[trafico_proper_interval] ([id])
GO




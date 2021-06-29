USE [ciudadesAbiertas]
GO

CREATE TABLE [ciudadesAbiertas].[contratos_log](
	[id] [numeric](18, 0) NOT NULL,
	[load_time] [datetime] NOT NULL,
	[resume] [varchar](200) NOT NULL,
 CONSTRAINT [PK_contratos_log] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)
) ON [PRIMARY]
GO
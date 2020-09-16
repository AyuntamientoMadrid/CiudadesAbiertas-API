USE [ciudadesAbiertas]
GO
/****** Object:  UserDefinedFunction [dbo].[Translate]    Script Date: 04/02/2020 15:39:11 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE FUNCTION [dbo].[Translate]
(     @Source      VARCHAR(8000)
   , @ReplaceCharOrder    VARCHAR(8000)
   , @ReplaceWithCharOrder   VARCHAR(8000)
)
   RETURNS VARCHAR(8000) AS  

/*  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *
 *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *

   Object Name      :   dbo.Translate
   Author         :   UB for DCF, on August05, 2008
   Purpose         :   Like TRANSLATE function in Oracle. Charecter-wise replace in source string with given charecters.
   Input         :   
   Output         :   returns @Translated_Source string 
   Version         :   1.0 as of 08/05/2008
   Modification   :   
   Execute         :   SELECT dbo.Translate('ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890', '1234567890', '0987654321')

 *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *
 *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  */


BEGIN 

   --
   --   Validate input 
   --
   IF   @Source   IS NULL
         RETURN NULL
   
   IF    @Source   = ''
         RETURN ''

   IF    @ReplaceCharOrder IS NULL         OR
      @ReplaceCharOrder = ''
         RETURN @Source

   IF   @ReplaceWithCharOrder IS NULL   
         RETURN 'Invalid parameters in function call dbo.Translate'


   --
   --   Variables used
   --
   DECLARE   @Curr_Pos_In_Source      INT
      , @Check_Source_Str_Len      INT
      , @nth_source         VARCHAR(1)
      , @Found_Match         INT
      , @Translated_Source      VARCHAR(8000)
      , @Match_In_ReplaceWith      VARCHAR(1)


   --
   --   Assign starting values for variables
   --
   SELECT    @Curr_Pos_In_Source      = 1
      , @Check_Source_Str_Len      = LEN(@Source)
      , @Translated_Source      = ''



   --
   --   Replace each charecter with its corrosponding charecter from @ReplaceWithCharOrder
   --
   WHILE @Curr_Pos_In_Source <= @Check_Source_Str_Len
   BEGIN

      --
      --   Get the n'th charecter in @Source
      --
      SELECT @nth_source = SUBSTRING(@Source, @Curr_Pos_In_Source, 1)
   

      --
      --   See if there is a matching character for @nth_source in the @ReplaceCharOrder String, then replace it with 
      --   corrosponding character in @ReplaceWithCharOrder String. If not..go to next n'th character in @Source
      --      If a match is found in @ReplaceCharOrder but no corrosponding character in @ReplaceWithCharOrder
      --      then, replace it with '' (nothing)
      --   Store the resultant string in a separate variable
      --
      SELECT @Found_Match = CHARINDEX(@nth_source, @ReplaceCharOrder COLLATE SQL_Latin1_General_CP1_CS_AS)

      IF @Found_Match > 0
      BEGIN
         --
         --   Finding corrosponding match in the @Found_Match'th position in @ReplaceWithCharOrder
         --   if not found then replace it with '' (nothing)
         --
         SELECT  @Match_In_ReplaceWith = SUBSTRING(@ReplaceWithCharOrder, @Found_Match, 1)

         --
         --   Now replace @nth_source with @Match_In_ReplaceWith and store it in @Translated_Source
         --
         SELECT @Translated_Source = @Translated_Source + @Match_In_ReplaceWith
      END
      ELSE
      BEGIN
         --
         --   No match found in @ReplaceCharOrder
         --
         SELECT @Translated_Source = @Translated_Source + @nth_source
      END

      --
      --   Increment the current position for loop
      --
      SELECT @Curr_Pos_In_Source = @Curr_Pos_In_Source + 1
      
   END
   

   RETURN @Translated_Source

END
GO
/****** Object:  Table [apiDinamica].[DATABASECHANGELOG]    Script Date: 25/02/2020 14:33:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [apiDinamica].[DATABASECHANGELOG](
	[ID] [nvarchar](255) NOT NULL,
	[AUTHOR] [nvarchar](255) NOT NULL,
	[FILENAME] [nvarchar](255) NOT NULL,
	[DATEEXECUTED] [datetime2](3) NOT NULL,
	[ORDEREXECUTED] [int] NOT NULL,
	[EXECTYPE] [nvarchar](10) NOT NULL,
	[MD5SUM] [nvarchar](35) NULL,
	[DESCRIPTION] [nvarchar](255) NULL,
	[COMMENTS] [nvarchar](255) NULL,
	[TAG] [nvarchar](255) NULL,
	[LIQUIBASE] [nvarchar](20) NULL,
	[CONTEXTS] [nvarchar](255) NULL,
	[LABELS] [nvarchar](255) NULL
) ON [PRIMARY]
GO
/****** Object:  Table [apiDinamica].[DATABASECHANGELOGLOCK]    Script Date: 25/02/2020 14:33:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [apiDinamica].[DATABASECHANGELOGLOCK](
	[ID] [int] NOT NULL,
	[LOCKED] [bit] NOT NULL,
	[LOCKGRANTED] [datetime2](3) NULL,
	[LOCKEDBY] [nvarchar](255) NULL,
 CONSTRAINT [PK_DATABASECHANGELOGLOCK] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [apiDinamica].[entidad_base]    Script Date: 25/02/2020 14:33:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [apiDinamica].[entidad_base](
	[id] [varchar](50) NOT NULL,
	[texto] [varchar](100) NOT NULL,
	[texto_largo] [varchar](4000) NULL,
	[fecha] [datetime] NULL,
	[numero_entero] [int] NULL,
	[numero_decimal] [decimal](12, 2) NULL,
 CONSTRAINT [pk_base] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [apiDinamica].[estadistica]    Script Date: 25/02/2020 14:33:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [apiDinamica].[estadistica](
	[id] [varchar](50) NOT NULL,
	[url] [varchar](2000) NOT NULL,
	[fecha_peticion] [datetime] NOT NULL,
	[origen] [varchar](20) NOT NULL,
	[user_agent] [varchar](200) NOT NULL,
 CONSTRAINT [pk_estadistica] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [apiDinamica].[group_authorities]    Script Date: 25/02/2020 14:33:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [apiDinamica].[group_authorities](
	[group_id] [bigint] NOT NULL,
	[authority] [varchar](50) NOT NULL,
 CONSTRAINT [pk_group_authorities] PRIMARY KEY CLUSTERED 
(
	[group_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [apiDinamica].[group_members]    Script Date: 25/02/2020 14:33:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [apiDinamica].[group_members](
	[id] [bigint] NOT NULL,
	[username] [varchar](45) NOT NULL,
	[group_id] [bigint] NOT NULL,
 CONSTRAINT [pk_group_members] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [apiDinamica].[groups]    Script Date: 25/02/2020 14:33:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [apiDinamica].[groups](
	[id] [bigint] NOT NULL,
	[group_name] [varchar](50) NOT NULL,
 CONSTRAINT [pk_groups] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [apiDinamica].[oauth_access_token]    Script Date: 25/02/2020 14:33:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [apiDinamica].[oauth_access_token](
	[token_id] [varchar](255) NULL,
	[token] [varbinary](max) NULL,
	[authentication_id] [varchar](255) NOT NULL,
	[user_name] [varchar](255) NULL,
	[client_id] [varchar](255) NULL,
	[authentication] [varbinary](max) NULL,
	[refresh_token] [varchar](255) NULL,
 CONSTRAINT [pk_oauth_access_token] PRIMARY KEY CLUSTERED 
(
	[authentication_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [apiDinamica].[oauth_approvals]    Script Date: 25/02/2020 14:33:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [apiDinamica].[oauth_approvals](
	[userId] [varchar](255) NULL,
	[clientId] [varchar](255) NULL,
	[scope] [varchar](255) NULL,
	[status] [varchar](10) NULL,
	[expiresAt] [datetime] NOT NULL,
	[lastModifiedAt] [datetime] NULL
) ON [PRIMARY]
GO
/****** Object:  Table [apiDinamica].[oauth_client_details]    Script Date: 25/02/2020 14:33:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [apiDinamica].[oauth_client_details](
	[client_id] [varchar](255) NOT NULL,
	[resource_ids] [varchar](255) NULL,
	[client_secret] [varchar](255) NULL,
	[scope] [varchar](255) NULL,
	[authorized_grant_types] [varchar](255) NULL,
	[web_server_redirect_uri] [varchar](255) NULL,
	[authorities] [varchar](255) NULL,
	[access_token_validity] [int] NULL,
	[refresh_token_validity] [int] NULL,
	[additional_information] [varchar](2000) NULL,
	[autoapprove] [varchar](255) NULL,
 CONSTRAINT [pk_oauth_client_details] PRIMARY KEY CLUSTERED 
(
	[client_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [apiDinamica].[oauth_client_token]    Script Date: 25/02/2020 14:33:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [apiDinamica].[oauth_client_token](
	[token_id] [varchar](255) NULL,
	[token] [varbinary](max) NULL,
	[authentication_id] [varchar](255) NOT NULL,
	[user_name] [varchar](255) NULL,
	[client_id] [varchar](255) NULL,
 CONSTRAINT [pk_oauth_client_token] PRIMARY KEY CLUSTERED 
(
	[authentication_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [apiDinamica].[oauth_code]    Script Date: 25/02/2020 14:33:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [apiDinamica].[oauth_code](
	[code] [varchar](255) NULL,
	[authentication] [varbinary](max) NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [apiDinamica].[oauth_refresh_token]    Script Date: 25/02/2020 14:33:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [apiDinamica].[oauth_refresh_token](
	[token_id] [varchar](255) NULL,
	[token] [varbinary](max) NULL,
	[authentication] [varbinary](max) NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [apiDinamica].[param]    Script Date: 25/02/2020 14:33:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [apiDinamica].[param](
	[id] [int] NOT NULL,
	[query_code] [varchar](20) NOT NULL,
	[name] [varchar](20) NOT NULL,
	[type] [varchar](10) NOT NULL,
	[description] [varchar](100) NULL,
	[example] [varchar](500) NULL,
 CONSTRAINT [param-pk] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [apiDinamica].[query]    Script Date: 25/02/2020 14:33:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [apiDinamica].[query](
	[code] [varchar](20) NOT NULL,
	[texto] [varchar](4000) NOT NULL,
	[database_con] [varchar](20) NOT NULL,
	[summary] [varchar](100) NULL,
	[tags] [varchar](255) NOT NULL,
	[definition] [varchar](20) NULL,
 CONSTRAINT [query-pk] PRIMARY KEY CLUSTERED 
(
	[code] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [apiDinamica].[user_roles]    Script Date: 25/02/2020 14:33:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [apiDinamica].[user_roles](
	[user_role_id] [int] NOT NULL,
	[username] [varchar](45) NOT NULL,
	[role] [varchar](45) NOT NULL,
 CONSTRAINT [pk_user_roles] PRIMARY KEY CLUSTERED 
(
	[user_role_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [apiDinamica].[users]    Script Date: 25/02/2020 14:33:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [apiDinamica].[users](
	[username] [varchar](45) NOT NULL,
	[pass] [varchar](60) NOT NULL,
	[enabled] [tinyint] NOT NULL,
 CONSTRAINT [pk_users] PRIMARY KEY CLUSTERED 
(
	[username] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
INSERT [apiDinamica].[DATABASECHANGELOG] ([ID], [AUTHOR], [FILENAME], [DATEEXECUTED], [ORDEREXECUTED], [EXECTYPE], [MD5SUM], [DESCRIPTION], [COMMENTS], [TAG], [LIQUIBASE], [CONTEXTS], [LABELS]) VALUES (N'empty-ciudades-abiertas', N'Localidata', N'liquibase/db-changelog-script-0.0-EMPTY.xml', CAST(N'2020-02-25T13:06:51.3800000' AS DateTime2), 1, N'EXECUTED', N'7:bbfce268af0d2d9b0cd2a79fb056cd15', N'tagDatabase', N'', N'0.0', N'3.4.2', NULL, NULL)
INSERT [apiDinamica].[DATABASECHANGELOG] ([ID], [AUTHOR], [FILENAME], [DATEEXECUTED], [ORDEREXECUTED], [EXECTYPE], [MD5SUM], [DESCRIPTION], [COMMENTS], [TAG], [LIQUIBASE], [CONTEXTS], [LABELS]) VALUES (N'table-estadistica', N'Localidata', N'liquibase/db-changelog-script-1.0-CORE-table.xml', CAST(N'2020-02-25T13:06:51.3970000' AS DateTime2), 2, N'EXECUTED', N'7:c6a7f0cec14432c3580ddb06f076665d', N'createTable', N'', NULL, N'3.4.2', NULL, NULL)
INSERT [apiDinamica].[DATABASECHANGELOG] ([ID], [AUTHOR], [FILENAME], [DATEEXECUTED], [ORDEREXECUTED], [EXECTYPE], [MD5SUM], [DESCRIPTION], [COMMENTS], [TAG], [LIQUIBASE], [CONTEXTS], [LABELS]) VALUES (N'table-oauth-access-token', N'Localidata', N'liquibase/db-changelog-script-1.0-CORE-table.xml', CAST(N'2020-02-25T13:06:51.4100000' AS DateTime2), 3, N'EXECUTED', N'7:5b193f221ba44f8eb280b29fd59d4bd9', N'createTable', N'', NULL, N'3.4.2', NULL, NULL)
INSERT [apiDinamica].[DATABASECHANGELOG] ([ID], [AUTHOR], [FILENAME], [DATEEXECUTED], [ORDEREXECUTED], [EXECTYPE], [MD5SUM], [DESCRIPTION], [COMMENTS], [TAG], [LIQUIBASE], [CONTEXTS], [LABELS]) VALUES (N'table-oauth-approvals', N'Localidata', N'liquibase/db-changelog-script-1.0-CORE-table.xml', CAST(N'2020-02-25T13:06:51.4430000' AS DateTime2), 4, N'EXECUTED', N'7:62688a22372dd8f7c8eb59d9b73e5367', N'createTable', N'', NULL, N'3.4.2', NULL, NULL)
INSERT [apiDinamica].[DATABASECHANGELOG] ([ID], [AUTHOR], [FILENAME], [DATEEXECUTED], [ORDEREXECUTED], [EXECTYPE], [MD5SUM], [DESCRIPTION], [COMMENTS], [TAG], [LIQUIBASE], [CONTEXTS], [LABELS]) VALUES (N'table-oauth-client-details', N'Localidata', N'liquibase/db-changelog-script-1.0-CORE-table.xml', CAST(N'2020-02-25T13:06:51.4570000' AS DateTime2), 5, N'EXECUTED', N'7:9c97e2436866044a413f80a52588f6ec', N'createTable', N'', NULL, N'3.4.2', NULL, NULL)
INSERT [apiDinamica].[DATABASECHANGELOG] ([ID], [AUTHOR], [FILENAME], [DATEEXECUTED], [ORDEREXECUTED], [EXECTYPE], [MD5SUM], [DESCRIPTION], [COMMENTS], [TAG], [LIQUIBASE], [CONTEXTS], [LABELS]) VALUES (N'table-oauth-client-token', N'Localidata', N'liquibase/db-changelog-script-1.0-CORE-table.xml', CAST(N'2020-02-25T13:06:51.4730000' AS DateTime2), 6, N'EXECUTED', N'7:9f9984fff938a4d5116ef6eceef4b01a', N'createTable', N'', NULL, N'3.4.2', NULL, NULL)
INSERT [apiDinamica].[DATABASECHANGELOG] ([ID], [AUTHOR], [FILENAME], [DATEEXECUTED], [ORDEREXECUTED], [EXECTYPE], [MD5SUM], [DESCRIPTION], [COMMENTS], [TAG], [LIQUIBASE], [CONTEXTS], [LABELS]) VALUES (N'table-oauth-code', N'Localidata', N'liquibase/db-changelog-script-1.0-CORE-table.xml', CAST(N'2020-02-25T13:06:51.4900000' AS DateTime2), 7, N'EXECUTED', N'7:4c9bea4afc85cd184227f460427a2a36', N'createTable', N'', NULL, N'3.4.2', NULL, NULL)
INSERT [apiDinamica].[DATABASECHANGELOG] ([ID], [AUTHOR], [FILENAME], [DATEEXECUTED], [ORDEREXECUTED], [EXECTYPE], [MD5SUM], [DESCRIPTION], [COMMENTS], [TAG], [LIQUIBASE], [CONTEXTS], [LABELS]) VALUES (N'table-oauth-refresh-token', N'Localidata', N'liquibase/db-changelog-script-1.0-CORE-table.xml', CAST(N'2020-02-25T13:06:51.5030000' AS DateTime2), 8, N'EXECUTED', N'7:e3ff3683926345e15d014b7a28cc0854', N'createTable', N'', NULL, N'3.4.2', NULL, NULL)
INSERT [apiDinamica].[DATABASECHANGELOG] ([ID], [AUTHOR], [FILENAME], [DATEEXECUTED], [ORDEREXECUTED], [EXECTYPE], [MD5SUM], [DESCRIPTION], [COMMENTS], [TAG], [LIQUIBASE], [CONTEXTS], [LABELS]) VALUES (N'table-user-roles', N'Localidata', N'liquibase/db-changelog-script-1.0-CORE-table.xml', CAST(N'2020-02-25T13:06:51.5200000' AS DateTime2), 9, N'EXECUTED', N'7:f97726314258c37899248c395df39665', N'createTable', N'', NULL, N'3.4.2', NULL, NULL)
INSERT [apiDinamica].[DATABASECHANGELOG] ([ID], [AUTHOR], [FILENAME], [DATEEXECUTED], [ORDEREXECUTED], [EXECTYPE], [MD5SUM], [DESCRIPTION], [COMMENTS], [TAG], [LIQUIBASE], [CONTEXTS], [LABELS]) VALUES (N'table-users', N'Localidata', N'liquibase/db-changelog-script-1.0-CORE-table.xml', CAST(N'2020-02-25T13:06:51.5500000' AS DateTime2), 10, N'EXECUTED', N'7:e652fd1e9b69e8738bf20613721ffd39', N'createTable', N'', NULL, N'3.4.2', NULL, NULL)
INSERT [apiDinamica].[DATABASECHANGELOG] ([ID], [AUTHOR], [FILENAME], [DATEEXECUTED], [ORDEREXECUTED], [EXECTYPE], [MD5SUM], [DESCRIPTION], [COMMENTS], [TAG], [LIQUIBASE], [CONTEXTS], [LABELS]) VALUES (N'table-group-authorities', N'Localidata', N'liquibase/db-changelog-script-1.0-CORE-table.xml', CAST(N'2020-02-25T13:06:51.5670000' AS DateTime2), 11, N'EXECUTED', N'7:c9f4157fb185eb137532c79cc89c175d', N'createTable', N'', NULL, N'3.4.2', NULL, NULL)
INSERT [apiDinamica].[DATABASECHANGELOG] ([ID], [AUTHOR], [FILENAME], [DATEEXECUTED], [ORDEREXECUTED], [EXECTYPE], [MD5SUM], [DESCRIPTION], [COMMENTS], [TAG], [LIQUIBASE], [CONTEXTS], [LABELS]) VALUES (N'table-group-members', N'Localidata', N'liquibase/db-changelog-script-1.0-CORE-table.xml', CAST(N'2020-02-25T13:06:51.5830000' AS DateTime2), 12, N'EXECUTED', N'7:b116abd678e2679558ff6031f31802de', N'createTable', N'', NULL, N'3.4.2', NULL, NULL)
INSERT [apiDinamica].[DATABASECHANGELOG] ([ID], [AUTHOR], [FILENAME], [DATEEXECUTED], [ORDEREXECUTED], [EXECTYPE], [MD5SUM], [DESCRIPTION], [COMMENTS], [TAG], [LIQUIBASE], [CONTEXTS], [LABELS]) VALUES (N'table-groups', N'Localidata', N'liquibase/db-changelog-script-1.0-CORE-table.xml', CAST(N'2020-02-25T13:06:51.6000000' AS DateTime2), 13, N'EXECUTED', N'7:a1332aa076e0b118d770d2bae9b42838', N'createTable', N'', NULL, N'3.4.2', NULL, NULL)
INSERT [apiDinamica].[DATABASECHANGELOG] ([ID], [AUTHOR], [FILENAME], [DATEEXECUTED], [ORDEREXECUTED], [EXECTYPE], [MD5SUM], [DESCRIPTION], [COMMENTS], [TAG], [LIQUIBASE], [CONTEXTS], [LABELS]) VALUES (N'entidadBase', N'Localidata', N'liquibase/db-changelog-script-1.0-CORE-table.xml', CAST(N'2020-02-25T13:06:51.6130000' AS DateTime2), 14, N'EXECUTED', N'7:75f9711a6dc621c6635f22013af7334a', N'createTable', N'', NULL, N'3.4.2', NULL, NULL)
INSERT [apiDinamica].[DATABASECHANGELOG] ([ID], [AUTHOR], [FILENAME], [DATEEXECUTED], [ORDEREXECUTED], [EXECTYPE], [MD5SUM], [DESCRIPTION], [COMMENTS], [TAG], [LIQUIBASE], [CONTEXTS], [LABELS]) VALUES (N'PK-table-base', N'Localidata', N'liquibase/db-changelog-script-1.0-CORE-table.xml', CAST(N'2020-02-25T13:06:51.6770000' AS DateTime2), 15, N'EXECUTED', N'7:b814949a382eba3f205c37cf4c15a835', N'addPrimaryKey', N'', NULL, N'3.4.2', NULL, NULL)
INSERT [apiDinamica].[DATABASECHANGELOG] ([ID], [AUTHOR], [FILENAME], [DATEEXECUTED], [ORDEREXECUTED], [EXECTYPE], [MD5SUM], [DESCRIPTION], [COMMENTS], [TAG], [LIQUIBASE], [CONTEXTS], [LABELS]) VALUES (N'PK-table-estadistica', N'Localidata', N'liquibase/db-changelog-script-1.0-CORE-table.xml', CAST(N'2020-02-25T13:06:51.6930000' AS DateTime2), 16, N'EXECUTED', N'7:cf7262df414fc451d07c883ea8427f7d', N'addPrimaryKey', N'', NULL, N'3.4.2', NULL, NULL)
INSERT [apiDinamica].[DATABASECHANGELOG] ([ID], [AUTHOR], [FILENAME], [DATEEXECUTED], [ORDEREXECUTED], [EXECTYPE], [MD5SUM], [DESCRIPTION], [COMMENTS], [TAG], [LIQUIBASE], [CONTEXTS], [LABELS]) VALUES (N'PK-table-oauth-access-token', N'Localidata', N'liquibase/db-changelog-script-1.0-CORE-table.xml', CAST(N'2020-02-25T13:06:51.7230000' AS DateTime2), 17, N'EXECUTED', N'7:991ead7eb536d8f74a80d5b90d218f04', N'addPrimaryKey', N'', NULL, N'3.4.2', NULL, NULL)
INSERT [apiDinamica].[DATABASECHANGELOG] ([ID], [AUTHOR], [FILENAME], [DATEEXECUTED], [ORDEREXECUTED], [EXECTYPE], [MD5SUM], [DESCRIPTION], [COMMENTS], [TAG], [LIQUIBASE], [CONTEXTS], [LABELS]) VALUES (N'PK-table-oauth-client-details', N'Localidata', N'liquibase/db-changelog-script-1.0-CORE-table.xml', CAST(N'2020-02-25T13:06:51.7230000' AS DateTime2), 18, N'EXECUTED', N'7:886facdc9619f43493dd0a19e244603c', N'addPrimaryKey', N'', NULL, N'3.4.2', NULL, NULL)
INSERT [apiDinamica].[DATABASECHANGELOG] ([ID], [AUTHOR], [FILENAME], [DATEEXECUTED], [ORDEREXECUTED], [EXECTYPE], [MD5SUM], [DESCRIPTION], [COMMENTS], [TAG], [LIQUIBASE], [CONTEXTS], [LABELS]) VALUES (N'PK-table-oauth-client-token', N'Localidata', N'liquibase/db-changelog-script-1.0-CORE-table.xml', CAST(N'2020-02-25T13:06:51.7400000' AS DateTime2), 19, N'EXECUTED', N'7:52ec1ab295b5e32febc60b64be33e9c3', N'addPrimaryKey', N'', NULL, N'3.4.2', NULL, NULL)
INSERT [apiDinamica].[DATABASECHANGELOG] ([ID], [AUTHOR], [FILENAME], [DATEEXECUTED], [ORDEREXECUTED], [EXECTYPE], [MD5SUM], [DESCRIPTION], [COMMENTS], [TAG], [LIQUIBASE], [CONTEXTS], [LABELS]) VALUES (N'PK-table-user-roles', N'Localidata', N'liquibase/db-changelog-script-1.0-CORE-table.xml', CAST(N'2020-02-25T13:06:51.7700000' AS DateTime2), 20, N'EXECUTED', N'7:7332d9c46f9bb3dbeeee064f3ccfd49c', N'addPrimaryKey', N'', NULL, N'3.4.2', NULL, NULL)
INSERT [apiDinamica].[DATABASECHANGELOG] ([ID], [AUTHOR], [FILENAME], [DATEEXECUTED], [ORDEREXECUTED], [EXECTYPE], [MD5SUM], [DESCRIPTION], [COMMENTS], [TAG], [LIQUIBASE], [CONTEXTS], [LABELS]) VALUES (N'PK-table-users', N'Localidata', N'liquibase/db-changelog-script-1.0-CORE-table.xml', CAST(N'2020-02-25T13:06:51.7700000' AS DateTime2), 21, N'EXECUTED', N'7:01de5d6fb10427b23ef21c9989e6d46b', N'addPrimaryKey', N'', NULL, N'3.4.2', NULL, NULL)
INSERT [apiDinamica].[DATABASECHANGELOG] ([ID], [AUTHOR], [FILENAME], [DATEEXECUTED], [ORDEREXECUTED], [EXECTYPE], [MD5SUM], [DESCRIPTION], [COMMENTS], [TAG], [LIQUIBASE], [CONTEXTS], [LABELS]) VALUES (N'Unique-field-user_roles', N'Localidata', N'liquibase/db-changelog-script-1.0-CORE-table.xml', CAST(N'2020-02-25T13:06:51.7870000' AS DateTime2), 22, N'EXECUTED', N'7:96f86e27cdb2422b854506bb1fa4c84a', N'addUniqueConstraint', N'', NULL, N'3.4.2', NULL, NULL)
INSERT [apiDinamica].[DATABASECHANGELOG] ([ID], [AUTHOR], [FILENAME], [DATEEXECUTED], [ORDEREXECUTED], [EXECTYPE], [MD5SUM], [DESCRIPTION], [COMMENTS], [TAG], [LIQUIBASE], [CONTEXTS], [LABELS]) VALUES (N'Indice-username-table-user_roles', N'Localidata', N'liquibase/db-changelog-script-1.0-CORE-table.xml', CAST(N'2020-02-25T13:06:51.8000000' AS DateTime2), 23, N'EXECUTED', N'7:fbac76edc3b9fbfbe0d64e954e5335e6', N'createIndex', N'', NULL, N'3.4.2', NULL, NULL)
INSERT [apiDinamica].[DATABASECHANGELOG] ([ID], [AUTHOR], [FILENAME], [DATEEXECUTED], [ORDEREXECUTED], [EXECTYPE], [MD5SUM], [DESCRIPTION], [COMMENTS], [TAG], [LIQUIBASE], [CONTEXTS], [LABELS]) VALUES (N'function-TRANSLAT', N'Localidata', N'liquibase/db-changelog-script-1.0-SQLSERVER-FUNCIONES.xml', CAST(N'2020-02-25T13:06:51.8630000' AS DateTime2), 24, N'EXECUTED', N'7:c2389830cbbc7058cab2feca546186f6', N'sqlFile (x2)', N'', NULL, N'3.4.2', NULL, NULL)
INSERT [apiDinamica].[DATABASECHANGELOG] ([ID], [AUTHOR], [FILENAME], [DATEEXECUTED], [ORDEREXECUTED], [EXECTYPE], [MD5SUM], [DESCRIPTION], [COMMENTS], [TAG], [LIQUIBASE], [CONTEXTS], [LABELS]) VALUES (N'data-user-roles', N'Localidata', N'liquibase/db-changelog-script-1.0-CORE-data.xml', CAST(N'2020-02-25T13:06:51.8800000' AS DateTime2), 25, N'EXECUTED', N'7:ac1092dcc0f09000fcf50228877d4e5a', N'insert (x3)', N'', NULL, N'3.4.2', NULL, NULL)
INSERT [apiDinamica].[DATABASECHANGELOG] ([ID], [AUTHOR], [FILENAME], [DATEEXECUTED], [ORDEREXECUTED], [EXECTYPE], [MD5SUM], [DESCRIPTION], [COMMENTS], [TAG], [LIQUIBASE], [CONTEXTS], [LABELS]) VALUES (N'data-users', N'Localidata', N'liquibase/db-changelog-script-1.0-CORE-data.xml', CAST(N'2020-02-25T13:06:51.8970000' AS DateTime2), 26, N'EXECUTED', N'7:510f8602fefea9b6edb555dafb672949', N'insert (x2)', N'', NULL, N'3.4.2', NULL, NULL)
INSERT [apiDinamica].[DATABASECHANGELOG] ([ID], [AUTHOR], [FILENAME], [DATEEXECUTED], [ORDEREXECUTED], [EXECTYPE], [MD5SUM], [DESCRIPTION], [COMMENTS], [TAG], [LIQUIBASE], [CONTEXTS], [LABELS]) VALUES (N'data-groups', N'Localidata', N'liquibase/db-changelog-script-1.0-CORE-data.xml', CAST(N'2020-02-25T13:06:51.9100000' AS DateTime2), 27, N'EXECUTED', N'7:7ca7bbea357ee7c45482cc9354d94296', N'insert', N'', NULL, N'3.4.2', NULL, NULL)
INSERT [apiDinamica].[DATABASECHANGELOG] ([ID], [AUTHOR], [FILENAME], [DATEEXECUTED], [ORDEREXECUTED], [EXECTYPE], [MD5SUM], [DESCRIPTION], [COMMENTS], [TAG], [LIQUIBASE], [CONTEXTS], [LABELS]) VALUES (N'data-group-authorities', N'Localidata', N'liquibase/db-changelog-script-1.0-CORE-data.xml', CAST(N'2020-02-25T13:06:51.9270000' AS DateTime2), 28, N'EXECUTED', N'7:3f8747c4c08ac9ea4ed9ccc39fc62093', N'insert', N'', NULL, N'3.4.2', NULL, NULL)
INSERT [apiDinamica].[DATABASECHANGELOG] ([ID], [AUTHOR], [FILENAME], [DATEEXECUTED], [ORDEREXECUTED], [EXECTYPE], [MD5SUM], [DESCRIPTION], [COMMENTS], [TAG], [LIQUIBASE], [CONTEXTS], [LABELS]) VALUES (N'data-group-members', N'Localidata', N'liquibase/db-changelog-script-1.0-CORE-data.xml', CAST(N'2020-02-25T13:06:51.9270000' AS DateTime2), 29, N'EXECUTED', N'7:c641227f106de85fffe90f7a50316206', N'insert', N'', NULL, N'3.4.2', NULL, NULL)
INSERT [apiDinamica].[DATABASECHANGELOG] ([ID], [AUTHOR], [FILENAME], [DATEEXECUTED], [ORDEREXECUTED], [EXECTYPE], [MD5SUM], [DESCRIPTION], [COMMENTS], [TAG], [LIQUIBASE], [CONTEXTS], [LABELS]) VALUES (N'PK-table-group-authorities', N'Localidata', N'liquibase/db-changelog-script-1.0-CORE-data.xml', CAST(N'2020-02-25T13:06:52.0030000' AS DateTime2), 30, N'EXECUTED', N'7:e1e0ad7e580044e3f6f7629dbee76df0', N'addPrimaryKey', N'', NULL, N'3.4.2', NULL, NULL)
INSERT [apiDinamica].[DATABASECHANGELOG] ([ID], [AUTHOR], [FILENAME], [DATEEXECUTED], [ORDEREXECUTED], [EXECTYPE], [MD5SUM], [DESCRIPTION], [COMMENTS], [TAG], [LIQUIBASE], [CONTEXTS], [LABELS]) VALUES (N'PK-table-group-members', N'Localidata', N'liquibase/db-changelog-script-1.0-CORE-data.xml', CAST(N'2020-02-25T13:06:52.0200000' AS DateTime2), 31, N'EXECUTED', N'7:565c0dfcc407b9e477d41bc6ffd558af', N'addPrimaryKey', N'', NULL, N'3.4.2', NULL, NULL)
INSERT [apiDinamica].[DATABASECHANGELOG] ([ID], [AUTHOR], [FILENAME], [DATEEXECUTED], [ORDEREXECUTED], [EXECTYPE], [MD5SUM], [DESCRIPTION], [COMMENTS], [TAG], [LIQUIBASE], [CONTEXTS], [LABELS]) VALUES (N'PK-table-groups', N'Localidata', N'liquibase/db-changelog-script-1.0-CORE-data.xml', CAST(N'2020-02-25T13:06:52.0500000' AS DateTime2), 32, N'EXECUTED', N'7:d17293c9f0aacd1f652b0d5b64ec81d1', N'addPrimaryKey', N'', NULL, N'3.4.2', NULL, NULL)
INSERT [apiDinamica].[DATABASECHANGELOG] ([ID], [AUTHOR], [FILENAME], [DATEEXECUTED], [ORDEREXECUTED], [EXECTYPE], [MD5SUM], [DESCRIPTION], [COMMENTS], [TAG], [LIQUIBASE], [CONTEXTS], [LABELS]) VALUES (N'FK-USER-ROLE-USERNAME', N'Localidata', N'liquibase/db-changelog-script-1.0-CORE-data.xml', CAST(N'2020-02-25T13:06:52.0830000' AS DateTime2), 33, N'EXECUTED', N'7:76bd6afc490b2fed2aff27036df3791b', N'addForeignKeyConstraint', N'', NULL, N'3.4.2', NULL, NULL)
INSERT [apiDinamica].[DATABASECHANGELOG] ([ID], [AUTHOR], [FILENAME], [DATEEXECUTED], [ORDEREXECUTED], [EXECTYPE], [MD5SUM], [DESCRIPTION], [COMMENTS], [TAG], [LIQUIBASE], [CONTEXTS], [LABELS]) VALUES (N'FK-GROUP-MEMBERS-USERS', N'Localidata', N'liquibase/db-changelog-script-1.0-CORE-data.xml', CAST(N'2020-02-25T13:06:52.0830000' AS DateTime2), 34, N'EXECUTED', N'7:34873c68b5f685c58167c1cd4650afaf', N'addForeignKeyConstraint', N'', NULL, N'3.4.2', NULL, NULL)
INSERT [apiDinamica].[DATABASECHANGELOG] ([ID], [AUTHOR], [FILENAME], [DATEEXECUTED], [ORDEREXECUTED], [EXECTYPE], [MD5SUM], [DESCRIPTION], [COMMENTS], [TAG], [LIQUIBASE], [CONTEXTS], [LABELS]) VALUES (N'FK-GROUP-MEMBERS-GROUP', N'Localidata', N'liquibase/db-changelog-script-1.0-CORE-data.xml', CAST(N'2020-02-25T13:06:52.1130000' AS DateTime2), 35, N'EXECUTED', N'7:8dcbba0717c053e81702277e406a4c9d', N'addForeignKeyConstraint', N'', NULL, N'3.4.2', NULL, NULL)
INSERT [apiDinamica].[DATABASECHANGELOG] ([ID], [AUTHOR], [FILENAME], [DATEEXECUTED], [ORDEREXECUTED], [EXECTYPE], [MD5SUM], [DESCRIPTION], [COMMENTS], [TAG], [LIQUIBASE], [CONTEXTS], [LABELS]) VALUES (N'FK-GROUP-AUTHORI-GROUP', N'Localidata', N'liquibase/db-changelog-script-1.0-CORE-data.xml', CAST(N'2020-02-25T13:06:52.1300000' AS DateTime2), 36, N'EXECUTED', N'7:553a44914ab92b994237f8e1b522c9da', N'addForeignKeyConstraint', N'', NULL, N'3.4.2', NULL, NULL)
INSERT [apiDinamica].[DATABASECHANGELOG] ([ID], [AUTHOR], [FILENAME], [DATEEXECUTED], [ORDEREXECUTED], [EXECTYPE], [MD5SUM], [DESCRIPTION], [COMMENTS], [TAG], [LIQUIBASE], [CONTEXTS], [LABELS]) VALUES (N'DATA-CORE-tag-1.0', N'Localidata', N'liquibase/db-changelog-script-1.0-CORE-data.xml', CAST(N'2020-02-25T13:06:52.1300000' AS DateTime2), 37, N'EXECUTED', N'7:5560655641c5ef77bca6b90c5b234724', N'tagDatabase', N'', N'1.0', N'3.4.2', NULL, NULL)
INSERT [apiDinamica].[DATABASECHANGELOG] ([ID], [AUTHOR], [FILENAME], [DATEEXECUTED], [ORDEREXECUTED], [EXECTYPE], [MD5SUM], [DESCRIPTION], [COMMENTS], [TAG], [LIQUIBASE], [CONTEXTS], [LABELS]) VALUES (N'table-query', N'Localidata', N'liquibase/db-changelog-script-2.0-DYNAMIC_SQL-table.xml', CAST(N'2020-02-25T13:06:52.1470000' AS DateTime2), 38, N'EXECUTED', N'7:9306cef60bb7a9f607fb42b0dc764bb6', N'createTable', N'', NULL, N'3.4.2', NULL, NULL)
INSERT [apiDinamica].[DATABASECHANGELOG] ([ID], [AUTHOR], [FILENAME], [DATEEXECUTED], [ORDEREXECUTED], [EXECTYPE], [MD5SUM], [DESCRIPTION], [COMMENTS], [TAG], [LIQUIBASE], [CONTEXTS], [LABELS]) VALUES (N'table-param', N'Localidata', N'liquibase/db-changelog-script-2.0-DYNAMIC_SQL-table.xml', CAST(N'2020-02-25T13:06:52.1770000' AS DateTime2), 39, N'EXECUTED', N'7:399edc8fb4adf2c2f6f9323343e14e9a', N'createTable', N'', NULL, N'3.4.2', NULL, NULL)
INSERT [apiDinamica].[DATABASECHANGELOG] ([ID], [AUTHOR], [FILENAME], [DATEEXECUTED], [ORDEREXECUTED], [EXECTYPE], [MD5SUM], [DESCRIPTION], [COMMENTS], [TAG], [LIQUIBASE], [CONTEXTS], [LABELS]) VALUES (N'PK-table-query', N'Localidata', N'liquibase/db-changelog-script-2.0-DYNAMIC_SQL-table.xml', CAST(N'2020-02-25T13:06:52.1930000' AS DateTime2), 40, N'EXECUTED', N'7:a5a26e86150267669c42019d515dbd20', N'addPrimaryKey', N'', NULL, N'3.4.2', NULL, NULL)
INSERT [apiDinamica].[DATABASECHANGELOG] ([ID], [AUTHOR], [FILENAME], [DATEEXECUTED], [ORDEREXECUTED], [EXECTYPE], [MD5SUM], [DESCRIPTION], [COMMENTS], [TAG], [LIQUIBASE], [CONTEXTS], [LABELS]) VALUES (N'PK-table-param', N'Localidata', N'liquibase/db-changelog-script-2.0-DYNAMIC_SQL-table.xml', CAST(N'2020-02-25T13:06:52.2070000' AS DateTime2), 41, N'EXECUTED', N'7:4491b7be30662cc062b2e313564f0eaa', N'addPrimaryKey', N'', NULL, N'3.4.2', NULL, NULL)
INSERT [apiDinamica].[DATABASECHANGELOG] ([ID], [AUTHOR], [FILENAME], [DATEEXECUTED], [ORDEREXECUTED], [EXECTYPE], [MD5SUM], [DESCRIPTION], [COMMENTS], [TAG], [LIQUIBASE], [CONTEXTS], [LABELS]) VALUES (N'Index-code-param', N'Localidata', N'liquibase/db-changelog-script-2.0-DYNAMIC_SQL-table.xml', CAST(N'2020-02-25T13:06:52.2070000' AS DateTime2), 42, N'EXECUTED', N'7:71324acfced822683c74215ecb48bfad', N'createIndex', N'', NULL, N'3.4.2', NULL, NULL)
INSERT [apiDinamica].[DATABASECHANGELOG] ([ID], [AUTHOR], [FILENAME], [DATEEXECUTED], [ORDEREXECUTED], [EXECTYPE], [MD5SUM], [DESCRIPTION], [COMMENTS], [TAG], [LIQUIBASE], [CONTEXTS], [LABELS]) VALUES (N'FK-param-query', N'Localidata', N'liquibase/db-changelog-script-2.0-DYNAMIC_SQL-table.xml', CAST(N'2020-02-25T13:06:52.2400000' AS DateTime2), 43, N'EXECUTED', N'7:46c4b7abea340f35a139a595b6d32aae', N'addForeignKeyConstraint', N'', NULL, N'3.4.2', NULL, NULL)
INSERT [apiDinamica].[DATABASECHANGELOG] ([ID], [AUTHOR], [FILENAME], [DATEEXECUTED], [ORDEREXECUTED], [EXECTYPE], [MD5SUM], [DESCRIPTION], [COMMENTS], [TAG], [LIQUIBASE], [CONTEXTS], [LABELS]) VALUES (N'DATA-CORE-tag-2.0', N'Localidata', N'liquibase/db-changelog-script-2.0-DYNAMIC_SQL-table.xml', CAST(N'2020-02-25T13:06:52.2700000' AS DateTime2), 44, N'EXECUTED', N'7:aea2a994f82fdf3ace7794cc62e3a10f', N'tagDatabase', N'', N'2.0', N'3.4.2', NULL, NULL)
INSERT [apiDinamica].[DATABASECHANGELOGLOCK] ([ID], [LOCKED], [LOCKGRANTED], [LOCKEDBY]) VALUES (1, 0, NULL, NULL)
INSERT [apiDinamica].[group_authorities] ([group_id], [authority]) VALUES (1, N'consulta')
INSERT [apiDinamica].[group_members] ([id], [username], [group_id]) VALUES (1, N'localidata', 1)
INSERT [apiDinamica].[groups] ([id], [group_name]) VALUES (1, N'Grupo Ciudades Abiertas')
INSERT [apiDinamica].[user_roles] ([user_role_id], [username], [role]) VALUES (2, N'admin', N'ROLE_ADMIN')
INSERT [apiDinamica].[user_roles] ([user_role_id], [username], [role]) VALUES (1, N'admin', N'ROLE_USER')
INSERT [apiDinamica].[user_roles] ([user_role_id], [username], [role]) VALUES (3, N'localidata', N'ROLE_USER')
INSERT [apiDinamica].[users] ([username], [pass], [enabled]) VALUES (N'admin', N'$2a$10$8YBOCvb.UC0Lk40f2Gd2Uugxmk7QPeGSnT1oDRjxzuRrAVYNO62ke', 1)
INSERT [apiDinamica].[users] ([username], [pass], [enabled]) VALUES (N'localidata', N'$2a$10$8YBOCvb.UC0Lk40f2Gd2Uugxmk7QPeGSnT1oDRjxzuRrAVYNO62ke', 1)
SET ANSI_PADDING ON
GO
/****** Object:  Index [code]    Script Date: 25/02/2020 14:33:25 ******/
CREATE NONCLUSTERED INDEX [code] ON [apiDinamica].[param]
(
	[query_code] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [uni_username_role]    Script Date: 25/02/2020 14:33:25 ******/
ALTER TABLE [apiDinamica].[user_roles] ADD  CONSTRAINT [uni_username_role] UNIQUE NONCLUSTERED 
(
	[role] ASC,
	[username] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [index_username_idx]    Script Date: 25/02/2020 14:33:25 ******/
CREATE NONCLUSTERED INDEX [index_username_idx] ON [apiDinamica].[user_roles]
(
	[username] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
ALTER TABLE [apiDinamica].[oauth_approvals] ADD  CONSTRAINT [DF_oauth_approvals_expiresAt]  DEFAULT (getdate()) FOR [expiresAt]
GO
ALTER TABLE [apiDinamica].[users] ADD  CONSTRAINT [DF_users_enabled]  DEFAULT ((1)) FOR [enabled]
GO
ALTER TABLE [apiDinamica].[group_authorities]  WITH CHECK ADD  CONSTRAINT [fk_group_authori_users] FOREIGN KEY([group_id])
REFERENCES [apiDinamica].[groups] ([id])
GO
ALTER TABLE [apiDinamica].[group_authorities] CHECK CONSTRAINT [fk_group_authori_users]
GO
ALTER TABLE [apiDinamica].[group_members]  WITH CHECK ADD  CONSTRAINT [fk_group_memebers_groups] FOREIGN KEY([group_id])
REFERENCES [apiDinamica].[groups] ([id])
GO
ALTER TABLE [apiDinamica].[group_members] CHECK CONSTRAINT [fk_group_memebers_groups]
GO
ALTER TABLE [apiDinamica].[group_members]  WITH CHECK ADD  CONSTRAINT [fk_group_memebers_users] FOREIGN KEY([username])
REFERENCES [apiDinamica].[users] ([username])
GO
ALTER TABLE [apiDinamica].[group_members] CHECK CONSTRAINT [fk_group_memebers_users]
GO
ALTER TABLE [apiDinamica].[param]  WITH CHECK ADD  CONSTRAINT [param_ibfk_1] FOREIGN KEY([query_code])
REFERENCES [apiDinamica].[query] ([code])
GO
ALTER TABLE [apiDinamica].[param] CHECK CONSTRAINT [param_ibfk_1]
GO
ALTER TABLE [apiDinamica].[user_roles]  WITH CHECK ADD  CONSTRAINT [fk_user_role_username] FOREIGN KEY([username])
REFERENCES [apiDinamica].[users] ([username])
GO
ALTER TABLE [apiDinamica].[user_roles] CHECK CONSTRAINT [fk_user_role_username]
GO
USE [master]
GO
ALTER DATABASE [ciudadesAbiertas] SET  READ_WRITE 
GO

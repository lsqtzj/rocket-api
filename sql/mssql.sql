USE [rocket-api]
GO

/****** Object:  Table [dbo].[api_config]    Script Date: 2021/7/1 10:38:50 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[api_config](
	[id] [varchar](45) NOT NULL,
	[service] [varchar](45) NOT NULL,
	[config_context] [text] NULL,
PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO


CREATE TABLE [dbo].[api_directory](
	[id] [varchar](45) NOT NULL,
	[name] [varchar](45) NULL,
	[path] [varchar](200) NULL,
	[parent_id] [varchar](45) NULL,
	[service] [varchar](45) NULL,
PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[api_directory] ADD  DEFAULT (NULL) FOR [name]
GO

ALTER TABLE [dbo].[api_directory] ADD  DEFAULT (NULL) FOR [path]
GO

ALTER TABLE [dbo].[api_directory] ADD  DEFAULT (NULL) FOR [parent_id]
GO

ALTER TABLE [dbo].[api_directory] ADD  DEFAULT (NULL) FOR [service]
GO

CREATE TABLE [dbo].[api_example](
	[id] [varchar](45) NOT NULL,
	[api_info_id] [varchar](45) NOT NULL,
	[method] [varchar](45) NULL,
	[url] [text] NULL,
	[request_header] [text] NULL,
	[request_body] [text] NULL,
	[response_header] [text] NULL,
	[response_body] [text] NULL,
	[status] [varchar](10) NULL,
	[elapsed_time] [int] NULL,
	[options] [text] NULL,
	[editor] [varchar](45) NULL,
	[create_time] [varchar](45) NULL,
PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

ALTER TABLE [dbo].[api_example] ADD  DEFAULT (NULL) FOR [method]
GO

ALTER TABLE [dbo].[api_example] ADD  DEFAULT (NULL) FOR [status]
GO

ALTER TABLE [dbo].[api_example] ADD  DEFAULT (NULL) FOR [elapsed_time]
GO

ALTER TABLE [dbo].[api_example] ADD  DEFAULT (NULL) FOR [editor]
GO

ALTER TABLE [dbo].[api_example] ADD  DEFAULT (NULL) FOR [create_time]
GO

CREATE TABLE [dbo].[api_info](
	[id] [varchar](45) NOT NULL,
	[method] [varchar](45) NULL,
	[path] [varchar](100) NULL,
	[type] [varchar](5) NULL,
	[service] [varchar](45) NULL,
	[group_name] [varchar](45) NULL,
	[editor] [varchar](45) NULL,
	[name] [varchar](200) NULL,
	[datasource] [varchar](45) NULL,
	[script] [text] NULL,
	[options] [text] NULL,
	[create_time] [varchar](45) NULL,
	[full_path] [varchar](200) NULL,
	[directory_id] [varchar](45) NULL,
	[update_time] [varchar](45) NULL,
 CONSTRAINT [PK__api_info__3213E83F05A8BA6D] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

ALTER TABLE [dbo].[api_info] ADD  CONSTRAINT [DF__api_info__method__37A5467C]  DEFAULT (NULL) FOR [method]
GO

ALTER TABLE [dbo].[api_info] ADD  CONSTRAINT [DF__api_info__path__38996AB5]  DEFAULT (NULL) FOR [path]
GO

ALTER TABLE [dbo].[api_info] ADD  CONSTRAINT [DF__api_info__type__398D8EEE]  DEFAULT (NULL) FOR [type]
GO

ALTER TABLE [dbo].[api_info] ADD  CONSTRAINT [DF__api_info__servic__3A81B327]  DEFAULT (NULL) FOR [service]
GO

ALTER TABLE [dbo].[api_info] ADD  CONSTRAINT [DF__api_info__group___3B75D760]  DEFAULT (NULL) FOR [group_name]
GO

ALTER TABLE [dbo].[api_info] ADD  CONSTRAINT [DF__api_info__editor__3C69FB99]  DEFAULT (NULL) FOR [editor]
GO

ALTER TABLE [dbo].[api_info] ADD  CONSTRAINT [DF__api_info__name__3D5E1FD2]  DEFAULT (NULL) FOR [name]
GO

ALTER TABLE [dbo].[api_info] ADD  CONSTRAINT [DF__api_info__dataso__3E52440B]  DEFAULT (NULL) FOR [datasource]
GO

ALTER TABLE [dbo].[api_info] ADD  CONSTRAINT [DF__api_info__create__3F466844]  DEFAULT (NULL) FOR [create_time]
GO

ALTER TABLE [dbo].[api_info] ADD  CONSTRAINT [DF__api_info__update__403A8C7D]  DEFAULT (NULL) FOR [update_time]
GO


CREATE TABLE [dbo].[api_info_history](
	[id] [varchar](45) NOT NULL,
	[api_info_id] [varchar](45) NOT NULL,
	[method] [varchar](45) NULL,
	[path] [varchar](100) NULL,
	[type] [varchar](5) NULL,
	[service] [varchar](45) NULL,
	[group_name] [varchar](45) NULL,
	[editor] [varchar](45) NULL,
	[name] [varchar](200) NULL,
	[datasource] [varchar](45) NULL,
	[script] [text] NULL,
	[options] [text] NULL,
	[create_time] [varchar](45) NULL,
	[full_path] [varchar](200) NULL,
	[directory_id] [varchar](45) NULL,
PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

ALTER TABLE [dbo].[api_info_history] ADD  DEFAULT (NULL) FOR [method]
GO

ALTER TABLE [dbo].[api_info_history] ADD  DEFAULT (NULL) FOR [path]
GO

ALTER TABLE [dbo].[api_info_history] ADD  DEFAULT (NULL) FOR [type]
GO

ALTER TABLE [dbo].[api_info_history] ADD  DEFAULT (NULL) FOR [service]
GO

ALTER TABLE [dbo].[api_info_history] ADD  DEFAULT (NULL) FOR [group_name]
GO

ALTER TABLE [dbo].[api_info_history] ADD  DEFAULT (NULL) FOR [editor]
GO

ALTER TABLE [dbo].[api_info_history] ADD  DEFAULT (NULL) FOR [name]
GO

ALTER TABLE [dbo].[api_info_history] ADD  DEFAULT (NULL) FOR [datasource]
GO

ALTER TABLE [dbo].[api_info_history] ADD  DEFAULT (NULL) FOR [create_time]
GO

CREATE TABLE [dbo].[sys_user](
	[id] [varchar](45) NOT NULL,
	[name] [varchar](255) NOT NULL,
	[password] [varchar](255) NOT NULL,
	[token] [varchar](200) NOT NULL,
 CONSTRAINT [PK__sys_user__3213E83FE745B1C0] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

INSERT INTO [dbo].[sys_user]
           ([id]
           ,[name]
           ,[password]
           ,[token])
     VALUES
           ('60dc6ed8131aba3a000c9a66','admin','123','eyJ1c2VybmFtZSI6ImFkbWluIiwicGFzc3dvcmQiOiIxMjMifQ==')
GO


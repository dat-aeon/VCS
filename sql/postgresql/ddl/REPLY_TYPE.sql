CREATE TABLE public.reply_type
(
  reply_type_id character(1) NOT NULL,
  reply_type_name character varying(200),
  CONSTRAINT reply_type_pkey PRIMARY KEY (reply_type_id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.reply_type
  OWNER TO postgres;
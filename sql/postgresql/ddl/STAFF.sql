CREATE TABLE public.staff
(
  last_upd_time timestamp without time zone NOT NULL DEFAULT now(),
  upd_count numeric(12,0) NOT NULL,
  del_div character(1) NOT NULL,
  staff_id character varying(20) NOT NULL,
  staff_name character varying(50) NOT NULL,
  password character varying(100) NOT NULL,
  staff_type character varying(50) NOT NULL,
  created_by character varying(30) NOT NULL,
  created_date timestamp without time zone NOT NULL,
  updated_by character varying(9),
  CONSTRAINT staff_pkey PRIMARY KEY (staff_id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.staff
  OWNER TO postgres;
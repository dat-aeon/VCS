CREATE TABLE public.customer
(
  last_upd_time timestamp without time zone NOT NULL DEFAULT now(),
  upd_count numeric(12,0) NOT NULL,
  del_div character(1),
  cust_agreement_code character varying(100) NOT NULL,
  cust_phone_no character varying(15) NOT NULL,
  cust_name character varying(100) NOT NULL,
  cust_payment_timer integer,
  cust_payment_amount numeric(12,4),
  reply_type_id character(1) NOT NULL,
  reply_date timestamp without time zone,
  reply_count character(1),
  comment character varying(1000),
  actual_payment_date date,
  cust_call_status character varying(9),
  paid_status character varying(1),
  actual_payment_amount numeric(12,2),
  created_by character varying(20) NOT NULL,
  created_date timestamp without time zone NOT NULL,
  updated_by character varying(20),
  CONSTRAINT customer_pkey PRIMARY KEY (cust_agreement_code)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.customer
  OWNER TO postgres;
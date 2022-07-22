FROM postgres:12

COPY resources/1_schema.sql /docker-entrypoint-initdb.d/
COPY resources/2_data.sql /docker-entrypoint-initdb.d/


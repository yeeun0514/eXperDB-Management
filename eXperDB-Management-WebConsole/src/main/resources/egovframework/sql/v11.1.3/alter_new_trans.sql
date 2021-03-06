INSERT INTO T_SYSGRP_C(GRP_CD, GRP_CD_NM, GRP_CD_EXP, USE_YN, FRST_REGR_ID, FRST_REG_DTM, LST_MDFR_ID, LST_MDF_DTM ) VALUES( 'TC0037', '압축형태', '데이터전송 압축형태', 'Y', 'ADMIN', clock_timestamp(), 'ADMIN', clock_timestamp());

INSERT INTO T_SYSDTL_C(GRP_CD, SYS_CD, SYS_CD_NM, USE_YN, FRST_REGR_ID, FRST_REG_DTM, LST_MDFR_ID, LST_MDF_DTM ) VALUES('TC0037', 'TC003701', 'NONE', 'Y', 'ADMIN', CLOCK_TIMESTAMP(), 'ADMIN', CLOCK_TIMESTAMP());
INSERT INTO T_SYSDTL_C(GRP_CD, SYS_CD, SYS_CD_NM, USE_YN, FRST_REGR_ID, FRST_REG_DTM, LST_MDFR_ID, LST_MDF_DTM ) VALUES('TC0037', 'TC003702', 'GZIP', 'Y', 'ADMIN', CLOCK_TIMESTAMP(), 'ADMIN', CLOCK_TIMESTAMP());
INSERT INTO T_SYSDTL_C(GRP_CD, SYS_CD, SYS_CD_NM, USE_YN, FRST_REGR_ID, FRST_REG_DTM, LST_MDFR_ID, LST_MDF_DTM ) VALUES('TC0037', 'TC003703', 'SNAPPY', 'Y', 'ADMIN', CLOCK_TIMESTAMP(), 'ADMIN', CLOCK_TIMESTAMP());
INSERT INTO T_SYSDTL_C(GRP_CD, SYS_CD, SYS_CD_NM, USE_YN, FRST_REGR_ID, FRST_REG_DTM, LST_MDFR_ID, LST_MDF_DTM ) VALUES('TC0037', 'TC003704', 'LZ4', 'Y', 'ADMIN', CLOCK_TIMESTAMP(), 'ADMIN', CLOCK_TIMESTAMP());
INSERT INTO T_SYSDTL_C(GRP_CD, SYS_CD, SYS_CD_NM, USE_YN, FRST_REGR_ID, FRST_REG_DTM, LST_MDFR_ID, LST_MDF_DTM ) VALUES('TC0037', 'TC003705', 'ZSTD', 'Y', 'ADMIN', CLOCK_TIMESTAMP(), 'ADMIN', CLOCK_TIMESTAMP());


ALTER TABLE T_TRANSCNG_I ADD COLUMN compression_type varchar(50);
COMMENT ON COLUMN T_TRANSCNG_I.compression_type IS '압축형태';
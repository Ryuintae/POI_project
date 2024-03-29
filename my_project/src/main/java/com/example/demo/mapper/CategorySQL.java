package com.example.demo.mapper;


public class CategorySQL {
    public String findByLCLASCD() {
        return "SELECT DISTINCT ON (lclascd) * FROM public.poi_category_data WHERE lclascd BETWEEN 1 AND 10";
    }

    public String findByMLSFCCD(final int lclascd) {
        return "SELECT * FROM public.poi_category_data WHERE lclascd = " + lclascd;
    }

    public String findByLCLASCDAndMLSFCCD(Integer lclascd) {
        String SELECT_QUERY = "SELECT DISTINCT ON (mlsfccd) * FROM public.poi_category_data WHERE";

        StringBuilder query = new StringBuilder(SELECT_QUERY);

        // lclascd 값이 null이 아닌 경우, 쿼리에 lclascd 조건을 추가합니다.
        if (lclascd != null) {
            query.append(" lclascd = #{lclascd}");
        }

        // 완성된 쿼리 문자열을 반환합니다.
        return query.toString();
    }

    public String findByMLSFCCDAndSCLASCD(Integer lclascd, Integer mlsfccd) {
        String SELECT_QUERY = "SELECT DISTINCT ON (sclascd) * FROM public.poi_category_data WHERE";

        StringBuilder query = new StringBuilder(SELECT_QUERY);

        boolean hasPrevCondition = false;

        // lclascd 값이 null이 아닌 경우, 쿼리에 lclascd 조건을 추가합니다.
        if (lclascd != null) {
            query.append(" lclascd = #{lclascd}");
            hasPrevCondition = true;
        }

        // mlsfccd 값이 null이 아닌 경우, 쿼리에 mlsfccd 조건을 추가합니다.
        // 이전 조건이 있다면 AND 구문을 사용하여 추가합니다.
        if (mlsfccd != null) {
            if (hasPrevCondition) {
                query.append(" AND");
            }
            query.append(" mlsfccd = #{mlsfccd}");
        }

        // 완성된 쿼리 문자열을 반환합니다.
        return query.toString();
    }

    public String findBySCLASCDAndDCLASCD(Integer lclascd, Integer mlsfccd, Integer sclascd) {
        String SELECT_QUERY = "SELECT DISTINCT ON (dclascd) * FROM public.poi_category_data WHERE";

        StringBuilder query = new StringBuilder(SELECT_QUERY);

        boolean hasPrevCondition = false;

        // lclascd 값이 null이 아닌 경우, 쿼리에 lclascd 조건을 추가합니다.
        if (lclascd != null) {
            query.append(" lclascd = #{lclascd}");
            hasPrevCondition = true;
        }

        // mlsfccd 값이 null이 아닌 경우, 쿼리에 mlsfccd 조건을 추가합니다.
        // 이전 조건이 있다면 AND 구문을 사용하여 추가합니다.
        if (mlsfccd != null) {
            if (hasPrevCondition) {
                query.append(" AND");
            }
            query.append(" mlsfccd = #{mlsfccd}");
        }
        if (sclascd != null) {
            if (hasPrevCondition) {
                query.append(" AND");
            }
            query.append(" sclascd = #{sclascd}");
        }

        // 완성된 쿼리 문자열을 반환합니다.
        return query.toString();
    }

    public String findByDCLASCDAndBCLASCD(Integer lclascd, Integer mlsfccd, Integer sclascd, Integer dclascd) {
        String SELECT_QUERY = "SELECT DISTINCT ON (bclascd) * FROM public.poi_category_data WHERE";

        StringBuilder query = new StringBuilder(SELECT_QUERY);

        boolean hasPrevCondition = false;

        // lclascd 값이 null이 아닌 경우, 쿼리에 lclascd 조건을 추가합니다.
        if (lclascd != null) {
            query.append(" lclascd = #{lclascd}");
            hasPrevCondition = true;
        }

        // mlsfccd 값이 null이 아닌 경우, 쿼리에 mlsfccd 조건을 추가합니다.
        // 이전 조건이 있다면 AND 구문을 사용하여 추가합니다.
        if (mlsfccd != null) {
            if (hasPrevCondition) {
                query.append(" AND");
            }
            query.append(" mlsfccd = #{mlsfccd}");
        }
        if (sclascd != null) {
            if (hasPrevCondition) {
                query.append(" AND");
            }
            query.append(" sclascd = #{sclascd}");
        }
        if (dclascd != null) {
            if (hasPrevCondition) {
                query.append(" AND");
            }
            query.append(" dclascd = #{dclascd}");
        }

        // 완성된 쿼리 문자열을 반환합니다.
        return query.toString();
    }


    public String findByNameAndCategory(String poi_name, Integer lclascd, Integer mlsfccd, Integer sclascd, Integer dclascd, Integer bclascd) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT pd.* ");
        sql.append("FROM public.poi_data pd ");
        sql.append("JOIN public.poi_category_data pcd ON pd.category_code = pcd.category_code ");

        if (poi_name != null && !poi_name.isEmpty()) {
            sql.append("WHERE pd.poi_name LIKE '%").append(poi_name).append("%' ");

            if (lclascd != null) {
                sql.append("AND pcd.lclascd = ").append(lclascd).append(" ");
            }

            if (mlsfccd != null) {
                sql.append("AND pcd.mlsfccd = ").append(mlsfccd).append(" ");
            }

            if (sclascd != null) {
                sql.append("AND pcd.sclascd = ").append(sclascd).append(" ");
            }
            if (mlsfccd != null) {
                sql.append("AND pcd.dclascd = ").append(dclascd).append(" ");
            }

            if (sclascd != null) {
                sql.append("AND pcd.bclascd = ").append(bclascd).append(" ");
            }
        }

        return sql.toString();
    }


}

package com.annette.cw.utility;

import java.util.Map;

public class Searcher {
    public static int findOrgID(Map<Integer, String> organization,int pos){
        int i = 0;
        for (Map.Entry<Integer, String> map : organization.entrySet()) {
            if (i == pos) return map.getKey();
            i++;
        }
        return 0;
    }
}

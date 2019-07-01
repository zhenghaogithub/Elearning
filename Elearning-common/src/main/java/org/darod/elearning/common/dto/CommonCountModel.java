package org.darod.elearning.common.dto;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.List;

/**
 * @author Darod
 * @version 1.0
 * @date 2019/7/1 0001 16:21
 */
@Data
public class CommonCountModel<T extends List<?>> {
    Long total;
    T dataList;

    public JSONObject toJSONObject(String listName) {
        JSONObject jsonObject = new JSONObject(true);
        jsonObject.put("total", total);
        jsonObject.put(listName, dataList);
        return jsonObject;
    }


    public static void main(String[] args) {
        CommonCountModel<List<Integer>> commonCountModel = new CommonCountModel<>();
    }
}



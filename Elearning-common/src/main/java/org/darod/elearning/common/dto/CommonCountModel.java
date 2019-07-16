package org.darod.elearning.common.dto;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import lombok.Data;
import org.darod.elearning.common.utils.CopyPropertiesUtils;

import java.util.List;
import java.util.function.Function;

/**
 * @author Darod
 * @version 1.0
 * @date 2019/7/1 0001 16:21
 */
@Data
public class CommonCountModel<T extends List<?>> {
    Long total;
    T dataList;

    public CommonCountModel() {
    }

    private CommonCountModel(Long total, T dataList) {
        this.total = total;
        this.dataList = dataList;
    }

    public JSONObject toJSONObject(String listName) {
        JSONObject jsonObject = new JSONObject(true);
        jsonObject.put("total", total);
        jsonObject.put(listName, dataList);
        return jsonObject;
    }
    public static <T> CommonCountModel<List<T>> getCountModel(Long total,List<T> dataList){
        return new CommonCountModel<>(total,dataList);
    }

    public static <T> CommonCountModel<List<T>> getCountModelFromList(Page page, List<?> list, Class<T> clazz){
        return getCountModel(page.getTotal(),CopyPropertiesUtils.mapListObject(list,clazz));
    }
    public static <T,R> CommonCountModel<List<T>> getCountModelFromListWithMapper(Page page, List<R> list, Class<T> clazz, Function<R, T> mapper){
        return getCountModel(page.getTotal(),CopyPropertiesUtils.mapListObjectWithMapper(list, clazz,mapper));
    }


    public static void main(String[] args) {
        CommonCountModel<List<Integer>> commonCountModel = new CommonCountModel<>();
    }
}



package com.rimi.componet;

import com.rimi.model.User;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;

public class BuilderPageComponet {
    /**
     *构建分页数据
     * @param page
     * @param <T>
     * @return
     */
    public static<T> HashMap builder(Page<T> page){
        int totalPages = page.getTotalPages();
        int pageSize=page.getSize();
        int currentPage=page.getNumber();
        List<T> lists=page.getContent();
        HashMap<Object, Object> map = new HashMap<>();
        map.put("totalPages",totalPages);
        map.put("pageSize",pageSize);
        map.put("currentPage",currentPage);
        map.put("lists",lists);
        return map;
    }
}

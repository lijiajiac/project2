package com.yida.project.mapper;

import com.yida.project.entity.Peripheral;
import org.apache.ibatis.annotations.CacheNamespaceRef;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@CacheNamespaceRef(name = "com.yida.project.mapper.PeripheralMapper") //指定自定义 redis 缓存引用
public interface PeripheralMapper extends Mapper<Peripheral> {
    //添加
    public void add(Peripheral p);

    //查一个
    public Peripheral selecton(Integer id);

    //搜索按钮模糊查询        param参数名必须与controller里传递的参数名相同
    public List<Peripheral> selectall(@Param("title") String title, @Param("classify_id") Integer classify_id
            , @Param("begin") String begin, @Param("end") String end);
}

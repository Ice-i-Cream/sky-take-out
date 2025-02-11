package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Category;
import com.sky.entity.Employee;
import com.sky.enumeration.OperationType;
import com.sky.result.PageResult;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CategoryMapper {

    @Insert("insert into category (type, name, sort, status, create_time, update_time, create_user, update_user) " +
            "values " +
            "(#{type}, #{name}, #{sort}, #{status}, #{createTime}, #{updateTime}, #{createUser}, #{updateUser})")
    @AutoFill(value = OperationType.INSERT)
    void save(Category category);

    Page<Category> pageQuery(CategoryPageQueryDTO dto);

    @Delete("delete from category where id = #{id}")
    void deleteById(Long id);

    @AutoFill(value = OperationType.UPDATE)
    void update(Category category);

    List<Category> list(Integer type);
}

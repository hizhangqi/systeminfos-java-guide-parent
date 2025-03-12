package ${package.Mapper};

import ${package.Entity}.${entity};
import ${superMapperClassPackage};
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ${author}
 * @version 1.0
 * @desc ${table.comment!} Mapper
 <#if date != "">
 * @date ${date}
 </#if>
 */
<#if kotlin>
interface ${table.mapperName} : ${superMapperClass}<${entity}>
<#else>
@Repository
public interface ${table.mapperName} extends ${superMapperClass}<${entity}> {

    /**
     * ${table.comment!} 批量插入数据集
     *
     * @param param
     * @return Boolean
     */
    Boolean saveBatch(@Param("paramList") List<${entity}> param);

}
</#if>
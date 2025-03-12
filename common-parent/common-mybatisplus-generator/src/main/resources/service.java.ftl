package ${package.Service};

import ${package.Entity}.${entity};
import ${superServiceClassPackage};

import java.util.List;

/**
 * @author ${author}
 * @version 1.0
 * @desc ${table.comment!} 服务
 <#if date != "">
 * @date ${date}
 </#if>
 */
<#if kotlin>
interface ${table.serviceName} : ${superServiceClass}<${entity}>
<#else>
public interface ${table.serviceName} extends ${superServiceClass}<${entity}> {

    /**
     * ${table.comment!} 批量新增重写
     *
     * @param param model集合
     * @return Boolean
     */
    Boolean saveBatch(List<${entity}> param);

}
</#if>
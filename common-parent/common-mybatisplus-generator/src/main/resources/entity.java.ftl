package ${package.Entity};

<#list table.importPackages as pkg>
import ${pkg};
</#list>
import io.swagger.annotations.ApiModelProperty;
<#if entityLombokModel>
import lombok.Data;
import lombok.EqualsAndHashCode;
    <#if chainModel>
import lombok.experimental.Accessors;
    </#if>
</#if>
import java.util.Date;

/**
 * @author ${author}
 * @version 1.0
 * @desc ${table.comment!}
 <#if date != "">
 * @date ${date}
 </#if>
 */
<#if entityLombokModel>
@Data
<#if superEntityClass??>
@EqualsAndHashCode(callSuper = true)
<#else>
@EqualsAndHashCode(callSuper = false)
</#if>
<#if chainModel>
@Accessors(chain = true)
</#if>
</#if>
<#if table.convert>
@TableName("${table.name}")
</#if>
<#if swagger2??>
@ApiModel(value = "${entity} 实体", description = "${table.comment!}")
</#if>
<#if superEntityClass??>
public class ${entity} extends ${superEntityClass}<#if activeRecord><${entity}></#if> {
<#elseif activeRecord>
public class ${entity} extends Model<${entity}> {
<#else>
public class ${entity} implements Serializable {
</#if>

<#if entitySerialVersionUID>
    private static final long serialVersionUID = 1L;

</#if>
<#-- ----------  BEGIN 字段循环遍历  ---------->
<#list table.fields as field>
<#if field.keyFlag>
    <#assign keyPropertyName="${field.propertyName}"/>
</#if>
<#if field.comment!?length gt 0>
    <#if swagger2??>
    @ApiModelProperty(value = "${field.comment}")
    <#else>
    /**
     * ${field.comment}
     */
    </#if>
</#if>
<#if field.keyFlag>
<#-- 主键 -->
<#if field.keyIdentityFlag>
    @TableId(value = "${field.annotationColumnName}", type = IdType.AUTO)
<#elseif idType??>
    @TableId(value = "${field.annotationColumnName}", type = IdType.${idType})
<#elseif field.convert>
    @TableId("${field.annotationColumnName}")
</#if>
<#-- 普通字段 -->
<#elseif field.fill??>
<#-- -----   存在字段填充设置   ----->
<#if field.convert>
    @TableField(value = "${field.annotationColumnName}", fill = FieldFill.${field.fill})
<#else>
    @TableField(fill = FieldFill.${field.fill})
</#if>
<#elseif field.convert>
    @TableField("${field.annotationColumnName}")
</#if>
<#-- 乐观锁注解 -->
<#if field.versionField>
    @Version
</#if>
<#-- 逻辑删除注解 -->
<#if field.logicDeleteField>
    @TableLogic
</#if>
    @ApiModelProperty("${field.comment}")
    <#if field.propertyType == "LocalDateTime">
    private Date ${field.propertyName};
    <#elseif field.type == "smallint">
    private Short ${field.propertyName};
    <#elseif 1 == 1>
    private ${field.propertyType} ${field.propertyName};
    </#if>
</#list>
<#------------  END 字段循环遍历  ---------->
<#-- Lombok 模式 -->
<#if !entityLombokModel>
<#list table.fields as field>
    <#if field.propertyType == "boolean">
        <#assign getprefix="is"/>
    <#else>
        <#assign getprefix="get"/>
    </#if>
    public ${field.propertyType} ${getprefix}${field.capitalName}() {
        return ${field.propertyName};
    }

<#if chainModel>
    public ${entity} set${field.capitalName}(${field.propertyType} ${field.propertyName}) {
<#else>
    public void set${field.capitalName}(${field.propertyType} ${field.propertyName}) {
</#if>
        this.${field.propertyName} = ${field.propertyName};
        <#if chainModel>
            return this;
        </#if>
    }
</#list>
</#if>
<#-- 列常量 -->
<#if entityColumnConstant>
    <#list table.fields as field>
    public static final String ${field.name?upper_case} = "${field.name}";

    </#list>
</#if>
<#if activeRecord>
    @Override
    protected Serializable pkVal() {
    <#if keyPropertyName??>
        return this.${keyPropertyName};
    <#else>
        return null;
    </#if>
    }

</#if>
<#if !entityLombokModel>
    @Override
    public String toString() {
    return "${entity}{" +
    <#list table.fields as field>
        <#if field_index==0>
            "${field.propertyName}=" + ${field.propertyName} +
        <#else>
            ", ${field.propertyName}=" + ${field.propertyName} +
        </#if>
    </#list>
    "}";
    }
</#if>
}
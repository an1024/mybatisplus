package ${package.Mapper};

import ${package.Entity}.${entity};
import ${superMapperClassPackage};
import java.util.List;

/**
 * <p>
 * ${table.comment!} Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if kotlin>
interface ${table.mapperName} : ${superMapperClass}<${entity}>
<#else>
public interface ${table.mapperName} extends ${superMapperClass}<${entity}> {

    Integer updateByPrimaryKeySelective(${entity} ${table.entityPath});

    Long insertSelective(${entity} ${table.entityPath});

    ${entity} selectByPrimaryKey(Long id);

    Integer selectCountByExample(${entity} ${table.entityPath});

    List<${entity}> selectListByExample(${entity} ${table.entityPath});

}
</#if>

package ${package.Controller};

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ${package.Service}.${table.serviceName};
import ${package.Entity}.${entity};
import com.mybatisplus.utils.MyPage;
import com.mybatisplus.utils.ResponseMsg;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;


import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;

<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>

/**
 * <p>
 * ${table.comment!} 前端控制器
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@RequestMapping("/${table.entityPath}")
@Api(value = "${table.controllerName}", tags = "${table.comment!}")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
    <#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
    <#else>
public class ${table.controllerName} {
    </#if>

    @Autowired
    public ${table.serviceName} ${table.entityPath}Service;

    @ApiOperation(value = "分页查询${entity}信息", notes = "分页条件查询${entity}信息")
    @RequestMapping(value = "/page", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public ResponseMsg page(Page<${entity}> page, ${entity} ${table.entityPath}) {
        QueryWrapper<${entity}> queryWrapper = new QueryWrapper<>();
        IPage<${entity}> data = ${table.entityPath}Service.page(page, queryWrapper);
        return new ResponseMsg(HttpStatus.OK.value(), true, HttpStatus.OK.getReasonPhrase(), new MyPage<>(data));
    }

    @ApiOperation(value = "查询${entity}信息", notes = "条件查询${entity}信息")
    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public ResponseMsg list(${entity} ${table.entityPath}) {
        QueryWrapper<${entity}> queryWrapper = new QueryWrapper<>();
        queryWrapper.setEntity(${table.entityPath});
        List data = ${table.entityPath}Service.list(queryWrapper);
        return new ResponseMsg(HttpStatus.OK.value(), true, HttpStatus.OK.getReasonPhrase(), data);
    }

    @ApiOperation(value = "查询单个${entity}", notes = "根据${entity}号查询单个${entity}信息")
    @RequestMapping(value = "/{${table.entityPath}no}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public ResponseMsg queryOne(@PathVariable("${table.entityPath}no") Integer ${table.entityPath}no) {
        QueryWrapper<${entity}> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("${table.entityPath}no", ${table.entityPath}no);
        ${entity} data = ${table.entityPath}Service.getOne(queryWrapper);
        return new ResponseMsg(HttpStatus.OK.value(), true, HttpStatus.OK.getReasonPhrase(), data);
    }

    @ApiOperation(value = "添加${entity}信息", notes = "新增")
    @RequestMapping(value = "/", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
        public ResponseMsg save(@RequestBody @Valid ${entity} ${table.entityPath}) throws IOException {
        ${table.entityPath}Service.save(${table.entityPath});
        return new ResponseMsg(HttpStatus.OK.value(), true, "添加成功", null);
    }

     @ApiOperation(value = "修改${entity}信息", notes = "修改")
     @RequestMapping(value = "/", method = RequestMethod.PUT, produces = "application/json;charset=UTF-8")
         public ResponseMsg update(@RequestBody @Valid ${entity} ${table.entityPath}) throws IOException {
         ${table.entityPath}Service.updateById(${table.entityPath});
         return new ResponseMsg(HttpStatus.OK.value(), true, "修改成功", null);
     }

     @ApiOperation(value = "删除${entity}信息", notes = "删除(单个条目)")
     @RequestMapping(value = "/{${table.entityPath}no}", method = RequestMethod.DELETE, produces = "application/json;charset=UTF-8")
          public ResponseMsg remove(@PathVariable("${table.entityPath}no") Integer ${table.entityPath}no) {
          ${table.entityPath}Service.removeById(${table.entityPath}no);
          return new ResponseMsg(HttpStatus.OK.value(), true, "删除成功", null);
     }

}
</#if>

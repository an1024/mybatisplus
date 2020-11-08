package com.mybatisplus.generator;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

/**
 * mybatis plus 提供的代码生成器
 * 可以快速生成 Entity、Mapper、Mapper XML、Service、Controller 等各个模块的代码
 */
public class CodeGenerator {
    public static void main(String[] args) throws InterruptedException {
        //用来获取Mybatis-Plus.properties文件的配置信息
        ResourceBundle rb = ResourceBundle.getBundle("mybatiesplus-config"); //不要加后缀

        AutoGenerator generator = new AutoGenerator();
        generator.setTemplateEngine(new FreemarkerTemplateEngine());

        // 全局配置
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setOutputDir(rb.getString("OutputDir"));
        globalConfig.setAuthor(rb.getString("AUTHOR"));
        globalConfig.setOpen(false);
        globalConfig.setFileOverride(true);// 是否覆盖已有文件
        globalConfig.setActiveRecord(true);// 开启activeRecord模式
        globalConfig.setBaseResultMap(true);// XML ResultMap: mapper.xml生成查询映射结果
        globalConfig.setBaseColumnList(true);// XML ColumnList: mapper.xml生成查询结果列
        generator.setGlobalConfig(globalConfig);

        // 数据源配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setUrl(rb.getString("jdbc.url"));
        dataSourceConfig.setDriverName(rb.getString("jdbc.drivername"));
        dataSourceConfig.setUsername(rb.getString("jdbc.username"));
        dataSourceConfig.setPassword(rb.getString("jdbc.password"));
        generator.setDataSource(dataSourceConfig);

        // 包配置
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setModuleName(scanner("模块名"));// 不需要模块的话注释掉就可以了
        packageConfig.setParent(rb.getString("BASE_PACKAGE_URL"));
        generator.setPackageInfo(packageConfig);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(rb.getString("XML_MAPPER_TEMPLATE_PATH")) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return rb.getString("OutputDirXml")+ rb.getString("XML_PACKAGE_URL") + tableInfo.getEntityName() + "Mapper.xml";
            }
        });
        cfg.setFileOutConfigList(focList);
        generator.setCfg(cfg);

        // 配置自定义代码模板
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setXml(null); // 关闭默认 xml 生成，调整生成至根目录
        templateConfig.setMapper(rb.getString("MAPPER_TEMPLATE_PATH"));
        templateConfig.setEntity(rb.getString("ENTITY_TEMPLATE_PATH"));
        templateConfig.setService(rb.getString("SERVICE_TEMPLATE_PATH"));
        templateConfig.setServiceImpl(rb.getString("SERVICE_IMPL_TEMPLATE_PATH"));
        templateConfig.setController(rb.getString("CONTROLLER_TEMPLATE_PATH"));
        generator.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);// 数据库表映射到实体的命名策略: 下划线转驼峰命名
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);// 数据库表字段映射到实体的命名策略: 下划线转驼峰命名
        strategy.setEntityLombokModel(true);// 【实体】是否为lombok模型（默认 false）
        strategy.setRestControllerStyle(true);
        strategy.setSuperEntityColumns("id");
        strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));
        strategy.setControllerMappingHyphenStyle(true);// 驼峰转连字符
        strategy.setTablePrefix(packageConfig.getModuleName() + "_");
        generator.setStrategy(strategy);
        generator.setTemplateEngine(new FreemarkerTemplateEngine());
        generator.execute();

    }

    private static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(("请输入" + tip + "："));
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) return ipt;
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }
}
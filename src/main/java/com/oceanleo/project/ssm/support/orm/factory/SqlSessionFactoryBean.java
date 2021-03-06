package com.oceanleo.project.ssm.support.orm.factory;

import com.oceanleo.project.ssm.support.utils.DateUtils;
import com.oceanleo.project.ssm.support.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author haiyang.li on 2017/9/21.
 */
public class SqlSessionFactoryBean extends org.mybatis.spring.SqlSessionFactoryBean {

    private static final String DEFAULT_RESOURCE_PATTERN = "**/*.class";

    private Logger logger = LoggerFactory.getLogger(SqlSessionFactoryBean.class);

    /**
     * mybatis类别名映射 支符持通配
     */
    @Override
    public void setTypeAliasesPackage(String typeAliasesPackage) {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resolver);
        //typeAliasesPackage = classpath*: + typeAliasesPackage + /**/*.class"
        typeAliasesPackage = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + ClassUtils.convertClassNameToResourcePath(typeAliasesPackage) + "/" + DEFAULT_RESOURCE_PATTERN;
        try {
            //包下面所有的类的全限定名
            List<String> result = new ArrayList<String>();
            //获取所有的资源
            Resource[] resources = resolver.getResources(typeAliasesPackage);
            if (resources != null && resources.length > 0) {
                MetadataReader metadataReader;
                for (Resource resource : resources) {
                    if (resource.isReadable()) {
                        metadataReader = metadataReaderFactory.getMetadataReader(resource);
                        try {
                            //获取所有类下面的包名
                            String packageName = Class.forName(metadataReader.getClassMetadata().getClassName()).getPackage().getName();
                            //去掉重复的
                            if (!result.contains(packageName)) {
                                result.add(packageName);
                            }
                        } catch (ClassNotFoundException e) {
                            logger.error("mybatis typeAliasesPackage:" + typeAliasesPackage + ",class not fund");
                            System.out.println(DateUtils.formatCurrentTime()+" mybatis 类的别名映射错误,别名: "+typeAliasesPackage+" 错误: "+e.getMessage());
                        }
                    }
                }
            }
            if (result.size() > 0) {
                String packageString = StringUtils.join(result, StringUtils.COMMA);
                System.out.println(DateUtils.formatCurrentTime()+" mybatis 类的别名映射,包名: "+packageString);
                super.setTypeAliasesPackage(packageString);
            } else {
                System.out.println(DateUtils.formatCurrentTime()+" mybatis 类的别名映射,包名不存在");
                logger.error("mybatis typeAliasesPackage:" + typeAliasesPackage + ",package not fund");
            }
        } catch (IOException e) {
            System.out.println(DateUtils.formatCurrentTime()+" mybatis 类的别名映射错误,别名: "+typeAliasesPackage+" 错误: "+e.getMessage());
            logger.error("mybatis set typeAliasesPackage error,typeAliasesPackage:" + typeAliasesPackage, e);
        }
    }
}

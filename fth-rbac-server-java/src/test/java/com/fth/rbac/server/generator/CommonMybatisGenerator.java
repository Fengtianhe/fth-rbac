package com.fth.rbac.server.generator;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.api.ShellCallback;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.JDBCConnectionConfiguration;
import org.mybatis.generator.config.JavaClientGeneratorConfiguration;
import org.mybatis.generator.config.JavaModelGeneratorConfiguration;
import org.mybatis.generator.config.ModelType;
import org.mybatis.generator.config.SqlMapGeneratorConfiguration;
import org.mybatis.generator.config.TableConfiguration;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author 冯天鹤
 * @version $Id: CommonMybatisGenerator.java, v 0.1 2018年10月17日 下午1:52:11 19391 Exp $
 */
@PropertySource(value = {"classpath:application.properties"})
public class CommonMybatisGenerator {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(CommonMybatisGenerator.class);

    private static final String DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";

    public static void main(String[] args) throws IOException {
        Properties config = getConfig();

        //本地mysql的jar包路径
        String jdbcJarPath = config.getProperty("mybatis.generator.jdbc-jar-path");

        String dbUrl = config.getProperty("mybatis.generator.db-url");

        String dbName = config.getProperty("mybatis.generator.db-name");
        String user = config.getProperty("mybatis.generator.db-username");
        String pass = config.getProperty("mybatis.generator.db-password");
        //项目所在目录
        String projectSrc = config.getProperty("mybatis.generator.project-src");
        String parentProjectName = "fth-rbac-server-java";
        // 如果有子module就设置
        String projectName = "";
        String packageName = "com.fth.rbac.server.core";
        // 是否生成mapper对应的interface类 如果不是第一次生成，需要改为false 否则里面方法会被覆盖
        boolean createInterfaces = false;

        // 需要生成的表
        String[] tableNames = {
//                "fr_app_application",
//                "fr_app_env",
//                "ft_app_log",
//                "fr_app_resource",
                "fr_app_role",
//                "fr_app_role_resource",
//                "fr_sys_user",
        };

        generate(dbUrl + dbName, dbName, user, pass, projectSrc, parentProjectName, projectName,
                packageName, createInterfaces, jdbcJarPath, tableNames);
    }

    public static void generate(String dbUrl, String dbName, String user, String pass,
                                String projectSrc, String parentProjectName, String projectName,
                                String packageName, boolean createInterfaces, String jdbcJarPath,
                                String... tableNames) {
        long start = System.currentTimeMillis();
        Configuration configuration = new Configuration();

        for (String tbName : tableNames) {
            String seperator = "\\";
            if (System.getProperties().getProperty("os.name").toLowerCase().contains("mac")) {
                seperator = "/";
            }
            String base = projectSrc + seperator + parentProjectName + seperator + projectName;
            String suffix = seperator + "src" + seperator + "main" + seperator + "java";

            configuration.addContext(getContext(configuration, dbUrl, dbName, user, pass, tbName,
                    createInterfaces, jdbcJarPath, base + suffix, packageName));
        }

        generate(configuration);
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("generate success! excute time is {} millis!",
                    System.currentTimeMillis() - start);
        }
    }

    private static void generate(Configuration configuration) {
        ShellCallback shellCallback = new DefaultShellCallback(true);
        List<String> warnings = new ArrayList<String>();

        try {
            MyBatisGenerator generator = new MyBatisGenerator(configuration, shellCallback,
                    warnings);
            generator.generate(null);
        } catch (InvalidConfigurationException | SQLException | IOException
                | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * java model configuration
     *
     * @param targetModelProject
     * @param
     * @return
     */
    private static JavaModelGeneratorConfiguration getJavaModelGeneratorConfiguration(String targetModelProject,
                                                                                      String packageName) {
        JavaModelGeneratorConfiguration javaModelGeneratorConfiguration = new JavaModelGeneratorConfiguration();
        javaModelGeneratorConfiguration.setTargetProject(targetModelProject);
        javaModelGeneratorConfiguration.getProperties().setProperty("constructorBased", "true");
        javaModelGeneratorConfiguration.setTargetPackage(packageName);
        return javaModelGeneratorConfiguration;
    }

    private static SqlMapGeneratorConfiguration getSqlMapGeneratorConfiguration(String targetDalProject,
                                                                                String packageName) {
        SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration = new SqlMapGeneratorConfiguration();
        sqlMapGeneratorConfiguration.setTargetProject(targetDalProject);
        sqlMapGeneratorConfiguration.setTargetPackage(packageName);
        return sqlMapGeneratorConfiguration;
    }

    /**
     * 生成mybatis dao接口
     *
     * @param targetDalProject
     * @param
     * @param createIntefaces  是否生成接口,if true,新生成的接口不包括后来手动添加的接口方法
     * @return
     */
    private static JavaClientGeneratorConfiguration getJavaClientGeneratorConfiguration(String targetDalProject,
                                                                                        String packageName,
                                                                                        boolean createIntefaces) {
        if (!createIntefaces) {
            return null;
        }

        JavaClientGeneratorConfiguration javaClientGeneratorConfiguration = new JavaClientGeneratorConfiguration();
        javaClientGeneratorConfiguration.setConfigurationType("XMLMAPPER");
        javaClientGeneratorConfiguration.setTargetProject(targetDalProject);
        javaClientGeneratorConfiguration.setTargetPackage(packageName);
        return javaClientGeneratorConfiguration;
    }

    private static TableConfiguration getTableConfiguration(String dbName, String tbName,
                                                            Context context) {
        TableConfiguration tc = new TableConfiguration(context);
        tc.setSchema(dbName);
        tc.setTableName(tbName);
        tc.setCountByExampleStatementEnabled(true);
        tc.setUpdateByExampleStatementEnabled(true);
        tc.setDeleteByExampleStatementEnabled(true);
        tc.setSelectByExampleStatementEnabled(true);
        tc.setSelectByExampleQueryId("true");
        tc.setWildcardEscapingEnabled(false);
        Properties properties = tc.getProperties();
        properties.setProperty("useActualColumnNames", "false");
        properties.setProperty("ignoreQualifiersAtRuntime", "true");
        return tc;
    }

    private static JDBCConnectionConfiguration getJDBCConnectionConfiguration(String url,
                                                                              String user,
                                                                              String pass) {
        JDBCConnectionConfiguration jdbcConnectionConfiguration = new JDBCConnectionConfiguration();
        jdbcConnectionConfiguration.setDriverClass(DRIVER_CLASS);
        jdbcConnectionConfiguration.setConnectionURL(url);
        jdbcConnectionConfiguration.setUserId(user);
        jdbcConnectionConfiguration.setPassword(pass);
        return jdbcConnectionConfiguration;
    }

    /**
     * @param configuration
     * @param tbName
     * @param createInterfaces
     * @param jdbcJarPath
     * @param storePath
     * @return
     */
    private static Context getContext(Configuration configuration, String url, String dbName,
                                      String name, String pass, String tbName,
                                      boolean createInterfaces, String jdbcJarPath,
                                      String storePath, String packageName) {
        Context context = new Context(ModelType.FLAT);
        context.setId("jfox");
        context.setTargetRuntime("MyBatis3");

        configuration.addClasspathEntry(jdbcJarPath);
        context.setJdbcConnectionConfiguration(getJDBCConnectionConfiguration(url, name, pass));

        context.setJavaModelGeneratorConfiguration(
                getJavaModelGeneratorConfiguration(storePath, packageName + ".entity"));

        context.setSqlMapGeneratorConfiguration(
                getSqlMapGeneratorConfiguration(storePath, packageName + ".mapper"));

        context.setJavaClientGeneratorConfiguration(getJavaClientGeneratorConfiguration(storePath,
                packageName + ".mapper", createInterfaces));

        context.addTableConfiguration(getTableConfiguration(dbName, tbName, context));
        return context;
    }

    private static Properties getConfig() throws IOException {
        Properties properties = new Properties();
        ClassPathResource classPathResource = new ClassPathResource("mybatis-generator.properties");   //这里的填写的参数是配置文件的相对路径
        properties.load(new InputStreamReader(classPathResource.getInputStream(), StandardCharsets.UTF_8));     //文件流的编码方式

        return properties;
    }
}

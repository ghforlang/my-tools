package fan.nbu.edu.cn.generator;

import com.google.common.collect.Maps;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import fan.nbu.edu.cn.config.DBProperties;
import fan.nbu.edu.cn.constants.TableType;
import fan.nbu.edu.cn.db.DBTableHandler;
import fan.nbu.edu.cn.db.metadata.ColumnMetaDataInfo;
import fan.nbu.edu.cn.db.metadata.TableMetaDataInfo;
import fan.nbu.edu.cn.db.parser.TableNameParser;
import fan.nbu.edu.cn.exception.IllegalNameException;
import fan.nbu.edu.cn.registry.TypeRegistry;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.PostConstruct;
import javax.lang.model.element.Modifier;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author laoshi . hua
 * @version 1.0 2022/3/18-2:38 下午
 * @since 1.0
 */


@Slf4j
public class JavaCodeGenerator{

    
    private DBTableHandler dbTableHandler;
    
    private DBProperties dbProperties = DBProperties.getInstance();

    private static final Map<String,String> tableName2ClassNameMap = new HashMap<>();
    private static final Map<String,Map<String,POJOFieldMetaDataInfo>> tableColumn2POJOFieldMap = new HashMap<>();
    private static final Map<String,Map<String,String>> tableColumnFieldMap = new HashMap<>();
    private static final String sqlFieldFileSuffix = "_sqlField.txt";
    private static final String CHAR_SET_UTF = "UTF-8";

    @PostConstruct
    public void initMethod(){
        // init tableName2ClassNameMap
        Set<String> allTableNames = dbTableHandler.allTableNames();
        for (String tableName : allTableNames) {
            tableName2ClassNameMap.put(tableName, TableNameParser.tableName2POJOClassName(tableName));
        }
        // init tableColumnNameMap
        Map<String, TableMetaDataInfo> tableMetaDataInfoMap = dbTableHandler.tableMetaDataInfoMap();
        tableMetaDataInfoMap.forEach((tableName,metaInfo) ->{
            tableColumn2POJOFieldMap.putIfAbsent(tableName, Maps.newHashMap());
            if(CollectionUtils.isNotEmpty(metaInfo.getColumnMetaDatas())){
                Map<String,POJOFieldMetaDataInfo> columnNameMap = tableColumn2POJOFieldMap.get(tableName);
                metaInfo.getColumnMetaDatas().forEach(columnMetaDataInfo -> {
                    columnNameMap.put(columnMetaDataInfo.getColumnName(),buildFiledMetaData(columnMetaDataInfo));
                });
            }
        });

        //初始化tableColumnFieldMap
        Set<String> targetTableNames = new HashSet<>(dbProperties.getTarSrcTableNamePairs().keySet());
        String filePathContext = Thread.currentThread().getContextClassLoader().getClass().getResource("/").getPath();
        for(String tarTableName : targetTableNames){
            File tempSqlFile = new File(filePathContext + tarTableName + sqlFieldFileSuffix);
            try {
                String tempSqlFieldContent = FileUtils.readFileToString(tempSqlFile,CHAR_SET_UTF);
                String tempSubSqlFields[] = StringUtils.split(tempSqlFieldContent,"|");
                if(tempSubSqlFields.length != 2){
                    System.out.println("invalid sqlFieldContent ,tempSqlFieldContent=" + tempSqlFieldContent);
                    continue;
                }
                String[] tarColumnNames = StringUtils.split(tempSubSqlFields[0],",");
                String[] tarColumnValues = StringUtils.split(tempSubSqlFields[1],",");
                tableColumnFieldMap.putIfAbsent(tarTableName,new HashMap<>());
                Map<String,String> tempMap = tableColumnFieldMap.get(tarTableName);
                for(int i=0;i<tarColumnNames.length;i++){
                    String tempTarColumnName = StringUtils.remove(StringUtils.remove(tarColumnNames[i],"\n")," ");
                    String tempTarColumnValue = StringUtils.remove(StringUtils.remove(tarColumnValues[i],"\n")," ");
                    tempMap.put(tempTarColumnName,tempTarColumnValue);
                }
            } catch (IOException e) {
                System.out.println("file " + filePathContext + tarTableName + sqlFieldFileSuffix + "not exists!");
                continue;
            }
        }

    }

    @SneakyThrows
    private POJOFieldMetaDataInfo buildFiledMetaData(ColumnMetaDataInfo columnData){
        POJOFieldMetaDataInfo fieldMetaDataInfo = new POJOFieldMetaDataInfo();
        fieldMetaDataInfo.setFieldName(TableNameParser.columnName2FieldName(columnData.getColumnName()));
//        log.info("tableName: " + columnData.getTableName() + ",columnName: " + columnData.getColumnName() + ",jdbcType: " + columnData.getColumnJdbcType());
        fieldMetaDataInfo.setFieldType(TypeRegistry.getFieldTypeWrapper(columnData.getColumnJdbcType()).getType());
        fieldMetaDataInfo.setComments(columnData.getComments());
        return fieldMetaDataInfo;
    }

    public String generator(String s) {
        tableName2ClassNameMap.keySet().forEach(tableName ->{
            generateCode(tableName);
        });
        return s;
    }

    private void generateCode(String tableName){
        if(StringUtils.isBlank(tableName)){
            throw new IllegalNameException("illegal tableName when generate CODE!");
        }

        TypeSpec typeSpec = generateClassModel(tableName);
        String javaFilePackage = dbProperties.getPojoDir();
        String contextPath = Thread.currentThread().getContextClassLoader().getClass().getResource("/").getPath();
        JavaFile file = JavaFile.builder(javaFilePackage,typeSpec).build();
        File abstractClazzFile = new File(contextPath);
        try {
            file.writeTo(abstractClazzFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private TypeSpec generateClassModel(String tableName){
        if(StringUtils.isBlank(tableName)){
            return null;
        }
        List<FieldSpec> fieldSpecList = new ArrayList<>();
        TableMetaDataInfo tableMetaDataInfo = dbTableHandler.tableMetaDataInfoMap().get(tableName);
        Map<String,String> tarColumnMap = tableColumnFieldMap.get(tableMetaDataInfo.getTableName());
        tableMetaDataInfo.getColumnMetaDatas().forEach(columnMetaDataInfo -> {
            fieldSpecList.add(generateField(tableMetaDataInfo.getTableType(),tarColumnMap,columnMetaDataInfo));
        });

//        AnnotationSpec annotationSpec = AnnotationSpec.builder(Transfer.class)
//                .addMember("sourceType","$T.$L", TableType.class,tableMetaDataInfo.getTableType())
//                .addMember("tableName","$S",tableMetaDataInfo.getTableName())
//                .build();

        TypeSpec typeSpec = TypeSpec.classBuilder(tableName2ClassNameMap.get(tableName))
//                .addAnnotation(annotationSpec)
                .addAnnotation(Getter.class)
                .addAnnotation(Setter.class)
                .addFields(fieldSpecList)
                .addModifiers(Modifier.PUBLIC)
                .build();
        return typeSpec;
    }

    private FieldSpec generateField(TableType tableType,Map<String,String> columnFieldMap,ColumnMetaDataInfo columnMetaData){
        Map<String,POJOFieldMetaDataInfo> fieldMetaDataInfoMap = tableColumn2POJOFieldMap.get(columnMetaData.getTableName());
        POJOFieldMetaDataInfo fieldMetaDataInfo = fieldMetaDataInfoMap.get(columnMetaData.getColumnName());
        AnnotationSpec annotationSpec = null;
        if(TableType.TARGET.equals(tableType) && MapUtils.isNotEmpty(columnFieldMap)){
//            annotationSpec = AnnotationSpec.builder(TransferColumn.class)
//                    .addMember("tarColumnName","$S", columnMetaData.getColumnName())
//                    .addMember("tarColumnValue","$S", Objects.isNull(columnFieldMap.get(columnMetaData.getColumnName())) ? "" : columnFieldMap.get(columnMetaData.getColumnName()))
//                    .build();
        }else{
//            annotationSpec = AnnotationSpec.builder(TransferColumn.class).build();
        }

        FieldSpec fieldSpec = FieldSpec.builder(fieldMetaDataInfo.getFieldType(),fieldMetaDataInfo.getFieldName())
                .addAnnotation(annotationSpec)
                .addJavadoc(fieldMetaDataInfo.getComments())
                .addModifiers(Modifier.PRIVATE)
                .build();
        return fieldSpec;
    }

    @Getter
    @Setter
    private static class POJOFieldMetaDataInfo{
        private String fieldName;
        private Class<?> fieldType;
        private String comments;
    }
}

//package fan.nbu.edu.cn.generator;
//
//import fan.nbu.edu.cn.config.DBProperties;
//import fan.nbu.edu.cn.constants.TableType;
//import fan.nbu.edu.cn.db.DBTableHandler;
//import fan.nbu.edu.cn.db.parser.TableNameParser;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.collections.CollectionUtils;
//import org.apache.commons.io.FileUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.commons.lang3.tuple.ImmutablePair;
//
//import java.io.File;
//import java.io.IOException;
//import java.lang.reflect.Field;
//import java.util.List;
//import java.util.Objects;
//import java.util.Set;
//
///**
// * @author laoshi . hua
// * @version 1.0 2022/4/11-4:32 PM
// * @since 1.0
// */
//
//@Slf4j
//public class SqlScriptGenerator {
//
//    private DBTableHandler dbTableHandler;
//
//    private DBProperties dbProperties;
//
//    private static final String transferSqlPrefix = "INSERT INTO ";
//    private static final String tabString = "    ";
//    private static final String blankStr = " ";
//    private static final String leftBracket = "(";
//    private static final String rightBracket = ")";
//    private static final String comma = ",";
//    private static final String semicolon = ";";
//    private static final String period = ".";
//    private static final String from = " FROM ";
//    private static final String sql_suffix_name = "_transfer.sql";
//
//
//    public void generate(String message){
//        Set<String> targetTableNames = dbProperties.getTarSrcTableNamePairs().keySet();
//        for (String targetTableName : targetTableNames) {
//            generateTableSqlScript(targetTableName);
//        }
//        System.out.println("generate sqlScript success!");
//    }
//
//    private  void generateTableSqlScript(String tarTableName){
//        if(StringUtils.isBlank(tarTableName) || CollectionUtils.isEmpty(dbProperties.getTarSrcTableNamePairs().get(tarTableName))){
//            System.out.println("target table name can not be null or source tables not configurated!");
//            return;
//        }
//
//        List<ImmutablePair<String,String>> tarSrcPairs = dbProperties.getTarSrcTableNamePairs().get(tarTableName);
//        // 先处理源表为单张表的sql
//        if(tarSrcPairs.size() == 1){
//            String sourceTableName = tarSrcPairs.get(0).getRight();
//            try {
//                Class<?> pojoModelClass = Thread.currentThread().getContextClassLoader().loadClass(dbProperties.getPojoDir()+ period + TableNameParser.tableName2POJOClassName(tarTableName));
//                generateTransferSql(sourceTableName,pojoModelClass);
//            } catch (ClassNotFoundException e) {
//                System.out.println(e.getMessage());
//                e.printStackTrace();
//            }
//        }else if(tarSrcPairs.size() > 1) {
//            System.out.println("mutil srcTables is not support now ,table[" + tarTableName + "]");
//            //TODO
//        }
//    }
//
//
////    public void generateTransferSql(String srcTableName,Class<?> targetModelClass){
////        Transfer transferAnno = targetModelClass.getAnnotation(Transfer.class);
////        if(Objects.isNull(transferAnno)){
////            System.out.println("invalid class: [" + targetModelClass.getName() + "]");
////            return;
////        }
////
////        if(!TableType.TARGET.equals(transferAnno.sourceType())){
////            System.out.println("invalid class: [" + targetModelClass.getName() + "],not a target table model");
////            return;
////        }
////
////        StringBuffer resultSqlContent = new StringBuffer();
////        StringBuffer srcTableFieldSqlSb = new StringBuffer(" SELECT ");
////        StringBuffer tarTableFieldSqlSb = new StringBuffer();
////
////        resultSqlContent.append(transferSqlPrefix)
////                .append(transferAnno.tableName())
////                .append(blankStr)
////                .append(leftBracket);
////        Field[] columnFields = targetModelClass.getDeclaredFields();
////        for (Field field : columnFields) {
////            TransferColumn transferColumnAnno = field.getAnnotation(TransferColumn.class);
////            if(Objects.nonNull(transferColumnAnno)){
////                String tarColumnName = transferColumnAnno.tarColumnName();
////                String tarColumnValue = transferColumnAnno.tarColumnValue();
////                if(StringUtils.isNotBlank(tarColumnValue)){
////                    tarTableFieldSqlSb.append(tarColumnName).append(comma);
////                    srcTableFieldSqlSb.append(tarColumnValue).append(comma);
////                }
////            }
////        }
////        String tarTableFieldSql = StringUtils.removeEnd(tarTableFieldSqlSb.toString(),comma);
////        String srcTableFieldSql = StringUtils.removeEnd(srcTableFieldSqlSb.toString(),comma);
////        resultSqlContent.append(tarTableFieldSql)
////                .append(rightBracket)
////                .append(srcTableFieldSql)
////                .append(from)
////                .append(srcTableName)
////                .append(semicolon);
////        writeSql2File(transferAnno.tableName(),resultSqlContent.toString());
////    }
//
//
//    public void writeSql2File(String tarTableName,String transferSql){
//        if(StringUtils.isBlank(tarTableName) || StringUtils.isBlank(transferSql)){
//            System.out.println("invalid fileName and transferSql content!");
//            return ;
//        }
//
//        String filePath = Thread.currentThread().getContextClassLoader().getResource("").getPath() + tarTableName + sql_suffix_name;
//        try {
//            FileUtils.writeStringToFile(new File(filePath),transferSql,"UTF-8");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}

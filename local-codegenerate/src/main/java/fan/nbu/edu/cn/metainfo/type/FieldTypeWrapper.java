package fan.nbu.edu.cn.metainfo.type;


import fan.nbu.edu.cn.constants.TypeEnum;

/**
 * @author laoshi . hua
 * @version 1.0 2022/3/22-5:31 下午
 * @since 1.0
 */
public class FieldTypeWrapper implements TypeWrapper<Class<?>> {

    private Class<?> fieldType;

    public FieldTypeWrapper(Class<?> fieldType) {
        this.fieldType = fieldType;
    }

    @Override
    public Class<?> getType() {
        return fieldType;
    }

    @Override
    public TypeEnum getTypeCategory() {
        return TypeEnum.FIELD;
    }

    @Override
    public String getTypeName() {
        return fieldType.getSimpleName();
    }


    public static FieldTypeWrapper ofFieldTypeWrapper(Class<?> field){
        return new FieldTypeWrapper(field);
    }
}

package pers.hyu.tyche.enums;

public enum BGMOperatorTypeEnum {
    ADD("1","Add BGM"),
    DELETE("-1", "Delete BGM");

    private final String code;
    private final String operation;

    BGMOperatorTypeEnum(String code, String operation) {
        this.code = code;
        this.operation = operation;
    }

    public String getCode() {
        return code;
    }

    public String getOperation() {
        return operation;
    }

//    public static String getOperationByCode(String code){
//        for (operation: BGMOperatorTypeEnum.values()
//             ) {
//            if(operation.getCode().equals(code)){
//                return operation.getOperation();
//            }
//        }
//        return null;
//    }
}

package com.you.common.excel.converters;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.you.common.core.enums.StatusEnum;

public class StatusConverter implements Converter<String> {

    @Override
    public Class<String> supportJavaTypeKey() {
        return String.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.NUMBER;
    }

    @Override
    public WriteCellData<String> convertToExcelData(String data, ExcelContentProperty contentProperty,
                                                    GlobalConfiguration globalConfiguration) {
        return new WriteCellData<>(StatusEnum.getInfo(data));
    }
}

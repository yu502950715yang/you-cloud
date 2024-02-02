package com.you.common.core.excel.converters;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.you.common.core.excel.annotation.ExcelDictItem;

import java.lang.reflect.Field;

public class ExcelDictConverter implements Converter<String> {

    @Override
    public WriteCellData<?> convertToExcelData(String value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        // 获取字典类型
        Field field = contentProperty.getField();
        ExcelDictItem excel = field.getAnnotation(ExcelDictItem.class);
        String dictType = excel.type();

        if (value == null || value.isEmpty()) {
            return new WriteCellData<>("");
        }

        return Converter.super.convertToExcelData(value, contentProperty, globalConfiguration);
    }
}

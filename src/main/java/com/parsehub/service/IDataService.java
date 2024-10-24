package com.parsehub.service;

import com.parsehub.util.ConversionType;

public interface IDataService
{
    public String convertData(String json, ConversionType type);
}

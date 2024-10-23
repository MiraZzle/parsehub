package com.parsehub.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.parsehub.util.ConversionType;

import java.io.IOException;

public interface IDataService
{
    public String convertData(String json, ConversionType type);
}

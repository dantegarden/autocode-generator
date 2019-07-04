package com.letinvr.autocode.service;

import com.letinvr.autocode.bean.PageUtils;
import com.letinvr.autocode.bean.Query;

import java.util.List;
import java.util.Map;

public interface SysGeneratorService {

    public PageUtils queryList(Query query);

    public Map<String, String> queryTable(String tableName);

    public List<Map<String, String>> queryColumns(String tableName);

    public byte[] generatorCode(String[] tableNames);
}

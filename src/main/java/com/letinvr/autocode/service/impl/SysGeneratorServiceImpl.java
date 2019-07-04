package com.letinvr.autocode.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.letinvr.autocode.bean.PageUtils;
import com.letinvr.autocode.bean.Query;
import com.letinvr.autocode.dao.MySQLGeneratorDao;
import com.letinvr.autocode.service.SysGeneratorService;
import com.letinvr.autocode.utils.GenUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

@Service
public class SysGeneratorServiceImpl implements SysGeneratorService {

    @Autowired
    private MySQLGeneratorDao generatorDao;


    @Override
    public PageUtils queryList(Query query) {
        Page<?> page = PageHelper.startPage(query.getPage(), query.getLimit());
        List<Map<String, Object>> list = generatorDao.queryList(query);
        return new PageUtils(list, (int)page.getTotal(), query.getLimit(), query.getPage());
    }

    @Override
    public Map<String, String> queryTable(String tableName) {
        return generatorDao.queryTable(tableName);
    }

    @Override
    public List<Map<String, String>> queryColumns(String tableName) {
        return generatorDao.queryColumns(tableName);
    }

    @Override
    public byte[] generatorCode(String[] tableNames) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);

        for(String tableName : tableNames){
            //查询表信息
            Map<String, String> table = queryTable(tableName);
            //查询列信息
            List<Map<String, String>> columns = queryColumns(tableName);
            //生成代码
            GenUtils.generatorCode(table, columns, zip);
        }
        IOUtils.closeQuietly(zip);
        return outputStream.toByteArray();
    }
}

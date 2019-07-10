package com.jiuye.baseframex.util;

import android.content.Context;
import android.content.res.AssetManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : GuoQiang
 * e-mail : 849199845@qq.com
 * time   : 2019/06/01  10:01
 * desc   : 读取Json文件的工具类
 * version: 1.0
 */
public class JsonDataUnit {
    /**
     *IO流读取assets目录下的json文件
     * @param context
     * @param fileName
     * @return
     */
    public static String getJson(Context context, String fileName){
        StringBuilder stringBuilder=new StringBuilder();
        try {
            AssetManager assetManager=context.getAssets();
            InputStreamReader streamReader=new InputStreamReader(assetManager.open(fileName));
            BufferedReader bf=new BufferedReader(streamReader);
            String line;
            while ((line=bf.readLine())!=null){
                stringBuilder.append(line);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    /**
     * 方法1:
     * @param result
     * @param cls
     * @param <T>
     * @return
     */
    public static  <T> ArrayList<T> parseData(String result,Class<T> cls) {//Gson 解析
        ArrayList<T> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                T entity = gson.fromJson(data.optJSONObject(i).toString(), cls);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail;
    }


    /**
     * 方法2：使用方法:List<Bean> list = analysisJson("car_code.json");
     * 暂时无法使用 报错
     * @param context
     * @param fileName
     * @return
     */
    public static <T> ArrayList<T> parseJsonData(Context context,String fileName) {
        //得到本地json文本内容
        //String fileNames = "car_code.json";
        String myJson=getJson(context,fileName);
        // json为单个对象时
        //return new Gson().fromJson(myJson,Bean.class);
        //json转换为集合(对象数组)
        Type typeList= new TypeToken<List<?>>(){}.getType();
        return new Gson().fromJson(myJson,typeList );
    }

}

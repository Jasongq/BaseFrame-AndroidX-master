package com.jiuye.baseframex.http;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author : GuoQiang
 * e-mail : 849199845@qq.com
 * time   : 2019/07/10  13:59
 * desc   :  retrofit api 接口类
 *  * 请求方法注解
 *  *
 *  * @GET get请求
 *  * @POST post请求
 *  * @PUT put请求
 *  * @DELETE delete请求
 *  * @PATCH patch请求，该请求是对put请求的补充，用于更新局部资源
 *  * @HEAD head请求
 *  * @OPTIONS option请求
 *  * @HTTP 通用注解, 可以替换以上所有的注解，其拥有三个属性：method，path，hasBody
 *  * 请求头注解
 *  * @Headers 用于添加固定请求头，可以同时添加多个。通过该注解添加的请求头不会相互覆盖，而是共同存在
 *  * @Header 作为方法的参数传入，用于添加不固定值的Header，该注解会更新已有的请求头
 *  * 标记注解
 *  * @FormUrlEncoded 表示请求发送编码表单数据，每个键值对需要使用@Field注解
 *  * 用于修饰Fiedl注解 和FileldMap注解
 *  * 使用该注解，表示请求正文将使用表单网址编码。字段应该声明为参数，并用@Field 注解和 @FieldMap 注解，
 *  * 使用@FormUrlEncoded 注解的请求将具有"application/x-www-form-urlencoded" MIME类型。
 *  * 字段名称和值将先进行UTF-8进行编码，再根据RFC-3986进行URI编码。
 *  * @Multipart 作用于方法
 *  * 表示请求发送multipart数据，使用该注解，表示请求体是多部分的，每个部分作为一个参数，且用Part注解声明。
 *  * @Streaming 作用于方法
 *  * 未使用@Straming 注解，默认会把数据全部载入内存，之后通过流获取数据也是读取内存中数据，所以返回数据较大时，需要使用该注解。
 *  * 处理返回Response的方法的响应体，用于下载大文件
 *  * 提醒:如果是下载大文件必须加上@Streaming 否则会报OOM
 *  * @Streaming
 *  * @GET Call<ResponseBody> downloadFileWithDynamicUrlAsync(@Url String fileUrl);
 *  * <p>
 *  * 参数注解
 *  * 参数注解：@Query 、@QueryMap、@Body、@Field、@FieldMap、@Part、@PartMap
 *  * 其它注解
 *  * @Path、@Url
 * version: 1.0
 */
public interface ApiService {
/**
 * 注意事项：两种写法都可以
 * 1、全局配置并且唯一baseUrl的写法如下
 * RxHttpUtils.createApi(XXXApi.class) 等同于 ApiFactory.getInstance().createApi(XXXApi.class)
 * 2、多个baseUrl写法如下
 * RxHttpUtils.createApi("xxxUrlKey", "xxxUrlValue", XXXApi.class) 等同于 ApiFactory.getInstance().createApi("xxxUrlKey", "xxxUrlValue", XXXApi.class)
 * 3、多baseUrl的情况下可以设置不同retrofit配置,写法如下
 * ApiFactory.getInstance(...).setConverterFactory(...).setCallAdapterFactory(...).setOkClient(...).createApi(...)
 */
//    @GET("/article/list/{page}/json")
//    Observable<BaseResp<HomeArticleBean>> getArticleData(@Path("page") int num);

    @POST("user/login")
    Observable<String> login(@Query("username") String username, @Query("password") String password);

}

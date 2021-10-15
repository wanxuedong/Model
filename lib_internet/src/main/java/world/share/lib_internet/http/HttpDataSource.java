package world.share.lib_internet.http;


import io.reactivex.Observable;
import world.share.lib_internet.bean.BaseResponse;

/**
 * @author mac
 */
public interface HttpDataSource {

    /**
     * 模拟登录
     * **/
    Observable<BaseResponse<String>> login(String appkey, String token);

}

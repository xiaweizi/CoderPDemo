package con.xiaweizi.dashboardview;

import io.reactivex.Completable;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : con.xiaweizi.dashboardview.ExampleRepository
 *     e-mail : 1012126908@qq.com
 *     time   : 2019/02/15
 *     desc   :
 * </pre>
 */
public interface ExampleRepository {

    @POST("hello-convert-and-send")
    Completable sendRestEcho(@Query("msg") String message);
}
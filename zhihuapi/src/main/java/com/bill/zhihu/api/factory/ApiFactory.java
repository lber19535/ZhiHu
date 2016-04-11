package com.bill.zhihu.api.factory;

import com.bill.zhihu.api.AnswerApi;
import com.bill.zhihu.api.FeedsApi;
import com.bill.zhihu.api.LoginApi;
import com.bill.zhihu.api.PeopleApi;
import com.bill.zhihu.api.QuestionApi;
import com.bill.zhihu.api.net.ZhihuRetrofit;
import com.bill.zhihu.api.service.API;
import com.bill.zhihu.api.service.AnswerService;
import com.bill.zhihu.api.service.FeedsApiService;
import com.bill.zhihu.api.service.LoginApiService;
import com.bill.zhihu.api.service.PeopleApiService;
import com.bill.zhihu.api.service.QuestionService;

import java.util.HashMap;


/**
 * Create all available api.
 * <p/>
 * Created by bill_lv on 2016/2/2.
 */
public class ApiFactory {

    private static HashMap<Object, API> serviceMap = new HashMap<>();

    /**
     * Create {@link LoginApi}
     *
     * @return
     */
    public static LoginApi createLoginApi() {
        LoginApiService service = ZhihuRetrofit.retrofit().create(LoginApiService.class);
        LoginApi api = (LoginApi) checkApiInstance(service);
        if (api == null) {
            return (LoginApi) createApi(service, new LoginApi(service));
        } else {
            return api;
        }
    }

    /**
     * Create {@link PeopleApi}
     *
     * @return
     */
    public static PeopleApi createPeopleApi() {
        PeopleApiService service = ZhihuRetrofit.retrofit().create(PeopleApiService.class);
        PeopleApi api = (PeopleApi) checkApiInstance(service);
        if (api == null) {
            return (PeopleApi) createApi(service, new PeopleApi(service));
        } else {
            return api;
        }
    }

    /**
     * Create {@link PeopleApi}
     *
     * @return
     */
    public static FeedsApi createFeedsApi() {
        FeedsApiService service = ZhihuRetrofit.retrofit().create(FeedsApiService.class);
        FeedsApi api = (FeedsApi) checkApiInstance(service);
        if (api == null) {
            return (FeedsApi) createApi(service, new FeedsApi(service));
        } else {
            return api;
        }
    }

    /**
     * Create {@link AnswerApi}
     *
     * @return
     */
    public static AnswerApi createAnswerApi() {
        AnswerService service = ZhihuRetrofit.retrofit().create(AnswerService.class);
        AnswerApi api = (AnswerApi) checkApiInstance(service);
        if (api == null) {
            return (AnswerApi) createApi(service, new AnswerApi(service));
        } else {
            return api;
        }
    }

    /**
     * Create {@link QuestionApi}
     *
     * @return
     */
    public static QuestionApi createQuestionApi() {
        QuestionService service = ZhihuRetrofit.retrofit().create(QuestionService.class);
        QuestionApi api = (QuestionApi) checkApiInstance(service);
        if (api == null) {
            return (QuestionApi) createApi(service, new QuestionApi(service));
        } else {
            return api;
        }
    }

    private static API checkApiInstance(Object service) {
        if (serviceMap.containsKey(service)) {
            return serviceMap.get(service);
        }
        return null;
    }

    private static API createApi(Object service, API api) {
        serviceMap.put(service, api);
        return api;

    }
}

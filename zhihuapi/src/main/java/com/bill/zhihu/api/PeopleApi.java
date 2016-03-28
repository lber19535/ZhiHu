package com.bill.zhihu.api;

import com.bill.zhihu.api.bean.response.PeopleBasicResponse;
import com.bill.zhihu.api.service.API;
import com.bill.zhihu.api.service.PeopleApiService;
import com.bill.zhihu.api.utils.AuthStore;
import com.bill.zhihu.api.utils.TextUtils;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by bill_lv on 2016/3/1.
 */
public class PeopleApi implements API {


    private PeopleApiService service;
    private Realm realm;
    private static final String PEOPLE_FILE = "people_info";

    public PeopleApi(PeopleApiService service) {
        this.service = service;
        RealmConfiguration configuration = new RealmConfiguration.Builder(ZhihuApi.getContext()).name(PEOPLE_FILE).build();
        realm = Realm.getInstance(configuration);
    }

    public Observable<PeopleBasicResponse> getSelfBasic() {
        return service
                .selfBasic(TextUtils.upperHead(AuthStore.getTokenType()) + " " + AuthStore.getAccessToken(), AuthStore.getUnlockTicket())
                .map(new Func1<PeopleBasicResponse, PeopleBasicResponse>() {
                    @Override
                    public PeopleBasicResponse call(PeopleBasicResponse response) {
//                        realm.beginTransaction();
//                        realm.copyToRealm(response);
//                        realm.commitTransaction();

                        return response;
                    }
                });
    }

}

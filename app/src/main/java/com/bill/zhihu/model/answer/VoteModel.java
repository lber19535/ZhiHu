package com.bill.zhihu.model.answer;

import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;

import com.bill.zhihu.api.bean.answer.AnswerRelationship;
import com.bill.zhihu.api.bean.common.VoteType;

/**
 * Created by bill_lv on 2016/4/19.
 */
public class VoteModel extends BaseObservable {


    public final ObservableBoolean isVoteUp = new ObservableBoolean(false);
    public final ObservableBoolean isVoteDown = new ObservableBoolean(false);
    public final ObservableBoolean isNohelp = new ObservableBoolean(false);
    public final ObservableBoolean isThanked = new ObservableBoolean(false);

    public void setRelationship(AnswerRelationship ship) {
        setVoteType(ship.voteType);
        isThanked.set(ship.isThanked);
        isNohelp.set(ship.isNohelp);
    }

    public void setVoteType(int voteType) {
        switch (voteType) {
            case VoteType.UP_VOTE:
                isVoteUp.set(true);
                isVoteDown.set(false);
                break;
            case VoteType.CANCEL_VOTE:
                isVoteUp.set(false);
                isVoteDown.set(false);
                break;
            case VoteType.DOWN_VOTE:
                isVoteUp.set(false);
                isVoteDown.set(true);
                break;
        }
    }

}

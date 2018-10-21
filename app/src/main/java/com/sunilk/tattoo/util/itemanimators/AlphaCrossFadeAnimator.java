package com.sunilk.tattoo.util.itemanimators;


/**
 * Created by mikepenz on 08.01.16.
 * https://github.com/mikepenz/ItemAnimators
 */
public class AlphaCrossFadeAnimator extends DefaultAnimator<AlphaCrossFadeAnimator> {

    @Override
    public long getAddDelay(long remove, long move, long change) {
        return 0;
    }

    @Override
    public long getRemoveDelay(long remove, long move, long change) {
        return remove / 2;
    }
}

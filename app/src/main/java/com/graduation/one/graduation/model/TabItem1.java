package com.graduation.one.graduation.model;


import com.graduation.one.graduation.fragment.BaseFragment;

/**
 * tab类
 */
public class TabItem1 {


    /**
     * 文本
     */
    public int lableResId;

    public Class<? extends BaseFragment> tagFragmentClz;

    public TabItem1(int lableResId) {

        this.lableResId = lableResId;
    }

    public TabItem1(int lableResId, Class<? extends BaseFragment> tagFragmentClz) {

        this.lableResId = lableResId;
        this.tagFragmentClz = tagFragmentClz;
    }
}




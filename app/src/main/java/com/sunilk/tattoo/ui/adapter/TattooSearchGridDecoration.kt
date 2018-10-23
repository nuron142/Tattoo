package com.sunilk.tattoo.ui.adapter

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Created by Sunil on 22/10/18.
 */

class TattooSearchGridDecoration : RecyclerView.ItemDecoration {

    private val offset: Int

    constructor(offset: Int) : super() {
        this.offset = offset
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {

        outRect.top = 0
        outRect.bottom = offset

        var position = (parent.getChildAdapterPosition(view) + 1) % 5

        if (position % 5 == 0) {

            outRect.left = 0
            outRect.right = 0

        } else {

            if (position % 2 != 0) {
                outRect.left = 0
                outRect.right = offset / 2
            } else {
                outRect.left = offset / 2
                outRect.right = 0
            }
        }
    }
}
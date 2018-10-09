package com.ahmedadelsaid.moviesampleapp.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * DividerItemDecoration is a good thing for recycler view component.
 */

@Suppress("unused")
class DividerItemDecoration : RecyclerView.ItemDecoration {

    private var mDivider: Drawable? = null
    private var mShowFirstDivider = false
    private var mShowLastDivider = false


    constructor(context: Context, attrs: AttributeSet) {
        val a = context
                .obtainStyledAttributes(attrs, intArrayOf(android.R.attr.listDivider))
        mDivider = a.getDrawable(0)
        a.recycle()
    }

    constructor(context: Context, attrs: AttributeSet, showFirstDivider: Boolean,
                showLastDivider: Boolean) : this(context, attrs) {
        mShowFirstDivider = showFirstDivider
        mShowLastDivider = showLastDivider
    }

    constructor(divider: Drawable) {
        mDivider = divider
    }

    constructor(divider: Drawable, showFirstDivider: Boolean,
                showLastDivider: Boolean) : this(divider) {
        mShowFirstDivider = showFirstDivider
        mShowLastDivider = showLastDivider
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        if (mDivider == null) {
            return
        }

        if (parent.getChildLayoutPosition(view) < 1) {
            return
        }

        if (getOrientation(parent) == LinearLayoutManager.VERTICAL) {
            outRect.top = mDivider!!.intrinsicHeight
        } else {
            outRect.left = mDivider!!.intrinsicWidth
        }
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        if (mDivider == null) {
            super.onDrawOver(c, parent, state)
            return
        }

        // Initialization needed to avoid compiler warning
        var left = 0
        var right = 0
        var top = 0
        var bottom = 0
        val size: Int
        val orientation = getOrientation(parent)
        val childCount = parent.getChildCount()

        if (orientation == LinearLayoutManager.VERTICAL) {
            size = mDivider!!.intrinsicHeight
            left = parent.getPaddingLeft()
            right = parent.getWidth() - parent.getPaddingRight()
        } else { //horizontal
            size = mDivider!!.intrinsicWidth
            top = parent.getPaddingTop()
            bottom = parent.getHeight() - parent.getPaddingBottom()
        }

        for (i in (if (mShowFirstDivider) 0 else 1) until childCount) {
            val child = parent.getChildAt(i)
            val params = child.getLayoutParams() as RecyclerView.LayoutParams

            if (orientation == LinearLayoutManager.VERTICAL) {
                top = child.getTop() - params.topMargin
                bottom = top + size
            } else { //horizontal
                left = child.getLeft() - params.leftMargin
                right = left + size
            }
            mDivider!!.setBounds(left, top, right, bottom)
            mDivider!!.draw(c)
        }

        // show last divider
        if (mShowLastDivider && childCount > 0) {
            val child = parent.getChildAt(childCount - 1)
            val params = child.layoutParams as RecyclerView.LayoutParams
            if (orientation == LinearLayoutManager.VERTICAL) {
                top = child.getBottom() + params.bottomMargin
                bottom = top + size
            } else { // horizontal
                left = child.getRight() + params.rightMargin
                right = left + size
            }
            mDivider!!.setBounds(left, top, right, bottom)
            mDivider!!.draw(c)
        }
    }

    private fun getOrientation(parent: RecyclerView): Int {
        if (parent.getLayoutManager() is LinearLayoutManager) {
            val layoutManager = parent.getLayoutManager() as LinearLayoutManager
            return layoutManager.getOrientation()
        } else {
            throw IllegalStateException(
                    "DividerItemDecoration can only be used with a LinearLayoutManager.")
        }
    }
}
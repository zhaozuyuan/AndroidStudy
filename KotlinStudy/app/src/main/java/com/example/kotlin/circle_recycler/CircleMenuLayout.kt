package com.example.kotlin.circle_recycler

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.util.Log.d
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import java.util.jar.Attributes
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin
import kotlin.properties.Delegates

/**
 * create by zuyuan on 2019/11/19
 * 实现圆形菜单
 */
class CircleMenuLayout : ViewGroup{

    //圆形半径
    var mRadius: Int = 0
    //若需使用padding，使用该变量
    var mPadding: Float = 0f
    //布局开始时的角度
    var mStartAngle: Double = 0.0
    //item点击监听器
    var mItemClickListener: OnItemClickListener? = null
    //item适配器 注意踩坑，这里不允许出现子类
    var mAdapter: BaseAdapter by Delegates.observable(EmptyAdapter() as BaseAdapter) {
            _, oldValue, _->
        if (oldValue.count != 0) {
            clearOldItems()
        }
        buildItems()
    }

    constructor(context: Context): super(context)

    constructor(context: Context, attributes: AttributeSet): super(context, attributes)

    companion object {
        //默认大小
        private const val DEFAULT_SIZE_RELATIVE_DISPLAY: Float = 1f / 2f
        //默认item相对半径
        private const val ITEM_SIZE_RELATIVE_RADIUS: Float = 1f / 2f
    }

    init {
        //忽视padding
        val padding = 0
        setPadding(padding, padding, padding, padding)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        measureMySelf(widthMeasureSpec, heightMeasureSpec)
        measureChild()
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val itemWidth: Int = (mRadius * ITEM_SIZE_RELATIVE_RADIUS).toInt()
        val onceDelay: Float = 360f / childCount

        for (i: Int in 0 until childCount) {
            val item: View = getChildAt(i)
            if (item.visibility == GONE) {
                continue
            }
            mStartAngle %= 360
            //起始点到中心点距离，算上padding
            val distanceFromCenter: Float = mRadius - itemWidth / 2f - mPadding
            //离左侧的距离
            val left: Int = mRadius + (distanceFromCenter * cos(Math
                .toRadians(mStartAngle)) - 1 / 2f * itemWidth).roundToInt()
            //离顶部的距离
            val top: Int = mRadius + (distanceFromCenter * sin(Math
                .toRadians(mStartAngle)) - 1 / 2f * itemWidth).roundToInt()

            item.layout(left, top, left + itemWidth, top + itemWidth)
            mStartAngle += onceDelay
        }
    }

    private fun buildItems() {
        for (i: Int in 0 until mAdapter.count) {
            val itemView: View = mAdapter.getView(i, null, this)
            addView(itemView)
            itemView.setOnClickListener{ mItemClickListener?.onClick(i, it) }
        }
    }

    private fun clearOldItems() {
        removeAllViews()
    }

    private fun measureMySelf(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val resWidth: Int
        val resHeight: Int
        val width: Int = MeasureSpec.getSize(widthMeasureSpec)
        val height: Int = MeasureSpec.getSize(heightMeasureSpec)
        val widthMode: Int = MeasureSpec.getMode(widthMeasureSpec)
        val heightModel: Int = MeasureSpec.getMode(heightMeasureSpec)

        //非精确值模式
        resWidth = if (widthMode != MeasureSpec.EXACTLY || heightModel != MeasureSpec.EXACTLY) {
            if (suggestedMinimumWidth == 0) {
                getDefaultWidth().toInt()
            } else {
                suggestedMinimumWidth
            }
        } else {
            width.coerceAtMost(height)
        }
        resHeight = resWidth

        setMeasuredDimension(resWidth, resHeight)
    }

    private fun measureChild() {
        mRadius = (measuredWidth / 2f).toInt()

        val childWidth: Int = (mRadius * ITEM_SIZE_RELATIVE_RADIUS).toInt()
        val mode: Int = MeasureSpec.EXACTLY

        for (i: Int in 0 until childCount) {
            val child: View = getChildAt(i)
            if (child.visibility != GONE) {
                continue
            }
            val makeMeasureSpec: Int = MeasureSpec.makeMeasureSpec(childWidth, mode)
            child.measure(makeMeasureSpec, makeMeasureSpec)
        }
    }

    private fun getDefaultWidth() : Float
            = resources.displayMetrics.widthPixels * DEFAULT_SIZE_RELATIVE_DISPLAY

    interface OnItemClickListener {
        fun onClick(position: Int, view: View)
    }

    class EmptyAdapter : BaseAdapter() {
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? = null

        override fun getItem(position: Int): Any? = null

        override fun getItemId(position: Int): Long = 0L

        override fun getCount(): Int = 0
    }
}
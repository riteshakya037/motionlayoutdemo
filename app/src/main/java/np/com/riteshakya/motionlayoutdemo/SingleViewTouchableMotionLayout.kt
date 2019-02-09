package np.com.riteshakya.motionlayoutdemo

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout

class SingleViewTouchableMotionLayout(context: Context, attributeSet: AttributeSet? = null) :
    MotionLayout(context, attributeSet) {

    private val viewToDetectTouch by lazy {
        if (resourceId != -1)
            return@lazy findViewById<View>(resourceId)
        else
            return@lazy null
    }
    private val viewRect = Rect()
    private var touchStarted = false
    private val transitionListenerList = mutableListOf<TransitionListener?>()
    private var resourceId: Int = -1

    init {
        addTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {

            }

            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {
            }

            override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {
            }

            override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
                touchStarted = false
            }
        })

        super.setTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {

            }

            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {

            }

            override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {
                transitionListenerList.filterNotNull()
                    .forEach { it.onTransitionChange(p0, p1, p2, p3) }
            }

            override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
                transitionListenerList.filterNotNull()
                    .forEach { it.onTransitionCompleted(p0, p1) }
            }
        })
        val ta = context.theme.obtainStyledAttributes(attributeSet, R.styleable.SingleViewTouchableMotionLayout, 0, 0)
        resourceId = ta.getResourceId(R.styleable.SingleViewTouchableMotionLayout_touchId, -1)
        ta.recycle()
    }


    override fun setTransitionListener(listener: TransitionListener?) {
        addTransitionListener(listener)
    }

    override fun setTransition(beginId: Int, endId: Int) {
        if (getStartId() != beginId || getEndId() != endId) {
            super.setTransition(beginId, endId)
        }
    }

    fun addTransitionListener(listener: TransitionListener?) {
        transitionListenerList += listener
    }

    internal fun getStartId(): Int {
        return javaClass.superclass!!.getDeclaredField("mBeginState").let {
            it.isAccessible = true
            return@let it.getInt(this)
        }
    }

    internal fun getEndId(): Int {
        return javaClass.superclass!!.getDeclaredField("mEndState").let {
            it.isAccessible = true
            return@let it.getInt(this)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        //gestureDetector.onTouchEvent(event)
        when (event.actionMasked) {
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                touchStarted = false
                return super.onTouchEvent(event)
            }
        }
        if (!touchStarted && viewToDetectTouch != null) {
            viewToDetectTouch!!.getHitRect(viewRect)
            touchStarted = viewRect.contains(event.x.toInt(), event.y.toInt())
        }
        return touchStarted && super.onTouchEvent(event)
    }
}
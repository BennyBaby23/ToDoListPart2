package com.mdev.todolistpart2

import android.content.Context
import android.util.Log
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import java.lang.Math.abs

//Class for gesture touch listener
open class CustomTouchListener(context: Context?): OnTouchListener
{
    private val gestureDetector: GestureDetector

    override fun onTouch(view: View?, motionEvent: MotionEvent?): Boolean
    {
        return gestureDetector.onTouchEvent(motionEvent)
    }

    inner class GestureListener: SimpleOnGestureListener()
    {
        private val SWIPE_LENGTH_HORIZONTAL_THRESHOLD: Int = 50
        private val SWIPE_VELOCITY_THRESHOLD: Int = 50
        private val DEBUG_TAG = "Gestures"

        override fun onDown(event: MotionEvent?): Boolean
        {
             return true
        }

        override fun onSingleTapConfirmed(event: MotionEvent?): Boolean
        {
            onClick()
            return super.onSingleTapUp(event)
        }

        override fun onFling(event1: MotionEvent, event2: MotionEvent,
                             velocityX: Float, velocityY: Float): Boolean
        {
            try
            {
                val diffYs = event2.y - event1.y
                val diffXs = event2.x - event1.x


                if(abs(diffXs) > abs(diffYs))
                {

                    if(abs(diffXs) > SWIPE_LENGTH_HORIZONTAL_THRESHOLD && abs(velocityX) > SWIPE_VELOCITY_THRESHOLD)
                    {
                        // determine which direction
                        if(diffXs > 0)
                        {
                            onSwipeRight()
                            Log.d(DEBUG_TAG, "onSwipeRight")
                        }
                        else
                        {
                            onSwipeLeft()
                            Log.d(DEBUG_TAG, "onSwipeLeft")
                        }
                    }
                }
            }
            catch(exception: Exception)
            {
                exception.printStackTrace()
            }
            return false
        }
    }

    open fun onSwipeLeft(){}
    open fun onSwipeRight(){}
    open fun onClick(){}
    init {
        gestureDetector = GestureDetector(context, GestureListener())
    }
}
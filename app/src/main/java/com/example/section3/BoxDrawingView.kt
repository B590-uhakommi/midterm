package com.example.section3

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.util.Log

private const val TAG = "BoxDrawingView"

class BoxDrawingView(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    private var currentBox: Box? = null
    private val boxes = mutableListOf<Box>()
    private val boxPaint = Paint().apply {
        color = 0x22ff0000.toInt() // Semi-transparent red color
    }
    private val backgroundPaint = Paint().apply {
        color = 0xfff8efe0.toInt() // Light background color
    }

    // Save the boxes' state when the view is going to be destroyed or orientation changes
    override fun onSaveInstanceState(): Parcelable? {
        val superState = super.onSaveInstanceState() // Save the super state first
        val bundle = Bundle().apply {
            putParcelableArrayList("boxes", ArrayList(boxes)) // Save the list of boxes
        }
        bundle.putParcelable("superState", superState) // Store the super state for restoration
        Log.d(TAG, "onSaveInstanceState: Boxes saved: ${boxes.size}")
        return bundle
    }

    // Restore the boxes' state when the view is recreated after orientation changes
    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state is Bundle) {
            val superState = state.getParcelable<Parcelable>("superState")
            super.onRestoreInstanceState(superState) // Restore the super state
            val restoredBoxes = state.getParcelableArrayList<Box>("boxes")
            restoredBoxes?.let {
                boxes.clear() // Clear current boxes and add restored ones
                boxes.addAll(it)
            }
            Log.d(TAG, "onRestoreInstanceState: Boxes restored: ${boxes.size}")
        } else {
            super.onRestoreInstanceState(state)
        }
    }

    // Handle touch events like ACTION_DOWN, ACTION_MOVE, and ACTION_UP
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val current = PointF(event.x, event.y)
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                currentBox = Box(current).also {
                    boxes.add(it) // Start a new box
                }
            }

            MotionEvent.ACTION_MOVE -> {
                currentBox?.let {
                    it.end = current // Update the current box's endpoint
                    invalidate() // Redraw the view
                }
            }

            MotionEvent.ACTION_UP -> {
                currentBox = null // Finish the current box
            }

            MotionEvent.ACTION_CANCEL -> {
                currentBox = null // Cancel the current box
            }
        }
        return true
    }

    // Draw the background and boxes
    override fun onDraw(canvas: Canvas) {
        canvas.drawPaint(backgroundPaint) // Fill the background
        boxes.forEach { box ->
            canvas.drawRect(box.left, box.top, box.right, box.bottom, boxPaint) // Draw each box
        }
    }
}

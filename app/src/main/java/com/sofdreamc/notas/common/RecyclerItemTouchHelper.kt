package com.sofdreamc.notas.common

import android.graphics.Canvas
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.sofdreamc.notas.R
import com.sofdreamc.notas.ui.interfaces.RecyclerItemTouchHelperListener

class RecyclerItemTouchHelper(
    dragDirs: Int,
    swipeDirs: Int,
    private val listener: RecyclerItemTouchHelperListener
) :
    ItemTouchHelper.SimpleCallback(dragDirs, swipeDirs) {


    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return true
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        viewHolder?.let { vHolder ->
            val foregroundView =
                vHolder.itemView.findViewById<ConstraintLayout>(R.id.view_foreground)
            ItemTouchHelper.Callback.getDefaultUIUtil().onSelected(foregroundView)
        }
    }

    override fun onChildDrawOver(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder?,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        viewHolder?.let { vHolder ->
            val foregroundView =
                vHolder.itemView.findViewById<ConstraintLayout>(R.id.view_foreground)
            ItemTouchHelper.Callback.getDefaultUIUtil()
                .onDrawOver(c, recyclerView, foregroundView, dX, dY, actionState, isCurrentlyActive)
        }
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        viewHolder.let { vHolder ->
            val foregroundView =
                vHolder.itemView.findViewById<ConstraintLayout>(R.id.view_foreground)
            ItemTouchHelper.Callback.getDefaultUIUtil().clearView(foregroundView)
        }
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        viewHolder.let { vHolder ->
            val foregroundView =
                vHolder.itemView.findViewById<ConstraintLayout>(R.id.view_foreground)
            ItemTouchHelper.Callback.getDefaultUIUtil()
                .onDraw(c, recyclerView, foregroundView, dX, dY, actionState, isCurrentlyActive)
        }
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        listener.onSwiped(viewHolder, direction, viewHolder.adapterPosition)
    }
}
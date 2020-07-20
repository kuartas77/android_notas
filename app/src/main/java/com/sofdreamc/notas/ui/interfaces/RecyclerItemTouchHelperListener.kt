package com.sofdreamc.notas.ui.interfaces

import androidx.recyclerview.widget.RecyclerView

interface RecyclerItemTouchHelperListener {
    fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int, position: Int)
}
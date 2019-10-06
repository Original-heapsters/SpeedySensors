package com.space.speedysensors.ui.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.space.speedysensors.models.SensorPayload

class PayloadViewHolder(view: View): RecyclerView.ViewHolder(view) {

    interface PayloadViewHolderListener {
        fun cellClicked(payload: SensorPayload)
    }

    lateinit var payload: SensorPayload
    lateinit var listener: PayloadViewHolderListener

    init {
        view.isClickable = true
        view.setOnClickListener {
            listener.cellClicked(payload)
        }
    }

}
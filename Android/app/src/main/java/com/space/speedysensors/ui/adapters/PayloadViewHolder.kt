package com.space.speedysensors.ui.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.space.speedysensors.models.SensorPayload
import kotlinx.android.synthetic.main.list_view_payload.view.*

class PayloadViewHolder(private val view: View): RecyclerView.ViewHolder(view){
    fun bind(thing:SensorPayload){
        //
        view.textViewUsername.text = thing.id
    }
}
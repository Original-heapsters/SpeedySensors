package com.space.speedysensors.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.space.speedysensors.R
import com.space.speedysensors.models.Anomaly
import com.space.speedysensors.models.SensorPayload
import kotlinx.android.synthetic.main.list_view_payload.view.*

class PayloadAdapter(
    private val payloadListener: PayloadViewHolder.PayloadViewHolderListener
): RecyclerView.Adapter<PayloadViewHolder>() {

    private var payloads: ArrayList<SensorPayload> = arrayListOf()
    private var anomalies: ArrayList<Anomaly> = arrayListOf()

    fun updatePayloads(list: ArrayList<SensorPayload>) {
        payloads = list
        notifyDataSetChanged()
        return

//        if (payloads.isEmpty()) {
//            this.payloads.addAll(list)
//            this.notifyDataSetChanged()
//            return
//        }
//
//        val diffCallback = PayloadDiffCallback(this.payloads, list)
//        val diffResult = DiffUtil.calculateDiff(diffCallback, false)
//
//        this.payloads.clear()
//        this.payloads.addAll(list)
//
//        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PayloadViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_view_payload, parent, false)
        return PayloadViewHolder(itemView)
    }

    override fun getItemCount(): Int = payloads.size

    override fun onBindViewHolder(holder: PayloadViewHolder, position: Int) {
        val payload = payloads[position]
        val userIsAnomaly = anomalies.filter { it.id == payload.id }.isNotEmpty()
        val backgroundColor = ContextCompat.getColor(holder.itemView.context, if (userIsAnomaly) R.color.colorAccent else android.R.color.white)
        holder.payload = payload
        holder.listener = payloadListener
        holder.itemView.textViewUsername.text = payload.id
        holder.itemView.textViewRole.text = payload.role
        holder.itemView.setBackgroundColor(backgroundColor)
    }

    fun anomalyDetected(anomalies: ArrayList<Anomaly>) {
        this.anomalies = anomalies
        notifyDataSetChanged()
    }


    private inner class PayloadDiffCallback(
            private val mOldList: ArrayList<SensorPayload>,
            private val mNewList: ArrayList<SensorPayload>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int {
            return mOldList.size
        }

        override fun getNewListSize(): Int {
            return mNewList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return true
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = mOldList[oldItemPosition]
            val newItem = mNewList[newItemPosition]
            println("$$$$$ oldX: ${oldItem.accelerometer[0]}  newX: ${newItem.accelerometer[0]}")
            println("$$$$$ oldY: ${oldItem.accelerometer[1]}  newY: ${newItem.accelerometer[1]}")
            println("$$$$$ oldZ: ${oldItem.accelerometer[2]}  newZ: ${newItem.accelerometer[2]}")
            return oldItem.accelerometer[0] == newItem.accelerometer[0]
                    && oldItem.accelerometer[1] == newItem.accelerometer[1]
                    && oldItem.accelerometer[2] == newItem.accelerometer[2]
        }
    }
}
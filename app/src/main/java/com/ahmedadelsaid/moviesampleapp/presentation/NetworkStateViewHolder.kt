package com.ahmedadelsaid.moviesampleapp.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ahmedadelsaid.moviesampleapp.R
import com.ahmedadelsaid.moviesampleapp.domain.NetworkState
import com.ahmedadelsaid.moviesampleapp.domain.Status
import com.paginate.recycler.LoadingListItemCreator
import kotlinx.android.synthetic.main.item_network_state.view.*


class CustomLoadingListItemCreator(private val networkState: NetworkState?, private val retryCallback: () -> Unit) : LoadingListItemCreator {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_network_state, parent, false)
        return NetworkStateViewHolder(view, retryCallback)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as NetworkStateViewHolder
        viewHolder.bindTo(networkState)
    }
}

class NetworkStateViewHolder(val view: View, private val retryCallback: () -> Unit) : RecyclerView.ViewHolder(view) {

    init {
        itemView.retryLoadingButton.setOnClickListener { retryCallback() }
    }

    fun bindTo(networkState: NetworkState?) {

        //error message
        itemView.errorMessageTextView.visibility = if (networkState?.message != null) View.VISIBLE else View.GONE
        if (networkState?.message != null) {
            itemView.errorMessageTextView.text = networkState.message
        }

        //loading and retry
        itemView.retryLoadingButton.visibility = if (networkState?.status == Status.FAILED) View.VISIBLE else View.GONE
        itemView.loadingProgressBar.visibility = if (networkState?.status == Status.RUNNING) View.VISIBLE else View.GONE
    }

}
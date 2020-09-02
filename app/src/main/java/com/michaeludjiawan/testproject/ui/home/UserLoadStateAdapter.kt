package com.michaeludjiawan.testproject.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.michaeludjiawan.testproject.R
import com.michaeludjiawan.testproject.util.toVisibility
import kotlinx.android.synthetic.main.user_load_state_footer_view_item.view.*

class UserLoadStateAdapter(private val retry: () -> Unit) : LoadStateAdapter<UserLoadStateViewHolder>() {

    override fun onBindViewHolder(holder: UserLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): UserLoadStateViewHolder =
        UserLoadStateViewHolder.create(parent, retry)

}

class UserLoadStateViewHolder(
    private val view: View,
    private val retry: () -> Unit
) : RecyclerView.ViewHolder(view) {

    init {
        view.tv_user_load_state_retry_button.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            view.tv_user_load_state_error_msg.text = loadState.error.localizedMessage
        }
        view.tv_user_load_state_progress_bar.visibility = toVisibility(loadState is LoadState.Loading)
        view.tv_user_load_state_retry_button.visibility = toVisibility(loadState !is LoadState.Loading)
        view.tv_user_load_state_error_msg.visibility = toVisibility(loadState !is LoadState.Loading)
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): UserLoadStateViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.user_load_state_footer_view_item, parent, false)
            return UserLoadStateViewHolder(view, retry)
        }
    }

}
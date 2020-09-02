package com.michaeludjiawan.testproject.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.michaeludjiawan.testproject.R
import com.michaeludjiawan.testproject.data.model.User
import kotlinx.android.synthetic.main.item_user.view.*

class UserPagingAdapter(
    private val onItemClick: (User) -> Unit
) : PagingDataAdapter<User, RecyclerView.ViewHolder>(USER_COMPARATOR) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(position)?.let { (holder as UserViewHolder).bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        UserViewHolder.create(parent, onItemClick)

    companion object {
        val USER_COMPARATOR = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean = oldItem == newItem
            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean = oldItem.id == newItem.id
        }
    }

}

class UserViewHolder(
    private val view: View,
    private val onItemClick: (User) -> Unit
) : RecyclerView.ViewHolder(view) {

    fun bind(user: User) {
        with(view) {
            Glide.with(view.context)
                .load(user.avatarUrl)
                .apply(RequestOptions().placeholder(R.color.colorPrimary))
                .into(iv_user_avatar)

            tv_user_name.text = "${user.firstName} ${user.lastName}"
            tv_user_email.text = user.email

            setOnClickListener { onItemClick(user) }
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            onItemClick: (User) -> Unit
        ): UserViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
            return UserViewHolder(view, onItemClick)
        }
    }

}
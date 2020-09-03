package com.michaeludjiawan.testproject.ui.home

import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.michaeludjiawan.testproject.R
import com.michaeludjiawan.testproject.data.model.User
import com.michaeludjiawan.testproject.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_user_detail.*

class UserDetailFragment : BaseFragment(R.layout.fragment_user_detail) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initDefaultToolbar(getString(R.string.user_detail_page_title))

        requireArguments().getParcelable<User>(INTENT_USER)?.let { user ->
            loadUserData(user)
        }
    }

    private fun loadUserData(user: User) {
        Glide.with(this)
            .load(user.avatarUrl)
            .into(iv_user_detail_avatar)
        tv_user_detail_name.text = "${user.firstName} ${user.lastName}"
        tv_user_detail_email.text = user.email
    }

    companion object {
        private const val INTENT_USER = "user"

        fun newInstance(user: User): UserDetailFragment {
            return UserDetailFragment().also { fragment ->
                fragment.arguments = Bundle().also { bundle ->
                    bundle.putParcelable(INTENT_USER, user)
                }
            }
        }
    }
}
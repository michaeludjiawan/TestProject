package com.michaeludjiawan.testproject.ui.profile

import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.michaeludjiawan.testproject.R
import com.michaeludjiawan.testproject.data.model.User
import com.michaeludjiawan.testproject.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_profile_detail.*

class ProfileDetailFragment : BaseFragment(R.layout.fragment_profile_detail) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initDefaultToolbar(getString(R.string.profile_detail_page_title))

        requireArguments().getParcelable<User>(INTENT_USER)?.let { user ->
            loadProfileData(user)
        }
    }

    private fun loadProfileData(user: User) {
        Glide.with(this)
            .load(user.avatarUrl)
            .into(iv_profile_detail_avatar)
        tv_profile_detail_name.text = "${user.firstName} ${user.lastName}"
        tv_profile_detail_email.text = user.email
    }

    companion object {
        private const val INTENT_USER = "user"

        fun newInstance(user: User): ProfileDetailFragment {
            return ProfileDetailFragment().also { fragment ->
                fragment.arguments = Bundle().also { bundle ->
                    bundle.putParcelable(INTENT_USER, user)
                }
            }
        }
    }
}
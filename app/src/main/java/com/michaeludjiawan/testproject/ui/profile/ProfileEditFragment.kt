package com.michaeludjiawan.testproject.ui.profile

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.michaeludjiawan.testproject.R
import com.michaeludjiawan.testproject.data.model.Account
import com.michaeludjiawan.testproject.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_profile_edit.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileEditFragment : BaseFragment(R.layout.fragment_profile_edit) {

    private val viewModel by viewModel<ProfileEditViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initDefaultToolbar(getString(R.string.profile_action_edit_label))

        initObservers()
        initSaveButton()

        viewModel.getAccount()
    }

    private fun initObservers() {
        viewModel.account.observe(viewLifecycleOwner, Observer { account ->
            account ?: return@Observer

            populateFields(account)
        })
    }

    private fun populateFields(account: Account) {
        et_profile_edit_first_name.setText(account.firstName)
        et_profile_edit_last_name.setText(account.lastName)
        et_profile_edit_email.setText(account.email)
    }

    private fun initSaveButton() {
        btn_profile_edit_save.setOnClickListener {
            val firstName = et_profile_edit_first_name.text.toString()
            val lastName = et_profile_edit_last_name.text.toString()
            val email = et_profile_edit_email.text.toString()

            viewModel.updateAccount(firstName, lastName, email)

            requireActivity().supportFragmentManager.popBackStack()
        }
    }
}
package com.michaeludjiawan.testproject.ui.profile

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import com.michaeludjiawan.testproject.R
import com.michaeludjiawan.testproject.data.model.Account
import com.michaeludjiawan.testproject.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_profile.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : BaseFragment(R.layout.fragment_profile) {

    private val viewModel by viewModel<ProfileViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initDefaultToolbar(getString(R.string.profile_page_title), false)
        initObservers()

        viewModel.getAccount()
    }

    private fun initObservers() {
        viewModel.account.observe(viewLifecycleOwner, Observer { account ->
            account ?: return@Observer

            populateFields(account)
        })
    }

    private fun populateFields(account: Account) {
        tv_profile_name.text = "${account.firstName} ${account.lastName}".ifBlank { "-" }
        tv_profile_email.text = account.email.ifBlank { "-" }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        return inflater.inflate(R.menu.menu_profile, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_profile_edit -> {
                navigateToEditPage()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun navigateToEditPage() {
        val destination = ProfileEditFragment()
        findNavController().navigateToPage(destination)
    }
}
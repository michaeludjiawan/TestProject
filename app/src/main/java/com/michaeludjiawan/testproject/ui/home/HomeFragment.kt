package com.michaeludjiawan.testproject.ui.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.michaeludjiawan.testproject.R
import com.michaeludjiawan.testproject.data.model.User
import com.michaeludjiawan.testproject.ui.BaseFragment
import com.michaeludjiawan.testproject.ui.profile.ProfileDetailFragment
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment(R.layout.fragment_home) {

    private val viewModel by viewModel<HomeViewModel>()

    private val onUserClick: (User) -> Unit = { user ->
        val destination = ProfileDetailFragment.newInstance(user)
        findNavController().navigateToPage(destination)
    }

    private val userAdapter = UserPagingAdapter(onUserClick)

    private var getUsersJob: Job? = null

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initDefaultToolbar(getString(R.string.home_page_title), false)
        initRecyclerView()

        getUsers()
    }

    @OptIn(ExperimentalPagingApi::class)
    private fun initRecyclerView() {
        userAdapter.withLoadStateHeaderAndFooter(
            header = UserLoadStateAdapter { userAdapter.retry() },
            footer = UserLoadStateAdapter { userAdapter.retry() }
        )

        userAdapter.addLoadStateListener { loadState ->
            if (isDetached) return@addLoadStateListener

            rv_home_users.isVisible = loadState.source.refresh is LoadState.NotLoading
            pb_home_progress_bar.isVisible = loadState.source.refresh is LoadState.Loading
            btn_home_retry.isVisible = loadState.source.refresh is LoadState.Error

            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
            errorState?.let {
                Toast.makeText(
                    context,
                    "Error: ${it.error.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        rv_home_users.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = userAdapter
        }

        viewLifecycleOwner.lifecycleScope.launch {
            userAdapter.loadStateFlow
                .distinctUntilChangedBy { it.refresh }
                .filter { it.refresh is LoadState.NotLoading }
                .collect { rv_home_users.scrollToPosition(0) }
        }

        btn_home_retry.setOnClickListener { userAdapter.retry() }
    }

    private fun getUsers() {
        getUsersJob?.cancel()
        getUsersJob = viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getUsers().collectLatest { userAdapter.submitData(it) }
        }
    }
}
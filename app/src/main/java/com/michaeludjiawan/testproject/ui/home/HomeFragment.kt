package com.michaeludjiawan.testproject.ui.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.michaeludjiawan.testproject.R
import com.michaeludjiawan.testproject.data.model.User
import com.michaeludjiawan.testproject.util.toVisibility
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val viewModel by viewModel<HomeViewModel>()

    private val onUserClick: (User) -> Unit = {}

    private val userAdapter = UserPagingAdapter(onUserClick)

    private var getUsersJob: Job? = null

    @ExperimentalCoroutinesApi
    @ExperimentalPagingApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()

        getUsers()
    }

    @ExperimentalPagingApi
    private fun initRecyclerView() {
        userAdapter.withLoadStateHeaderAndFooter(
            header = UserLoadStateAdapter { userAdapter.retry() },
            footer = UserLoadStateAdapter { userAdapter.retry() }
        )

        userAdapter.addLoadStateListener { loadState ->
            if (isDetached) return@addLoadStateListener

            if (loadState.refresh !is LoadState.NotLoading) {
                handleUiLoading(loadState)
            } else {
                handleUiComplete(loadState)
            }
        }

        rv_home_users.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = userAdapter
        }

        viewLifecycleOwner.lifecycleScope.launch {
            userAdapter.dataRefreshFlow.collect {
                rv_home_users.scrollToPosition(0)
            }
        }

        btn_home_retry.setOnClickListener { userAdapter.retry() }
    }

    private fun handleUiLoading(loadState: CombinedLoadStates) {
        rv_home_users.visibility = View.GONE
        pb_home_progress_bar.visibility = toVisibility(loadState.refresh is LoadState.Loading)
        btn_home_retry.visibility = toVisibility(loadState.refresh is LoadState.Error)
    }

    private fun handleUiComplete(loadState: CombinedLoadStates) {
        rv_home_users.visibility = View.VISIBLE
        pb_home_progress_bar.visibility = View.GONE
        btn_home_retry.visibility = View.GONE

        val errorState = when {
            loadState.append is LoadState.Error -> {
                loadState.append as LoadState.Error
            }
            loadState.prepend is LoadState.Error -> {
                loadState.prepend as LoadState.Error
            }
            else -> {
                null
            }
        }

        errorState?.let {
            Toast.makeText(requireContext(), "Error: ${it.error.message}", Toast.LENGTH_LONG).show()
        }
    }

    @ExperimentalCoroutinesApi
    private fun getUsers() {
        getUsersJob?.cancel()
        getUsersJob = viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getUsers().collectLatest { userAdapter.submitData(it) }
        }
    }
}
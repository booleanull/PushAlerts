package com.booleanull.feature_home_ui.fragment

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import com.booleanull.core_ui.adapter.GenericAdapter
import com.booleanull.core_ui.adapter.GenericItemDiff
import com.booleanull.core_ui.adapter.OnItemClickListener
import com.booleanull.core_ui.base.BaseFragment
import com.booleanull.core_ui.dp
import com.booleanull.core_ui.getAttributeColor
import com.booleanull.core_ui.helper.RecyclerDivider
import com.booleanull.feature_home.data.Application
import com.booleanull.feature_home_ui.R
import com.booleanull.feature_home_ui.adapter.ApplicationViewHolderFactory
import com.booleanull.feature_home_ui.screen.HomeDetailsScreen
import com.booleanull.feature_home_ui.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment() {

    private val viewModel: HomeViewModel by viewModel()

    private val applicationAdapter by lazy {
            GenericAdapter(ApplicationViewHolderFactory()).apply {
                setOnItemClickListener(object :
                    OnItemClickListener<ApplicationViewHolderFactory.ApplicationItemClickData> {
                    override fun onItemClick(item: ApplicationViewHolderFactory.ApplicationItemClickData) {
                        router.navigateTo(
                            HomeDetailsScreen(
                                item.application.packageName
                            ),
                            item.view,
                            item.application.packageName
                        )
                    }
                })
                setDiffUtil(object : GenericItemDiff<Application> {
                    override fun areItemsTheSame(
                        oldItems: List<Application>,
                        newItems: List<Application>,
                        oldItemPosition: Int,
                        newItemPosition: Int
                    ): Boolean {
                        val old = oldItems[oldItemPosition]
                        val new = newItems[newItemPosition]
                        return old.packageName == new.packageName
                    }

                    override fun areContentsTheSame(
                        oldItems: List<Application>,
                        newItems: List<Application>,
                        oldItemPosition: Int,
                        newItemPosition: Int
                    ): Boolean {
                        val old = oldItems[oldItemPosition]
                        val new = newItems[newItemPosition]
                        return old.name == new.name
                    }
                })
            }
    }

    private val applicationRecyclerDivider by lazy {
        RecyclerDivider(
            line = RecyclerDivider.Line(
                dp(68),
                0,
                dp(1),
                requireContext().getAttributeColor(
                    R.attr.colorDivider,
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.colorGray
                    )
                )
            ),
            hasFooter = true
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            viewModel.loadApplications()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.adapter = applicationAdapter
        recyclerView.addItemDecoration(applicationRecyclerDivider)

        searchFrameLayout.setOnClickListener {
            if (viewModel.searchVisible.value == true && searchEditText.text.isNotEmpty()) {
                searchEditText.setText("")
            } else {
                viewModel.changeSearchVisible()
            }
        }

        searchEditText.addTextChangedListener {
            if (it.isNullOrBlank() && viewModel.isSearch) {
                viewModel.loadApplications()
            }
        }

        searchEditText.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH
                || actionId == EditorInfo.IME_ACTION_DONE
                || event.action == KeyEvent.ACTION_DOWN
                && event.keyCode == KeyEvent.KEYCODE_ENTER
            ) {
                if (!searchEditText.text.isNullOrBlank())
                    viewModel.searchApplication(searchEditText.text.toString())
                true
            }
            false
        }

        viewModel.applicationList.observe(viewLifecycleOwner, Observer {
            applicationAdapter.dataList = it.toMutableList()
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer {
            progressBar.isVisible = it
        })

        viewModel.applicationNotFound.observe(viewLifecycleOwner, Observer {
            emptyTextView.isVisible = it
        })

        viewModel.searchVisible.observe(viewLifecycleOwner, Observer {
            titleTextView.isVisible = !it
            searchEditText.isVisible = it
            searchImageView.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    if (it) R.drawable.ic_close else R.drawable.ic_search
                )
            )
            if (it) {
                searchEditText.requestFocusFromTouch()
            }
        })

        viewModel.searchLoading.observe(viewLifecycleOwner, Observer {
            searchProgressBar.isVisible = it
            searchImageView.isVisible = !it
        })
    }
}
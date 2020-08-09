package com.booleanull.feature_home_ui.fragment

import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.booleanull.core.data.Application
import com.booleanull.core_ui.adapter.GenericAdapter
import com.booleanull.core_ui.adapter.GenericItemDiff
import com.booleanull.core_ui.adapter.OnItemClickListener
import com.booleanull.core_ui.base.BaseFragment
import com.booleanull.core_ui.dp
import com.booleanull.core_ui.getAttributeColor
import com.booleanull.core_ui.helper.RecyclerDivider
import com.booleanull.feature_home_ui.R
import com.booleanull.feature_home_ui.adapter.ApplicationViewHolderFactory
import com.booleanull.feature_home_ui.screen.HomeDetailsScreen
import com.booleanull.feature_home_ui.screen.SettingsScreen
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
                        HomeDetailsScreen(item.application.packageName),
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
                dp(68f),
                0f,
                dp(0.5f),
                requireContext().getAttributeColor(R.attr.colorDivider)
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            viewModel.loadApplications()
        }
    }

    override fun onStart() {
        super.onStart()
        searchView.isVisible = viewModel.isSearch
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)

        recyclerView.adapter = applicationAdapter
        recyclerView.addItemDecoration(applicationRecyclerDivider)

        closeImageView.setOnClickListener {
            searchView.isVisible = false
            searchEditText.setText("")
            hideKeyboard(searchEditText)
            if (viewModel.isSearch) {
                viewModel.loadApplications()
            }
        }

        searchImageView.setOnClickListener {
            viewModel.searchApplication(searchEditText.text.toString())
        }

        searchEditText.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH
                || actionId == EditorInfo.IME_ACTION_DONE
                || event.action == KeyEvent.ACTION_DOWN
                && event?.keyCode == KeyEvent.KEYCODE_ENTER
            ) {
                if (!searchEditText.text.isNullOrBlank()) {
                    viewModel.searchApplication(searchEditText.text.toString())
                    hideKeyboard(searchEditText)
                }
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
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_home, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.search -> {
                searchView.isVisible = true
                showKeyboard(searchEditText)
                true
            }
            R.id.settings -> {
                router.navigateTo(
                    SettingsScreen(),
                    android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right,
                    android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right
                )
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
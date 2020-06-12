package com.booleanull.feature_home_ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.core.content.ContextCompat
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import com.booleanull.core_ui.ItemTouchSwipeHelper
import com.booleanull.core_ui.Line
import com.booleanull.core_ui.RecyclerDivider
import com.booleanull.core_ui.base.BaseFragment
import com.booleanull.core_ui.setChecked
import com.booleanull.feature_home_ui.R
import com.booleanull.feature_home_ui.adapter.FilterAdapter
import com.booleanull.feature_home_ui.viewmodel.HomeDetailsViewModel
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_home_details.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min


class HomeDetailsFragment : BaseFragment() {

    private lateinit var viewModel: HomeDetailsViewModel

    private val filterAdapter by lazy {
        FilterAdapter()
    }

    private val alarmSwitchOnCheckedChangeListener =
        CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            viewModel.setAlarm(isChecked)
        }

    private val filterSwitchOnCheckedChangeListener =
        CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            viewModel.setFilter(isChecked)
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home_details, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = viewModel<HomeDetailsViewModel> {
            parametersOf(
                arguments?.getString("packageName") ?: ""
            )
        }.value
        if (savedInstanceState == null) {
            viewModel.loadApplication()
            viewModel.searchApplicationAlarm()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar.navigationIcon =
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.ic_arrow_back
            )
        toolbar.setNavigationOnClickListener { onBackPressed() }

        appbar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            val threshold = appBarLayout.totalScrollRange
            val progress = min(1f, (abs(verticalOffset).toFloat() / threshold))
            iconImageView.scaleX = max(1f - progress, 0.5f)
            iconImageView.scaleY = max(1f - progress, 0.5f)
        })

        iconImageView.transitionName = arguments?.getString("packageName") ?: ""

        filterRecyclerView.adapter = filterAdapter
        filterRecyclerView.addItemDecoration(
            RecyclerDivider(
                line = Line(
                    0, 0, 1, ContextCompat.getColor(
                        requireContext(),
                        R.color.colorDivider
                    )
                )
            )
        )
        ItemTouchHelper(ItemTouchSwipeHelper(filterAdapter)).attachToRecyclerView(filterRecyclerView)
        filterAdapter.delegate = object :
            FilterAdapter.Delegate {
            override fun onSwipeDismiss(position: Int) {
                val item = filterAdapter.filters[position]
                viewModel.removeFilter(item)
                Snackbar.make(
                    requireParentFragment().requireView(),
                    getString(R.string.RemoveFilter, item),
                    Snackbar.LENGTH_SHORT
                )
                    .setAction(android.R.string.cancel) {
                        viewModel.addFilter(item)
                    }
                    .show()
            }
        }

        fab.setOnClickListener {
            FilterAddBottomSheetFragment()
                .apply {
                    delegate = object : FilterAddBottomSheetFragment.Delegate {
                        override fun onFinished(filter: String) {
                            viewModel.addFilter(filter)
                        }
                    }
                }.also {
                    it.showNow(childFragmentManager, FilterAddBottomSheetFragment::class.simpleName)
                }
        }

        filterOverlapLayout.setOnClickListener { }

        viewModel.application.observe(viewLifecycleOwner, Observer {
            iconImageView.setImageDrawable(it.icon)
            titleTextView.text = it.name
            descriptionTextView.text = it.packageName
        })

        viewModel.alarm.observe(viewLifecycleOwner, Observer {
            it.alarm.let {
                alarmSwitch.setChecked(it.isAlarm, alarmSwitchOnCheckedChangeListener)
                filterSwitch.setChecked(it.isFilter, filterSwitchOnCheckedChangeListener)

                optionalLayout.isInvisible = !it.isAlarm
                fab.isInvisible = !(it.isAlarm && it.isFilter)

                filterRecyclerView.alpha = if (it.isFilter) 1.0f else 0.5f
                filterOverlapLayout.isVisible = !it.isFilter
            }
            updateFilterAdapter(it.filters.map { it.filter }.toMutableList())
        })

        viewModel.errorNotFound.observe(viewLifecycleOwner, Observer {
            Snackbar.make(
                requireParentFragment().requireView(),
                getString(R.string.ErrorText),
                Snackbar.LENGTH_SHORT
            )
                .show()
            onBackPressed()
        })
    }

    private fun updateFilterAdapter(list: List<String>) {
        val contactDiffUtilCallback =
            FilterAdapter.FilterDiffUtilCallback(
                filterAdapter.filters.toList(),
                list
            )
        val diffResult = DiffUtil.calculateDiff(contactDiffUtilCallback)

        filterAdapter.filters = list.toMutableList()
        diffResult.dispatchUpdatesTo(filterAdapter)
    }
}
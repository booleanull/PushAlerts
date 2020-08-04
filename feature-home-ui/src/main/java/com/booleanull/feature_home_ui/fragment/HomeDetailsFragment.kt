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
import androidx.recyclerview.widget.ItemTouchHelper
import com.booleanull.core_ui.adapter.GenericAdapter
import com.booleanull.core_ui.adapter.GenericItemDiff
import com.booleanull.core_ui.base.BaseFragment
import com.booleanull.core_ui.dp
import com.booleanull.core_ui.getAttributeColor
import com.booleanull.core_ui.helper.DismissItemTouchHelper
import com.booleanull.core_ui.helper.RecyclerDivider
import com.booleanull.core_ui.setChecked
import com.booleanull.feature_home_ui.R
import com.booleanull.feature_home_ui.adapter.FilterViewHolderFactory
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
            GenericAdapter(FilterViewHolderFactory()).apply {
                setOnItemDismissListener(object : DismissItemTouchHelper.OnItemDismissListener {
                    override fun onItemDismiss(position: Int) {
                        val item = this@apply.dataList[position]
                        viewModel.removeFilter(item)
                        Snackbar.make(
                            requireParentFragment().requireView(),
                            getString(R.string.remove_filter, item),
                            Snackbar.LENGTH_SHORT
                        )
                            .setAction(android.R.string.cancel) {
                                viewModel.addFilter(item)
                            }
                            .show()
                    }
                })
                setDiffUtil(object : GenericItemDiff<String> {
                    override fun areItemsTheSame(
                        oldItems: List<String>,
                        newItems: List<String>,
                        oldItemPosition: Int,
                        newItemPosition: Int
                    ): Boolean {
                        val old = oldItems[oldItemPosition]
                        val new = newItems[newItemPosition]
                        return old == new
                    }

                    override fun areContentsTheSame(
                        oldItems: List<String>,
                        newItems: List<String>,
                        oldItemPosition: Int,
                        newItemPosition: Int
                    ): Boolean {
                        val old = oldItems[oldItemPosition]
                        val new = newItems[newItemPosition]
                        return old == new
                    }
                })
            }
    }

    private val filterItemDecoration by lazy {
        RecyclerDivider(
            line = RecyclerDivider.Line(
                0, 0, dp(1), requireContext().getAttributeColor(
                    R.attr.colorDivider,
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.colorGray
                    )
                )
            )
        )
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
        filterRecyclerView.addItemDecoration(filterItemDecoration)
        ItemTouchHelper(
            DismissItemTouchHelper(
                ItemTouchHelper.END,
                filterAdapter
            )
        ).attachToRecyclerView(filterRecyclerView)

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
            filterAdapter.dataList = it.filters.map { it.filter }.sorted().toMutableList()
        })

        viewModel.errorNotFound.observe(viewLifecycleOwner, Observer {
            Snackbar.make(
                requireParentFragment().requireView(),
                getString(R.string.simple_error_text),
                Snackbar.LENGTH_SHORT
            )
                .show()
            onBackPressed()
        })
    }
}
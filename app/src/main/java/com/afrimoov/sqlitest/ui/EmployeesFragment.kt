package com.afrimoov.sqlitest.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import com.afrimoov.sqlitest.R
import com.afrimoov.sqlitest.api.ApiRequest
import com.afrimoov.sqlitest.databinding.FragmentEmployeesBinding
import com.afrimoov.sqlitest.models.ListItem
import com.afrimoov.sqlitest.utils.EmployeesAdapter
import com.afrimoov.sqlitest.utils.ResultStatus
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EmployeesFragment : Fragment(), EmployeesAdapter.FooterViewHolder.OnFooterClickListener,
    EmployeesAdapter.EmployeesViewHolder.OnEmployeeClickListener {

    private val binding : FragmentEmployeesBinding by lazy {
        FragmentEmployeesBinding.inflate(layoutInflater)
    }

    private val recyclerView : RecyclerView by lazy {
        binding.listEmployees
    }

    private val loadingView : View by lazy {
        binding.progressBar
    }

    private val errorView : View by lazy {
        binding.textError
    }

    private val apiRequest = ApiRequest()

    private val viewModel : EmployeesViewModel by viewModels()

    private val adapter : EmployeesAdapter by lazy {
        EmployeesAdapter(this, this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        recyclerView.addItemDecoration(DividerItemDecoration(context, VERTICAL))
        recyclerView.adapter = adapter

        activity?.title = getString(R.string.app_name)

        viewModel.employeesList.observe(viewLifecycleOwner){
            when(it){
                is ResultStatus.Success -> {
                    if (adapter.employees.any { item -> item is ListItem.Footer }){
                        adapter.removeItem(adapter.itemCount-1)
                    }
                    val apiResponse = it.data
                    viewModel.mItems.addAll(apiResponse.data.filterNot { item -> viewModel.mItems.any { item2 -> item.id == item2.id } })

                    val itemsToShow : MutableList<ListItem> = mutableListOf()
                    itemsToShow.add(ListItem.Header(getString(R.string.employees_header, viewModel.mItems.size, apiResponse.total)))
                    itemsToShow.addAll(viewModel.mItems)

                    if (apiResponse.total > viewModel.mItems.size){
                        itemsToShow.add(ListItem.Footer(false))
                    }

                    adapter.addData(itemsToShow)

                    loadingView.visibility = GONE
                    recyclerView.visibility = VISIBLE
                }

                is ResultStatus.Loading -> {
                    loadingView.visibility = VISIBLE
                    recyclerView.visibility = GONE
                }

                else -> {
                    loadingView.visibility = GONE
                    errorView.visibility = VISIBLE
                }
            }
        }
        if (viewModel.firstLoad){
            viewModel.getListEmployees(apiRequest)
            viewModel.firstLoad = false
        }
    }

    override fun onFooterClick() {
        apiRequest.page += 1
        adapter.removeItem(adapter.itemCount-1)
        adapter.notifyItemChanged(adapter.itemCount-1)
        adapter.addItem(item = ListItem.Footer(true))
        viewModel.getListEmployees(apiRequest, false)
    }

    override fun onEmployeeClick(employee: ListItem.Employee, position: Int) {
        findNavController().navigate(R.id.details_nav, Bundle().apply {
            putParcelable(EMPLOYEE_KEY, employee)
        })
    }

}
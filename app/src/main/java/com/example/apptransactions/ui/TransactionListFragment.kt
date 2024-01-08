package com.example.apptransactions.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apptransactions.databinding.FragmentTransactionListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransactionListFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels({ requireActivity() })

    private var _binding: FragmentTransactionListBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =
            FragmentTransactionListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val (searchView, adapter) = initBindingComponents()

        viewModel.getAllTransactions()
        viewModel.transactions.observe(viewLifecycleOwner) { transactions ->
            adapter.submitList(transactions)
        }
        setOnQueryTextListener(searchView)
        viewModel.searchResult.observe(viewLifecycleOwner) { result ->
            adapter.submitList(result?.let { listOf(it) } ?: emptyList())
        }
    }

    private fun setOnQueryTextListener(searchView: SearchView) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { viewModel.searchTransactionByReceiptId(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrBlank()) {
                    viewModel.clearSearch()
                    viewModel.getAllTransactions()
                } else {
                    newText?.let { viewModel.searchTransactionByReceiptId(it) }
                }
                return true
            }
        })
    }

    private fun initBindingComponents(): Pair<SearchView, TransactionAdapter> {
        val recyclerView = binding.recyclerViewTransactions
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val searchView = binding.searchView
        val adapter = TransactionAdapter { transaction ->
            viewModel.transactionClick = transaction
            val action =
                TransactionListFragmentDirections.actionTransactionListFragmentToTransactionDetailFragment()
            findNavController().navigate(action)

        }
        recyclerView.adapter = adapter
        return Pair(searchView, adapter)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        viewModel.clearSearch()
        viewModel.getAllTransactions()
    }
}
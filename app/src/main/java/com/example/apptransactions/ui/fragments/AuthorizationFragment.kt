package com.example.apptransactions.ui.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.apptransactions.R
import com.example.apptransactions.data.model.TransactionModel
import com.example.apptransactions.databinding.FragmentAuthorizationBinding
import com.example.apptransactions.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthorizationFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels({ requireActivity() })

    private var _binding: FragmentAuthorizationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthorizationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        seOnclickListenerButtons()
        isShowModalObserver()
    }

    private fun seOnclickListenerButtons() {
        binding.btnAuthorize.setOnClickListener {
            val commerceCode = binding.editCommerceCode.text.toString()
            val terminalCode = binding.editTerminalCode.text.toString()
            val amount = binding.editAmount.text.toString()
            val cardNumber = binding.editCardNumber.text.toString()

            val transaction = TransactionModel(
                id = "001",
                commerceCode = commerceCode,
                terminalCode = terminalCode,
                amount = amount,
                card = cardNumber
            )
            viewModel.authorizeTransaction(transaction)
        }

        binding.btnList.setOnClickListener {
            findNavController().navigate(R.id.action_authorizationFragment_to_transactionListFragment)
        }
    }

    private fun isShowModalObserver() {
        viewModel.isShow.observe(viewLifecycleOwner, Observer { shouldShow ->
            viewModel.message.observe(viewLifecycleOwner, Observer { message ->
                if (shouldShow == true) {
                    AlertDialog.Builder(requireContext())
                        .setMessage(message)
                        .setPositiveButton("Aceptar") { dialog, _ ->
                            dialog.dismiss()
                        }
                        .show()
                } else {
                    AlertDialog.Builder(requireContext())
                        .setMessage("La transacción se autorizó correctamente")
                        .setPositiveButton("Aceptar") { dialog, _ ->
                            dialog.dismiss()
                        }
                        .show()
                    cleanTextView()
                }
            })

        })
    }

    private fun cleanTextView() {
        binding.editAmount.text.clear()
        binding.editCommerceCode.text.clear()
        binding.editTerminalCode.text.clear()
        binding.editCardNumber.text.clear()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package com.example.apptransactions.ui


import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.example.apptransactions.data.model.TransactionResponse
import com.example.apptransactions.databinding.FragmentTransactionDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransactionDetailFragment : Fragment() {

    private var _binding: FragmentTransactionDetailBinding? = null
    private val viewModel: MainViewModel by viewModels({ requireActivity() })
    private var transactionClick: TransactionResponse? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTransactionDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        transactionClick = viewModel.transactionClick
        initView()
        setOnClickButtonListener()
    }

    private fun initView() {
        binding.tvTransactionDetails.text =
            "Transaccion Detalles:\n${transactionClick?.receiptId} " +
                    " ${transactionClick?.rrn} \n${transactionClick?.statusCode}" +
                    " \n${transactionClick?.statusDescription}\n "
    }

    private fun setOnClickButtonListener() {
        binding.btnCancelTransaction.setOnClickListener {
            if (transactionClick?.statusDescription != "Anulada") {
                showCancelConfirmationDialog()
            } else {
                showAlreadyCancelledDialog()
            }
        }
    }

    private fun showCancelConfirmationDialog() {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.setTitle("Confirmación")
        alertDialogBuilder.setMessage("¿Estás seguro de que quieres anular esta transacción?")
        alertDialogBuilder.setPositiveButton("Sí") { _, _ ->
            viewModel.cancelTransaction(transactionClick!!)
            binding.btnCancelTransaction.isEnabled = false
            binding.btnCancelTransaction.isClickable = false
            setStateTransaccion()
            showSuccessDialog("La transacción se ha anulado correctamente.")
        }
        alertDialogBuilder.setNegativeButton("No", null)
        alertDialogBuilder.show()
    }

    private fun setStateTransaccion() {
        binding.tvTransactionDetails.text =
            "Transaccion Detalles:\n${transactionClick?.receiptId} " +
                    " ${transactionClick?.rrn} \n${transactionClick?.statusCode}" +
                    " \nAnulada\n "
    }

    private fun showAlreadyCancelledDialog() {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.setTitle("Advertencia")
        alertDialogBuilder.setMessage("Esta transacción ya ha sido anulada.")
        alertDialogBuilder.setPositiveButton("Aceptar", null)
        alertDialogBuilder.show()
    }

    private fun showSuccessDialog(message: String) {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.setTitle("Éxito")
        alertDialogBuilder.setMessage(message)
        alertDialogBuilder.setPositiveButton("Aceptar", null)
        alertDialogBuilder.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
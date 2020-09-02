package com.parseus.codecinfo.fragments

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.core.app.ShareCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.parseus.codecinfo.MainActivity
import com.parseus.codecinfo.R
import com.parseus.codecinfo.adapters.CodecInfoAdapter
import com.parseus.codecinfo.codecinfo.getDetailedCodecInfo
import com.parseus.codecinfo.databinding.CodecDetailsFragmentLayoutBinding
import com.parseus.codecinfo.settings.SettingsActivity

class CodecDetailsDialogFragment : DialogFragment() {

    private var _binding: CodecDetailsFragmentLayoutBinding? = null
    private val binding get() = _binding!!

    private var dismissDialog = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = CodecDetailsFragmentLayoutBinding.inflate(inflater, container, false)
        val view = binding.root

        if (binding.dialogToolbar == null) {
            dismissDialog = true
            return null
        }

        binding.dialogToolbar!!.title = requireContext().getString(R.string.codec_details)
        (requireActivity() as MainActivity).setSupportActionBar(binding.dialogToolbar)
        (requireActivity() as MainActivity).supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_close)
            setHomeActionContentDescription(R.string.close_details)
        }

        setHasOptionsMenu(true)

        return view
    }

    override fun onResume() {
        super.onResume()

        if (dismissDialog) {
            requireActivity().supportFragmentManager.popBackStack()
            dismiss()
            return
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val codecId = requireArguments().getString("codecId")
        val codecName = requireArguments().getString("codecName")
        val codecInfoMap = getDetailedCodecInfo(requireContext(), codecId!!, codecName!!)
        val codecAdapter = CodecInfoAdapter(codecInfoMap)

        binding.fullCodecInfoName.text = codecName
        binding.fullCodecInfoContent.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = codecAdapter
            ViewCompat.setNestedScrollingEnabled(this, false)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.fragment_bar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    @SuppressLint("InflateParams")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            android.R.id.home -> dismiss()
            R.id.fragment_menu_item_share -> {
                val codecId = requireArguments().getString("codecId")
                val codecName = requireArguments().getString("codecName")
                val header = "${requireContext().getString(R.string.codec_details)}: $codecName\n\n"
                val codecStringBuilder = StringBuilder(header)
                val codecInfoMap = getDetailedCodecInfo(requireContext(), codecId!!, codecName!!)

                for (key in codecInfoMap.keys) {
                    codecStringBuilder.append("$key\n${codecInfoMap[key]}\n\n")
                }

                ShareCompat.IntentBuilder.from(requireActivity()).setType("text/plain")
                        .setText(codecStringBuilder.toString()).startChooser()
            }
            R.id.menu_item_settings -> startActivity(Intent(requireActivity(), SettingsActivity::class.java))
        }

        return true
    }

}
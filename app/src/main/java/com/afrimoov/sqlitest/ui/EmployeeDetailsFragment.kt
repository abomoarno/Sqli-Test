package com.afrimoov.sqlitest.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.HtmlCompat
import com.afrimoov.sqlitest.R
import com.afrimoov.sqlitest.databinding.FragmentEmployeeDetailsBinding
import com.afrimoov.sqlitest.models.ListItem
import com.squareup.picasso.Picasso

const val EMPLOYEE_KEY = "employee"

class EmployeeDetailsFragment : Fragment() {

    private val binding : FragmentEmployeeDetailsBinding by lazy {
        FragmentEmployeeDetailsBinding.inflate(layoutInflater)
    }

    private val firstName : TextView by lazy {
        binding.firstName
    }

    private val lastName : TextView by lazy {
        binding.lastName
    }

    private val email : TextView by lazy {
        binding.email
    }

    private val avatar : ImageView by lazy {
        binding.avatar
    }

    private var employee: ListItem.Employee? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            employee = it.getParcelable(EMPLOYEE_KEY)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        firstName.text = HtmlCompat.fromHtml(getString(R.string.first_name, employee?.firstName),
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )
        lastName.text = HtmlCompat.fromHtml(getString(R.string.last_name, employee?.lastName),
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )
        email.text = HtmlCompat.fromHtml(getString(R.string.email, employee?.email),
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )

        activity?.title = String.format("%s %s", employee?.firstName, employee?.lastName)

        Picasso.get().load(employee?.avatar).into(avatar)
    }
}
package com.afrimoov.sqlitest.utils

import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.OnClickListener
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_LEGACY
import androidx.recyclerview.widget.RecyclerView
import com.afrimoov.sqlitest.R
import com.afrimoov.sqlitest.models.ListItem
import com.squareup.picasso.Picasso

private const val VIEW_TYPE_HEADER = 0
private const val VIEW_TYPE_EMPLOYEE = 1
private const val VIEW_TYPE_FOOTER = 2

class EmployeesAdapter(
    private val footerClickListener: FooterViewHolder.OnFooterClickListener,
    private val employeeClickListener: EmployeesViewHolder.OnEmployeeClickListener
) : RecyclerView.Adapter<EmployeesAdapter.ListItemViewHolder>() {

    val employees : MutableList<ListItem> = mutableListOf()

    fun addData(items : List<ListItem>){
        employees.clear()
        employees.addAll(items)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int){
        employees.removeAt(position)
        notifyItemRemoved(position)
    }

    fun addItem(position: Int = itemCount, item:ListItem){
        employees.add(position, item)
        notifyItemInserted(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemViewHolder {

        when(viewType){

            VIEW_TYPE_HEADER -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.header_list_item, parent, false)
                return HeaderViewHolder(view)
            }

            VIEW_TYPE_FOOTER -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.footer_list_item, parent, false)
                return FooterViewHolder(view, footerClickListener)
            }

            VIEW_TYPE_EMPLOYEE -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.employee_list_item, parent, false)
                return EmployeesViewHolder(view, employeeClickListener)
            }

            else -> throw IllegalArgumentException("Unknown view type requested: $viewType")
        }
    }

    override fun onBindViewHolder(holder: ListItemViewHolder, position: Int) {
        holder.bindData(employees[position])
    }

    override fun getItemCount(): Int {
        return employees.size
    }

    override fun getItemViewType(position: Int): Int {
        return when(employees[position]){
            is ListItem.Header -> VIEW_TYPE_HEADER
            is ListItem.Footer -> VIEW_TYPE_FOOTER
            is ListItem.Employee -> VIEW_TYPE_EMPLOYEE
        }
    }

    class EmployeesViewHolder(itemView : View, private val listener : OnEmployeeClickListener) : ListItemViewHolder(itemView){

        private val firstName : TextView by lazy {
            itemView.findViewById(R.id.first_name)
        }

        private val lastName : TextView by lazy {
            itemView.findViewById(R.id.last_name)
        }

        private val email : TextView by lazy {
            itemView.findViewById(R.id.email)
        }

        private val avatar : ImageView by lazy {
            itemView.findViewById(R.id.avatar)
        }

        override fun bindData(listItem : ListItem){

            require(listItem is ListItem.Employee)

            firstName.text = HtmlCompat.fromHtml(itemView.context.getString(R.string.first_name, listItem.firstName), FROM_HTML_MODE_LEGACY)
            lastName.text = HtmlCompat.fromHtml(itemView.context.getString(R.string.last_name, listItem.lastName), FROM_HTML_MODE_LEGACY)
            email.text = HtmlCompat.fromHtml(itemView.context.getString(R.string.email, listItem.email), FROM_HTML_MODE_LEGACY)

            Picasso.get().load(listItem.avatar).into(avatar)

            itemView.setOnClickListener{
                listener.onEmployeeClick(listItem, absoluteAdapterPosition)
            }
        }

        interface OnEmployeeClickListener{
            fun onEmployeeClick(employee : ListItem.Employee, position : Int)
        }

    }

    class HeaderViewHolder(itemView: View) : ListItemViewHolder(itemView){

        private val title : TextView by lazy {
            itemView.findViewById(R.id.title)
        }

        override fun bindData(listItem: ListItem) {
            require(listItem is ListItem.Header)
            title.text = listItem.text
        }
    }

    class FooterViewHolder(itemView: View, var listener : OnFooterClickListener) : ListItemViewHolder(itemView){

        private val title : TextView by lazy {
            itemView.findViewById(R.id.title)
        }

        private val progressBar : View by lazy {
            itemView.findViewById(R.id.progress_bar)
        }

        override fun bindData(listItem: ListItem) {
            require(listItem is ListItem.Footer)

            title.visibility = if (listItem.loading) GONE else VISIBLE
            progressBar.visibility = if (listItem.loading) VISIBLE else GONE

            title.setOnClickListener {
                listener.onFooterClick()
            }

        }

        interface OnFooterClickListener{
            fun onFooterClick()
        }
    }

    abstract class ListItemViewHolder(
        containerView: View
    ) : RecyclerView.ViewHolder(containerView) {
        abstract fun bindData(listItem: ListItem)
    }
}
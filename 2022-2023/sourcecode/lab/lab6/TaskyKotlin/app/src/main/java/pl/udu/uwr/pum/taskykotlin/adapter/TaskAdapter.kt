package pl.udu.uwr.pum.taskykotlin.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pl.udu.uwr.pum.taskykotlin.databinding.ItemGroupRowBinding
import pl.udu.uwr.pum.taskykotlin.databinding.ItemTaskRowBinding
import pl.udu.uwr.pum.taskykotlin.model.Task
import pl.udu.uwr.pum.taskykotlin.model.TaskRow
import pl.udu.uwr.pum.taskykotlin.util.saveTaskList

class TaskAdapter(private val tasksList: MutableList<Task>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val groupedList = tasksList.groupBy { it.type }.flatMap {
        listOf(TaskRow.Header(it.key.name), *(it.value.map { task ->
            (TaskRow.Task(task.name))
        }).toTypedArray())
    }.toMutableList()

    fun add(task: Task, context: Context){
        tasksList.add(task)
        saveTaskList(context, tasksList)
        val header = groupedList
            .filterIsInstance<TaskRow.Header>()
            .find { it.name == task.type.name }
        if (header == null)
            groupedList.apply {
                add(TaskRow.Header(task.type.name))
                add(TaskRow.Task(task.name))
                notifyItemInserted(groupedList.size)
            }
        else{
            val i = groupedList.indexOf(TaskRow.Header(task.type.name))
            groupedList.add(i + subList(task.type.name).size, TaskRow.Task(task.name))
            notifyItemInserted(i + subList(task.type.name).size)
        }
    }

    fun clear(context: Context){
        tasksList.clear()
        notifyItemRangeRemoved(0, groupedList.size)
        groupedList.clear()
        saveTaskList(context, tasksList)
    }


    private class TaskViewHolder(private val itemBinding: ItemTaskRowBinding)
        : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(task: TaskRow.Task) {
            itemBinding.taskTextView.text = task.name
        }
    }

    private class HeaderViewHolder(private val itemBinding: ItemGroupRowBinding)
        : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(header: TaskRow.Header) {
            itemBinding.groupTextView.text = header.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TaskRow.TYPE_TASK -> TaskViewHolder(
                ItemTaskRowBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false)
            )
            TaskRow.TYPE_HEADER -> HeaderViewHolder(
                ItemGroupRowBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false)
            )
            else -> throw IllegalArgumentException("Wrong row type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val taskRow = groupedList[position]) {
            is TaskRow.Task -> (holder as TaskViewHolder).bind(taskRow)
            is TaskRow.Header -> (holder as HeaderViewHolder).bind(taskRow)
        }
    }

    override fun getItemCount(): Int = groupedList.size

    override fun getItemViewType(position: Int): Int = groupedList[position].rowType

    private fun subList(groupName: String): List<TaskRow>{
        return tasksList
            .filter { it.type.name == groupName }
            .map {TaskRow.Task(it.name)}
    }
}
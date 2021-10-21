package com.github.zexuz.jetbrainscodecommitplugin.toolWindow

import com.amazonaws.services.codecommit.AWSCodeCommitClientBuilder
import com.amazonaws.services.codecommit.model.ListRepositoriesRequest
import com.intellij.diff.DiffContentFactory
import com.intellij.diff.DiffContext
import com.intellij.diff.DiffManager
import com.intellij.diff.contents.DocumentContent
import com.intellij.diff.requests.DiffRequest
import com.intellij.diff.requests.SimpleDiffRequest
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.project.ProjectManager
import com.intellij.openapi.vcs.actions.VcsContextFactory
import com.intellij.openapi.wm.ToolWindow
import com.intellij.tasks.context.BranchContextTracker
import java.awt.event.ActionEvent
import java.util.*
import javax.swing.ImageIcon
import javax.swing.JButton
import javax.swing.JLabel
import javax.swing.JPanel

class MyToolWindow(toolWindow: ToolWindow) {
    private var refreshToolWindowButton: JButton? = null
    private var hideToolWindowButton: JButton? = null
    private var diffToolWindowButton: JButton? = null
    private var currentDate: JLabel? = null
    private var currentTime: JLabel? = null
    private var timeZone: JLabel? = null
    var content: JPanel? = null

    private val log: Logger = Logger.getInstance(MyToolWindow::class.java)

    fun currentDateTime() {
        // Get current date and time
        val instance = Calendar.getInstance()
        currentDate!!.text = (instance[Calendar.DAY_OF_MONTH].toString() + "/"
                + (instance[Calendar.MONTH] + 1) + "/"
                + instance[Calendar.YEAR])
        currentDate!!.icon = ImageIcon(javaClass.getResource("/toolWindow/Calendar-icon.png"))
        val min = instance[Calendar.MINUTE]
        val strMin = if (min < 10) "0$min" else min.toString()
        currentTime!!.text = instance[Calendar.HOUR_OF_DAY].toString() + ":" + strMin
        currentTime!!.icon = ImageIcon(javaClass.getResource("/toolWindow/Time-icon.png"))
        // Get time zone
        val gmt_Offset = instance[Calendar.ZONE_OFFSET].toLong() // offset from GMT in milliseconds
        var str_gmt_Offset = (gmt_Offset / 3600000).toString()
        str_gmt_Offset = if (gmt_Offset > 0) "GMT + $str_gmt_Offset" else "GMT - $str_gmt_Offset"
        timeZone!!.text = str_gmt_Offset
        timeZone!!.icon = ImageIcon(javaClass.getResource("/toolWindow/Time-zone-icon.png"))

        log.info("This is a debug message")

        val client = AWSCodeCommitClientBuilder.defaultClient()
        val repos = client.listRepositories(ListRepositoriesRequest());
        log.info("Lenght is ${repos.repositories.size}")
        repos.repositories.forEach {
            log.info(it.repositoryName)
        }

    }

    init {
        hideToolWindowButton!!.addActionListener { e: ActionEvent? -> toolWindow.hide(null) }
        refreshToolWindowButton!!.addActionListener { e: ActionEvent? -> currentDateTime() }
        diffToolWindowButton!!.addActionListener { e: ActionEvent? -> showDiffWindow() }
        currentDateTime()
    }

    private fun showDiffWindow() {
        com.intellij.openapi.vcs.diff.ItemLatestState().
        val project = ProjectManager.getInstance().openProjects.first()
        val content1: DocumentContent = DiffContentFactory.getInstance().create("text1")
        val content2: DocumentContent = DiffContentFactory.getInstance().create("text2")
        val request = SimpleDiffRequest("Window Title", content1, content2, "Title 1", "Title 2")
        DiffManager.getInstance().showDiff(project, request)
    }
}

class Test : com.intellij.diff.DiffTool{
    override fun getName(): String {
        TODO("Not yet implemented")
    }

    override fun canShow(context: DiffContext, request: DiffRequest): Boolean {
        TODO("Not yet implemented")
    }

}
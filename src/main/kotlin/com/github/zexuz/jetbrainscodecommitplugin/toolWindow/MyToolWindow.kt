package com.github.zexuz.jetbrainscodecommitplugin.toolWindow

import com.intellij.openapi.wm.ToolWindow
import javax.swing.JButton
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.ImageIcon
import java.awt.event.ActionListener
import java.awt.event.ActionEvent
import java.util.*

class MyToolWindow(toolWindow: ToolWindow) {
    private var refreshToolWindowButton: JButton? = null
    private var hideToolWindowButton: JButton? = null
    private var currentDate: JLabel? = null
    private var currentTime: JLabel? = null
    private var timeZone: JLabel? = null
    var content: JPanel? = null

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
    }

    init {
        hideToolWindowButton!!.addActionListener { e: ActionEvent? -> toolWindow.hide(null) }
        refreshToolWindowButton!!.addActionListener { e: ActionEvent? -> currentDateTime() }
        currentDateTime()
    }
}
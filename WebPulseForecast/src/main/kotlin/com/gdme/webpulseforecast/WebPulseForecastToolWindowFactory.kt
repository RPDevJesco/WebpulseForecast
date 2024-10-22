package com.gdme.webpulseforecast

import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.ContentFactory
import javax.swing.*
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.FlowLayout

class WebPulseForecastToolWindowFactory : ToolWindowFactory {
    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val contentFactory = ContentFactory.getInstance()
        val content = contentFactory.createContent(createToolWindowPanel(project), "", false)
        toolWindow.contentManager.addContent(content)
    }

    private fun createToolWindowPanel(project: Project): JPanel {
        val panel = JPanel(BorderLayout())
        panel.add(JLabel("WebPulse Forecast Analysis Results"), BorderLayout.NORTH)

        // Create a panel for the button with FlowLayout
        val buttonPanel = JPanel(FlowLayout(FlowLayout.CENTER))
        val analyzeButton = JButton("Analyze Project")
        analyzeButton.preferredSize = Dimension(150, 30)

        buttonPanel.add(analyzeButton)
        panel.add(buttonPanel, BorderLayout.NORTH)

        // Create split panel to split the project type table and the resource usage section
        val splitPane = JSplitPane(JSplitPane.VERTICAL_SPLIT)
        splitPane.resizeWeight = 0.5 // Adjust this to control how much space each panel takes

        // Create the result panel for project type details
        val projectPanel = JPanel()
        projectPanel.layout = BoxLayout(projectPanel, BoxLayout.Y_AXIS)

        // Create the resource panel for resource usage and performance impact
        val resourcePanel = JPanel()
        resourcePanel.layout = BoxLayout(resourcePanel, BoxLayout.Y_AXIS)

        // Add the projectPanel and resourcePanel to the splitPane
        splitPane.topComponent = JScrollPane(projectPanel)
        splitPane.bottomComponent = JScrollPane(resourcePanel)

        // Add the splitPane to the main panel
        panel.add(splitPane, BorderLayout.CENTER)

        // Action listener for the button
        analyzeButton.addActionListener {
            val service = project.service<WebPulseForecastService>()

            // Analyze project type and estimate resources
            val projectType = service.analyzeProject()
            val resourceEstimation = service.estimateResources(projectType)
            // Calculate performance impact
            val performanceImpact = service.calculatePerformanceImpact(projectType)
            val potentialIssues = service.getPotentialIssues(projectType);
            val formattedPotentialIssues = potentialIssues.joinToString(separator = "\n")
            projectPanel.removeAll()

            projectPanel.removeAll()
            resourcePanel.removeAll()

            // Display project type in a JTable
            val projectData = arrayOf(
                arrayOf("Framework", projectType.framework),
                arrayOf("HTML Files", projectType.htmlFileCount.toString()),
                arrayOf("CSS Files", projectType.cssFileCount.toString()),
                arrayOf("JS Files", projectType.jsFileCount.toString()),
                arrayOf("JSON Files", projectType.jsonFileCount.toString()),
                arrayOf("Image Files", projectType.imageFileCount.toString())
            )
            val columnNames = arrayOf("Metric", "Value")
            val table = JTable(projectData, columnNames)
            projectPanel.add(JScrollPane(table))

            projectPanel.revalidate()
            projectPanel.repaint()

            // Display resource estimation
            resourcePanel.add(JLabel("Resource Estimation:"))
            resourcePanel.add(JLabel(service.getResourceUsageDisplay(resourceEstimation)))

            // Display performance impact
            resourcePanel.add(JLabel("Performance Impact: %.2f".format(performanceImpact)))

            resourcePanel.revalidate()
            resourcePanel.repaint()
        }

        return panel
    }
}
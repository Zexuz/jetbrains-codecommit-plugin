package com.github.zexuz.jetbrainscodecommitplugin.services

import com.intellij.openapi.project.Project
import com.github.zexuz.jetbrainscodecommitplugin.MyBundle

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}

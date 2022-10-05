package com.nisus.refactoringsheet.common

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

abstract class BaseRsAction : AnAction(), ReferencedAction {


    override fun actionPerformed(e: AnActionEvent) {
        if (e.presentation.isEnabled) {
            gotoReference()
        }
    }


}
package media.pixi.appkit.ui.chat.actions

import com.leinardi.android.speeddial.SpeedDialActionItem
import com.leinardi.android.speeddial.SpeedDialView

object ActionUtils {

    fun setActions(speedDialView: SpeedDialView, actions: List<MessageAction>) {
        clearActions(speedDialView)
        addActions(speedDialView, actions)
    }

    fun clearActions(speedDialView: SpeedDialView) {
        for (item in speedDialView.actionItems) {
            speedDialView.removeActionItem(item)
        }
    }

    fun addActions(speedDialView: SpeedDialView, actions: List<MessageAction>) {
        for (action in actions) {
            speedDialView.addActionItem(
                SpeedDialActionItem.Builder(action.type.ordinal, action.iconResourceId)
                    .setLabel(action.titleResourceId)
                    .create()
            )
        }
    }
}
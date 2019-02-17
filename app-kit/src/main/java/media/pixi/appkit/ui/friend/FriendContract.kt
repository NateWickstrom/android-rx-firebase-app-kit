package media.pixi.appkit.ui.friend

interface FriendContract {

    interface View {
        var userImageUrl: String
        var username: String
        var firstName: String
        var lastName: String
    }

    interface Presenter {
        var userId: String?

        fun takeView(view: View)
        fun dropView()
    }

    interface Navigator {

    }
}
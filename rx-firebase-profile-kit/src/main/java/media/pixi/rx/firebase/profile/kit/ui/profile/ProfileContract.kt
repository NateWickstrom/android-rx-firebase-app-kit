package media.pixi.rx.firebase.profile.kit.ui.profile

interface ProfileContract {

    interface View {

    }

    interface Presenter {
        fun takeView(view: View)
        fun dropView()
    }

    interface Navigator {

    }
}
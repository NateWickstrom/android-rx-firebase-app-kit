package media.pixi.appkit.ui

interface BasePresenter<T> {

    /**
     * Binds listener with a view when resumed. The Presenter will perform initialization here.
     *
     * @param view the view associated with this listener
     */
    fun takeView(view: T)

    /**
     * Drops the reference to the view when destroyed
     */
    fun dropView()

}

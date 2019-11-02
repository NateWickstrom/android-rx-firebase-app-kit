package media.pixi.appkit.ui.chat.options;


public class LocationChatOption extends BaseChatOption {

    public LocationChatOption(String title, Integer iconResourceId, Action action) {
        super(title, iconResourceId, action);
    }

//    public LocationChatOption(String title, Integer iconResourceId) {
//        super(title, iconResourceId, (activity, thread) -> {
//            return new LocationSelector().startChooseLocationActivity(activity)
//                    .flatMapCompletable(result -> ChatSDK.locationMessage()
//                            .sendMessageWithLocation(result.snapshotPath, result.latLng, thread));
//        });
//    }
//
//    public LocationChatOption(String title) {
//        this(title, null);
//    }


}
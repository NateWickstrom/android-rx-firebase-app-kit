package media.pixi.appkit.ui.chat.textinput;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;

import java.lang.ref.WeakReference;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import media.pixi.appkit.R;
import media.pixi.appkit.data.audio.Recording;
import media.pixi.appkit.domain.chats.models.MessageAttachment;
import media.pixi.appkit.utils.InfiniteToast;
import media.pixi.appkit.utils.ToastHelper;

public class TextInputView extends LinearLayout implements TextView.OnEditorActionListener {

    private static final int AUIDO_MESSAGE_LENGTH_MAX = 300;

    protected ImageButton btnSend;
    protected ImageButton btnOptions;
    protected TextInputEditText etMessage;
    protected boolean audioModeEnabled = false;
    protected boolean recordOnPress = false;
    protected Recording recording = null;
    protected InfiniteToast toast;
    protected WeakReference<TextInputListener> delegate;
    protected Rect rect;
    protected Date recordingStart;
    protected Disposable toastUpdateDisposable;
    protected boolean audioMaxLengthReached = false;
    protected RecyclerView recyclerView;
    protected AttachmentAdapter attachmentAdapter;
    protected View attachments;

    public TextInputView(Context context) {
        super(context);
        init();
    }

    public TextInputView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TextInputView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public TextInputView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void setListener(TextInputListener delegate) {
        this.delegate = new WeakReference<>(delegate);
    }

    protected void init() {
        inflate(getContext(), R.layout.view_chat_text_input, this);
    }

    protected void initViews() {
        btnSend = findViewById(R.id.button_send);
        btnOptions = findViewById(R.id.button_options);
        etMessage = findViewById(R.id.text_input_message);
        attachments = findViewById(R.id.attachments);
        recyclerView = findViewById(R.id.attachments);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        updateSendButton();
    }

    protected Activity getActivity() {
        Context context = getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity)context;
            }
            context = ((ContextWrapper)context).getBaseContext();
        }
        return null;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initViews();


        if (isInEditMode()) {
            return;
        }

        btnSend.setOnClickListener(view -> {
            if(!recordOnPress) {
                if (delegate != null) {
                    delegate.get().onSendPressed(getMessageText());
                }
            }
        });

        // Handle recording when the record button is held down
//        btnSend.setOnTouchListener((view, motionEvent) ->  {
//            if(recordOnPress) {
//                Disposable d = PermissionRequestHandler.shared().requestRecordAudio(getActivity()).subscribe(() -> {
//                        // Start recording when we press down
//                        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
//                            startRecording(view);
//                        }
//
//                        // Stop recording
//                        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
//                            stopRecording(view, motionEvent);
//                        }
//                }, throwable -> ToastHelper.show(getContext(), throwable.getLocalizedMessage()));
//            }
//            return btnSend.onTouchEvent(motionEvent);
//        });

        btnOptions.setOnClickListener(view -> showOption());

        etMessage.setOnEditorActionListener(this);
        etMessage.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                return false;
            }
        });

        etMessage.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);

        etMessage.setOnFocusChangeListener((view, focus) -> {
            if(delegate != null) {
                if(focus) {
                    delegate.get().onKeyboardShow();
                }
                else {
                    delegate.get().onKeyboardHide();
                }
            }
        });

        etMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(delegate != null) {
                    delegate.get().startTyping();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                updateSendButton();
            }
        });

    }

    public void startRecording (View view) {

        audioMaxLengthReached = false;

        recording = new Recording();
        recording.start().subscribe(() -> toast = new InfiniteToast(getContext(), R.string.recording, false));

        rect = new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
        recordingStart = new Date();

        toastUpdateDisposable = Observable.interval(0, 1000, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(aLong -> {
            long remainingTime = AUIDO_MESSAGE_LENGTH_MAX - (new Date().getSeconds() - recordingStart.getSeconds());
            if (remainingTime <= 10) {
                toast.setText(String.format(getContext().getString(R.string.seconds_remaining), remainingTime));
            }
            if (remainingTime <= 0) {
                audioMaxLengthReached = true;
                this.presentAlertView();
            }
        });

    }

    public void presentAlertView () {
        finishRecording();
        toast.hide();

        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setTitle(getContext().getString(R.string.audio_length_limit_reached));

        // Set up the buttons
        builder.setPositiveButton(getContext().getString(R.string.send), (dialog, which) -> {
            delegate.get().sendAudio(recording);
            recording = null;
            dialog.cancel();
            audioMaxLengthReached = false;
        });
        builder.setNegativeButton(R.string.cancel, (dialog, which) -> {
            dialog.cancel();
            audioMaxLengthReached = false;
        });

        builder.show();

    }

    public void stopRecording (View view, MotionEvent motionEvent) {

        if (audioMaxLengthReached) {
            return;
        }

        finishRecording();
        if(recording != null) {
            if(delegate != null && recording.getDurationMillis() > 1000) {
                if(!rect.contains(view.getLeft() + (int) motionEvent.getX(), view.getTop() + (int) motionEvent.getY())){
                    // User moved outside bounds
                    ToastHelper.show(getContext(), getContext().getString(R.string.recording_cancelled));
                }
                else {
                    delegate.get().sendAudio(recording);
                }
            }
            else {
                ToastHelper.show(getContext(), getContext().getString(R.string.recording_too_short));
            }
            recording = null;
        }
    }

    public void finishRecording () {
        if(recording != null) {
            recording.stop();
        }
        if(toast != null) {
            toast.cancel();
        }
        if (toastUpdateDisposable != null) {
            toastUpdateDisposable.dispose();
        }
    }

    public void setAudioModeEnabled(boolean audioEnabled) {
        audioModeEnabled = audioEnabled;
        updateSendButton();
    }

    public void updateSendButton() {
        if (isNullOrEmpty(getMessageText()) && audioModeEnabled) {
            btnSend.setImageResource(R.drawable.ic_mic_24px);
            recordOnPress = true;
        }
        else {
            btnSend.setImageResource(R.drawable.ic_send_24px);
            recordOnPress = false;
        }
    }

    /** Show the message option popup, From here the user can send images and location messages.*/
    public void showOption () {
        if(delegate != null) {
            delegate.get().showOptions();
        }
    }

    public void hideOption () {
        if(delegate != null) {
            delegate.get().hideOptions();
        }
    }

    /** Send a text message when the done button is pressed on the keyboard.*/
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEND && delegate != null) {
            delegate.get().onSendPressed(getMessageText());
        }

        return false;
    }

//    @Override
//    public boolean onKey(View v, int keyCode, KeyEvent event) {
//        // if enter is pressed start calculating
//        if(delegate != null) {
//            delegate.get().startTyping();
//        }
//        if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
//            int editTextLineCount = ((EditText) v).getLineCount();
//            if (editTextLineCount >= getResources().getInteger(R.integer.chat_sdk_max_message_lines))
//                return true;
//        }
//        return false;
//    }

    public void setAttachmentAdapter(AttachmentAdapter attachmentAdapter) {
        this.attachmentAdapter = attachmentAdapter;
        recyclerView.setAdapter(attachmentAdapter);
    }

    public void addAttachemnt(MessageAttachment attachment) {
        if (attachmentAdapter == null) return;
        attachmentAdapter.add(attachment);
        attachments.setVisibility(View.VISIBLE);
    }

    public void setAttachments(List<MessageAttachment> messageAttachments) {
        if (attachmentAdapter == null) return;
        attachmentAdapter.add(messageAttachments);
        if (messageAttachments.isEmpty()) {
            attachments.setVisibility(View.GONE);
        } else {
            attachments.setVisibility(View.VISIBLE);
        }
    }

    public String getMessageText(){
        return etMessage.getText().toString();
    }

    public void clearText(){
        etMessage.getText().clear();
    }

    private boolean isNullOrEmpty(String string) {
        return string == null || string.isEmpty();
    }

}

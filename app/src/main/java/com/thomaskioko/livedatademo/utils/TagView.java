package com.thomaskioko.livedatademo.utils;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thomaskioko.livedatademo.R;

import java.util.ArrayList;
import java.util.List;

public class TagView extends RelativeLayout {

    private int mWidth;
    private int lineMargin;
    private int tagMargin;
    private int textPaddingLeft;
    private int textPaddingRight;
    private int textPaddingTop;
    private int texPaddingBottom;
    private List<Tag> mTags = new ArrayList<>();
    private LayoutInflater mInflater;

    public TagView(Context context) {
        super(context, null);
        init(context, null, 0, 0);
    }

    public TagView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public TagView(Context ctx, AttributeSet attrs, int defStyle) {
        super(ctx, attrs, defStyle);
        init(ctx, attrs, defStyle, defStyle);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TagView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyle, int defStyleRes) {
        AppConstants.DEBUG = (context.getApplicationContext().getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // get AttributeSet
        TypedArray typeArray = context.obtainStyledAttributes(attrs, R.styleable.TagView, defStyle, defStyleRes);
        this.lineMargin = (int) typeArray.getDimension(R.styleable.TagView_lineMargin, dpToPx(this.getContext(), AppConstants.DEFAULT_LINE_MARGIN));
        this.tagMargin = (int) typeArray.getDimension(R.styleable.TagView_tagMargin, dpToPx(this.getContext(), AppConstants.DEFAULT_TAG_MARGIN));
        this.textPaddingLeft = (int) typeArray.getDimension(R.styleable.TagView_textPaddingLeft, dpToPx(this.getContext(), AppConstants.DEFAULT_TAG_TEXT_PADDING_LEFT));
        this.textPaddingRight = (int) typeArray.getDimension(R.styleable.TagView_textPaddingRight, dpToPx(this.getContext(), AppConstants.DEFAULT_TAG_TEXT_PADDING_RIGHT));
        this.textPaddingTop = (int) typeArray.getDimension(R.styleable.TagView_textPaddingTop, dpToPx(this.getContext(), AppConstants.DEFAULT_TAG_TEXT_PADDING_TOP));
        this.texPaddingBottom = (int) typeArray.getDimension(R.styleable.TagView_textPaddingBottom, dpToPx(this.getContext(), AppConstants.DEFAULT_TAG_TEXT_PADDING_BOTTOM));
        typeArray.recycle();
        mWidth = getScreenWidth(context);
        // this.setWillNotDraw(false);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        // drawTags();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        /*int width = getMeasuredWidth();
        if (width <= 0) return;
        mWidth = getMeasuredWidth();
        drawTags();*/
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // View#onDraw is disabled in view group;
        // enable View#onDraw for view group : View#setWillNotDraw(false);
        // drawTags();
    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        /*if (changedView == this){
            if (visibility == View.VISIBLE){
                drawTags();
            }
        }*/
        super.onVisibilityChanged(changedView, visibility);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    private void drawTags() {
        if (getVisibility() != View.VISIBLE) return;
        // clear all tag
        removeAllViews();
        // layout padding left & layout padding right
        float total = getPaddingLeft() + getPaddingRight();
        int listIndex = 1;// List Index
        int index_bottom = 1;// The Tag to add below
        int index_header = 1;// The header tag of this line
        Tag tag_pre = null;
        for (Tag item : mTags) {
            // inflate tag layout
            View tagLayout = mInflater.inflate(R.layout.item_tagview, null);
            tagLayout.setId(listIndex);
            tagLayout.setBackgroundDrawable(getSelector(item));
            // tag text
            TextView tagView = tagLayout.findViewById(R.id.tv_tag_item_contain);
            tagView.setText(item.text);
            //tagView.setPadding(textPaddingLeft, textPaddingTop, textPaddingRight, texPaddingBottom);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tagView.getLayoutParams();
            params.setMargins(textPaddingLeft, textPaddingTop, textPaddingRight, texPaddingBottom);
            tagView.setLayoutParams(params);
            tagView.setTextColor(item.tagTextColor);
            tagView.setTextSize(TypedValue.COMPLEX_UNIT_SP, item.tagTextSize);
            // calculate　of tag layout width
            float tagWidth = tagView.getPaint().measureText(item.text) + textPaddingLeft + textPaddingRight;
            // tagView padding (left & right)

            LayoutParams tagParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            //tagParams.setMargins(0, 0, 0, 0);
            //add margin of each line
            tagParams.bottomMargin = lineMargin;
            if (mWidth <= total + tagMargin + tagWidth + dpToPx(this.getContext(), AppConstants.LAYOUT_WIDTH_OFFSET)) {
                //need to add in new line
                tagParams.addRule(RelativeLayout.BELOW, index_bottom);
                // initialize total param (layout padding left & layout padding right)
                total = getPaddingLeft() + getPaddingRight();
                index_bottom = listIndex;
                index_header = listIndex;
            } else {
                //no need to new line
                tagParams.addRule(RelativeLayout.ALIGN_TOP, index_header);
                //not header of the line
                if (listIndex != index_header) {
                    tagParams.addRule(RelativeLayout.RIGHT_OF, listIndex - 1);
                    tagParams.leftMargin = tagMargin;
                    total += tagMargin;
                    if (tag_pre.tagTextSize < item.tagTextSize) {
                        index_bottom = listIndex;
                    }
                }
            }
            total += tagWidth;
            addView(tagLayout, tagParams);
            tag_pre = item;
            listIndex++;
        }
    }

    private Drawable getSelector(Tag tag) {
        if (tag.background != null) return tag.background;
        StateListDrawable states = new StateListDrawable();
        GradientDrawable gd_normal = new GradientDrawable();
        gd_normal.setColor(tag.layoutColor);
        gd_normal.setCornerRadius(tag.radius);
        if (tag.layoutBorderSize > 0) {
            gd_normal.setStroke(dpToPx(getContext(), tag.layoutBorderSize), tag.layoutBorderColor);
        }
        GradientDrawable gd_press = new GradientDrawable();
        gd_press.setColor(tag.layoutColorPress);
        gd_press.setCornerRadius(tag.radius);
        states.addState(new int[]{android.R.attr.state_pressed}, gd_press);
        //must add state_pressed first，or state_pressed will not take effect
        states.addState(new int[]{}, gd_normal);
        return states;
    }

    public void addTag(Tag tag) {
        mTags.add(tag);
        drawTags();
    }

    public static int dpToPx(Context c, float dipValue) {
        DisplayMetrics metrics = c.getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics);
    }

    public static int spToPx(Context context, float spValue) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, metrics);
    }

    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static class Tag {

        public int id;
        public String text;
        public int tagTextColor;
        public float tagTextSize;
        public int layoutColor;
        public int layoutColorPress;
        public int deleteIndicatorColor;
        public float deleteIndicatorSize;
        public float radius;
        public String deleteIcon;
        public float layoutBorderSize;
        public int layoutBorderColor;
        public Drawable background;

        public Tag(String text) {
            init(0, text, AppConstants.DEFAULT_TAG_TEXT_COLOR, AppConstants.DEFAULT_TAG_TEXT_SIZE, AppConstants.DEFAULT_TAG_LAYOUT_COLOR, AppConstants.DEFAULT_TAG_LAYOUT_COLOR_PRESS,
                    AppConstants.DEFAULT_TAG_DELETE_INDICATOR_COLOR, AppConstants.DEFAULT_TAG_DELETE_INDICATOR_SIZE, AppConstants.DEFAULT_TAG_RADIUS, AppConstants.DEFAULT_TAG_DELETE_ICON, AppConstants.DEFAULT_TAG_LAYOUT_BORDER_SIZE, AppConstants.DEFAULT_TAG_LAYOUT_BORDER_COLOR);
        }

        public Tag(String text, int color) {
            init(0, text, AppConstants.DEFAULT_TAG_TEXT_COLOR, AppConstants.DEFAULT_TAG_TEXT_SIZE, color, AppConstants.DEFAULT_TAG_LAYOUT_COLOR_PRESS,
                    AppConstants.DEFAULT_TAG_DELETE_INDICATOR_COLOR, AppConstants.DEFAULT_TAG_DELETE_INDICATOR_SIZE, AppConstants.DEFAULT_TAG_RADIUS, AppConstants.DEFAULT_TAG_DELETE_ICON, AppConstants.DEFAULT_TAG_LAYOUT_BORDER_SIZE, AppConstants.DEFAULT_TAG_LAYOUT_BORDER_COLOR);

        }

        public Tag(String text, String color) {
            init(0, text, AppConstants.DEFAULT_TAG_TEXT_COLOR, AppConstants.DEFAULT_TAG_TEXT_SIZE, Color.parseColor(color), AppConstants.DEFAULT_TAG_LAYOUT_COLOR_PRESS,
                    AppConstants.DEFAULT_TAG_DELETE_INDICATOR_COLOR, AppConstants.DEFAULT_TAG_DELETE_INDICATOR_SIZE, AppConstants.DEFAULT_TAG_RADIUS, AppConstants.DEFAULT_TAG_DELETE_ICON, AppConstants.DEFAULT_TAG_LAYOUT_BORDER_SIZE, AppConstants.DEFAULT_TAG_LAYOUT_BORDER_COLOR);

        }

        public Tag(int id, String text) {
            init(id, text, AppConstants.DEFAULT_TAG_TEXT_COLOR, AppConstants.DEFAULT_TAG_TEXT_SIZE, AppConstants.DEFAULT_TAG_LAYOUT_COLOR, AppConstants.DEFAULT_TAG_LAYOUT_COLOR_PRESS,
                    AppConstants.DEFAULT_TAG_DELETE_INDICATOR_COLOR, AppConstants.DEFAULT_TAG_DELETE_INDICATOR_SIZE, AppConstants.DEFAULT_TAG_RADIUS, AppConstants.DEFAULT_TAG_DELETE_ICON, AppConstants.DEFAULT_TAG_LAYOUT_BORDER_SIZE, AppConstants.DEFAULT_TAG_LAYOUT_BORDER_COLOR);
        }

        public Tag(int id, String text, int color) {
            init(id, text, AppConstants.DEFAULT_TAG_TEXT_COLOR, AppConstants.DEFAULT_TAG_TEXT_SIZE, color, AppConstants.DEFAULT_TAG_LAYOUT_COLOR_PRESS,
                    AppConstants.DEFAULT_TAG_DELETE_INDICATOR_COLOR, AppConstants.DEFAULT_TAG_DELETE_INDICATOR_SIZE, AppConstants.DEFAULT_TAG_RADIUS, AppConstants.DEFAULT_TAG_DELETE_ICON, AppConstants.DEFAULT_TAG_LAYOUT_BORDER_SIZE, AppConstants.DEFAULT_TAG_LAYOUT_BORDER_COLOR);

        }

        public Tag(int id, String text, String color) {
            init(id, text, AppConstants.DEFAULT_TAG_TEXT_COLOR, AppConstants.DEFAULT_TAG_TEXT_SIZE, Color.parseColor(color), AppConstants.DEFAULT_TAG_LAYOUT_COLOR_PRESS, AppConstants.DEFAULT_TAG_DELETE_INDICATOR_COLOR, AppConstants.DEFAULT_TAG_DELETE_INDICATOR_SIZE, AppConstants.DEFAULT_TAG_RADIUS, AppConstants.DEFAULT_TAG_DELETE_ICON, AppConstants.DEFAULT_TAG_LAYOUT_BORDER_SIZE, AppConstants.DEFAULT_TAG_LAYOUT_BORDER_COLOR);

        }

        private void init(int id, String text, int tagTextColor, float tagTextSize, int layout_color, int layout_color_press, int deleteIndicatorColor,
                          float deleteIndicatorSize, float radius, String deleteIcon, float layoutBorderSize, int layoutBorderColor) {
            this.id = id;
            this.text = text;
            this.tagTextColor = tagTextColor;
            this.tagTextSize = tagTextSize;
            this.layoutColor = layout_color;
            this.layoutColorPress = layout_color_press;
            this.deleteIndicatorColor = deleteIndicatorColor;
            this.deleteIndicatorSize = deleteIndicatorSize;
            this.radius = radius;
            this.deleteIcon = deleteIcon;
            this.layoutBorderSize = layoutBorderSize;
            this.layoutBorderColor = layoutBorderColor;
        }
    }


}
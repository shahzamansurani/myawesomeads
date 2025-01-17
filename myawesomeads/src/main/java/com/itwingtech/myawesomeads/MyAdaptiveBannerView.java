package com.itwingtech.myawesomeads;


import static android.view.Gravity.CENTER;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.android.material.card.MaterialCardView;

public class MyAdaptiveBannerView extends MaterialCardView {
    private Context context;

    // Constructor for programmatic use
    public MyAdaptiveBannerView(Context context) {
        super(context);
        init(context, null);
    }

    // Constructor used when inflating from XML
    public MyAdaptiveBannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    // Constructor used when inflating from XML with a style attribute
    public MyAdaptiveBannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    // Initialization method
    private void init(Context context, @Nullable AttributeSet attrs) {
        this.context = context;
        setLayoutParams(new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        // Set up layout and child views (TextView)
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(CENTER);
        layout.setVerticalGravity(CENTER);
        layout.setLayoutParams(new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        // Create a TextView for the banner text
        TextView textView = new TextView(context);
        textView.setText("Banner Ad Area...");
        textView.setGravity(CENTER);
        textView.setLayoutParams(new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));


        // Add TextView to the LinearLayout
        layout.addView(textView);

        // Add the layout to the MaterialCardView
        addView(layout);


        // If there are attributes from XML, apply them
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MyAdaptiveBannerView);
            String bannerText = a.getString(R.styleable.MyAdaptiveBannerView_adaptiveBannerText);
            int textColor = a.getColor(R.styleable.MyAdaptiveBannerView_adaptiveBannerTextColor, 0);
            int backgroundColor = a.getColor(R.styleable.MyAdaptiveBannerView_adaptiveBannerBackgroundColor, 0);
            int strokeColor = a.getColor(R.styleable.MyAdaptiveBannerView_adaptiveBannerStrokeColor, 0);
            float strokeWidth = a.getDimension(R.styleable.MyAdaptiveBannerView_adaptiveBannerStrokeWidth, 0);
            float cornerRadius = a.getDimension(R.styleable.MyAdaptiveBannerView_adaptiveBannerCornerRadius, 0);

            // Apply custom attributes
            if (bannerText != null) {
                textView.setText(bannerText);
            }
            if (textColor != 0) {
                textView.setTextColor(textColor);
            }
            if (backgroundColor != 0) {
                setCardBackgroundColor(backgroundColor);
            }
            if (strokeColor != 0) {
                setStrokeColor(strokeColor);
            }
            if (strokeWidth != 0) {
                setStrokeWidth((int) strokeWidth);
            }
            if (cornerRadius != 0) {
                // Retrieve corner radius from XML if provided, otherwise use default
                // Retrieve corner radius from XML, defaulting to 0 if not provided
                float adRadius = a.getDimension(R.styleable.MyAdaptiveBannerView_adaptiveBannerCornerRadius, 0);
                setShapeAppearanceModel(
                        getShapeAppearanceModel()
                                .toBuilder()
                                .setAllCornerSizes(adRadius)
                                .build());
            }

            // Recycle the TypedArray to free up memory
            a.recycle();
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (context instanceof Activity) {
            MyAwesomeAds.loadAdaptiveBanner((Activity) context, MyAdaptiveBannerView.this);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int fixedHeightPx = DimensionUtil.convertSdpToPx(context, 60);  // Convert 50sdp to px
        int padding = DimensionUtil.convertSdpToPx(context, 5);         // Convert 3sdp to px
        int customHeightMeasureSpec = MeasureSpec.makeMeasureSpec(fixedHeightPx + padding, MeasureSpec.EXACTLY);
        int customWidthMeasureSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(widthMeasureSpec) + padding, MeasureSpec.EXACTLY);
        super.onMeasure(customWidthMeasureSpec, customHeightMeasureSpec);
        setPadding(padding, padding, padding, padding);
    }
}
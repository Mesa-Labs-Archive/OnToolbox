package com.mesalabs.cerberus.ui.app;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.icu.text.NumberFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.mesalabs.on.toolbox.R;
import com.samsung.android.ui.app.SeslAlertDialog;
import com.samsung.android.ui.widget.SeslProgressBar;

/*
 * Cerberus Core App
 *
 * Coded by BlackMesa @2020
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * ULTRA-MEGA-PRIVATE SOURCE CODE. SHARING TO DEVKINGS TEAM
 * EXTERNALS IS PROHIBITED AND WILL BE PUNISHED WITH ANAL ABUSE.
 */

public class ProgressDialog extends SeslAlertDialog {
    public static final int STYLE_SPINNER = 0;
    public static final int STYLE_HORIZONTAL = 1;

    private Context mContext;

    private SeslProgressBar mProgress;
    private TextView mMessageView;

    private int mProgressStyle = STYLE_SPINNER;
    private TextView mProgressNumber;
    private String mProgressNumberFormat;
    private TextView mProgressPercent;
    private NumberFormat mProgressPercentFormat;

    private int mMax;
    private int mProgressVal;
    private int mSecondaryProgressVal;
    private int mIncrementBy;
    private int mIncrementSecondaryBy;
    private Drawable mProgressDrawable;
    private Drawable mIndeterminateDrawable;
    private CharSequence mMessage;
    private boolean mIndeterminate;

    private boolean mHasStarted;
    private Handler mViewUpdateHandler;

    public ProgressDialog(Context context) {
        super(context);
        mContext = context;
        initFormats();
    }

    public ProgressDialog(Context context, int theme) {
        super(context, theme);
        mContext = context;
        initFormats();
    }
    private void initFormats() {
        mProgressNumberFormat = "%1d/%2d";
        mProgressPercentFormat = NumberFormat.getPercentInstance();
        mProgressPercentFormat.setMaximumFractionDigits(0);
    }

    public static ProgressDialog show(Context context, CharSequence title, CharSequence message) {
        return show(context, title, message, false);
    }

    public static ProgressDialog show(Context context, CharSequence title, CharSequence message, boolean indeterminate) {
        return show(context, title, message, indeterminate, false, null);
    }

    public static ProgressDialog show(Context context, CharSequence title, CharSequence message, boolean indeterminate, boolean cancelable) {
        return show(context, title, message, indeterminate, cancelable, null);
    }

    public static ProgressDialog show(Context context, CharSequence title, CharSequence message, boolean indeterminate, boolean cancelable, OnCancelListener cancelListener) {
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setIndeterminate(indeterminate);
        dialog.setCancelable(cancelable);
        dialog.setOnCancelListener(cancelListener);
        dialog.show();
        return dialog;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        TypedArray a = mContext.obtainStyledAttributes(null, R.styleable.SeslAlertDialog, R.attr.alertDialogStyle, 0);
        if (mProgressStyle == STYLE_HORIZONTAL) {
            mViewUpdateHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);

                    /* Update the number and percent */
                    int progress = mProgress.getProgress();
                    int max = mProgress.getMax();
                    if (mProgressNumberFormat != null) {
                        String format = mProgressNumberFormat;
                        mProgressNumber.setText(String.format(format, progress, max));
                    } else {
                        mProgressNumber.setText("");
                    }
                    if (mProgressPercentFormat != null) {
                        double percent = (double) progress / (double) max;
                        SpannableString tmp = new SpannableString(mProgressPercentFormat.format(percent));
                        tmp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0, tmp.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        mProgressPercent.setText(tmp);
                    } else {
                        mProgressPercent.setText("");
                    }
                }
            };
            View view = inflater.inflate(a.getResourceId(R.styleable.SeslAlertDialog_horizontalProgressLayout, R.layout.sesl_alert_dialog_progress), null);
            mProgress = (SeslProgressBar) view.findViewById(R.id.progress);
            mProgressNumber = (TextView) view.findViewById(R.id.progress_number);
            mProgressPercent = (TextView) view.findViewById(R.id.progress_percent);
            setView(view);
        } else {
            View view = inflater.inflate(a.getResourceId(R.styleable.SeslAlertDialog_progressLayout, R.layout.sesl_progress_dialog), null);
            mProgress = (SeslProgressBar) view.findViewById(R.id.progress);
            mMessageView = (TextView) view.findViewById(R.id.message);
            setView(view);
        }
        a.recycle();
        if (mMax > 0) {
            setMax(mMax);
        }
        if (mProgressVal > 0) {
            setProgress(mProgressVal);
        }
        if (mSecondaryProgressVal > 0) {
            setSecondaryProgress(mSecondaryProgressVal);
        }
        if (mIncrementBy > 0) {
            incrementProgressBy(mIncrementBy);
        }
        if (mIncrementSecondaryBy > 0) {
            incrementSecondaryProgressBy(mIncrementSecondaryBy);
        }
        if (mProgressDrawable != null) {
            setProgressDrawable(mProgressDrawable);
        }
        if (mIndeterminateDrawable != null) {
            setIndeterminateDrawable(mIndeterminateDrawable);
        }
        if (mMessage != null) {
            setMessage(mMessage);
        }
        setIndeterminate(mIndeterminate);
        onProgressChanged();
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        mHasStarted = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        mHasStarted = false;
    }

    public void setProgress(int value) {
        if (mHasStarted) {
            try {
                mProgress.setProgress(value);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            onProgressChanged();
        } else {
            mProgressVal = value;
        }
    }

    public void setSecondaryProgress(int secondaryProgress) {
        if (mProgress != null) {
            try {
                mProgress.setSecondaryProgress(secondaryProgress);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            onProgressChanged();
        } else {
            mSecondaryProgressVal = secondaryProgress;
        }
    }

    public int getProgress() {
        if (mProgress != null) {
            return mProgress.getProgress();
        }
        return mProgressVal;
    }

    public int getSecondaryProgress() {
        if (mProgress != null) {
            return mProgress.getSecondaryProgress();
        }
        return mSecondaryProgressVal;
    }

    public int getMax() {
        if (mProgress != null) {
            return mProgress.getMax();
        }
        return mMax;
    }

    public void setMax(int max) {
        if (mProgress != null) {
            try {
                mProgress.setMax(max);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            onProgressChanged();
        } else {
            mMax = max;
        }
    }

    public void incrementProgressBy(int diff) {
        if (mProgress != null) {
            try {
                mProgress.incrementProgressBy(diff);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            onProgressChanged();
        } else {
            mIncrementBy += diff;
        }
    }

    public void incrementSecondaryProgressBy(int diff) {
        if (mProgress != null) {
            try {
                mProgress.incrementSecondaryProgressBy(diff);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            onProgressChanged();
        } else {
            mIncrementSecondaryBy += diff;
        }
    }

    public void setProgressDrawable(Drawable d) {
        if (mProgress != null) {
            try {
                mProgress.setProgressDrawable(d);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        } else {
            mProgressDrawable = d;
        }
    }

    public void setIndeterminateDrawable(Drawable d) {
        if (mProgress != null) {
            mProgress.setIndeterminateDrawable(d);
        } else {
            mIndeterminateDrawable = d;
        }
    }

    public void setIndeterminate(boolean indeterminate) {
        if (mProgress != null) {
            try {
                mProgress.setIndeterminate(indeterminate);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        } else {
            mIndeterminate = indeterminate;
        }
    }

    public boolean isIndeterminate() {
        if (mProgress != null) {
            return mProgress.isIndeterminate();
        }
        return mIndeterminate;
    }

    @Override
    public void setMessage(CharSequence message) {
        if (mProgress != null) {
            if (mProgressStyle == STYLE_HORIZONTAL) {
                super.setMessage(message);
            } else {
                mMessageView.setText(message);
            }
        } else {
            mMessage = message;
        }
    }

    public void setProgressStyle(int style) {
        mProgressStyle = style;
    }

    public void setProgressNumberFormat(String format) {
        mProgressNumberFormat = format;
        onProgressChanged();
    }

    public void setProgressPercentFormat(NumberFormat format) {
        mProgressPercentFormat = format;
        onProgressChanged();
    }

    private void onProgressChanged() {
        if (mProgressStyle == STYLE_HORIZONTAL) {
            if (mViewUpdateHandler != null && !mViewUpdateHandler.hasMessages(0)) {
                mViewUpdateHandler.sendEmptyMessage(0);
            }
        }
    }
}

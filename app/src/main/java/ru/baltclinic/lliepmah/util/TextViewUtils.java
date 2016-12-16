package ru.baltclinic.lliepmah.util;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

import ru.baltclinic.lliepmah.models.Action;


public class TextViewUtils {

    public static final String VALUE_SPACE_VALUE = "%1$s %2$s";
    public static final String VALUE_DASH_VALUE = "%1$s - %2$s";

    private static final String EMPTY = "";
    private static final String ACTION_INSTEAD = "вместо";
    private static final String ACTION_ROUBLE = "руб.";
    private static final String LINE_BREAK = "\n";
    public static final String SPACE = " ";

    private static final String BULLET = "• ";
    public static final String TAG_P = "<p>";

    public static void setTextIfNotEmpty(TextView textView, String text) {
        textView.setText(text != null ? fromHtml(text) : EMPTY);
    }

    public static void setTextIfNotEmpty(TextView textView, CharSequence text) {
        textView.setText(text != null ? text : EMPTY);
    }

    private static CharSequence fromHtml(String text) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            return Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(text);
        }
    }

    public static void setTextWithPrefixIfNotEmptyOrHide(TextView textView, String prefix, String text) {
        boolean hasText = !TextUtils.isEmpty(text);
        textView.setText(hasText ? splitValues(VALUE_SPACE_VALUE, prefix, text) : EMPTY);
        textView.setVisibility(hasText ? View.VISIBLE : View.GONE);
    }

    public static String splitValues(String format, String value1, String value2) {
        return String.format(Locale.getDefault(), format, value1, value2);
    }

    public static String splitValues(String delimeter, String... values) {
        return TextUtils.join(delimeter, values);
    }

    public static CharSequence formatList(String... values) {

        SpannableStringBuilder builder = new SpannableStringBuilder();
        for (String value : values) {
            if (!TextUtils.isEmpty(value)) {
                builder.append(BULLET);
                builder.append(value);
                builder.append(LINE_BREAK);
            }
        }
        int resultLength = builder.length();
        if (resultLength > 0) {
            builder.delete(resultLength - LINE_BREAK.length(), resultLength);
        }
        return builder;
    }

    public static CharSequence makeActionTitle(Context context, Action action) {

        String dates = splitValues(VALUE_DASH_VALUE, action.getFromDateString(), action.getToDateString());
        String programm = action.getTitle();

        String subtitle = action.getSubtitle().replaceAll(LINE_BREAK, SPACE).trim();

        int end = 0;
        int start = subtitle.indexOf(ACTION_INSTEAD);
        if (start > 0 && (end = subtitle.indexOf(ACTION_ROUBLE, start)) > 0) {
            start += ACTION_INSTEAD.length();
            boolean isDigits = TextUtils.isDigitsOnly(subtitle.substring(start, end).replaceAll(SPACE, ""));
            end = isDigits ? end + ACTION_ROUBLE.length() : -1;
        }

        Spannable spannableSubtitle = new SpannableString(subtitle);
        if (start > 0 && end > start && end <= spannableSubtitle.length()) {
            spannableSubtitle.setSpan(new StrikethroughSpan(), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableSubtitle.setSpan(new ForegroundColorSpan(Color.RED), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        return TextUtils.concat(dates, LINE_BREAK, programm, LINE_BREAK, spannableSubtitle);
    }
}

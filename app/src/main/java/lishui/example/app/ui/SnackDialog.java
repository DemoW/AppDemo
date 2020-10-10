package lishui.example.app.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;

import lishui.example.app.R;

/**
 * Created by lishui.lin on 20-9-27
 */
public class SnackDialog {

    public static void showTestDialog(Context context) {
        new BuilderWrapper(context)
                .setTitle(android.R.string.dialog_alert_title)
                .setMessage("test SnackDialog")
                .setNegativeButton(android.R.string.cancel, null)
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }

    public static class BuilderWrapper extends AlertDialog.Builder {

        public BuilderWrapper(Context context) {
            this(context, R.style.Theme_SnackDialog);
        }

        public BuilderWrapper(Context context, int themeResId) {
            super(context, R.style.Theme_SnackDialog);
        }

        @Override
        public AlertDialog create() {
            final AlertDialog dialog = super.create();
            dialog.getWindow().setGravity(Gravity.BOTTOM);
            //dialog.getWindow().setWindowAnimations(R.style.Animation_Snack_Dialog);
            return dialog;
        }

        @Override
        public AlertDialog show() {
            final AlertDialog dialog = create();
            dialog.show();
            return dialog;
        }

        public BuilderWrapper setTitle(int titleId) {
            super.setTitle(titleId);
            return this;
        }

        public BuilderWrapper setTitle(CharSequence title) {
            super.setTitle(title);
            return this;
        }

        public BuilderWrapper setCustomTitle(View customTitleView) {
            super.setCustomTitle(customTitleView);
            return this;
        }

        public BuilderWrapper setMessage(int messageId) {
            super.setMessage(messageId);
            return this;
        }

        public BuilderWrapper setMessage(CharSequence message) {
            super.setMessage(message);
            return this;
        }

        public BuilderWrapper setIcon(int iconId) {
            super.setIcon(iconId);
            return this;
        }

        public BuilderWrapper setIcon(Drawable icon) {
            super.setIcon(icon);
            return this;
        }

        public BuilderWrapper setIconAttribute(int attrId) {
            super.setIconAttribute(attrId);
            return this;
        }

        public BuilderWrapper setPositiveButton(int textId, DialogInterface.OnClickListener listener) {
            super.setPositiveButton(textId, listener);
            return this;
        }

        public BuilderWrapper setPositiveButton(CharSequence text, DialogInterface.OnClickListener listener) {
            super.setPositiveButton(text, listener);
            return this;
        }

        public BuilderWrapper setNegativeButton(int textId, DialogInterface.OnClickListener listener) {
            super.setNegativeButton(textId, listener);
            return this;
        }

        public BuilderWrapper setNegativeButton(CharSequence text, DialogInterface.OnClickListener listener) {
            super.setNegativeButton(text, listener);
            return this;
        }

        public BuilderWrapper setNeutralButton(int textId, DialogInterface.OnClickListener listener) {
            super.setNeutralButton(textId, listener);
            return this;
        }

        public BuilderWrapper setNeutralButton(CharSequence text, DialogInterface.OnClickListener listener) {
            super.setNeutralButton(text, listener);
            return this;
        }

        public BuilderWrapper setCancelable(boolean cancelable) {
            super.setCancelable(cancelable);
            return this;
        }

        public BuilderWrapper setOnCancelListener(DialogInterface.OnCancelListener onCancelListener) {
            super.setOnCancelListener(onCancelListener);
            return this;
        }

        public BuilderWrapper setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
            super.setOnDismissListener(onDismissListener);
            return this;
        }

        public BuilderWrapper setOnKeyListener(DialogInterface.OnKeyListener onKeyListener) {
            super.setOnKeyListener(onKeyListener);
            return this;
        }

        public BuilderWrapper setItems(int itemsId, DialogInterface.OnClickListener listener) {
            super.setItems(itemsId, listener);
            return this;
        }

        public BuilderWrapper setItems(CharSequence[] items, DialogInterface.OnClickListener listener) {
            super.setItems(items, listener);
            return this;
        }

        public BuilderWrapper setAdapter(ListAdapter adapter, DialogInterface.OnClickListener listener) {
            super.setAdapter(adapter, listener);
            return this;
        }

        public BuilderWrapper setCursor(Cursor cursor, DialogInterface.OnClickListener listener, String labelColumn) {
            super.setCursor(cursor, listener, labelColumn);
            return this;
        }

        public BuilderWrapper setMultiChoiceItems(int itemsId, boolean[] checkedItems, DialogInterface.OnMultiChoiceClickListener listener) {
            super.setMultiChoiceItems(itemsId, checkedItems, listener);
            return this;
        }

        public BuilderWrapper setMultiChoiceItems(CharSequence[] items, boolean[] checkedItems, DialogInterface.OnMultiChoiceClickListener listener) {
            super.setMultiChoiceItems(items, checkedItems, listener);
            return this;
        }

        public BuilderWrapper setMultiChoiceItems(Cursor cursor, String isCheckedColumn, String labelColumn, DialogInterface.OnMultiChoiceClickListener listener) {
            super.setMultiChoiceItems(cursor, isCheckedColumn, labelColumn, listener);
            return this;
        }

        public BuilderWrapper setSingleChoiceItems(int itemsId, int checkedItem, DialogInterface.OnClickListener listener) {
            super.setSingleChoiceItems(itemsId, checkedItem, listener);
            return this;
        }

        public BuilderWrapper setSingleChoiceItems(Cursor cursor, int checkedItem, String labelColumn, DialogInterface.OnClickListener listener) {
            super.setSingleChoiceItems(cursor, checkedItem, labelColumn, listener);
            return this;
        }

        public BuilderWrapper setSingleChoiceItems(CharSequence[] items, int checkedItem, DialogInterface.OnClickListener listener) {
            super.setSingleChoiceItems(items, checkedItem, listener);
            return this;
        }

        public BuilderWrapper setSingleChoiceItems(ListAdapter adapter, int checkedItem, DialogInterface.OnClickListener listener) {
            super.setSingleChoiceItems(adapter, checkedItem, listener);
            return this;
        }

        public BuilderWrapper setOnItemSelectedListener(AdapterView.OnItemSelectedListener listener) {
            super.setOnItemSelectedListener(listener);
            return this;
        }

        public BuilderWrapper setView(int layoutResId) {
            super.setView(layoutResId);
            return this;
        }

        public BuilderWrapper setView(View view) {
            super.setView(view);
            return this;
        }
    }
}
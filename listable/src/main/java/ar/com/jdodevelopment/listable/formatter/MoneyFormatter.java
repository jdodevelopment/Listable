package ar.com.jdodevelopment.listable.formatter;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Format numbers as money in a TextView.
 *
 * @author Juan Daniel Ornella
 */
public class MoneyFormatter implements ListableFormatter {



    @Override
    public void setValueInView(@NonNull View view, @NonNull Object value) {
        TextView textView = (TextView) view;
        Number douebleValue = Double.parseDouble(value.toString());
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.getDefault());
        textView.setText(numberFormat.format(douebleValue));
    }

    @Override
    public void setNullInView(View view) {
        TextView textView = (TextView) view;
        textView.setText("-");
    }

    @Override
    public boolean supportedViewType(View view) {
        return view instanceof TextView;
    }

    @Override
    public boolean supportedValueType(Object value) {
        return value instanceof Integer || value instanceof Long || value instanceof Float || value instanceof Double;
    }


}

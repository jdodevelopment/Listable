/*
    Copyright (c) 2019 Juan Daniel Ornella <juan.daniel.sp@gmail.com>

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/

package ar.com.jdodevelopment.listable.formatter;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Set formatted {@link java.util.Date} values or child classes in a TextView.
 *
 * @author Juan Daniel Ornella
 */
public class DateFormatter implements ListableFormatter {




    @Override
    public void setValueInView(@NonNull View view, @NonNull Object value) {
        TextView textView = (TextView) view;
        Date date = (Date) value;
        textView.setText(getFormattedValue(date.getTime()));
    }


    private String getFormattedValue(long milis) {
        String datePattern = "dd/MM/yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(datePattern, Locale.getDefault());
        return dateFormat.format(new java.util.Date(milis));
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
        return value instanceof Date;
    }


}

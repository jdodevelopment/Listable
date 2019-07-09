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


/**
 * Default formatter for the Annotation '{@link ar.com.jdodevelopment.listable.annotation.ListableField}'.
 * Call the method toString() of an object and set it in a {@link TextView}.
 * Ensure that override the method String.toString if you are trying to format your custom entity.
 *
 * @author Juan Daniel Ornella
 */
public class ToStringFormatter implements ListableFormatter {


    @Override
    public void setValueInView(@NonNull View view, @NonNull Object value) {
        TextView textView = (TextView) view;
        textView.setText(value.toString());
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
        return true;
    }


}
